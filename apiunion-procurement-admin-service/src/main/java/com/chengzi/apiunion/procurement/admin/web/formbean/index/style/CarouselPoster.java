package com.chengzi.apiunion.procurement.admin.web.formbean.index.style;

import java.math.BigDecimal;
import java.util.List;

import com.chengzi.apiunion.common.data.style.IndicatorStyle;
import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;

import net.sf.oval.constraint.Min;

/** 
* @author 苏子 
* @date 2018年10月23日
*/
public class CarouselPoster extends BaseFormStyle{
	
	/**
	 * 宽高比例
	 */
	private BigDecimal proportion;
	/**
	 * 宽高比字符串
	 */
	private String     proportionStr;
	/**
	 * 填充方式 0：填充，1：按比例
	 */
	private Integer scaleType;
	/**
	 * 轮播间隔时间
	 */
	private Long duration;
	/**
	 * 指示器
	 */
	private IndicatorStyle indicatorStyle;
	/**
	 * 图片
	 */
	@NotEmptyAndNull(message="请填写正确数据")
	private List<Image> imageList;
	
	public BigDecimal getProportion() {
		return proportion;
	}
	public void setProportion(BigDecimal proportion) {
		this.proportion = proportion;
	}
	public String getProportionStr() {
		return proportionStr;
	}
	public void setProportionStr(String proportionStr) {
		this.proportionStr = proportionStr;
	}
	public Integer getScaleType() {
		return scaleType;
	}
	public void setScaleType(Integer scaleType) {
		this.scaleType = scaleType;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public IndicatorStyle getIndicatorStyle() {
		return indicatorStyle;
	}
	public void setIndicatorStyle(IndicatorStyle indicatorStyle) {
		this.indicatorStyle = indicatorStyle;
	}
	public List<Image> getImageList() {
		return imageList;
	}
	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}
}
