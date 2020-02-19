package com.chengzi.apiunion.procurement.admin.web.controllers.coupon;

import com.chengzi.apiunion.common.async.ProgressContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.excel.AsyncExportExcelController;
import com.chengzi.apiunion.common.web.pojo.excel.ExcelData;
import com.chengzi.apiunion.coupon.enums.SendCouponResultEnum;
import com.chengzi.apiunion.coupon.pojo.CouponConfigDO;
import com.chengzi.apiunion.coupon.service.CouponConfigService;
import com.chengzi.apiunion.coupon.service.CouponService;
import com.chengzi.apiunion.procurement.admin.web.formbean.coupon.SendCouponConfigForm;
import com.chengzi.apiunion.procurement.admin.web.helper.coupon.CouponHelper;
import com.chengzi.apiunion.user.enums.AuditStatusEnum;
import com.chengzi.apiunion.user.pojo.search.UserSearchBO;
import com.chengzi.apiunion.user.pojo.search.query.UserSearchQuery;
import com.chengzi.apiunion.user.search.UserSearchService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.*;

/**
 * 管理端主动发放优惠券
 */
//public class SendCouponConfigController extends AbstractApiController<SendCouponConfigForm> {
public class SendCouponConfigController extends AsyncExportExcelController<SendCouponConfigForm> {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponConfigService couponConfigService;

    private static final Logger logger = LoggerFactory.getLogger(SendCouponConfigController.class);

    @Override
    protected Result<ExcelData> doExport(HttpServletRequest request, HttpServletResponse response, SendCouponConfigForm command) {

        CouponConfigDO couponConfigDO = couponConfigService.getCouponConfigById(command.getCouponConfigId());

        //标识每个用户可以领取的优惠券限制
        int limitForEach = 5;
        List<Long> successUserList = new ArrayList<>();
        List<Long> errorUserList = new ArrayList<>();
        String commonErrorReason = null;
        //保存用户错误原因
        HashMap<Long, String> errorReasonMap = new HashMap<>();
        List<Long> userIds = null;//new ArrayList<>( command.getUserIds());
        int selectType = command.getSelectType();
        if (selectType == 1) {//全部用户
            userIds = searchUserList();
        } else {
            userIds = new ArrayList<>();
            Set<Long> userSet = command.getUserIds();
            if (userSet != null && userSet.size() > 0) {
                userIds.addAll(userSet);
            }
        }
        logger.error("send coupon user size:" + userIds.size());
        Long couponConfigId = command.getCouponConfigId();
        //循环执行发放优惠券操作，记录错误信息
        int totalSize = userIds.size();
        if (userIds != null && userIds.size() > 0) {
            for (int index = 0; index < userIds.size(); index++) {
                Long userId = userIds.get(index);
                Result<Integer> sendResult = couponService.sendCouponForBatch(userId, limitForEach, couponConfigId, couponConfigDO);
                //设置执行进度
                ProgressContext.percent(ProgressContext.getCurrent(), index + 1, totalSize);
                if (sendResult.isSuccess()) {
                    successUserList.add(userId);
                } else {
                    int dataCode = sendResult.getData();
                    if (dataCode == SendCouponResultEnum.TIME_VALIDATE.getCode()) {
                        commonErrorReason = SendCouponResultEnum.TIME_VALIDATE.getDesc();
                        break;
                    } else if (dataCode == SendCouponResultEnum.COUNT_NOTENOUGHT.getCode()) {
                        commonErrorReason = SendCouponResultEnum.COUNT_NOTENOUGHT.getDesc();
                        break;
                    } else if (dataCode == SendCouponResultEnum.COUNT_LIMIT_FOREACH.getCode()) {
                        errorReasonMap.put(userId, SendCouponResultEnum.COUNT_LIMIT_FOREACH.getDesc());
                    } else {
                        //其它异常，用户个例情况
                        errorReasonMap.put(userId, SendCouponResultEnum.SEND_SYS_ERROR.getDesc());
                    }
                }
            }
        }
        //过滤出失败的用户
        userIds.removeAll(successUserList);
        errorUserList = userIds;

        Result<ExcelData> result = CouponHelper.buildErrorExcel(commonErrorReason, successUserList, errorUserList, errorReasonMap);

        return result;
    }

    /**
     * 从ES中查询所有的用户
     *
     * @return
     */
    private List<Long> searchUserList() {
        UserSearchService userSearchService = BeanFactory.getBean(UserSearchService.class);
        int from = 1;
        int pageSize = 30;
        List<Long> userIds = new ArrayList<>();
        for (; ; from++) {
            UserSearchQuery userSearchQuery = new UserSearchQuery();
            userSearchQuery.setSize(pageSize);
            userSearchQuery.setFrom(from);
            userSearchQuery.setAuditStatus(CollectionUtil.asArrayList(AuditStatusEnum.ACCOUNT_PASS_AUDIT.getCode()));
            userSearchQuery.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));
            SearchResult<UserSearchBO> searchResult = userSearchService.query(userSearchQuery);
            long totalHits = searchResult.getTotalHits();
            if (totalHits > 0) {
                List<UserSearchBO> userSearchBOS = searchResult.getData();
                userSearchBOS.forEach(userSearchBO -> {
                    userIds.add(userSearchBO.getId());
                    logger.error("username:" + userSearchBO.getNickName());
                });
            }
            if (totalHits < pageSize) {
                break;
            }
        }
        return userIds;
    }
}
