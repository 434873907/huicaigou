package com.chengzi.apiunion.procurement.pipeline;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.summercool.web.servlet.BeanFactory;
import org.summercool.web.servlet.pipeline.PreProcessPipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2018/11/5 14:02
 */
public class FunctionPermissionPipeline implements PreProcessPipeline {

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    private int order;
    private List<String> whiteList = new ArrayList<>();

    @Override
    public boolean isPermitted(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        List<String> permissions = service.getFunctionPermissionsInCache(RequestContext.getUserId());
        String uri = httpServletRequest.getRequestURI();
        for (String whiteUri : whiteList) {
            if (StringUtils.isNotBlank(whiteUri) && pathMatcher.match(whiteUri, uri)) {
                return true;
            }
        }
        for (String permission : permissions) {
            if (StringUtils.isNotBlank(permission) && pathMatcher.match(permission, uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ModelAndView handleProcessInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
