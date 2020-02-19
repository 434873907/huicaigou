package com.apiunion.procurement.admin.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.item.enums.ItemImageTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.AddTemplateItemForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.QueryTemplateItemListForm;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
import com.chengzi.common.util.CollectionUtil;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class TemplateItemTest {
    private static String api_url = "http://at.huidinghuo.com";

    private static String addTemplateItemUrl = "/template/add_template_item.do";
    private static String queryTemplateItemUrl = "/template/query_template_item_list.do";

    private static String contentType = "application/x-www-form-urlencoded";
    private static String cookie = "__kor__=tl4ktUns9EZMXpFaQAbBvZH21y6A+Rsf5X+2KvAUlEAhYFGlFbEf/WM02RKOsIxB2uyeP58EIOlo4n+yiDtzNrewoxTXxmN1; __coe__=hhEd3AdTfNk7T/LPOxV8FmBUyVOrMr/Yr2+kBXigZSKyg/AqsMYPWH9lKAHA2WIiZMD9hJuOOdAMzve8adHL8fO8qqeMkXq9";
    @Test
    public void addTemplateItem() {


        String url = api_url + addTemplateItemUrl;

//        AddTemplateItemForm form = new AddTemplateItemForm();
//        HashMap<String, String> param = new HashMap<>();
        AddTemplateItemForm itemForm = new AddTemplateItemForm();
        itemForm.setName("测试模板商品");
        itemForm.setStatus(1);
//        param.put("name", "测试");
//        param.put("brandId", "1");
//        param.put("brandName", "KM");
        itemForm.setBrandId(1);
        itemForm.setBrandName("KM");
//        CurrencyEnum
        itemForm.setCurrency(1);
        itemForm.setDesc("测试");
        itemForm.setRichDesc("richdesc");
//        itemForm.set

        Set<SupplierItemImageInfo> imageInfos = new HashSet<>();
        SupplierItemImageInfo itemImageInfo1= new SupplierItemImageInfo();
        itemImageInfo1.setType(ItemImageTypeEnum.MAIN.getCode());

        imageInfos.add(itemImageInfo1);
        itemForm.setAddImages(imageInfos);

        SupplierItemSkuInfo skuInfo = new SupplierItemSkuInfo();
        skuInfo.setUpc("123");
        skuInfo.setWeight(new BigDecimal(1));
        skuInfo.setQuantity(1);
        SupplierItemSkuInfo.ItemSkuValues itemSkuValues = new SupplierItemSkuInfo.ItemSkuValues();
        SupplierItemSkuInfo.ItemSkuValue itemSkuValue = new SupplierItemSkuInfo.ItemSkuValue();
        itemSkuValue.setSkuName("尺寸");
        itemSkuValue.setSkuValue("XXL");
        itemSkuValues.add(itemSkuValue);
        skuInfo.setSkuValues(itemSkuValues);
        skuInfo.setSalePrice(new BigDecimal(10));
        skuInfo.setSettlePrice(new BigDecimal(20));
//        skuInfo.set

        List<SupplierItemSkuInfo> skuInfos = new ArrayList<>();
        skuInfos.add(skuInfo);
        itemForm.setSkuList(skuInfos);


        List<ItemSkuNameValues> skuNameValues = new ArrayList<>();
        ItemSkuNameValues itemSkuNameValues = new ItemSkuNameValues();
        itemSkuNameValues.setSkuName("尺寸");
        LinkedHashSet<String> skuValues = (LinkedHashSet<String>) CollectionUtil.asLinkedHashSet("XL","XXL","L","XXXL");
        itemSkuNameValues.setSkuValues(skuValues);

        ItemSkuNameValues itemSkuNameValues1 = new ItemSkuNameValues();
        itemSkuNameValues1.setSkuName("颜色");
        LinkedHashSet<String> skuValues1 = (LinkedHashSet<String>) CollectionUtil.asLinkedHashSet("red","blue");
        itemSkuNameValues1.setSkuValues(skuValues1);

        skuNameValues.add(itemSkuNameValues);
        skuNameValues.add(itemSkuNameValues1);
        itemForm.setSkuNameValues(skuNameValues);
        String json = JSON.toJSONString(itemForm);
        System.out.println(json);

//      Map<String, String> param =(Map<String, String>)  JSON.parseObject(json, Map.class);

//        Map<String, String> reqHeader = new HashMap<>();
//        reqHeader.put("Content-Type", contentType);
//        reqHeader.put("SIGN", ApiSignUtil.buildSign(json, secret));
//        reqHeader.put("Cookie", cookie);

//        HttpClientUtil.doPostJson(url, json, reqHeader);

//        HttpClientUtil.doGet(url, param, reqHeader);
    }

    @Test
    public void queryTemplateItem() {
        QueryTemplateItemListForm form = null;
         String url = api_url + queryTemplateItemUrl;
        Map<String, String> reqHeader = new HashMap<>();
        reqHeader.put("Content-Type", contentType);
//        reqHeader.put("SIGN", ApiSignUtil.buildSign(json, secret));
        reqHeader.put("Cookie", cookie);
        HashMap<String, String> param = new HashMap<>();

        param.put("status", "0");
        param.put("page", "1");
        param.put("limit", "10");
        param.put("keyword", "");
        param.put("modifyTimeBegin", "");
        param.put("modifyTimeEnd", "");
        HttpClientUtil.doGet(url, param, reqHeader);
    }


}
