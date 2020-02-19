package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.dao.ThirdSyncItemAuthMapper;
import com.chengzi.apiunion.item.pojo.ThirdSyncItemAuthDO;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.SupplierListForAddItemForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierBO;
import com.chengzi.apiunion.supplier.pojo.SupplierChannelQuery;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/09/25 16:20
 */
public class SupplierListForAddItemController extends AbstractApiController<SupplierListForAddItemForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SupplierListForAddItemForm command) throws Exception {
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        ThirdSyncItemAuthMapper thirdSyncItemAuthMapper = BeanFactory.getBean(ThirdSyncItemAuthMapper.class);
        SupplierChannelQuery query = new SupplierChannelQuery();
        query.setChannelType(command.getChannelType());
        List<SupplierDO> supplierDOList = supplierService.getSupplierByChannelTypeAndName(query);

        List<SupplierBO> list = new ArrayList<>();
        for (SupplierDO supplierDO : supplierDOList) {
            if (supplierDO.getStatus() == 0) {
                continue;
            }
            List<ThirdSyncItemAuthDO> thirdSyncItemAuthList = thirdSyncItemAuthMapper.selectBySupplierId(SimpleRouteOperate.of(supplierDO.getId()));
            if (!thirdSyncItemAuthList.isEmpty()) {
                continue;
            }

            SupplierBO bo = new SupplierBO();
            list.add(bo);
            bo.setId(supplierDO.getId());
            bo.setSupplierName(supplierDO.getSupplierName());
        }
        return Result.buildSuccessResult(list);
    }
}
