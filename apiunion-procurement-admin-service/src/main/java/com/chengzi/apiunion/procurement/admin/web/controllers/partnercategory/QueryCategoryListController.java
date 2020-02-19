package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryQuery;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.QueryCategoryListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory.QueryCategoryListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取类目列表
 *
 * @author 月汐
 * @date 2018/10/12 13:52
 */
public class QueryCategoryListController extends AbstractApiController<QueryCategoryListForm> {
    @Override
    protected Result<PageDataList<QueryCategoryListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryCategoryListForm command) throws Exception {

        List<QueryCategoryListBO> resultList = new ArrayList<>();

        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);

        PartnerCategoryQuery query = new PartnerCategoryQuery();
        query.setParentId(command.getParentId());
        query.setName(command.getName());
        query.setDepth(command.getDepth());
        query.setOffset(command.getOffset());
        query.setLimit(command.getLimit());

        List<PartnerCategoryDO> partnerCategoryDOList = service.queryCategories(query);
        for (PartnerCategoryDO partnerCategoryDO : partnerCategoryDOList) {
            List<PartnerCategoryDO> children = service.queryByParentId(partnerCategoryDO.getId());
            PartnerCategoryDO parent = null;
            String hierarchy = null;
            if (partnerCategoryDO.getDepth() != PartnerCategoryConstant.FIRST_CLASS_CATEGORY) {
                parent = service.getById(partnerCategoryDO.getParentId());
                hierarchy = service.queryHierarchy(parent);
            }
            resultList.add(QueryCategoryListBO.convert(partnerCategoryDO, children, parent, hierarchy));
        }
        long total = service.countCategories(query);
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }
}
