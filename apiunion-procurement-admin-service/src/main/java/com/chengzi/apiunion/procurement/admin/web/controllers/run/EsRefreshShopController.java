package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.shop.event.pojo.ShopUpdateEvent;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.client.util.CollectionUtil;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/10/25 11:41
 */
public class EsRefreshShopController extends AbstractManageController<EmptyForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        List<ShopDO> list = shopService.queryAll();
        for (ShopDO shopDO : list) {
            EventBusFactory.getSyncEventBus().post(new ShopUpdateEvent(shopDO.getId()));
        }
        return null;
    }
}
