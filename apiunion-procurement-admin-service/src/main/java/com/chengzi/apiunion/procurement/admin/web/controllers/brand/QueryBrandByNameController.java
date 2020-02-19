package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.brand.pojo.search.query.BrandSearchQuery;
import com.chengzi.apiunion.brand.search.BrandSearchService;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.QueryBrandByNameForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.brand.QueryBrandByNameBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 品牌下拉列表查询
 *
 * @author 月汐
 * @date 2018/11/7 10:39
 */
public class QueryBrandByNameController extends AbstractApiController<QueryBrandByNameForm> {
    @Override
    protected Result<List<QueryBrandByNameBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryBrandByNameForm command) throws Exception {
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        BrandSearchService brandSearchService = BeanFactory.getBean(BrandSearchService.class);

        BrandSearchQuery query = new BrandSearchQuery();
        query.setSize(9999);
        query.setName(command.getName());
        query.setLetter(command.getLetter());
        query.setStatus(command.getStatus());

        SearchResult<BrandSearchBO> searchResult = brandSearchService.query(query);
        if (searchResult.isEmpty()) {
            return Result.buildSuccessResult(new ArrayList<>());
        }

        List<QueryBrandByNameBO> resultList = new ArrayList<>();
        List<BrandDO> brandList = brandService.getByIds(CollectionUtil.getFieldValueList(searchResult.getData(), "id"));

        for (BrandDO brand : brandList) {
            resultList.add(QueryBrandByNameBO.convert(brand));
        }
        return Result.buildSuccessResult(resultList);
    }
}
