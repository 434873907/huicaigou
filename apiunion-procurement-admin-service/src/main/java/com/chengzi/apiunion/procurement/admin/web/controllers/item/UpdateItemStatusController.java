/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemOfflineTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UpdateItemStatusForm;
import com.chengzi.common.data.validate.Result;

/**
 * 更新商品状态
 * 
 * @author Kolor
 */
public class UpdateItemStatusController extends AbstractApiController<UpdateItemStatusForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateItemStatusForm command) throws Exception {
        if (command.getStatus() == ItemStatusEnum.INIT) {
            return Result.buildOpFailedResult("不支持更新状态为待审核");
        }
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        int beforeUpdateItemNum = command.getIds().size();
        itemService.updateItemStatus(command.getIds(), command.getStatus(), ItemOfflineTypeEnum.NORMAL, "更新商品状态 UpdateItemStatusController");
        int afterUpdateItemNum = command.getIds().size();
        if (beforeUpdateItemNum == afterUpdateItemNum) {
            return Result.buildSuccessMsg("设置成功");
        } else {
            if (beforeUpdateItemNum == 1){
                return Result.buildOpFailedResult("设置失败,商品品牌、类目或供应商禁用或被删除，无法上架");
            }
            return Result.buildSuccessMsg("设置成功,但部分商品品牌、类目或供应商禁用或被删除，无法上架");
        }
    }

}