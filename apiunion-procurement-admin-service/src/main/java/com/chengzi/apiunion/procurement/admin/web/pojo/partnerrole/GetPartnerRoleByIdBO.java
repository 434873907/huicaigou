package com.chengzi.apiunion.procurement.admin.web.pojo.partnerrole;

import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/5 15:50
 */
public class GetPartnerRoleByIdBO extends JsonPojo {

    private Long id;

    private String roleName;

    private Integer platform;

    private String menuPermissions;

    private String functionPermissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public static GetPartnerRoleByIdBO convert(PartnerRoleDO partnerRoleDO) {
        GetPartnerRoleByIdBO bo = new GetPartnerRoleByIdBO();
        bo.setId(partnerRoleDO.getId());
        bo.setRoleName(partnerRoleDO.getRoleName());
        bo.setPlatform(partnerRoleDO.getPlatform());
        bo.setMenuPermissions(partnerRoleDO.getMenuPermissions());
        bo.setFunctionPermissions(partnerRoleDO.getFunctionPermissions());
        return bo;
    }

}
