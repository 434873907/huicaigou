package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.dao.ThirdSyncItemAuthMapper;
import com.chengzi.apiunion.item.enums.PresetPriceStrategyTypeEnum;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.*;
import com.chengzi.apiunion.item.strategy.presetprice.PercentPresetPriceStrategy;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.GetSupplierDetailForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO.ChannelInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.GetSupplierDetailBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 获取供应商详情
 *
 * @author 月汐
 * @date 2018/11/23 19:52
 */
public class GetSupplierDetailController extends AbstractApiController<GetSupplierDetailForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetSupplierDetailForm command) throws Exception {
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        SupplierService service = BeanFactory.getBean(SupplierService.class);
        SupplierDO supplier = service.getSupplier(command.getId());
        if (supplier == null) {
            return Result.buildNotExistedResult("供应商不存在");
        }

        GetSupplierDetailBO bo = new GetSupplierDetailBO();
        bo.setId(supplier.getId());
        bo.setCreateTime(supplier.getCreateTime());
        bo.setSupplierName(supplier.getSupplierName());
        bo.setStatus(supplier.getStatus());
        bo.setAccount(supplier.getAccount());
        bo.setContacts(supplier.getContacts());
        bo.setPhone(supplier.getPhone());
        bo.setApiTypeName(ThirdSyncItemTypeEnum.parse(supplier.getApiType()).getName());

        ThirdSyncItemAuthMapper thirdSyncItemAuthMapper = BeanFactory.getBean(ThirdSyncItemAuthMapper.class);
        List<ThirdSyncItemAuthDO> thirdSyncItemAuthList = thirdSyncItemAuthMapper.selectBySupplierId(SimpleRouteOperate.of(supplier.getId()));
        if (!thirdSyncItemAuthList.isEmpty()) {
            ThirdSyncItemAuthDO thirdSyncItemAuthDO = thirdSyncItemAuthList.get(0);
            bo.setApiAuth(ThirdSyncApiAuth.fromJson(thirdSyncItemAuthDO.getApiAuth()));
            bo.setApiType(thirdSyncItemAuthDO.getApiType());
            PresetPriceStrategy priceStrategy = PresetPriceStrategy.fromJson(thirdSyncItemAuthDO.getPriceStrategy());
            if (priceStrategy.getType() == PresetPriceStrategyTypeEnum.PERCENT.getCode()) {
                //小数点比例改为百分比
                BigDecimal priceUpPercent = ((PercentPresetPriceStrategy) priceStrategy).getPriceUpPercent().multiply(new BigDecimal(100));
                BigDecimal originalPriceUpPercent = ((PercentPresetPriceStrategy) priceStrategy).getOriginalPriceUpPercent().multiply(new BigDecimal(100));
                ((PercentPresetPriceStrategy) priceStrategy).setPriceUpPercent(priceUpPercent);
                ((PercentPresetPriceStrategy) priceStrategy).setOriginalPriceUpPercent(originalPriceUpPercent);
            }
            bo.setPriceStrategy(priceStrategy);
            bo.setSourceItemAttr(ThirdSourceItemAttr.fromJson(thirdSyncItemAuthDO.getSourceItemAttr()));

            List<Integer> channelTypes = bo.getSourceItemAttr().getChannelTypes();
            if (CollectionUtil.isNotEmpty(channelTypes)) {
                List<ItemChannelTypeDO> itemChannelTypeList = itemChannelTypeService.queryByTypeList(channelTypes);
                if (CollectionUtil.isNotEmpty(itemChannelTypeList)) {
                    StringBuilder channelNames = new StringBuilder();
                    itemChannelTypeList.forEach(channel -> channelNames.append(channel.getChannelTypeName()).append(","));
                    bo.getSourceItemAttr().setChannelNames(channelNames.substring(0, channelNames.length() - 1));
                }
            }

            bo.setTargetItemAttr(ThirdTargetItemAttr.fromJson(thirdSyncItemAuthDO.getTargetItemAttr()));

            Long shopId = bo.getTargetItemAttr().getShopId();
            Long expressFeeTemplateId = bo.getTargetItemAttr().getExpressFeeTemplateId();
            if (shopId != null && shopId > 0) {
                ShopService shopService = BeanFactory.getBean(ShopService.class);
                ShopDO shopDO = shopService.getById(shopId);
                if (shopDO != null) {
                    bo.getTargetItemAttr().setShopName(shopDO.getName());
                }
            }
            if (expressFeeTemplateId != null && expressFeeTemplateId > 0) {
                ExpressFeeTemplateService expressFeeTemplateService = BeanFactory.getBean(ExpressFeeTemplateService.class);
                ExpressFeeTemplateDO expressFeeTemplateDO = expressFeeTemplateService.getById(expressFeeTemplateId);
                if (expressFeeTemplateDO != null) {
                    bo.getTargetItemAttr().setExpressFeeTemplateName(expressFeeTemplateDO.getTemplateName());
                }
            }
        }

        Map<Long, List<ItemChannelTypeDO>> supplierChannelTypes = service.getSupplierChannelTypes(CollectionUtil.asArrayList(supplier.getId()));
        List<ItemChannelTypeDO> itemChannelTypeDOS = supplierChannelTypes.get(supplier.getId());
        if (bo.getApiType() == 0) {
            List<ChannelInfo> channelTypes = new ArrayList<>();
            bo.setChannelTypes(channelTypes);
            if (CollectionUtil.isNotEmpty(itemChannelTypeDOS)) {
                for (ItemChannelTypeDO itemChannelTypeDO : itemChannelTypeDOS) {
                    ChannelInfo channelInfo = new ChannelInfo();
                    channelInfo.setChannelName(itemChannelTypeDO.getChannelTypeName());
                    channelInfo.setChannelType(itemChannelTypeDO.getChannelType());

                    channelTypes.add(channelInfo);
                }
            }
        }

        return Result.buildSuccessResult(bo);
    }
}
