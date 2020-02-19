package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.announcement;

import com.chengzi.apiunion.common.util.Builder;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.announcement.SupplierAnnouncementBO;
import com.chengzi.apiunion.supplier.common.announcement.pojo.SupplierAnnouncementDO;
import com.chengzi.apiunion.supplier.common.announcement.service.SupplierAnnouncementService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2020/01/16 16:39
 */
public class GetAnnouncementController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        SupplierAnnouncementService supplierAnnouncementService = BeanFactory.getBean(SupplierAnnouncementService.class);

        SupplierAnnouncementDO announcementDO = supplierAnnouncementService.getAnnouncement(command.getId());

        if (announcementDO != null) {
            SupplierAnnouncementBO bo = Builder.of(SupplierAnnouncementBO::new)
                    .with(SupplierAnnouncementBO::setId, announcementDO.getId())
                    .with(SupplierAnnouncementBO::setTitle, announcementDO.getTitle())
                    .with(SupplierAnnouncementBO::setContent, announcementDO.getContent())
                    .build();
            return Result.buildSuccessResult(bo);
        } else {
            return Result.buildIllegalArgumentResult("该通知不存在");
        }
    }
}
