package com.chengzi.apiunion.procurement.admin.partneruser.dao;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserPasswordOperate;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserQuery;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserUpdateOperate;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface PartnerUserMapper {

    int insert(PartnerUserDO partnerUserDO);

    int delete(SimpleRouteOperate<Long> id);

    PartnerUserDO selectByAccount(SimpleRouteOperate<String> account);

    List<PartnerUserDO> selectByQuery(PartnerUserQuery query);

    long countByQuery(PartnerUserQuery query);

    PartnerUserDO selectById(SimpleRouteOperate<Long> id);

    List<PartnerUserDO> selectByIds(ListRouteOperate<Long> ids);

    int updatePassword(PartnerUserPasswordOperate operate);

    int update(PartnerUserUpdateOperate operate);

    int updateLastLoginTime(SimpleRouteOperate<Long> id);

}