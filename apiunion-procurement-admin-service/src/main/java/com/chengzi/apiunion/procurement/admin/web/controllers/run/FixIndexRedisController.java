package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.redis.StringRedisTemplate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.pojo.IndexTabDO;
import com.chengzi.apiunion.index.service.IndexTabService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.springframework.data.redis.core.HashOperations;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/11/18 16:45
 */
public class FixIndexRedisController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        IndexTabService indexTabService = BeanFactory.getBean(IndexTabService.class);
        List<IndexTabDO> tabList = indexTabService.queryIndexTabFromRedis(RequestContext.getRouteId());
        StringRedisTemplate<String> redisTemplate = BeanFactory.getBean("indexRedisTemplate");
        HashOperations<String, String, String> ops = redisTemplate.opsForHash();
        for (IndexTabDO tab : tabList) {
            List<String> values = ops.values(String.valueOf(tab.getId()));
        }
        return null;
    }
}
