package com.chengzi.apiunion.procurement.role.partnerrole.pojo;

import com.chengzi.apiunion.common.data.beans.RouteBaseDO;

/**
 * @author 月汐
 * @date 2018/11/2 15:21
 */
public class PartnerRoleDO extends RouteBaseDO {

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 平台
     */
    private Integer platform;

    /**
     * 菜单权限
     */
    private String menuPermissions;

    /**
     * 功能权限
     */
    private String functionPermissions;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getMenuPermissions() {
        return menuPermissions;
    }

    public void setMenuPermissions(String menuPermissions) {
        this.menuPermissions = menuPermissions;
    }

    public String getFunctionPermissions() {
        return functionPermissions;
    }

    public void setFunctionPermissions(String functionPermissions) {
        this.functionPermissions = functionPermissions;
    }
}
