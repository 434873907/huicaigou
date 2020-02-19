/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.summercool.hsf.util.UUIDUtil;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.enums.StatusEnum;
import com.chengzi.apiunion.common.util.RegexUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.PresetPriceStrategyTypeEnum;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdSyncItemAuthDO;
import com.chengzi.apiunion.item.strategy.presetprice.PercentPresetPriceStrategy;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.AddSupplierForm;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 添加供应商
 * 
 * @author Kolor
 *
 */
public class AddSupplierController extends AbstractApiController<AddSupplierForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddSupplierForm command) throws Exception {
        ThirdSyncItemAuthDO thirdSyncItemAuthDO = null;
        if (StringUtils.isNotBlank(command.getPhone())) {
            if (!RegexUtil.isMobile(command.getPhone())) {
                return Result.buildIllegalArgumentResult("手机号码格式不正确");
            }
        }
        if (command.getApiType() != null && command.getApiType() != ThirdSyncItemTypeEnum.NULL_EXP) {//判断是否是需要同步的供应商类型
            if (command.getApiType() == ThirdSyncItemTypeEnum.SUPER_EXP) {
                if (StringUtils.isBlank(command.getAccount())) {
                    return Result.buildIllegalArgumentResult("登录账号未填写");
                } else if (StringUtils.isBlank(command.getPassword())) {
                    return Result.buildIllegalArgumentResult("登录密码未填写");
                }
                ThirdSyncApiAuth apiAuth = new ThirdSyncApiAuth();
                apiAuth.setAppId(UUIDUtil.random());
                apiAuth.setSecretKey(UUIDUtil.random());
                command.setApiAuth(apiAuth);
            } else {
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

            if (command.getApiType() == null && CollectionUtil.isEmpty(command.getChannelTypes())) {
                return Result.buildIllegalArgumentResult("请选择渠道");
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
            thirdSyncItemAuthDO.setStatus(StatusEnum.ONE.getCode());
        }

        int apiType = 0;
        if (command.getApiType() != null) {
            apiType = command.getApiType().getCode();
        }
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        SupplierDO supplierDO = new SupplierDO();
        supplierDO.setSupplierName(command.getSupplierName());
        supplierDO.setApiType(apiType);
        supplierDO.setStatus(command.getStatus());
        supplierDO.setAccount(command.getAccount());
        supplierDO.setPassword(command.getPassword());
        supplierDO.setContacts(command.getContacts());
        supplierDO.setPhone(command.getPhone());
        return supplierService.addSupplier(supplierDO, command.getChannelTypes(),
                                            thirdSyncItemAuthDO, RequestContext.getUserId());
    }

    public static void main(String[] args) {
        AddSupplierForm form = new AddSupplierForm();
        form.setAccount("13366146190");
        form.setPassword("123456");
//        form.setApiType(ThirdSyncItemTypeEnum.NULL_EXP.getCode());
        form.setSupplierName("zhiyuantest");
//        form.set

        System.out.println(JSON.toJSONString(form));
    }
}
