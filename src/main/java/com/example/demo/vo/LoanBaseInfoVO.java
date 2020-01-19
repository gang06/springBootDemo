package com.example.demo.vo;

import java.io.Serializable;

/**
 * @author ligegang
 * @title: LoanBaseInfoVO
 * @projectName jfcimb-check-api
 * @description: TODO
 * @date 2019/11/8 11:13
 */
public class LoanBaseInfoVO implements Serializable {
    private static final long serialVersionUID = 5438613825782507256L;

    private Long id;
    private String loanAmt;
    private String contractAmt;
    private String period;
    private String interestRate;
    private String custName;
    private String mobile;
    private String custNo;
    private String orderState;
    private String receiveAcct;
    private String repaymentAcct;
    private String monthlyRepayment;
    private String purpose;
    private String riskScore;
    private String icoreLoanNo;

    public LoanBaseInfoVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(String loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(String contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getReceiveAcct() {
        return receiveAcct;
    }

    public void setReceiveAcct(String receiveAcct) {
        this.receiveAcct = receiveAcct;
    }

    public String getRepaymentAcct() {
        return repaymentAcct;
    }

    public void setRepaymentAcct(String repaymentAcct) {
        this.repaymentAcct = repaymentAcct;
    }

    public String getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(String monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(String riskScore) {
        this.riskScore = riskScore;
    }

    public String getIcoreLoanNo() {
        return icoreLoanNo;
    }

    public void setIcoreLoanNo(String icoreLoanNo) {
        this.icoreLoanNo = icoreLoanNo;
    }

}
