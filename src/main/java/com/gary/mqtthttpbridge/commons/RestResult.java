package com.gary.mqtthttpbridge.commons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装统一返回结果
 *
 * 前后端分离开发，后端返回的数据都是json，那么json的格式要统一
 *
 */
@Data
@NoArgsConstructor //生成没有参数的构造方法
@AllArgsConstructor //生成所有参数的构造方法
public class RestResult {

    private int code;

    private String msg;

    private Object data;

    public static RestResult SUCCESS() {
        return SUCCESS(EnumCode.SUCCESS);
    }

    public static RestResult SUCCESS(int code, String msg) {
        return SUCCESS(code, msg, null);
    }

    public static RestResult SUCCESS(int code, String msg, Object data) {
        return new RestResult(code, msg, data);
    }

    public static RestResult SUCCESS(Object data) {
        return SUCCESS(EnumCode.SUCCESS, data);
    }

    public static RestResult SUCCESS(EnumCode enumCode) {
        return SUCCESS(enumCode.getCode(), enumCode.getMsg());
    }

    public static RestResult SUCCESS(EnumCode enumCode, Object data) {
        return SUCCESS(enumCode.getCode(), enumCode.getMsg(), data);
    }

    //-------------------------------------------------------------

    public static RestResult FAIL() {
        return FAIL(EnumCode.FAIL);
    }

    public static RestResult FAIL(int code, String msg) {
        return FAIL(code, msg, null);
    }

    public static RestResult FAIL(int code, String msg, Object data) {
        return new RestResult(code, msg, data);
    }

    public static RestResult FAIL(Object data) {
        return FAIL(EnumCode.FAIL, data);
    }

    public static RestResult FAIL(EnumCode enumCode) {
        return FAIL(enumCode.getCode(), enumCode.getMsg());
    }

    public static RestResult FAIL(EnumCode enumCode, Object data) {
        return FAIL(enumCode.getCode(), enumCode.getMsg(), data);
    }
}