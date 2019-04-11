package com.njit.zang.dto;

/**
 * Created by Administrator on 2019/3/19.
 */
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {

    public static final int SUCCESS_CODE = 0;
    public static final int FAILED_CODE = -1;

    public static final Result<Void> SUCCESS = Result.<Void>builder().code(SUCCESS_CODE).build();

    private int code;
    private T res;
    private String error;
    private String trace;
}
