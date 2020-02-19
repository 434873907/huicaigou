/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.partneruser.fetch;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chengzi.apiunion.common.context.LoginUserFetch;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.common.data.cipher.token.LoginToken;

/**
 * 商户用户获取实现类
 * 
 * @author Kolor
 */
@Component
public class PartnerUserFetch implements LoginUserFetch<PartnerUserDO> {

    @Autowired
    private PartnerUserService partnerUserService;

    @Override
    public String getNickName(PartnerUserDO user) {
        return StringUtils.isNotBlank(user.getNickName()) ? user.getNickName() : user.getAccount();
    }

    @Override
    public PartnerUserDO getLoginUser(LoginToken loginToken) {
        return partnerUserService.getUserById(loginToken.getUserId());
    }

    @Override
    public long getLastChangePwdTime(PartnerUserDO user) {
        Date lastPwdChangeTime = user.getLastPwdChangeTime();
        return lastPwdChangeTime != null ? lastPwdChangeTime.getTime() : 0L;
    }

    @Override
    public boolean isValid(PartnerUserDO user) {
        return !user.isDeleted();
    }

    @Override
    public int getUserBusinessType(PartnerUserDO partnerUserDO) {
        return 0;
    }

}
