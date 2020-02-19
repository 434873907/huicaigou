package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import java.util.List;

import org.summercool.web.annotation.JsonField;

import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

/**
 * @author 随风
 * @create 2020-01-15 20:13
 **/
public class AddExpressNumForm extends BaseForm {

    @NotEmptyAndNull(message = "订单号不能为空")
    private String          orderNum;

    @NotEmptyAndNull(message = "包裹号不能为空")
    private String          pkgNo;

    @NotEmptyAndNull(message = "物流单号不能为空")
    private String          expressNum;

    @Min(value = 1, message = "物流公司必须选择")
    @NotEmptyAndNull(message = "物流公司必须选择")
    private Long            expressCompanyId;

    @NotNull(message = "商品必须选择")
    @MinSize(value = 1, message = "商品必须选择")
    @JsonField
    private List<ItemInfo>  itemList;

    public static class ItemInfo {

        private long    id;

        private int     num;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Long getExpressCompanyId() {
        return expressCompanyId;
    }

    public void setExpressCompanyId(Long expressCompanyId) {
        this.expressCompanyId = expressCompanyId;
    }

    public List<ItemInfo> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemInfo> itemList) {
        this.itemList = itemList;
    }

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }
}
