package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBuilder;
import com.chengzi.apiunion.brand.search.BrandSearchService;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 月汐
 * @date 2019/12/06 17:21
 */
public class FixBrandRedisController extends AbstractManageController<EmptyForm> {
    private static final int                        LIMIT      = 200;

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        long startId = 0;
        Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.BRAND);
        DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.BRAND.getShardStrategy();
        List<Long> brandIds = new ArrayList<>();
        for (String tableName : tableNames) {
            int idx = StringUtils.lastIndexOf(tableName, "_");
            int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));

            long shardBrandId = suffixNum * shardStrategy.getDivisor();

            while (true) {
                List<BrandDO> brandList = brandService.getBrands(startId, LIMIT, shardBrandId);

                if (CollectionUtil.isNotEmpty(brandList)) {
                    for (BrandDO brand : brandList) {
                        brandIds.add(brand.getId());
                        if (brand.getId() > startId) {
                            startId = brand.getId();
                        }
                    }
                } else {
                    break;
                }
            }
        }
        List<BrandDO> brandList = brandService.getByIdsWithCache(brandIds);
        brandList.forEach(brandDO -> {
            if (brandDO.getPropStatus() == null) {
                brandDO.setPropStatus(2);
                brandService.updatePropStatus(brandDO);
            }
        });
        return null;
    }
}
