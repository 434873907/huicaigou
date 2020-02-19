package com.chengzi.apiunion.procurement.admin.web.pojo.groupbuying;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/2/18 20:26
 */
public class QueryGroupBuyingBO extends JsonPojo {

    private long id;

    private String name;

    private String banner;

    private String itemName;

    private long itemId;

    private String actTime;

    private String actStatusDesc;

    private int status;

    private int participateNum;

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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getActTime() {
        return actTime;
    }

    public void setActTime(String actTime) {
        this.actTime = actTime;
    }

    public String getActStatusDesc() {
        return actStatusDesc;
    }

    public void setActStatusDesc(String actStatusDesc) {
        this.actStatusDesc = actStatusDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getParticipateNum() {
        return participateNum;
    }

    public void setParticipateNum(int participateNum) {
        this.participateNum = participateNum;
    }
}
