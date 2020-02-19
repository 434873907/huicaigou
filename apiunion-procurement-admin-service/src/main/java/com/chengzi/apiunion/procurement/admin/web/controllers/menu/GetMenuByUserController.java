package com.chengzi.apiunion.procurement.admin.web.controllers.menu;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.menu.MenuBO;
import com.chengzi.apiunion.procurement.menu.Menu;
import com.chengzi.apiunion.procurement.menu.MenuManager;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户菜单
 *
 * @author 月汐
 * @date 2018/11/9 10:40
 */
public class GetMenuByUserController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<List<MenuBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        MenuManager menuManager = BeanFactory.getBean(MenuManager.class);
        PartnerRoleService roleService = BeanFactory.getBean(PartnerRoleService.class);

        String menuPermissions = roleService.getMenuPermissions();
        List<Menu> menus = new ArrayList<>(menuManager.getRoot().getMenus());
        List<MenuBO> menuBOList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuBO menuBO = MenuBO.convert(menu, menuPermissions);
            if (menuBO != null) {
                menuBOList.add(menuBO);
            }
        }

        if (CollectionUtil.isEmpty(menuBOList)) {
            return Result.buildFailResult(ErrorConstants.ERR_TOKEN_EXPIRED,"token失效");
        }

        return Result.buildSuccessResult(menuBOList);
    }
}
