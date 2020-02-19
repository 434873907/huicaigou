package com.chengzi.apiunion.procurement.admin.web.pojo.menu;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.module.config.util.DataSourceRouteUtil;
import com.chengzi.apiunion.procurement.menu.Menu;
import com.chengzi.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 月汐
 * @date 2018/10/25 20:11
 */
public class MenuBO {

    private String id;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 链接
     */
    private String url;

    private List<MenuBO> menus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuBO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuBO> menus) {
        this.menus = menus;
    }

    public static MenuBO convert(Menu menu, String menuPermissions) {
        MenuBO menuBO = new MenuBO();
        menuBO.setId(menu.getId());
        menuBO.setTitle(menu.getTitle());
        menuBO.setUrl(menu.getUrl());
        
        String key = DataSourceRouteUtil.getKeyByRouteId(RequestContext.getRouteId());
        
        List<Menu> menus = menu.getMenus();
        List<MenuBO> menuBOList = new ArrayList<>();
        if (menus != null) {
            for (Menu row : menus) {
                // 判断是否忽略菜单项
                boolean matchExcludeKeys = false;
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(row.getExcludeKeys())) {
                    String[] keys = StringUtils.split(row.getExcludeKeys(), ',');
                    for (String k : keys) {
                        if (StringUtils.equalsIgnoreCase(key, k.trim())) {
                            matchExcludeKeys = true;
                            break;
                        }
                    }
                }
                if (matchExcludeKeys) {
                    continue;
                }
                MenuBO bo = MenuBO.convert(row, menuPermissions);
                if (bo != null) {
                    menuBOList.add(bo);
                }
            }
        }
        if (!menuPermissions.contains("@all") && !menuPermissions.contains("," + menu.getId() + ",")) {
            return null;
        }
        menuBO.setMenus(menuBOList);
        return menuBO;
    }
}
