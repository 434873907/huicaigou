package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.UpdateShopStatusForm;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改店铺状态
 *
 * @author 月汐
 * @date 2018/11/15 19:37
 */
public class UpdateShopStatusController extends AbstractApiController<UpdateShopStatusForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateShopStatusForm command) throws Exception {
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        return shopService.updateStatus(command.getId(), command.getStatus());
    }
}
