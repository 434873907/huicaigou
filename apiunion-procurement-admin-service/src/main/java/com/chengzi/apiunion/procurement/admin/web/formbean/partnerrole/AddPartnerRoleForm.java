package com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2018/11/5 15:07
 */
public class AddPartnerRoleForm extends BaseForm {

    @NotBlankAndNull(message = "角色名不能为空")
    private String roleName;

    /**
     * 功能权限,针对添加、修改和删除功能（ex:add**）
     */
    private String functionPermissions;

    /**
     * 菜单权限
     */
    private String menuPermissions;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getFunctionPermissions() {
        return functionPermissions;
    }

    public void setFunctionPermissions(String functionPermissions) {
        this.functionPermissions = functionPermissions;
    }

    public String getMenuPermissions() {
        return menuPermissions;
    }

    public void setMenuPermissions(String menuPermissions) {
        this.menuPermissions = menuPermissions;
    }
}
