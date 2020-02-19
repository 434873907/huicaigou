package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.announcement;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.supplier.common.announcement.pojo.SupplierAnnouncementDO;
import com.chengzi.apiunion.supplier.common.announcement.query.SupplierAnnouncementQuery;
import com.chengzi.apiunion.supplier.common.announcement.service.SupplierAnnouncementService;
import com.chengzi.apiunion.common.util.Builder;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.announcement.QueryAnnouncementListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.announcement.SupplierAnnouncementBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2020/01/16 14:02
 */
public class QueryAnnouncementListController extends AbstractApiController<QueryAnnouncementListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryAnnouncementListForm command) throws Exception {
        SupplierAnnouncementService supplierAnnouncementService = BeanFactory.getBean(SupplierAnnouncementService.class);

        SupplierAnnouncementQuery query = Builder.of(SupplierAnnouncementQuery::new)
                .with(SupplierAnnouncementQuery::setKeyword, command.getKeyword())
                .with(SupplierAnnouncementQuery::setDateRange, new Range<>(command.getStartTime(), command.getEndTime()))
                .build();

        List<SupplierAnnouncementDO> announcements = supplierAnnouncementService.getAnnouncements(query);
        long count = supplierAnnouncementService.countAnnouncements(query);

        List<SupplierAnnouncementBO> boList = new ArrayList<>();
        announcements.forEach(announcement ->
            boList.add(
                    Builder.of(SupplierAnnouncementBO::new)
                            .with(SupplierAnnouncementBO::setId, announcement.getId())
                            .with(SupplierAnnouncementBO::setTitle, announcement.getTitle())
                            .with(SupplierAnnouncementBO::setCreateTime, announcement.getCreateTime())
                            .with(SupplierAnnouncementBO::setCreator, announcement.getCreator())
                            .with(SupplierAnnouncementBO::setContent, announcement.getContent())
                            .build()
            )
        );

        return Result.buildSuccessResult(new PageDataList<>(count, command.getPage(), command.getLimit(), boList));
    }
}
