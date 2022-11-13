package com.ismos_salt_erp.utils;


import com.ismos_salt_erp.R;

import java.util.ArrayList;
import java.util.List;

public class AccountsUtil {
    public static String receiveDue = "Receipt";
    public static String receiptList = "Receipt History List";
    public static String receiptPendingList = "Receipt Pending List";
    public static String receiptDeclinedList = "Receipt Declined List";

    public static String payDue = "Payment";
    public static String paymentList = "Payment History List";
    public static String paymentPendingList = "Payment Pending List";
    public static String paymentDeclinedList = "Payment Declined List";


    public static String payDueExpense = "Vendor Payment";
    public static String vendorPaymentList = "Vendor Payment List";
    public static String pendingVendorPayment = "Pending Vendor Payments";
    public static String declinedVendorPayments = "Declined Vendor Payments";



    public static String cash = "Cash Book";
    public static String dayBook = "Day Book";
    public static String payInstruction = "Payment Instruction";//
    public static String payInstructionList = "Payment Instruction List";//
    public static String transactionIn = "Transaction In";
    public static String transactionOut = "Transaction Out";
    public static String debitVoucher = "Debit Voucher";
    public static String debitVoucherHistory = "Debit Voucher History";
    public static String creditVoucher = "Credit Voucher";
    public static String creditVoucherHistory = "Credit Voucher History";
    public static String bankBalanceList = "Bank Balance List";
    public static String bnkTranxList = "Bank Transaction History";

    public static Integer receiveDueImage = R.drawable.ic_receipt_solid;//
    public static Integer receiptHistoryListImage = R.drawable.ic_receipt_history;//
    public static Integer payDueImage = R.drawable.ic_payment_instruction;
    public static Integer paymentHistoryListListImage = R.drawable.ic_payment_instruction_list;//
    public static Integer payDueExpenseImage = R.drawable.ic_expense_payment;
    public static Integer payInstructionImage = R.drawable.ic_payment;

    public static Integer payInstructionListImage = R.drawable.ic_expense_pament_list;
    public static Integer expenseDuePaymentHistory = R.drawable.ic_payment_history;
    public static Integer cashImage = R.drawable.ic_cashbook;
    public static Integer dayBookImage = R.drawable.ic_daybook;
    public static Integer transactionInImage = R.drawable.transfer_history_in;
    public static Integer transactionOitImage = R.drawable.transaction_out;
    public static Integer receiptPendingListImage = R.drawable.receipt_pending;
    public static Integer receiptDeclinedListImage = R.drawable.receipt_decline;
    public static Integer debitVoucherImage = R.drawable.ic_acc_due_receive;
    public static Integer debitVoucherHistoryImage = R.drawable.receipt_history;
    public static Integer creditVoucherImage = R.drawable.ic_acc_due_pay;
    public static Integer creditVoucherHistoryImage = R.drawable.payment_history;
    public static Integer bankTransactionListImage = R.drawable.transfer_history_in;
    public static Integer bankBalanceListImage = R.drawable.payment_history;


    public static List<String> getAccountsChildNameList() {
        List<String> accountChild = new ArrayList<>();
        accountChild.add(receiveDue);
        accountChild.add(receiptList);
        accountChild.add(receiptPendingList);
        accountChild.add(receiptDeclinedList);

        accountChild.add(payDue);
        accountChild.add(paymentList);
        accountChild.add(paymentPendingList);
        accountChild.add(paymentDeclinedList);


        accountChild.add(payDueExpense);
        accountChild.add(vendorPaymentList);
        accountChild.add(pendingVendorPayment);
        accountChild.add(declinedVendorPayments);



        accountChild.add(payInstruction);
        accountChild.add(payInstructionList);
        accountChild.add(bankBalanceList);
        accountChild.add(bnkTranxList);

        accountChild.add(transactionIn);
        accountChild.add(transactionOut);
        accountChild.add(debitVoucher);
        accountChild.add(debitVoucherHistory);
        accountChild.add(creditVoucher);
        accountChild.add(creditVoucherHistory);
        accountChild.add(cash);
        accountChild.add(dayBook);
        return accountChild;
    }

    public static List<Integer> getAccountsChildImageList() {
        List<Integer> imageList = new ArrayList<>();
        imageList.add(receiveDueImage);
        imageList.add(receiptHistoryListImage);
        imageList.add(receiptPendingListImage);
        imageList.add(receiptDeclinedListImage);

        imageList.add(payInstructionImage);
        imageList.add(expenseDuePaymentHistory);
        imageList.add(receiptPendingListImage);
        imageList.add(receiptDeclinedListImage);

        imageList.add(payDueExpenseImage);
        imageList.add(payInstructionListImage);
        imageList.add(receiptPendingListImage);
        imageList.add(receiptDeclinedListImage);

        imageList.add(payDueImage);
        imageList.add(paymentHistoryListListImage);
        imageList.add(R.drawable.ic_bank_balance_list);
        imageList.add(R.drawable.ic_bank_transaction_history);

        imageList.add(transactionInImage);
        imageList.add(transactionOitImage);
        imageList.add(R.drawable.ic_debit_boucher);
        imageList.add(R.drawable.ic_debit_boucher_list);
        imageList.add(R.drawable.ic_credit_voucher);
        imageList.add(R.drawable.ic_credit_voucher_history);
        imageList.add(cashImage);
        imageList.add(dayBookImage);
        return imageList;
    }
}
