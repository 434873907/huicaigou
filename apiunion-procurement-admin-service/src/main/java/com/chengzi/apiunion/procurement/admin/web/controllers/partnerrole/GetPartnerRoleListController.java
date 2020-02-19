package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnerrole.GetPartnerRoleListBO;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有角色
 *
 * @author 月汐
 * @date 2018/11/5 19:30
 */
public class GetPartnerRoleListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<List<GetPartnerRoleListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        List<PartnerRoleDO> partnerRoleList = service.getPartnerRoleList();
        List<GetPartnerRoleListBO> resultList = new ArrayList<>();
        for (PartnerRoleDO partnerRoleDO : partnerRoleList) {
            resultList.add(GetPartnerRoleListBO.convert(partnerRoleDO));
        }
        return Result.buildSuccessResult(resultList);
    }

}
