package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultVoucherService implements VoucherService {
    private final VoucherRepository repository;

    @Transactional
    @Override
    public Voucher createNewVoucher(ConvertedVoucherCreateForm form) {
        UUID id = UUID.randomUUID();
        Voucher newVoucher = new Voucher(id,form.getVoucherType(), form.getDiscountAmount());
        repository.insert(newVoucher);
        return newVoucher;
    }

    @Override
    public List<Voucher> loadAllVoucher() {
        return repository.findAll();
    }
}
