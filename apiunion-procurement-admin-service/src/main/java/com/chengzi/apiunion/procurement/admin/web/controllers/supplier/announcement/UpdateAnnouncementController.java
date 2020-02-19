package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.announcement;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.util.Builder;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.announcement.UpdateAnnouncementForm;
import com.chengzi.apiunion.supplier.common.announcement.pojo.SupplierAnnouncementDO;
import com.chengzi.apiunion.supplier.common.announcement.service.SupplierAnnouncementService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2020/01/16 16:14
 */
public class UpdateAnnouncementController extends AbstractApiController<UpdateAnnouncementForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateAnnouncementForm command) throws Exception {
        SupplierAnnouncementService supplierAnnouncementService = BeanFactory.getBean(SupplierAnnouncementService.class);

        SupplierAnnouncementDO announcementDO = Builder.of(SupplierAnnouncementDO::new)
                .with(SupplierAnnouncementDO::setId, command.getId())
                .with(SupplierAnnouncementDO::setContent, command.getContent())
                .with(SupplierAnnouncementDO::setCreatorId, RequestContext.getUserId())
                .with(SupplierAnnouncementDO::setCreator, RequestContext.getUserName())
                .build();

        Result<Void> result = supplierAnnouncementService.update(announcementDO);

        if (result.isSuccess()) {
            return Result.buildSuccessResult("更新成功");
        } else {
            return result;
        }
    }
}
