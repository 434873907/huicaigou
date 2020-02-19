package com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/5 17:32
 */
public class UpdatePartnerRoleForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的角色")
    private long id;

    @NotBlankAndNull(message = "角色名不能为空")
    private String roleName;

    /**
     * 菜单权限
     */
    private String menuPermissions;

    /**
     * 功能权限
     */
    private String functionPermissions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
