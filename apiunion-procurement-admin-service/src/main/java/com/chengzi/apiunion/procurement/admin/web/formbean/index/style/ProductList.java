package com.chengzi.apiunion.procurement.admin.web.formbean.index.style;

import java.util.List;

import com.chengzi.apiunion.common.data.style.ItemStyle;
import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.apiunion.index.enums.ProductContentEnum;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;

import net.sf.oval.constraint.Min;

/** 
* @author 苏子 
* @date 2018年10月23日
*/
public class ProductList extends BaseFormStyle {
    /**
     * 主键id
     */
    private Long          id;
    /**
     * 列表样式，0：单列，1：双列
     */
    private int           listStyleType;
    /**
     * 图片宽度，单列显示时所需字段
     */
    private Integer       imgWidth;
    /**
     * 行间距
     */
    private Integer       rowSpacing;
    /**
     * 列间距
     */
    private Integer       columnSpacing;
    /**
     * 购物车
     */
    private Image         shopCart;
    /**
     * 商品样式
     */
    private ItemStyle     itemStyle;
    /**
     * 显示内容
     * {@link ProductContentEnum } 建一个枚举类
     */
    private List<Integer> displayContent;
    /**
     * 数据类型
     */
    @Min(value=1,message="请选择数据类型")
    private int           dataType;
    /**
     * 数据
     */
    @NotBlankAndNull(message="请填写正确数据")
    private String        value;
    /**
     * 一级类目
     */
    private Long firstCate;
    /**
     * 二级类目
     */
    private Long secondCate;
    /**
     * 三级类目
     */
    private Long thirdCate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getListStyleType() {
        return listStyleType;
    }

    public void setListStyleType(int listStyleType) {
        this.listStyleType = listStyleType;
    }

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public Integer getRowSpacing() {
        return rowSpacing;
    }

    public void setRowSpacing(Integer rowSpacing) {
        this.rowSpacing = rowSpacing;
    }

    public Integer getColumnSpacing() {
        return columnSpacing;
    }

    public void setColumnSpacing(Integer columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    public Image getShopCart() {
        return shopCart;
    }

    public void setShopCart(Image shopCart) {
        this.shopCart = shopCart;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
    }

    public List<Integer> getDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(List<Integer> displayContent) {
        this.displayContent = displayContent;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getFirstCate() {
        return firstCate;
    }

    public void setFirstCate(Long firstCate) {
        this.firstCate = firstCate;
    }

    public Long getSecondCate() {
        return secondCate;
    }

    public void setSecondCate(Long secondCate) {
        this.secondCate = secondCate;
    }

    public Long getThirdCate() {
        return thirdCate;
    }

    public void setThirdCate(Long thirdCate) {
        this.thirdCate = thirdCate;
    }
}
