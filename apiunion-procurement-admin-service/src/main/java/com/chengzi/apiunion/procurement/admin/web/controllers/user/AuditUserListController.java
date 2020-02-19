package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.UserListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.user.AuditUserListBO;
import com.chengzi.apiunion.user.enums.AccountTypeEnum;
import com.chengzi.apiunion.user.enums.AuditStatusEnum;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.pojo.search.UserSearchBO;
import com.chengzi.apiunion.user.pojo.search.query.UserSearchQuery;
import com.chengzi.apiunion.user.search.UserSearchService;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/3/11 11:24
 */
public class AuditUserListController extends AbstractApiController<UserListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UserListForm command) throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);
        UserSearchService userSearchService = BeanFactory.getBean(UserSearchService.class);

        UserSearchQuery query = new UserSearchQuery();
        query.setAuditStatus(command.getAuditStatusList());
        query.setFrom(command.getOffset());
        query.setSize(command.getLimit());
        query.setRouteId(RequestContext.getRouteId());
        query.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));
        query.setRemark(command.getRemark());
        query.setKeyword(command.getName());
        SearchResult<UserSearchBO> result = userSearchService.query(query);

        List<Long> ids = CollectionUtil.getFieldValueList(result.getData(), "id");
        List<UserDO> userDOList = userService.getUserByUserIds(ids);
        Map<Long, UserDO> userMap = CollectionUtil.toMap(userDOList, "id");

        List<AuditUserListBO> boList = new ArrayList<>();

        for (UserSearchBO userSearchBO : result.getData()) {
            UserDO userDO = userMap.get(userSearchBO.getId());
            AuditUserListBO bo = new AuditUserListBO();
            bo.setId(userDO.getId());
            bo.setName(userDO.getNickName());
            bo.setAccount(userDO.getAccount());
            bo.setRegisterTime(DateUtil.formatDate(userDO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            bo.setContact(userDO.getContacts());
            bo.setAccountType(AccountTypeEnum.parse(userDO.getCertificateType()).getRemark());
            bo.setEmail(userDO.getEmail());
            bo.setCertificatePhoto(userDO.getCertificatePhoto());
            bo.setBankAccountInfo(genUserBankInfo(userDO));
            bo.setAuditStatus(userDO.getAuditStatus());
            bo.setAuditStatusDesc(AuditStatusEnum.parse(userDO.getAuditStatus()).getRemark());
            if (StringUtils.isNotBlank(userDO.getInvitationCode())) {
                bo.setInvitationCode("系统（邀请码为：" + userDO.getInvitationCode() + "）");
            }
            boList.add(bo);
        }

        return Result.buildSuccessResult(new PageDataList<>(result.getTotalHits(), command.getPage(), command.getLimit(), boList));
    }

    private String genUserBankInfo(UserDO userDO) {
        StringBuilder bankInfo = new StringBuilder();

        if (StringUtils.isNotBlank(userDO.getBankName())) {
            bankInfo.append(userDO.getBankName()).append(",");
        }
        if (StringUtils.isNotBlank(userDO.getBankAccount())) {
            bankInfo.append(userDO.getBankAccount()).append(",");
        }
        if (StringUtils.isNotBlank(userDO.getAccountOpener())) {
            bankInfo.append(userDO.getAccountOpener());
        }

        return bankInfo.toString();
    }

}
