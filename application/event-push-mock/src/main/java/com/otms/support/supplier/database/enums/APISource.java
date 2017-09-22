package com.otms.support.supplier.database.enums;

import com.otms.support.supplier.database.define.DBEnum;

/**
 * Created by bert on 17-9-11.
 */
public enum APISource implements DBEnum {

    ORDER_EVENT(1,"订单事件"),
    JOB_SHEET_EVENT(2,"运输单事件"),
    BILLING_EVENT(3,"账单事件"),
    CONFIRM_DISCREPANCY_EVENT(4,"确认货差事件");

    private Integer constant;

    private String message;

    APISource(Integer constant, String message) {

        this.constant = constant;
        this.message = message;
    }

    @Override
    public Integer getConstant() {
        return constant;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
