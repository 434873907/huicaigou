package com.chengzi.apiunion.procurement.menu;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author 月汐
 * @date 2018/10/25 20:25
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Menu {

    @XmlAttribute
    private String     id;

    /**
     * 菜单标题
     */
    @XmlAttribute
    private String     title;

    /**
     * 链接
     */
    @XmlAttribute
    private String     url;

    @XmlElement(name = "menu")
    private List<Menu> menus;

    private String     permissions;

    /**
     * 排除的规则KEY列表，这些规则下隐藏当前菜单
     */
    private String     excludeKeys;

    public String getExcludeKeys() {
        return excludeKeys;
    }

    public void setExcludeKeys(String excludeKeys) {
        this.excludeKeys = excludeKeys;
    }

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

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
