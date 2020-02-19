package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.brand.enums.BrandCountryEnum;
import com.chengzi.apiunion.brand.pojo.search.query.BrandSearchQuery;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.QueryBrandLibraryListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.QueryBrandLibraryBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateItemListBO;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierTemplateItemSearchBO;
import com.chengzi.apiunion.supplier.common.template.pojo.BrandLibraryQuery;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierBrandLibraryDO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDO;
import com.chengzi.apiunion.supplier.common.template.pojo.search.SupplierBrandSearchBO;
import com.chengzi.apiunion.supplier.common.template.pojo.search.query.SupplierBrandSearchQuery;
import com.chengzi.apiunion.supplier.common.template.service.SupplierBrandLibraryService;
import com.chengzi.apiunion.supplier.common.template.service.SupplierBrandSearchService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.jiayu.common.address.CountryEnum;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模板品牌查询
 */
public class QueryBrandLibraryListController extends AbstractApiController<QueryBrandLibraryListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryBrandLibraryListForm command) throws Exception {

        SupplierBrandLibraryService brandLibraryService = BeanFactory.getBean(SupplierBrandLibraryService.class);
        SupplierBrandSearchService supplierBrandSearchService = BeanFactory.getBean(SupplierBrandSearchService.class);

        SupplierBrandSearchQuery searchQuery = command.buildQuery();
        SearchResult<SupplierBrandSearchBO> searchResult = supplierBrandSearchService.query(searchQuery);
        long total = searchResult.getTotalHits();
        if (total > 0) {
            List<Long> brandIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");
            List<SupplierBrandLibraryDO> brandLibraryDOS = brandLibraryService.getBrandLibraryByIds(brandIds);
            if(CollectionUtil.isNotEmpty(brandLibraryDOS)) {
                List<QueryBrandLibraryBO> brandLibraryBOS = new ArrayList<>();
                for (SupplierBrandLibraryDO brandLibraryDO : brandLibraryDOS) {

                    QueryBrandLibraryBO brandLibraryBO = convert(brandLibraryDO);
                    brandLibraryBOS.add(brandLibraryBO);
                }
                return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), brandLibraryBOS));
            } else {
                return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
            }
        }
        return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), new ArrayList()));
    }

    private QueryBrandLibraryBO convert(SupplierBrandLibraryDO brandLibraryDO) {
        QueryBrandLibraryBO bo = new QueryBrandLibraryBO();

        bo.setId(brandLibraryDO.getId());
        bo.setNameZh(brandLibraryDO.getNameZh());
        bo.setNameEn(brandLibraryDO.getNameEn());
        bo.setCountryCode(brandLibraryDO.getCountryCode());

        //TODO 国名转换
        bo.setCountryName(BrandCountryEnum.getDescByCode(brandLibraryDO.getCountryCode()));
        if(StringUtils.isNotBlank(brandLibraryDO.getAlias())) {
            List<String> alias = JSON.parseArray(brandLibraryDO.getAlias(), String.class);
            bo.setAlias(alias);
        }
        return bo;
    }


}
