/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.brand.enums.BrandCountryEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.brand.CountryBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * 国家列表
 * 
 * @author 行者
 */
public class CountryListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ArrayList<CountryBO> countryBOList = new ArrayList<>();
        for (BrandCountryEnum countryEnum : BrandCountryEnum.values()) {
            CountryBO countryBO = new CountryBO();
            countryBO.setCode(countryEnum.getCode());
            countryBO.setName(countryEnum.getDesc());
            countryBOList.add(countryBO);
        }
        return Result.buildSuccessResult(countryBOList);
    }

}
