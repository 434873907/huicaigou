package com.chengzi.apiunion.procurement.admin.web.controllers.areaaddress;

import com.chengzi.apiunion.common.module.areaadress.enums.AreaAddressLevelEnum;
import com.chengzi.apiunion.common.module.areaadress.pojo.AreaAddressDO;
import com.chengzi.apiunion.common.module.areaadress.service.AreaAddressService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.areaaddress.QueryAreaAddressListBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取地址列表（大区-省-市）
 *
 * @author 月汐
 * @date 2018/11/9 9:44
 */
public class QueryAreaAddressListForExpressController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        AreaAddressService service = BeanFactory.getBean(AreaAddressService.class);
        List<AreaAddressDO> list = service.queryListForExpressInCache();
        if (list == null || list.size() == 0) {
            return Result.buildSuccessResult(null);
        }

        Map<Long, QueryAreaAddressListBO> firstAreaAddress = new LinkedHashMap<>();
        Map<Long, List<QueryAreaAddressListBO>> secondAreaAddress = new LinkedHashMap<>();
        Map<Long, List<QueryAreaAddressListBO>> thirdAreaAddress = new LinkedHashMap<>();

        for (AreaAddressDO areaAddressDO : list) {
            if (areaAddressDO.getLevel() == AreaAddressLevelEnum.ZERO_LEVEL.getCode()) {
                firstAreaAddress.put(areaAddressDO.getId(), QueryAreaAddressListBO.convert(areaAddressDO, null));
            } else if(areaAddressDO.getLevel() == AreaAddressLevelEnum.FIRST_LEVEL.getCode()) {
                List<QueryAreaAddressListBO> secondList = secondAreaAddress.get(areaAddressDO.getParentId());
                if (secondList == null) {
                    secondList = new ArrayList<>();
                    secondAreaAddress.put(areaAddressDO.getParentId(), secondList);
                }
                secondList.add(QueryAreaAddressListBO.convert(areaAddressDO, null));
            } else if(areaAddressDO.getLevel() == AreaAddressLevelEnum.SECOND_LEVEL.getCode()) {
                List<QueryAreaAddressListBO> thirdList = thirdAreaAddress.get(areaAddressDO.getParentId());
                if (thirdList == null) {
                    thirdList = new ArrayList<>();
                    thirdAreaAddress.put(areaAddressDO.getParentId(), thirdList);
                }
                thirdList.add(QueryAreaAddressListBO.convert(areaAddressDO, null));
            }
        }

        List<QueryAreaAddressListBO> resultList = new ArrayList<>();
        for (Map.Entry<Long, QueryAreaAddressListBO> firstEntry : firstAreaAddress.entrySet()) {
            QueryAreaAddressListBO firstEntryValue = firstEntry.getValue();

            List<QueryAreaAddressListBO> secondList = secondAreaAddress.get(firstEntry.getKey());
            for (QueryAreaAddressListBO secondBO : secondList) {
                List<QueryAreaAddressListBO> thirdList = thirdAreaAddress.get(secondBO.getId());
                secondBO.setChildren(thirdList);
            }

            firstEntryValue.setChildren(secondList);
            resultList.add(firstEntryValue);
        }

        return Result.buildSuccessResult(resultList);
    }

}
