package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.dao.ThirdSyncItemAuthMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.pojo.ThirdSourceItemAttr;
import com.chengzi.apiunion.item.pojo.ThirdSyncItemAuthDO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.QuerySupplierListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.QuerySupplierBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.pojo.SupplierQuery;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;

/**
 * 查询供应商列表
 *
 * @author 月汐
 * @date 2018/11/23 19:44
 */
public class QuerySupplierController extends AbstractApiController<QuerySupplierListForm> {

    @Autowired
    private SupplierService supplierService;
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QuerySupplierListForm command) throws Exception {
        //要查询的供应商的供货类型
        SupplierQuery query = new SupplierQuery();
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());
        query.setSupplierName(command.getSupplierName());
        Set<Integer> apiTypeSet =  command.getApiTypes();
        if(apiTypeSet != null && !apiTypeSet.isEmpty()) {
            query.setApiTypes(new ArrayList<>(apiTypeSet));
        }
        if (command.getStatus() != null) {
            query.setStatus(command.getStatus());
        }

        List<SupplierDO> supplierDOList = supplierService.querySupplier(query);
        if (CollectionUtil.isEmpty(supplierDOList)) {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
        }
        Map<Long, List<ItemChannelTypeDO>> supplierChannelTypesMap = supplierService
                .getSupplierChannelTypes(CollectionUtil.getFieldValueList(supplierDOList, "id"));
        List<QuerySupplierBO> boList = convert(supplierDOList, supplierChannelTypesMap);
        long total = supplierService.countSupplier(query);
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), boList));
    }

    private List<QuerySupplierBO> convert(List<SupplierDO> supplierDOList, Map<Long, List<ItemChannelTypeDO>> supplierChannelTypesMap) {
        ThirdSyncItemAuthMapper thirdSyncItemAuthMapper = BeanFactory.getBean(ThirdSyncItemAuthMapper.class);
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        ItemSearchQuery itemSearchQuery = new ItemSearchQuery();
        itemSearchQuery.setSupplierIds(CollectionUtil.getDisctinctFieldValueList(supplierDOList,"id"));
        itemSearchQuery.setStatus(null);
        itemSearchQuery.setApprovedStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE,ItemApproveStatusEnum.APPROVED));
        Map<Long, Integer> supplierIdItemNumMap = itemSearchService.supplierIdAggregation(itemSearchQuery);
        List<QuerySupplierBO> resultList = new ArrayList<>();
        for (SupplierDO supplierDO : supplierDOList) {
            List<ThirdSyncItemAuthDO> thirdSyncItemAuthList = thirdSyncItemAuthMapper.selectBySupplierId(SimpleRouteOperate.of(supplierDO.getId()));
            int apiType = 0;
            ThirdSourceItemAttr thirdSourceItemAttr = null;
            if (!thirdSyncItemAuthList.isEmpty()) {
                apiType = thirdSyncItemAuthList.get(0).getApiType();
                thirdSourceItemAttr = ThirdSourceItemAttr.fromJson(thirdSyncItemAuthList.get(0).getSourceItemAttr());
            }
            String apiTypeName = apiType == 0 ? "普通供货商" : EnumUtil.parse(ThirdSyncItemTypeEnum.class, apiType).getName();
            QuerySupplierBO bo = new QuerySupplierBO();
            bo.setId(supplierDO.getId());
            bo.setCreateTime(supplierDO.getCreateTime());
            bo.setSupplierName(supplierDO.getSupplierName());
            bo.setStatus(supplierDO.getStatus());
            bo.setItemNum(supplierIdItemNumMap.getOrDefault(supplierDO.getId(),0));
            bo.setApiType(apiType);
            bo.setApiTypeName(apiTypeName);
            bo.setPhone(supplierDO.getPhone());
            List<ItemChannelTypeDO> itemChannelTypeDOS = supplierChannelTypesMap.get(supplierDO.getId());
            StringBuilder sb = new StringBuilder();
            resultList.add(bo);
            if (thirdSourceItemAttr != null) {
                List<Integer> channelTypes = thirdSourceItemAttr.getChannelTypes();
                if (CollectionUtil.isNotEmpty(channelTypes)) {
                    for (int channelType : channelTypes) {
                        String channelName = itemChannelTypeService.getChannelNameInCache(channelType);
                        sb.append(channelName).append(",");
                    }
                }
            } else {
                if (CollectionUtil.isNotEmpty(itemChannelTypeDOS)) {
                    for (ItemChannelTypeDO itemChannelTypeDO : itemChannelTypeDOS) {
                        sb.append(itemChannelTypeDO.getChannelTypeName()).append(",");
                    }
                }
            }
            if (StringUtils.isNotBlank(sb.toString())) {
                sb.deleteCharAt(sb.length() - 1);
            }
            bo.setChannelTypeNames(sb.toString());
        }
        return resultList;
    }

}
