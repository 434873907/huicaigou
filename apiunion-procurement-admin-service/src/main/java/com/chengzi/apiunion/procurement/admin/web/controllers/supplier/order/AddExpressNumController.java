package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.AddExpressNumForm;
import com.chengzi.apiunion.supplier.common.order.pojo.SupplierOrderItemDO;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressService;
import com.chengzi.common.data.validate.Result;

/**
 * @author 随风
 * @create 2020-01-10 16:29
 **/
public class AddExpressNumController extends AbstractApiController<AddExpressNumForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddExpressNumForm command) throws Exception {
        SupplierOrderExpressService expressService = BeanFactory.getBean(SupplierOrderExpressService.class);
        
        List<SupplierOrderItemDO> itemList = command.getItemList().stream().map(itemInfo -> {
            SupplierOrderItemDO itemDO = new SupplierOrderItemDO();
            itemDO.setId(itemInfo.getId());
            itemDO.setBuyNum(itemInfo.getNum());
            return itemDO;
        }).collect(Collectors.toList());

        return Result.buildSuccessResult(expressService.addExpressNumByPkgNoAndItemList(command.getPkgNo(), command.getOrderNum(),
                                            command.getExpressNum(), command.getExpressCompanyId(), itemList));
    }
}
