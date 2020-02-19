package com.chengzi.apiunion.procurement.admin.web.pojo.staticrresources;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2017/5/18
 */
public class ConfigTplBO {

    private long   tplId;

    private String tplName;

    private String currentKey;

    public long getTplId() {
        return tplId;
    }

    public void setTplId(long tplId) {
        this.tplId = tplId;
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName;
    }

    public String getCurrentKey() {
        return currentKey;
    }

    public void setCurrentKey(String currentKey) {
        this.currentKey = currentKey;
    }
}
