/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item.third;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.third.ThirdSyncTypeBO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

/**
 * 第三方商品同步方式列表
 * 
 * @author Kolor
 */
public class ThirdSyncTypeListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);

        List<ThirdSyncTypeBO> list = new ArrayList<>();
        List<ThirdSyncItemTypeEnum> typeArr = supplierService.getThirdSyncItemTypeFromCache();
        for (ThirdSyncItemTypeEnum thirdSyncItemType : typeArr) {
            ThirdSyncTypeBO syncTypeBO = new ThirdSyncTypeBO();
            syncTypeBO.setType(thirdSyncItemType.getCode());
            syncTypeBO.setName(thirdSyncItemType.getName());

            list.add(syncTypeBO);
        }
        return Result.buildSuccessResult(list);
    }

}
