package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemOfflineTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.UpdateStatusForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 更新类目状态
 *
 * @author 月汐
 * @date 2018/10/12 16:45
 */
public class UpdateStatusController extends AbstractApiController<UpdateStatusForm> {
    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateStatusForm command) throws Exception {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        PartnerCategoryDO partnerCategoryDO = service.getById(command.getId());
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        int startPage = 0;
        ItemSearchQuery query = new ItemSearchQuery();
        if (partnerCategoryDO.getDepth() == PartnerCategoryConstant.FIRST_CLASS_CATEGORY) {
            query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(partnerCategoryDO.getId(), 0, 0)));
        } else if (partnerCategoryDO.getDepth() == PartnerCategoryConstant.SECOND_CLASS_CATEGORY) {
            query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, partnerCategoryDO.getId(), 0)));
        } else if (partnerCategoryDO.getDepth() == PartnerCategoryConstant.THIRD_CLASS_CATEGORY) {
            query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, 0, partnerCategoryDO.getId())));
        }
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
                itemService.updateItemStatus(itemIds, ItemStatusEnum.OFFLINE, ItemOfflineTypeEnum.AUTO_OFFLINE, "类目禁用");
            } else {
                itemService.updateItemStatus(itemIds, ItemStatusEnum.ONLINE, null, "类目启用");
            }
            if (result.getData().size() < query.getSize()) {
                break;
            }
            query.setFrom((++startPage) * query.getSize());
            result = itemSearchService.query(query);
        }

        if (service.updateStatus(command.getId(), command.getStatus()) == -1) {
            return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "请先启用父类目");
        }
        return Result.buildSuccessResult("");
    }
}
