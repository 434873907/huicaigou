package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.module.image.enums.ImageSizeLimitEnum;
import com.chengzi.apiunion.common.module.image.util.ImageSizeVerifyUtil;
import com.chengzi.apiunion.common.util.RegexUtil;
import com.chengzi.apiunion.index.pojo.style.GroupBuyStyle;
import com.chengzi.apiunion.index.pojo.style.LimitedTimeStyle;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.GroupBuy;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.LimitedTime;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.service.ActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.style.TextStyle;
import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.apiunion.common.jump.Jump;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.enums.LayoutTypeEnum;
import com.chengzi.apiunion.index.pojo.IndexTabModuleDO;
import com.chengzi.apiunion.index.pojo.IndexTabModuleDataDO;
import com.chengzi.apiunion.index.pojo.IndexTabModuleStyleDO;
import com.chengzi.apiunion.index.pojo.data.BaseData;
import com.chengzi.apiunion.index.pojo.data.ClosetLayoutData;
import com.chengzi.apiunion.index.pojo.data.MagicData;
import com.chengzi.apiunion.index.pojo.data.MixedArrangementData;
import com.chengzi.apiunion.index.pojo.data.ProductListData;
import com.chengzi.apiunion.index.pojo.style.CarouselPosterStyle;
import com.chengzi.apiunion.index.pojo.style.ClosetLayoutStyle;
import com.chengzi.apiunion.index.pojo.style.GalleryModuleStyle;
import com.chengzi.apiunion.index.pojo.style.MixedArrangementStyle;
import com.chengzi.apiunion.index.pojo.style.ProductListStyle;
import com.chengzi.apiunion.index.pojo.style.SlidingPageStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.GalleryModuleDisplayStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.OtherStyle;
import com.chengzi.apiunion.index.pojo.stylemodule.SlidingPageDisplayStyle;
import com.chengzi.apiunion.index.service.IndexTabModuleAndDataService;
import com.chengzi.apiunion.index.service.IndexTabModuleStyleService;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.UpdateIndexTabModuleAndDataForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.CarouselPoster;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.ClosetLayout;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.GalleryModule;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.MixedArrangement;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.ProductList;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.style.SlidingPage;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;

/** 
* @author 苏子 
* @date 2018年10月10日
*/
public class UpdateIndexTabModuleAndDataController extends AbstractApiController<UpdateIndexTabModuleAndDataForm>{

