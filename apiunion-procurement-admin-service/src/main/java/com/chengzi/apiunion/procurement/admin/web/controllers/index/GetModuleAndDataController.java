package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.index.pojo.style.GroupBuyStyle;
import com.chengzi.apiunion.index.pojo.style.LimitedTimeStyle;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.GroupBuy;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.LimitedTime;
import org.springframework.beans.BeanUtils;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.apiunion.common.jump.Jump;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.enums.LayoutTypeEnum;
import com.chengzi.apiunion.index.pojo.IndexTabModuleDO;
import com.chengzi.apiunion.index.pojo.IndexTabModuleDataDO;
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
import com.chengzi.apiunion.index.service.IndexTabModuleAndDataService;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.GetModuleAndDataForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.IndexTabModuleBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.BaseAdminStyle;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.CarouselPoster;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.ClosetLayout;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.GalleryModule;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.MagicItem;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.MixedArrangement;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.ProductList;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.SlidingPage;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class GetModuleAndDataController extends AbstractApiController<GetModuleAndDataForm>{

	@Override
	protected Result<IndexTabModuleBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetModuleAndDataForm command)
			throws Exception {
		long moduleId = command.getId();
		
		IndexTabModuleAndDataService indexTabModuleAndDataService = BeanFactory.getBean(IndexTabModuleAndDataService.class);
		IndexTabModuleDO module = indexTabModuleAndDataService.getIndexTabModuleAndDataByModuleId(moduleId);
		
		int type = module.getLayoutType();
		String styleStr = module.getStyle();
		List<IndexTabModuleDataDO> dataList = module.getDataList();
		
		IndexTabModuleBO moduleBO = new IndexTabModuleBO();
		moduleBO.setId(module.getId());
		moduleBO.setTabId(module.getTabId());
		moduleBO.setTitle(module.getTitle());
		moduleBO.setLayoutType(type);
		moduleBO.setOrder(module.getOrder());
		
		moduleBO.setStyleAndData(analysisStyleAndData(type, styleStr, dataList));
		
		return Result.buildSuccessResult(moduleBO);
	}


    private BaseAdminStyle analysisStyleAndData(int type, String styleStr, List<IndexTabModuleDataDO> dataList){
    	//根据传进来的类型、样式、数据，拼装成对应的结构，返回jsonString
    	
    	switch (EnumUtil.parse(LayoutTypeEnum.class, type)) {
    	case CAROUSEL_POSTER:
    		//轮播海报
    		CarouselPosterStyle carouselPosterStyle = JSONObject.parseObject(styleStr, CarouselPosterStyle.class);
    		CarouselPoster carouselPoster = new CarouselPoster();
    		BeanUtils.copyProperties(carouselPosterStyle, carouselPoster);
    		
    		if (CollectionUtil.isNotEmpty(dataList)) {
    			List<Image> imageList = new ArrayList<>();
    			for (IndexTabModuleDataDO data : dataList) {
    				String dataInfoStr = data.getData();
    				BaseData baseData = JSONObject.parseObject(dataInfoStr, BaseData.class);
    				
    				Image image = new Image();
    				image.setId(data.getId());
    				image.setUrl(baseData.getImgUrl());
    				Jump jump = baseData.getJump();
    				image.setJump(jump);
    				
    				imageList.add(image);
    			}
    			carouselPoster.setImageList(imageList);
    		}
    		
			return carouselPoster;
		case CLOSET_LAYOUT:
			ClosetLayoutStyle closetLayoutStyle = JSONObject.parseObject(styleStr, ClosetLayoutStyle.class);
			ClosetLayout closetLayout = new ClosetLayout();
			BeanUtils.copyProperties(closetLayoutStyle, closetLayout);
			
			if (CollectionUtil.isNotEmpty(dataList)) {
				//壁橱样式，一个壁橱，只有一条数据
				IndexTabModuleDataDO data = dataList.get(0);
				ClosetLayoutData closetLayoutData = JSONObject.parseObject(data.getData(), ClosetLayoutData.class);
				
				Image headerImage = new Image();
				headerImage.setId(data.getId());
				headerImage.setUrl(closetLayoutData.getImgUrl());
				Jump jump = closetLayoutData.getJump();
				headerImage.setJump(jump);
				closetLayout.setHeaderImage(headerImage);
				
				closetLayout.setDataType(closetLayoutData.getDataType());
				closetLayout.setValue(closetLayoutData.getValue());
			}
			return closetLayout;
		case PRODUCT_LIST:
			ProductListStyle productListStyle = JSONObject.parseObject(styleStr, ProductListStyle.class);
			ProductList productList = new ProductList();
			BeanUtils.copyProperties(productListStyle, productList);
			
			if (CollectionUtil.isNotEmpty(dataList)) {
				//商品列表，一个列表模块，只有一条数据
				IndexTabModuleDataDO data = dataList.get(0);
				productList.setId(data.getId());
				ProductListData productListData = JSONObject.parseObject(data.getData(), ProductListData.class);
				productList.setDataType(productListData.getDataType());
				productList.setValue(productListData.getValue());
			}
			
			return productList;
		case LIMITED_TIME:
			//暂时不做
			LimitedTimeStyle limitedTimeStyle = LimitedTimeStyle.parse(styleStr);
			LimitedTime limitedTime = new LimitedTime();
			BeanUtils.copyProperties(limitedTimeStyle, limitedTime);

			return limitedTime;
		case SLIDING_PAGE:
			SlidingPageStyle slidingPageStyle = JSONObject.parseObject(styleStr, SlidingPageStyle.class);
			SlidingPage slidingPage = new SlidingPage();
			BeanUtils.copyProperties(slidingPageStyle, slidingPage);
			
			if (CollectionUtil.isNotEmpty(dataList)) {
    			List<Image> imageList = new ArrayList<>();
    			for (IndexTabModuleDataDO data : dataList) {
    				String dataInfoStr = data.getData();
    				BaseData baseData = JSONObject.parseObject(dataInfoStr, BaseData.class);
    				
    				Image image = new Image();
    				image.setId(data.getId());
    				image.setUrl(baseData.getImgUrl());
    				Jump jump = baseData.getJump();
    				image.setJump(jump);
    				
    				imageList.add(image);
    			}
    			slidingPage.setImageList(imageList);
			}
			return slidingPage;
		case GALLERY_MODULE:
			GalleryModuleStyle galleryModuleStyle = JSONObject.parseObject(styleStr, GalleryModuleStyle.class);
			GalleryModule galleryModule = new GalleryModule();
			BeanUtils.copyProperties(galleryModuleStyle, galleryModule);
			
			if (CollectionUtil.isNotEmpty(dataList)) {
    			List<Image> imageList = new ArrayList<>();
    			for (IndexTabModuleDataDO data : dataList) {
    				String dataInfoStr = data.getData();
    				BaseData baseData = JSONObject.parseObject(dataInfoStr, BaseData.class);
    				
    				Image image = new Image();
    				image.setId(data.getId());
    				image.setUrl(baseData.getImgUrl());
    				Jump jump = baseData.getJump();
    				image.setJump(jump);
    				
    				imageList.add(image);
    			}
    			galleryModule.setImageList(imageList);
			}
			return galleryModule;
		case MIXED_ARRANGEMENT:
			MixedArrangementStyle mixedArrangementStyle = JSONObject.parseObject(styleStr, MixedArrangementStyle.class);
			MixedArrangement mixedArrangement = new MixedArrangement();
			BeanUtils.copyProperties(mixedArrangementStyle, mixedArrangement);
			
			if (CollectionUtil.isNotEmpty(dataList)) {
				//混合排列的数据结构应该是单条数据包含一个数据列表
				IndexTabModuleDataDO data = dataList.get(0);
				mixedArrangement.setId(data.getId());
				MixedArrangementData mixedArrangementData = JSONObject.parseObject(data.getData(), MixedArrangementData.class);
				mixedArrangement.setRow(mixedArrangementData.getRowCount());
				mixedArrangement.setColumn(mixedArrangementData.getColumnCount());
				
				List<MagicData> magicDataList = mixedArrangementData.getDataList();
				if (CollectionUtil.isNotEmpty(magicDataList)) {
					List<MagicItem> magicItemList = new ArrayList<>();
					for (MagicData magicData : magicDataList) {
						MagicItem magicItem = new MagicItem();
						magicItem.setStartX(magicData.getStartX());
						magicItem.setStartY(magicData.getStartY());
						magicItem.setEndX(magicData.getEndX());
						magicItem.setEndY(magicData.getEndY());
						magicItem.setImage(magicData.getImage());
						
						magicItemList.add(magicItem);
					}
					mixedArrangement.setMagicItems(magicItemList);
				}
			}
			return mixedArrangement;
		case GROUP_BUY:
			GroupBuyStyle groupBuyStyle = GroupBuyStyle.parse(styleStr);
			GroupBuy groupBuy = new GroupBuy();
			BeanUtils.copyProperties(groupBuyStyle, groupBuy);

			return groupBuy;
		default:
			return null;
		}
    }
}
