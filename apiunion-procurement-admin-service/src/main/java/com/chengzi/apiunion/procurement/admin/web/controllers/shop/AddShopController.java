package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.AddShopForm;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.beans.BaseDO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * 新增店铺
 *
 * @author 月汐
 * @date 2018/11/15 15:54
 */
public class AddShopController extends AbstractApiController<AddShopForm> {
    public static final Pattern DAY_PATTERN = Pattern.compile("^[1-9]\\d*$|[1-9]\\d*-[1-9]\\d*");
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddShopForm command) throws Exception {
        ShopService shopService = BeanFactory.getBean(ShopService.class);

        ShopDO shopDO = new ShopDO();
        shopDO.setIsDeleted(BaseDO.NOT_DELETED);
        shopDO.setName(command.getName());
        shopDO.setLogoUrl(command.getLogoUrl());
        shopDO.setDesc(command.getDesc());
        shopDO.setIsSelf(command.getIsSelf());

        if (!DAY_PATTERN.matcher(command.getDeliverDays()).find()) {
            return Result.buildIllegalArgumentResult("发货时间不合法");
        }
        if (!DAY_PATTERN.matcher(command.getArrivalDays()).find()) {
            return Result.buildIllegalArgumentResult("到货时间不合法");
        }
        shopDO.setDeliverDays(command.getDeliverDays());
        shopDO.setArrivalDays(command.getArrivalDays());
        shopDO.setDefaultCurrency(command.getCurrency().getCode());
        shopDO.setStatus(command.getStatus());

        return shopService.add(shopDO);
    }


}
