package com.chengzi.apiunion.procurement.pipeline;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.procurement.menu.Menu;
import com.chengzi.apiunion.procurement.menu.MenuManager;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.summercool.web.servlet.BeanFactory;
import org.summercool.web.servlet.pipeline.PreProcessPipeline;
import org.summercool.web.views.string.JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2018/11/5 10:58
 */
public class MenuPermissionPipeline implements PreProcessPipeline {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private int order;
    private List<String> whiteList = new ArrayList<>();

    @Override
    public boolean isPermitted(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String uri = httpServletRequest.getRequestURI().trim();
        for (String whiteUri : whiteList) {
            if (matchUri(whiteUri, uri)) {
                return true;
            }
        }
        return getMenuIdByUri(uri);
    }

    @Override
    public ModelAndView handleProcessInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView(new JsonView(Result.buildFailResult(ErrorConstants.ERR_UNAUTHORIZED, "对不起，您没有权限访问该请求，请联系管理员！").toJsonString()));
    }

    @Override
    public int getOrder() {
        return order;
    }

    private boolean getMenuIdByUri(String uri) {
        long userId = RequestContext.getUserId();
        if (userId == 0) {
            return false;
        }
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        Map<String, String> roleMap = service.getRoleMapInCache(userId);
        if (roleMap.get("@all") != null) {
            return true;
        }
        MenuManager menuManager = BeanFactory.getBean(MenuManager.class);
        for (Menu menu : menuManager.getRoot().getMenus()) {
            String menuId = getMenuIdByUri(menu, uri.substring(0, uri.length() - 3));
            if (StringUtils.isNotBlank(menuId)) {
                if (StringUtils.isNotBlank(roleMap.get(menuId))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private String getMenuIdByUri(Menu menu, String uri) {
        if (matchUri(menu.getUrl(), uri)) {
            return menu.getId();
        }
        if (StringUtils.isNotBlank(menu.getPermissions())) {
            String[] permissions = menu.getPermissions().split(",");
            for (String permission : permissions) {
                if (matchUri(permission, uri)) {
                    return menu.getId();
                }
            }
        }

        if (menu.getMenus() == null || menu.getMenus().size() == 0) {
            return null;
        }
        for (Menu subMenu : menu.getMenus()) {
            String menuId = getMenuIdByUri(subMenu, uri);
            if (StringUtils.isNotBlank(menuId)) {
                return menuId;
            }
        }

        return null;
    }

    private boolean matchUri(String pattern, String uri) {
        if (StringUtils.isBlank(pattern)) {
            return false;
        }
        return pathMatcher.match(pattern, uri);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
