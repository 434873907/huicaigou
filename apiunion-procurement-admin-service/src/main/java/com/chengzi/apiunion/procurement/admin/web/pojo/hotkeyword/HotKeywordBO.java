package com.chengzi.apiunion.procurement.admin.web.pojo.hotkeyword;

import com.chengzi.apiunion.hotkeyword.pojo.HotKeywordDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/12/4 14:51
 */
public class HotKeywordBO extends JsonPojo {

    private long id;

    /**
     * 热门关键词名称
     */
    private String name;

    /**
     * 跳转类型
     */
    private int jumpType;

    /**
     * 跳转链接
     */
    private String jumpValue;

    /**
     * 启用状态
     */
    private int status;

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

    public int getJumpType() {
        return jumpType;
    }

    public void setJumpType(int jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpValue() {
        return jumpValue;
    }

    public void setJumpValue(String jumpValue) {
        this.jumpValue = jumpValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static HotKeywordBO convert(HotKeywordDO hotKeywordDO) {
        HotKeywordBO bo = new HotKeywordBO();
        bo.setId(hotKeywordDO.getId());
        bo.setName(hotKeywordDO.getName());
        bo.setJumpType(hotKeywordDO.getJumpType());
        bo.setJumpValue(hotKeywordDO.getJumpValue());
        bo.setStatus(hotKeywordDO.getStatus());
        return bo;
    }
}
