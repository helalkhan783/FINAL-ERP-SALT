package com.ismos_salt_erp.view.fragment.accounts;

public interface EditDebitVoucher {
    void updateDebitAndCreditVoucher(int position,String paymentId,String amount,String date,String voucherType,String paymentType,String orderId);
}
