package com.chengzi.apiunion.procurement.admin.web.pojo.filefolder;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/2/11 11:37
 */
public class ImageBO extends JsonPojo {

    private long id;

    private String name;

    @ImageDecorater(ImageBizType.ADMIN_IMAGE_MANAGE)
    private String imageUrl;

    private String imageUrlMd5;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlMd5() {
        return imageUrlMd5;
    }

    public void setImageUrlMd5(String imageUrlMd5) {
        this.imageUrlMd5 = imageUrlMd5;
    }
}
