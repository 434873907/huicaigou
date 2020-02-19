package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.item;

import java.util.HashSet;
import java.util.List;

import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierItemSearchQuery;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.BasePageForm;
import org.summercool.web.annotation.JsonField;

/**
 * 查询供应商商品List
 **/
public class QuerySupplierItemListForm extends BasePageForm {

    //商品类目
    private Long                    cateId;

    private String                          keyword;

    /**
     * 供应商id列表
     */
    @JsonField
    private List<Long> supplierIds;

    /**
     * 发货方式
     */
    private Integer channelType;

    /**
     * 商品状态：null表示全部状态
     * 已上架，已下架，未公售
     * @see com.chengzi.apiunion.item.enums.ItemStatusEnum
     */
    private Integer status;

    /**
     * 库存状态：null表示全部状态
     */
    private Integer stockStatus;


    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Long> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(List<Long> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }


    public Result<SupplierItemSearchQuery> buildQuery() {
        SupplierItemSearchQuery searchQuery = new SupplierItemSearchQuery();

        searchQuery.setFrom(getOffset());
        searchQuery.setSize(getLimit());

        searchQuery.setKeyword(keyword);
        searchQuery.setSupplierIds(supplierIds);

        if(channelType != null) {
            searchQuery.setChannelTypes(new HashSet<>(channelType));
        }

        searchQuery.setCateId(cateId);
        searchQuery.setStatus(status);
        searchQuery.setStockStatus(stockStatus);

        return Result.buildSuccessResult(searchQuery);

    }

}
