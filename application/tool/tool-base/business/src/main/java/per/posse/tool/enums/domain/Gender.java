package per.posse.tool.enums.domain;

import per.posse.tool.utils.enums.DbEnum;

/**
 * Created by posse on 17-7-20.
 */
public enum Gender implements DbEnum {
    MALE(0, "male"),
    FEMALE(1, "female");

    private Integer dbConstant;
    private String messageKey;

    Gender(Integer dbConstant, String messageKey) {
        this.dbConstant = dbConstant;
        this.messageKey = messageKey;
    }

    public int getDbConstant() {
        return dbConstant;
    }

    public void setDbConstant(int dbConstant) {
        this.dbConstant = dbConstant;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
