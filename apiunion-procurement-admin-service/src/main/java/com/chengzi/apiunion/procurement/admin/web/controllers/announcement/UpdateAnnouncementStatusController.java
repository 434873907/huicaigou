package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.module.announcement.service.AnnouncementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.announcement.UpdateAnnouncementStatusForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新公告状态
 *
 * @author 月汐
 * @date 2018/11/14 14:08
 */
public class UpdateAnnouncementStatusController extends AbstractApiController<UpdateAnnouncementStatusForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateAnnouncementStatusForm command) throws Exception {
        AnnouncementService service = BeanFactory.getBean(AnnouncementService.class);
        return service.updateStatus(command.getId(), command.getStatus());
    }

}
