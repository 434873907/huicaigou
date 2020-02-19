package com.chengzi.apiunion.procurement.admin.web.controllers.hotkeyword;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.hotkeyword.pojo.HotKeywordDO;
import com.chengzi.apiunion.hotkeyword.pojo.HotKeywordQuery;
import com.chengzi.apiunion.hotkeyword.service.HotKeywordService;
import com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword.QueryHotKeywordForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.hotkeyword.HotKeywordBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 热门关键词列表查询
 *
 * @author 月汐
 * @date 2018/12/4 14:19
 */
public class QueryHotKeywordController extends AbstractApiController<QueryHotKeywordForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryHotKeywordForm command) throws Exception {
        HotKeywordService hotKeywordService = BeanFactory.getBean(HotKeywordService.class);
        HotKeywordQuery query = new HotKeywordQuery();
        query.setName(command.getName());
        query.setStatus(command.getStatus());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());
        List<HotKeywordDO> hotKeywordDOList = hotKeywordService.queryKeyword(query);
        List<HotKeywordBO> hotKeywordBOList = new ArrayList<>();
        for (HotKeywordDO hotKeywordDO : hotKeywordDOList) {
            hotKeywordBOList.add(HotKeywordBO.convert(hotKeywordDO));
        }
        long total = hotKeywordService.countKeyword(query);
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), hotKeywordBOList));
    }
}
