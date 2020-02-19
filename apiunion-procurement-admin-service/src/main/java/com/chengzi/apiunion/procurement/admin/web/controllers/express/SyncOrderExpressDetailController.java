package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.module.config.pojo.ExpressConfigDO;
import com.chengzi.apiunion.common.module.config.service.ExpressConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.service.OrderItemService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.SyncOrderExpressForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.UpdateOrderExpressForm;
//import com.chengzi.apiunion.procurement.admin.web.formbean.order.SyncOrderExpressForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.yuncang.common.util.StringUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 同步订单的物流信息：同步抓取物流
 */
public class SyncOrderExpressDetailController extends AbstractApiController<SyncOrderExpressForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SyncOrderExpressForm command) throws Exception {

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        OrderItemService orderItemService = BeanFactory.getBean(OrderItemService.class);
        OrderExpressService orderExpressService = BeanFactory.getBean(OrderExpressService.class);

        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        ExpressConfigService expressConfigService = BeanFactory.getBean(ExpressConfigService.class);

        List<ExpressCompanyDO> expressCompanyList = expressCompanyService.queryExpressCompanyList();
        Map<Long, ExpressCompanyDO> expressCompanyMap = CollectionUtil.toMap(expressCompanyList, "id");

        List<ExpressConfigDO> expressConfigList = expressConfigService.selectAll();
        Map<Long, ExpressConfigDO> expressConfigMap = CollectionUtil.toMap(expressConfigList, "id");


        String orderNum = command.getOrderNum();
        long userId = command.getUserId();

       List<OrderItemDO> orderItemDOS = orderService.queryByOrderNum(userId, orderNum);

      List<String> pkgNos = CollectionUtil.getFieldValueList(orderItemDOS, "pkgNo");


        Map<String, List<OrderExpressDO>> orderExpressMap = orderExpressService.getByPkgNos( pkgNos, userId );

        for (List<OrderExpressDO> orderExpressList : orderExpressMap.values()) {
            for (OrderExpressDO orderExpress : orderExpressList) {
                long expressCompanyId = orderExpress.getExpressCompany();

                ExpressCompanyDO expressCompany = expressCompanyMap.get(expressCompanyId);
                if (expressCompany == null) {
                    LOGGER.error("expressCompany is null !! id：" + expressCompanyId);
                    continue;
                }
                ExpressConfigDO expressConfig = expressConfigMap.get(expressCompany.getExpressConfigId());
                if (expressConfig == null) {
                    LOGGER.error("expressConfig is null !! id：" + expressCompany.getExpressConfigId());
                    continue;
                }

                String expressCompanyName = expressConfig.getCompanyName();
                String expressNum = orderExpress.getExpressNum();


                try {

//                    SpiderStrategyEnum spiderStrategyEnum = EnumUtil.parse(SpiderStrategyEnum.class, expressConfig.getSpiderStrategy());
//                    ExpressSpider expressSpider = spiderStrategyEnum.getSpiderClass().newInstance();
//                    Result<ExpressSpiderBO> res = expressSpider.expressSpider(orderExpress, expressConfig.getSpiderStrategyParam());
//                    if (!res.isSuccess()) {
//                        LOGGER.error(
//                                StringUtil.buildStatLog("expressSpider is failed", res, expressCompanyName, expressCompanyId, expressNum));
//                        continue;
//                    }
//                    LOGGER.warn(
//                            StringUtil.buildStatLog("expressSpider ok", res, expressCompanyName, expressCompanyId, expressNum));
//                    ExpressSpiderBO expressSpiderBO = res.getData();




                } catch (Exception e) {
                    LOGGER.error("error", e);
                }


            }
        }




//        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
//        OrderExpressDO orderExpressDO = new OrderExpressDO();
//        orderExpressDO.setId(command.getId());
//        orderExpressDO.setUserId(command.getUserId());
//        orderExpressDO.setExpressCompany(command.getExpressCompany());
//        orderExpressDO.setExpressNum(command.getExpressNum());
//        orderExpressDO.setTrackingNum(command.getTrackingNum());
//        orderExpressDO.setDelivered(0);
//        orderExpressDO.setDetail("");
//        return service.update(orderExpressDO);
        return null;
    }
}
