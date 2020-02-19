package com.chengzi.apiunion.procurement.admin.web.controllers.hotkeyword;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.hotkeyword.pojo.HotKeywordDO;
import com.chengzi.apiunion.hotkeyword.service.HotKeywordService;
import com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword.GetHotKeywordByIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.hotkeyword.HotKeywordBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取热门关键词详情
 *
 * @author 月汐
 * @date 2018/12/4 14:20
 */
public class GetHotKeywordByIdController extends AbstractApiController<GetHotKeywordByIdForm> {
    @Override
    protected Result<HotKeywordBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetHotKeywordByIdForm command) throws Exception {
        HotKeywordService hotKeywordService = BeanFactory.getBean(HotKeywordService.class);
        HotKeywordDO hotKeywordDO = hotKeywordService.getById(command.getId());
        return Result.buildSuccessResult(HotKeywordBO.convert(hotKeywordDO));
    }
}
