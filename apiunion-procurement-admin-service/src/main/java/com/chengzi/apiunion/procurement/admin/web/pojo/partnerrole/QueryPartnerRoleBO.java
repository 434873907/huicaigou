package com.chengzi.apiunion.procurement.admin.web.pojo.partnerrole;

import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;

/**
 * @author 月汐
 * @date 2018/11/5 16:34
 */
public class QueryPartnerRoleBO {

    private Long id;

    private String roleName;

    private String userName;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static QueryPartnerRoleBO convert(PartnerRoleDO partnerRoleDO, String userName) {
        QueryPartnerRoleBO bo = new QueryPartnerRoleBO();
        bo.setId(partnerRoleDO.getId());
        bo.setRoleName(partnerRoleDO.getRoleName());
        bo.setUserName(userName);
        return bo;
    }
}
