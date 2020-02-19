package com.chengzi.apiunion.procurement.admin.web.pojo.staticrresources;

import com.chengzi.apiunion.staticresource.constants.StaticResourcesConstants;
import com.chengzi.apiunion.staticresource.enums.ConfigTplTypeEnum;
import com.chengzi.apiunion.staticresource.pojo.StaticResourcesConfigDO;
import com.chengzi.apiunion.staticresource.service.StaticResourcesConfigService;
import com.chengzi.common.data.beans.JsonPojo;
import org.springframework.util.CollectionUtils;
import org.summercool.web.servlet.BeanFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date: 17/4/18
 */
public class StaticResourcesConfigListBO extends JsonPojo {

    /**
     * id
     */
    private long   id;

    /**
     * 配置的key
     */
    private String key;

    /**
     * 配置描述
     */
    private String desc;

    /**
     * 配置值
     */
    private String value;

    /**
     * 平台
     * 0 全部
     * 1 android
     * 2 ios
     */
    private long   platform;

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
    private int    autoConfig;

    /**
     * 值类型
     * {@link com.chengzi.apiunion.staticresource.enums.ConfigValueTypeEnum}
     */
    private int    valueType;

    /**
     * 对应类型的值
     */
    private Object objectValue;

    /**
     * 模板id
     */
    private long   tplId;

    /**
     * 配置类型
     */
    private int    configType;

    /**
     * 启用和禁用状态
     */
    private int    status;

    /**
     * 是否默认 
     */
    private int    defaultType;

    /**
     * 顺序
     */
    private int    order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getPlatform() {
        return platform;
    }

    public void setPlatform(long platform) {
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

    public Object getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(Object objectValue) {
        this.objectValue = objectValue;
    }

    public long getTplId() {
        return tplId;
    }

    public void setTplId(long tplId) {
        this.tplId = tplId;
    }

    public int getConfigType() {
        return configType;
    }

    public void setConfigType(int configType) {
        this.configType = configType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDefaultType() {
        return defaultType;
    }

    public void setDefaultType(int defaultType) {
        this.defaultType = defaultType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static List<StaticResourcesConfigListBO> convertToBOList(List<StaticResourcesConfigDO> configDOList) {

        List<StaticResourcesConfigListBO> configListBOs = new ArrayList<>();
        if (CollectionUtils.isEmpty(configDOList)) {
            return configListBOs;
        }
        for (StaticResourcesConfigDO configDO : configDOList) {
            StaticResourcesConfigListBO configListBO = convert(configDO);
            if (configListBO != null) {
                configListBOs.add(configListBO);
            }
        }
        return configListBOs;
    }

    public static StaticResourcesConfigListBO convert(StaticResourcesConfigDO configDO) {
        if (configDO == null) {
            return null;
        }

        StaticResourcesConfigService configService = BeanFactory.getBean(StaticResourcesConfigService.class);
        StaticResourcesConfigListBO configListBO = new StaticResourcesConfigListBO();
        configListBO.setId(configDO.getId());
        configListBO.setKey(configDO.getKey());
        configListBO.setDesc(configDO.getDesc());
        configListBO.setPlatform(configDO.getPlatform());
        configListBO.setMinApiV(configDO.getMinApiV());
        configListBO.setMaxApiV(configDO.getMaxApiV());
        configListBO.setMinAppV(configDO.getMinAppV());
        configListBO.setMaxAppV(configDO.getMaxAppV());
        configListBO.setAutoConfig(configDO.getAutoConfig());
        configListBO.setValueType(configDO.getValueType());
        configListBO.setStartTime(configDO.getStartTime());
        configListBO.setEndTime(configDO.getEndTime());
        configListBO.setTplId(configDO.getTplId());
        configListBO.setOrder(configDO.getConfigOrder());

        if (configDO.getTplId() == ConfigTplTypeEnum.LABEL_IMAGE.getTplId()) {
            configListBO.setConfigType(StaticResourcesConstants.getConfigKeyType(configDO.getKey()));
        }

        if (configDO.getTplId() == ConfigTplTypeEnum.MENUS.getTplId() || configDO.getTplId() == ConfigTplTypeEnum.LABEL_IMAGE.getTplId()) {
            configListBO.setDefaultType(StaticResourcesConstants.isDefaultKey(configDO.getKey()));
            configListBO.setStatus(StaticResourcesConstants.getEnableByTimes(configDO));
        }

        if (configDO.getTplId() == ConfigTplTypeEnum.USER_CENTER_MENU.getTplId()) {
            configListBO.setStatus(configDO.getConfigStatus());

        }

        // 获取对象
        configListBO.setObjectValue(configService.getResourcesTypeValue(configDO, true, true));
        return configListBO;
    }
}
