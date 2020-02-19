package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.constant.OrderExpressConstant;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.GetOrderPressByPkgNoForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.express.GetOrderExpressByPkgNoBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据包裹号获取订单物流详情
 *
 * @author 月汐
 * @date 2018/10/30 15:21
 */
public class GetOrderExpressByPkgNoController extends AbstractApiController<GetOrderPressByPkgNoForm> {

    @Override
    protected Result<List<GetOrderExpressByPkgNoBO>> doBiz(HttpServletRequest request, HttpServletResponse response, GetOrderPressByPkgNoForm command) throws Exception {
        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
        OrderExpressDO orderExpressDO = new OrderExpressDO();
        orderExpressDO.setUserId(command.getUserId());
        orderExpressDO.setPkgNo(command.getPkgNo());
        List<OrderExpressDO> expressList = service.getByPkgNo(orderExpressDO);
        OrderExpressDO firstExpress = expressList.get(expressList.size() - 1);
        if (firstExpress.getType() != OrderExpressConstant.TYPE_FIRST) {
            orderExpressDO = new OrderExpressDO();
            orderExpressDO.setUserId(command.getUserId());
            orderExpressDO.setId(firstExpress.getParentId());
            OrderExpressDO expressDO = service.getById(orderExpressDO);
            expressList.add(expressDO);
        }

        List<GetOrderExpressByPkgNoBO> resultList = new ArrayList<>();
        for (OrderExpressDO expressDO : expressList) {
            resultList.add(GetOrderExpressByPkgNoBO.convert(expressDO));
        }

        return Result.buildSuccessResult(resultList);
    }

}
