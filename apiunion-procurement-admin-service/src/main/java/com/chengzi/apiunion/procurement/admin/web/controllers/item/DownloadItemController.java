package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.web.controllers.excel.AsyncExportExcelController;
import com.chengzi.apiunion.common.web.pojo.excel.ExcelData;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UnapprovedItemSkuListForm;
import com.chengzi.apiunion.procurement.admin.web.helper.order.item.ItemListHelper;
import com.chengzi.common.data.validate.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/09/10 09:47
 */
public class DownloadItemController extends AsyncExportExcelController<UnapprovedItemSkuListForm> {
    @Override
    protected Result<ExcelData> doExport(HttpServletRequest request, HttpServletResponse response, UnapprovedItemSkuListForm command) {
        return ItemListHelper.searchAndBuildExcel(command);
    }
}
