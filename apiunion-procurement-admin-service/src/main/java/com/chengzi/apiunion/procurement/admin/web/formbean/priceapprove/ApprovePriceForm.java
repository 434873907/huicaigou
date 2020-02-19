package com.chengzi.apiunion.procurement.admin.web.formbean.priceapprove;

import com.chengzi.apiunion.supplier.common.priceapprove.enums.SkuPriceApproveStatusEnum;
import com.chengzi.common.data.validate.oval.OvalPojo;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;

import java.util.List;

public class ApprovePriceForm extends BaseForm {

    /**
     * @see SkuPriceApproveStatusEnum
     */
    private int status;


    @MinSize(value = 1,message = "请选择要审核的申请")
    private List<ApproveParam> approveParams;

    private String desc;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ApproveParam> getApproveParams() {
        return approveParams;
    }

    public void setApproveParams(List<ApproveParam> approveParams) {
        this.approveParams = approveParams;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public class ApproveParam extends OvalPojo {

        @Min(value = 1)
        private long supplierId;

        @Min(value = 1)
        private long approveId;

        public long getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(long supplierId) {
            this.supplierId = supplierId;
        }

        public long getApproveId() {
            return approveId;
        }

        public void setApproveId(long approveId) {
            this.approveId = approveId;
        }
    }
}
