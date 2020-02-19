package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemOfflineTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.UpdateBrandStatusForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 更新品牌状态
 *
 * @author 月汐
 * @date 2018/10/31 9:52
 */
public class UpdateBrandStatusController extends AbstractApiController<UpdateBrandStatusForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBrandStatusForm command) throws Exception {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        int startPage = 0;
        ItemSearchQuery query = new ItemSearchQuery();
        Set<Long> brandIds = new HashSet<>();
        brandIds.add(command.getId());
        query.setBrandIds(brandIds);
        if (command.getStatus() == 0) {
            query.setStatus(ItemStatusEnum.ONLINE);
        } else {
            query.setStatus(ItemStatusEnum.OFFLINE);
            query.setOfflineType(ItemOfflineTypeEnum.AUTO_OFFLINE);
        }

        query.setFrom(0);
        query.setSize(30);
        SearchResult<ItemSearchBO> result = itemSearchService.query(query);
        while (result.getData() != null && result.getData().size() != 0) {
            List<Long> itemIds =CollectionUtil.getFieldValueList(result.getData(), "id");
            if (command.getStatus() == 0) {
                itemService.updateItemStatus(itemIds, ItemStatusEnum.OFFLINE, ItemOfflineTypeEnum.AUTO_OFFLINE, "品牌禁用 UpdateBrandStatusController");
            } else {
                itemService.updateItemStatus(itemIds, ItemStatusEnum.ONLINE, null, "品牌启用 UpdateBrandStatusController");
            }
            if (result.getData().size() < query.getSize()) {
                break;
            }
            query.setFrom((++startPage) * query.getSize());
            result = itemSearchService.query(query);
        }

        BrandService service = BeanFactory.getBean(BrandService.class);
        BrandDO brandDO = new BrandDO();
        brandDO.setId(command.getId());
        brandDO.setStatus(command.getStatus());
        service.updateStatus(brandDO);
        return Result.buildSuccessResult("修改成功");
    }

}
