package com.chengzi.apiunion.procurement.role.partnerrole.service.impl;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.beans.RouteOperate;
import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.data.cache.KVLocalCache;
import com.chengzi.apiunion.procurement.admin.partneruser.dao.PartnerUserMapper;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.role.partnerrole.dao.PartnerRoleMapper;
import com.chengzi.apiunion.procurement.role.partnerrole.dao.PartnerRoleUserMapper;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleUserDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summercool.mybatis.util.StackTraceUtil;

import java.util.*;

/**
 * @author 月汐
 * @date 2018/11/2 15:22
 */
@Service
public class PartnerRoleServiceImpl implements PartnerRoleService {
    
    @Autowired
    private PartnerRoleMapper roleMapper;
    @Autowired
    private PartnerRoleUserMapper roleUserMapper;
    @Autowired
    private PartnerUserMapper userMapper;

    private KVLocalCache<Long, Map<String, String>> cache =
            new KVLocalCache<>(100, 300, this::getRoleMap, null);
    private KVLocalCache<Long, List<String>> functionCache =
            new KVLocalCache<>(100, 300, this::getFunctionPermissions, null);

    @Override
    public List<PartnerRoleDO> getRoleByUserId(Long userId) {
        List<PartnerRoleUserDO> partnerRoleUserList = roleUserMapper.selectByUserId(SimpleRouteOperate.of(userId));

        if (CollectionUtil.isEmpty(partnerRoleUserList)) {
            return null;
        }

        Collection<Long> roleIds = CollectionUtil.getDisctinctFieldValueList(partnerRoleUserList, "roleId");
        Set<Long> queryIds = new HashSet<>(roleIds);
        return roleMapper.selectByIds(ListRouteOperate.of(new ArrayList<>(queryIds)));
    }

    @Override
    public Map<String, String> getRoleMap(Long userId) {
        List<PartnerRoleDO> roleList = getRoleByUserId(userId);
        Map<String, String> roleMap = new HashMap<>();
        for (PartnerRoleDO role : roleList) {
            String[] roles = role.getMenuPermissions().split(",");
            for (String str : roles) {
                roleMap.put(str, str);
            }
        }
        return roleMap;
    }

    @Override
    public Map<String, String> getRoleMapInCache(Long userId) {
        return cache.get(userId);
    }

    @Override
    public List<String> getFunctionPermissions(Long userId) {
        List<PartnerRoleDO> roleList = getRoleByUserId(userId);
        List<String> functionUrls = new ArrayList<>();
        for (PartnerRoleDO role : roleList) {
            functionUrls.addAll(CollectionUtil.splitAndTrim(role.getFunctionPermissions(), ","));
        }
        return functionUrls;
    }

    @Override
    public List<String> getFunctionPermissionsInCache(Long userId) {
        return functionCache.get(userId);
    }

    @Override
    public String getUserNameByRoleId(Long roleId) {
        List<PartnerRoleUserDO> list = roleUserMapper.selectByRoleId(SimpleRouteOperate.of(roleId));
        if (list == null || list.size() == 0) {
            return null;
        }
        Collection<Long> userIds = CollectionUtil.getDisctinctFieldValueList(list, "partnerUserId");
        Set<Long> queryIds = new HashSet<>(userIds);
        List<PartnerUserDO> userList = userMapper.selectByIds(ListRouteOperate.of(new ArrayList<>(queryIds)));
        if (userList != null &&userList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (PartnerUserDO user : userList) {
                sb.append(user.getNickName());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else {
            return null;
        }
    }

    @Override
    public Result<String> add(PartnerRoleDO partnerRoleDO) {
        try {
            roleMapper.insert(partnerRoleDO);
            return Result.buildSuccessResult("添加成功");
        } catch (Exception e) {
            if (StackTraceUtil.isDuplicateKeyException(e)) {
                return Result.buildIllegalArgumentResult("该角色名已存在");
            }
            return Result.buildOpFailedResult("添加失败");
        }
    }

    @Override
    public Result<String> delete(Long id) {
        roleUserMapper.deleteByRoleId(SimpleRouteOperate.of(id));
        roleMapper.delete(SimpleRouteOperate.of(id));
        return Result.buildSuccessResult("删除成功");
    }

    @Override
    public Result<String> deleteBatch(List<Long> ids) {
        roleUserMapper.deleteByRoleIds(ListRouteOperate.of(ids));
        roleMapper.deleteBatch(ListRouteOperate.of(ids));
        return Result.buildSuccessResult("删除成功");
    }

    @Override
    public PartnerRoleDO getById(Long id) {
        return roleMapper.selectById(SimpleRouteOperate.of(id));
    }

    @Override
    public List<PartnerRoleDO> queryList(RouteQuery query) {
        return roleMapper.selectByQuery(query);
    }

    @Override
    public Long countByQuery(RouteQuery query) {
        return roleMapper.countByQuery(query);
    }

    @Override
    public Result<String> update(PartnerRoleDO partnerRoleDO) {
        try {
            roleMapper.update(partnerRoleDO);
            return Result.buildSuccessResult("修改成功");
        } catch (Exception e) {
            if (StackTraceUtil.isDuplicateKeyException(e)) {
                return Result.buildIllegalArgumentResult("该角色名已存在");
            }
            return Result.buildOpFailedResult("修改失败");
        }
    }

    @Override
    public List<PartnerRoleDO> getPartnerRoleList() {
        return roleMapper.selectPartnerRoleList(new RouteOperate());
    }

    @Override
    public String getMenuPermissions() {
        List<PartnerRoleUserDO> list = roleUserMapper.selectByUserId(SimpleRouteOperate.of(RequestContext.getUserId()));
        StringBuilder menuPermissions = new StringBuilder(",");
        for (PartnerRoleUserDO roleUserDO : list) {
            PartnerRoleDO roleDO = roleMapper.selectById(SimpleRouteOperate.of(roleUserDO.getRoleId()));
            menuPermissions.append(roleDO.getMenuPermissions());
            menuPermissions.append(",");
        }
        return menuPermissions.toString();
    }

    @Override
    public String getRoleNamesByUserId(Long userId) {
        List<PartnerRoleUserDO> list = roleUserMapper.selectByUserId(SimpleRouteOperate.of(userId));
        StringBuilder roleIds = new StringBuilder();
        for (PartnerRoleUserDO roleUserDO : list) {
            PartnerRoleDO partnerRoleDO = roleMapper.selectById(SimpleRouteOperate.of(roleUserDO.getRoleId()));
            roleIds.append(partnerRoleDO.getId());
            roleIds.append(",");
        }
        if (roleIds.length() > 0) {
            roleIds.deleteCharAt(roleIds.length() - 1);
        }
        return roleIds.toString();
    }

}
