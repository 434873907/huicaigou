package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.module.announcement.pojo.AnnouncementDO;
import com.chengzi.apiunion.common.module.announcement.pojo.AnnouncementQuery;
import com.chengzi.apiunion.common.module.announcement.service.AnnouncementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.announcement.QueryAnnouncementForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.announcement.QueryAnnouncementBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询公告列表
 *
 * @author 月汐
 * @date 2018/10/22 20:27
 */
public class QueryAnnouncementController extends AbstractApiController<QueryAnnouncementForm> {

    @Override
    protected Result<PageDataList<QueryAnnouncementBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryAnnouncementForm command) throws Exception {
        AnnouncementService service = BeanFactory.getBean(AnnouncementService.class);

        AnnouncementQuery query = new AnnouncementQuery();
        query.setKey(command.getKey());
        query.setBeginTime(command.getBeginTime());
        query.setEndTime(command.getEndTime());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        List<QueryAnnouncementBO> resultList = new ArrayList<>();
        List<AnnouncementDO> list = service.queryAnnouncements(query);
        for (AnnouncementDO announcementDO : list) {
            resultList.add(QueryAnnouncementBO.convert(announcementDO));
        }
        long total = service.countAnnouncements(query);
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }
}
