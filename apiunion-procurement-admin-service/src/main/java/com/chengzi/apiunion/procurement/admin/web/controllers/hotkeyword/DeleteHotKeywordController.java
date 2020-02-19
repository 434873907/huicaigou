package com.chengzi.apiunion.procurement.admin.web.controllers.hotkeyword;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.hotkeyword.service.HotKeywordService;
import com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword.DeleteHotKeywordForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除热门关键词
 *
 * @author 月汐
 * @date 2018/12/4 14:17
 */
public class DeleteHotKeywordController extends AbstractApiController<DeleteHotKeywordForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteHotKeywordForm command) throws Exception {
        HotKeywordService hotKeywordService = BeanFactory.getBean(HotKeywordService.class);
        return hotKeywordService.delete(command.getId());
    }
}
