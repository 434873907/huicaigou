package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.data.redis.StringRedisTemplate;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.event.pojo.ItemsDeletedEvent;
import com.chengzi.apiunion.item.event.pojo.ItemsUpdatedEvent;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.IdsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ES部分商品重置
 *
 * @author 行者
 */
public class EsResetSomeItemController extends AbstractManageController<IdsForm> {

    @Autowired
    @Qualifier("itemRedisTemplate")
    private StringRedisTemplate<ItemCO> itemRedisTemplate;

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, IdsForm command) {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        for (long itemId : command.getIds()) {
            ItemRichDO itemRich = itemService.getItemRich(itemId);
            if (itemRich == null) {
                EventBusFactory.getSyncEventBus().post(new ItemsDeletedEvent(CollectionUtil.asArrayList(itemId)));
            }
        }
        EventBusFactory.getSyncEventBus().post(new ItemsUpdatedEvent(command.getIds()));
        itemRedisTemplate.delete(CollectionUtil.toStringListIgnoreNull(command.getIds()));
        return Result.buildSuccessResult("更新成功");
    }
}
