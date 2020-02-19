package com.chengzi.apiunion.procurement.admin.web.formbean.express;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/30 15:16
 */
public class GetOrderPressByPkgNoForm extends BaseForm {

    @Min(value = 1, message = "请输入正确的用户ID")
    private long userId;

    @NotBlankAndNull(message = "包裹号不能为空")
    private String pkgNo;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }
}
