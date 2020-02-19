package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.brand.constant.BrandConstant;
import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.item.enums.ItemOfflineTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.UpdateBrandForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 更新品牌详情
 *
 * @author 月汐
 * @date 2018/10/15 15:39
 */
public class UpdateBrandController extends AbstractApiController<UpdateBrandForm> {
    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBrandForm command) throws Exception {
        if (StringUtils.isBlank(command.getName()) && StringUtils.isBlank(command.getOriginalName())) {
            return Result.buildIllegalArgumentResult("品牌中文名和英文名至少存在一个");
        }

        //去除图片后缀
        command.setLogoUrl(ImageUtil.getActualImageUrl(command.getLogoUrl()));
        command.setPosterUrl(ImageUtil.getActualImageUrl(command.getPosterUrl()));
        if (command.getStatus() != null) {
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
                    itemService.updateItemStatus(itemIds, ItemStatusEnum.OFFLINE, ItemOfflineTypeEnum.AUTO_OFFLINE, "品牌禁用 UpdateBrandController");
                } else {
                    itemService.updateItemStatus(itemIds, ItemStatusEnum.ONLINE, null, "品牌启用 UpdateBrandController");
                }
                if (result.getData().size() < query.getSize()) {
                    break;
                }
                query.setFrom((++startPage) * query.getSize());
                result = itemSearchService.query(query);
            }
        }

        BrandService service = BeanFactory.getBean(BrandService.class);
        BrandDO brandDO = UpdateBrandForm.convert(command);
        /**
         * todo: 添加关键词
         */
        long result = service.update(brandDO);
        if (result >= 0) {
            return Result.buildSuccessResult(brandDO.getId());
        } else if (BrandConstant.NAME_REPEAT == result) {
            return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "该品牌中文名或英文名已存在");
        } else {
            return Result.buildOpFailedResult("修改失败");
        }
    }
}
