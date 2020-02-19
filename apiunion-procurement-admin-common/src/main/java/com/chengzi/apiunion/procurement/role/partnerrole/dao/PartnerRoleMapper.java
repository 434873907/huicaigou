package com.chengzi.apiunion.procurement.role.partnerrole.dao;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.beans.RouteOperate;
import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/11/2 15:20
 */
@MapperScan
public interface PartnerRoleMapper {

    int insert(PartnerRoleDO partnerRoleDO);

    int delete(SimpleRouteOperate<Long> id);

    int deleteBatch(ListRouteOperate<Long> ids);

    PartnerRoleDO selectById(SimpleRouteOperate<Long> id);

    List<PartnerRoleDO> selectByIds(ListRouteOperate<Long> ids);

    List<PartnerRoleDO> selectPartnerRoleList(RouteOperate operate);

    List<PartnerRoleDO> selectByQuery(RouteQuery query);

    long countByQuery(RouteQuery query);

    int update(PartnerRoleDO partnerRoleDO);

}