	@Override
	protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response,
			UpdateIndexTabModuleAndDataForm command) throws Exception {
		long routeId = RequestContext.getRouteId();
		long userId = RequestContext.getUserId();
		long tabId = command.getTabId();
		long moduleId = command.getId();
		int layoutType = command.getLayoutType();
		String title = command.getTitle();
		String styleAndData = command.getStyleAndData();
		int order = command.getOrder();

		if (layoutType == LayoutTypeEnum.PRODUCT_LIST.getCode()) {
			//定死商品列表排最后
			order = 99;
		}
		
		//修改模块数据
		IndexTabModuleDO indexTabModule = new IndexTabModuleDO();
		indexTabModule.setId(moduleId);
		indexTabModule.setTabId(tabId);
		indexTabModule.setLayoutType(layoutType);
		indexTabModule.setTitle(title);
		indexTabModule.setOrder(order);
		indexTabModule.setCreateUserId(userId);
		indexTabModule.setModifyUserId(userId);
		Result<String> result = analysisStyleAndData(routeId, userId, layoutType, styleAndData, indexTabModule);
		if (!result.isSuccess()) {
			return result;
		}

		IndexTabModuleAndDataService indexTabModuleAndDataService = BeanFactory.getBean(IndexTabModuleAndDataService.class);
		int moduleRow = indexTabModuleAndDataService.updateIndexTabModuleAndData(indexTabModule);
		if (moduleRow <= 0) {
			return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "修改模块失败");
		}
		return Result.buildSuccessMsg("修改成功");
	}

	private Result<String> analysisStyleAndData(long routeId, long userId, int layoutType, String styleAndData, IndexTabModuleDO indexTabModule){
		//解析样式
		String style = "";
		long styleType = 0;
        long titleStyleType = 0;
        long displayStyleType = 0;
		Result result;
        IndexTabModuleStyleService moduleStyleService = BeanFactory.getBean(IndexTabModuleStyleService.class);
		List<IndexTabModuleDataDO> dataList = new ArrayList<>();
		List<Image> imageList = new ArrayList<>();
		Result checkJumpResult;
		switch (EnumUtil.parse(LayoutTypeEnum.class, layoutType)) {
		case CAROUSEL_POSTER:
			//轮播海报，数据多条
			CarouselPoster carouselPoster = JSONObject.parseObject(styleAndData,CarouselPoster.class);

			if (StringUtils.isNotBlank(carouselPoster.getProportionStr())) {
				if (RegexUtil.isProportion(carouselPoster.getProportionStr())) {
					List<Long> proportions = CollectionUtil.splitAsLong(carouselPoster.getProportionStr(), ":");
					carouselPoster.setProportion(BigDecimal.valueOf(proportions.get(0)).divide(
							BigDecimal.valueOf(proportions.get(1)), 4, RoundingMode.HALF_UP
					));
				} else {
					return Result.buildIllegalArgumentResult("请按要求的格式填写图片比例");
				}
			}

			// 图片尺寸校验
			for (Image image : carouselPoster.getImageList()) {
				result = ImageSizeVerifyUtil.verifyImageSize(image.getUrl(), ImageSizeLimitEnum.APP_CAROUSEL, carouselPoster.getProportion());
				if (!result.isSuccess()) {
					return result;
				}
			}

			CarouselPosterStyle carouselPosterStyle = new CarouselPosterStyle();
			BeanUtils.copyProperties(carouselPoster, carouselPosterStyle);
			
			imageList = carouselPoster.getImageList();
			if (CollectionUtil.isNotEmpty(imageList)) {
				for (Image image : imageList) {
					IndexTabModuleDataDO data = new IndexTabModuleDataDO();
					Long imgId = image.getId();
					if (imgId != null) {
					    data.setId(imgId);
                    }
					data.setRouteId(routeId);
					data.setCreateUserId(userId);
					data.setModifyUserId(userId);
					
					Jump jump = image.getJump();
					checkJumpResult = checkJumpData(jump);
					if (!checkJumpResult.isSuccess()) {
						return checkJumpResult;
					}
					data.setJump(jump.toJsonString());
					
					BaseData baseData = new BaseData();
					baseData.setImgUrl(image.getUrl());
					baseData.setJump(jump);
					data.setData(baseData.toJsonString());
					dataList.add(data);
				}
			}
			style = carouselPosterStyle.toJsonString();
			break;
		case CLOSET_LAYOUT:
			//壁橱，数据一条,有数据源
			ClosetLayout closetLayout = JSONObject.parseObject(styleAndData,ClosetLayout.class);

			// 图片尺寸校验
			result = ImageSizeVerifyUtil.verifyImageSize(closetLayout.getHeaderImage().getUrl(), ImageSizeLimitEnum.APP_CLOSET_LAYOUT, null);
			if (!result.isSuccess()) {
				return result;
			}

			ClosetLayoutStyle closetLayoutStyle = new ClosetLayoutStyle();
			BeanUtils.copyProperties(closetLayout, closetLayoutStyle);
			
			Image headerImage = closetLayout.getHeaderImage();
			if (headerImage != null) {
				IndexTabModuleDataDO data = new IndexTabModuleDataDO();
				Long imgId = headerImage.getId();
                if (imgId != null) {
                    data.setId(imgId);
                }
				data.setRouteId(routeId);
				data.setCreateUserId(userId);
				data.setModifyUserId(userId);
				
				Jump jump = headerImage.getJump();
				checkJumpResult = checkJumpData(jump);
				if (!checkJumpResult.isSuccess()) {
					return checkJumpResult;
				}
				data.setJump(jump.toJsonString());
				
				ClosetLayoutData closetLayoutData = new ClosetLayoutData();
				closetLayoutData.setImgUrl(headerImage.getUrl());
				closetLayoutData.setJump(jump);
				closetLayoutData.setDataType(closetLayout.getDataType());
				closetLayoutData.setValue(closetLayout.getValue());
				closetLayoutData.setFirstCate(closetLayout.getFirstCate());
				closetLayoutData.setSecondCate(closetLayout.getSecondCate());
				closetLayoutData.setThirdCate(closetLayout.getThirdCate());
				data.setData(JSONObject.toJSONString(closetLayoutData));
				dataList.add(data);
			}
			style = JSONObject.toJSONString(closetLayoutStyle);
			break;
		case PRODUCT_LIST:
			//商品列表，数据一条，没有图片数据
			ProductList productList = JSONObject.parseObject(styleAndData,ProductList.class);
			ProductListStyle productListStyle = new ProductListStyle();
			BeanUtils.copyProperties(productList, productListStyle);
			
			int dataType = productList.getDataType();
			String value = productList.getValue();
			if (dataType > 0) {
				IndexTabModuleDataDO data = new IndexTabModuleDataDO();
				data.setId(productList.getId());
				data.setRouteId(routeId);
				data.setCreateUserId(userId);
				data.setModifyUserId(userId);
				
				Jump jump = new Jump();
				jump.setJumpType(EnumUtil.parse(JumpTypeEnum.class, dataType).getCode());
				jump.setJumpData(value);
				checkJumpResult = checkJumpData(jump);
				if (!checkJumpResult.isSuccess()) {
					return checkJumpResult;
				}
				data.setJump(jump.toJsonString());
				
				ProductListData productListData = new ProductListData();
				productListData.setJump(jump);
				productListData.setDataType(productList.getDataType());
				productListData.setValue(productList.getValue());
				productListData.setFirstCate(productList.getFirstCate());
				productListData.setSecondCate(productList.getSecondCate());
				productListData.setThirdCate(productList.getThirdCate());
				data.setData(JSONObject.toJSONString(productListData));
				dataList.add(data);
			}
			style = JSONObject.toJSONString(productListStyle);
			break;
		case LIMITED_TIME:
				//暂时没有
				LimitedTime limitedTime = LimitedTime.parse(styleAndData);
				LimitedTimeStyle limitedTimeStyle = new LimitedTimeStyle();
				BeanUtils.copyProperties(limitedTime, limitedTimeStyle);

				style = limitedTimeStyle.toJsonString();

				IndexTabModuleDataDO limitItemData = new IndexTabModuleDataDO();
				limitItemData.setRouteId(routeId);
				limitItemData.setCreateUserId(userId);
				limitItemData.setModifyUserId(userId);
				dataList.add(limitItemData);
				break;
		case SLIDING_PAGE:
			//划动翻页，数据多条
			SlidingPage slidingPage = JSONObject.parseObject(styleAndData,SlidingPage.class);

			SlidingPageStyle slidingPageStyle = new SlidingPageStyle();
			BeanUtils.copyProperties(slidingPage, slidingPageStyle);
			
			styleType = slidingPage.getStyleType();
            titleStyleType = slidingPage.getTitleStyleType();
            displayStyleType = slidingPage.getDisplayStyleType();
            if (styleType > 0) {
                IndexTabModuleStyleDO moduleStyle = moduleStyleService.getModuleStyle(styleType);
                if (moduleStyle != null && StringUtils.isNotBlank(moduleStyle.getStyle())) {
                    OtherStyle otherStyle = JSONObject.parseObject(moduleStyle.getStyle(), OtherStyle.class);
                    BeanUtils.copyProperties(otherStyle, slidingPageStyle);
                }
                
            }
            if (titleStyleType > 0) {
                IndexTabModuleStyleDO moduleStyle = moduleStyleService.getModuleStyle(titleStyleType);
                if (moduleStyle != null && StringUtils.isNotBlank(moduleStyle.getStyle())) {
                    TextStyle titlrStyle = JSONObject.parseObject(moduleStyle.getStyle(), TextStyle.class);
                    BeanUtils.copyProperties(titlrStyle, slidingPageStyle);
                }
            }
            if (displayStyleType > 0) {
                IndexTabModuleStyleDO moduleStyle = moduleStyleService.getModuleStyle(displayStyleType);
                if (moduleStyle != null && StringUtils.isNotBlank(moduleStyle.getStyle())) {
                    SlidingPageDisplayStyle displayStyle = JSONObject.parseObject(moduleStyle.getStyle(), SlidingPageDisplayStyle.class);
                    BeanUtils.copyProperties(displayStyle, slidingPageStyle);
                }
            }
            
			imageList = slidingPage.getImageList();
			if (CollectionUtil.isNotEmpty(imageList)) {
				for (Image image : imageList) {
					IndexTabModuleDataDO data = new IndexTabModuleDataDO();
					Long imgId = image.getId();
                    if (imgId != null) {
                        data.setId(imgId);
                    }
					data.setRouteId(routeId);
					data.setCreateUserId(userId);
					data.setModifyUserId(userId);
					
					Jump jump = image.getJump();
					checkJumpResult = checkJumpData(jump);
					if (!checkJumpResult.isSuccess()) {
						return checkJumpResult;
					}
					data.setJump(jump.toJsonString());
					
					BaseData baseData = new BaseData();
					baseData.setImgUrl(image.getUrl());
					baseData.setJump(jump);
					data.setData(baseData.toJsonString());
					dataList.add(data);
				}
			}
			style = slidingPageStyle.toJsonString();
			break;
		case GALLERY_MODULE:
			//画廊模块，数据多条
			GalleryModule galleryModule = JSONObject.parseObject(styleAndData,GalleryModule.class);

			for (Image image : galleryModule.getImageList()) {
				result = ImageSizeVerifyUtil.verifyImageSize(image.getUrl(), ImageSizeLimitEnum.APP_GALLERY, null);
				if (!result.isSuccess()) {
					return result;
				}
			}

			GalleryModuleStyle galleryModuleStyle = new GalleryModuleStyle();
			BeanUtils.copyProperties(galleryModule, galleryModuleStyle);
			
			styleType = galleryModule.getStyleType();
            titleStyleType = galleryModule.getTitleStyleType();
            displayStyleType = galleryModule.getDisplayStyleType();
            if (styleType > 0) {
                IndexTabModuleStyleDO moduleStyle = moduleStyleService.getModuleStyle(styleType);
                if (moduleStyle != null && StringUtils.isNotBlank(moduleStyle.getStyle())) {
                    OtherStyle otherStyle = JSONObject.parseObject(moduleStyle.getStyle(), OtherStyle.class);
                    BeanUtils.copyProperties(otherStyle, galleryModuleStyle);
                }
                
            }
            if (titleStyleType > 0) {
                IndexTabModuleStyleDO moduleStyle = moduleStyleService.getModuleStyle(titleStyleType);
                if (moduleStyle != null && StringUtils.isNotBlank(moduleStyle.getStyle())) {
                    TextStyle titlrStyle = JSONObject.parseObject(moduleStyle.getStyle(), TextStyle.class);
                    BeanUtils.copyProperties(titlrStyle, galleryModuleStyle);
                }
            }
            if (displayStyleType > 0) {
                IndexTabModuleStyleDO moduleStyle = moduleStyleService.getModuleStyle(displayStyleType);
                if (moduleStyle != null && StringUtils.isNotBlank(moduleStyle.getStyle())) {
                    GalleryModuleDisplayStyle displayStyle = JSONObject.parseObject(moduleStyle.getStyle(), GalleryModuleDisplayStyle.class);
                    BeanUtils.copyProperties(displayStyle, galleryModuleStyle);
                }
            }
			
			imageList = galleryModule.getImageList();
			if (CollectionUtil.isNotEmpty(imageList)) {
				for (Image image : imageList) {
					IndexTabModuleDataDO data = new IndexTabModuleDataDO();
					Long imgId = image.getId();
                    if (imgId != null) {
                        data.setId(imgId);
                    }
					data.setRouteId(routeId);
					data.setCreateUserId(userId);
					data.setModifyUserId(userId);
					
					Jump jump = image.getJump();
					checkJumpResult = checkJumpData(jump);
					if (!checkJumpResult.isSuccess()) {
						return checkJumpResult;
					}
					data.setJump(jump.toJsonString());
					
					BaseData baseData = new BaseData();
					baseData.setImgUrl(image.getUrl());
					baseData.setJump(jump);
					data.setData(baseData.toJsonString());
					dataList.add(data);
				}
			}
			style = galleryModuleStyle.toJsonString();
			break;
		case MIXED_ARRANGEMENT:
			//混合排列（魔方），一条数据，包含多条数据
			MixedArrangement mixedArrangement = JSONObject.parseObject(styleAndData,MixedArrangement.class);
			MixedArrangementStyle mixedArrangementStyle = new MixedArrangementStyle();
			BeanUtils.copyProperties(mixedArrangement, mixedArrangementStyle);
			
			List<MagicData> magicDataList = mixedArrangement.getMagicItems();
			if (CollectionUtil.isNotEmpty(magicDataList)) {
			    for (MagicData magicData : magicDataList) {
                    Result<?> checkJumpDataResult = checkJumpData(magicData.getImage().getJump());
                    if (!checkJumpDataResult.isSuccess()) {
                        return Result.buildIllegalArgumentResult(checkJumpDataResult.getMessage());
                    }
                }

				IndexTabModuleDataDO data = new IndexTabModuleDataDO();
				data.setId(mixedArrangement.getId());
				data.setRouteId(routeId);
				data.setCreateUserId(userId);
				data.setModifyUserId(userId);
				
				MixedArrangementData mixedArrangementData = new MixedArrangementData();
				mixedArrangementData.setRowCount(mixedArrangement.getRow());
				mixedArrangementData.setColumnCount(mixedArrangement.getColumn());
				mixedArrangementData.setDataList(magicDataList);
				data.setData(mixedArrangementData.toJsonString());
				dataList.add(data);
			}
			style = mixedArrangementStyle.toJsonString();
			break;
		case GROUP_BUY:
			GroupBuy groupBuy = GroupBuy.parse(styleAndData);

			GroupBuyStyle groupBuyStyle = new GroupBuyStyle();
			BeanUtils.copyProperties(groupBuy, groupBuyStyle);

			style = groupBuyStyle.toJsonString();

			IndexTabModuleDataDO groupBuyData = new IndexTabModuleDataDO();
			groupBuyData.setRouteId(routeId);
			groupBuyData.setCreateUserId(userId);
			groupBuyData.setModifyUserId(userId);
			dataList.add(groupBuyData);
			break;
		}
		indexTabModule.setStyle(style);
		indexTabModule.setDataList(dataList);
		return Result.buildEmptySuccess();
	}

	private Result<?> checkJumpData(Jump jump) {
		try {
			if (jump.getJumpType() == JumpTypeEnum.CATEGORY_DETAIL.getCode()) {
				String data = jump.getJumpData();
				if (StringUtils.isBlank(data)) {
					return Result.buildOpFailedResult("请选择跳转类目");
				}
				String[] valueArray = data.split("-");
				long cateId = 0;
				if (valueArray.length == 3) {
					cateId = Long.valueOf(valueArray[2]);
				} else if (valueArray.length == 2) {
					cateId = Long.valueOf(valueArray[1]);
				} else {
					cateId = Long.valueOf(valueArray[0]);
				}

				PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
				PartnerCategoryDO category = categoryService.getById(cateId);
				if (category == null) {
					return Result.buildIllegalArgumentResult("请选择跳转类目");
				}
			} else if (jump.getJumpType() == JumpTypeEnum.PAGE_BRAND_DETAIL.getCode()) {
				String data = jump.getJumpData();
				if (StringUtils.isBlank(data)) {
					return Result.buildOpFailedResult("请输入品牌ID");
				}
				BrandService brandService = BeanFactory.getBean(BrandService.class);
				BrandDO brandDO = brandService.getById(Long.valueOf(data));
				if (brandDO == null) {
					return Result.buildIllegalArgumentResult("品牌ID:" + data + "不存在");
				}
			} else if (jump.getJumpType() == JumpTypeEnum.ACTIVITY_DETAIL.getCode()) {
				String data = jump.getJumpData();
				if (StringUtils.isBlank(data)) {
					return Result.buildOpFailedResult("请输入活动ID");
				}
				ActivityService activityService = BeanFactory.getBean(ActivityService.class);
				ActivityDO activity = activityService.getActivity(Long.parseLong(data));
				if (activity == null) {
					return Result.buildIllegalArgumentResult("活动ID" + data + "不存在");
				}
			} else if (jump.getJumpType() == JumpTypeEnum.PAGE_GOODS_DETAIL.getCode()) {
				String data = jump.getJumpData();
				if (StringUtils.isBlank(data)) {
					return Result.buildOpFailedResult("请输入商品ID");
				}
				ItemService itemService = BeanFactory.getBean(ItemService.class);
				ItemCO item = itemService.getItemWithCache(Long.parseLong(data));
				if (item == null) {
					return Result.buildIllegalArgumentResult("商品ID" + data + "不存在");
				}
			}
		} catch (Exception e) {
			return Result.buildIllegalArgumentResult(jump.getJumpData() + "数据类型错误");
		}

		return Result.buildEmptySuccess();
	}
}
