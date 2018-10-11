package com.yyq.es_retreatment.exception;

import com.yyq.es_retreatment.entity.enums.ExceptionEnum;
import lombok.Data;

/**
 * create by XiangChao on 2018/10/11
 */
@Data
public class CustomException extends RuntimeException {
    /**
     * 错误编码
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;

    public CustomException(ExceptionEnum exceptionEnum) {
        this.setCode(exceptionEnum.getCode());
        this.setMsg(exceptionEnum.getMessage());
    }
}
