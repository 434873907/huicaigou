package com.chengzi.apiunion.procurement.admin.web.pojo.index;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.data.style.TextStyle;
import com.chengzi.apiunion.index.enums.LayoutTypeEnum;
import com.chengzi.apiunion.index.enums.PartStyleEnum;
import com.chengzi.apiunion.index.pojo.IndexTabModuleStyleDO;
import com.chengzi.apiunion.index.pojo.stylemodule.ClosetLayoutOtherStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.GalleryModuleDisplayStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.OtherStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.ProductListOtherStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.SlidingPageDisplayStyle;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;

/**
 * @author 苏子
 * @date 2018年10月26日
 */
public class IndexTabModuleStylesBO extends JsonPojo{
    /**
     * 主键ID
     */
    private long id;
    /**
     * 局部，哪一部分
     * {@link PartStyleEnum}
     */
    private int part;
    /**
     * 样式名称
     */
    private String styleName;
    /**
     * 样式
     */
    private Object style;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getPart() {
        return part;
    }
    public void setPart(int part) {
        this.part = part;
    }
    public String getStyleName() {
        return styleName;
    }
    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
    public Object getStyle() {
        return style;
    }
    public void setStyle(Object style) {
        this.style = style;
    }
    
    /*--------------------------------------------------------*/
    public static List<IndexTabModuleStylesBO> converter(List<IndexTabModuleStyleDO> moduleStyleList){
        if (CollectionUtil.isEmpty(moduleStyleList)) {
            return null;
        }
        List<IndexTabModuleStylesBO> list = new ArrayList<>();
        for (IndexTabModuleStyleDO moduleStyle : moduleStyleList) {
            IndexTabModuleStylesBO bo = converter(moduleStyle);
            if (bo != null) {
                list.add(bo);
            }
        }
        return list;
    }
    
    public static IndexTabModuleStylesBO converter(IndexTabModuleStyleDO moduleStyle){
        if (moduleStyle == null) {
            return null;
        }
        int part = moduleStyle.getPart();
        int layoutType = moduleStyle.getLayoutType();
        IndexTabModuleStylesBO bo = new IndexTabModuleStylesBO();
        bo.setId(moduleStyle.getId());
        bo.setPart(part);
        bo.setStyleName(moduleStyle.getStyleName());
        
        String style = moduleStyle.getStyle();
        switch (EnumUtil.parse(PartStyleEnum.class, part)) {
        case OTHER_STYLE:
            switch (EnumUtil.parse(LayoutTypeEnum.class,layoutType)) {
            case CLOSET_LAYOUT:
                bo.setStyle(JSONObject.parseObject(style, ClosetLayoutOtherStyle.class));
                break;
            case PRODUCT_LIST:
                bo.setStyle(JSONObject.parseObject(style, ProductListOtherStyle.class));
                break;
            default:
                bo.setStyle(JSONObject.parseObject(style, OtherStyle.class));
                break;
            }
            break;
        case TITLE_STYLE:
            bo.setStyle(JSONObject.parseObject(style, TextStyle.class));
            break;
        case DISPLAY_STYLE:
            switch (EnumUtil.parse(LayoutTypeEnum.class,layoutType)) {
            case SLIDING_PAGE:
                bo.setStyle(JSONObject.parseObject(style, SlidingPageDisplayStyle.class));
                break;
            case GALLERY_MODULE:
                bo.setStyle(JSONObject.parseObject(style, GalleryModuleDisplayStyle.class));
                break;
            default:
                break;
            }
        default:
            break;
        }
        return bo;
    }
    
}
