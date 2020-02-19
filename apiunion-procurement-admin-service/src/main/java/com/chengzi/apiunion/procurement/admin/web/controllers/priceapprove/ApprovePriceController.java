package com.chengzi.apiunion.procurement.admin.web.controllers.priceapprove;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.priceapprove.ApprovePriceForm;
import com.chengzi.apiunion.supplier.common.priceapprove.enums.SkuPriceApproveStatusEnum;
import com.chengzi.apiunion.supplier.common.priceapprove.service.SkuPriceApproveService;
import com.chengzi.apiunion.supplier.common.priceapprove.util.SupplierPriceApproveShardUtil;
import com.chengzi.common.data.support.BigDecimalRange;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovePriceController extends AbstractApiController<ApprovePriceForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, ApprovePriceForm command) throws Exception {
        SkuPriceApproveService priceApproveService = BeanFactory.getBean(SkuPriceApproveService.class);
        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        BigDecimal minProfitMargin = new BigDecimal(configService.getConfigValue(ConfigKeyEnums.MIN_PROFIT_MARGIN));

        List<ApprovePriceForm.ApproveParam> approveParams = command.getApproveParams();
        Map<Long, List<Long>> shardMap = new HashMap<>();
        approveParams.forEach(approveParam -> {
            SupplierPriceApproveShardUtil.getShardId(approveParam.getSupplierId());
            shardMap.computeIfAbsent(SupplierPriceApproveShardUtil.getShardId(approveParam.getSupplierId()),
                    k -> new ArrayList<>()).add(approveParam.getApproveId());
        });

        if (command.getStatus() == SkuPriceApproveStatusEnum.PASS.getCode()) {
            return priceApproveService.approvePrice(shardMap, true, command.getDesc());
        } else if (command.getStatus() == SkuPriceApproveStatusEnum.REJECT.getCode()) {
            return priceApproveService.approvePrice(shardMap, false, command.getDesc());
        } else {
            return Result.buildOpFailedResult("状态错误");
        }

    }
}
