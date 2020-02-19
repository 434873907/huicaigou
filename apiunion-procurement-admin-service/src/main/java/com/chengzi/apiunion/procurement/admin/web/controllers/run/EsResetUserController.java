package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.user.event.pojo.UserUpdateEvent;
import com.chengzi.apiunion.user.pojo.search.UserSearchBO;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.brand.event.pojo.BrandUpdateEvent;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 行者
 */
public class EsResetUserController extends AbstractManageController<EmptyForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SearchScrollable searchScrollable = BeanFactory.getBeansOfType(SearchScrollable.class).get("userSearchServiceImpl");
        searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<UserSearchBO>() {
            @Override
            public void onScrolled(ScrollResult<UserSearchBO> scrollResult) {
                for (UserSearchBO bo : scrollResult.getData()) {
                    EventBusFactory.getSyncEventBus().post(new UserUpdateEvent(bo.getId()));
                }
            }
        });
        return Result.buildSuccessResult("更新成功");
    }
}
