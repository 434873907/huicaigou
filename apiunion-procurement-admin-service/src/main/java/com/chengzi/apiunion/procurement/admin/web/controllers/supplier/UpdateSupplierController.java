package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.util.RegexUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.PresetPriceStrategyTypeEnum;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ThirdSyncItemAuthDO;
import com.chengzi.apiunion.item.strategy.presetprice.PercentPresetPriceStrategy;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.UpdateSupplierForm;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;

/**
 * 更新供应商
 *
 * @author 月汐
 * @author Kolor
 * @date 2018/11/23 19:42
 */
public class UpdateSupplierController extends AbstractApiController<UpdateSupplierForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateSupplierForm command) throws Exception {
        ThirdSyncItemAuthDO thirdSyncItemAuthDO = null;
        if (StringUtils.isNotBlank(command.getPhone())) {
            if (!RegexUtil.isMobile(command.getPhone())) {
                return Result.buildIllegalArgumentResult("手机号码格式不正确");
            }
        }
        if (command.getApiType() != null && command.getApiType() != ThirdSyncItemTypeEnum.NULL_EXP) {
            if (command.getApiType() != ThirdSyncItemTypeEnum.SUPER_EXP) {
                if (command.getApiAuth() == null) {
                    return Result.buildIllegalArgumentResult("API授权信息未填写");
                } else if (StringUtils.isBlank(command.getApiAuth().getAppId())) {
                    return Result.buildIllegalArgumentResult("appId未填写");
                } else if (StringUtils.isBlank(command.getApiAuth().getSecretKey())) {
                    return Result.buildIllegalArgumentResult("secretKey未填写");
                }
            }
            if (command.getTargetItemAttr() == null) {
                return Result.buildIllegalArgumentResult("目标店铺和运费模板未设置");
            } else if (command.getTargetItemAttr().getShopId() == null) {
                return Result.buildIllegalArgumentResult("目标店铺未设置");
            } else if (command.getTargetItemAttr().getExpressFeeTemplateId() == null) {
                return Result.buildIllegalArgumentResult("运费模板未设置");
            }

            thirdSyncItemAuthDO = new ThirdSyncItemAuthDO();
            thirdSyncItemAuthDO.setAppId(command.getApiAuth().getAppId());
            thirdSyncItemAuthDO.setApiAuth(command.getApiAuth().toJsonString());
            thirdSyncItemAuthDO.setApiType(command.getApiType().getCode());
            if (StringUtils.isNotBlank(command.getPriceStrategy())) {
                try {
                    PresetPriceStrategy priceStrategy = PresetPriceStrategy.fromJson(command.getPriceStrategy());
                    if (priceStrategy != null) {
                        if (priceStrategy.getType() == PresetPriceStrategyTypeEnum.PERCENT.getCode()) {
                            //百分比改为小数点比例
                            BigDecimal priceUpPercent = ((PercentPresetPriceStrategy) priceStrategy).getPriceUpPercent().divide(new BigDecimal(100),
                                    4, BigDecimal.ROUND_HALF_UP);
                            BigDecimal originalPriceUpPercent = ((PercentPresetPriceStrategy) priceStrategy).getOriginalPriceUpPercent()
                                    .divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP);
                            ((PercentPresetPriceStrategy) priceStrategy).setPriceUpPercent(priceUpPercent);
                            ((PercentPresetPriceStrategy) priceStrategy).setOriginalPriceUpPercent(originalPriceUpPercent);
                        }
                        thirdSyncItemAuthDO.setPriceStrategy(priceStrategy.toJsonString());
                    }
                } catch (Exception e) {
                    return Result.buildIllegalArgumentResult("价格策略格式不正确");
                }
            }
            if (command.getSourceItemAttr() != null) {
                thirdSyncItemAuthDO.setSourceItemAttr(command.getSourceItemAttr().toJsonString());
            }
            if (command.getTargetItemAttr() != null) {
                thirdSyncItemAuthDO.setTargetItemAttr(command.getTargetItemAttr().toJsonString());
            }
            thirdSyncItemAuthDO.setStatus(command.getStatus());
        }

        SupplierService service = BeanFactory.getBean(SupplierService.class);
        SupplierDO supplierDO = service.getSupplier(command.getId());
        if (supplierDO == null) {
            return Result.buildIllegalArgumentResult("供应商不存在");
        }
        int apiType =0;
        if (command.getApiType() != null) {
            apiType = command.getApiType().getCode();
        }
        supplierDO.setSupplierName(command.getSupplierName());
        supplierDO.setApiType(apiType);
        supplierDO.setStatus(command.getStatus());
        supplierDO.setPassword(command.getPassword());
        supplierDO.setContacts(command.getContacts());
        supplierDO.setPhone(command.getPhone());

        return service.updateSupplier(supplierDO, command.getChannelTypes(), thirdSyncItemAuthDO, RequestContext.getUserId());
    }

}
