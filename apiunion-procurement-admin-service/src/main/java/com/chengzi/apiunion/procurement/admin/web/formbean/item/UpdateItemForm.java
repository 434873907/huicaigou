/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import java.util.List;

import org.summercool.web.annotation.JsonField;

import net.sf.oval.constraint.Min;

/**
 * @author Kolor
 *
 */
public class UpdateItemForm extends AddItemForm {
    public static final int FROM_PAGE_APPROVE = 1;

    @Min(value = 1, message = "商品ID不能为空")
    private long            id;

    /**
     * 删除的图片ID
     */
    @JsonField
    private List<Long>      delImageIds;

    /**
     * 来源页面
     * <br>审核页面：1
     */
    private int             fromPage;

    public int getFromPage() {
        return fromPage;
    }

    public void setFromPage(int fromPage) {
        this.fromPage = fromPage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getDelImageIds() {
        return delImageIds;
    }

    public void setDelImageIds(List<Long> delImageIds) {
        this.delImageIds = delImageIds;
    }

}
