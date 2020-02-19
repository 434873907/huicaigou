package com.chengzi.apiunion.procurement.admin.manageuser.service.impl;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.procurement.admin.manageuser.constant.ManageUserConstant;
import com.chengzi.apiunion.procurement.admin.manageuser.dao.ManageUserMapper;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserPasswordOperate;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserQuery;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserUpdateOperate;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.common.data.beans.BaseDO;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.summercool.mybatis.util.StackTraceUtil;
import org.summercool.util.MD5Util;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/17 16:51
 */
@Service
public class ManageUserServiceImpl implements ManageUserService {

    @Autowired
    private ManageUserMapper mapper;

    @Override
    public Result<Long> addManageUser(ManageUserDO manageUserDO) {
        manageUserDO.setRouteId(RequestContext.getRouteId());
        manageUserDO.setIsDeleted(BaseDO.NOT_DELETED);
        try {
            mapper.insert(manageUserDO);
            return Result.buildSuccessResult(manageUserDO.getId());
        } catch (Exception e) {
            if (StackTraceUtil.isDuplicateKeyException(e)) {
                return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "用户名或昵称已存在");
            }
            return Result.buildOpFailedResult("添加失败");
        }
    }

    @Override
    public ManageUserDO getUserByAccount(String account, String password) {
        ManageUserDO manageUserDO = mapper.selectByAccount(SimpleRouteOperate.of(account));
        if (manageUserDO != null) {
            if (manageUserDO.getPassword().equals(MD5Util.getMD5Format(password))) {
                mapper.updateLastLoginTime(SimpleRouteOperate.of(manageUserDO.getId()));
                return manageUserDO;
            }
        }
        return null;
    }

    @Override
    public Result<Void> updatePasswordById(Long id, String originalPassword, String newPassword) {
        ManageUserDO manageUserDO = mapper.selectById(SimpleRouteOperate.of(id));

        if (manageUserDO.getPassword().equals(MD5Util.getMD5Format(originalPassword))) {
            ManageUserPasswordOperate operate = new ManageUserPasswordOperate();
            operate.setId(id);
            operate.setPassword(MD5Util.getMD5Format(newPassword));
            mapper.updatePasswordById(operate);
            return Result.buildSuccessResult(null);
        } else {
            return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "密码错误");
        }
    }

    @Override
    public Result<String> updateInitPassword(Long id) {
        ManageUserPasswordOperate operate = new ManageUserPasswordOperate();
        operate.setId(id);
        operate.setPassword(MD5Util.getMD5Format(ManageUserConstant.INIT_PASSWORD));
        mapper.updatePasswordById(operate);
        return Result.buildSuccessResult("修改成功");
    }

    @Override
    public List<ManageUserDO> queryManageUserList(String nickName, Integer offset, Integer limit) {
        ManageUserQuery query = new ManageUserQuery();
        query.setNickName(nickName);
        query.setOffset(offset);
        query.setLimit(limit);
        return mapper.selectByUserName(query);
    }

    @Override
    public long getManageUserListSize(String nickName) {
        ManageUserQuery query = new ManageUserQuery();
        query.setNickName(nickName);
        return mapper.selectByUserNameCount(query);
    }

    @Override
    public Result<Long> updateById(Long id, String nickName, String logoUrl, String password) {
        ManageUserUpdateOperate operate = new ManageUserUpdateOperate();
        operate.setId(id);
        operate.setNickName(nickName);
        operate.setLogoUrl(logoUrl);
        operate.setPassword(MD5Util.getMD5Format(password));
        try {
            mapper.updateById(operate);
            return Result.buildSuccessResult(id);
        } catch (Exception e) {
            if (StackTraceUtil.isDuplicateKeyException(e)) {
                return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "该昵称已存在");
            }
            return Result.buildOpFailedResult("修改失败");
        }
    }

    @Override
    public int deleteOne(Long id) {
        return mapper.delete(SimpleRouteOperate.of(id));
    }

    @Override
    public void deleteManagerUserList(List<Long> ids) {
        for (long id : ids) {
            mapper.delete(SimpleRouteOperate.of(id));
        }
    }

}
