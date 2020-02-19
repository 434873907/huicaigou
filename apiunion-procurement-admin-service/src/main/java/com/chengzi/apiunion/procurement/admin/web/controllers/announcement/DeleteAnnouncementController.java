package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.module.announcement.service.AnnouncementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.announcement.DeleteAnnouncementForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除公告
 *
 * @author 月汐
 * @date 2018/10/22 19:51
 */
public class DeleteAnnouncementController extends AbstractApiController<DeleteAnnouncementForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteAnnouncementForm command) throws Exception {
        AnnouncementService service = BeanFactory.getBean(AnnouncementService.class);
        return service.delete(command.getId());
    }

}
