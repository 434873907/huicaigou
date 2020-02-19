/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.dao.ThirdSyncItemAuthMapper;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.pojo.ThirdSourceItemAttr;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdSyncItemAuthDO;
import com.chengzi.apiunion.item.pojo.ThirdTargetItemAttr;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.SupplierListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO.ChannelInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.pojo.SupplierChannelQuery;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 供应商列表
 * 
 * @author Kolor
 *
 */
public class SupplierListController extends AbstractApiController<SupplierListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SupplierListForm command) throws Exception {
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        SupplierChannelQuery query = new SupplierChannelQuery();
        query.setChannelType(command.getChannelType());
        query.setSupplierName(command.getName());
        List<SupplierDO> supplierDOList = supplierService.getSupplierByChannelTypeAndName(query);
        if (CollectionUtil.isEmpty(supplierDOList)) {
            return Result.buildSuccessResult(new ArrayList<>());
        }

        SupplierService service = BeanFactory.getBean(SupplierService.class);
        Map<Long, List<ItemChannelTypeDO>> supplierChannelTypes = service
                .getSupplierChannelTypes(CollectionUtil.getFieldValueList(supplierDOList, "id"));

        List<SupplierBO> list = new ArrayList<>();
        for (SupplierDO supplierDO : supplierDOList) {
            SupplierBO bo = new SupplierBO();
            list.add(bo);
            bo.setId(supplierDO.getId());
            bo.setSupplierName(supplierDO.getSupplierName());

            List<ItemChannelTypeDO> itemChannelTypeDOS = supplierChannelTypes.get(supplierDO.getId());
            if (CollectionUtil.isNotEmpty(itemChannelTypeDOS)) {
                List<ChannelInfo> channelTypes = new ArrayList<>();
                bo.setChannelTypes(channelTypes);
                for (ItemChannelTypeDO itemChannelTypeDO : itemChannelTypeDOS) {
                    ChannelInfo channelInfo = new ChannelInfo();
                    channelInfo.setChannelName(itemChannelTypeDO.getChannelTypeName());
                    channelInfo.setChannelType(itemChannelTypeDO.getChannelType());

                    channelTypes.add(channelInfo);
                }
            }

            ThirdSyncItemAuthMapper thirdSyncItemAuthMapper = BeanFactory.getBean(ThirdSyncItemAuthMapper.class);
            List<ThirdSyncItemAuthDO> thirdSyncItemAuthList = thirdSyncItemAuthMapper.selectBySupplierId(SimpleRouteOperate.of(supplierDO.getId()));
            if (!thirdSyncItemAuthList.isEmpty()) {
                ThirdSyncItemAuthDO thirdSyncItemAuthDO = thirdSyncItemAuthList.get(0);
                bo.setApiAuth(ThirdSyncApiAuth.fromJson(thirdSyncItemAuthDO.getApiAuth()));
                bo.setApiType(thirdSyncItemAuthDO.getApiType());
                bo.setPriceStrategy(PresetPriceStrategy.fromJson(thirdSyncItemAuthDO.getPriceStrategy()));
                bo.setSourceItemAttr(ThirdSourceItemAttr.fromJson(thirdSyncItemAuthDO.getSourceItemAttr()));
                bo.setTargetItemAttr(ThirdTargetItemAttr.fromJson(thirdSyncItemAuthDO.getTargetItemAttr()));
            }
        }
        return Result.buildSuccessResult(list);
    }

}
