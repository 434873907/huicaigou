package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.brand.event.pojo.BrandDeletedEvent;
import com.chengzi.apiunion.brand.event.pojo.BrandUpdateEvent;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/08/13 12:06
 */
public class EsResetBrandController extends AbstractManageController<EmptyForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SearchScrollable searchScrollable = BeanFactory.getBeansOfType(SearchScrollable.class).get("brandSearchServiceImpl");
        searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<BrandSearchBO>() {
            @Override
            public void onScrolled(ScrollResult<BrandSearchBO> scrollResult) {
                for (BrandSearchBO bo : scrollResult.getData()) {
                    if (bo.getIsDeleted() == 0) {
                        EventBusFactory.getSyncEventBus().post(new BrandUpdateEvent(bo.getId()));
                    } else {
                        EventBusFactory.getSyncEventBus().post(new BrandDeletedEvent(bo.getId()));
                    }
                }
            }
        });
        return Result.buildSuccessResult("更新成功");
    }
}
