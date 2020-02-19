package com.chengzi.apiunion.procurement.admin.web.pojo.user;

import com.chengzi.apiunion.account.pojo.UserAccountFlowDO;
import com.chengzi.common.data.beans.JsonPojo;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-12 17:04
 */
public class TopUpRecordBO extends JsonPojo {
    private Date   createTime;

    private String flowNum;

    private int    flowType;

    private double account;

    private double balance;

    private int    flowStatus;

    private double rewardAmount;

    private String orderNum;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFlowNum() {
        return flowNum;
    }

    public void setFlowNum(String flowNum) {
        this.flowNum = flowNum;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    public double getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(double rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public static TopUpRecordBO convert(UserAccountFlowDO userAccountFlowDO) {
        TopUpRecordBO topUpRecordBO = new TopUpRecordBO();
        topUpRecordBO.setAccount(userAccountFlowDO.getFlowAmount().doubleValue());
        topUpRecordBO.setBalance(userAccountFlowDO.getBalance().doubleValue());
        topUpRecordBO.setCreateTime(userAccountFlowDO.getCreateTime());
        topUpRecordBO.setFlowNum(userAccountFlowDO.getFlowNum());
        topUpRecordBO.setFlowStatus(userAccountFlowDO.getFlowStatus());
        topUpRecordBO.setFlowType(userAccountFlowDO.getFlowType());
        topUpRecordBO.setRewardAmount(userAccountFlowDO.getRewardAmount() != null ? userAccountFlowDO.getRewardAmount().doubleValue() : 0);
        topUpRecordBO.setOrderNum(userAccountFlowDO.getOrderNum());
        return topUpRecordBO;
    }
}
