package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.QueryTemplateItemListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateItemListBO;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierTemplateItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierTemplateItemSearchBO;
import com.chengzi.apiunion.supplier.common.item.service.SupplierTemplateItemSearchService;
import com.chengzi.apiunion.supplier.common.template.pojo.*;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateCategoryService;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateItemImageService;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateItemService;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateItemSkuService;
import com.chengzi.apiunion.supplier.common.template.util.TemplateItemHelper;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 模板商品查询
 */
public class QueryTemplateItemListController extends AbstractApiController<QueryTemplateItemListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryTemplateItemListForm command) throws Exception {

        LOGGER.error("param:" + JSON.toJSONString(command));
        SupplierTemplateItemService supplierTemplateItemService = BeanFactory.getBean(SupplierTemplateItemService.class);
        SupplierTemplateItemSearchService templateItemSearchService = BeanFactory.getBean(SupplierTemplateItemSearchService.class);
        SupplierTemplateItemSearchQuery searchQuery = buildSearchQuery(command);

        SearchResult<SupplierTemplateItemSearchBO> searchResult = templateItemSearchService.queryItem(searchQuery);
        long total = searchResult.getTotalHits();
        if (total > 0) {
            List<Long> templateItemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");

            List<SupplierTemplateItemDO> templateItemDOS = supplierTemplateItemService.getSupplierTemplateItemByIds(templateItemIds);
            Map<Long, SupplierTemplateItemSearchBO> itemSearchBOMap = CollectionUtil.toMap(searchResult.getData(), "id");

            List<TemplateItemListBO> templateItemListBOS =  buildSupplierTemplateItemListBOS(templateItemDOS, itemSearchBOMap);
            return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), templateItemListBOS));
        }

        return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));

    }

    private SupplierTemplateItemSearchQuery buildSearchQuery(QueryTemplateItemListForm form) {
        SupplierTemplateItemSearchQuery searchQuery = new SupplierTemplateItemSearchQuery();

        searchQuery.setKeyword(form.getKeyword());
        searchQuery.setStatus(form.getStatus());

        searchQuery.setCateIds(CollectionUtil.asHashSet(form.getCateId()));
        searchQuery.setFrom(form.getOffset());
        searchQuery.setSize(form.getLimit());

        searchQuery.setDateRange(new Range(form.getModifyTimeBegin(), form.getModifyTimeEnd()));
        return searchQuery;
    }

    private List<TemplateItemListBO> buildSupplierTemplateItemListBOS(List<SupplierTemplateItemDO> itemList,
                                                                      Map<Long, SupplierTemplateItemSearchBO> itemSearchBOMap) {
//        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        SupplierTemplateItemImageService templateItemImageService = BeanFactory.getBean(SupplierTemplateItemImageService.class);
        SupplierTemplateItemSkuService itemSkuService = BeanFactory.getBean(SupplierTemplateItemSkuService.class);
        SupplierTemplateCategoryService categoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);

        List<Long> itemIds = CollectionUtil.getFieldValueList(itemList, "id");
        // 图片
        Map<Long, List<SupplierTemplateItemImageDO>> itemImageMap = templateItemImageService.getImagesByTemplateItemIds(itemIds);

        Map<Long, List<SupplierTemplateItemSkuDO>> itemIdSkuMap = itemSkuService.getSupplierTemplateItemSkuByIds(itemIds);
//        Map<Long, List<ItemSkuInfo>> skuInfosMap = CollectionUtil.toListMap(itemSkuService.getSkuInfosByItemIds(itemIds), "itemId");

        List<TemplateItemListBO> supplierItemListBOS = new ArrayList<>();
        for (SupplierTemplateItemDO templateItemDO : itemList) {
            Long templateItemId = templateItemDO.getId();
//            Long supplierId = supplierItemDO.getSupplierId();
            TemplateItemListBO bo = new TemplateItemListBO();
            bo.setId(templateItemDO.getId());
            // 品牌
            if (templateItemDO.getBrandId() > 0) {
                bo.setBrandId(templateItemDO.getBrandId());
                bo.setBrandName(templateItemDO.getBrandName());
//                BrandDO brandDO = brandMap.get(itemRichDO.getBrandId());
//                if (brandDO != null) {
//                    bo.setBrandName(brandDO.getBrandNameOrOriginalName());
//                }
            }
            // 主图
            String mainImageUrl = TemplateItemHelper.getMainImageUrl(itemImageMap.get(templateItemId));
            bo.setMainImageUrl(mainImageUrl);

            bo.setCateId(templateItemDO.getCateId());
          SupplierTemplateCategoryDO templateCategoryDO =  categoryService.getSupplierTemplateById(templateItemDO.getCateId());
          if(templateCategoryDO != null) {
              bo.setCateName(templateCategoryDO.getCateName());
          }
            // 规格
//            List<ItemSkuInfo> skuInfoList = skuInfosMap.get(supplierItemDO.getId());

            // 规格名称及可选值
//            StringBuilder builder = new StringBuilder();
//            HashSet<String> skuList = new HashSet<>();
//            List<ItemSkuNameDO> itemSkuNameDOList = itemSkuNameMapper.selectByItemId(SimpleRouteOperate.of(supplierItemDO.getId()));

            List<SupplierTemplateItemSkuDO> itemSkuDOS = itemIdSkuMap.get(templateItemId);
            String itemSkuListStr = TemplateItemHelper.buildTemplateItemSkuStr(templateItemId, itemSkuDOS);

            bo.setSkuListStr(itemSkuListStr);
            bo.setModifyTime(templateItemDO.getModifyTime());
            bo.setName(templateItemDO.getName());
            bo.setStatus(templateItemDO.getStatus());
//            SupplierTemplateItemSearchBO templateItemSearchBO = itemSearchBOMap.get(templateItemId);
//            bo.setStockStatus(supplierItemSearchBO.getStockStatus());
            bo.setSupplierNum(0);
//            String priceStr = AmountUtil.getAdminDisplayPriceStartWithSymbol(templateItemDO.getCurrency(), new BigDecimal(10));
            String priceStr = TemplateItemHelper.buildTemplateItemSkuPrices(templateItemDO, itemSkuDOS);
            bo.setPrice(priceStr);

            supplierItemListBOS.add(bo);
        }

        return supplierItemListBOS;
    }

}
