package com.chengzi.apiunion.procurement.admin.manageuser.service;

import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.common.data.validate.Result;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/17 16:51
 */
public interface ManageUserService {

    Result<Long> addManageUser(ManageUserDO manageUserDO);

    ManageUserDO getUserByAccount(String account, String password);

    Result<Void> updatePasswordById(Long id, String originalPassword, String newPassword);

    Result<String> updateInitPassword(Long id);

    List<ManageUserDO> queryManageUserList(String nickName, Integer offset, Integer limit);

    long getManageUserListSize(String nickName);

    Result<Long> updateById(Long id, String nickName, String logoUrl, String password);

    int deleteOne(Long id);

    void deleteManagerUserList(List<Long> ids);

}
