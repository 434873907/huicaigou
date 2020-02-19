package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 16:30
 */
public class UpdateFlashSaleItemIndexForm extends BaseForm {
    /**
     * 活动id
     */
    @Min(value = 0,message = "活动id不能为空")
    private long activityId;

    /**
     * 商品id
     */
    private long itemId;

    /**
     * 商品排序值
     */
    private int index;

    /**
     * 上移或下移 0下移 1上移
     */
    private int direction;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
