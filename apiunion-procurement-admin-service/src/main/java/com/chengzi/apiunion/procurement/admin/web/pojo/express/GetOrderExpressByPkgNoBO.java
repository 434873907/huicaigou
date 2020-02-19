package com.chengzi.apiunion.procurement.admin.web.pojo.express;

import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/30 15:36
 */
public class GetOrderExpressByPkgNoBO extends JsonPojo {

    private Long id;

    /**
     * 物流公司
     */
    private Long expressCompany;

    /**
     * 物流单号
     */
    private String expressNum;

    /**
     * 轨迹号
     */
    private String trackingNum;

    /**
     * 物流轨迹
     */
    private String detail;

    /**
     * 备注
     */
    private String attr;

    /**
     * 物流段次
     */
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(Long expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static GetOrderExpressByPkgNoBO convert(OrderExpressDO orderExpressDO) {
        GetOrderExpressByPkgNoBO bo = new GetOrderExpressByPkgNoBO();
        bo.setId(orderExpressDO.getId());
        bo.setExpressCompany(orderExpressDO.getExpressCompany());
        bo.setExpressNum(orderExpressDO.getExpressNum());
        bo.setTrackingNum(orderExpressDO.getTrackingNum());
        bo.setDetail(orderExpressDO.getDetail());
        bo.setAttr(orderExpressDO.getAttr());
        bo.setType(orderExpressDO.getType());
        return bo;
    }

}
