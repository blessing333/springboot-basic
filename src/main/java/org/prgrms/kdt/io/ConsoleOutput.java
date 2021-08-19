package org.prgrms.kdt.io;

import org.prgrms.kdt.strategy.Voucher;

public class ConsoleOutput implements Output {

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    @Override
    public void newLine() {
        System.out.println();
    }
}
