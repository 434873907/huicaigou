package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.math.BigDecimal;

/**
 * @author 月汐
 * @date 2019/4/10 20:21
 */
public class GetDifferenceExpressFeeBO extends JsonPojo {

    private BigDecimal expFee;

    private BigDecimal currentExpFee;

    private BigDecimal difference;

    public BigDecimal getExpFee() {
        return expFee;
    }

    public void setExpFee(BigDecimal expFee) {
        this.expFee = expFee;
    }

    public BigDecimal getCurrentExpFee() {
        return currentExpFee;
    }

    public void setCurrentExpFee(BigDecimal currentExpFee) {
        this.currentExpFee = currentExpFee;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }
}
