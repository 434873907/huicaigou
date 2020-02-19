package com.chengzi.apiunion.procurement.admin.web.controllers.coupon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.coupon.enums.CouponConditionEnum;
import com.chengzi.apiunion.coupon.pojo.CouponConditionBO;
import com.chengzi.apiunion.coupon.pojo.CouponConfigDO;
import com.chengzi.apiunion.coupon.service.CouponConfigService;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.coupon.GetCouponConfigForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.coupon.CouponConfigBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.chengzi.apiunion.procurement.admin.web.pojo.coupon.CouponConfigBO.brandConverCondition;
import static com.chengzi.apiunion.procurement.admin.web.pojo.coupon.CouponConfigBO.cateConverCondition;
import static com.chengzi.apiunion.procurement.admin.web.pojo.coupon.CouponConfigBO.itemConverCondition;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 13:58
 */
public class GetCouponConfigController extends AbstractApiController<GetCouponConfigForm> {

    @Override
    protected Result<CouponConfigBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetCouponConfigForm command)
            throws Exception {
        CouponConfigService couponConfigService = BeanFactory.getBean(CouponConfigService.class);
        CouponConfigDO couponConfigDO = couponConfigService.getCouponConfigById(command.getCouponConfigId());
        if (couponConfigDO != null) {
            CouponConfigBO couponConfigBO = new CouponConfigBO();
            couponConfigBO.setCouponType(couponConfigDO.getCouponType());
            couponConfigBO.setCouponCode(couponConfigDO.getCouponCode());
            couponConfigBO.setCouponName(couponConfigDO.getCouponName());
            couponConfigBO.setCouponValue(couponConfigDO.getCouponValue());
            couponConfigBO.setCouponValueType(couponConfigDO.getCouponValueType());
            couponConfigBO.setOrderPriceLimit(couponConfigDO.getOrderPriceLimit());

            CouponConditionBO conditionBO = JSON.parseObject(couponConfigDO.getCondition(),CouponConditionBO.class);
            if(couponConfigDO.getConditionType() == CouponConditionEnum.BRAND_CONDITION.getCode()){
                BrandService brandService = BeanFactory.getBean(BrandService.class);
                List<BrandDO> brandDOS = brandService.getByIds(conditionBO.getBrands());
                couponConfigBO = brandConverCondition(couponConfigBO,brandDOS);
            } else if (couponConfigDO.getConditionType() == CouponConditionEnum.CATE_CONDITION.getCode()){
                List<PartnerCategoryDO> categoryDOS = new ArrayList<>();
                PartnerCategoryService partnerCategoryService = BeanFactory.getBean(PartnerCategoryService.class);
                conditionBO.getCategories().forEach(x->{
                    PartnerCategoryDO categoryDO = partnerCategoryService.getCategoryInCache(x);
                    categoryDOS.add(categoryDO);
                });
                couponConfigBO = cateConverCondition(couponConfigBO,categoryDOS);
            }else if (couponConfigDO.getConditionType() == CouponConditionEnum.ITEM_CONDITION.getCode()){
                ItemService itemService = BeanFactory.getBean(ItemService.class);
                List<ItemCO> itemCOS = itemService.getItemWithCacheByIds(conditionBO.getItems());
                couponConfigBO = itemConverCondition(couponConfigBO, itemCOS);
            }

            couponConfigBO.setTimeType(couponConfigDO.getTimeType());
            couponConfigBO.setConditionType(couponConfigDO.getConditionType());
            couponConfigBO.setCouponType(couponConfigDO.getCouponType());
            couponConfigBO.setAfterDays(couponConfigDO.getAfterDays());
            couponConfigBO.setStartTime(couponConfigDO.getStartTime());
            couponConfigBO.setEndTime(couponConfigDO.getEndTime());
            couponConfigBO.setCouponCount(couponConfigDO.getCouponCount());
            couponConfigBO.setDesc(couponConfigDO.getDesc());
            couponConfigBO.setSendCouponCount(couponConfigDO.getSendCouponCount());
            return Result.buildSuccessResult(couponConfigBO);
        }
        return Result.buildOpFailedResult("操作失败");
    }
}
