package com.chengzi.apiunion.procurement.admin.web.formbean.index.style;

import java.util.List;

import com.chengzi.apiunion.index.pojo.data.MagicData;
import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class MixedArrangement extends BaseFormStyle {
    /**
     * 主键id
     */
    private Long            id;
    /**
     * 展示模板 0：1-2，1：1-3，2：1-4，3：2-2，4：1l-2r，5：1t-2d，9：自定义
     */
    private int             displayTemplate;
    /**
     * 填充方式 0：填充，1：按比例
     */
    private int             scaleType;
    /**
     * 图片间距
     */
    private int             spacing;
    /**
     * 列数
     */
    private int             column;
    /**
     * 行数
     */
    private int             row;
    /**
     * 每个魔方模块-数据
     */
    @NotEmptyAndNull(message="请填写正确数据")
    private List<MagicData> magicItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDisplayTemplate() {
        return displayTemplate;
    }

    public void setDisplayTemplate(int displayTemplate) {
        this.displayTemplate = displayTemplate;
    }

    public int getScaleType() {
        return scaleType;
    }

    public void setScaleType(int scaleType) {
        this.scaleType = scaleType;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<MagicData> getMagicItems() {
        return magicItems;
    }

    public void setMagicItems(List<MagicData> magicItems) {
        this.magicItems = magicItems;
    }
}
