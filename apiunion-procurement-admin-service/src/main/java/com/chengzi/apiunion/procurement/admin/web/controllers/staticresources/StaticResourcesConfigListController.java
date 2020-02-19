package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.staticresource.StaticResourcesConfigListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.staticrresources.StaticResourcesConfigListBO;
import com.chengzi.apiunion.staticresource.pojo.StaticResourcesConfigDO;
import com.chengzi.apiunion.staticresource.query.StaticResourcesConfigQuery;
import com.chengzi.apiunion.staticresource.service.StaticResourcesConfigService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date: 17/4/18
 */
public class StaticResourcesConfigListController extends AbstractApiController<StaticResourcesConfigListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, StaticResourcesConfigListForm command) throws Exception {
        StaticResourcesConfigService staticResourcesConfigService = BeanFactory.getBean(StaticResourcesConfigService.class);
        StaticResourcesConfigQuery query = new StaticResourcesConfigQuery();
        query.setIsDeleted(0);
        query.setShow(command.getShow());
        query.setKey(command.getKey());
        query.setTplId(command.getTplId());
        query.setConfigStatus(-1);
        List<StaticResourcesConfigDO> configs = staticResourcesConfigService.queryResourcesConfigDO(query);
        List<StaticResourcesConfigListBO> configListBOs = StaticResourcesConfigListBO.convertToBOList(configs);
        if (CollectionUtil.isNotEmpty(configListBOs)) {
            Collections.sort(configListBOs, new Comparator<StaticResourcesConfigListBO>() {
                @Override
                public int compare(StaticResourcesConfigListBO o1, StaticResourcesConfigListBO o2) {
                    if (o1.getOrder() != o2.getOrder()) {
                        return o1.getOrder() - o2.getOrder();
                    }

                    if (o1.getConfigType() != o2.getConfigType()) {
                        return o1.getConfigType() - o2.getConfigType();
                    }
                    return o1.getDefaultType() - o2.getDefaultType();
                }
            });
        }

        PageDataList<StaticResourcesConfigListBO> pageDataList = new PageDataList<>();
        pageDataList.setDataList(configListBOs);
        pageDataList.setTotalPage(1);
        pageDataList.setTotal(configListBOs.size());
        return Result.buildSuccessResult(pageDataList);
    }
}
