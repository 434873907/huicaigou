package com.chengzi.apiunion.procurement.admin.web.pojo.inventory;

import com.chengzi.apiunion.inventory.pojo.InventoryDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 月汐
 * @date 2018/12/18 15:32
 */
public class QueryInventoryBO {

    private long itemId;

    private String itemName;

    private String imageUrl;

    private String brand;

    private String skuValue;

    private String channel;

    private String supplierNames;

    private int inventoryNum;

    private List<SkuInventory> skuInventoryList;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSupplierNames() {
        return supplierNames;
    }

    public void setSupplierNames(String supplierNames) {
        this.supplierNames = supplierNames;
    }

    public int getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(int inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public List<SkuInventory> getSkuInventoryList() {
        return skuInventoryList;
    }

    public void setSkuInventoryList(List<SkuInventory> skuInventoryList) {
        this.skuInventoryList = skuInventoryList;
    }

    static class SkuInventory {

        private String skuValue;

        private String upc;

        private String channel;

        private String supplierNames;

        private int inventoryNum;

        private List<String> supplierInventory;

        public String getSkuValue() {
            return skuValue;
        }

        public void setSkuValue(String skuValue) {
            this.skuValue = skuValue;
        }

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getSupplierNames() {
            return supplierNames;
        }

        public void setSupplierNames(String supplierNames) {
            this.supplierNames = supplierNames;
        }

        public int getInventoryNum() {
            return inventoryNum;
        }

        public void setInventoryNum(int inventoryNum) {
            this.inventoryNum = inventoryNum;
        }

        public List<String> getSupplierInventory() {
            return supplierInventory;
        }

        public void setSupplierInventory(List<String> supplierInventory) {
            this.supplierInventory = supplierInventory;
        }
    }

    public static QueryInventoryBO convert(InventoryDO inventoryDO) {
        QueryInventoryBO bo = new QueryInventoryBO();
        bo.setBrand(inventoryDO.getBrandName());
        bo.setImageUrl(inventoryDO.getMainImg());
        bo.setItemId(inventoryDO.getId());
        bo.setItemName(inventoryDO.getName());
        int inventoryNum = 0;
        List<String> skuValues = new ArrayList<>();
        Set<String> supplierNames = new HashSet<>();
        Set<String> channels = new HashSet<>();
        List<SkuInventory> skuInventoryList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inventoryDO.getSkuList())) {
            for (ItemSearchBO.ItemSearchSkuInfo skuInfo : inventoryDO.getSkuList()) {
                skuValues.add(buildSkuValue(skuInfo));
                SkuInventory skuInventory = new SkuInventory();
                Set<String> skuChannels = new HashSet<>();
                Set<String> skuSupplierNames = new HashSet<>();
                int skuInventoryNum = 0;
                List<String> supplierInventory = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(skuInfo.getSkuChannels())) {
                    for (ItemSkuInfo.SkuChannelInfo channelInfo : skuInfo.getSkuChannels()) {
                        channels.add(channelInfo.getChannelName());
                        skuChannels.add(channelInfo.getChannelName());
                        inventoryNum += channelInfo.getTotalStock();
                        skuInventoryNum += channelInfo.getTotalStock();
                        for (ItemSkuInfo.SupplierStock supplierStock : channelInfo.getSupplierStocks()) {
                            supplierNames.add(supplierStock.getSupplierName());
                            skuSupplierNames.add(supplierStock.getSupplierName());
                            supplierInventory.add(supplierStock.getSupplierName() + ",库存" + supplierStock.getStock());
                        }
                    }
                }
                skuInventory.setChannel(String.join(",", skuChannels));
                skuInventory.setSupplierNames(String.join(",", skuSupplierNames));
                skuInventory.setInventoryNum(skuInventoryNum);
                skuInventory.setSupplierInventory(supplierInventory);
                skuInventory.setSkuValue(buildSkuValue(skuInfo));
                skuInventory.setUpc(skuInfo.getUpc());
                skuInventoryList.add(skuInventory);
            }
        }
        bo.setChannel(String.join(",", channels));
        bo.setInventoryNum(inventoryNum);
        bo.setSkuValue(String.join(",", skuValues));
        bo.setSupplierNames(String.join(",", supplierNames));
        bo.setSkuInventoryList(skuInventoryList);
        return bo;
    }

    private static String buildSkuValue(ItemSearchBO.ItemSearchSkuInfo skuInfo) {
        List<String> skuValues = new ArrayList<>();
        for (ItemSearchBO.SearchItemSkuValue skuValue : skuInfo.getSkuValues()) {
            skuValues.add(skuValue.getSkuValue());
        }
        return String.join("+", skuValues);
    }

}
