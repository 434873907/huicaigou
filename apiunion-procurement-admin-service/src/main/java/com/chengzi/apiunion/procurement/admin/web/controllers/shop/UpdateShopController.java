package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.UpdateShopForm;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新店铺信息
 *
 * @author 月汐
 * @date 2018/11/15 17:35
 */
public class UpdateShopController extends AbstractApiController<UpdateShopForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateShopForm command) throws Exception {
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        ShopDO shopDO = new ShopDO();
        shopDO.setId(command.getId());
        shopDO.setName(command.getName());
        shopDO.setLogoUrl(command.getLogoUrl());
        shopDO.setDesc(command.getDesc());
        shopDO.setIsSelf(command.getIsSelf());
        shopDO.setDeliverDays(command.getDeliverDays());
        shopDO.setArrivalDays(command.getArrivalDays());
        shopDO.setDefaultCurrency(command.getCurrency().getCode());
        shopDO.setStatus(command.getStatus());
        return shopService.update(shopDO);
    }
}
