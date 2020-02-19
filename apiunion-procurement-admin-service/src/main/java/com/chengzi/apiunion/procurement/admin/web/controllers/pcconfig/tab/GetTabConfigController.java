package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.tab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.pcconfig.tab.pojo.Condition;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.tab.pojo.TabConfigDO;
import com.chengzi.apiunion.pcconfig.tab.service.TabConfigService;
import com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.tab.TabConfigBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;

import java.util.List;
import java.util.Map;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class GetTabConfigController extends AbstractApiController<IdForm>{

    @Override
    protected Result<TabConfigBO> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        TabConfigService tabConfigService = BeanFactory.getBean(TabConfigService.class);
        PartnerCategoryService partnerCategoryService = BeanFactory.getBean(PartnerCategoryService.class);
        List<PartnerCategoryDO> partnerCategoryDOList = partnerCategoryService.queryCategoryInCache();
        Map<Long, PartnerCategoryDO> idPartnerCategoryDOMap = CollectionUtil.toMap(partnerCategoryDOList, "id");
        TabConfigDO tabConfig = tabConfigService.getTabConfig(command.getId());
        TabConfigBO bo = TabConfigBO.convert(tabConfig);
        for (Condition condition : bo.getConditionList()) {
            for (CategoryUnit cate : condition.getCategoryUnitList()) {
                if (cate.getCateId3() > 0) {
                    cate.setTitle(idPartnerCategoryDOMap.get(cate.getCateId3()).getName());
                } else if (cate.getCateId2() > 0) {
                    cate.setTitle(idPartnerCategoryDOMap.get(cate.getCateId2()).getName());
                } else if (cate.getCateId1() > 0) {
                    cate.setTitle(idPartnerCategoryDOMap.get(cate.getCateId1()).getName());
                }
            }
        }

        return Result.buildSuccessResult(bo);
    }

}
