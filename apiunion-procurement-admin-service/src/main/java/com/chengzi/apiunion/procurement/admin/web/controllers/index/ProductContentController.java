package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.enums.ProductContentEnum;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.ProductContentBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/** 
* @author 苏子 
* @date 2018年10月25日
*/
public class ProductContentController extends AbstractApiController<EmptyForm>{

	private static final List<ProductContentBO> PRODUCT_CONTENT_LIST = new ArrayList<>();

    static {
    	ProductContentEnum[] arr = ProductContentEnum.values();
        for (ProductContentEnum productContent : arr) {
        	ProductContentBO productContentBO = new ProductContentBO();
        	productContentBO.setCode(productContent.getCode());
        	productContentBO.setRemark(productContent.getRemark());
        	PRODUCT_CONTENT_LIST.add(productContentBO);
        }
    }

	@Override
	protected Result<List<ProductContentBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command)
			throws Exception {
		return Result.buildSuccessResult(PRODUCT_CONTENT_LIST);
	}
}
