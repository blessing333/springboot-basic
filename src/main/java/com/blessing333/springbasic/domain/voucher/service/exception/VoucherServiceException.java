package com.blessing333.springbasic.domain.voucher.service.exception;

public class VoucherServiceException extends IllegalArgumentException{

    public VoucherServiceException(String message) {
        super(message);
    }

    public VoucherServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
