package com.chengzi.apiunion.procurement.admin.web.controllers.priceapprove;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ThirdItemSkuAttr;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.formbean.priceapprove.QueryPriceApproveListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.priceapprove.PriceApproveBO;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierItemSearchBO;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierItemSkuSearchBO;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemImageService;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemSearchService;
import com.chengzi.apiunion.supplier.common.item.util.SupplierItemSkuUtil;
import com.chengzi.apiunion.supplier.common.priceapprove.enums.SkuPriceApproveStatusEnum;
import com.chengzi.apiunion.supplier.common.priceapprove.pojo.SkuPriceApproveDO;
import com.chengzi.apiunion.supplier.common.priceapprove.pojo.search.SkuPriceApproveSearchBO;
import com.chengzi.apiunion.supplier.common.priceapprove.query.SkuPriceApproveSearchQuery;
import com.chengzi.apiunion.supplier.common.priceapprove.service.SkuPriceApproveSearchService;
import com.chengzi.apiunion.supplier.common.priceapprove.service.SkuPriceApproveService;
import com.chengzi.apiunion.supplier.common.priceapprove.util.SupplierPriceApproveShardUtil;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.support.Tuple2;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class QueryPriceApproveListController extends AbstractApiController<QueryPriceApproveListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryPriceApproveListForm command) throws Exception {
        SkuPriceApproveService skuPriceApproveService = BeanFactory.getBean(SkuPriceApproveService.class);
        SupplierItemSearchService supplierItemSearchService = BeanFactory.getBean(SupplierItemSearchService.class);
        SkuPriceApproveSearchService priceApproveSearchService = BeanFactory.getBean(SkuPriceApproveSearchService.class);
        ItemChannelTypeService channelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        SupplierItemImageService itemImageService = BeanFactory.getBean(SupplierItemImageService.class);
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        BigDecimal minProfitMargin = new BigDecimal(configService.getConfigValue(ConfigKeyEnums.MIN_PROFIT_MARGIN));

        List<PriceApproveBO> resultList = new ArrayList<>();
        SkuPriceApproveSearchQuery approveSearchQuery = new SkuPriceApproveSearchQuery();
        approveSearchQuery.setStatus(command.getStatus());
        approveSearchQuery.setCreateTimeRange(new Range<>(command.getBegainTime(),command.getEndTime()));
        approveSearchQuery.setSupplierIds(command.getSupplierIds());
        approveSearchQuery.setCateId(command.getCateId());
        approveSearchQuery.setKeyword(command.getKeyword());
        approveSearchQuery.setApproveChannelTypes(CollectionUtil.asArrayList(command.getChannelType()));
        if (command.getChannelType() > 0) {
            approveSearchQuery.setChannelTypes(CollectionUtil.asHashSet(command.getChannelType()));
        }

        SearchResult<SkuPriceApproveSearchBO> searchResult = priceApproveSearchService.query(approveSearchQuery);
        if (searchResult.isEmpty()) {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), resultList));
        }

        Map<Long, List<Long>> supplierIdApproveIdListMap = new HashMap<>();
        searchResult.getData().forEach(bo -> supplierIdApproveIdListMap.computeIfAbsent(SupplierPriceApproveShardUtil.getShardId(bo.getApproveSupplierId()),
                k -> new ArrayList<>()).add(bo.getApproveId()));
        List<SkuPriceApproveDO> approveDOList = skuPriceApproveService.queryByIds(supplierIdApproveIdListMap);


        List<Long> supplierItemIds = CollectionUtil.getFieldValueList(approveDOList, "supplierItemId");
        SupplierItemSearchQuery itemQuery = new SupplierItemSearchQuery();
        itemQuery.setItemIds(supplierItemIds);
        SearchResult<SupplierItemSearchBO> supplierItemSearchResult = supplierItemSearchService.queryItem(itemQuery);

        HashMap<Long, Tuple2<String, String>> skuIdTupleMap = new HashMap<>();
        supplierItemSearchResult.getData().forEach(supplierItemSearchBO -> {
            List<SupplierItemSkuSearchBO> skuList = supplierItemSearchBO.getSkuList();
            if (CollectionUtil.isNotEmpty(skuList)) {
                skuList.forEach(sku -> {
                    Tuple2<String, String> tuple2 = new Tuple2<>(supplierItemSearchBO.getName(), SupplierItemSkuUtil.buildSkuDes(sku));
                    skuIdTupleMap.put(sku.getId(), tuple2);
                });
            }
        });

        List<PriceApproveBO> boList = new ArrayList<>();
        approveDOList.forEach(approveDO -> boList.add(PriceApproveBO.convert(approveDO)));
        if (command.getStatus() == SkuPriceApproveStatusEnum.WAIT_AUDIT.getCode()) {
            //待审核列表需要设置利润率
            List<String> thirdSkuIds = new ArrayList<>();
            approveDOList.forEach(approveDO -> thirdSkuIds.add(String.valueOf(approveDO.getSupplierSkuId())));
            List<Integer> apiTypes = CollectionUtil.asArrayList(ThirdSyncItemTypeEnum.SUPER_EXP.getCode(), ThirdSyncItemTypeEnum.SUPER_EXP_STORAGE.getCode());
            SearchResult<Long> itemSearchResult = itemSearchService.queryByThirdSkuIds(apiTypes, thirdSkuIds);
            Map<Long, ItemSkuInfo.SkuChannelInfo> supplierSkuIdSkuChannelMap = new HashMap<>();
            Map<Long, Long> supplierSkuIdItemIdMap = new HashMap<>();

            List<ItemSkuInfo> skuInfoList = itemSkuService.getSkuInfosByItemIds(itemSearchResult.getData());
            skuInfoList.forEach(skuInfo -> {
                List<ItemSkuInfo.SkuChannelInfo> skuChannels = skuInfo.getSkuChannels();
                if (CollectionUtil.isNotEmpty(skuChannels)) {
                    skuChannels.forEach(skuChannel -> {
                        List<ItemSkuInfo.SupplierStock> supplierStocks = skuChannel.getSupplierStocks();
                        if (CollectionUtil.isNotEmpty(supplierStocks)) {
                            supplierStocks.forEach(supplierStock -> {
                                ThirdItemSkuAttr thirdAttr = supplierStock.getThirdAttr();
                                supplierSkuIdSkuChannelMap.put(Long.parseLong(thirdAttr.getThirdSkuId()), skuChannel);
                                supplierSkuIdItemIdMap.put(Long.parseLong(thirdAttr.getThirdSkuId()), skuInfo.getItemId());
                            });
                        }
                    });
                }
            });

            //设置利润率、售价等
            boList.forEach(bo -> {
                ItemSkuInfo.SkuChannelInfo channelInfo = supplierSkuIdSkuChannelMap.get(bo.getSupplierSkuId());
                if (channelInfo != null) {
                    BigDecimal profitRate = channelInfo.getEnterprisePrice().subtract(bo.getPriceApply())
                            .divide(bo.getPriceApply(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                    if (profitRate.compareTo(minProfitMargin) < 0) {
                        bo.setNeedRemind(BooleanStatusEnum.YES.getCode());
                    }
                    bo.setProfitRate(String.format("%f%%", profitRate));
                    bo.setEnterprisePrice(channelInfo.getEnterprisePrice());
                    bo.setSalePrice(channelInfo.getPrice());
                }
                bo.setItemId(supplierSkuIdItemIdMap.getOrDefault(bo.getSupplierSkuId(), 0L));
            });
        }

        Map<Long, String> itemIdimageMap = itemImageService.getItemMainImageByItemIds(supplierItemIds);
        boList.forEach(bo -> {
                Tuple2<String, String> tuple2 = skuIdTupleMap.get(bo.getSupplierSkuId());
                bo.setItemName(tuple2.getV1());
                bo.setSkuDesc(tuple2.getV2());
                bo.setChannelName(channelTypeService.getChannelNameInCache(bo.getChannelType()));
                bo.setMainImageUrl(itemIdimageMap.get(bo.getSupplierItemId()));
        });

        return Result.buildSuccessResult(new PageDataList<>(searchResult.getTotalHits(), command.getPage(), command.getLimit(), resultList));
    }

}
