package org.prgrms.kdtspringw1d1.voucher;

import java.io.Serializable;
import java.util.UUID;

public interface Voucher{

    UUID getVoucherId();

    long discount(long beforeDiscount);

}
