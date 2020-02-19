/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.module.sequence.service.SequenceService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 清除Redis中的序列缓存
 * 
 * @author Kolor
 */
public class ClearRedisReqController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SequenceService sequenceService = BeanFactory.getBean(SequenceService.class);
        sequenceService.clearCache();
        return Result.buildSuccessMsg("清除成功");
    }

}
