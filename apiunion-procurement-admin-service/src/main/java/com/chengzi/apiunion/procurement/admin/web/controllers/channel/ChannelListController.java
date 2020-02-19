/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.EmptyRouteOperate;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.item.dao.ItemChannelTypeMapper;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.procurement.admin.web.pojo.channel.ItemChannelTypeBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate.ExpressFeeTemplateInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierBO;
import com.chengzi.apiunion.supplier.dao.SupplierChannelMapper;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 渠道列表
 * 
 * @author Kolor
 *
 */
public class ChannelListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemChannelTypeMapper itemChannelTypeMapper = BeanFactory.getBean(ItemChannelTypeMapper.class);
        SupplierChannelMapper supplierChannelMapper = BeanFactory.getBean(SupplierChannelMapper.class);
        ExpressFeeTemplateService expressFeeTemplateService = BeanFactory.getBean(ExpressFeeTemplateService.class);

        List<ItemChannelTypeDO> channelList = itemChannelTypeMapper.selectChannelTypes(new EmptyRouteOperate());

        List<ItemChannelTypeBO> list = new ArrayList<>();
        for (ItemChannelTypeDO itemChannelTypeDO : channelList) {
            ItemChannelTypeBO bo = new ItemChannelTypeBO();
            bo.setChannelType(itemChannelTypeDO.getChannelType());
            bo.setChannelTypeName(itemChannelTypeDO.getChannelTypeName());

            List<SupplierDO> supplierChannelList = supplierChannelMapper
                    .selectSupplierByChannelType(SimpleRouteOperate.of(itemChannelTypeDO.getChannelType()));
            if (!supplierChannelList.isEmpty()) {
                List<SupplierBO> supplierList = new ArrayList<>();
                for (SupplierDO supplierDO : supplierChannelList) {
                    SupplierBO supplier = new SupplierBO();
                    supplier.setId(supplierDO.getId());
                    supplier.setSupplierName(supplierDO.getSupplierName());
                    supplierList.add(supplier);
                }
                bo.setSupplierList(supplierList);
                // 运费模板
                List<Long> supplierIds = CollectionUtil.getFieldValueList(supplierChannelList, "id");
                List<ExpressFeeTemplateDO> templateList = expressFeeTemplateService.getTemplateBySupplierIds(supplierIds);
                Map<Long, List<ExpressFeeTemplateDO>> templateViaSupplierIdMap = CollectionUtil.toListMap(templateList, "supplierId");
                for (SupplierBO supplierBO : supplierList) {
                    List<ExpressFeeTemplateInfo> l = new ArrayList<>();
                    supplierBO.setExpressFeeTemplates(l);
                    // 取自己关联的
                    List<ExpressFeeTemplateDO> templates = templateViaSupplierIdMap.get(supplierBO.getId());
                    if (CollectionUtil.isNotEmpty(templates)) {
                        for (ExpressFeeTemplateDO expressFeeTemplateDO : templates) {
                            ExpressFeeTemplateInfo templateInfo = new ExpressFeeTemplateInfo();
                            templateInfo.setId(expressFeeTemplateDO.getId());
                            templateInfo.setTemplateName(expressFeeTemplateDO.getTemplateName());

                            l.add(templateInfo);
                        }
                    }
                    // 取公共的
                    templates = templateViaSupplierIdMap.get(0L);
                    if (CollectionUtil.isNotEmpty(templates)) {
                        for (ExpressFeeTemplateDO expressFeeTemplateDO : templates) {
                            ExpressFeeTemplateInfo templateInfo = new ExpressFeeTemplateInfo();
                            templateInfo.setId(expressFeeTemplateDO.getId());
                            templateInfo.setTemplateName(expressFeeTemplateDO.getTemplateName());

                            l.add(templateInfo);
                        }
                    }
                }
            }

            list.add(bo);
        }
        return Result.buildSuccessResult(list);
    }

}
