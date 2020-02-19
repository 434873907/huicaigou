package com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData;

import java.util.List;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class MixedArrangement extends BaseAdminStyle {
    /**
     * 主键id
     */
    private Long            id;
    /**
     * 展示模板 0：1-2，1：1-3，2：1-4，3：2-2，4：1l-2r，5：1t-2d，9：自定义
     */
    private Integer         displayTemplate;
    /**
     * 填充方式 0：填充，1：按比例
     */
    private Integer         scaleType;
    /**
     * 图片间距
     */
    private Integer         spacing;
    /**
     * 列数
     */
    private Integer         column;
    /**
     * 行数
     */
    private Integer         row;
    /**
     * 每个魔方模块-数据
     */
    private List<MagicItem> magicItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDisplayTemplate() {
        return displayTemplate;
    }

    public void setDisplayTemplate(Integer displayTemplate) {
        this.displayTemplate = displayTemplate;
    }

    public Integer getScaleType() {
        return scaleType;
    }

    public void setScaleType(Integer scaleType) {
        this.scaleType = scaleType;
    }

    public Integer getSpacing() {
        return spacing;
    }

    public void setSpacing(Integer spacing) {
        this.spacing = spacing;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public List<MagicItem> getMagicItems() {
        return magicItems;
    }

    public void setMagicItems(List<MagicItem> magicItems) {
        this.magicItems = magicItems;
    }

}
