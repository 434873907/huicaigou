package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.order.enums.OrderPkgStatusEnum;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 20:45
 */
public class OrderItemBO {

    /**
     * id
     */
    private long               orderItemId;

    /**
     * 商品图片
     */
    @ImageDecorater(ImageBizType.ADMIN_ORDER)
    private String             itemImg;

    /**
     * 商品名称
     */
    private String             itemTitle;

    /**
     * 商品编号
     */
    private String             itemNum;

    /**
     * 规格编号
     */
    private String             skuNum;

    /**
     * 采购价
     */
    private double             channelPrice;

    /**
     * 供应价
     */
    private double             price;

    /**
     * 数量
     */
    private int                count;

    /**
     * 重量（kg）
     */
    private BigDecimal             weight;

    /**
     * 规格
     */
    private String             spec;

    /**
     * 状态
     */
    private int                status;

    /**
     * 第三方包裹状态
     * @see OrderPkgStatusEnum
     */
    private int                pkgStatus;

    /**
     * 第三方包裹状态名
     */
    private String             pkgStatusName;

    /**
     * 第三方包裹状态说明
     */
    private String             pkgStatusDesc;
    /**
     * 第三方
     */
    private ThirdItemSkuAttrBO thirdAttr;

    /**
     * 供应商ID
     */
    private long               supplierId;

    /**
     * 供应商名字
     */
    private String             supplierName;

    /**
     * 供应商列表
     */
    private List<Supplier>     supplierList;

    /**
     * 渠道类型
     */
    private int                channelType;

    /**
     * 渠道名
     */
    private String             channelName;

    public static class ThirdItemSkuAttrBO extends JsonPojo {

        /**
         * 第三方同步授权ID
         */
        private long                          authId;
        /**
         * 类型
         * @see ThirdSyncItemTypeEnum
         */
        private int                           apiType;
        /**
         * 第三方商品ID
         */
        private String                        thirdItemId;
        /**
         * 第三方规格ID
         */
        private String                        thirdSkuId;
        /**
         * 私有商品ID（针对超级快递公私有库）
         */
        private String                        thirdPriItemId;
        /**
         * 第三方规格信息
         */
        private LinkedHashMap<String, String> thirdSkuInfo;

        public long getAuthId() {
            return authId;
        }

        public void setAuthId(long authId) {
            this.authId = authId;
        }

        public int getApiType() {
            return apiType;
        }

        public void setApiType(int apiType) {
            this.apiType = apiType;
        }

        public String getThirdItemId() {
            return thirdItemId;
        }

        public void setThirdItemId(String thirdItemId) {
            this.thirdItemId = thirdItemId;
        }

        public String getThirdSkuId() {
            return thirdSkuId;
        }

        public void setThirdSkuId(String thirdSkuId) {
            this.thirdSkuId = thirdSkuId;
        }

        public String getThirdPriItemId() {
            return thirdPriItemId;
        }

        public void setThirdPriItemId(String thirdPriItemId) {
            this.thirdPriItemId = thirdPriItemId;
        }

        public LinkedHashMap<String, String> getThirdSkuInfo() {
            return thirdSkuInfo;
        }

        public void setThirdSkuInfo(LinkedHashMap<String, String> thirdSkuInfo) {
            this.thirdSkuInfo = thirdSkuInfo;
        }

    }

    public ThirdItemSkuAttrBO getThirdAttr() {
        return thirdAttr;
    }

    public void setThirdAttr(ThirdItemSkuAttrBO thirdAttr) {
        this.thirdAttr = thirdAttr;
    }

    public int getPkgStatus() {
        return pkgStatus;
    }

    public void setPkgStatus(int pkgStatus) {
        this.pkgStatus = pkgStatus;
    }

    public String getPkgStatusName() {
        return pkgStatusName;
    }

    public void setPkgStatusName(String pkgStatusName) {
        this.pkgStatusName = pkgStatusName;
    }

    public String getPkgStatusDesc() {
        return pkgStatusDesc;
    }

    public void setPkgStatusDesc(String pkgStatusDesc) {
        this.pkgStatusDesc = pkgStatusDesc;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(String skuNum) {
        this.skuNum = skuNum;
    }

    public double getChannelPrice() {
        return channelPrice;
    }

    public void setChannelPrice(double channelPrice) {
        this.channelPrice = channelPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public static class Supplier {

        private long   id;

        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
