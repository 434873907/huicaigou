package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * @author 月汐
 * @date 2019/07/17 20:47
 */
public class GroupItemListForm extends ItemListForm {

    /**
     * 分组ID
     */
    private long                    groupId;

    /**
     * 排除的分组ID
     */
    private long                    exceptGroupId;

    /**
     * 商品ID
     */
    private long                    itemId;

    /**
     * 排除的商品ID
     */
    private long                    exceptItemId;

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getExceptGroupId() {
        return exceptGroupId;
    }

    public void setExceptGroupId(long exceptGroupId) {
        this.exceptGroupId = exceptGroupId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getExceptItemId() {
        return exceptItemId;
    }

    public void setExceptItemId(long exceptItemId) {
        this.exceptItemId = exceptItemId;
    }

    @Override
    public Result<ItemSearchQuery> buildQuery() {
        Result<ItemSearchQuery> result = super.buildQuery();
        if (!result.isSuccess()) {
            return result;
        }

        ItemSearchQuery query = result.getData();
        if (itemId > 0 && groupId == 0) {
            query.setItemIds(CollectionUtil.asHashSet(itemId));
        }

        if (exceptItemId > 0 && groupId == 0) {
            query.setExceptItemId(exceptItemId);
        }

        if (groupId > 0) {
            query.setGroupIds(CollectionUtil.asArrayList(getGroupId()));
        }
        if (exceptGroupId > 0) {
            query.setExceptGroupId(getExceptGroupId());
        }

        return Result.buildSuccessResult(query);
    }
}
