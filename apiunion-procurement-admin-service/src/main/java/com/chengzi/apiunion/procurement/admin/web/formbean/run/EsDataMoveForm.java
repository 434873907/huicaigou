/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.run;

import java.util.Date;

import com.chengzi.apiunion.common.data.search.elastic.enums.SearchBizKeyEnum;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.NotNull;

/**
 * @author Kolor
 */
public class EsDataMoveForm extends BaseForm {
    @NotNull(message = "请指定bizKey确定ES集群")
    private SearchBizKeyEnum bizKey;
    @NotBlankAndNull(message = "请指定源索引sourceIndex")
    private String           sourceIndex;
    @NotBlankAndNull(message = "请指定目标索引destIndex")
    private String           destIndex;
    private Date             beginTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public SearchBizKeyEnum getBizKey() {
        return bizKey;
    }

    public void setBizKey(SearchBizKeyEnum bizKey) {
        this.bizKey = bizKey;
    }

    public String getSourceIndex() {
        return sourceIndex;
    }

    public void setSourceIndex(String sourceIndex) {
        this.sourceIndex = sourceIndex;
    }

    public String getDestIndex() {
        return destIndex;
    }

    public void setDestIndex(String destIndex) {
        this.destIndex = destIndex;
    }

}
