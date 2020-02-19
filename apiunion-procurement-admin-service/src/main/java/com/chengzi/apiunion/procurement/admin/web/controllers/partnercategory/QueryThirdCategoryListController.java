package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.pojo.ThirdCategoryQuery;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.QueryThirdCategoryListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory.QueryCategoryListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取三级类目列表
 *
 * @author 月汐
 * @date 2018/10/31 16:03
 */
public class QueryThirdCategoryListController extends AbstractApiController<QueryThirdCategoryListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryThirdCategoryListForm command) throws Exception {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        ThirdCategoryQuery query = new ThirdCategoryQuery();
        query.setFirstCategoryId(command.getFirstCategoryId());
        query.setSecondCategoryId(command.getSecondCategoryId());
        query.setName(command.getName());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        List<PartnerCategoryDO> partnerCategoryDOList = service.queryThirdCategory(query);
        List<QueryCategoryListBO> resultList = new ArrayList<>();
        for (PartnerCategoryDO partnerCategoryDO : partnerCategoryDOList) {
            PartnerCategoryDO parent = service.getById(partnerCategoryDO.getParentId());
            String hierarchy = service.queryHierarchy(parent);
            resultList.add(QueryCategoryListBO.convert(partnerCategoryDO, null, parent, hierarchy));
        }
        long total = service.countThirdCategory(query);
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }
}
