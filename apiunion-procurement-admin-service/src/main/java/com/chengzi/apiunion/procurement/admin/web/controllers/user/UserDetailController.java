package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.pojo.UserAccountDO;
import com.chengzi.apiunion.account.service.UserAccountService;
import com.chengzi.apiunion.common.data.enums.UserTypeEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.user.UserDetailBO;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/10/30 20:43
 */
public class UserDetailController extends AbstractApiController<IdForm> {
    @Override
    protected Result<UserDetailBO> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);
        UserAccountService userAccountService = BeanFactory.getBean(UserAccountService.class);

        UserDO user = userService.getUserById(command.getId());
        UserAccountDO userAccount = userAccountService.getUserAccount(command.getId());

        UserDetailBO bo = new UserDetailBO();
        bo.setUserId(user.getId());
        bo.setPhone(user.getAccount());
        bo.setRegisterTime(DateUtil.formatDate(user.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setUserName(user.getNickName());
        bo.setContacts(user.getContacts());
        bo.setUserType(EnumUtil.parse(UserTypeEnum.class, user.getUserType()).getRemark());
        bo.setEmail(user.getEmail());
        bo.setStatus(user.getIsDisable());
        bo.setBalance(userAccount.getBalance().toString());
        bo.setRemark(user.getRemark());
        bo.setLoginAccount(user.getLoginAccount());
        bo.setWxNickName(user.getWxNickName());
        bo.setInvitationCode(user.getInvitationCode());
        bo.setInvitationCodeCreator(user.getInvitationCodeCreator());

        return Result.buildSuccessResult(bo);
    }
}
