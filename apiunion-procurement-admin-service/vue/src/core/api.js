export default {
    apiUrlList: {
        user: {
            login:'/partneruser/partner_user_login.do',
            userAudit:'/user/audit_user.do'
        },
        goods: {
            list: '/item/item_list.do',
            add: '/item/add_item.do',
            detail: '/item/get_item_detail.do',
            update: '/item/update_item.do',
            setStatus: '/item/update_item_status.do',
            brand: '/brand/query_brand_list.do',
            supplier:'/supplier/supplier_list.do',
            channel: '/channel/channel_list.do',
            buyer: '/user/user_list.do',
            actGoods: '/item/query_item_for_act.do',
            unapproved:'/item/unapproved_item_sku_list.do',
            approvedItem:'/item/get_item_approve_list.do',
            updatApproveStatus:'/item/update_item_approve_status.do',
            updateGoodsCategory:'/item/update_item_cate.do',
            mergeGoodsGroup:'/item/merge_item_group.do',
            goodsGroupNum:'/item/get_group_item_num.do',
            removeGoodsFromGroup:'/item/remove_from_group.do',
            groupList:'/item/group_item_list.do',
            deleGoods:'/item/delete_items.do',
            downloadItem:'/item/download_item.do',
            unitList:'/unit/unit_list.do',
            // fastApproved:'/item/get_item_for_fast_approved.do',
            fastApproved:'/item/get_item_for_fast_approve2.do',
            labelListByType:'/item/get_item_labels_by_type.do',
        },
        folder:{
            add:'/filefolder/add_file_folder.do',
            query:'/filefolder/query_file_folder_by_id.do',
            rename:'/filefolder/rename_file.do',
            getImg:'/filefolder/query_images.do',
            deleteFile:'/filefolder/deleted_file.do',
            move:'/filefolder/move_file.do'
        },
        brand: {
            list: '/brand/query_brand_list.do',
            del: '/brand/delete_brand.do',
            add: '/brand/add_brand.do',
            update: '/brand/update_brand.do',
            detail: '/brand/get_brand_by_id.do',
            setStatus: '/brand/update_brand_status.do',
            letterList: '/brand/query_brand_by_name.do',
            moveBrand: '/brand/move_brand_item.do',
            updateBrandStatus: '/brand/update_brand_prop_status.do',
            countryList: '/brand/country_list.do',
        },
        category: {
            cate: '/partnercategory/query_category_list.do',
            thirdCate: '/partnercategory/query_third_category_list.do',
            add: '/partnercategory/add_category.do',
            edit: '/partnercategory/update_category.do',
            del: '/partnercategory/delete_category.do',
            tree: '/partnercategory/get_category_tree.do',
            detail: '/partnercategory/get_category_by_id.do',
            setStatus: '/partnercategory/update_status.do',
            categoryNode: '/partnercategory/query_category_by_parent.do',  // 获取子类目列表
            updateCategory:'/partnercategory/update_category.do'
        },
        template:{
            queryTemplateTtemList:'/template/query_template_item_list.do',
            addTemplateItem:'/template/add_template_item.do',
            queryBrandLibraryList:'/template/query_brand_library_list.do',
            templateItemDetail:'/template/template_item_detail.do',
            updateTemplateItem:'/template/update_template_item.do',
            addTemplateCategory:'/template/add_template_category.do',
            queryTemplateCategoryList:'/template/query_template_category_list.do',
            updateTemplateCategory:'/template/update_template_category.do',
            addBrandLibrary:'/template/add_brand_library.do',
            updateBrandLibrary:'/template/update_brand_library.do',
            queryTemplateCategory:'/template/query_template_category.do',
            deleteTemplateItem:'/template/delete_template_item.do',
            queryTemplateCategoryCount:'/template/query_template_category_count.do',
        },
        notice: {
            list: '/announcement/query_announcement.do',
            setStatus: '/announcement/update_announcement_status.do',
            add: '/announcement/add_announcement.do',
            detail: '/announcement/get_announcement_by_id.do',
            edit: '/announcement/update_announcement.do',
            del: '/announcement/delete_announcement.do',
            type: '/announcement/announcement_jump_type.do'
        },
        invoice: {
            list: '/invoice/query_invoice_record.do',
            detail: '/invoice/get_invoice_record.do',
            uploadImg: '/invoice/upload_invoice_image.do'
        },
        supplier: {
            list: '/supplier/query_supplier.do',
            add: '/supplier/add_supplier.do',
            detail: '/supplier/get_supplier_detail.do',
            edit: '/supplier/update_supplier.do',
            del: '/supplier/delete_supplier.do',
            apiType:'/item/third/third_sync_type_list.do',
            newList: '/supplier/supplier_list_for_add_item.do',
            resetSupplierPassword: '/supplier/reset_supplier_password.do',
            orderExpressStatusList: '/supplier/order/supplier_order_express_status_list.do',
            querySupplierOrderList: '/supplier/order/query_supplier_order_list.do',
            getOrderPkgExpress: '/supplier/order/get_order_pkg_express.do',
            queryAnnouncementList: '/supplier/announcement/query_announcement_list.do',
            addAnnouncement: '/supplier/announcement/add_announcement.do',
            deleteAnnouncement: '/supplier/announcement/delete_announcement.do',
            getAnnouncement: '/supplier/announcement/get_announcement.do',
            querySupplierItemList: '/supplier/item/query_supplier_item_list.do',
            supplierItemDetail: '/supplier/item/supplier_item_detail.do',
            checkSameTemplateItemDetail:'/supplier/item/check_same_template_item_detail.do',
            supplierOrderExpressStatusList: '/supplier/order/supplier_order_express_status_list.do',
            getOrderSupplierList: '/supplier/order/get_order_supplier_list.do',
            downloadOrder: '/supplier/order/download_order.do',
            supplierOrderAbnormalStatusList: '/supplier/order/supplier_order_abnormal_status_list.do',
            queryAbnormalSupplierOrderList: '/supplier/order/query_abnormal_supplier_order_list.do',
            getSupplierOrderDetail: '/supplier/order/get_supplier_order_detail.do',
            addExpressNum: '/supplier/order/add_express_num.do',
            updateLockStatus: '/supplier/order/update_lock_status.do',
            closeAbnormal: '/supplier/order/close_abnormal.do',
            queryCountByCategory: '/supplier/priceapprove/query_count_by_category.do',
            queryCountBySupplier: '/supplier/priceapprove/query_count_by_supplier.do',
            approvePrice: '/supplier/priceapprove/approve_price.do',
            queryPriceApproveList: '/supplier/priceapprove/query_price_approve_list.do',
            supplierOrderSummary: '/supplier/order/supplier_order_summary.do',
            statisticByDay: '/supplier/order/statistic_by_day.do',

        },
        activity: {
            list: '/activity/activity_list.do',
            typeList: '/activity/actity_type_list.do',
            add: '/activity/add_activity.do',
            edit: '/activity/update_activity.do',
            detail: '/activity/get_activity.do',
            setStatus: '/activity/update_activity_status.do',
            delAct: '/activity/delete_activity.do',
            display: '/activity/display_terminal_list.do',
            buyLimit: '/activity/flash_sale_activity_list.do',
            getBuyLimit:'/activity/get_flash_sale_activity.do',
            addBuyLimit:'/activity/add_flash_sale_activity.do',
            delBuyLimit: '/activity/delete_flash_sale_activity.do',
            updateBuyLimit:'/activity/update_flash_sale_activity.do',
            moveBuyLimit:'/activity/update_flash_sale_item_index.do',
        },
        groupBuy:{
            add:'/groupbuying/add_group_buying.do',
            list:'/groupbuying/query_group_buying.do',
            delete:'/groupbuying/delete_group_buying.do',
            get:'/groupbuying/get_group_buying_by_id.do',
            update:'/groupbuying/update_group_buying.do',
            updateStatus:'/groupbuying/update_group_buying_status.do'
        },
        presell:{
            add:'/presell/add_presell.do',
            list:'/presell/query_presell.do',
            delete:'/presell/delete_presell.do',
            get:'/presell/get_presell_detail.do',
            update:'/presell/update_presell.do',
            updateStatus:'/presell/update_presell_status.do'
        },
        coupon: {
            list: '/coupon/query_coupon_config_list.do',
            add: '/coupon/add_coupon_config.do',
            detail: '/coupon/get_coupon_config.do',
            del: '/coupon/delete_coupon_config.do',
            sendCoupon: '/coupon/send_coupon_config.do',
            cancelCoupon: '/coupon/beinvalid_coupon_config.do',
            getUserList: '/user/user_list.do',
        },
        hotKeyword: {
            list: '/hotkeyword/query_hot_keyword.do',
            add: '/hotkeyword/add_hot_keyword.do',
            detail: '/hotkeyword/get_hot_keyword_by_id.do',
            edit: '/hotkeyword/update_hot_keyword.do',
            del: '/hotkeyword/delete_hot_keyword.do',
            batchDel: '/hotkeyword/delete_batch_hot_keyword.do',
            typeList: '/hotkeyword/hot_keyword_jump_type.do',
            setStatus: '/hotkeyword/update_hot_keyword_status.do'
        },
        store: {
            list: '/shop/query_shop.do',
            add: '/shop/add_shop.do',
            del: '/shop/delete_shop.do',
            detail: '/shop/get_shop_detail.do',
            edit: '/shop/update_shop.do',
            setStatus: '/shop/update_shop_status.do',
            allShop: '/shop/query_shop_list.do'
        },
        role: {
            list: '/partnerrole/query_partner_role.do',
            detail: '/partnerrole/get_partner_role_by_id.do',
            add: '/partnerrole/add_partner_role.do',
            edit: '/partnerrole/update_partner_role.do',
            del: '/partnerrole/delete_partner_role.do',
            all: '/partnerrole/get_partner_role_list.do'
        },
        staff: {
            list: '/partneruser/query_partner_user_list.do',
            detail: '/partneruser/get_partner_user_detail.do',
            add: '/partneruser/add_partner_user.do',
            edit: '/partneruser/update_partner_user.do',
            resetPassword: '/partneruser/update_self_password.do',
            initPassword: '/partneruser/update_init_password.do',
            del: '/partneruser/delete_partner_user.do',
            batchDel: '/partneruser/delete_partner_user_list.do',
            levelList: '/user/query_user_level_rule.do',
            levelDetail: '/user/get_user_level_rule.do',
            addLevel: '/user/add_user_level_rule.do',
            updateLevel: '/user/update_user_level_rule.do',
            delLevel: '/user/delete_user_level_rule.do',
            setLevelStatus: '/user/update_user_level_rule_status.do',
            auditList: '/user/audit_user_list.do',
            auditUser: '/user/audit_user.do',
            disableUser: '/user/disable_user.do',
            recharge: '/user/user_top_up_record.do'
        },
        feetemplate: {
            list: '/expressfeetemplate/query_express_fee_template.do',
            setStatus: '/expressfeetemplate/update_express_fee_template_status.do',
            del: '/expressfeetemplate/delete_express_fee_template.do',
            add: '/expressfeetemplate/add_express_fee_template.do',
            edit: '/expressfeetemplate/update_express_fee_template.do',
            detail: '/expressfeetemplate/get_express_fee_template_by_id.do',
            allTemp: '/expressfeetemplate/query_template_list.do',
            expressCompanyList: '/expresscompany/express_company_list.do',
            expressCompanyDetail: '/expresscompany/express_company_detail.do',
            updateExpressCompany: '/expresscompany/update_express_company.do',
            deleteExpressCompany: '/expresscompany/delete_express_company.do',
            addExpressCompany: '/expresscompany/add_express_company.do',
        },
        order: {
            list: '/order/order_list.do',
            detail: '/order/order_info.do',
            addExp: '/express/add_order_express.do',
            delExp: '/express/delete_order_express.do',
            updateOrderExpress: '/express/update_order_express.do',
            split: '/order/order_split.do',
            importLog: '/order/upload_express.do',
            inventory: '/inventory/query_inventory.do',
            exportOrder: '/order/download_order.do',
            changeSupplier: '/order/change_supplier.do',
            orderRefund: '/order/order_refund.do',
            refund: '/order/refund_from_order.do',
            refundReason: '/order/refund_reason_list.do',
            refundDetail: '/order/refund_detail.do',
            platform: '/order/get_pay_platform.do',
            refuse: '/order/refuse_order.do',
            applyPass: '/order/order_refund_apply_check.do',
            applyRefund: '/order/order_refund_apply_review.do',
            resetAmount: '/order/update_refund_amount.do',
            payConfirm:'/order/confirm_offline_pay_order.do',
            abnormalType:'/order/get_order_abnormal_types.do',
            orderNum:'/order/get_order_num_with_status.do',
            pushOrder: '/order/push_order.do',
            orderConrolLog: '/order/get_order_operate_list.do',
            orderCreatePlatform: '/order/order_create_platform.do',
            tagOrderDo: '/order/tag_order.do'
        },
        consumer: {
            user: '/user/user_list.do',
            remark:"/user/update_user_remark.do",
            detail:"/user/user_detail.do",
            disable:"/user/disable_user.do",

        },
        address: {
            update: '/order/update_order_address.do',
        },
        progress: {
            rate: '/common/progress_rate.do',
            result: '/common/progress_result.do'
        },
        common: {
            jumpType:'/common/jump_type.do',
            currency:'/common/currency_list.do',
            menu: '/menu/get_menu_by_user.do',
            expressAddress: '/areaaddress/query_area_address_list_for_express.do',
            expressCompany: '/expresscompany/query_express_company_list.do',
            logout: '/partneruser/partner_user_logout.do',
            config: '/config/get_system_config.do',
            smsList: '/config/sms_channel_list.do',
            accountConfig: '/config/get_config_by_type.do',
            updateAccountConfig: '/config/edit_config.do',
            pageConfig: '/systeminfo/get_system_info.do',
            updatePageConfig: '/systeminfo/update_system_info.do',
            sqlRun:'/run/sql_generate.do',
            area: '/areaaddress/query_area_address_list.do',
        },
        system: {
            agreement: '/agreement/update_agreement.do',
            agreementDetail: '/agreement/get_agreement.do',
            helpConfig:'/config/get_help_config.do',
            bannerList: '/pcconfig/banner/banner_config_list.do',
            moveBanner: '/pcconfig/banner/update_banner_config_order.do',
            addBanner: '/pcconfig/banner/add_banner_config.do',
            editBanner: '/pcconfig/banner/update_banner_config.do',
            setBannerStatus: '/pcconfig/banner/update_banner_config_status.do',
            bannerDetail: '/pcconfig/banner/get_banner_config.do',
            delBanner: '/pcconfig/banner/delete_banner_config.do',
            tabList: '/pcconfig/tab/tab_config_list.do',
            moveTab: '/pcconfig/tab/update_tab_config_order.do',
            addTab: '/pcconfig/tab/add_tab_config.do',
            editTab: '/pcconfig/tab/update_tab_config.do',
            setTabStatus: '/pcconfig/tab/update_tab_config_status.do',
            tabDetail: '/pcconfig/tab/get_tab_config.do',
            delTab: '/pcconfig/tab/delete_tab_config.do'
        },
        index:{
            moduleList:'/index/index_forward.do',
            getModule:'/index/get_module_and_data.do',
            addModule:'/index/add_index_tab_module_and_data.do',
            updateModule:'/index/update_index_tab_module_and_data.do',
            deleteModule:'/index/deleted_module_and_data.do',
            moduleOrder:'/index/update_index_tab_module_order.do',
            moduleType:'/index/layout_type.do',
            updateBottomBar:'/index/update_bottom_bar.do',
            getBottomBar:'/index/get_bottom_bar.do',
            goodsListContent:'/index/product_content.do',
            jumpType:'/index/jump_type_for_layout.do',
            getModuleStyle:'/index/index_tab_module_styles.do',

        },
        invitationcode: {
            matchTypeList: '/invitationcode/get_invitation_code_match_type.do',
            getCodeTypeList: '/invitationcode/get_invitation_code_type.do',
            delete: '/invitationcode/delete_invitation_code.do',
            add: '/invitationcode/add_invitation_code.do',
            getDetail: '/invitationcode/invitation_code_detail.do',
            list: '/invitationcode/query_invitation_code.do',
            changeStatus: '/invitationcode/update_invitation_status.do'
        }
    },
    static:{
        upload:'/upload/upload_file.do'
    }
}
