package com.chengzi.apiunion.procurement.admin.web.controllers.menu;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.menu.MenuBO;
import com.chengzi.apiunion.procurement.menu.Menu;
import com.chengzi.apiunion.procurement.menu.MenuManager;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有菜单
 *
 * @author 月汐
 * @date 2018/10/25 20:11
 */
public class GetMenuController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<List<MenuBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        MenuManager menuManager = BeanFactory.getBean(MenuManager.class);

        List<Menu> menus = new ArrayList<>(menuManager.getRoot().getMenus());
        List<MenuBO> menuBOList = new ArrayList<>();
        for (Menu menu : menus) {
            MenuBO menuBO = MenuBO.convert(menu, "@all");
            if (menuBO != null) {
                menuBOList.add(menuBO);
            }
        }
        return Result.buildSuccessResult(menuBOList);
    }

}
