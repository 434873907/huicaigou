package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.GetShopDetailForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.shop.ShopBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取店铺详情
 *
 * @author 月汐
 * @date 2018/11/15 17:11
 */
public class GetShopDetailController extends AbstractApiController<GetShopDetailForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetShopDetailForm command) throws Exception {
        ShopService shopService = BeanFactory.getBean(ShopService.class);

        ShopDO shopDO = shopService.getByIdWithCache(command.getId());

        return Result.buildSuccessResult(ShopBO.convert(shopDO));
    }

}
