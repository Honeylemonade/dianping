package com.example.dianping.common;

import static com.example.dianping.common.CommonRs.Status.SUCCESS;
import static com.example.dianping.common.CommonRs.Status.FAIL;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xuyanpeng01 on 2022/4/16
 */
@Data
@AllArgsConstructor
public class CommonRs {

    public static final String API_WORK_WELL = "This API works well.";

    private Status status;

    private Object data;

    private String message;

    public static CommonRs success(Object data) {
        return new CommonRs(SUCCESS, data, API_WORK_WELL);
    }

    public static CommonRs success(Object data, String message) {
        return new CommonRs(SUCCESS, data, message);
    }

    /**
     * TODO 重构，增加状态码和异常信息绑定
     * TODO 自定义异常拦截器
     */
    public static CommonRs fail(Object data, String message) {
        return new CommonRs(FAIL, data, message);
    }

    public enum Status {
        SUCCESS,
        FAIL;
    }
}
