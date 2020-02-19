/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.brand.search.BrandSearchService;
import com.chengzi.apiunion.cart.pojo.search.CartSearchBO;
import com.chengzi.apiunion.cart.search.CartSearchService;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.enums.SearchBizKeyEnum;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.image.pojo.search.PartnerImageSizeSearchBO;
import com.chengzi.apiunion.common.module.image.service.ImageSearchService;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.event.pojo.ItemUpdatedEvent;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.order.pojo.search.OrderItemSearchBO;
import com.chengzi.apiunion.order.pojo.search.OrderRefundSearchBO;
import com.chengzi.apiunion.order.pojo.search.OrderSearchBO;
import com.chengzi.apiunion.order.service.OrderItemSearchService;
import com.chengzi.apiunion.order.service.OrderRefundSearchService;
import com.chengzi.apiunion.order.service.OrderSearchService;
import com.chengzi.apiunion.shop.pojo.search.ShopSearchBO;
import com.chengzi.apiunion.shop.search.ShopSearchService;
import com.chengzi.apiunion.suggest.pojo.search.SuggestSearchBO;
import com.chengzi.apiunion.suggest.search.SuggestSearchService;
import com.chengzi.apiunion.user.pojo.search.UserSearchBO;
import com.chengzi.apiunion.user.search.UserSearchService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * ES引擎ID格式重置
 * 
 * @author Kolor
 */
public class EsIdResetController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        long beginTime = System.currentTimeMillis();
        final AtomicInteger total = new AtomicInteger();
        Map<String, SearchScrollable> beanMap = BeanFactory.getBeansOfType(SearchScrollable.class);
        for (SearchScrollable searchScrollable : beanMap.values()) {
            // 不处理的索引
            if (SearchBizKeyEnum.ITEM_FLOW.equals(searchScrollable.getBizKey())
                    || SearchBizKeyEnum.ORDER_FLOW.equals(searchScrollable.getBizKey())) {
                continue;
            }
            if (SearchBizKeyEnum.SUGGEST.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<SuggestSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<SuggestSearchBO> scrollResult) {
                        // ID
                        for (SuggestSearchBO searchBO : scrollResult.getData()) {
                            SuggestSearchService searchService = ((SuggestSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexSuggest(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.BRAND.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<BrandSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<BrandSearchBO> scrollResult) {
                        // ID
                        for (BrandSearchBO searchBO : scrollResult.getData()) {
                            BrandSearchService searchService = ((BrandSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexBrand(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.IMAGE.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<PartnerImageSizeSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<PartnerImageSizeSearchBO> scrollResult) {
                        // ID
                        for (PartnerImageSizeSearchBO searchBO : scrollResult.getData()) {
                            ImageSearchService searchService = ((ImageSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexImage(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.ITEM.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<ItemSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<ItemSearchBO> scrollResult) {
                        // ID
                        for (ItemSearchBO searchBO : scrollResult.getData()) {
                            searchScrollable.deleteById(searchBO.get_id());
                            EventBusFactory.getSyncEventBus().post(new ItemUpdatedEvent(searchBO.getId()));
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.ORDER_ITEM.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<OrderItemSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<OrderItemSearchBO> scrollResult) {
                        // ID
                        for (OrderItemSearchBO searchBO : scrollResult.getData()) {
                            OrderItemSearchService searchService = ((OrderItemSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexOrderItem(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.ORDER_REFUND.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<OrderRefundSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<OrderRefundSearchBO> scrollResult) {
                        // ID
                        for (OrderRefundSearchBO searchBO : scrollResult.getData()) {
                            OrderRefundSearchService searchService = ((OrderRefundSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexOrderRefund(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.CART.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<CartSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<CartSearchBO> scrollResult) {
                        // ID
                        for (CartSearchBO searchBO : scrollResult.getData()) {
                            CartSearchService searchService = ((CartSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexCart(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.ORDER.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<OrderSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<OrderSearchBO> scrollResult) {
                        // ID
                        for (OrderSearchBO searchBO : scrollResult.getData()) {
                            OrderSearchService searchService = ((OrderSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexOrder(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.SHOP.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<ShopSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<ShopSearchBO> scrollResult) {
                        // ID
                        for (ShopSearchBO searchBO : scrollResult.getData()) {
                            ShopSearchService searchService = ((ShopSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexShop(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            } else if (SearchBizKeyEnum.USER.equals(searchScrollable.getBizKey())) {
                searchScrollable.scroll(600_000, 100, null, new ScrollDataProcessor<UserSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<UserSearchBO> scrollResult) {
                        // ID
                        for (UserSearchBO searchBO : scrollResult.getData()) {
                            UserSearchService searchService = ((UserSearchService) searchScrollable);
                            searchScrollable.deleteById(searchBO.get_id());
                            searchService.indexUser(searchBO);
                            total.incrementAndGet();
                        }
                    }
                });
            }
        }
        return Result.buildSuccessResult(String.format("共处理了%d条记录，耗时：%d(ms)", total.get(), (System.currentTimeMillis() - beginTime)));
    }

}
