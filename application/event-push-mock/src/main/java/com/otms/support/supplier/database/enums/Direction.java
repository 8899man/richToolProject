package com.otms.support.supplier.database.enums;

import com.otms.support.supplier.database.define.DBEnum;

public enum Direction implements DBEnum {

    IN(1, "in"),
    OUT(0, "out");

    private Integer constant;

    private String message;

    Direction(Integer constant, String message) {
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
