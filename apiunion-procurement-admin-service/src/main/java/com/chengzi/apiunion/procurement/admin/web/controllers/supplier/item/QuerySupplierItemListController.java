package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.item.QuerySupplierItemListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierItemListBO;
import com.chengzi.apiunion.supplier.common.item.pojo.SupplierItemDO;
import com.chengzi.apiunion.supplier.common.item.pojo.SupplierItemImageDO;
import com.chengzi.apiunion.supplier.common.item.pojo.SupplierItemSkuDO;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierItemSearchBO;
import com.chengzi.apiunion.supplier.common.item.service.*;
import com.chengzi.apiunion.supplier.common.item.util.SupplierItemHelper;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateCategoryService;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * A端查询各个供应商的商品
 */
public class QuerySupplierItemListController extends AbstractApiController<QuerySupplierItemListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QuerySupplierItemListForm command) throws Exception {

        SupplierItemService supplierItemService = BeanFactory.getBean(SupplierItemService.class);
        SupplierItemSearchService supplierItemSearchService = BeanFactory.getBean(SupplierItemSearchService.class);

        Result<SupplierItemSearchQuery> queryResult = command.buildQuery();

        if (!queryResult.isSuccess()) {
            return Result.buildIllegalArgumentResult(queryResult.getMessage());
        }

        try {
            SearchResult<SupplierItemSearchBO> searchResult = supplierItemSearchService.queryItem(queryResult.getData());
            long total = searchResult.getTotalHits();
            if (total > 0) {
                List<Long> supplierItemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");

                Map<Long, SupplierItemSearchBO> itemSearchBOMap = CollectionUtil.toMap(searchResult.getData(), "id");

                List<SupplierItemDO> supplierItemDOS = supplierItemService.getSupplierItemByIds(supplierItemIds);
                List<SupplierItemListBO> supplierItemListBOS = buildSupplierItemListBOS(supplierItemDOS, itemSearchBOMap);
                return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), supplierItemListBOS));
            }
        }catch (Exception e) {
            LOGGER.error("error", e);
            return Result.buildOpFailedResult("查询失败");
        }
        return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
    }


    private List<SupplierItemListBO> buildSupplierItemListBOS(List<SupplierItemDO> itemList,  Map<Long, SupplierItemSearchBO> itemSearchBOMap) {

        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        SupplierItemImageService supplierItemImageService = BeanFactory.getBean(SupplierItemImageService.class);
        SupplierItemSkuService itemSkuService = BeanFactory.getBean(SupplierItemSkuService.class);
        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);
        List<Long> itemIds = CollectionUtil.getFieldValueList(itemList, "id");
        // 图片
        Map<Long, List<SupplierItemImageDO>> itemImageMap = supplierItemImageService.getImagesByItemIds(itemIds);

        Map<Long, List<SupplierItemSkuDO>> itemSkuMap = itemSkuService.getSupplierItemSkuByIds(itemIds);
//        Map<Long, List<ItemSkuInfo>> skuInfosMap = CollectionUtil.toListMap(itemSkuService.getSkuInfosByItemIds(itemIds), "itemId");

        List<SupplierItemListBO> supplierItemListBOS = new ArrayList<>();
        for (SupplierItemDO supplierItemDO : itemList) {
            Long itemId = supplierItemDO.getId();
            Long supplierId = supplierItemDO.getSupplierId();
            SupplierItemListBO itemListBO = new SupplierItemListBO();
            itemListBO.setId(supplierItemDO.getId());
            itemListBO.setName(supplierItemDO.getName());
            // 品牌
            if (supplierItemDO.getBrandId() > 0) {
                itemListBO.setBrandId(supplierItemDO.getBrandId());
                itemListBO.setBrandName(supplierItemDO.getBrandName());
//                BrandDO brandDO = brandMap.get(itemRichDO.getBrandId());
//                if (brandDO != null) {
//                    bo.setBrandName(brandDO.getBrandNameOrOriginalName());
//                }
            }
            // 主图
            String mainImageUrl = SupplierItemHelper.getMainImageUrl(itemImageMap.get(supplierItemDO.getId()));
            itemListBO.setMainImageUrl(mainImageUrl);

            List<SupplierItemSkuDO> itemSkuDOS = itemSkuMap.get(itemId);
            String itemSkuListStr = SupplierItemHelper.buildItemSkuStr(itemId, itemSkuDOS);
            itemListBO.setSkuListStr(itemSkuListStr);
            itemListBO.setModifyTime(supplierItemDO.getModifyTime());

            itemListBO.setStatus(supplierItemDO.getStatus());
            SupplierItemSearchBO supplierItemSearchBO = itemSearchBOMap.get(itemId);
            itemListBO.setStockStatus(supplierItemSearchBO.getStockStatus());

            if (supplierItemDO.getCateId() > 0) {
                SupplierTemplateCategoryDO templateCategoryDO = templateCategoryService.getSupplierTemplateById(supplierItemDO.getCateId());
                if (templateCategoryDO != null) {
                    itemListBO.setCateId(supplierItemDO.getCateId());
                    itemListBO.setCateName(templateCategoryDO.getCateName());
                }
            }
            if(supplierItemDO.getSupplierTemplateItemId() > 0) {
                itemListBO.setType(1);
            } else {
                itemListBO.setType(2);
            }

            SupplierDO supplierDO = supplierService.getSupplier(supplierId);
            if (supplierDO != null) {
                itemListBO.setSupplierName(supplierDO.getSupplierName());
            }
            supplierItemListBOS.add(itemListBO);
        }

        return supplierItemListBOS;
    }

}
