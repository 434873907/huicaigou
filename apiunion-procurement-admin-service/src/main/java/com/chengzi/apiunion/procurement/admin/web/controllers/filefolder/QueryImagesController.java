package com.chengzi.apiunion.procurement.admin.web.controllers.filefolder;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.pojo.search.ImageSearchQuery;
import com.chengzi.apiunion.common.module.image.pojo.search.PartnerImageSizeSearchBO;
import com.chengzi.apiunion.common.module.image.service.ImageSearchService;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.filefolder.QueryImagesByFileFolderIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.filefolder.ImageBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询文件夹下图片
 *
 * @author 月汐
 * @date 2019/2/11 11:22
 */
public class QueryImagesController extends AbstractApiController<QueryImagesByFileFolderIdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryImagesByFileFolderIdForm command) throws Exception {
        ImageSearchService imageSearchService = BeanFactory.getBean(ImageSearchService.class);

        ImageSearchQuery query = new ImageSearchQuery();
        query.setFolderIds(CollectionUtil.asArrayList(command.getId()));
        query.setFrom(command.getOffset());
        query.setSize(command.getLimit());
        query.setRouteId(RequestContext.getRouteId());
        query.setOrderBy(CollectionUtil.asArrayList(new OrderBy("modifyTime", SortOrder.DESC)));
        query.setIsDeleted(false);
        SearchResult<PartnerImageSizeSearchBO> result = imageSearchService.query(query);

        List<ImageBO> imageBOList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(result.getData())) {
            for (PartnerImageSizeSearchBO partnerImageSizeSearchBO : result.getData()) {
                ImageBO imageBO = new ImageBO();
                imageBO.setId(partnerImageSizeSearchBO.getId());
                imageBO.setImageUrl(partnerImageSizeSearchBO.getImageUrl());
                imageBO.setImageUrlMd5(partnerImageSizeSearchBO.getImageUrlMd5());
                imageBO.setName(partnerImageSizeSearchBO.getFileName());
                imageBOList.add(imageBO);
            }
        }
        return Result.buildSuccessResult(new PageDataList<>(result.getTotalHits(), command.getPage(), command.getLimit(), imageBOList));
    }
}
