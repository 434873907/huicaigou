package com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/19 15:33
 */
public class UpdateExpressFeeTemplateForm extends AddExpressFeeTemplateForm {

    @Min(value = 1, message = "请选择正确的物流模板")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static ExpressFeeTemplateDO convert(UpdateExpressFeeTemplateForm form) {
        ExpressFeeTemplateDO expressFeeTemplateDO = new ExpressFeeTemplateDO();
        expressFeeTemplateDO.setId(form.getId());
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

}
