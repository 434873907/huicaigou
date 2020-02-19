package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.user.event.pojo.UserUpdateEvent;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author 月汐
 * @date 2019/11/20 13:52
 */
public class EsClearAndRefreshUserController extends AbstractManageController<EmptyForm> {

    private static final int                        LIMIT      = 200;

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);

        long updateUserNum = 0;
        long startId = 0;
        Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.USER);
        DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.USER.getShardStrategy();
        for (String tableName : tableNames) {
            int idx = StringUtils.lastIndexOf(tableName, "_");
            int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));

            long shardUserId = suffixNum * shardStrategy.getDivisor();

            while (true) {
                List<UserDO> userList = userService.getUsers(startId, LIMIT, shardUserId);

                if (CollectionUtil.isNotEmpty(userList)) {
                    for (UserDO user : userList) {
                        EventBusFactory.getSyncEventBus().post(new UserUpdateEvent(user.getId()));
                        if (user.getId() > startId) {
                            startId = user.getId();
                        }
                    }
                    updateUserNum += userList.size();
                } else {
                    break;
                }
            }
        }
        return Result.buildSuccessResult(updateUserNum);
    }
}
