package com.chengzi.apiunion.procurement.admin.partneruser.service;

import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserQuery;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserRichDO;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserUpdateOperate;
import com.chengzi.common.data.validate.Result;

import java.util.List;

public interface PartnerUserService {

    Result<Long> add(PartnerUserDO partnerUserDO, String roleIds);

    int delete(Long id);

    void deletePartnerUserList(List<Long> ids);

    PartnerUserDO getUserById(long id);
    
    PartnerUserDO getUserByAccount(String account, String password);

    List<PartnerUserRichDO> queryPartnerUsers(PartnerUserQuery query);

    long countPartnerUsers(PartnerUserQuery query);

    Result<String> updateInitPassword(Long id, String password);

    Result<String> updatePassword(Long id, String originalPassword, String newPassword);

    Result<String> update(PartnerUserUpdateOperate operate, String roles);

}
