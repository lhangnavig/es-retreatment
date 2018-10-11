package com.yyq.es_retreatment.entity.enums;

/**
 * create by XiangChao on 2018/10/11
 */

public enum ExceptionEnum {
    /**
     * 文件未找到异常
     */
    FILE_NOT_FOUND("001","文件未找到");

    private String code;
    private String message;
    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
