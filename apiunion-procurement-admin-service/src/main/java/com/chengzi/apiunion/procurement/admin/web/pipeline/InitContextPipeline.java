/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pipeline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.summercool.web.servlet.pipeline.PreProcessPipeline;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.common.data.enums.PlatformEnum;

/**
 * 上下文初始化
 * 
 * @author Kolor
 */
public class InitContextPipeline implements PreProcessPipeline {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean isPermitted(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestContext.setPlatform(PlatformEnum.PROCUREMENT_ADMIN.getCode());
        return true;
    }

    @Override
    public ModelAndView handleProcessInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

}
