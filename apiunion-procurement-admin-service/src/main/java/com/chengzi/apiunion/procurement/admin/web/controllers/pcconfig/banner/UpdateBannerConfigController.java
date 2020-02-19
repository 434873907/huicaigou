package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.banner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.jump.Jump;
import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.module.image.enums.ImageSizeLimitEnum;
import com.chengzi.apiunion.common.module.image.util.ImageSizeVerifyUtil;
import com.chengzi.apiunion.common.util.RegexUtil;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.banner.pojo.BannerConfigDO;
import com.chengzi.apiunion.pcconfig.banner.service.BannerConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.banner.UpdateBannerConfigForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class UpdateBannerConfigController extends AbstractApiController<UpdateBannerConfigForm>{

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBannerConfigForm command) throws Exception {
        long userId = RequestContext.getUserId();

        Result<String> checkJumpDataResult = checkJumpData(command.getJump());
        if (!checkJumpDataResult.isSuccess()) {
            return checkJumpDataResult;
        }

        BannerConfigService bannerConfigService = BeanFactory.getBean(BannerConfigService.class);
        BannerConfigDO bannerConfig = bannerConfigService.getBannerConfig(command.getId());
        if (bannerConfig == null) {
            return Result.buildOpFailedResult("数据不存在");
        }
        bannerConfig.setModifyUserId(userId);
        bannerConfig.setTitle(command.getTitle());
        bannerConfig.setImage(new Image(command.getImageUrl()).toJsonString());
        bannerConfig.setJump(command.getJump().toJsonString());
        bannerConfig.setStartTime(command.getStartTime());
        bannerConfig.setEndTime(command.getEndTime());
        bannerConfig.setStatus(command.getStatus());

        // 图片校验
        Result<String> result = ImageSizeVerifyUtil.verifyImageSize(command.getImageUrl(), ImageSizeLimitEnum.PC_CAROUSEL, null);
        if (!result.isSuccess()) {
            return result;
        }

        int row = bannerConfigService.updateBannerConfig(bannerConfig);
        if (row > 0) {
            return Result.buildSuccessMsg("修改成功");
        }
        return Result.buildOpFailedResult("修改失败");
    }

    private static Result<String> checkJumpData(Jump jump) {
        JumpTypeEnum jumpType = EnumUtil.parse(JumpTypeEnum.class, jump.getJumpType());
        switch (jumpType) {
            case PAGE_SEARCH:
                if (StringUtils.isBlank(jump.getJumpData())) {
                    return Result.buildIllegalArgumentResult("请输入搜索关键词");
                }
                break;
            case PAGE_WEB:
                if (!RegexUtil.isUrl(jump.getJumpData())) {
                    return Result.buildIllegalArgumentResult("链接地址不合法");
                }
                break;
            case ACTIVITY_DETAIL:
                try {
                    ActivityService activityService = BeanFactory.getBean(ActivityService.class);
                    long actId = Long.parseLong(jump.getJumpData());
                    ActivityDO activity = activityService.getActivity(actId);
                    if (activity == null) {
                        return Result.buildIllegalArgumentResult("所选活动不存在");
                    }
                } catch (Exception e) {
                    return Result.buildIllegalArgumentResult("请输入正确的活动ID");
                }
                break;
        }
        return Result.buildEmptySuccess();
    }

}
