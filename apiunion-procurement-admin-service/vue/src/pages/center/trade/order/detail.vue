<template>
  <div class="order-detail">
    <div class="order-process" v-show="false">
      <div class="process-item process-item-active">
        <p class="process-name">订单生成</p>
        <div class="circle"></div>
        <p class="process-time">2018.10.10 10:10:10</p>
      </div>
      <div class="process-item process-item-active">
        <p class="process-name">供应商确认</p>
        <div class="circle"></div>
        <p class="process-time">2018.10.10 10:10:10</p>
      </div>
      <div class="process-item">
        <p class="process-name">待发货</p>
        <div class="circle"></div>
        <p class="process-time">2018.10.10 10:10:10</p>
      </div>
      <div class="process-item">
        <p class="process-name">已发货</p>
        <div class="circle"></div>
        <p class="process-time">2018.10.10 10:10:10</p>
      </div>
      <div class="process-item">
        <p class="process-name">确认收货</p>
        <div class="circle"></div>
        <p class="process-time">2018.10.10 10:10:10</p>
      </div>
    </div>
    <div class="order-info">
      <h2>订单详情 <span style="color: #E1376C" v-if="detail.orderInfo.desc">（{{detail.orderInfo.desc}}）</span></h2>
      <div class="info">
        <div class="info-item">
          <span class="name">订单号</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.orderNum}}</span>
        </div>

        <div class="info-item">
          <span class="name">订单状态</span>
          <span class="symbol">:</span>
          <span v-show="detail.orderInfo.orderStatus==1">待付款</span>
          <span v-show="detail.orderInfo.orderStatus==2">待付款-定金</span>
          <span v-show="detail.orderInfo.orderStatus==3">待付款-线下付款</span>
          <span v-show="detail.orderInfo.orderStatus==100">待发货</span>
          <span v-show="detail.orderInfo.orderStatus==200">待收货</span>
          <span v-show="detail.orderInfo.orderStatus==300">已收货</span>
          <span v-show="detail.orderInfo.orderStatus==400">订单取消</span>
          <span v-show="detail.orderInfo.orderStatus==500">退货退款</span>
          <span v-show="detail.orderInfo.orderStatus==600">订单驳回</span>
          <span v-show="detail.orderInfo.orderStatus==700">订单关闭</span>
          <a class="link link-op leftMar" v-show="false">关闭订单</a>
          <a class="link link-op leftMar" v-show="detail.orderInfo.orderStatus==3" @click="payConfirm">确认收款</a>
