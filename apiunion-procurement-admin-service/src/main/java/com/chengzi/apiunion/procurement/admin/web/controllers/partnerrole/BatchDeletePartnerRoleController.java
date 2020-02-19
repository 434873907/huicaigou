package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole.BatchDeletePartnerRoleForm;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 批量删除角色
 *
 * @author 月汐
 * @date 2018/11/5 17:00
 */
public class BatchDeletePartnerRoleController extends AbstractApiController<BatchDeletePartnerRoleForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, BatchDeletePartnerRoleForm command) throws Exception {
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        return service.deleteBatch(command.getIds());
    }
}
