package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.data.excel.ExcelRow;
import com.chengzi.apiunion.common.web.controllers.excel.AsyncImportExcelsController;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.common.data.support.Tuple3;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.MultipartFilesForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author 月汐
 * @date 2019/12/23 15:08
 */
public class UploadItemPriceController extends AsyncImportExcelsController<MultipartFilesForm> {
    @Override
    protected Result<?> doImport(HttpServletRequest request, HttpServletResponse response, MultipartFilesForm command, Map<MultipartFile, List<ExcelRow>> fileMap) {
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        for (Entry<MultipartFile, List<ExcelRow>> fileEntry : fileMap.entrySet()) {
            List<ExcelRow> excelRows = fileEntry.getValue();
            Map<String, ItemSkuSupplierDO> skuSupplierMap = new HashMap<>();
            for (int i = 1; i < excelRows.size(); i++) {
                ExcelRow excelRow = excelRows.get(i);
                String itemCode = excelRow.get(10);

                ItemSkuSupplierDO itemSkuSupplierDO = skuSupplierMap.get(itemCode);
                if (itemSkuSupplierDO == null) {
                    itemSkuSupplierDO = new ItemSkuSupplierDO();
                    Tuple3<Long, Long, Integer> tuple = SkuUtil.decodeSkuNo(itemCode);
                    itemSkuSupplierDO.setItemId(tuple.getV1());
                    itemSkuSupplierDO.setSkuId(tuple.getV2());
                    itemSkuSupplierDO.setChannelType(tuple.getV3());
                    skuSupplierMap.put(itemCode, itemSkuSupplierDO);
                }
                if (StringUtils.isNotBlank(excelRow.get(16))) {
                    itemSkuSupplierDO.setPrice(new BigDecimal(excelRow.get(16).replace(",", "")));
                }
            }
            itemService.updatePrice(skuSupplierMap.values());
        }

        return Result.buildSuccessResult("修改成功");
    }
}
