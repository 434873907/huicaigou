package com.chengzi.apiunion.procurement.admin.web.formbean.coupon;

import java.util.List;
import java.util.Set;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
//import org.summercool.web.annotation.JsonField;

/**
 * User: 致远
 */
public class SendCouponConfigForm extends BaseForm {
    @Min(value = 1, message = "请选择需要领取的优惠券")
    private long couponConfigId;
    /**
     * 发放的用户对象
     */
//    @JsonField
    private Set<Long> userIds;

    /**
     * 选取类型：1全选商户下所有用户，2部分用户userids数据
     */
    private int selectType;

    public long getCouponConfigId() {
        return couponConfigId;
    }

    public void setCouponConfigId(long couponConfigId) {
        this.couponConfigId = couponConfigId;
    }

//    public List<Long> getUserIds() {
//        return userIds;
//    }

//    public void setUserIds(List<Long> userIds) {
//        this.userIds = userIds;
//    }

    public Set<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Long> userIds) {
        this.userIds = userIds;
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }
}
