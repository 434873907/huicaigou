package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/10/14 15:25
 */
public class CheckItemStatusController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        itemSkuService.checkItemStatus(CollectionUtil.asArrayList(command.getId()));
        return Result.buildSuccessResult("检查成功");
    }
}
