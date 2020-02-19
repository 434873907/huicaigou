package com.chengzi.apiunion.procurement.admin.web.pojo.partnerrole;

import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/5 19:31
 */
public class GetPartnerRoleListBO extends JsonPojo {

    private Long id;

    private String roleName;

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

    public static GetPartnerRoleListBO convert(PartnerRoleDO partnerRoleDO) {
        GetPartnerRoleListBO bo = new GetPartnerRoleListBO();
        bo.setId(partnerRoleDO.getId());
        bo.setRoleName(partnerRoleDO.getRoleName());
        return bo;
    }

}
