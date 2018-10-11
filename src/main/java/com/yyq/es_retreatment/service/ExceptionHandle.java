package com.yyq.es_retreatment.service;

import com.yyq.es_retreatment.entity.enums.ExceptionWrapperEnum;
import com.yyq.es_retreatment.entity.vo.CommonData;
import com.yyq.es_retreatment.exception.CustomException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * create by XiangChao on 2018/10/11
 */
@Component
public class ExceptionHandle {
    public CommonData handle(Exception e) {
        CommonData commonData = new CommonData();
        /**
         * 自定义异常
         */
        if (e instanceof CustomException) {
            commonData.setCode(((CustomException) e).getCode());
            commonData.setMessage(((CustomException) e).getMsg());
            return commonData;               
        }
        /**
         * 其他异常
         */
        //空指针
         if (e instanceof NullPointerException) {
            commonData.setCode(ExceptionWrapperEnum.NullPointerException.getCode());
            commonData.setMessage(ExceptionWrapperEnum.NullPointerException.getExplain1());
        }
        //数学运算异常--分母为零
        else if (e instanceof ArithmeticException) {
             commonData.setCode(ExceptionWrapperEnum.ArithmeticException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.ArithmeticException.getExplain1());
        }
        //数组下标越界
        else if (e instanceof ArrayIndexOutOfBoundsException) {
             commonData.setCode(ExceptionWrapperEnum.ArrayIndexOutOfBoundsException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.ArrayIndexOutOfBoundsException.getExplain1());
        }
        //数据操作异常
        else if (e instanceof DataAccessException) {
             commonData.setCode(ExceptionWrapperEnum.DataAccessException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.DataAccessException.getExplain1());
        }
        //数据库异常
        else if (e instanceof SQLException) {
             commonData.setCode(ExceptionWrapperEnum.SQLException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.SQLException.getExplain1());
        }
        //IO异常
        else if (e instanceof IOException) {
             commonData.setCode(ExceptionWrapperEnum.IOException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.IOException.getExplain1());
        }
        //指定类不存在
        else if (e instanceof ClassNotFoundException) {
             commonData.setCode(ExceptionWrapperEnum.ClassNotFoundException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.ClassNotFoundException.getExplain1());
        }
        //字符串转数字错误
        else if (e instanceof NumberFormatException) {
             commonData.setCode(ExceptionWrapperEnum.NumberFormatException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.NumberFormatException.getExplain1());
        }
        //参数错误
        else if (e instanceof IllegalArgumentException) {
             commonData.setCode(ExceptionWrapperEnum.NullPointerException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.NullPointerException.getExplain1());
        }
        //没有该类的访问权限
        else if (e instanceof IllegalAccessException) {
             commonData.setCode(ExceptionWrapperEnum.IllegalAccessException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.IllegalAccessException.getExplain1());
        }
        //数据类型转换异常
        else if (e instanceof ClassCastException) {
             commonData.setCode(ExceptionWrapperEnum.ClassCastException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.ClassCastException.getExplain1());
        }
        //数组存储异常
        else if (e instanceof ArrayStoreException) {
             commonData.setCode(ExceptionWrapperEnum.ArrayStoreException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.ArrayStoreException.getExplain1());
        }
        //文件未找到
        else if (e instanceof FileNotFoundException) {
             commonData.setCode(ExceptionWrapperEnum.FileNotFoundException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.FileNotFoundException.getExplain1());
        }
        //文件已结束
        else if (e instanceof EOFException) {
             commonData.setCode(ExceptionWrapperEnum.EOFException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.EOFException.getExplain1());
        }
        //违背安全原则
        else if (e instanceof SecurityException) {
             commonData.setCode(ExceptionWrapperEnum.SecurityException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.SecurityException.getExplain1());
        }
        //方法未找到
        else if (e instanceof NoSuchMethodException) {
             commonData.setCode(ExceptionWrapperEnum.NoSuchMethodException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.NoSuchMethodException.getExplain1());
        }
        //线程被中断
        else if (e instanceof InterruptedException) {
             commonData.setCode(ExceptionWrapperEnum.InterruptedException.getCode());
             commonData.setMessage(ExceptionWrapperEnum.InterruptedException.getExplain1());
        } else {
             commonData.setCode(ExceptionWrapperEnum.UNKNOW_ERROR.getCode());
             commonData.setMessage(ExceptionWrapperEnum.UNKNOW_ERROR.getExplain1());
        }
        return commonData;
    }
}
