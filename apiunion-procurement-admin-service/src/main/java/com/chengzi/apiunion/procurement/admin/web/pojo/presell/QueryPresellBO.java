package com.chengzi.apiunion.procurement.admin.web.pojo.presell;

import com.chengzi.common.data.beans.JsonPojo;

import java.math.BigDecimal;

/**
 * @author 月汐
 * @date 2019/2/26 9:59
 */
public class QueryPresellBO extends JsonPojo {

    private long id;

    private String name;

    private String itemName;

    private BigDecimal depositAmount;

    private int depositPaidNum;

    private int tailPaidNum;

    private BigDecimal actualPaidAmount;

    private String actStatusDesc;

    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public int getDepositPaidNum() {
        return depositPaidNum;
    }

    public void setDepositPaidNum(int depositPaidNum) {
        this.depositPaidNum = depositPaidNum;
    }

    public int getTailPaidNum() {
        return tailPaidNum;
    }

    public void setTailPaidNum(int tailPaidNum) {
        this.tailPaidNum = tailPaidNum;
    }

    public BigDecimal getActualPaidAmount() {
        return actualPaidAmount;
    }

    public void setActualPaidAmount(BigDecimal actualPaidAmount) {
        this.actualPaidAmount = actualPaidAmount;
    }

    public String getActStatusDesc() {
        return actStatusDesc;
    }

    public void setActStatusDesc(String actStatusDesc) {
        this.actStatusDesc = actStatusDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
