package com.blessing333.springbasic.voucher.dto;

import com.blessing333.springbasic.voucher.domain.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoucherCreateForm {
    private Voucher.VoucherType voucherType;
    private long discountAmount;
}
