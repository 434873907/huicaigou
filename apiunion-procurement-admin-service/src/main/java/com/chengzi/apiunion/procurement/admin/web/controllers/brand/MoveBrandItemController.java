package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.MoveBrandItemForm;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;

/**
 * 迁移商品品牌
 * @author 行者
 */
public class MoveBrandItemController extends AbstractApiController<MoveBrandItemForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, MoveBrandItemForm command) throws Exception {
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        BrandDO targetBrandDO = brandService.getById(command.getTargetId());
        if (targetBrandDO == null || targetBrandDO.isDeleted()) {
            return Result.buildOpFailedResult("目标品牌不存在");
        }

        List<BrandDO> oriBrandDOList = brandService.getByIds(command.getOriginalIds());
        if (CollectionUtil.isEmpty(oriBrandDOList)) {
            return Result.buildOpFailedResult("目标品牌不存在");
        }

        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemSearchQuery query = new ItemSearchQuery();
        query.setStatusList(CollectionUtil.asArrayList(ItemStatusEnum.ONLINE,ItemStatusEnum.OFFLINE));
        query.setBrandIds(new HashSet<>(command.getOriginalIds()));
        query.setSize(9999);
        SearchResult<ItemSearchBO> searchResult = itemSearchService.query(query);
        if (searchResult.getData().size() == 0) {
            return Result.buildOpFailedResult("原品牌下没有商品");
        }
        List<Long> itemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");

        int count = brandService.moveBrandItem(oriBrandDOList,targetBrandDO,itemIds, command.getNeedDelete() == BooleanStatusEnum.YES.getCode());
        if (count < 1) {
            Result.buildOpFailedResult("操作失败");
        }
        return Result.buildSuccessResult(count);
    }
}
