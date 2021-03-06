package com.chengzi.apiunion.procurement.admin.web.controllers.hotkeyword;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.hotkeyword.pojo.HotKeywordDO;
import com.chengzi.apiunion.hotkeyword.service.HotKeywordService;
import com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword.UpdateHotKeywordForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新热门关键词
 *
 * @author 月汐
 * @date 2018/12/4 14:18
 */
public class UpdateHotKeywordController extends AbstractApiController<UpdateHotKeywordForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateHotKeywordForm command) throws Exception {
        HotKeywordService hotKeywordService = BeanFactory.getBean(HotKeywordService.class);
        HotKeywordDO hotKeywordDO = new HotKeywordDO();
        hotKeywordDO.setId(command.getId());
        hotKeywordDO.setName(command.getName());
        hotKeywordDO.setJumpType(command.getJumpType());
        hotKeywordDO.setJumpValue(command.getJumpValue());
        return hotKeywordService.update(hotKeywordDO);
    }
}
