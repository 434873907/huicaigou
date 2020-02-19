package com.chengzi.apiunion.procurement.admin.web.formbean.staticresource;

import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;

import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2017/5/18
 */
public class AddStaticResourcesConfigForm extends BaseForm {

    /**
     * 配置的key
     */
    @NotEmptyAndNull(message = "key不能为空")
    private String key;

    /**
     * 配置描述
     */
    @NotEmptyAndNull(message = "配置说明不能为空")
    private String desc;

    /**
     * 配置值
     */
    @NotEmptyAndNull(message = "配置的值不能为空")
    private String value;

    /**
     * 平台
     * 0 全部
     * 1 android
     * 2 ios
     * 4 h5
     * @see com.chengzi.apiunion.common.data.enums.PlatformEnum
     */
    private int    platform;

    /**
     * 支持最小的app版本
     */
    private int    minAppV;

    /**
     * 支持最大的app版本
     */
    private int    maxAppV;

    /**
     * 支持的最小apiv
     */
    private int    minApiV;

    /**
     * 支持的最大apiV
     */
    private int    maxApiV;

    /**
     * 开始时间
     */
    private Date   startTime;

    /**
     * 结束时间
     */
    private Date   endTime;

    /**
     * 自动配置
     * 0 否
     * 1 是
     */
    @Range(min = 0, max = 1, message = "自动配置参数错误，必须为[0,1]")
    private int    autoConfig;

    /**
     * 值类型
     * @see com.chengzi.apiunion.staticresource.enums.ConfigValueTypeEnum
     */
    @Range(min = 1, max = 6, message = "值类型范围为[0,6]")
    private int    valueType;

    /**
     * 模板id
     * @see com.chengzi.apiunion.staticresource.enums.ConfigTplTypeEnum
     */
    @Min(value = 0, message = "模板id必须大于等于0")
    private long   tplId;

    /**
     * 是否展示
     * 0 否
     * 1 是
     */
    @Range(min = 0, max = 1, message = "是否在后台展示状态只能为[0,1]")
    private int    show;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getMinAppV() {
        return minAppV;
    }

    public void setMinAppV(int minAppV) {
        this.minAppV = minAppV;
    }

    public int getMaxAppV() {
        return maxAppV;
    }

    public void setMaxAppV(int maxAppV) {
        this.maxAppV = maxAppV;
    }

    public int getMinApiV() {
        return minApiV;
    }

    public void setMinApiV(int minApiV) {
        this.minApiV = minApiV;
    }

    public int getMaxApiV() {
        return maxApiV;
    }

    public void setMaxApiV(int maxApiV) {
        this.maxApiV = maxApiV;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getAutoConfig() {
        return autoConfig;
    }

    public void setAutoConfig(int autoConfig) {
        this.autoConfig = autoConfig;
    }

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public long getTplId() {
        return tplId;
    }

    public void setTplId(long tplId) {
        this.tplId = tplId;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }
}
