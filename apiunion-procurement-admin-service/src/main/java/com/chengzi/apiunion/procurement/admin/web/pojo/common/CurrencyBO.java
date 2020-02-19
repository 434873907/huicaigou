/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.common;

import com.chengzi.common.data.beans.JsonPojo;

import java.math.BigDecimal;

/**
 * @author Kolor
 */
public class CurrencyBO extends JsonPojo {

    /**
     * 货币代码
     */
    private int    code;
    /**
     * 货币符号
     */
    private String symbol;

    /**
     * 货币名称
     */
    private String name;

    private BigDecimal rate;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
