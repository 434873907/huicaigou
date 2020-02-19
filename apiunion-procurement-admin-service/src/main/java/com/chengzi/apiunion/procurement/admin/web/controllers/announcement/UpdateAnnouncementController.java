package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.module.announcement.pojo.AnnouncementDO;
import com.chengzi.apiunion.common.module.announcement.service.AnnouncementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.announcement.UpdateAnnouncementForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新公告详情
 *
 * @author 月汐
 * @date 2018/10/22 20:04
 */
public class UpdateAnnouncementController extends AbstractApiController<UpdateAnnouncementForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateAnnouncementForm command) throws Exception {
        AnnouncementService service = BeanFactory.getBean(AnnouncementService.class);

        AnnouncementDO announcementDO = new AnnouncementDO();
        announcementDO.setId(command.getId());
        announcementDO.setTitle(command.getTitle());
        announcementDO.setContent(command.getContent());
        announcementDO.setType(command.getType());
        announcementDO.setUrl(command.getUrl());
        announcementDO.setIssueTime(command.getIssueTime());
        announcementDO.setInvalidTime(command.getInvalidTime());
        announcementDO.setStatus(command.getStatus());

        service.update(announcementDO);
        return Result.buildSuccessResult("添加成功");
    }

}
