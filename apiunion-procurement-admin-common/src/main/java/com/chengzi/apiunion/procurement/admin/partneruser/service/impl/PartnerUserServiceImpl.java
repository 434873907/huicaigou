package com.chengzi.apiunion.procurement.admin.partneruser.service.impl;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.procurement.admin.partneruser.constant.PartnerUserConstant;
import com.chengzi.apiunion.procurement.admin.partneruser.dao.PartnerUserMapper;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.*;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.role.partnerrole.dao.PartnerRoleMapper;
import com.chengzi.apiunion.procurement.role.partnerrole.dao.PartnerRoleUserMapper;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleUserDO;
import com.chengzi.common.data.beans.BaseDO;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summercool.mybatis.util.StackTraceUtil;
import org.summercool.util.MD5Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 月汐
 * @date 2018/10/18 14:38
 */
@Service
public class PartnerUserServiceImpl implements PartnerUserService {

    @Autowired
    private PartnerUserMapper mapper;
    @Autowired
    private PartnerRoleUserMapper roleUserMapper;
    @Autowired
    private PartnerRoleMapper roleMapper;

    @Override
    public Result<Long> add(PartnerUserDO partnerUserDO, String roleIds) {
        partnerUserDO.setRouteId(RequestContext.getRouteId());
        partnerUserDO.setIsDeleted(BaseDO.NOT_DELETED);
        try {
            mapper.insert(partnerUserDO);
            String[] roleIdArray = roleIds.split(",");
            if (roleIdArray.length > 0) {
                PartnerRoleUserDO roleUserDO = new PartnerRoleUserDO();
                roleUserDO.setPartnerUserId(partnerUserDO.getId());
                for (String roleId : roleIdArray) {
                    roleUserDO.setRoleId(new Long(roleId));
                    roleUserMapper.insert(roleUserDO);
                }
            }
            return Result.buildSuccessResult(partnerUserDO.getId());
        } catch (Exception e) {
            if (StackTraceUtil.isDuplicateKeyException(e)) {
                return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "用户名或昵称已存在");
            }
            return Result.buildOpFailedResult("添加失败");
        }
    }

    @Override
    public int delete(Long id) {
        roleUserMapper.deleteByUserId(SimpleRouteOperate.of(id));
        return mapper.delete(SimpleRouteOperate.of(id));
    }

    @Override
    public void deletePartnerUserList(List<Long> ids) {
        for (Long id : ids) {
            roleUserMapper.deleteByUserId(SimpleRouteOperate.of(id));
            mapper.delete(SimpleRouteOperate.of(id));
        }
    }

    @Override
    public PartnerUserDO getUserByAccount(String account, String password) {
        PartnerUserDO partnerUserDO = mapper.selectByAccount(SimpleRouteOperate.of(account));
        if (partnerUserDO != null) {
            if (partnerUserDO.getPassword().equalsIgnoreCase(MD5Util.getMD5Format(password))) {
                mapper.updateLastLoginTime(SimpleRouteOperate.of(partnerUserDO.getId()));
                return partnerUserDO;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5Format("123456"));
    }
    
    @Override
    public PartnerUserDO getUserById(long id) {
        return mapper.selectById(SimpleRouteOperate.of(id));
    }

    @Override
    public List<PartnerUserRichDO> queryPartnerUsers(PartnerUserQuery query) {
        List<PartnerUserDO> userDOList = mapper.selectByQuery(query);
        List<PartnerUserRichDO> userRichDOList = new ArrayList<>();
        if (userDOList != null && userDOList.size() > 0) {
            for (PartnerUserDO userDO : userDOList) {
                List<PartnerRoleUserDO> list = roleUserMapper.selectByUserId(SimpleRouteOperate.of(userDO.getId()));
                StringBuilder roleNames = new StringBuilder();
                for (PartnerRoleUserDO roleUserDO : list) {
                    roleNames.append(roleMapper.selectById(SimpleRouteOperate.of(roleUserDO.getRoleId())).getRoleName());
                    roleNames.append(",");
                }
                if (roleNames.length() > 0) {
                    roleNames.deleteCharAt(roleNames.length() - 1);
                }
                PartnerUserRichDO userRichDO = new PartnerUserRichDO();
                BeanUtils.copyProperties(userDO, userRichDO);
                userRichDO.setRoleNames(roleNames.toString());
                userRichDOList.add(userRichDO);
            }
        }
        return userRichDOList;
    }

    @Override
    public long countPartnerUsers(PartnerUserQuery query) {
        return mapper.countByQuery(query);
    }

    @Override
    public Result<String> updateInitPassword(Long id, String password) {
        PartnerUserPasswordOperate operate = new PartnerUserPasswordOperate();
        operate.setId(id);
        operate.setPassword(MD5Util.getMD5Format(password));
        mapper.updatePassword(operate);

        return Result.buildSuccessResult("修改成功");
    }

    @Override
    public Result<String> updatePassword(Long id, String originalPassword, String newPassword) {
        PartnerUserDO partnerUserDO = mapper.selectById(SimpleRouteOperate.of(id));
        if (partnerUserDO != null && partnerUserDO.getPassword().equalsIgnoreCase(MD5Util.getMD5Format(originalPassword))) {
            PartnerUserPasswordOperate operate = new PartnerUserPasswordOperate();
            operate.setId(id);
            operate.setPassword(MD5Util.getMD5Format(newPassword));
            mapper.updatePassword(operate);
            return Result.buildSuccessResult("修改成功");
        }
        return Result.buildIllegalArgumentResult("密码错误");
    }

    @Override
    public Result<String> update(PartnerUserUpdateOperate operate, String roles) {
        try {
            List<PartnerRoleUserDO> roleUserList = roleUserMapper.selectByUserIdForUpdate(SimpleRouteOperate.of(operate.getId()));
            if (StringUtils.isNotBlank(roles)) {
                List<Long> roleIds = Arrays.stream(roles.split(",")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                for (PartnerRoleUserDO roleUser : roleUserList) {
                    long roleId = roleUser.getRoleId();
                    if (roleIds.contains(roleId)) {
                        roleUser.setIsDeleted(BaseDO.NOT_DELETED);
                        roleUserMapper.update(roleUser);
                        roleIds.remove(roleId);
                    } else {
                        roleUserMapper.delete(SimpleRouteOperate.of(roleUser.getId()));
                    }
                }
                if (roleIds.size() > 0) {
                    for (long roleId : roleIds) {
                        PartnerRoleUserDO roleUserDO = new PartnerRoleUserDO();
                        roleUserDO.setRoleId(roleId);
                        roleUserDO.setPartnerUserId(operate.getId());
                        roleUserMapper.insert(roleUserDO);
                    }
                }
            } else {
                for (PartnerRoleUserDO roleUser : roleUserList) {
                    roleUserMapper.delete(SimpleRouteOperate.of(roleUser.getId()));
                }
            }
            mapper.update(operate);
            return Result.buildSuccessResult("修改成功");
        } catch (Exception e) {
            if (StackTraceUtil.isDuplicateKeyException(e)) {
                return Result.buildIllegalArgumentResult("该昵称已存在");
            }
            return Result.buildOpFailedResult("操作失败");
        }
    }

}
