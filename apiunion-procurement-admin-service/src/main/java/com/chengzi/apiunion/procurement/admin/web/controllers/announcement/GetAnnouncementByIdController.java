package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.module.announcement.pojo.AnnouncementDO;
import com.chengzi.apiunion.common.module.announcement.service.AnnouncementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.announcement.QueryAnnouncementByIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.announcement.QueryAnnouncementByIdBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取公告详情
 *
 * @author 月汐
 * @date 2018/10/22 20:51
 */
public class GetAnnouncementByIdController extends AbstractApiController<QueryAnnouncementByIdForm> {
    @Override
    protected Result<QueryAnnouncementByIdBO> doBiz(HttpServletRequest request, HttpServletResponse response, QueryAnnouncementByIdForm command) throws Exception {
        AnnouncementService service = BeanFactory.getBean(AnnouncementService.class);
        AnnouncementDO announcementDO = service.getById(command.getId());
        return Result.buildSuccessResult(QueryAnnouncementByIdBO.convert(announcementDO));
    }
}
