/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.currency.pojo.CurrencyExchangeRateDO;
import com.chengzi.apiunion.common.module.currency.service.CurrencyExchangeRateService;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.common.CurrencyBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 货币列表
 * 
 * @author Kolor
 */
public class CurrencyListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        CurrencyExchangeRateService exchangeRateService = BeanFactory.getBean(CurrencyExchangeRateService.class);

        List<CurrencyEnum> supportCurrencys = configService.getAndParseConfigValue(ConfigKeyEnums.SUPPORT_CURRENCYS);

        List<CurrencyBO> ccyList = new ArrayList<>();
        for (CurrencyEnum ccyEnum : supportCurrencys) {
            CurrencyBO ccy = new CurrencyBO();
            ccy.setCode(ccyEnum.getCode());
            ccy.setName(ccyEnum.getName());
            ccy.setSymbol(ccyEnum.getSymbol());
            CurrencyExchangeRateDO rateDO = exchangeRateService.getCurrencyRateWithCache(ccyEnum.getCode());
            if (rateDO != null) {
                ccy.setRate(rateDO.getRate());
            }
            ccyList.add(ccy);
        }
        return Result.buildSuccessResult(ccyList);
    }

}
