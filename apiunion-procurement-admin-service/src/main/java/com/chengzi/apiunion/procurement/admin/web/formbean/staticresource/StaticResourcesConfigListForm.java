package com.chengzi.apiunion.procurement.admin.web.formbean.staticresource;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date: 17/4/18
 */
public class StaticResourcesConfigListForm extends BaseForm {

    private int    show  = 1;

    private long   tplId = -1;

    private String key;

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public long getTplId() {
        return tplId;
    }

    public void setTplId(long tplId) {
        this.tplId = tplId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
