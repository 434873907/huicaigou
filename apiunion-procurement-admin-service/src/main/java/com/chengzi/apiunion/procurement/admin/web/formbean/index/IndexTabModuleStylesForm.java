package com.chengzi.apiunion.procurement.admin.web.formbean.index;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 苏子
 * @date 2018年10月26日
 */
public class IndexTabModuleStylesForm extends BaseForm {

    /**
     * 布局类型 
     * {@link} LayoutTypeEnum
     */
    private int layoutType;

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }

}
