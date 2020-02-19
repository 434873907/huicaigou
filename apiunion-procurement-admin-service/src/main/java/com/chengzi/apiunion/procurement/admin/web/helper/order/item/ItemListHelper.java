package com.chengzi.apiunion.procurement.admin.web.helper.order.item;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.data.enums.StatusEnum;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.common.util.ExcelUtil;
import com.chengzi.apiunion.common.web.pojo.excel.ExcelData;
import com.chengzi.apiunion.item.dao.ItemSkuNameMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.apiunion.item.enums.ItemImageTypeEnum;
import com.chengzi.apiunion.item.enums.ItemRefStatusEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.*;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SkuChannelInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SupplierStock;
import com.chengzi.apiunion.item.pojo.search.ItemApproveSearchBO;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemApproveSearchQuery;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemApproveSearchService;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.ItemListForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UnapprovedItemSkuListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemListBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.support.Tuple2;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.BeanUtils;
import org.summercool.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author 月汐
 * @date 2019/07/17 20:42
 */
public class ItemListHelper {

    private static final String[] HEADER = {"序号", "商品ID", "商品名称", "规格名", "条形码", "品牌", "一级类目",
            "二级类目", "三级类目", "重量（克）", "商品编码", "供应商", "发货方式", "库存", "供货价", "企业价", "售价", "在售状态"};

    public static <T extends ItemListForm> Result<?> searchAndBuildItemList(T command) {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);

