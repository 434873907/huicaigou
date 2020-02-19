package com.chengzi.apiunion.procurement.role.partnerrole.service;

import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.common.data.validate.Result;

import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2018/11/2 15:21
 */
public interface PartnerRoleService {

    List<PartnerRoleDO> getRoleByUserId(Long userId);

    Map<String, String> getRoleMap(Long userId);

    Map<String, String> getRoleMapInCache(Long userId);

    List<String> getFunctionPermissions(Long userId);

    List<String> getFunctionPermissionsInCache(Long userId);

    String getUserNameByRoleId(Long roleId);

    Result<String> add(PartnerRoleDO partnerRoleDO);

    Result<String> delete(Long id);

    Result<String> deleteBatch(List<Long> ids);

    PartnerRoleDO getById(Long id);

    List<PartnerRoleDO> queryList(RouteQuery query);

    Long countByQuery(RouteQuery query);

    Result<String> update(PartnerRoleDO partnerRoleDO);

    List<PartnerRoleDO> getPartnerRoleList();

    String getMenuPermissions();

    String getRoleNamesByUserId(Long userId);

}
