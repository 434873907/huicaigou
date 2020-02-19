/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.brand.pojo.search.query.BrandSearchQuery;
import com.chengzi.apiunion.brand.search.BrandSearchService;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.pojo.search.enums.ItemSortTypeEnum;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import net.sf.oval.constraint.Min;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.common.data.enums.CodeEnum;
import com.chengzi.common.data.support.BigDecimalRange;
import com.chengzi.common.web.formbean.BasePageForm;
import org.summercool.web.servlet.BeanFactory;

/**
 * @author Kolor
 */
public class ItemListForm extends BasePageForm {
    private List<Long>              ids;

    @JsonField
    private List<Integer>           statusList;

    private Long                    cateId;

    private Set<Long>               supplierIds;

    private Set<Long>               shopIds;
    
    private Set<Long>               brandIds;

    private Set<Integer>            itemChannelTypes;

    private int                     isGift = -1;

    private int                     presell = -1;

    private String                  keyword;
    /**
     * 关键字类型，1：商品ID，2：商品名称，3：UPC
     */
    private ItemListKeywordTypeEnum keywordType;

    /**
     * 价格区间
     */
    @Min(value = 0, message = "请输入正确金额")
    private BigDecimal              minPrice;

    @Min(value = 0, message = "请输入正确金额")
    private BigDecimal              maxPrice;

    /**
     * 供应商库存范围
     */
    private Integer minSupplierStock;
    private Integer maxSupplierStock;

    /**
     * 供应商下架类型
     */
    private int                     supplierOfflineType;
    
    public static enum ItemListKeywordTypeEnum implements CodeEnum {
        ITEM_ID(1, "商品ID"),
        ITEM_NAME(2, "商品名称"),
        UPC(3, "商品规格UPC"),
        ;
        private int    code;
        private String name;

        private ItemListKeywordTypeEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Set<Long> getShopIds() {
        return shopIds;
    }

    public void setShopIds(Set<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Set<Long> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Long> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public Set<Long> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(Set<Long> brandIds) {
        this.brandIds = brandIds;
    }

    public Set<Integer> getItemChannelTypes() {
        return itemChannelTypes;
    }

    public void setItemChannelTypes(Set<Integer> itemChannelTypes) {
        this.itemChannelTypes = itemChannelTypes;
    }

    public int getIsGift() {
        return isGift;
    }

    public void setIsGift(int isGift) {
        this.isGift = isGift;
    }

    public int getPresell() {
        return presell;
    }

    public void setPresell(int presell) {
        this.presell = presell;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ItemListKeywordTypeEnum getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(ItemListKeywordTypeEnum keywordType) {
        this.keywordType = keywordType;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getSupplierOfflineType() {
        return supplierOfflineType;
    }

    public void setSupplierOfflineType(int supplierOfflineType) {
        this.supplierOfflineType = supplierOfflineType;
    }

    public Integer getMinSupplierStock() {
        return minSupplierStock;
    }

    public void setMinSupplierStock(Integer minSupplierStock) {
        this.minSupplierStock = minSupplierStock;
    }

    public Integer getMaxSupplierStock() {
        return maxSupplierStock;
    }

    public void setMaxSupplierStock(Integer maxSupplierStock) {
        this.maxSupplierStock = maxSupplierStock;
    }

    public Result<ItemSearchQuery> buildQuery() {
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);

        // 搜索过滤
        ItemSearchQuery searchQuery = new ItemSearchQuery();
        searchQuery.setApprovedStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE,ItemApproveStatusEnum.APPROVED));
        searchQuery.setIncludesFields(new String[] { "id" });
        searchQuery.setFrom(getOffset());
        searchQuery.setSize(getLimit());
        searchQuery.setChannelTypes(getItemChannelTypes());
        searchQuery.setSupplierOfflineType(getSupplierOfflineType());

        List<ItemStatusEnum> statusEnumList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(statusList)) {
            for (Integer status : statusList) {
                statusEnumList.add(EnumUtil.parse(ItemStatusEnum.class, status));
            }
        }

        searchQuery.setStatusList(statusEnumList);
        searchQuery.setBrandIds(getBrandIds());
        searchQuery.setShopIds(getShopIds());
        searchQuery.setSupplierIds(getSupplierIds());
        searchQuery.setIsPresell(getPresell());
        if (getCateId() != null) {
            PartnerCategoryDO cateDO = categoryService.getById(getCateId());
            if (cateDO == null) {
                return Result.buildIllegalArgumentResult("类目不存在");
            }
            if (PartnerCategoryConstant.THIRD_CLASS_CATEGORY == cateDO.getDepth()) {
                searchQuery.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, 0, cateDO.getId())));
            } else if (PartnerCategoryConstant.SECOND_CLASS_CATEGORY == cateDO.getDepth()) {
                searchQuery.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, cateDO.getId(), 0)));
            } else if (PartnerCategoryConstant.FIRST_CLASS_CATEGORY == cateDO.getDepth()) {
                searchQuery.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(cateDO.getId(), 0, 0)));
            }
        }

        if (StringUtils.isNotBlank(getKeyword())) {
            if (ItemListKeywordTypeEnum.ITEM_NAME == getKeywordType()) {
                searchQuery.setItemName(getKeyword());
            } else if (ItemListKeywordTypeEnum.UPC == getKeywordType()) {
                searchQuery.setUpc(getKeyword());
            } else {
                searchQuery.setKeyword(getKeyword());
            }
        }

        searchQuery.setPriceRange(new BigDecimalRange(getMinPrice(), getMaxPrice()));
        searchQuery.setRouteId(RequestContext.getRouteId());
        searchQuery.setIsGift(getIsGift());
        if (getOrderByField() > 0) {
            searchQuery.setOrderBy(ItemSortTypeEnum.parse(getOrderByField()).getOrderBys());
        }

        searchQuery.setSupplierStockRange(new Range(minSupplierStock,maxSupplierStock));

        if (CollectionUtil.isEmpty(brandIds) && StringUtils.isNotEmpty(keyword) && keywordType == null) {
            BrandSearchService brandSearchService = BeanFactory.getBean(BrandSearchService.class);
            BrandSearchQuery brandSearchQuery = new BrandSearchQuery();
            brandSearchQuery.setName(keyword);
            brandSearchQuery.setSize(999);
            SearchResult<BrandSearchBO> brandSearchResult = brandSearchService.query(brandSearchQuery);
            if (!brandSearchResult.isEmpty()) {
                Set<Long> brandIds = CollectionUtil.getDisctinctFieldValueList(brandSearchResult.getData(), "id");
                searchQuery.setOrBrandIds(brandIds);
            }
        }

        return Result.buildSuccessResult(searchQuery);
    }
}
