package com.djh.shanjupay.limit.client.exception;

/**
 * 限流异常
 *
 * @author MyMrDiao
 * @date 2022/03/19
 */
public class LimitException extends RuntimeException {
    /**错误码*/
    protected String code;

    public LimitException() {
    }

    public LimitException(Throwable ex) {
        super(ex);
    }
    public LimitException(String message) {
        super(message);
    }
    public LimitException(String code, String message) {
        super(message);
        this.code = code;
    }
    public LimitException(String message, Throwable ex) {
        super(message, ex);
    }
}
