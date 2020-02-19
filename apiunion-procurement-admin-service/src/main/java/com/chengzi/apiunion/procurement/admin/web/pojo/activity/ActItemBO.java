package com.chengzi.apiunion.procurement.admin.web.pojo.activity;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;

/**
 * @author 苏子
 * @date 2018年11月14日
 */
public class ActItemBO {

    private long   id;
    private String name;
    @ImageDecorater(ImageBizType.PLACE_HOLDER)
    private String mainImg;
    private String price;

    public ActItemBO() {

    }

    public ActItemBO(long id, String name, String mainImg, String price) {
        this.id = id;
        this.name = name;
        this.mainImg = mainImg;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
