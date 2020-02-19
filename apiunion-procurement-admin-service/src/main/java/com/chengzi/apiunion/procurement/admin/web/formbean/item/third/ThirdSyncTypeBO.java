/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item.third;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 */
public class ThirdSyncTypeBO extends JsonPojo {
    private int    type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
