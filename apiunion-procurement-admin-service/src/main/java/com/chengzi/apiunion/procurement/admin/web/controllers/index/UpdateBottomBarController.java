package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.image.enums.ImageSizeLimitEnum;
import com.chengzi.apiunion.common.module.image.util.ImageSizeVerifyUtil;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSONArray;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.pojo.BottomBarDO;
import com.chengzi.apiunion.index.pojo.BottomBarData;
import com.chengzi.apiunion.index.service.BottomBarService;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.UpdateBottomBarForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;

import java.util.List;

/**
* @author 苏子 
* @date 2018年10月16日
*/
public class UpdateBottomBarController extends AbstractApiController<UpdateBottomBarForm>{

	@Override
	protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBottomBarForm command)
			throws Exception {
		long id = command.getId();
		String dataStr = command.getData();
		
		BottomBarDO bottomBar = new BottomBarDO();
		bottomBar.setId(id);
		List<BottomBarData> bottomBarDataList = JSONArray.parseArray(dataStr, BottomBarData.class);

		Result result = null;
		for (BottomBarData bottomBarData : bottomBarDataList) {
			result = ImageSizeVerifyUtil.verifyImageSize(bottomBarData.getBeforeClickingImg(), ImageSizeLimitEnum.APP_BOTTOM_BAR, null);
			if (!result.isSuccess()) {
				return result;
			}
			result = ImageSizeVerifyUtil.verifyImageSize(bottomBarData.getAfterClickingImg(), ImageSizeLimitEnum.APP_BOTTOM_BAR, null);
			if (!result.isSuccess()) {
				return result;
			}
		}

		bottomBar.setData(JSONArray.toJSONString(bottomBarDataList));
		
		BottomBarService bottomBarService = BeanFactory.getBean(BottomBarService.class);
		int row = bottomBarService.updateBottomBar(bottomBar);
		if (row > 0 ) {
			return Result.buildSuccessMsg("修改成功");
		}else{
			return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "修改失败");
		}
	}

}
