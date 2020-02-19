package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 20:07
 */
public class OrderPkgBO extends JsonPojo {

    /**
     * 包裹号
     */
    private String               pkgNo;

    /**
     * 是否支持物流操作
     */
    private boolean              supportOperateExpress = true;

    /**
     * 包裹下的商品
     */
    private List<OrderItemBO>    pkgItemInfo;

    /**
     * 包裹物流
     */
    private List<OrderExpressBO> pkgExpressInfos;

    public List<OrderItemBO> getPkgItemInfo() {
        return pkgItemInfo;
    }

    public void setPkgItemInfo(List<OrderItemBO> pkgItemInfo) {
        this.pkgItemInfo = pkgItemInfo;
    }

    public List<OrderExpressBO> getPkgExpressInfos() {
        return pkgExpressInfos;
    }

    public void setPkgExpressInfos(List<OrderExpressBO> pkgExpressInfos) {
        this.pkgExpressInfos = pkgExpressInfos;
    }

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public boolean isSupportOperateExpress() {
        return supportOperateExpress;
    }

    public void setSupportOperateExpress(boolean supportOperateExpress) {
        this.supportOperateExpress = supportOperateExpress;
    }
}
