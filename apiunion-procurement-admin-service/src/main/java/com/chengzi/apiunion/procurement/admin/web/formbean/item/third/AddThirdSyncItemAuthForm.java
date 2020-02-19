/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item.third;

import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdTargetItemAttr;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * @author Kolor
 */
public class AddThirdSyncItemAuthForm extends BaseForm {

    /**
    * 名称
    */
    @NotBlankAndNull(message = "名称不能为空")
    @MaxLength(value = 32, message = "名称长度不能超过32个字符")
    private String                name;

    /**
    * 供应商ID
    */
    @Min(value = 1, message = "供应商ID不能为空")
    private long                  supplierId;

    /**
    * API授权信息
    */
    @NotNull(message = "授权信息不能为空")
    private ThirdSyncApiAuth      apiAuth;

    /**
    * 类型，1：超级快递，2：橙子
    */
    @NotNull(message = "接口类型不能为空")
    private ThirdSyncItemTypeEnum apiType;

    /**
    * 商品目标
    */
    private ThirdTargetItemAttr   itemAttr;

    /**
    * 价格策略
    */
    private String                priceStrategy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public ThirdSyncApiAuth getApiAuth() {
        return apiAuth;
    }

    public void setApiAuth(ThirdSyncApiAuth apiAuth) {
        this.apiAuth = apiAuth;
    }

    public ThirdSyncItemTypeEnum getApiType() {
        return apiType;
    }

    public void setApiType(ThirdSyncItemTypeEnum apiType) {
        this.apiType = apiType;
    }

    public ThirdTargetItemAttr getItemAttr() {
        return itemAttr;
    }

    public void setItemAttr(ThirdTargetItemAttr itemAttr) {
        this.itemAttr = itemAttr;
    }

    public String getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(String priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

}
