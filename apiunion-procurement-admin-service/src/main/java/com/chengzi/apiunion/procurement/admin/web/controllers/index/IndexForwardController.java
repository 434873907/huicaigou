package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.summercool.web.views.string.TextView;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.ContextEnvUtil;
import com.chengzi.common.util.HttpWrap;
import com.chengzi.common.util.PropertyUtil;
import com.chengzi.common.util.StringUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 苏子
 * @date 2018年11月1日
 */
public class IndexForwardController extends AbstractApiController<EmptyForm> {
    private static HttpWrap httpWrap = new HttpWrap(15000, 30000);

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        //        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        //        SystemInfo systemInfo = configService.getAndParseConfigValue(ConfigKeyEnums.SYSTEM_INFO);
        String url;

        StringBuilder params = new StringBuilder();
        params.append("api=index.getIndexTab")
                .append("&appv=1.0")
                .append("&appId=admin_" + RequestContext.getRouteId())
                .append("&param={\"platForm\":\"admin\"}")
                .append("&t=").append(System.currentTimeMillis())
                .append("&v=1.0")
                .append("&body&secret");
        String sign = DigestUtils.md5DigestAsHex(params.toString().getBytes());

        String apiDomainInProp = PropertyUtil.getProperty("api.domain");
        String defaultApiDomain = "api.apiunion.com";
        if (ContextEnvUtil.isDev()) {
            defaultApiDomain = "apitest.apiunion.com";
        }
        url = String.format("http://%s/api.do?%s&sign=%s", StringUtils.isNotBlank(apiDomainInProp) ? apiDomainInProp : defaultApiDomain,
                params.toString(), sign);

        try {
            String result = httpWrap.httpGet(url);
            return Result.buildSuccessMsg(result);
        } catch (Exception e) {
            LOGGER.error(StringUtil.buildStatLog("IndexForwardFail", RequestContext.getRouteId(), e), e);
            return Result.buildOpFailedResult("获取失败");
        }
    }

    @Override
    protected ModelAndView wrapView(Result<?> result) {
        return new ModelAndView(new TextView(result.getMessage()));
    }

}
