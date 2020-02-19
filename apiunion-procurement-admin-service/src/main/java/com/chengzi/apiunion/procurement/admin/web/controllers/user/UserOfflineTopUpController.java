package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.service.UserAccountService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.UserOfflineTopUpForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/12/20 15:41
 */
public class UserOfflineTopUpController extends AbstractApiController<UserOfflineTopUpForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UserOfflineTopUpForm command) throws Exception {
        UserAccountService userAccountService = BeanFactory.getBean(UserAccountService.class);

        return userAccountService.offlineTopUp(command.getAccount(), command.getAmount(), command.getDesc());
    }
}
