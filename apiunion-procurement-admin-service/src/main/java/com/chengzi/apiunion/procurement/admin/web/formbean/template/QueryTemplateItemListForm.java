package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.apiunion.supplier.common.item.enums.SupplierTemplateItemStatusEnum;
import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Date;

/**
 * @author zhiyuan
 * 模板商品查询：供应商类型，供应商名字 查询
 */
public class QueryTemplateItemListForm extends BasePageForm {

    private SupplierTemplateItemStatusEnum status;

    /**
     * 搜索的关键字
     * 品牌 名称 条形码
     */
    private String                          keyword;
    /**
     * 类目
     */
    private Long                    cateId;
    /**
     * 更新时间 开始
     */
    private Date modifyTimeBegin;
    /**
     * 更新时间 结束
     */
    private Date modifyTimeEnd;


    public SupplierTemplateItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SupplierTemplateItemStatusEnum status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Date getModifyTimeBegin() {
        return modifyTimeBegin;
    }

    public void setModifyTimeBegin(Date modifyTimeBegin) {
        this.modifyTimeBegin = modifyTimeBegin;
    }

    public Date getModifyTimeEnd() {
        return modifyTimeEnd;
    }

    public void setModifyTimeEnd(Date modifyTimeEnd) {
        this.modifyTimeEnd = modifyTimeEnd;
    }
}
