package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.tab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.pcconfig.enums.ConfigEnum;
import com.chengzi.apiunion.pcconfig.tab.pojo.Condition;
import com.chengzi.apiunion.pcconfig.tab.pojo.TabConfigDO;
import com.chengzi.apiunion.pcconfig.tab.service.TabConfigService;
import com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.tab.TabConfigListBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 苏子
 * @date 2019年1月16日
 */
public class TabConfigListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<List<TabConfigListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        TabConfigService tabConfigService = BeanFactory.getBean(TabConfigService.class);
        List<TabConfigListBO> list = convert(tabConfigService.queryTabConfig());
        return Result.buildSuccessResult(list);
    }

    private List<TabConfigListBO> convert(List<TabConfigDO> tabConfigList) {
        if (CollectionUtil.isEmpty(tabConfigList)) {
            return null;
        }
        //拿到所有待查数据，只查一次
        Set<Long> categoryIdSet = new HashSet<>();
        Set<Long> brandIdSet = new HashSet<>();
        Set<Long> shopIdSet = new HashSet<>();
        Set<Integer> channelTypeSet = new HashSet<>();
        for (TabConfigDO tabConfig : tabConfigList) {
            List<Condition> conditionList = Condition.parseList(tabConfig.getCondition());
            if (CollectionUtil.isEmpty(conditionList)) {
                continue;
            }
            for (Condition condition : conditionList) {
                List<CategoryUnit> categoryUnitList = condition.getCategoryUnitList();
                List<Long> brandIdList = condition.getBrandIdList();
                List<Long> shopIdList = condition.getShopIdList();
                List<Integer> channelTypeList = condition.getChannelTypeList();

                if (CollectionUtil.isNotEmpty(categoryUnitList)) {
                    for (CategoryUnit categoryUnit : categoryUnitList) {
                        if (!categoryUnit.hasValue()) {
                            continue;
                        }
                        long cateId1 = categoryUnit.getCateId1();
                        long cateId2 = categoryUnit.getCateId2();
                        long cateId3 = categoryUnit.getCateId3();
                        if (cateId3 > 0) {
                            categoryIdSet.add(cateId3);
                        } else if (cateId2 > 0) {
                            categoryIdSet.add(cateId2);
                        } else if (cateId1 > 0) {
                            categoryIdSet.add(cateId1);
                        }
                    }
                }

                if (CollectionUtil.isNotEmpty(brandIdList)) {
                    brandIdSet.addAll(CollectionUtil.asHashSetFromIterable(brandIdList));
                }

                if (CollectionUtil.isNotEmpty(shopIdList)) {
                    shopIdSet.addAll(CollectionUtil.asHashSetFromIterable(shopIdList));
                }

                if (CollectionUtil.isNotEmpty(channelTypeList)) {
                    channelTypeSet.addAll(CollectionUtil.asHashSetFromIterable(channelTypeList));
                }
            }
        }

        Map<Long, PartnerCategoryDO> categoryMap = null;
        Map<Long, BrandDO> brandMap = null;
        Map<Long, ShopDO> shopMap = null;
        Map<Integer, ItemChannelTypeDO> channelMap = null;

        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            PartnerCategoryService partnerCategoryService = BeanFactory.getBean(PartnerCategoryService.class);
            List<PartnerCategoryDO> categoryList = partnerCategoryService.queryCategoryInCache(new ArrayList<>(categoryIdSet));
            categoryMap = CollectionUtil.toMap(categoryList, "id");
        }

        if (CollectionUtil.isNotEmpty(brandIdSet)) {
            BrandService brandService = BeanFactory.getBean(BrandService.class);
            List<BrandDO> brandList = brandService.getByIdsWithCache(brandIdSet);
            brandMap = CollectionUtil.toMap(brandList, "id");
        }

        if (CollectionUtil.isNotEmpty(shopIdSet)) {
            ShopService shopService = BeanFactory.getBean(ShopService.class);
            List<ShopDO> shopList = shopService.getByIdsWithCache(shopIdSet);
            shopMap = CollectionUtil.toMap(shopList, "id");
        }

        if (CollectionUtil.isNotEmpty(channelTypeSet)) {
            ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
            List<ItemChannelTypeDO> channelList = itemChannelTypeService.queryByTypeList(new ArrayList<>(channelTypeSet));
            channelMap = CollectionUtil.toMap(channelList, "channelType");
        }

        List<TabConfigListBO> list = new ArrayList<>();
        for (TabConfigDO tabConfig : tabConfigList) {
            TabConfigListBO bo = new TabConfigListBO();
            bo.setId(tabConfig.getId());
            bo.setTitle(tabConfig.getTitle());
            bo.setStartTime(tabConfig.getStartTime());
            bo.setEndTime(tabConfig.getEndTime());
            bo.setStatus(tabConfig.getStatus());
            bo.setStatusStr(tabConfig.isEnable() ? ConfigEnum.STATUS_ENABLE.getRemark() : ConfigEnum.STATUS_NOT_ENABLE.getRemark());
            
            List<Condition> conditionList = Condition.parseList(tabConfig.getCondition());
            if (CollectionUtil.isNotEmpty(conditionList)) {
                List<String> conditionStrList = Condition.conditionStrList(conditionList, categoryMap, brandMap, shopMap, channelMap);
                bo.setConditionList(conditionStrList);
            }
            list.add(bo);
        }
        return list;
    }
}
