package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.pojo.SupplierQuery;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 供应商订单里的供应商列表
 *
 * @author 随风
 * @create 2020-01-19 15:55
 **/
public class GetOrderSupplierListController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        SupplierQuery query = new SupplierQuery();
        query.setApiTypes(Collections.singletonList(ThirdSyncItemTypeEnum.SUPER_EXP.getCode()));
        List<SupplierDO> supplierDOS = supplierService.querySupplier(query);

        List<SupplierBO> boList = supplierDOS.stream().map(supplierDO -> {
            SupplierBO bo = new SupplierBO();
            bo.setId(supplierDO.getId());
            bo.setApiType(supplierDO.getApiType());
            bo.setSupplierName(supplierDO.getSupplierName());
            return bo;
        }).collect(Collectors.toList());

        return Result.buildSuccessResult(boList);
    }
}
