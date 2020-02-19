package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.AddTemplateItemForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.DeleteTemplateItemForm;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDetailDO;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateItemService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhiyuan
 * 删除模板商品
 */
public class DeleteTemplateItemController extends AbstractApiController<DeleteTemplateItemForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteTemplateItemForm command) throws Exception {

        SupplierTemplateItemService templateItemService = BeanFactory.getBean(SupplierTemplateItemService.class);

        long templateItemId = command.getTemplateItemId();
        Result<String> result = templateItemService.deleteTemplateItem(templateItemId);
        return result;
    }
}