<!--          <a class="link link-op leftMar"   @click="openBackModal()" v-show="detail.orderInfo.orderStatus==100||detail.orderInfo.orderStatus==200||detail.orderInfo.orderStatus==300">主动退款</a>-->
        </div>
      </div>

      <div class="info">
        <div class="info-item">
          <span class="name">订单金额</span>
          <span class="symbol">:</span>
          <span
            class="red-color"
            v-show="detail.orderInfo.totalAmount || detail.orderInfo.totalAmount===0"
          >¥{{detail.orderInfo.totalAmount}}</span>
        </div>
        <div class="info-item">
          <span class="name">商品金额</span>
          <span class="symbol">:</span>
          <span
            class="red-color"
            v-show="detail.orderInfo.itemAmount || detail.orderInfo.itemAmount===0"
          >¥{{detail.orderInfo.itemAmount}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item">
          <span class="name">运费金额</span>
          <span class="symbol">:</span>
          <span
            class="red-color"
            v-show="detail.orderInfo.expAmount!==0"
          >¥{{detail.orderInfo.expAmount}}</span>
          <span class="red-color" v-show="detail.orderInfo.expAmount===0">无</span>
        </div>
        <div class="info-item">
          <span class="name">优惠抵扣</span>
          <span class="symbol">:</span>
          <span
            class="red-color"
            v-show="detail.orderInfo.couponAmount!==0"
          >-¥{{detail.orderInfo.couponAmount}}</span>
          <span class="red-color" v-show="detail.orderInfo.couponAmount===0">无</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item">
          <span class="name">采购商</span>
          <span class="symbol">:</span>
          <span
            v-if="detail.orderInfo.buyerRemark"
          >{{detail.orderInfo.buyer}}{{'('+detail.orderInfo.buyerRemark+')'}}</span>
          <span v-if="!detail.orderInfo.buyerRemark">{{detail.orderInfo.buyer}}</span>
        </div>
        <div class="info-item">
          <span class="name">下单时间</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.createTime}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item" style="padding-bottom: 10px">
          <span class="name">订单来源</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.createPlatform}}</span>
        </div>
        <div class="info-item" v-if="detail.orderInfo.userOrderNum">
          <span class="name">原订单号</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.userOrderNum}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item" v-show="detail.orderInfo.orderStatus==500">
          <span class="name">退货退款</span>
          <span class="symbol">:</span>
          <a class="link" @click="openBackModal()">发起退款</a>
          <a class="detail" @click="openRefundDetailModal()">查看退款详情</a>
          <a class="detail" @click="applyRefund()">确认退款</a>
        </div>
      </div>

      <h2
              v-if="!detail.originalReceiverInfo"
        class="receiver"
      >
        收货人信息
        <span  v-show="detail.supportChangeAddress==1" @click="modifyAddressDailog = true;getAllAddress()">
          <img
            style="margin-left:8px;"
            width="15"
            height="15"
            src="https://timg.huidinghuo.com/i/dc652f35bd2d438c7ba1802ef9a9f6c1_20X21_huQ.png"
          />
        </span>
      </h2>
      <h2 v-if="detail.originalReceiverInfo" class="receiver">
        收货人信息
        <span  v-show="detail.supportChangeAddress==1" @click="modifyAddressDailog = true;getAllAddress()">
          <img
                  style="margin-left:8px;"
                  width="15"
                  height="15"
                  src="https://timg.huidinghuo.com/i/dc652f35bd2d438c7ba1802ef9a9f6c1_20X21_huQ.png"
          />
        </span>
        <span class="reciverImg">
          <img
            style="margin-left:8px;"
            @mouseenter="originAddressDailog = true;"
            width="16"
            height="16"
            src="~assets/goods/updated.png"
          />
          <div class="dialogReciver" v-if="detail.originalReceiverInfo">
            <h2 style="font-size:15px;font-weight:bold;">当前订单原地址为：</h2>
            <div class="set-item">
              <span>收货人:{{detail.originalReceiverInfo.receiver}}</span>
            </div>
            <div class="set-item">
              <span>手机号码:{{detail.originalReceiverInfo.phone}}</span>
            </div>
            <div class="set-item">
              <span>详细地址:{{detail.originalReceiverInfo.address}}</span>
            </div>
            <div class="set-item">
              <span>身份证号:{{detail.originalReceiverInfo.idCardNum}}</span>
            </div>

          </div>
        </span>
        <span style="margin-left: 10px;">{{detail.orderInfo | vertInfo}}</span>
      </h2>
      <div class="info">
        <div class="info-item">
          <span class="name">收货人</span>
          <span class="symbol">:</span>
          <span>{{detail.receiverInfo.receiver}}</span>
        </div>
        <div class="info-item">
          <span class="name">手机号</span>
          <span class="symbol">:</span>
          <span>{{detail.receiverInfo.phone}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item">
          <span class="name">收货地址</span>
          <span class="symbol">:</span>
          <span class="detial-address">
            <el-popover placement="top" width="200" trigger="hover" class="imgBoxCont">
                      <span>{{detail.receiverInfo.address}}</span>
                      <span slot="reference">{{detail.receiverInfo.address}}</span>
                    </el-popover>
            </span>
        </div>
        <div class="info-item">
          <span class="name">身份证号</span>
          <span class="symbol">:</span>
          <span>{{detail.receiverInfo.idCardNum}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item">
          <span class="name">备注信息</span>
          <span class="symbol">:</span>
          <span>{{detail.receiverInfo.remark}}</span>
        </div>
<!--         <div class="info-item" v-if="detail.orderInfo.userOrderNum">-->
<!--          <span class="name">原订单号</span>-->
<!--          <span class="symbol">:</span>-->
<!--          <span>{{detail.orderInfo.userOrderNum}}</span>-->
<!--          </div>-->
      </div>

    </div>

    <div
      class="order-info"
      v-if="detail.orderInfo.orderStatus!=1&&detail.orderInfo.payInfoList && detail.orderInfo.payInfoList.length"
    >
      <h2>支付信息</h2>
      <div class="info">
        <div class="info-item">
          <span class="name">支付时间</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.payInfoList[0].payTime}}</span>
        </div>
        <div class="info-item">
          <span class="name">支付金额</span>
          <span class="symbol">:</span>
          <span class="red-color">{{detail.orderInfo.payInfoList[0].payAmount}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item">
          <span class="name">支付方式</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.payInfoList[0].payMethod}}</span>
        </div>
        <div class="info-item">
          <span class="name">支付账号</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.payInfoList[0].payTradeAccount}}</span>
        </div>
      </div>
      <div class="info">
        <div class="info-item">
          <span class="name">支付流水号</span>
          <span class="symbol">:</span>
          <span>{{detail.orderInfo.payInfoList[0].payTradeNo}}</span>
        </div>
      </div>
    </div>
    <div class="order-content">
      <div class="order-inner">
        <h2>商品信息</h2>
        <table class="inner">
          <thead class="table-title">
            <tr>
              <th class="item-info table-w260">商品信息</th>
              <th class="table-w80">商品ID</th>
              <th class="table-w100">成本价(¥)</th>
              <th class="table-w80">售价(¥)</th>
              <th class="table-w100" v-show="!userConfig.singleChannel">渠道</th>
              <th style="width:100px;" class="suplly">供应商</th>
              <th class="table-w60">数量</th>
              <th class="table-w80">重量(kg)</th>
              <th class="table-w200">操作</th>
              <th></th>
            </tr>
          </thead>
          <tbody
            class="info-body"
            v-for="(item, index) in detail.orderItemInfo"
            :key="index+'item'"
          >
            <tr class="tips-top">
              <td colspan="9">
                <span class="table-tips">
                  包裹号:
                  <span class="tips-value">{{item.pkgNo}}</span>
                </span>
                <span class="pkgStatus" v-if="item.pkgItemInfo[0].thirdAttr">
                  （ 推单状态：
                  <span
                    :class="[item.pkgItemInfo[0].pkgStatus==0?'':'pkgSuccess']"
                    v-if="item.pkgItemInfo[0].pkgStatus!=50"
                  >{{item.pkgItemInfo[0].pkgStatusName}}</span>
                  <span class="pkgError" v-else>
                    <el-popover placement="top" width="200" trigger="hover" class="imgBoxCont">
                      <span>{{item.pkgItemInfo[0].pkgStatusDesc}}</span>
                      <span style="color: #E1376C" slot="reference">{{item.pkgItemInfo[0].pkgStatusName}}</span>
                    </el-popover>
                  </span>
                  ）
                </span>
                <span
                  class="check-log pull-right"
                  v-show="item.pkgNo==currentRightLogistics.pkgNo"
                >右侧显示当前物流详情</span>
                <span
                  class="check-log pull-right link"
                  @click="setCurrent(item)"
                  v-show="item.pkgNo!=currentRightLogistics.pkgNo"
                >查看物流详情</span>
              </td>
            </tr>
            <tr v-for="(goods, goods_index) in item.pkgItemInfo" :key="goods_index+'goods'">
              <td class="goods-info" @click="goGoodsDetail(goods,item)">
                <el-popover placement="top" width="200" trigger="hover">
                  <img style="width:100%;height:100%" :src="goods.itemImg" />
                  <img slot="reference" :src="goods.itemImg" />
                </el-popover>
                <h4 class="item-title">{{goods.itemTitle}}</h4>
                <el-popover placement="top" width="200" trigger="hover">
                  <p>规格:{{goods.spec}}</p>
                  <p slot="reference">规格:{{goods.spec}}</p>
                </el-popover>
                <!-- <p>规格:{{goods.spec}}</p> -->
              </td>
              <td>{{goods.itemNum}}</td>
              <td class="channelPrice">{{goods.channelPrice}}</td>
              <td class="price">{{goods.price}}</td>
              <td v-show="!userConfig.singleChannel">{{goods.channelName}}</td>
              <td ><div class="supplier">{{goods.supplierName}}</div></td>
              <td>{{goods.count}}</td>
              <td>{{goods.weight}}</td>
              <td class="text-op-padding">
                <div class="table-op">
                  <p>
                    <a
                      class="link splitPkg"
                      @click="splitPkg(item, goods)"
                      v-show="detail.orderInfo.orderStatus==100"
                    >拆包</a>
                    <a class="link splitPkg" @click="pushOrder(detail.orderInfo)" v-show="detail.orderInfo.orderStatus == 100 && detail.orderInfo.supportPushOrder == 1">推单</a>
                  </p>
                  <p>
                    <a  v-show="detail.orderInfo.orderStatus==100||detail.orderInfo.orderStatus==3||detail.orderInfo.orderStatus==2||detail.orderInfo.orderStatus==1" class="link resetSupModal" @click="resetSupModal(goods)">更换供应商</a>
                  </p>
                </div>
              </td>
              <td></td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="order-logistics">
        <div class="title">
          <span>物流详情</span>
          <div class="pull-right">
