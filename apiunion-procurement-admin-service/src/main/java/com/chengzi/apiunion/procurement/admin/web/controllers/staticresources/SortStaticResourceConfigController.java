package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.staticresource.SortStaticResourceConfigForm;
import com.chengzi.apiunion.staticresource.pojo.StaticResourcesConfigDO;
import com.chengzi.apiunion.staticresource.query.StaticResourcesConfigQuery;
import com.chengzi.apiunion.staticresource.service.StaticResourcesConfigService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.codec.binary.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     排序
 * </p>
 *
 * @author 爱夏
 * @date 2018/1/16
 */
public class SortStaticResourceConfigController extends AbstractApiController<SortStaticResourceConfigForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SortStaticResourceConfigForm command) throws Exception {
        List<Long> configIds = command.getSortIds();
        StaticResourcesConfigService staticResourcesConfigService = BeanFactory.getBean(StaticResourcesConfigService.class);
        StaticResourcesConfigQuery query = new StaticResourcesConfigQuery();
        query.setIds(configIds);
        List<StaticResourcesConfigDO> configList = staticResourcesConfigService.queryResourcesConfigDO(query);
        if (CollectionUtil.isEmpty(configList)) {
            return Result.buildIllegalArgumentResult("没有找到这些配置项");
        }

        String key = configList.get(0).getKey();
        long tplId = configList.get(0).getTplId();
        Map<Long, StaticResourcesConfigDO> configMap = new HashMap<>();
        for (StaticResourcesConfigDO configDO : configList) {
            configMap.put(configDO.getId(), configDO);
            if (!StringUtils.equals(configDO.getKey(), key) || configDO.getTplId() != tplId) {
                return Result.buildIllegalArgumentResult("这些配置存在不同的key值或者模板类型，不允许排序");
            }
        }
        int order = 0;
        for (long configId : configIds) {
            StaticResourcesConfigDO configDO = configMap.get(configId);
            if (configDO != null) {
                configDO.setConfigOrder(order++);
            }
        }
        staticResourcesConfigService.updateConfigOrder(configMap.values());
        return Result.buildSuccessMsg("执行成功！");
    }
}
