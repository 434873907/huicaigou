package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.staticrresources.ConfigTplBO;
import com.chengzi.apiunion.staticresource.enums.ConfigTplTypeEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.springframework.validation.BindException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2017/5/18
 */
public class ConfigTplListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {

        List<ConfigTplBO> tplList = new ArrayList<>();
        for (ConfigTplTypeEnum tpl : ConfigTplTypeEnum.values()) {

            ConfigTplBO tplBO = new ConfigTplBO();
            tplBO.setTplId(tpl.getTplId());
            tplBO.setTplName(tpl.getTplName());
            tplBO.setCurrentKey(tpl.getCurrentKey());
            tplList.add(tplBO);
        }

        return Result.buildSuccessResult(tplList);
    }
}
