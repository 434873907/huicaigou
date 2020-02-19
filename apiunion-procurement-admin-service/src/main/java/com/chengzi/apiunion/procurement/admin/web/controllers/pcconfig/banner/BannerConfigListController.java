package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.banner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.pcconfig.tab.pojo.TabConfigDO;
import com.chengzi.apiunion.pcconfig.tab.service.TabConfigService;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.apiunion.common.jump.Jump;
import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.pcconfig.banner.pojo.BannerConfigDO;
import com.chengzi.apiunion.pcconfig.banner.service.BannerConfigService;
import com.chengzi.apiunion.pcconfig.enums.ConfigEnum;
import com.chengzi.apiunion.pcconfig.tab.pojo.Condition;
import com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.banner.BannerConfigListBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 苏子
 * @date 2019年1月16日
 */
public class BannerConfigListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<List<BannerConfigListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        BannerConfigService bannerConfigService = BeanFactory.getBean(BannerConfigService.class);
        List<BannerConfigListBO> list = convert(bannerConfigService.queryBannerConfig());
        return Result.buildSuccessResult(list);
    }

    public static List<BannerConfigListBO> convert(List<BannerConfigDO> bannerConfigList) {
        if (CollectionUtil.isEmpty(bannerConfigList)) {
            return null;
        }
        //拿到所有待查数据，只查一次
        Set<Long> categoryIdSet = new HashSet<>();
        Set<Long> brandIdSet = new HashSet<>();
        Set<Long> shopIdSet = new HashSet<>();
        Set<Integer> channelTypeSet = new HashSet<>();
        for (BannerConfigDO bannerConfig : bannerConfigList) {
            Jump jump = Jump.parse(bannerConfig.getJump());
            if (jump == null) {
                continue;
            }
            if (jump.getJumpType() != JumpTypeEnum.COMBINATION_CONDITION.getCode()) {
                continue;
            }
            List<Condition> conditionList = Condition.parseList(jump.getJumpData());
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

        //查询出需要的数据
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

        //组装BO
        List<BannerConfigListBO> list = new ArrayList<>();
        for (BannerConfigDO bannerConfig : bannerConfigList) {
            BannerConfigListBO bo = new BannerConfigListBO();
            bo.setId(bannerConfig.getId());
            Image image = Image.parse(bannerConfig.getImage());
            bo.setImageUrl(image.getUrl());
            bo.setTitle(bannerConfig.getTitle());
            Jump jump = Jump.parse(bannerConfig.getJump());
            if (jump != null) {
                int jumpType = jump.getJumpType();
                bo.setJumpType(jumpType);
                if (jumpType == JumpTypeEnum.PAGE_WEB.getCode()) {
                    bo.setJumpStr(jump.getJumpData());
                } else if (jumpType == JumpTypeEnum.ACTIVITY_DETAIL.getCode()) {
                    JumpTypeEnum jumpEnum = EnumUtil.parse(JumpTypeEnum.class, jump.getJumpType());
                    bo.setJumpStr(jumpEnum.getRemark() + "：" + jump.getJumpData());
                } else if (jumpType == JumpTypeEnum.COMBINATION_CONDITION.getCode()) {
                    List<Condition> conditionList = Condition.parseList(jump.getJumpData());
                    if (CollectionUtil.isNotEmpty(conditionList)) {
                        List<String> conditionStrList = Condition.conditionStrList(conditionList, categoryMap, brandMap, shopMap, channelMap);
                        JumpTypeEnum jumpEnum = EnumUtil.parse(JumpTypeEnum.class, jump.getJumpType());
                        bo.setJumpStr(jumpEnum.getRemark() + "：" + StringUtils.join(conditionStrList, "，"));
                        bo.setConditionList(conditionStrList);
                    }
                } else if (jumpType == JumpTypeEnum.PC_TAB_PAGE.getCode()) {
                    TabConfigService tabConfigService = BeanFactory.getBean(TabConfigService.class);
                    TabConfigDO tabConfig = tabConfigService.getTabConfig(Long.parseLong(jump.getJumpData()));
                    if (tabConfig != null) {
                        bo.setJumpStr(tabConfig.getTitle());
                    }
                } else if (jumpType == JumpTypeEnum.PAGE_SEARCH.getCode()) {
                    bo.setJumpStr(jump.getJumpData());
                }
            }
            bo.setStartTime(bannerConfig.getStartTime());
            bo.setEndTime(bannerConfig.getEndTime());
            bo.setStatus(bannerConfig.getStatus());
            bo.setStatusStr(bannerConfig.isEnable() ? ConfigEnum.STATUS_ENABLE.getRemark() : ConfigEnum.STATUS_NOT_ENABLE.getRemark());
            list.add(bo);
        }
        return list;
    }

}
