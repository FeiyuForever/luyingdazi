package com.luyingdazi.common.exception;

import com.luyingdazi.common.result.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author luyingdazi
 */
@Getter
public class BizException extends RuntimeException {

    private final int code;

    public BizException(String message) {
        super(message);
        this.code = ResultCode.FAIL.getCode();
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }
}
