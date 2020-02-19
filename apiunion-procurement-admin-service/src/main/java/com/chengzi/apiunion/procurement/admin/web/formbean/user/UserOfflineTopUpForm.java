package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BaseForm;

import java.math.BigDecimal;

/**
 * @author 月汐
 * @date 2019/12/20 15:42
 */
public class UserOfflineTopUpForm extends BaseForm {

    private String     account;

    private BigDecimal amount;

    private String     desc;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
