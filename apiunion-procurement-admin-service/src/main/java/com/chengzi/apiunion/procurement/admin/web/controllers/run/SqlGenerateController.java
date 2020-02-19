/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.mybatis.util.SqlGenerator;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.procurement.admin.web.formbean.run.SqlGenerateForm;
import com.chengzi.common.data.validate.Result;

/**
 * 生成SQL
 * 
 * @author Kolor
 */
public class SqlGenerateController extends AbstractManageController<SqlGenerateForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, SqlGenerateForm command) throws Exception {
        return Result.buildSuccessResult(SqlGenerator.generateForDDL(command.getSql()));
    }

}