<!--            <a-->
<!--              class="btn-main"-->
<!--              @click="logisticsModal()"-->
<!--              v-show="detail.orderInfo.orderStatus==100&&currentRightLogistics.supportOperateExpress"-->
<!--            >添加物流</a>-->
            <a
              class="btn-main"
              @click="logisticsModal()"
              v-show="detail.orderInfo.orderStatus==100||detail.orderInfo.orderStatus==200"
            >添加物流</a>
<!--            <a-->
<!--                    class="btn-main"-->
<!--                    @click="editLogistics()"-->
<!--                    v-show="(detail.orderInfo.orderStatus==100||detail.orderInfo.orderStatus==200)&&currentRightLogistics.supportOperateExpress&&currentRightLogistics.pkgExpressInfos.length>0"-->
<!--            >修改物流</a>-->
            <a
                    class="btn-main"
                    @click="editLogistics()"
                    v-show="(detail.orderInfo.orderStatus==100||detail.orderInfo.orderStatus==200)&&currentRightLogistics.currentLogistics.expressNum"
            >修改物流</a>
<!--            <a-->
<!--              class="btn-fill-grey-main"-->
<!--              v-if="detail.orderInfo.orderStatus==100"-->
<!--              @click="delLogisticsModal()"-->
<!--            >删除物流</a>-->
          </div>
        </div>
        <div class="logistics-inner" v-show="currentRightLogistics.pkgExpressInfos.length>0">
          <div class="logistics-tab">
            <span
              v-for="exp in currentRightLogistics.pkgExpressInfos"
              :key="exp.expressId"
              :class="{'active':currentRightLogistics.currentLogistics.expressId===exp.expressId}"
              @click="changeLogTab(currentRightLogistics, exp)"
            >{{exp.company}}</span>
          </div>
          <div class="logistics-content">
            <div class="content-title">
              <h5>物流公司：{{currentRightLogistics.currentLogistics.expressName}}</h5>
              <h5>物流单号：{{currentRightLogistics.currentLogistics.expressNum}}</h5>
            </div>
            <p
              v-for="(log, log_index) in currentRightLogistics.currentLogistics.expressDetail"
              :key="log_index+'log'"
            >{{log}}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="order-control-log">
      <h2>操作记录</h2>
      <table class="table">
          <thead>
            <tr>
              <th class="table-w200">操作时间</th>
              <th>操作记录</th>
              <th class="table-w100">操作人</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, i) in controlLogList" :key="i">
              <td>{{item.createTime}}</td>
              <td class="noraw">
                <el-popover class="ellipsis" placement="top" width="500" trigger="hover">
                  <span>{{item.remark}}</span>
                  <span slot="reference" class="hidden ellipsis">{{item.remark}}</span>
                </el-popover>
              </td>
              <td>{{item.sourceDesc}}</td>
            </tr>
          </tbody>
      </table>
       <pagination
        v-show="total>0"
        :total="total"
        :page.sync="listParams.page"
        :limit.sync="listParams.limit"
        @pagination="getUserOrderControlLog(listParams.page)"
      />
    </div>
    <el-dialog :title="isEdit ?'修改物流':'添加物流'" :visible.sync="showLogisticsModal" width="468px">
      <div class="dialog-form border-bottom">
        <div class="search-item">
          <span class="title">物流公司：</span>
          <select
            class="form-control"
            v-model="addLogisticsParams.expressCompany"
            :class="{'unset-select':!addLogisticsParams.expressCompany}"
          >
            <option value>请选择物流公司</option>
            <option v-for="item in expCompany" :key="item.id" :value="item.id">{{item.name}}</option>
          </select>
        </div>
      </div>
      <div class="dialog-form">
        <div class="search-item">
          <span class="title">物流单号：</span>
          <input
            type="text"
            class="form-control"
            placeholder="请输入物流单号"
            v-model="addLogisticsParams.expressNum"
          />
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveLogistics()">保存</button>
        <button class="btn-fill-grey-main" @click="showLogisticsModal=false">取消</button>
      </span>
    </el-dialog>
    <el-dialog title="删除物流:" :visible.sync="showDelLogisticsModal" width="468px">
      <div class="dialog-form">
        <h3>物流信息确认删除后无法找回，请确认是否删除本段物流信息</h3>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="delLogistics()">删除</button>
        <button class="btn-fill-grey-main" @click="showDelLogisticsModal=false">取消</button>
      </span>
    </el-dialog>
    <el-dialog title="拆分包裹:" :visible.sync="showSplitModal" width="468px">
      <div class="dialog-form split-dialog">
        <div class="search-item">
          <span class="title">商品名称：</span>
          <span class="goods-name">{{splitDisplay.goodsName}}</span>
        </div>
        <div class="search-item">
          <span class="title">拆分包裹：</span>
          <select class="form-control" v-model="splitParams.pkgNo">
            <option value>新建包裹</option>
            <option v-for="item in splitDisplay.splitList" :key="item" :value="item">{{item}}</option>
          </select>
        </div>
        <div class="search-item">
          <span class="title">拆分数量：</span>
          <input
            type="number"
            class="form-control"
            v-model="splitParams.num"
            :placeholder="'最多不超过'+splitDisplay.limit+'件'"
          />
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="splitPacket()">保存</button>
        <button class="btn-fill-grey-main" @click="showSplitModal=false">取消</button>
      </span>
    </el-dialog>
    <el-dialog title="更换供应商:" :visible.sync="showResetSupModal" width="910px">
      <div class="dialog-form reset-sup">
        <table class="table">
          <thead>
            <tr>
              <th class="table-w260">商品</th>
              <th class="table-w60">数量</th>
              <th class="table-w100">原供应商</th>
              <th class="table-w180">新供应商</th>
              <th>拆分新包裹</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <div class="back-goods-img">
                  <img :src="resetSupData.itemImg" />
                </div>
                <div class="back-goods-name">
                  <el-popover placement="top" width="200" trigger="hover">
                    <span>{{resetSupData.itemTitle}}</span>
                    <span class="ellipsis" slot="reference">{{resetSupData.itemTitle}}</span>
                  </el-popover>
                  <!-- <h2>{{resetSupData.itemTitle}}</h2> -->
                  <p>规格:{{resetSupData.spec}}</p>
                </div>
              </td>
              <td>{{resetSupData.count}}</td>
              <td>
                <el-popover placement="top" width="200" trigger="hover">
                  <span>{{resetSupData.supplierName}}</span>
                  <span class="ellipsis" slot="reference">{{resetSupData.supplierName}}</span>
                </el-popover>
              </td>
              <td>
                <select
                  class="form-control"
                  v-show="resetSupData.supplierList && resetSupData.supplierList.length>0"
                  v-model="resetSupData.supplierId"
                >
                  <option
                    v-for="sup in resetSupData.supplierList"
                    :key="sup.id"
                    :value="sup.id"
                  >{{sup.name}}</option>
                </select>
                <span
                  v-show="!resetSupData.supplierList || resetSupData.supplierList.length==0"
                >暂无可以更换的供应商</span>
              </td>
              <td>
                <el-radio v-model="resetSupData.needSplit" :label="1">是</el-radio>
                <el-radio v-model="resetSupData.needSplit" :label="0">否</el-radio>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="resetSup()">确认</button>
        <button class="btn-fill-grey-main" @click="showResetSupModal=false">取消</button>
      </span>
    </el-dialog>
    <!-- 修改收货人信息 -->
    <el-dialog title="修改收货人信息:" :visible.sync="modifyAddressDailog" width="910px">
      <div class="dialog-form reset-sup">
        <div class="set-item" style="color:#E1376C;">地址仅支持修改一次，请谨慎修改</div>
        <div class="set-item">
          <span class="address-form-title">
            所在地区
            <i>*</i>
          </span>
          <el-select class="form-control my-el-select select" v-model="addressParams.province">
            <el-option
              v-for="item in area"
              :key="item.areaName"
              :label="item.areaName"
              :value="item.areaName"
            ></el-option>
          </el-select>
          <el-select class="form-control my-el-select select" v-model="addressParams.city">
            <el-option
              v-for="item in city"
              :key="item.areaName"
              :value="item.areaName"
              :label="item.areaName"
            ></el-option>
          </el-select>
          <el-select class="form-control my-el-select select" v-model="addressParams.district">
            <el-option
              v-for="item in district"
              :key="item.areaName"
              :value="item.areaName"
              :label="item.areaName"
            ></el-option>
          </el-select>
        </div>
        <div class="set-item">
          <span class="address-form-title">
            详细地址
            <i>*</i>
          </span>
          <textarea
            class="form-control textarea"
            placeholder="无需重复填写省市区，小于75个字"
            v-model="addressParams.address1"
            style="width:414px;"
          ></textarea>
        </div>
        <!-- <div class="set-item">
        <span class="address-form-title">客户称呼</span>
        <input class="form-control" placeholder="请输入客户称呼" />
        </div>-->
        <div class="set-item">
          <span class="address-form-title">
            收货人
            <i class="i">*</i>
          </span>
          <input
            class="form-control"
            placeholder="不能为昵称、X先生、X小姐等、请使用真实姓名"
            v-model="addressParams.name"
          />
        </div>
        <div class="set-item">
          <span class="address-form-title">
            手机号码
            <i>*</i>
          </span>
          <input class="form-control" placeholder="请输入真实手机号码" v-model="addressParams.phoneNum" />
        </div>
        <div class="set-item">
          <span class="address-form-title">身份证号</span>
          <input class="form-control" placeholder="请输入真实身份证号码" v-model="addressParams.idCardNum" />
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <button class="btn-main" @click="saveAddress()">确认</button>
        <button class="btn-fill-grey-main" @click="modifyAddressDailog=false">取消</button>
      </span>
    </el-dialog>
    <!-- 原地址展示 -->

    <applyForRefund
      v-if="showRefundModal"
      ref="showRefundModal"
      :refundParams="refundParams"
      v-on:refund="refund"
      v-on:cancelRefund="refundCancel"
    ></applyForRefund>
    <refundDetail
      v-if="showRefundDetailModal"
      :refundParams="refundDetailParams"
      v-on:refundDetail="closeRefundDetail"
      v-on:cancelRefundDetail="closeRefundDetail"
    ></refundDetail>
  </div>
