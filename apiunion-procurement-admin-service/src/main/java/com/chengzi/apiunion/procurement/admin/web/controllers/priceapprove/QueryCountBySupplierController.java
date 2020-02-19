package com.chengzi.apiunion.procurement.admin.web.controllers.priceapprove;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.priceapprove.QueryPriceApproveListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.priceapprove.SupplierApproveCountBO;
import com.chengzi.apiunion.supplier.common.priceapprove.query.SkuPriceApproveSearchQuery;
import com.chengzi.apiunion.supplier.common.priceapprove.service.SkuPriceApproveSearchService;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.pojo.SupplierQuery;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryCountBySupplierController extends AbstractApiController<QueryPriceApproveListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryPriceApproveListForm command) throws Exception {
        SkuPriceApproveSearchService priceApproveSearchService = BeanFactory.getBean(SkuPriceApproveSearchService.class);
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        SupplierQuery supplierQuery = new SupplierQuery();
        List<SupplierDO> supplierDOList = supplierService.querySupplier(supplierQuery);

        SkuPriceApproveSearchQuery query = new SkuPriceApproveSearchQuery();
        query.setStatus(command.getStatus());
        Map<Long, Long> map = priceApproveSearchService.supplierAggregation(query);

        ArrayList<SupplierApproveCountBO> boList = new ArrayList<>();
        supplierDOList.forEach(supplierDO -> {
            SupplierApproveCountBO bo = new SupplierApproveCountBO();
            bo.setSupplierId(supplierDO.getId());
            bo.setSupplierName(supplierDO.getSupplierName());
            bo.setCount(map.getOrDefault(supplierDO.getId(), 0L));

            boList.add(bo);
        });

        return Result.buildSuccessResult(boList);
    }
}
