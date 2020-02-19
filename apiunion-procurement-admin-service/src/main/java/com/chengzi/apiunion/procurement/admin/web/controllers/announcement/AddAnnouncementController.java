package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.module.announcement.pojo.AnnouncementDO;
import com.chengzi.apiunion.common.module.announcement.service.AnnouncementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.announcement.AddAnnouncementForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * 新增公告
 *
 * @author 月汐
 * @date 2018/10/22 19:43
 */
public class AddAnnouncementController extends AbstractApiController<AddAnnouncementForm> {
    public static final Pattern URL_PATTERN = Pattern.compile("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+");

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, AddAnnouncementForm command) throws Exception {
        AnnouncementService service = BeanFactory.getBean(AnnouncementService.class);
        
        Integer type = command.getType();
        String url = command.getUrl();
        if (type != null && type == JumpTypeEnum.PAGE_WEB.getCode() && !URL_PATTERN.matcher(url).find()) {
            return Result.buildIllegalArgumentResult("请输入正确的网页地址");
        }

        if (command.getIssueTime().after(command.getInvalidTime())) {
            return Result.buildIllegalArgumentResult("失效时间请设置在发布时间之后");
        }

        AnnouncementDO announcementDO = new AnnouncementDO();
        announcementDO.setTitle(command.getTitle());
        announcementDO.setContent(command.getContent());
        announcementDO.setType(type);
        announcementDO.setUrl(url);
        announcementDO.setIssueTime(command.getIssueTime());
        announcementDO.setInvalidTime(command.getInvalidTime());
        announcementDO.setStatus(command.getStatus());

        return service.add(announcementDO);
    }
}
