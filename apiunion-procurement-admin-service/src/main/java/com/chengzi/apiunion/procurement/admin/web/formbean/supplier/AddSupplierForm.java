/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import java.util.ArrayList;
import java.util.List;

import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ThirdSourceItemAttr;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdTargetItemAttr;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.MaxLength;

/**
 * @author Kolor
 *
 */
public class AddSupplierForm extends BaseForm {
    @NotBlankAndNull(message = "供应商名称")
    @MaxLength(value = 32, message = "供应商名称字数过多")
    private String                supplierName;

    @JsonField
//    @MinSize(value = 1, message = "请至少选择一个渠道")
    private List<Integer>         channelTypes = new ArrayList<>();

    /**
     * 接口同步类型，0表示不同步，默认不同步
     */
    private ThirdSyncItemTypeEnum apiType;

    /**
     * 授权信息
     */
    @JsonField
    private ThirdSyncApiAuth      apiAuth;
    /**
     * 来源商品属性
     */
    @JsonField
    private ThirdSourceItemAttr   sourceItemAttr;
    /**
     * 目标商品属性
     */
    @JsonField
    private ThirdTargetItemAttr   targetItemAttr;
    /**
     * 价格策略
     */
    private String                priceStrategy;
    /**
     * 状态（0：禁用，1：启用）
     */
    private int                   status;
    /**
     * 登录账号
     */
    private String                account;
    /**
     * 登录密码
     */
    private String                password;
    /**
     * 联系人
     */
    private String                contacts;
    /**
     * 手机号码
     */
    private String                phone;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<Integer> getChannelTypes() {
        return channelTypes;
    }

    public void setChannelTypes(List<Integer> channelTypes) {
        this.channelTypes = channelTypes;
    }

    public ThirdSyncItemTypeEnum getApiType() {
        return apiType;
    }

    public void setApiType(ThirdSyncItemTypeEnum apiType) {
        this.apiType = apiType;
    }

    public ThirdSyncApiAuth getApiAuth() {
        return apiAuth;
    }

    public void setApiAuth(ThirdSyncApiAuth apiAuth) {
        this.apiAuth = apiAuth;
    }

    public ThirdSourceItemAttr getSourceItemAttr() {
        return sourceItemAttr;
    }

    public void setSourceItemAttr(ThirdSourceItemAttr sourceItemAttr) {
        this.sourceItemAttr = sourceItemAttr;
    }

    public ThirdTargetItemAttr getTargetItemAttr() {
        return targetItemAttr;
    }

    public void setTargetItemAttr(ThirdTargetItemAttr targetItemAttr) {
        this.targetItemAttr = targetItemAttr;
    }

    public String getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(String priceStrategy) {
        this.priceStrategy = priceStrategy;
    }
}
