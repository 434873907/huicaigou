package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.account.enums.AccountFlowStatusEnum;
import com.chengzi.apiunion.account.enums.AccountFlowTypeEnum;
import com.chengzi.apiunion.account.pojo.UserAccountDO;
import com.chengzi.apiunion.account.pojo.UserAccountFlowDO;
import com.chengzi.apiunion.account.service.UserAccountService;
import com.chengzi.apiunion.account.util.UserAccountUtil;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.module.sequence.service.SequenceService;
import com.chengzi.apiunion.common.pay.PayBizType;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderPayInfoDO;
import com.chengzi.apiunion.order.service.OrderPayInfoService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.CompleteOrderRunForm;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.OrderRefundRunForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.utils.simpleimage.analyze.RefFloat;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

import static com.chengzi.apiunion.common.module.sequence.enums.SequenceKeyEnum.USER_ACCOUNT_FLOW_ID;

/**
 * @author zhiyuan
 * 订单退款：给一个订单退款，并增加退款流水（仅限账户支付）
 */
public class OrderRefundRunController extends AbstractManageController<OrderRefundRunForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, OrderRefundRunForm command) throws Exception {

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        OrderPayInfoService orderPayInfoService = BeanFactory.getBean(OrderPayInfoService.class);
        UserAccountService userAccountService = BeanFactory.getBean(UserAccountService.class);
        SequenceService sequenceService = BeanFactory.getBean(SequenceService.class);

        LOGGER.error("param:" + command.toJsonString());
        String orderNum = command.getOrderNum();
        long userId = command.getUserId();
//        orderService.queryByOrderNum(userId, orderNum);
        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
        if(orderDO == null) {
            return Result.buildOpFailedResult("订单不存在");
        }
        BigDecimal refundAmount = command.getRefundAmount();
        if(refundAmount == null || BigDecimal.ZERO.compareTo(refundAmount) > 0) {
            return Result.buildOpFailedResult("金额错误");
        }
        LOGGER.error("orderDO:" + orderDO.toJsonString());

        List<OrderPayInfoDO> orderPayInfos = orderPayInfoService.queryPayInfoByOrderNumAndType(userId, orderNum, PayBizType.ORDER.getCode());

        if (CollectionUtil.isEmpty(orderPayInfos)) {
            return Result.buildOpFailedResult("该笔订单未完成支付，无法退款");
        }
        LOGGER.error("orderPayInfos size:" + orderPayInfos.size() );
        OrderPayInfoDO orderPayInfo = orderPayInfos.get(0);

        UserAccountDO userAccount = userAccountService.getUserAccount(userId);
        if(userAccount == null) {
            return Result.buildOpFailedResult("用户账户不存在");
        }
        userAccountService.updateUserAccountBalance(userId, AccountFlowTypeEnum.ACCOUNT_FLOW_TYPE_REFUND.getCode(),
                refundAmount.setScale(2, BigDecimal.ROUND_HALF_UP));

        long orderAccountFlowId = sequenceService.getSequence(USER_ACCOUNT_FLOW_ID);
        String accountFlowNum = UserAccountUtil.buildUserAccountFlowNum(orderAccountFlowId, userId);

        UserAccountFlowDO userAccountFlowDO = new UserAccountFlowDO();
        userAccountFlowDO.setId(orderAccountFlowId);
        userAccountFlowDO.setFlowNum(accountFlowNum);
        userAccountFlowDO.setFlowAmount(refundAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        userAccountFlowDO.setUserId(userId);
        userAccountFlowDO.setFlowStatus(AccountFlowStatusEnum.ACCOUNT_FLOW_STATUS_PAID.getCode());
        userAccountFlowDO.setFlowType(AccountFlowTypeEnum.ACCOUNT_FLOW_TYPE_REFUND.getCode());
        userAccountFlowDO.setOrderNum(orderNum);
        userAccountFlowDO.setBalance(userAccount.getBalance().add(refundAmount));
//        userAccountFlowDO.setFlowDesc(accountFlowDescBuilder.toString());

        //插入流水
        userAccountService.addUserAccountFlow(userAccountFlowDO);













        return Result.buildSuccessMsg("订单处理完成");
    }
}
