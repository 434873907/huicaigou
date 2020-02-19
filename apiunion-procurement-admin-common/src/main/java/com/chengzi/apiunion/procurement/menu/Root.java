package com.chengzi.apiunion.procurement.menu;

import javax.xml.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2018/10/25 20:24
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {

    @XmlAttribute
    public String id = "root";

    @XmlElement(name = "menu")
    private List<Menu> menus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public Map<String, Menu> getAllLeaves() {
        Map<String, Menu> result = new HashMap<String, Menu>();

        for (Menu menu : menus) {
            if (menu.getMenus() == null) {
                result.put(menu.getId(), menu);
            } else {
                for (Menu innerMenu : menu.getMenus()) {
                    result.put(innerMenu.getId(), innerMenu);
                }
            }
        }

        return result;
    }

    public String getMenuId(String url) {
        String menuId = null;

        for (Menu menu : menus) {
            if (menu.getMenus() == null) {
                if (menu.getUrl() != null) {
                    if (url.equals(menu.getUrl())) {
                        menuId = menu.getId();
                        break;
                    }
                    continue;
                } else {
                    continue;
                }
            } else {
                for (Menu innerMenu : menu.getMenus()) {
                    if (url.equals(menu.getUrl())) {
                        menuId = innerMenu.getId();
                        break;
                    }
                }
            }
        }
        return menuId;
    }

}
