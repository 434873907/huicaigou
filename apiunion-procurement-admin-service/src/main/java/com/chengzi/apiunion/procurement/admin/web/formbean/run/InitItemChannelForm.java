/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.run;

import java.util.List;

import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

/**
 * @author Kolor
 */
public class InitItemChannelForm extends BaseForm {
    @NotNull(message = "routeIds未设置")
    @MinSize(value = 1, message = "routeIds未设置")
    private List<Long>   routeIds;
    @NotNull(message = "channels未设置")
    @MinSize(value = 1, message = "channels未设置")
    private List<String> channels;

    public List<Long> getRouteIds() {
        return routeIds;
    }

    public void setRouteIds(List<Long> routeIds) {
        this.routeIds = routeIds;
    }

    public List<String> getChannels() {
        return channels;
    }

    public void setChannels(List<String> channels) {
        this.channels = channels;
    }

}
