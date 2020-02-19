package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.apiunion.brand.enums.BrandStatusEnum;
import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2018/10/15 15:09
 */
public class QueryBrandListForm extends BasePageForm {

    /**
     * 品牌名
     */
    private String name;

    /**
     * 品牌英文名首字母
     */
    private String letter;

    /**
     * 启用状态
     */
    private BrandStatusEnum status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public BrandStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BrandStatusEnum status) {
        this.status = status;
    }

}