</template>

<script>
import applyForRefund from "@/components/applyForRefund/index.vue";
import refundDetail from "@/components/refundDetail/index.vue";
import Pagination from "@/components/pagination";
export default {
  components: { applyForRefund, refundDetail, Pagination},
  data() {
    return {
      logisticsTab: "",
      isEdit:false,
      showLogisticsModal: false,
      showDelLogisticsModal: false,
      showSplitModal: false,
      showResetSupModal: false,
      showRefundModal: false,
      showRefundDetailModal: false,
      resetSupData: {},
      expCompany: [],
      refundParams: {},
      refundDetailParams: {},
      currentRightLogistics: {
        pkgExpressInfos: [],
        currentLogistics: {
          expressDetail: []
        }
      },
      params: {
        userId: "",
        orderNum: "",
        orderStatus: ""
      },
      detail: {
        orderInfo: {},
        orderItemInfo: [],
        receiverInfo: {}
      },
      addLogisticsParams: {
        expressCompany: "",
        expressNum: ""
      },
      delLogisticsParams: {},
      splitParams: {},
      splitDisplay: {
        id: "",
        limit: 0,
        goodsName: "",
        splitList: []
      },
      modifyAddressDailog: false, //修改收货人信息dailog
      addressParams: {
        orderNum: "",
        userId: "",
        name: "",
        phoneNum: "",
        idCardNum: "",
        country: "",
        province: "",
        city: "",
        district: "",
        address1: ""
      },
      area: [], //所有省市区数据
      city: [],
      district: [],
      originAddressDailog: false, //原地址展示
      controlLogList: [], // 操作记录
      total: 0,
      listParams: {
        limit: 10,
        page: 1
      }
    };
  },
  watch: {
    "addressParams.province"(val) {
      this.area.forEach(item => {
        if (item.areaName === val) {
          this.city = item.children;
          this.addressParams.city = this.city[0].areaName;
          // this.district = this.city[0].children;
          // this.addressParams.district = this.district[0].areaName;
        }
      });
    },
    "addressParams.city"(val) {
      this.city.forEach(item => {
        if (item.areaName === val) {
          this.district = item.children;
          this.addressParams.district = this.district[0].areaName;
        }
      });
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      this.params.userId = this.$route.params.userId || "";
      this.params.orderNum = this.$route.params.orderNum || "";
      this.params.orderStatus = this.$route.params.orderStatus || "";
      this.getUserOrderControlLog()
      // 获取所有物流公司
      this.api.common.expressCompany({
        hideNonExpressCompany:1
      }).then(res => {
        this.expCompany = res.data;
      });
      this.getDetail();
    },
    //确认收款
    payConfirm(item) {
      this.$msgbox({
        title: "",
        message: "是否确认收款？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          this.api.order
                  .payConfirm({
                    userId: this.params.userId,
                    orderNum: this.params.orderNum
                  })
                  .then(res => {
                    this.$message.success({ message: res.message, showClose: true });
                    this.getDetail();
                  });
        }
      });
    },
    // 推单
    pushOrder (item) {
      const { orderNum} = item;
      this.$msgbox({
        title: "订单推单",
        message: "确定要对该订单进行推单么？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let params = {
            userId:this.params.userId,
            orderNum
          }
          this.api.order.pushOrder(params).then(res => {
            this.$message.success({ message: res.message, showClose: true});
            this.getDetail();
          });
        }
      });
    },
    goGoodsDetail(goods) {
      const {itemNum} = goods
      // localStorage.setItem('groupGoodsInfo',JSON.stringify(item))
      // path = `commodity/detail/${item.id}/${this.listParams.status}`;
      // window.open(path, '_blank');
      let system = JSON.parse(localStorage.getItem('systemInfo'))
      const {pcServerAddr} = system
      if (pcServerAddr) {
        window.open(`${pcServerAddr}/center/goods/detail/${itemNum}`, '_blank')
      }
    },
    getDetail() {
      this.api.order.detail(this.params).then(res => {
        this.detail = res.data;
        if (res.data.orderItemInfo) {
          this.currentRightLogistics = res.data.orderItemInfo[0];
        }
        this.detail.orderItemInfo.forEach(item => {
          if (item.pkgExpressInfos && item.pkgExpressInfos.length > 0) {
            this.$set(item, "currentLogistics", item.pkgExpressInfos[0]);
          } else {
            this.$set(item, "currentLogistics", {});
          }
        });
      });
    },
    //获取所有省市区地址及赋值
    getAllAddress() {
      this.api.common.area({}).then(res => {
        this.area = res.data;
        this.addressParams = {
          userId: this.params.userId ? this.params.userId : "",
          orderNum: this.params.orderNum ? this.params.orderNum : "",
          name: this.detail.receiverInfo.receiver,
          phoneNum: this.detail.receiverInfo.phone,
          idCardNum: this.detail.receiverInfo.idCardNum,
          country: this.detail.receiverInfo.country,
          province: this.detail.receiverInfo.province,
          city: this.detail.receiverInfo.city,
          district: this.detail.receiverInfo.district,
          address1: this.detail.receiverInfo.address1
        };
      });
    },
    //修改地址
    saveAddress() {
      this.api.address.update(this.addressParams).then(res => {
        this.$message.success({ message: res.message, showClose: true });
        this.modifyAddressDailog = false;
        this.getDetail();
      });
    },
    changeLogTab(item, exp) {
      item.currentLogistics = exp;
    },
    openBackModal() {
      (this.refundParams.userId = this.params.userId),
        (this.refundParams.orderNum = this.params.orderNum),
        (this.showRefundModal = true);
    },
    openRefundDetailModal() {
      this.refundDetailParams = {
        userId: this.params.userId,
        orderNum: this.params.orderNum
      };
      this.showRefundDetailModal = true;
    },
    applyRefund() {
      this.$msgbox({
        title: "",
        message: "确认退款？",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      }).then(action => {
        if (action === "confirm") {
          let params = {
            userId: this.params.userId,
            orderRefundId: this.detail.orderInfo.orderRefundId,
            isPass: 1
          };
          this.api.order.applyRefund(params).then(res => {
            this.$message.success({ message: res.message, showClose: true });
            this.getList();
          });
        }
      });
    },
    refund(res) {
      this.console("refund:", res);
      this.showRefundModal = false;
    },
    refundCancel() {
      this.showRefundModal = false;
    },
    refundDetail() {
      this.showRefundDetailModal = false;
    },
    refundDetailCancel() {
      this.showRefundDetailModal = false;
    },
    setCurrent(item) {
      this.currentRightLogistics = item;
    },
    logisticsModal() {
      this.isEdit = false
      this.addLogisticsParams = {
        userId: this.params.userId,
        orderNum: this.params.orderNum,
        pkgNo: this.currentRightLogistics.pkgNo,
        expressCompany: "",
        expressNum: ""
      };
      if (
        !this.currentRightLogistics.pkgExpressInfos ||
        this.currentRightLogistics.pkgExpressInfos.length === 0
      ) {
        this.addLogisticsParams.type = 1;
        this.addLogisticsParams.parentId = 0;
      } else {
        this.addLogisticsParams.type =
          this.currentRightLogistics.pkgExpressInfos.length + 1;
        this.addLogisticsParams.parentId = this.currentRightLogistics.pkgExpressInfos[
          this.currentRightLogistics.pkgExpressInfos.length - 1
        ].expressId;
      }
      this.showLogisticsModal = true;
    },
    saveLogistics() {
      if(this.isEdit){
        this.api.order.updateOrderExpress({
          expressCompany:this.addLogisticsParams.expressCompany,
          expressNum:this.addLogisticsParams.expressNum,
          userId: this.params.userId,
          id:this.currentRightLogistics.pkgExpressInfos[0].expressId
        }).then(res=>{
          this.showLogisticsModal = false;
          this.getDetail();
        })
      }else {
        this.api.order.addExp(this.addLogisticsParams).then(res => {
          this.$message.success({ message: res.message, showClose: true });
          this.showLogisticsModal = false;
          this.getDetail();
        });
      }


    },
    //修改物流
    editLogistics(){
      this.isEdit = true;
      this.addLogisticsParams.expressCompany = this.currentRightLogistics.pkgExpressInfos[0].companyId;
      this.addLogisticsParams.expressNum = this.currentRightLogistics.pkgExpressInfos[0].expressNum;
      this.showLogisticsModal = true;

    },
    delLogisticsModal() {
      if (!this.currentRightLogistics.currentLogistics) {
        this.$message.info({ message: "没有可以删除的物流", showClose: true });
        return;
      }
      this.delLogisticsParams = {
        id: this.currentRightLogistics.currentLogistics.expressId,
        userId: this.params.userId
      };
      this.showDelLogisticsModal = true;
    },
    delLogistics() {
      if (!this.delLogisticsParams.id) {
        this.$message.info({ message: "没有可删除的物流", showClose: true });
        return;
      }
      this.api.order.delExp(this.delLogisticsParams).then(res => {
        this.$message.success({ message: res.message, showClose: true });
        this.showDelLogisticsModal = false;
        this.getDetail();
      });
    },
    splitPkg(item, goods) {
      this.showSplitModal = true;
      let splitList = [];
      this.splitDisplay.id = goods.orderItemId;
      this.splitDisplay.limit = goods.count;
      this.splitDisplay.goodsName = goods.itemTitle;
      this.detail.orderItemInfo.forEach(i => {
        splitList.push(i.pkgNo);
      });
      this.splitDisplay.splitList = splitList;
      this.splitParams.pkgNo = "";
      this.splitParams.num = "";
    },
    resetSupModal(item) {
      this.resetSupData = item;
      if (!this.resetSupData.needSplit) {
        this.$set(this.resetSupData, "needSplit", 0);
      }
      this.showResetSupModal = true;
    },
    resetSup() {
      let supplierName = "";
      this.resetSupData.supplierList.forEach(item => {
        if (item.id === this.resetSupData.supplierId) {
          supplierName = item.name;
        }
      });
      let params = {
        userId: this.params.userId,
        supplierId: this.resetSupData.supplierId,
        supplierName: supplierName,
        orderItemId: this.resetSupData.orderItemId,
        needSplit: this.resetSupData.needSplit
      };
      this.api.order.changeSupplier(params).then(res => {
        this.$message.success({ message: res.message, showClose: true });
        this.showResetSupModal = false;
        this.getDetail();
      });
    },
    //拆包
    splitPacket() {
      let params = {
        id: this.splitDisplay.id,
        pkgNo: this.splitParams.pkgNo,
        num: this.splitParams.num,
        orderUserId: this.params.userId,
        orderNum: this.params.orderNum
      };
      if (!params.num) {
        this.$message.info("拆包数量不能为空");
        return;
      }
      if (params.num > this.splitDisplay.limit) {
        this.$message.info("拆包数量不能超过该商品数");
        return;
      }
      this.api.order.split(params).then(res => {
        this.$message.success({ message: res.message, showClose: false });
        this.showSplitModal = false;
        this.getDetail();
      });
    },
    // 获取用户订单操作记录
    getUserOrderControlLog () {
      const {userId, orderNum} = this.params
      const {limit, page} = this.listParams
      let paramss = {
        userId,
        orderNum,
        page,
        limit
      }
      this.api.order.orderConrolLog(paramss).then(r => {
        if (r.success) {
           this.controlLogList = r.data && r.data.dataList || []
           this.total = r.data.total || 0
        }
      })
    }
  },
  filters:{
    vertInfo(cin){
      // 1, 待验证 2, 验证通过  3, 验证失败
      switch (cin.addressVerifyStatus-0) {
        case 0: return "";
        case 1: return `待验证（${cin.addressVerifyDesc}）`;
        case 2: return `验证通过（${cin.addressVerifyDesc}）`;
        case 3: return `验证失败（${cin.addressVerifyDesc}）`;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.order-detail {
  .order-process {
    margin-left: -54px;
    padding-bottom: 22px;
    .process-item {
      display: inline-block;
      vertical-align: top;
      width: 220px;
      text-align: center;
      position: relative;
      .process-name {
        color: #333333;
        font-size: 14px;
        padding-bottom: 14px;
      }
      .circle {
        width: 14px;
        height: 14px;
        background: #fff;
        border-radius: 14px;
        border: 4px solid #cccccc;
        margin-left: 104px;
      }
      .process-time {
        color: #666666;
        font-size: 12px;
        padding-top: 10px;
      }
      &::after {
        content: "";
        position: absolute;
        width: 175px;
        height: 1px;
        background: #ccc;
        right: -88px;
        top: 35px;
      }
      &:last-child {
        &::after {
          content: "";
          width: 0;
          height: 0;
        }
      }
    }
    .process-item-active {
      .circle {
        width: 14px;
        height: 14px;
        background: #fff;
        border-radius: 14px;
        border: 4px solid #70d008;
        margin-left: 104px;
      }
      &::after {
        background: #70d008;
      }
    }
  }
  .order-info {
    background: #fff;
    border-radius: 4px;
    padding: 20px;
    h2 {
      color: #333333;
      font-size: 14px;
      font-weight: bold;
      padding-bottom: 15px;
    }
    .receiver {
      padding-top: 10px;
      padding-bottom: 15px;
      .reciverImg{
        position: relative;
        .dialogReciver{
          width: 300px;
          height: 200px;
          display: none;
          position: absolute;
          left:25px;
          top:0;
          padding: 12px;
          z-index: 10;
          border-radius: 10px;
          background-color: #fff;
          border-color: transparent;
          border-style: solid;
          border: 1px solid #ebeef5;
          box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
        }
        &:hover .dialogReciver{
          display: block;
        }
      }
    }
    .info {
      .info-item {
        white-space: normal;
        word-break: break-all;
        padding-bottom: 10px;
        display: inline-block;
        vertical-align: top;
        width: 450px;
        .detial-address {
          width: 364px;
          white-space: nowrap;
          overflow:hidden;
          span {
            text-overflow:ellipsis;
            width: 100%;
            overflow: hidden;
          }
        }
        span {
          display: inline-block;
          vertical-align: top;
          font-size: 14px;
          color: #333333;
          line-height: 24px;
        }
        .link {
          font-size: 12px;
          display: inline-block;
          vertical-align: top;
          width: 60px;
          height: 24px;
          text-align: center;
          line-height: 22px;
          border: 1px solid $main-color;
          color: $main-color;
          border-radius: 4px;
        }
        .detail {
          font-size: 12px;
          display: inline-block;
          vertical-align: top;
          width: 86px;
          height: 24px;
          text-align: center;
          line-height: 22px;
          border: 1px solid #ddd;
          color: #666;
          border-radius: 4px;
          margin-left: 10px;
          cursor: pointer;
        }
        .name {
          width: 72px;
          text-align-last: justify;
        }
        .symbol {
          padding: 0 8px 0 2px;
        }
        .red-color {
          color: $main-color;
        }
        &:last-child {
          padding-bottom: 0;
        }
      }
    }
  }
  .order-content {
    position: relative;
    .order-inner {
      margin-top: 10px;
      background: #fff;
      width: calc(100% - 310px);
      border-radius: 4px;
      h2 {
        color: #333333;
        font-size: 14px;
        padding: 14px 16px;
        border-bottom: 1px solid $border-color;
        font-weight: bold;
      }
      .inner {
        width: 100%;
        tbody {
          tr {
            border-bottom: 1px solid $border-color;
          }
          .tips-top {
            background: #f5f5f5;
            border: none;
            .check-log {
              line-height: 44px;
              margin-right: 20px;
            }
            .pkgStatus {
              line-height: 44px;
              margin-right: 20px;
              font-size: 14px;
              color: #333333;
              font-weight: bold;
              span {
                font-size: 14px;
                color: #333333;
                font-weight: bold;
              }
              .pkgSuccess {
                color: #07a129;
              }
              .pkgError {
                color: #e61128;
              }
            }
          }
          .table-tips {
            font-size: 14px;
            color: #333333;
            line-height: 44px;
            font-weight: bold;
            margin-right: 40px;
            .tips-value {
              color: $main-color;
              font-weight: bold;
              padding-left: 10px;
            }
            a {
              padding-left: 20px;
              color: #5672dc;
            }
          }
        }
        th {
          line-height: 50px;
        }
        .item-info {
          min-width: 260px;
        }
        .table-w60 {
          width: 60px;
        }
        .table-w80 {
          width: 80px;
        }
        .table-w100 {
          width: 100px;
        }
        .table-w120 {
          width: 120px;
        }
        .table-w260 {
          width: 260px;
        }
        th,
        td {
          text-align: left;
          min-height: 50px;
          padding-left: 20px;
        }
        .channelPrice {
          font-weight: 500;
        }
        .price {
          color: $main-color;
          font-weight: 500;
        }
        .text-op-padding {
          position: relative;
          .table-op {
            white-space: nowrap;
            p {
              margin-bottom: 25px;
              display: inline-block;
              margin-right: 10px;
            }
            .splitPkg {
              font-size: 14px;
              display: inline-block;
              vertical-align: top;
              width: 56px;
              height: 30px;
              text-align: center;
              line-height: 28px;
              border: 1px solid $main-color;
              color: $main-color;
              border-radius: 4px;
              margin-left: 12px;
            }
            .resetSupModal {
              font-size: 14px;
              display: inline-block;
              vertical-align: top;
              width: 86px;
              height: 30px;
              text-align: center;
              line-height: 28px;
              border: 1px solid #dddddd;
              color: #666666;
              border-radius: 4px;
            }
          }
        }
        .goods-info {
          position: relative;
          padding: 20px 0 20px 110px;
          cursor: pointer;
          img {
            position: absolute;
            left: 20px;
            top: 20px;
            border-radius: 4px;
            border: 1px solid $border-color;
            max-height: 80px;
            max-width: 80px;
          }
          .item-title {
            vertical-align: top;
            font-size: 14px;
            color: #333333;
            height: 40px;
            line-height: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
          }
          p {
            font-size: 14px;
            color: #666666;
            line-height: 14px;
            padding: 20px 0;
            line-height: 20px;
            height: 14px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
          }
        }
        .info-body {
          tr {
            .supplier {
              vertical-align: top;
              font-size: 14px;
              color: #333;
              height: 40px;
              line-height: 20px;
              overflow: hidden;
              text-overflow: ellipsis;
              display: -webkit-box;
              -webkit-line-clamp: 2;
              -webkit-box-orient: vertical;
            }
          }
        }
      }
    }
    .order-logistics {
      position: absolute;
      width: 300px;
      right: 0;
      top: 0;
      background: #fff;
      border-radius: 4px;
      overflow: hidden;
      .title {
        height: 52px;
        padding: 12px 20px;
        border-bottom: 1px solid $border-color;
        span {
          color: #333;
          line-height: 28px;
          font-weight: bold;
        }
        .btn-main {
          margin-right: 10px;
        }
      }
      .logistics-inner {
        padding: 10px;
        .logistics-tab {
          span {
            display: inline-block;
            vertical-align: top;
            height: 32px;
            line-height: 32px;
            padding: 0 20px;
            border-left: 1px solid #dddddd;
            border-top: 1px solid #dddddd;
            margin-bottom: -1px;
            cursor: pointer;
            &:last-child {
              border-right: 1px solid #dddddd;
            }
          }
          .active {
            background: #f5f8fc;
            border-bottom: 1px solid #f5f8fc;
          }
        }
        .logistics-content {
          background: #f5f8fc;
          border: 1px solid #dddddd;
          padding: 0 20px 10px;
          .content-title {
            padding-bottom: 10px;
            border-bottom: 1px solid #dddddd;
            h5 {
              color: #333333;
              font-weight: bold;
              font-size: 14px;
              padding-top: 10px;
            }
          }
          p {
            color: #333333;
            font-size: 14px;
            line-height: 20px;
            padding-top: 10px;
          }
        }
      }
    }
  }
  .dialog-form {
    padding: 0;
    .search-item {
      padding-left: 20px;
      .form-control {
        width: 300px;
      }
    }
    h3 {
      font-size: 16px;
      line-height: 22px;
      text-align: center;
      padding: 0 20px;
    }
  }
  .border-bottom {
    padding-bottom: 15px;
  }
  .split-dialog {
    .search-item {
      padding-top: 15px;
      .goods-name {
        line-height: 30px;
      }
    }
  }
  .reset-sup {
    padding: 0;
    .table {
      padding-left: 0;
      .back-goods-img {
        display: inline-block;
        vertical-align: top;
        img {
          max-width: 54px;
          max-height: 54px;
        }
      }
      .back-goods-name {
        display: inline-block;
        vertical-align: top;
        width: 160px;
        margin-left: 10px;
        h2 {
          font-size: 14px;
          line-height: 18px;
          height: 36px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }
        p {
          font-size: 14px;
          max-width: 100%;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          margin-top: 5px;
        }
      }
      .el-radio + .el-radio {
        margin-left: 15px;
      }
    }
  }
  .order-control-log {
    background: #fff;
    overflow:hidden;
    margin-top: 10px;
    width: calc(100% - 310px);
    border-radius: 4px;
    border-bottom: 1px  solid #E5E5E5;
    h2 {
        height: 42px;
        padding: 0 20px;
        line-height: 42px;
        font-size: 14px;
        color: #333;
        font-weight: 500;
        border-bottom: 1px solid #E5E5E5;
    }
  }
}
.set-item {
  padding-bottom: 15px;
  .address-form-title {
    display: inline-block;
    vertical-align: top;
    width: 90px;
    line-height: 30px;
    text-align: right;
    font-size: 14px;
    color: #333;
    padding-right: 20px;
    position: relative;
    i {
      position: absolute;
      left: 0;
      top: 9px;
      font-size: 18px;
      color: $main-color;
      line-height: 1;
    }
    .i {
      left: 13px;
    }
  }
  .form-control {
    display: inline-block;
    vertical-align: top;
    width: 440px;
    font-size: 14px;
  }
  .select {
    width: 140px;
    min-width: 140px;
    margin-right: 10px;
  }
  .textarea {
    width: 440px;
    height: 70px;
  }
  .save {
    font-size: 18px;
    width: 160px;
    height: 50px;
    border-radius: 2px;
    border: 0;
    outline: medium;
    cursor: pointer;
    color: #fff;
    font-weight: bold;
    background: #f7316e;
    margin-left: 90px;
  }
}
/deep/.dialog-form {
    padding: 0;
    .set-item {
      padding-left: 20px;
      .my-el-select {
        &.select {
          width: 140px;
          border: 0 !important;
          height: 36px;
          .el-input {
            height: 100%;
            input {
              height: 100%;
            }
            .el-input__icon {
              line-height: 1;
            }
          }
        }
      }
    }
}
/deep/.noraw {
  width: 100%;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}
  .leftMar{
    margin-left: 15px;
  }
.item-titleName {
  vertical-align: top;
  font-size: 14px;
  color: #333333;
  height: 40px;
  line-height: 20px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
@media screen and (min-width: 1440px)  {
  /deep/.suplly {
    width: 180px !important;
  }
}
@media screen and (max-width: 1440px)  {
  /deep/.table-w260 {
    width: 170px !important;
    min-width: 170px !important;
  }
}

</style>
