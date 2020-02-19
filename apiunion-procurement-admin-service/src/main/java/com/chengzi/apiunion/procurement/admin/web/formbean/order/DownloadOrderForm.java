package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.enums.CodeEnum;
import com.chengzi.common.web.formbean.BaseForm;

import java.util.Date;
import java.util.Set;

/**
 * @author 月汐
 * @date 2018/12/20 10:20
 */
public class DownloadOrderForm extends BaseForm {

    /**
     * 状态 按照折疯了目前的状态定义
     */
    private int       status;

    /**
     * 供应商ID
     */
    private Set<Long> supplierIds;

    /**
     * 采购商ID
     */
    private Set<Long> userIds;

    /**
     * 关键词类型、
     * @see OrderListForm.OrderListKeywordTypeEnum
     */
    private int       keywordType;
    /**
     * 关键词
     */
    private String    keyword;

    /**
     * 下单时间 开始
     */
    private Date      startTime;

    /**
     * 下单时间 结束
     */
    private Date      endTime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Long> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Long> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public int getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(int keywordType) {
        this.keywordType = keywordType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public static enum OrderListKeywordTypeEnum implements CodeEnum {
        ITEM_ID(1, "商品ID"),
        ITEM_NAME(2, "商品名称"),
        RECEIVER_NAME(3, "收货人姓名"),
        ;

        private int    code;
        private String name;

        private OrderListKeywordTypeEnum(int code, String name) {
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

}
