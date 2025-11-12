package com.gary.mqtthttpbridge.commons;

import lombok.Getter;
import lombok.Setter;

public enum EnumCode {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败");

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String msg;

    EnumCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
