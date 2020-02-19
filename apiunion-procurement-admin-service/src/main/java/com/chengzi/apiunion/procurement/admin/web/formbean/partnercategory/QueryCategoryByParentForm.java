package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.brand.pojo.search.query.BrandSearchQuery;
import com.chengzi.apiunion.brand.search.BrandSearchService;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.enums.ItemSortTypeEnum;
import com.chengzi.apiunion.item.pojo.search.query.ItemApproveSearchQuery;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.ItemListForm;
import com.chengzi.common.data.support.BigDecimalRange;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.annotation.JsonField;
import org.summercool.web.servlet.BeanFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 月汐
 * @date 2018/10/12 19:11
 */
public class QueryCategoryByParentForm extends BaseForm {

    private Long parentId;

    private Integer status;

    /**
     * 商品状态
     */
    private ItemStatusEnum itemStatus;

    private Long                    cateId;

    private Set<Long> supplierIds;

    private Set<Long>               shopIds;

    private Set<Long>               brandIds;

    private Set<Integer>            itemChannelTypes;

    private int                     isGift = -1;

    private int                     presell = -1;

    private String                  keyword;
    /**
     * 关键字类型，1：商品ID，2：商品名称，3：UPC
     */
    private ItemListForm.ItemListKeywordTypeEnum keywordType;

    /**
     * 价格区间
     */
    @Min(value = 0, message = "请输入正确金额")
    private BigDecimal minPrice;

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

    /**
     * 审核类型
     * @see ItemApproveTypeEnum
     */
    private int approveType;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ItemStatusEnum getItemStatus() {
        return itemStatus;
    }

    public QueryCategoryByParentForm setItemStatus(ItemStatusEnum itemStatus) {
        this.itemStatus = itemStatus;
        return this;
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

    public Set<Long> getShopIds() {
        return shopIds;
    }

    public void setShopIds(Set<Long> shopIds) {
        this.shopIds = shopIds;
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

    
    public ItemListForm.ItemListKeywordTypeEnum getKeywordType() {
        return keywordType;
    }

    
    public void setKeywordType(ItemListForm.ItemListKeywordTypeEnum keywordType) {
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

    
    public int getSupplierOfflineType() {
        return supplierOfflineType;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }

    public void setSupplierOfflineType(int supplierOfflineType) {
        this.supplierOfflineType = supplierOfflineType;
    }

    public ItemApproveSearchQuery buildQuery() {
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);

        // 搜索过滤
        ItemApproveSearchQuery searchQuery = new ItemApproveSearchQuery();
        searchQuery.setIncludesFields(new String[] { "id" });
        searchQuery.setChannelTypes(getItemChannelTypes());
        searchQuery.setSupplierOfflineType(getSupplierOfflineType());
        searchQuery.setStatus(itemStatus);
        if (itemStatus != null) {
            if (itemStatus == ItemStatusEnum.INIT) {
                searchQuery.setApproveStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE));
            } else {
                searchQuery.setApprovedStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE,ItemApproveStatusEnum.APPROVED));
            }
        }

        searchQuery.setBrandIds(getBrandIds());
        searchQuery.setShopIds(getShopIds());
        searchQuery.setSupplierIds(getSupplierIds());
        searchQuery.setIsPresell(getPresell());
        if (StringUtils.isNotBlank(getKeyword())) {
            if (ItemListForm.ItemListKeywordTypeEnum.ITEM_NAME == getKeywordType()) {
                searchQuery.setItemName(getKeyword());
            } else if (ItemListForm.ItemListKeywordTypeEnum.UPC == getKeywordType()) {
                searchQuery.setUpc(getKeyword());
            } else {
                searchQuery.setKeyword(getKeyword());
            }
        }

        searchQuery.setPriceRange(new BigDecimalRange(getMinPrice(), getMaxPrice()));
        searchQuery.setRouteId(RequestContext.getRouteId());
        searchQuery.setIsGift(getIsGift());
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

        if (approveType > 0) {
            searchQuery.setApproveTypeList(CollectionUtil.asArrayList(EnumUtil.parse(ItemApproveTypeEnum.class,approveType)));
        }

        return searchQuery;
    }
}
