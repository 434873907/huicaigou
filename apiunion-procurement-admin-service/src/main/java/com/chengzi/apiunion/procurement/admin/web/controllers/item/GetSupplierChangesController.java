
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierChangeMapper;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierChangeDO;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.GetSupplierChangesForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取供应商变更记录
 * 
 * @author 行者
 *
 */
public class GetSupplierChangesController extends AbstractApiController<GetSupplierChangesForm> {

    @Override
    protected Result<List<ItemSkuInfo.SupplierChange>> doBiz(HttpServletRequest request, HttpServletResponse response, GetSupplierChangesForm command) throws Exception {
        ItemSkuSupplierChangeMapper itemSkuSupplierChangeMapper = BeanFactory.getBean(ItemSkuSupplierChangeMapper.class);
        List<ItemSkuSupplierChangeDO> changeDOList = itemSkuSupplierChangeMapper.selectBySkuSupplierId(SimpleRouteOperate.of(command.getSkuSupplierId()), command.getItemId(), 30);
        ArrayList<ItemSkuInfo.SupplierChange> supplierChanges = new ArrayList<>();
        for (ItemSkuSupplierChangeDO changeDO : changeDOList) {
            ItemSkuInfo.SupplierChange supplierChange = new ItemSkuInfo.SupplierChange();
            supplierChange.setType(changeDO.getType());
            supplierChange.setOriginalValue(changeDO.getOriginalValue());
            supplierChange.setValue(changeDO.getValue());
            supplierChange.setCreateTime(changeDO.getCreateTime());
            supplierChanges.add(supplierChange);
        }
        return Result.buildSuccessResult(supplierChanges);
    }
}
