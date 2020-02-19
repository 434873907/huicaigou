package com.chengzi.apiunion.procurement.role.partnerrole.dao;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleUserDO;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/11/2 16:10
 */
@MapperScan
public interface PartnerRoleUserMapper {

    int insert(PartnerRoleUserDO partnerRoleUserDO);

    int delete(SimpleRouteOperate<Long> id);

    int deleteByUserId(SimpleRouteOperate<Long> userId);

    int deleteByRoleId(SimpleRouteOperate<Long> roleId);

    int deleteByRoleIds(ListRouteOperate<Long> ids);

    int update(PartnerRoleUserDO partnerRoleUserDO);

    List<PartnerRoleUserDO> selectByUserId(SimpleRouteOperate<Long> userId);

    List<PartnerRoleUserDO> selectByRoleId(SimpleRouteOperate<Long> roleId);

    List<PartnerRoleUserDO> selectByUserIdForUpdate(SimpleRouteOperate<Long> userId);

}
