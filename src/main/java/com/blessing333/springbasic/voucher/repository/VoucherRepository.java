package com.blessing333.springbasic.voucher.repository;

import com.blessing333.springbasic.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {
    Optional<Voucher> findById(UUID id);
    List<Voucher> findAll();
    void save(Voucher voucher);
    void delete(UUID uuid);
}
