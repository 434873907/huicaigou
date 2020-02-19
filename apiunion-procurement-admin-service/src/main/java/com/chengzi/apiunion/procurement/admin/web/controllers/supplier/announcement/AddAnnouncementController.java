package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.announcement;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.util.Builder;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.announcement.AddAnnouncementForm;
import com.chengzi.apiunion.supplier.common.announcement.pojo.SupplierAnnouncementDO;
import com.chengzi.apiunion.supplier.common.announcement.service.SupplierAnnouncementService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2020/01/16 15:51
 */
public class AddAnnouncementController extends AbstractApiController<AddAnnouncementForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddAnnouncementForm command) throws Exception {
        SupplierAnnouncementService supplierAnnouncementService = BeanFactory.getBean(SupplierAnnouncementService.class);

        SupplierAnnouncementDO announcementDO = Builder.of(SupplierAnnouncementDO::new)
                .with(SupplierAnnouncementDO::setTitle, command.getTitle())
                .with(SupplierAnnouncementDO::setContent, command.getContent())
                .with(SupplierAnnouncementDO::setCreatorId, RequestContext.getUserId())
                .with(SupplierAnnouncementDO::setCreator, RequestContext.getUserName())
                .build();

        Result<Long> result = supplierAnnouncementService.add(announcementDO);

        if (result.isSuccess()) {
            return Result.buildSuccessResult("添加成功");
        } else {
            return result;
        }
    }
}
