package com.chengzi.apiunion.procurement.admin.manageuser.dao;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserPasswordOperate;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserQuery;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserUpdateOperate;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface ManageUserMapper {

    int insert(ManageUserDO manageUserDO);

    ManageUserDO selectById(SimpleRouteOperate<Long> id);

    ManageUserDO selectByAccount(SimpleRouteOperate<String> account);

    List<ManageUserDO> selectByUserName(ManageUserQuery query);

    long selectByUserNameCount(ManageUserQuery query);

    int updatePasswordById(ManageUserPasswordOperate operate);

    int updateById(ManageUserUpdateOperate operate);

    int updateLastLoginTime(SimpleRouteOperate<Long> id);

    int delete(SimpleRouteOperate<Long> id);

}