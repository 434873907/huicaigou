package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.pojo.UserAccountDO;
import com.chengzi.apiunion.account.service.UserAccountService;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.UserListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.user.UserListBO;
import com.chengzi.apiunion.user.enums.AccountTypeEnum;
import com.chengzi.apiunion.user.enums.AuditStatusEnum;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.pojo.search.UserSearchBO;
import com.chengzi.apiunion.user.pojo.search.query.UserSearchQuery;
import com.chengzi.apiunion.user.search.UserSearchService;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-31 16:41
 */
public class UserListController extends AbstractApiController<UserListForm> {

    @Override
    protected Result<PageDataList<UserListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, UserListForm command)
            throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);
        UserSearchService userSearchService = BeanFactory.getBean(UserSearchService.class);
        UserSearchQuery userSearchQuery = new UserSearchQuery();
        if (command.getType() != -1) {
            userSearchQuery.setCertificateType(command.getType());
        }
        if (StringUtils.isNotBlank(command.getName())) {
            userSearchQuery.setKeyword(command.getName());
        }
        userSearchQuery.setSize(command.getLimit());
        userSearchQuery.setFrom(command.getOffset());
        if (CollectionUtil.isNotEmpty(command.getAuditStatusList())) {
            userSearchQuery.setAuditStatus(command.getAuditStatusList());
        }
        userSearchQuery.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));
        userSearchQuery.setRemark(command.getRemark());
        SearchResult<UserSearchBO> searchResult = userSearchService.query(userSearchQuery);
        PageDataList pageDataList = new PageDataList();
        if (searchResult.getTotalHits() <= 0) {
            return Result.buildSuccessResult(pageDataList);
        }

        pageDataList.setTotal(searchResult.getTotalHits());
        pageDataList.setTotalPage(getTotalPage(searchResult.getTotalHits(), command.getLimit()));
        List<UserSearchBO> userSearchBOS = searchResult.getData();
        List<Long> userIds = new ArrayList<>();
        userSearchBOS.forEach(x -> userIds.add(x.getId()));

        List<UserDO> userDOS = userService.getUserByUserIds(userIds);
        Map<Long, UserDO> userMap = CollectionUtil.toMap(userDOS, "id");

        List<UserListBO> userListBOS = new ArrayList<>();
        userIds.forEach(x -> userListBOS.add(converter(userMap.get(x))));
        pageDataList.setDataList(userListBOS);
        return Result.buildSuccessResult(pageDataList);
    }

    private UserListBO converter(UserDO userDO) {
        UserAccountService userAccountService = BeanFactory.getBean(UserAccountService.class);
        UserAccountDO userAccount = userAccountService.getUserAccount(userDO.getId());

        UserListBO userListBO = new UserListBO();
        userListBO.setUserId(userDO.getId());
        userListBO.setChannel(userDO.getChannel());
        userListBO.setContact(userDO.getContacts());
        userListBO.setEmail(userDO.getEmail());
        userListBO.setUserType(AccountTypeEnum.parse(userDO.getCertificateType()).getRemark());
        userListBO.setRemark(userDO.getRemark());
        userListBO.setWxNickName(userDO.getWxNickName());
        if (userAccount != null) {
            userListBO.setBalance(userAccount.getBalance().toString());
        } else {
            userListBO.setBalance("0.00");
        }
        userListBO.setLevelName(userDO.getLevel() + "");
        userListBO.setDisable(userDO.getIsDisable());
        userListBO.setRegisterTime(userDO.getCreateTime());
        userListBO.setStatus(1);
        userListBO.setUserName(userDO.getNickName());
        userListBO.setUserPhone(userDO.getAccount());
        userListBO.setUserBankInfo(genUserBankInfo(userDO));
        userListBO.setCertificateType(userDO.getCertificateType());
        userListBO.setLoginAccount(userDO.getLoginAccount());
        return userListBO;
    }

    private String genUserBankInfo(UserDO userDO) {
        StringBuilder bankInfo = new StringBuilder();

        if (StringUtils.isNotBlank(userDO.getBankName())) {
            bankInfo.append(userDO.getBankName()).append(",");
        }
        if (StringUtils.isNotBlank(userDO.getBankAccount())) {
            bankInfo.append(userDO.getBankAccount()).append(",");
        }
        if (StringUtils.isNotBlank(userDO.getAccountOpener())) {
            bankInfo.append(userDO.getAccountOpener());
        }

        return bankInfo.toString();
    }
}
