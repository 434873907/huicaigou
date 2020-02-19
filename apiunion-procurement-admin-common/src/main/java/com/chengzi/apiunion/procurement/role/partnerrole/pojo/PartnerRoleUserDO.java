package com.chengzi.apiunion.procurement.role.partnerrole.pojo;

import com.chengzi.apiunion.common.data.beans.RouteBaseDO;

/**
 * @author 月汐
 * @date 2018/11/2 16:20
 */
public class PartnerRoleUserDO extends RouteBaseDO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 商家用户ID
     */
    private Long partnerUserId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(Long partnerUserId) {
        this.partnerUserId = partnerUserId;
    }
}
