package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.enums.CodeEnum;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Date;
import java.util.Set;

/**
 * @author 苏子
 * @date 2018年10月17日
 */
public class OrderListForm extends BasePageForm {

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
     * @see OrderListKeywordTypeEnum
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

    /**
     * 异常类型
     */
    private int       abnormalType;

    /**
     * 订单创建平台 ，null值表示全部平台
     * @see com.chengzi.common.data.enums.PlatformEnum
     */
    private Integer createPlatform;

    //是否查询已经删除的订单
    private Integer isDelete;
    //订单的标记状态
    private Integer tagStatus;

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

    public int getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(int abnormalType) {
        this.abnormalType = abnormalType;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getCreatePlatform() {
        return createPlatform;
    }

    public void setCreatePlatform(Integer createPlatform) {
        this.createPlatform = createPlatform;
    }

    public Integer getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(Integer tagStatus) {
        this.tagStatus = tagStatus;
    }

    public static enum OrderListKeywordTypeEnum implements CodeEnum {
        ITEM_ID(1, "商品ID"),
        ITEM_NAME(2, "商品名称"),
        RECEIVER_NAME(3, "收货人姓名"),
        ORDER_NUM(4, "订单号"),
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