        if (StringUtils.isNotBlank(command.getKeyword()) && ItemListForm.ItemListKeywordTypeEnum.ITEM_ID == command.getKeywordType()) {
            // 单查
            Long itemId = LangUtil.parseLong(command.getKeyword());
            if (itemId == null || itemId.longValue() <= 0) {
                return Result.buildIllegalArgumentResult("商品ID格式不正确");
            }
            ItemRichDO itemRichDO = itemService.getItemRich(itemId.longValue());
            if (itemRichDO == null || itemRichDO.isDeleted() || !command.getStatusList().contains(itemRichDO.getStatus())) {
                return Result.buildSuccessResult(new PageDataList<>(0, 1, command.getLimit(), new ArrayList<>()));
            }

            return Result.buildSuccessResult(new PageDataList<>(1, 1, command.getLimit(), convert(CollectionUtil.asArrayList(itemRichDO))));
        } else if (CollectionUtil.isNotEmpty(command.getIds())) {
            List<ItemRichDO> itemRichDOS = itemService.getItemRichByIds(command.getIds());
            if (CollectionUtil.isEmpty(itemRichDOS)) {
                return Result.buildSuccessResult(new PageDataList<>(0, 1, command.getLimit(), new ArrayList<>()));
            }
            return Result.buildSuccessResult(new PageDataList<>(itemRichDOS.size(), 1, itemRichDOS.size(), convert(itemRichDOS)));
        } else {
            Result<ItemSearchQuery> result = command.buildQuery();
            if (!result.isSuccess()) {
                return Result.buildIllegalArgumentResult(result.getMessage());
            }

            SearchResult<ItemSearchBO> searchResult = itemSearchService.query(result.getData());
            if (searchResult.isEmpty()) {
                return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
            }
            List<Long> itemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");
            List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);
            return Result
                    .buildSuccessResult(new PageDataList<>(searchResult.getTotalHits(), command.getPage(), command.getLimit(), convert(itemList)));
        }
    }

    public static Result<ExcelData> searchAndBuildExcel(UnapprovedItemSkuListForm command) {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);

        if (StringUtils.isNotBlank(command.getKeyword()) && ItemListForm.ItemListKeywordTypeEnum.ITEM_ID == command.getKeywordType()) {
            // 单查
            Long itemId = LangUtil.parseLong(command.getKeyword());
            if (itemId == null || itemId <= 0) {
                return Result.buildIllegalArgumentResult("商品ID格式不正确");
            }
            return writeExcel(CollectionUtil.asArrayList(itemId), new ItemListForm());
        } else {
            Result<ItemSearchQuery> result = command.buildQuery();
            if (!result.isSuccess()) {
                return Result.buildIllegalArgumentResult(result.getMessage());
            }

            ItemSearchQuery query = result.getData();
            query.setSize(9999);

            if (CollectionUtil.isNotEmpty(query.getStatusList()) &&
                    query.getStatusList().size() == 1 &&
                    query.getStatusList().get(0) == ItemStatusEnum.INIT) {
                query.setStatus(ItemStatusEnum.INIT);
            }

            if (command.getApproveType() <= 0) {
                SearchResult<ItemSearchBO> searchResult = itemSearchService.query(query);
                if (searchResult.isEmpty()) {
                    return writeExcel(null, null);
                }

                List<Long> itemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");
                return writeExcel(itemIds, command);
            } else {
                ItemApproveSearchQuery searchQuery = new ItemApproveSearchQuery();
                BeanUtils.copyProperties(query, searchQuery);
                searchQuery.setIncludesFields(null);
                searchQuery.setApproveStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE));
                searchQuery.setApproveTypeList(CollectionUtil.asArrayList(EnumUtil.parse(ItemApproveTypeEnum.class,command.getApproveType())));
                SearchResult<ItemApproveSearchBO> searchResult = itemApproveSearchService.query(searchQuery);

                if (searchResult.isEmpty()) {
                    return writeExcel(null, null);
                } else {
                    return writeExcelForUnApproved(searchResult.getData());
                }
            }
        }
    }

    private static Result<ExcelData> writeExcelForUnApproved(List<ItemApproveSearchBO> data) {
        if (CollectionUtil.isEmpty(data)) {
            return Result.buildOpFailedResult("无数据导出");
        }

        Tuple2<HSSFWorkbook, HSSFSheet> workbookAndSheetTuple = createAndSetHeader();
        HSSFWorkbook workbook = workbookAndSheetTuple.getV1();
        HSSFSheet sheet = workbookAndSheetTuple.getV2();

        ItemService itemService = BeanFactory.getBean(ItemService.class);
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);

        // 准备数据
        Map<Long, List<Long>> itemSkuIdsMap = CollectionUtil.toListMap(data, "approveItemId", null, "approveSkuId", null);
        Map<Long, ItemSkuInfo> skuMap = new HashMap<>();
        for (Map.Entry<Long, List<Long>> entry : itemSkuIdsMap.entrySet()) {
            List<ItemSkuInfo> skuList = itemSkuService.getSkuInfosBySkuIds(entry.getKey(), entry.getValue());
            for (ItemSkuInfo itemSkuInfo : skuList) {
                skuMap.put(itemSkuInfo.getId(), itemSkuInfo);
            }
        }

        List<Long> itemIds = CollectionUtil.getFieldValueList(data, "approveItemId");
        List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);
        Map<Long, ItemRichDO> itemMap = CollectionUtil.toMap(itemList, "id");

        List<Long> brandIds = CollectionUtil.getFieldValueList(itemList, "brandId");
        List<BrandDO> brandList = brandService.getByIds(brandIds);
        Map<Long, BrandDO> brandMap = CollectionUtil.toMap(brandList, "id");

        List<PartnerCategoryDO> categoryList = categoryService.queryCategoryInCache();
        Map<Long, PartnerCategoryDO> categoryMap = CollectionUtil.toMap(categoryList, "id");
        Map<Long, SupplierDO> supplierMap = new HashMap<>();

        int rowIndex = 1;
        for (ItemApproveSearchBO itemApproveSearchBO : data) {
            ItemRichDO itemRichDO = itemMap.get(itemApproveSearchBO.getApproveItemId());
            ItemSkuInfo skuInfo = skuMap.get(itemApproveSearchBO.getApproveSkuId());
            if (skuInfo != null) {
                for (SkuChannelInfo skuChannelInfo : skuInfo.getSkuChannels()) {
                    // 匹配渠道
                    if (skuChannelInfo.getChannelType() == itemApproveSearchBO.getApproveChannelType()) {
                        for (SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
                            if (supplierStock.getSupplierId() == itemApproveSearchBO.getApproveSupplierId()) {
                                writeRow(sheet, rowIndex++, itemRichDO, skuInfo, skuChannelInfo, supplierStock, brandMap, categoryMap, supplierMap);
                            }
                        }
                    }
                }
            }
        }

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return Result.buildSuccessResult(new ExcelData("商品明细数据导出_" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls", new ByteArrayInputStream(outputStream.toByteArray())));
        } catch (Exception e) {
            return Result.buildOpFailedResult("生成excel失败");
        }
    }

    private static Result<ExcelData> writeExcel(List<Long> itemIds, ItemListForm command) {
        if (CollectionUtil.isEmpty(itemIds)) {
            return Result.buildOpFailedResult("无数据导出");
        }

        Tuple2<HSSFWorkbook, HSSFSheet> workbookAndSheetTuple = createAndSetHeader();
        HSSFWorkbook workbook = workbookAndSheetTuple.getV1();
        HSSFSheet sheet = workbookAndSheetTuple.getV2();

        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);

        // 准备数据
        List<ItemRichDO> itemRichList = itemService.getItemRichByIds(itemIds);
        List<ItemSkuInfo> itemSkuInfos = itemSkuService.getSkuInfosByItemIds(itemIds);
        Map<Long, ItemRichDO> itemMap = CollectionUtil.toMap(itemRichList, "id");
        List<BrandDO> brandList = brandService.getByIds(new ArrayList<>(CollectionUtil.getDisctinctFieldValueList(itemRichList, "brandId")));
        List<PartnerCategoryDO> categoryList = categoryService.queryCategoryInCache();
        Map<Long, BrandDO> brandMap = CollectionUtil.toMap(brandList, "id");
        Map<Long, PartnerCategoryDO> categoryMap = CollectionUtil.toMap(categoryList, "id");
        Map<Long, SupplierDO> supplierMap;
        if (CollectionUtil.isNotEmpty(command.getSupplierIds())) {
            List<SupplierDO> suppliers = supplierService.getSuppliers(new ArrayList<>(command.getSupplierIds()));
            if (CollectionUtil.isNotEmpty(suppliers)) {
                supplierMap = CollectionUtil.toMap(suppliers, "id");
            } else {
                supplierMap = new HashMap<>();
            }
        } else {
            supplierMap = new HashMap<>();
        }

        // 生成excel
        buildExcel(command, itemSkuInfos, sheet, itemMap, brandMap, categoryMap, supplierMap);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return Result.buildSuccessResult(new ExcelData("商品明细数据导出_" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls", new ByteArrayInputStream(outputStream.toByteArray())));
        } catch (Exception e) {
            return Result.buildOpFailedResult("生成excel失败");
        }
    }

    private static void buildExcel(ItemListForm command, List<ItemSkuInfo> itemSkuInfos, HSSFSheet sheet,
                                   Map<Long, ItemRichDO> itemMap, Map<Long, BrandDO> brandMap,
                                   Map<Long, PartnerCategoryDO> categoryMap, Map<Long, SupplierDO> supplierMap) {
        // 筛选条件
//        int status = command.getStatusList().get(0);
        Set<Integer> itemChannelTypes = command.getItemChannelTypes();
//        Set<Long> supplierIds = command.getSupplierIds();
        String upc = command.getKeywordType() == ItemListForm.ItemListKeywordTypeEnum.UPC ? command.getKeyword() : null;
        BigDecimal minPrice = command.getMinPrice();
        BigDecimal maxPrice = command.getMaxPrice();

        int rowIndex = 1;
        for (ItemSkuInfo itemSkuInfo : itemSkuInfos) {
            if (StringUtils.isNotBlank(upc) && !upc.equals(itemSkuInfo.getUpc())) {
                continue;
            }
            if (itemSkuInfo.getSkuChannels() == null) {
                continue;
            }

            for (SkuChannelInfo skuChannelInfo : itemSkuInfo.getSkuChannels()) {
                if (CollectionUtil.isNotEmpty(itemChannelTypes) && !itemChannelTypes.contains(skuChannelInfo.getChannelType())) {
                    continue;
                }
                if ((minPrice != null && skuChannelInfo.getPrice().compareTo(minPrice) < 0) ||
                        (maxPrice != null && skuChannelInfo.getPrice().compareTo(maxPrice) > 0)) {
                    continue;
                }

                for (SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
//                    if ((supplierStock.getStatus() != status) ||
//                            (CollectionUtil.isNotEmpty(supplierIds) && !supplierIds.contains(supplierStock.getSupplierId()))) {
//                        continue;
//                    }
                    if (command.getSupplierOfflineType() > 0) {
                        if (supplierStock.getStatus() != ItemStatusEnum.OFFLINE.getCode()) {
                            continue;
                        }
                        if (supplierStock.getOfflineType() != command.getSupplierOfflineType()) {
                            continue;
                        }
                    }

                    writeRow(sheet, rowIndex++, itemMap.get(itemSkuInfo.getItemId()), itemSkuInfo,
                            skuChannelInfo, supplierStock, brandMap, categoryMap, supplierMap);
                }
            }
        }
    }

    private static void writeRow(HSSFSheet sheet, int rowIndex, ItemRichDO item, ItemSkuInfo itemSkuInfo,
                                 SkuChannelInfo skuChannelInfo, SupplierStock supplierStock,
                                 Map<Long, BrandDO> brandMap, Map<Long, PartnerCategoryDO> categoryMap, Map<Long, SupplierDO> supplierMap) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        int colIndex = 0;
        Cell cell = row.createCell(colIndex++);
        cell.setCellValue(rowIndex);
        cell.getCellStyle().setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getColor().getIndex());
        row.createCell(colIndex++).setCellValue(item.getId());
        row.createCell(colIndex++).setCellValue(item.getName());
        row.createCell(colIndex++).setCellValue(itemSkuInfo.getDisplaySkuValue());
        row.createCell(colIndex++).setCellValue(itemSkuInfo.getUpc());
        if (item.getBrandId() > 0 && brandMap.get(item.getBrandId()) != null) {
            row.createCell(colIndex++).setCellValue(brandMap.get(item.getBrandId()).getBrandNameOrOriginalName());
        }
        if (item.getStatus() != ItemStatusEnum.INIT.getCode()) {
            row.createCell(colIndex++).setCellValue(categoryMap.get(item.getCateId1()).getName());
            row.createCell(colIndex++).setCellValue(categoryMap.get(item.getCateId2()).getName());
            row.createCell(colIndex++).setCellValue(categoryMap.get(item.getCateId3()).getName());
        } else {
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue("");
            row.createCell(colIndex++).setCellValue("");
        }
        row.createCell(colIndex++).setCellValue(Math.round(itemSkuInfo.getWeight().doubleValue() * 1000));
        row.createCell(colIndex++).setCellValue(SkuUtil.encodeSkuNo(itemSkuInfo.getItemId(), itemSkuInfo.getId(), skuChannelInfo.getChannelType()));
        SupplierDO supplierDO = supplierMap.get(supplierStock.getSupplierId());
        if (supplierDO == null) {
            SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
            supplierDO = supplierService.getSupplier(supplierStock.getSupplierId());
            supplierMap.put(supplierDO.getId(), supplierDO);
        }
        row.createCell(colIndex++).setCellValue(supplierDO.getSupplierName());
        row.createCell(colIndex++).setCellValue(skuChannelInfo.getChannelName());
        row.createCell(colIndex++).setCellValue(supplierStock.getStock());
        row.createCell(colIndex++).setCellValue(supplierStock.getChannelPrice().toString());
        row.createCell(colIndex++).setCellValue(skuChannelInfo.getEnterprisePrice().toString());
        row.createCell(colIndex++).setCellValue(skuChannelInfo.getPrice().toString());
        row.createCell(colIndex).setCellValue(supplierStock.getStatus() == StatusEnum.ONE.getCode() ? "销售" : "不销售");
    }

    private static Tuple2<HSSFWorkbook, HSSFSheet> createAndSetHeader() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1);
        ExcelUtil.writeHeader(workbook, sheet, HEADER, null);
        return new Tuple2<>(workbook, sheet);
    }

    private static List<ItemListBO> convert(List<ItemRichDO> itemList) {
        List<ItemListBO> list = new ArrayList<>();
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        List<Long> itemIds = CollectionUtil.getFieldValueList(itemList, "id");
        // 规格
        Map<Long, List<ItemSkuInfo>> skuInfosMap = CollectionUtil.toListMap(itemSkuService.getSkuInfosByItemIds(itemIds), "itemId");
        // 图片
        Map<Long, List<ItemImageDO>> itemImageMap = itemService.getImagesByItemIds(itemIds);
        // 品牌
        Map<Long, BrandDO> brandMap = CollectionUtil.toMap(
                brandService.getByIds(new ArrayList<>(CollectionUtil.getDisctinctFieldValueList(itemList, "brandId"))), "id");
        for (ItemRichDO itemRichDO : itemList) {
            ItemListBO bo = new ItemListBO();
            // 品牌
            if (itemRichDO.getBrandId() > 0) {
                bo.setBrandId(itemRichDO.getBrandId());
                BrandDO brandDO = brandMap.get(itemRichDO.getBrandId());
                if (brandDO != null) {
                    bo.setBrandName(brandDO.getBrandNameOrOriginalName());
                }
            }
            // 主图
            String mainImageUrl = getMainImageUrl(itemImageMap.get(itemRichDO.getId()));
            bo.setMainImageUrl(mainImageUrl);
            // 规格
            List<ItemSkuInfo> skuInfoList = skuInfosMap.get(itemRichDO.getId());

            // 规格名称及可选值
            StringBuilder builder = new StringBuilder();
            ItemSkuNameMapper itemSkuNameMapper = BeanFactory.getBean(ItemSkuNameMapper.class);
            HashSet<String> skuList = new HashSet<>();
            List<ItemSkuNameDO> itemSkuNameDOList = itemSkuNameMapper.selectByItemId(SimpleRouteOperate.of(itemRichDO.getId()));
            if (CollectionUtil.isNotEmpty(itemSkuNameDOList)) {
                for (ItemSkuNameDO itemSkuNameDO : itemSkuNameDOList) {
                    ItemSkuNameValues skuNV = new ItemSkuNameValues();
                    skuNV.setSkuName(SkuUtil.fixDisplaySkuValue(itemSkuNameDO.getSkuName()));
                    skuNV.setSkuValues(itemSkuNameDO.parseSkuValue());
                    builder.append(itemSkuNameDO.getSkuName()).append("：");
                    for (String skuValue : itemSkuNameDO.parseSkuValue()) {
                        builder.append(skuValue).append("、");
                    }
                    builder.deleteCharAt(builder.length()-1);
                    builder.append("；");
                    skuList.add(builder.toString());
                }
            }
            HashSet<Integer> quantitySet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(skuInfoList)) {
                builder.append("件装：");
                for (ItemSkuInfo itemSkuInfo : skuInfoList) {
                    if (quantitySet.add(itemSkuInfo.getQuantity())) {
                        builder.append(SkuUtil.unitFormat(itemSkuInfo.getQuantity(), itemRichDO.getUnit())).append("、");
                    }
                }
                builder.deleteCharAt(builder.length()-1);
            }
            bo.setSkuListStr(builder.toString());

            bo.setChannels(getChannelList(skuInfoList));

            bo.setCateId1(itemRichDO.getCateId1());
            bo.setCateId2(itemRichDO.getCateId2());
            bo.setCateId3(itemRichDO.getCateId3());
            bo.setCreateTime(itemRichDO.getCreateTime());
            bo.setCurrency(itemRichDO.getCurrency());
            bo.setDeleted(itemRichDO.isDeleted());
            bo.setId(itemRichDO.getId());
            bo.setModifyTime(itemRichDO.getModifyTime());
            bo.setName(itemRichDO.getName());
            bo.setOriginalName(itemRichDO.getOriginalName());
            bo.setShopId(itemRichDO.getShopId());
            bo.setStatus(itemRichDO.getStatus());
            bo.setPrice(AmountUtil.getAdminDisplayPriceStartWithSymbol(itemRichDO.getCurrency(), itemRichDO.getPrice()));
            bo.setGroupId(itemRichDO.getGroupId());
            bo.setOnlineTime(itemRichDO.getOnlineTime());
            bo.setOfflineTime(itemRichDO.getOfflineTime());
            list.add(bo);
        }
        return list;
    }

    private static ItemListBO convert(ItemRichDO itemRichDO) {
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        BrandService brandService = BeanFactory.getBean(BrandService.class);

        ItemListBO bo = new ItemListBO();
        List<ItemSkuInfo> skuInfoList = itemSkuService.getSkuInfosByItemId(itemRichDO.getId());
        bo.setSkuList(getSkuList(skuInfoList,itemRichDO));

        if (itemRichDO.getBrandId() > 0) {
            bo.setBrandId(itemRichDO.getBrandId());
            BrandDO brandDO = brandService.getById(itemRichDO.getBrandId());
            if (brandDO != null) {
                bo.setBrandName(brandDO.getBrandNameOrOriginalName());
            }
        }
        bo.setCateId1(itemRichDO.getCateId1());
        bo.setCateId2(itemRichDO.getCateId2());
        bo.setCateId3(itemRichDO.getCateId3());
        bo.setCreateTime(itemRichDO.getCreateTime());
        bo.setCurrency(itemRichDO.getCurrency());
        bo.setDeleted(itemRichDO.isDeleted());
        bo.setId(itemRichDO.getId());
        bo.setModifyTime(itemRichDO.getModifyTime());
        bo.setName(itemRichDO.getName());
        bo.setOriginalName(itemRichDO.getOriginalName());
        bo.setShopId(itemRichDO.getShopId());
        bo.setStatus(itemRichDO.getStatus());
        if (EnumUtil.isMatchByBit(itemRichDO.getRefStatus(), ItemRefStatusEnum.IMAGE)) {
            ItemImageService itemImageService = BeanFactory.getBean(ItemImageService.class);
            List<ItemImageDO> itemImageList = itemImageService.getItemImages(itemRichDO.getId());
            bo.setMainImageUrl(getMainImageUrl(itemImageList));
        }

        return bo;
    }

    private static Set<String> getSkuList(List<ItemSkuInfo> skuInfoList,ItemRichDO itemRichDO) {
        if (CollectionUtil.isNotEmpty(skuInfoList)) {
            Set<String> skuList = new LinkedHashSet<>();
            for (ItemSkuInfo itemSkuInfo : skuInfoList) {
                skuList.add(SkuUtil.buildSkuDescIncludeGroup(itemSkuInfo,itemRichDO.getUnit()));
            }
            return skuList;
        }
        return null;
    }

    private static Set<String> getChannelList(List<ItemSkuInfo> skuInfoList) {
        if (CollectionUtil.isNotEmpty(skuInfoList)) {
            Set<String> channelList = new LinkedHashSet<>();
            for (ItemSkuInfo itemSkuInfo : skuInfoList) {
                if (CollectionUtil.isNotEmpty(itemSkuInfo.getSkuChannels())) {
                    for (ItemSkuInfo.SkuChannelInfo skuChannelInfo : itemSkuInfo.getSkuChannels()) {
                        channelList.add(skuChannelInfo.getChannelName());
                    }
                }
            }
            return channelList;
        }
        return null;
    }

    private static String getMainImageUrl(List<ItemImageDO> itemImageList) {
        String itemImageUrl = null;
        if (CollectionUtil.isNotEmpty(itemImageList)) {
            for (ItemImageDO itemImageDO : itemImageList) {
                if (EnumUtil.isMatch(itemImageDO.getType(), ItemImageTypeEnum.MAIN)) {
                    itemImageUrl = StringUtils.isNotBlank(itemImageDO.getImageUrl()) ? itemImageDO.getImageUrl() : itemImageDO.getOriImageUrl();
                    break;
                }
            }
            if (StringUtils.isBlank(itemImageUrl)) {
                ItemImageDO itemImageDO = itemImageList.get(0);
                itemImageUrl = StringUtils.isNotBlank(itemImageDO.getImageUrl()) ? itemImageDO.getImageUrl() : itemImageDO.getOriImageUrl();
            }
        }
        return itemImageUrl;
    }

}
