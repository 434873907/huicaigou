package com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.expressfeetemplate.pojo.CarryMode;
import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import com.chengzi.apiunion.expressfeetemplate.pojo.FreeCondition;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/19 11:55
 */
public class AddExpressFeeTemplateForm extends BaseForm {

    @NotBlankAndNull(message = "模板名不能为空")
    private String templateName;

    private Long supplierId;

    private Integer status;

    private Integer channelType;

    /**
     * 货币类型
     */
    @NotNull(message = "请选择货币")
    private CurrencyEnum currency;

    @Min(value = 0, message = "请选择是否包邮")
    private int isFree;

    @Min(value = 0, message = "请选择计价方式")
    private int valuationModel;

    /**
     * 计价方式下是否存在按条件包邮
     */
    private int isFreeByIf;

    /**
     * 计价方式
     */
    @JsonField
    private CarryMode carryMode;

    /**
     * 条件包邮
     */
    @JsonField
    private List<FreeCondition> freeConditionList;

    /**
     * 不可送达地区
     */
    private String undeliverableArea;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public int getValuationModel() {
        return valuationModel;
    }

    public void setValuationModel(int valuationModel) {
        this.valuationModel = valuationModel;
    }

    public int getIsFreeByIf() {
        return isFreeByIf;
    }

    public void setIsFreeByIf(int isFreeByIf) {
        this.isFreeByIf = isFreeByIf;
    }

    public CarryMode getCarryMode() {
        return carryMode;
    }

    public void setCarryMode(CarryMode carryMode) {
        this.carryMode = carryMode;
    }

    public List<FreeCondition> getFreeConditionList() {
        return freeConditionList;
    }

    public void setFreeConditionList(List<FreeCondition> freeConditionList) {
        this.freeConditionList = freeConditionList;
    }

    public String getUndeliverableArea() {
        return undeliverableArea;
    }

    public void setUndeliverableArea(String undeliverableArea) {
        this.undeliverableArea = undeliverableArea;
    }

    public static ExpressFeeTemplateDO convert(AddExpressFeeTemplateForm form) {
        ExpressFeeTemplateDO expressFeeTemplateDO = new ExpressFeeTemplateDO();
        expressFeeTemplateDO.setTemplateName(form.getTemplateName());
        expressFeeTemplateDO.setSupplierId(form.getSupplierId());
        expressFeeTemplateDO.setStatus(form.getStatus());
        expressFeeTemplateDO.setChannelType(form.getChannelType());
        expressFeeTemplateDO.setIsFree(form.getIsFree());
        expressFeeTemplateDO.setValuationModel(form.getValuationModel());
        expressFeeTemplateDO.setIsFreeByIf(form.getIsFreeByIf());
        expressFeeTemplateDO.setCarryMode(JSONObject.toJSONString(form.getCarryMode()));
        expressFeeTemplateDO.setFreeCondition(JSONObject.toJSONString(form.getFreeConditionList()));
        expressFeeTemplateDO.setCurrency(form.getCurrency().getCode());
        expressFeeTemplateDO.setUndeliverableArea(form.getUndeliverableArea());
        return expressFeeTemplateDO;
    }

    public Result<String> verifyData() {
        if (isFree != 1 && isFreeByIf == 1 && CollectionUtil.isNotEmpty(freeConditionList)) {
            for (FreeCondition freeCondition : freeConditionList) {
                if (freeCondition.getLimit() == null) {
                    return Result.buildIllegalArgumentResult("请输入包邮条件");
                }
                if (freeCondition.getType() == null || freeCondition.getType() <= 0) {
                    return Result.buildIllegalArgumentResult("请选择正确的包邮类型");
                }
                if (StringUtils.isBlank(freeCondition.getRegion())) {
                    return Result.buildIllegalArgumentResult("请选择参与包邮的地区");
                }
            }
        }
        return Result.buildEmptySuccess();
    }
}
