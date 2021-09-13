package org.prgms.w3d1.model.wallet;

import java.util.Optional;
import java.util.UUID;

public interface VoucherWalletRepository {

    Optional<VoucherWallet> findById(UUID voucherWalletId);

    Optional<VoucherWallet> findByCustomerId(UUID customerId);

    void insert(UUID voucherWalletId, UUID customerId);

    void deleteAll();
}
