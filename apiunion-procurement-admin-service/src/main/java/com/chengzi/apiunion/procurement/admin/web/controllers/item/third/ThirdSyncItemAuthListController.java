/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item.third;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.third.ThirdSyncItemAuthListForm;
import com.chengzi.common.data.validate.Result;

/**
 * 查询第三方商品同步授权信息
 * 
 * @author Kolor
 */
public class ThirdSyncItemAuthListController extends AbstractApiController<ThirdSyncItemAuthListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, ThirdSyncItemAuthListForm command) throws Exception {
        // TODO 未实现
        return null;
    }

}
