package com.chengzi.apiunion.procurement.admin.web.controllers.coupon;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.coupon.enums.CouponStatusEnum;
import com.chengzi.apiunion.coupon.pojo.CouponConfigDO;
import com.chengzi.apiunion.coupon.query.CouponConfigQuery;
import com.chengzi.apiunion.coupon.service.CouponConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.coupon.QueryCouponConfigLIstForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.coupon.CouponConfigListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.DateUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 13:57
 */
public class QueryCouponConfigListController extends AbstractApiController<QueryCouponConfigLIstForm> {

    @Override
    protected Result<PageDataList> doBiz(HttpServletRequest request, HttpServletResponse response, QueryCouponConfigLIstForm command)
            throws Exception {
        CouponConfigService couponConfigService = BeanFactory.getBean(CouponConfigService.class);

        CouponConfigQuery query = new CouponConfigQuery();
        query.setCouponName(command.getKeyWord());
        query.setCouponStatus(command.getCouponStatus());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        int total = couponConfigService.getCouponConfigCount(query);
        PageDataList pageDataList = new PageDataList();
        if (total > 0) {
            List<CouponConfigDO> couponConfigDOList = couponConfigService.queryCouponConfig(query);
            List<CouponConfigListBO> couponConfigListBOS = new ArrayList<>();
            couponConfigDOList.forEach(item -> {
                CouponConfigListBO couponConfigListBO = new CouponConfigListBO();
                couponConfigListBO.setId(item.getId());
                couponConfigListBO.setCouponName(item.getCouponName());
                couponConfigListBO.setCouponType(item.getCouponType() == 0 ? "优惠券" : "优惠码");
                couponConfigListBO.setCouponCount(item.getCouponCount());
                couponConfigListBO.setId(item.getId());
                couponConfigListBO.setSendCouponCount(item.getSendCouponCount());

                //配置优惠券的 状态
               int timeType = item.getTimeType();
               int couponStatus = item.getStatus();
               if(couponStatus == 0) {//优惠券未失效
                   if(timeType == 0) {//
                       couponConfigListBO.setCouponStatus(CouponStatusEnum.RUNNING.getStatus());
                   } else {
                       if(item.getStartTime() != null && item.getStartTime().getTime() > System.currentTimeMillis()) {
                           couponConfigListBO.setCouponStatus(CouponStatusEnum.INIT.getStatus());
                       } else if(item.getEndTime() != null && item.getEndTime().getTime() >= System.currentTimeMillis()) {
                           couponConfigListBO.setCouponStatus(CouponStatusEnum.RUNNING.getStatus());
                       } else if(item.getEndTime() != null && item.getEndTime().getTime() < System.currentTimeMillis()) {
                           couponConfigListBO.setCouponStatus(CouponStatusEnum.STOP.getStatus());
                       }
                   }
               } else if(couponStatus == 1) {//优惠券失效
                   couponConfigListBO.setCouponStatus(CouponStatusEnum.NOT_EFFECTIVE.getStatus());
               }
                //有效时间
               if(timeType == 0) {//领取后生效
                   couponConfigListBO.setValidateTime("领取后"+item.getAfterDays()+"天内有效");
               } else if(timeType == 1) {//截止时间内有效
                   String startTimeStr = DateUtil.formatDate(item.getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
                   String endTimeStr = DateUtil.formatDate(item.getEndTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
                   couponConfigListBO.setValidateTime(startTimeStr + "至" + endTimeStr);
               }

                couponConfigListBO.setCreateTime(item.getCreateTime());

                //todo louchongxiao 订单提供统计优惠券使用数量的方法
                couponConfigListBO.setUsedCouponCount(item.getUsedCouponCount());

                couponConfigListBOS.add(couponConfigListBO);
            });
            pageDataList.setDataList(couponConfigListBOS);
            pageDataList.setTotal(total);
            pageDataList.setTotalPage(getTotalPage(total, command.getLimit()));
        }
        return Result.buildSuccessResult(pageDataList);
    }
}
