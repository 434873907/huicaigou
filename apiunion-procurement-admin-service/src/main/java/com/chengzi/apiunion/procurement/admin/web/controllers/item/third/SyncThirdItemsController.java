/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item.third;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.event.pojo.ThirdItemSyncEvent;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdsForm;

/**
 * 批量同步第三方商品
 * 
 * @author Kolor
 */
public class SyncThirdItemsController extends AbstractApiController<IdsForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdsForm command) throws Exception {
        EventBusFactory.getMQEventBus().post(new ThirdItemSyncEvent(command.getIds()));
        return Result.buildSuccessMsg("商品同步任务异常进行中");
    }

}
