package com.ismos_salt_erp.retrofit;

import com.ismos_salt_erp.serverResponseModel.*;
import com.ismos_salt_erp.serverResponseModel.account_report.BankLedgerReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.DayBookReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.DebitorAndCreditorsReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentInstructionReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.PaymentReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report.TransactionReportResponse;
import com.ismos_salt_erp.serverResponseModel.account_report_response.ReceiptReportResponse;
import com.ismos_salt_erp.serverResponseModel.bankresponse.BankAccountDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.bankresponse.BankAccountListResponse;
import com.ismos_salt_erp.serverResponseModel.customer_report.CustomerReportResponse1;
import com.ismos_salt_erp.serverResponseModel.customer_report.SupplierResponse1;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.DashboardBalanceResponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.PieChartDataresponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.ProductionResponseJust;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.QcQaResponseTodays;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.SalePurchaseJustResponse;
import com.ismos_salt_erp.serverResponseModel.dashboard_repot.TopTenSupplierAndCustomerResponse;
import com.ismos_salt_erp.serverResponseModel.list_response.MillerLicenceReportListResponse;
import com.ismos_salt_erp.serverResponseModel.miller_response.MillerLicenceResponse;
import com.ismos_salt_erp.serverResponseModel.response.MillerLicenceReportResponse;
import com.ismos_salt_erp.serverResponseModel.view_details.MIllerViewResponse;
import com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier.ExpenseReportListReponse;
import com.ismos_salt_erp.view.fragment.all_report.customer_and_supplier.ExpenseType;
import com.ismos_salt_erp.view.fragment.all_report.employee_report.list.EmployeeReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.employee_report.miller.EmployeeMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.list.IodineReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.miller.IodineReportMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.page_data_response.IodineReportPageDataResponse;
import com.ismos_salt_erp.view.fragment.all_report.iodine_used_report.store.IodineReportStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.licence_expire_report.list.MillerLicenceExpireReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.list.PacketingReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.miller.PackagingMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.packaging_page_response.PackagingReportPageDataResponse;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.store.PackagingStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.list.PackegingReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.miller.PacketMIllerReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketingPageDataReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.store.PacketReportStorteResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.list_response.ReconciliationReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.miller_response.ReconciliationReportMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response.ReconciliationPageDataResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.store.ReconciliationStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.get_miller_by_association.MillerReportByAssociationResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.purchase_store.PurchaseReportStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report.PurchaseReturnReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_return_report.store.PurchaseReturnReportStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_response.SaleReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.miller_response.SaleReturnReportMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.page_data_response.SaleReturnReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.sale_return_report_list.SaleReturnReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.store.SaleReturnReportStoreResponse;
import com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.list_response.StockIOReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.response.StockIOReportResponse;
import com.ismos_salt_erp.view.fragment.auth.sign_up.SuccessResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineReconciliationListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockDeclineTransferredListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingReconciliationListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockPendingTransferredListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockReconciliationHistoryListResponse;
import com.ismos_salt_erp.view.fragment.stock.all_response.StockTransferHistoryListResponse;
import com.ismos_salt_erp.view.fragment.store.list_response.StoreListResponse;
import com.ismos_salt_erp.viewModel.TransferReportPageDataResponse;

import java.util.List;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("employee/verify-phone.php?verify_otp=1")
    Call<SuccessResponse> sendOtpCode(
            @Query("token") String token,
            @Field("primary_mobile") String primary_mobile,
            @Field("otp_code") String otp_code
    );

    @FormUrlEncoded
    @POST("employee/verify-phone.php?send_otp=1")
    Call<SuccessResponse> sendPhoneNumberForGetOtp(
            @Query("token") String token,
            @Field("primary_mobile") String primary_mobile
    );

    /**
     * For get permission
     */
    @FormUrlEncoded
    @POST("profile/user-password.php")
    Call<PermissionResponse> getCurrentUserPermissions(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("get_permission") String get_permission,//this value should be 1
            @Field("user_name") String user_name
    );

    @FormUrlEncoded
    @POST("products/product-trash.php?delete_product=1")
    Call<DuePaymentResponse> itemDeleteFromItemTrashList(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String itemsVendorID,
            @Field("storeID") String itemsStoreID,
            @Field("productID") String productID,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("products/products.php?update_product_tag=1")
    Call<DuePaymentResponse> saveAddTagResponse(
            @Query("token") String token,
            @Field("vendorID") String itemsVendorID,
            @Field("user_id") String user_id,
            @Field("productID") String productID,
            @Field("packet_id") String packet_id
    );

    @FormUrlEncoded
    @POST("miller/miller_data.php?licence_info=1")
    Call<MillerLicenseInfoEditResponse> getLicensePreviousInfo(
            @Query("token") String token,
            @Field("slID") String slID
    );


    @FormUrlEncoded
    @POST("miller/miller_data.php?owner_data=1")
    Call<OwnerDetailsResponse> getOwnerDetailsResponse(
            @Query("token") String token,
            @Field("slID") String slID
    );

    @FormUrlEncoded
    @POST("qcqa/update_qcqa.php")
    Call<DuePaymentResponse> approveDeclineQcQa(
            @Query("token") String token,
            @Field("reviewStatus") String reviewStatus,
            @Field("declined_remarks") String declined_remarks,
            @Field("user_id") String user_id,
            @Field("approve_declined") String approve_declined,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("sales/cancel-order.php?approve=1")
    Call<DuePaymentResponse> salesWholeOrderCancelApprove(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("note") String note
    );


    @FormUrlEncoded
    @POST("sales/cancel-order.php?decline=1")
    Call<DuePaymentResponse> salesWholeOrderCancelDecline(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("note") String note
    );


    @FormUrlEncoded
    @POST("purchase/return.php?approve=1")
    Call<DuePaymentResponse> purchaseReturnApprove(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("id") String id,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("purchase/return.php?decline=1")
    Call<DuePaymentResponse> purchaseReturnDecline(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("id") String id,
            @Field("note") String note
    );


    @FormUrlEncoded
    @POST("qcqa/qcqa-form-data.php?get_qcqa_details=1")
    Call<Qc_qaDetailsResponse> getQcQaDetailsBySlid(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("type") String type
    );


    @FormUrlEncoded
    @POST("production/iodization.php?decline=1")
    Call<DuePaymentResponse> declineIodization(
            @Query("token") String token,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("note") String note,
            @Field("profile_type_id") String profile_type_id
    );

    @FormUrlEncoded
    @POST("production/iodization.php?approve=1")
    Call<DuePaymentResponse> approveIodization(
            @Query("token") String token,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("note") String note,
            @Field("profile_type_id") String profile_type_id
    );

    @FormUrlEncoded
    @POST("notification-msg/msg.php?update_seen=1")
    Call<DuePaymentResponse> sendSeenStatus(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("msg_id") String msg_id
    );

    @FormUrlEncoded
    @POST("notification-msg/msg.php?msg_list=1")
    Call<GetInboxListResponse> getInboxList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("sales/cancel-order.php?decline=1")
    Call<DuePaymentResponse> declineSalesReturnWholeOrderCancel(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String userId,
            @Field("profile_type_id") String profile_type_id,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("sales/cancel-order.php?approve=1")
    Call<DuePaymentResponse> approveSalesReturnWholeOrderCancel(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String userId,
            @Field("profile_type_id") String profile_type_id,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("sales/cancel-order.php?details=1")
    Call<PendingSalesReturnDetailsResponse> getReturnSalesOrderDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("purchase/cancel-order.php?purchase_cancel=1")
    Call<DuePaymentResponse> purchaseWholeOrderCancel(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeIDSession,
            @Field("orderID") String orderID
    );

    @FormUrlEncoded
    @POST("sales/cancel-order.php?sell_cancel=1")
    Call<DuePaymentResponse> salesWholeOrderCancel(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeIDSession,
            @Field("orderID") String orderID
    );

    @FormUrlEncoded
    @POST("sales/return.php?return=1")
    Call<DuePaymentResponse> submitSalesReturn(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("storeID") String storeIDSession,
            @Field("grand_total_value") String grand_total_value,
            @Field("paid_amount") String paid_amount,
            @Field("order_detailsID[]") List<String> order_detailsID,
            @Field("productIDs[]") List<Integer> productIDsList,
            @Field("return_quantity[]") List<String> return_quantity,
            @Field("sell_price[]") List<String> sell_price,
            @Field("pr_title[]") List<String> pr_titleList,
            @Field("p_unit[]") List<String> p_unit,
            @Field("sold_from_store[]") List<String> sold_from_store,
            @Field("storeIDs[]") List<String> storeIDs,//
            @Field("ttl_amount")String subtotal, // subtotal
            @Field("compensation_input") String compensession,
            @Field("compensation_type") String compensessionType,
            @Field("return_amount") String returnAmount,
            @Field("return_paid_amount") String returnPaidAmount

    );


    @FormUrlEncoded
    @POST("sales/return.php?return-initial-data=1")
    Call<GetPurchaseReturnResponse> getSalesReturnDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("order_serial") String order_serial
    );


    @FormUrlEncoded
    @POST("purchase/return.php?return=1")
    Call<DuePaymentResponse> submitPurchaseReturn(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("storeID") String storeIDSession,
            @Field("grand_total_value") String grand_total_value,
            @Field("order_detailsID[]") List<String> order_detailsID,
            @Field("productIDs[]") List<Integer> productIDsList,
            @Field("return_quantity[]") List<String> return_quantity,
            @Field("buy_price[]") List<String> buy_price,
            @Field("pr_title[]") List<String> pr_titleList,
            @Field("p_unit[]") List<String> p_unit,
            @Field("sold_from_store[]") List<String> sold_from_store,
            @Field("paid_amount") String paid_amount,
            @Field("storeIDs[]") List<String> storeIDs,//
            @Field("ttl_amount")String subtotal, // subtotal
            @Field("compensation_input") String compensession,
            @Field("compensation_type") String compensessionType,
            @Field("return_amount") String returnAmount,
            @Field("return_paid_amount") String returnPaidAmount


    );

    @FormUrlEncoded
    @POST("purchase/return.php?return-initial-data=1")
    Call<GetPurchaseReturnResponse> getPurchaseReturnDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("order_serial") String order_serial
    );


    @FormUrlEncoded
    @POST("stock/reconciliation-edit.php?update=1")
    Call<DuePaymentResponse> submitConfirmEditReconciliationData(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("user_id") String user_id,
            @Field("orderSerial") String orderSerial,
            @Field("orderID") String orderID,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeIDSession,
            @Field("productIDs[]") Set<Integer> productIDList,
            @Field("product_titles[]") List<String> product_titlesList,
            @Field("quantities[]") List<String> quantities,
            @Field("selling_prices[]") List<Double> selling_priceList,
            @Field("sold_froms[]") List<String> sold_fromsList,
            @Field("old_sold_from[]") List<String> old_sold_fromList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("note") String note,
            @Field("damage") String damage,
            @Field("order_date") String order_date
    );


    @FormUrlEncoded
    @POST("stock/reconciliation-edit.php?edit-initial-data=1")
    Call<EditReconcilationPageResponse> getReconcilationPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("orderSerial") String orderSerial
    );

    @FormUrlEncoded
    @POST("stock/transfer-edit.php?update=1")
    Call<DuePaymentResponse> submitEditTransferInfo(
            @Query("token") String token,
            @Field("store") String store,
            @Field("profile_type_id") String profile_type_id,
            @Field("user_id") String user_id,
            @Field("orderSerial") String orderSerial,
            @Field("orderID") String orderID,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeIDSession,
            @Field("productIDs[]") Set<Integer> productIDList,
            @Field("product_titles[]") List<String> product_titlesList,
            @Field("quantities[]") List<Double> quantities,
            @Field("selling_prices[]") List<Double> selling_priceList,
            @Field("sold_froms[]") List<String> sold_fromsList,
            @Field("old_sold_from[]") List<String> old_sold_fromList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("transfer_storeID") String transfer_storeID,
            @Field("note") String note,
            @Field("transfer_from_enterprise") String transfer_from_enterprise,
            @Field("transfer_to_enterprise") String transfer_to_enterprise
    );

    @FormUrlEncoded
    @POST("production/washing-crushing.php?decline=1")
    Call<DuePaymentResponse> declineWashingAndCrushing(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("stock/transfer-edit.php?update-check-stock=1")
    Call<DuePaymentResponse> getTransferStockList(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("productID[]") List<String> productIdList,
            @Field("sold_from[]") List<String> soldFromList,
            @Field("quantity[]") List<String> quantity,
            @Field("product_title[]") List<String> product_titleList,
            @Field("previous_quantity[]") List<String> previous_quantity,
            @Field("old_sold_from[]") List<String> old_sold_fromList
    );


    @FormUrlEncoded
    @POST("stock/transfer-edit.php?edit-initial-data=1")
    Call<EditTransferInfoResonse> getEditTransferInfo(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("orderSerial") String orderSerial
    );


    @Multipart
    @POST("brand/api.php?brand_create=1")
    Call<DuePaymentResponse> addNewBrand(
            @Query("token") String token,
            @Part("brand_name") RequestBody brand_name,
            @Part("vendorID") RequestBody vendorID,
            @Part("user_id") RequestBody userId,
            @Part("storeID") RequestBody storeID,
            @Part MultipartBody.Part distributor
    );

    @FormUrlEncoded
    @POST("store/store.php?delete_store=1")
    Call<DuePaymentResponse> deleteStore(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID);

    @FormUrlEncoded
    @POST("miller/add_miller.php?apply_submit=1")
    Call<DuePaymentResponse> millerDetailsSubmitApproval(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("sl") String sl,
            @Field("is_submit") String is_submit
    );


    @FormUrlEncoded
    @POST("production/iodide-edit.php?update=1")
    Call<DuePaymentResponse> submitIodizationEdit(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("selling_price[]") List<String> selling_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("discount[]") List<String> discountList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("old_sold_from[]") List<String> old_sold_fromList,
            @Field("order_date") String order_date,
            @Field("total_amount") String total_amount,
            @Field("note") String note,
            @Field("old_destination_store") String old_destination_store,
            @Field("stage") String stage,
            @Field("destination_store") String destination_store
    );

    @FormUrlEncoded
    @POST("stock/api.php?prod-stock-infos-with-unit2=1")
    Call<GetEditedItemStockResponse> getEditedItemStockList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("productIDs[]") List<String> productIDsList,
            @Field("storeIDs[]") List<String> storeIDsList
    );


    @FormUrlEncoded
    @POST("stock/reconciliation.php?new-reconcillation=1")
    Call<DuePaymentResponse> submitAddNewReconcilation(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String selectedEnterPrise,
            @Field("productIDs[]") List<Integer> productIdList,
            @Field("quantities[]") List<String> quantitiesSet,
            @Field("selling_prices[]") List<Double> selling_prices,
            @Field("units[]") List<String> unitsSet,
            @Field("product_titles[]") List<String> product_titles,
            @Field("sold_froms[]") List<String> sold_froms,
            @Field("damage") String reconcilationType,
            @Field("order_date") String order_date,
            @Field("note") String note,
            @Field("customerID") Integer customerId
    );


    @FormUrlEncoded
    @POST("stock/transfer.php?new-transfer=1")
    Call<DuePaymentResponse> addNewTransfer(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("session_storeID") String storeID,//login storeId
            @Field("transfer_froms[]") List<Integer> firstPageStoreIdSet,
            @Field("productIDs[]") Set<Integer> productIdSet,
            @Field("transfer_storeID") String transferTo,
            @Field("quantities[]") List<Integer> quantitiesSet,
            @Field("buying_prices[]") List<Double> buying_pricesSet,
            @Field("units[]") List<String> unitsSet,
            @Field("product_titles[]") List<String> product_titles,
            @Field("transfer_from_enterprise") String transfer_from_enterprise,
            @Field("transfer_to_enterprise") String transfer_to_enterprise,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("stock/transfer.php?transfer_from_stores=1")
    Call<AddNewSaleStoreResponse> addNewSaleStoreData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id
    );


    @FormUrlEncoded
    //@POST("employee/users-permission.php?edit-initial-data=1")
    @POST("employee/users-permission.php?set-permissions=1")
    Call<DuePaymentResponse> submitPermissionUpdateInfo(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("employee_user_id") String employee_user_id,
            @Field("employee_profile_id") String employee_profile_id,
            @Field("permission[]") Set<Integer> permissionList,
            @Field("store_access[]") Set<Integer> store_accessList
    );


    @FormUrlEncoded
    @POST("employee/users-permission.php?edit-initial-data=1")
    Call<UserPermissions> getAllPermissions(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("employee_user_id") String employee_user_id
    );


    @FormUrlEncoded
    @POST("employee/users.php?list=1")
    Call<ManageAccessibilityResponse> getMangeUserAccessibility(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("employee/users.php?trash-list=1")
    Call<UserTrashListResponse> getUserTrashList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id
    );


    @Multipart
    @POST("employee/edit-employee.php?edit-employee=1")
    Call<DuePaymentResponse> submitEditUserInfo(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("employee_profile_id") RequestBody employee_profile_id,
            @Part("employee_vendorID") RequestBody employee_vendorID,
            @Part("designation") RequestBody designation,
            @Part("departmentID") RequestBody departmentID,
            @Part("Title") RequestBody Title,
            @Part("DisplayName") RequestBody DisplayName,
            @Part("FullName") RequestBody FullName,
            @Part("Gender") RequestBody Gender,
            @Part("PrimaryMobile") RequestBody PrimaryMobile,
            @Part("Email") RequestBody Email,
            @Part("storeID") RequestBody storeID,
            @Part("About") RequestBody About,
            @Part("DateOfBirth") RequestBody DateOfBirth,
            @Part("BloodGroup") RequestBody BloodGroup,
            @Part("Nationality") RequestBody Nationality,
            @Part("AlternativeEmail") RequestBody AlternativeEmail,
            @Part("OtherContactNumbers") RequestBody OtherContactNumbers,
            @Part("Website") RequestBody Website,
            @Part("CreatedDate") RequestBody joiningDate,
            @Part MultipartBody.Part user_photo
    );


    @FormUrlEncoded
    @POST("employee/edit-employee.php?details=1")
    Call<EditUserDataResponse> getEditUserPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("employee_profile_id") String employee_profile_id

    );


    @FormUrlEncoded
    @POST("employee/users.php?list=1")
    Call<UserListResponse> getUserList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("departmentID") String departmentID,
            @Field("designationID") String designationID

    );

    @Multipart
    @POST("employee/add-employee.php?add-employee=1")
    Call<DuePaymentResponse> submitAddNewUserInfo(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("designation") RequestBody designation,
            @Part("departmentID") RequestBody departmentID,
            @Part("Title") RequestBody title,
            @Part("DisplayName") RequestBody displayName,
            @Part("FullName") RequestBody FullName,
            @Part("Gender") RequestBody Gender,
            @Part("PrimaryMobile") RequestBody PrimaryMobile,
            @Part("Email") RequestBody Email,
            @Part("storeID") RequestBody storeID,
            @Part("About") RequestBody About,
            @Part("DateOfBirth") RequestBody DateOfBirth,
            @Part("BloodGroup") RequestBody BloodGroup,
            @Part("Nationality") RequestBody Nationality,
            @Part("AlternativeEmail") RequestBody AlternativeEmail,
            @Part("OtherContactNumbers") RequestBody OtherContactNumbers,
            @Part("Website") RequestBody Website,
            @Part("CreatedDate") RequestBody joiningDate,
            @Part MultipartBody.Part user_photo,
            @Part("password") RequestBody password,
            @Part("store_access[]") Set<Integer> store_accessList
    );

    @FormUrlEncoded
    @POST("employee/add-employee.php?get-initial-data=1")
    Call<AddNewUserResponse> getAddNewUserPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );


    @Multipart
    @POST("products/product-edit.php?update_product=1")
    Call<DuePaymentResponse> submitItemEditInformation(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("user_id") RequestBody user_id,
            @Part("productID") RequestBody productID,
            @Part("product_title") RequestBody product_title,
            @Part("categoryID") RequestBody categoryID,
            @Part("unit") RequestBody unit,
            @Part("brandID") RequestBody brandID,
            @Part("product_dimensions") RequestBody product_dimensions,//this is weight
            @Part("product_details") RequestBody product_details,
            @Part MultipartBody.Part product_image
    );


    @FormUrlEncoded
    @POST("products/product-edit-data.php?get_data=1")
    Call<EditItemResponse> getEditPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("productID") String productID
    );


    @FormUrlEncoded
    @POST("products/product-edit.php?update_product_price=1")
    Call<DuePaymentResponse> editItemPriceData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_Id,
            @Field("productID") String productID,
            @Field("product_priceID") String product_priceID,
            @Field("selling_price") String selling_price,
            @Field("old_selling_price") String old_selling_price
    );

    @FormUrlEncoded
    @POST("products/product-edit.php?add_new_price=1")
    Call<DuePaymentResponse> addNewPrice(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_Id,
            @Field("productID") String productID,
            @Field("selling_price") String selling_price
    );


    @FormUrlEncoded
    @POST("products/product-add.php?item_initial_stock=1")
    Call<DuePaymentResponse> submitConfirmAddNewItemInfo(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("productID") String productID,
            @Field("b_price[]") List<String> b_priceList,//0
            @Field("initial_stock[]") List<String> initial_stockList,//qn
            @Field("store[]") List<String> storeList//list of store id from list
    );


    @FormUrlEncoded
    @POST("products/product-add-data.php?get-initial-stockable-store=1")
    Call<ItemStoreList> getItemStoreList(
            @Query("token") String token,
            @Field("productID") String productID,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );


    @Multipart
    @POST("products/product-add.php?create_product=1")
    Call<AddNewItemSubmitResponse> submitAddNewItemInfo(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("user_id") RequestBody user_id,
            @Part("storeID") RequestBody storeID,
            @Part("categoryID") RequestBody categoryID,
            @Part("product_title") RequestBody product_title,
            @Part("unit") RequestBody unit,
            @Part("brandID") RequestBody brandID,
            @Part("product_dimensions") RequestBody product_dimensions,
            @Part("product_details") RequestBody product_details,
            @Part MultipartBody.Part product_image,
            @Part("is_grouped") RequestBody is_grouped,
            @Part("selling_price") RequestBody selling_price
    );


    @FormUrlEncoded
    @POST("products/product-add-data.php?get_pcode=1")
    Call<ItemCodeResponse> getItemCodeByCatId(
            @Query("token") String token,
            @Field("category") String category
    );


    @FormUrlEncoded
    @POST("products/product-add-data.php?get_data=1")
    Call<AddNewItemResponse> getAddNewItemPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?info=1")
    Call<HomePageResponse> getHomePageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("profileID") String profileID
    );

    @FormUrlEncoded
    @POST("production/iodide-edit.php?iodine-stock-order=1")
    Call<EditIodizationStock> getEditIodizationStock(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID
    );

    @FormUrlEncoded
    @POST("production/iodide-edit.php?edit-initial-data=1")
    Call<EditWashingCrushingResponse> getIodizationPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderSerial") String orderSerial
    );

    @FormUrlEncoded
    @POST("production/washing-crushing-edit.php?update-check-stock=1")
    Call<DuePaymentResponse> washingCrushingStockMessageCheck(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("old_sold_from[]") List<String> old_sold_fromList
    );


    @FormUrlEncoded
    @POST("production/iodide-edit.php?update-check-stock=1")
    Call<DuePaymentResponse> editIodizationStockStockMessageCheck(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("old_sold_from[]") List<String> old_sold_fromList
    );


    @FormUrlEncoded
    @POST("production/washing-crushing-edit.php?update=1")
    Call<DuePaymentResponse> submitEditedWashingCrushingData(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("selling_price[]") List<String> selling_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("discount[]") List<String> discountList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("old_sold_from[]") List<String> old_sold_fromList,
            @Field("order_date") String order_date,
            @Field("total_amount") String total_amount,
            @Field("note") String note,
            @Field("old_destination_store") String old_destination_store,
            @Field("stage") String stage,
            @Field("destination_store") String destination_store
    );

    @FormUrlEncoded
    @POST("reports/customer-ledger-form.php?customer-ledger=1")
    Call<CustomerReportResponse> getCustomerReportPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_Id

    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?bank_list=1")
    Call<BankListResposne> bankData(
            @Query("token") String token,
            @Field("user_id") String user_Id,
            @Field("store_access") String store_access,
            @Field("vendorID") String vendorID

    );

    @FormUrlEncoded
    @POST("expense/expense-report.php?expense_type=1")
    Call<ExpenseType> expenseTypeList(
            @Query("token") String token,
            @Field("vendorID") String vendorID

    );

    @FormUrlEncoded
    @POST("reports/receipt-report-form.php?receipt=1")
    Call<UserDataResponse> getUserpageList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_Id,
            @Field("store_access") String store_access

    );

    @FormUrlEncoded
    @POST("production/washing-crushing-edit.php?edit-initial-data=1")
    Call<EditWashingCrushingResponse> getEditWashingCrushingInfo(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("orderSerial") String orderSerial
    );

    @FormUrlEncoded
    @POST("purchase/edit.php?update=1")
    Call<DuePaymentResponse> submitPurchaseEditData(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("p_unit[]") List<String> p_unitList,
            @Field("selling_price[]") List<String> selling_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("discountList[]") List<String> discountList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("payment_type") String payment_type,
            @Field("discount_amount") String discount_amount,
            @Field("discount_type") String discount_type,
            @Field("order_date") String order_date,
            @Field("total_amount") String total_amount,
            @Field("paid_amount") String paid_amount,
            @Field("custom_discount") String custom_discount,
            @Field("note") String note

    );


    @FormUrlEncoded
    @POST("sales/edit.php?update-check-stock=1")
    Call<DuePaymentResponse> salesEditPageStockCheck(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("previous_quantity[]") List<String> previous_quantityList
    );

    @FormUrlEncoded
    @POST("sales/edit.php?update=1")
    Call<DuePaymentResponse> submitSalesEditData(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("orderSerial") String orderSerial,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("p_unit[]") List<String> p_unitList,
            @Field("selling_price[]") List<String> selling_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("discountList[]") List<String> discountList,
            @Field("previous_quantity[]") List<String> previous_quantityList,
            @Field("payment_type") String payment_type,
            @Field("discount_amount") String discount_amount,
            @Field("discount_type") String discount_type,
            @Field("order_date") String order_date,
            @Field("total_amount") String total_amount,
            @Field("paid_amount") String paid_amount,
            @Field("custom_discount") String custom_discount,
            @Field("note") String note
    );


    @FormUrlEncoded
    @POST("purchase/edit.php?edit-initial-data=1")
    Call<GetPreviousSaleInfoResponse> getEditPurchaseInfo(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderSerial") String orderSerial
    );


    @FormUrlEncoded
    @POST("sales/edit.php?edit-initial-data=1")
    Call<GetPreviousSaleInfoResponse> getEditSaleInfo(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderSerial") String orderSerial
    );


    @FormUrlEncoded
    @POST("miller/add_miller.php?update_employee_info=1")
    Call<DuePaymentResponse> updateEmployeeInfo(
            @Query("token") String token,
            @Field("update_employee_info") String update_employee_info,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("status") String status,
            @Field("totalTechFemale") String totalTechFemale,
            @Field("totalTechMale") String totalTechMale,
            @Field("partTimeFemail") String partTimeFemail,
            @Field("partTimeMale") String partTimeMale,
            @Field("fullTimeFemale") String fullTimeFemale,
            @Field("fullTimeMale") String fullTimeMale,
            @Field("profileID") String profileID,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("miller/add_miller.php?update_qc_qa_info=1")
    Call<DuePaymentResponse> updateQcInformation(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("status") String status,
            @Field("labRemarks") String labRemarks,
            @Field("laboratoryPerson") String laboratoryPerson,
            @Field("useTestKit") String useTestKit,
            @Field("trainedLaboratoryPerson") String trainedLaboratoryPerson,
            @Field("standardProcedure") String standardProcedure,
            @Field("haveLaboratory") String haveLaboratory,
            @Field("profileID") String profileID,
            @Field("id") String id,
            @Field("main_id") String main_id
    );

    @Multipart
    @POST("miller/add_miller.php?update_certificate_info=1")
    Call<DuePaymentResponse> updateMillerCertificateInfo(
            @Query("token") String token,
            @Part("slID") RequestBody slID,
            @Part("certificateTypeID") RequestBody certificateTypeID,
            @Part("profileID") RequestBody profileID,
            @Part("issuerName") RequestBody issuerName,
            @Part("issueDate") RequestBody issueDate,
            @Part("certificateDate") RequestBody certificateDate,
            @Part MultipartBody.Part certificateImage,
            @Part("renewDate") RequestBody renewDate,
            @Part("remarks") RequestBody remarks,
            @Part("status") RequestBody status,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("storeID") RequestBody storeID,
            @Part("vendorID") RequestBody vendorID


//            @Part("reviewStatus") RequestBody reviewStatus,
//            @Part("reviewTime") RequestBody reviewTime,
//            @Part("reviewBy") RequestBody reviewBy,
//            @Part("ref_slId") RequestBody ref_slI
    );


    @FormUrlEncoded
    @POST("miller/add_miller.php?update_owner_details=1")
    Call<DuePaymentResponse> updateMillerOwnerInfo(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("ownerName") String ownerName,
            @Field("profileID") String profileID,
            @Field("divisionID") String divisionID,
            @Field("districtID") String districtID,
            @Field("upazilaID") String thanaId,
            @Field("nid") String nid,
            @Field("mobile_no") String mobile_no,
            @Field("alt_mobile") String alt_mobile,
            @Field("email") String email,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("qcqa/update_qcqa.php?id=20&update_qc_qa=1")
    Call<DuePaymentResponse> updateQcQa(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("testID[]") List<String> testIDList,
            @Field("parameterValue[]") List<String> parameterValueList,
            @Field("model") String model,
            @Field("id") String id,
            @Field("status") String status,
            @Field("qcID") String qcID,
            @Field("testDate") String testDate,
            @Field("note") String note,
            @Field("ref_slID[]") List<String> ref_slID,
            @Field("details_slID[]") List<String> details_slIDList,
            @Field("storeID") String storeID,
            @Field("slID[]") List<String> slIDList
     );

    @FormUrlEncoded
    @POST("qcqa/qcqa-form-data.php?get_qcqa_data=1")
    Call<GetEditQcQaResponse> getQCQaPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("id") String slId
    );


    @Multipart
    @POST("miller/add_miller.php?update_profile_details=1")
    Call<DuePaymentResponse> updateMillerProfileInformation(
            @Query("token") String token,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("remarks") RequestBody remarks,
            @Part("capacity") RequestBody capacity,
            @Part("storeID") RequestBody storeID,
            @Part("vendorID") RequestBody vendorID,
            @Part("zoneID") RequestBody zoneID,
            @Part("ownerTypeID") RequestBody ownerTypeID,
            @Part("processTypeID") RequestBody processTypeID,
            @Part("DisplayName") RequestBody DisplayName,
            @Part("millTypeID[]") Set<String> millTypeID,
            @Part("millID") RequestBody millID,
            @Part("divisionID") RequestBody divisionID,
            @Part("districtID") RequestBody districtID,
            @Part("upazilaID") RequestBody upazilaID,
            @Part("sl_id") RequestBody sid,
            @Part("profileID_old") RequestBody profileID_oldFromGetPageData,
            @Part("profile_details_id") RequestBody profile_details_id,
            @Part("ref_sl") RequestBody ref_sl,
            @Part("is_submit") RequestBody isSubmit,
            @Part("associationID") RequestBody associationID,
            @Part MultipartBody.Part profile_photo
    );


    /**
     * get page info for update  Miller
     */
    @FormUrlEncoded
    @POST("miller/miller-edit-data.php?get_edit_data=1")
    Call<GetPreviousMillerInfoResponse> getMillerUpdatePageInfo(
            @Query("token") String token,
            @Field("sl") String SID
    );


    @FormUrlEncoded
    @POST("packeting/packeting.php?new-packeting=1")
    Call<DuePaymentResponse> addNewPackating(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("enterprise") String enterprise,
            @Field("user_id") String user_id,
            @Field("order_serial") String order_serial,
            @Field("note") String note,
            @Field("profile_type_id") String profile_type_id,
            @Field("prodIDs[]") List<String> productIdList,
            @Field("itemQtys[]") List<String> itemQtyList,
            @Field("subtotals[]") List<String> subtotalsList,
            @Field("storeID[]") List<String> storeIDList,
            @Field("order_date") String orderDate,
            @Field("customerID") String customerID
    );

    @FormUrlEncoded
    @POST("lastorderserial.php")
    Call<GetPackatingNo> getPackatingNo(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("order_type") String order_type
    );

    @FormUrlEncoded
    @POST("stock/api.php?prod-stock-infos-with-unit=1")
    Call<GetPackatingProductStockResponse> getProductStockByProductRefId(
            @Query("token") String token,
            @Field("productIDs[]") List<String> productIdList,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID
    );


    @FormUrlEncoded
    @POST("packeting/packeting.php?get-packet-products=1")
    Call<GetPacketingResponse> getPacketingPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );

    @FormUrlEncoded
    @POST("packaging/packaging.php?new-packaging=1")
    Call<DuePaymentResponse> confirmPackaging(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("note") String note,
            @Field("profile_type_id") String profile_type_id,
            @Field("prodIDs[]") List<String> productIdList,
            @Field("convertedIDs[]") List<String> convertedIDsList,
            @Field("sizes[]") List<String> sizesList,
            @Field("itemQtys[]") List<String> itemQtys,
            @Field("packet_product[]") List<String> packet_product,//all pkt product id
            @Field("subtotals[]") List<String> subtotals,//total weight list
            @Field("packagingNotes[]") List<String> packagingNotes,
            @Field("packagingID") String packagingID,
            @Field("entry_date") String entry_date,
            @Field("customerID") String customerID,
            @Field("sold_from_store") String sold_from// store id
    );

    @FormUrlEncoded
    @POST("packaging/packaging.php?next-packageid=1")
    Call<NextPackagingId> getNextPackagingId(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );


    @FormUrlEncoded
    @POST("stock/api.php?prod-stock-infos-with-unit=1")
    Call<PackagingStockByRequiredId> getPackagingStockByRequiredId(
            @Query("token") String token,
            @Field("productIDs[]") List<String> productIdList,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID
    );

    @FormUrlEncoded
    @POST("packaging/packaging.php?converted-items-infos=1")
    Call<PackageWeightAndDimensions> getPackageOthersDetailsByPacked(
            @Query("token") String token,
            @Field("productID") String productID,
            @Field("storeID") String storeID
    );


    @FormUrlEncoded
    @POST("packaging/packaging.php?converted-items-list=1")
    Call<PktNameListResponse> getPktNameListByItemNameId(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("productID") String productID
    );


    /**
     * For get origin packaging item response
     */
    @FormUrlEncoded
    @POST("packaging/packaging.php?get-packagible-products=1")
    Call<PackagingOriginItemsResponse> getPackagingOriginItems(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );


    @FormUrlEncoded
    @POST("production/create.php?iodine-stock=1")
    Call<AvailableKioResponse> getAvailableKio(
            @Query("token") String token,
            @Field("storeID") String enterpriseId
    );


    @FormUrlEncoded
    @POST("production/create.php?new-production=1")
    Call<DuePaymentResponse> addNewWashingAndCrushing(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,//search customer
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> storeIdList,//selected store
            @Field("quantity[]") List<String> quantityList,
            @Field("unit[]") List<String> unitList,
            @Field("selling_price[]") List<String> selling_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("total_discount[]") List<String> total_discountList,
            @Field("product_vat[]") List<String> product_vatList,
            @Field("payment_type") String payment_type,
            @Field("collected_amount") String collected_amount,
            @Field("due") String due,
            @Field("total_due_amount") String total_due_amount,
            @Field("discount") String discount,
            @Field("custom_discount") String custom_discount,
            @Field("order_date") String order_date,
            @Field("note") String note,
            @Field("storeID_val") String storeID_val,//output store
            @Field("stage") String stage,
            @Field("order_type") String order_type,
            @Field("carry_cost") String carry_cost,
            @Field("is_confirm") String is_confirm
    );


    @FormUrlEncoded
    @POST("products/products.php?outputable-items=1")
    Call<ProductionOutputResponse> getProductionOutputList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("production_type") String production_type
    );


    @Multipart
    @POST("customer/customer.php?add-customer=1")
    Call<DuePaymentResponse> addNewCustomer(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("storeID") RequestBody storeID,
            @Part("user_id") RequestBody user_id,
            @Part("company_name") RequestBody company_name,
            @Part("customer_fname") RequestBody customer_fname,
            @Part("phone") RequestBody phone,
            @Part("alt_phone") RequestBody alt_phone,
            @Part("email") RequestBody email,
            @Part("division") RequestBody division,
            @Part("district") RequestBody district,
            @Part("thana") RequestBody thanaRequestBody,
            @Part("bazar") RequestBody bazar,
            @Part("nid") RequestBody nid,
            @Part("tin") RequestBody tin,
            @Part("due_limit") RequestBody due_limit,
            @Part("country") RequestBody country,
            @Part("typeID") RequestBody typeID,
            @Part("address") RequestBody address,
            @Part("initial_amount") RequestBody initial_amount,
            @Part MultipartBody.Part image,
            @Part("note") RequestBody note
    );


    @Multipart
    @POST("customer/customer.php?customer-update=1")
    Call<DuePaymentResponse> editCustomer(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("storeID") RequestBody storeID,
            @Part("user_id") RequestBody user_id,
            @Part("company_name") RequestBody company_name,
            @Part("customer_fname") RequestBody customer_fname,
            @Part("phone") RequestBody phone,
            @Part("alt_phone") RequestBody alt_phone,
            @Part("email") RequestBody email,
            @Part("division") RequestBody division,
            @Part("district") RequestBody district,
            @Part("thana") RequestBody thanaRequestBody,
            @Part("bazar") RequestBody bazar,
            @Part("nid") RequestBody nid,
            @Part("tin") RequestBody tin,
            @Part("due_limit") RequestBody due_limit,
            @Part("country") RequestBody country,
            @Part("typeID") RequestBody typeID,
            @Part("address") RequestBody address,
            @Part("initial_amount") RequestBody initial_amount,
            @Part("edit_initial_amount") RequestBody edit_initial_amount,
            @Part("image") RequestBody image,
            @Part MultipartBody.Part new_image,
            @Part("note") RequestBody note,
            @Part("edit_note") RequestBody edit_note,
            @Part("customerID") RequestBody customerID
    );


    @FormUrlEncoded
    @POST("customer/customer.php?customer-update-due_limit=1")
    Call<DuePaymentResponse> updateDueLimit(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("due_limit") String due_limit
    );    @FormUrlEncoded
    @POST("sales/receipt.php?paymenat_instruction_edit=1")
    Call<DuePaymentResponse> updateDPaymentLimit(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("payment_id") String customerID,
            @Field("payment_limit") String due_limit,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST("customer/customer.php?customer-edit-data=1")
    Call<CustomerEditResponse> customerEditResponse(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("typeID") String typeID
    );


    @Multipart
    @POST("customer/customer.php?add-customer=1")
    Call<DuePaymentResponse> addNewForeignSupplier(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("storeID") RequestBody storeID,
            @Part("user_id") RequestBody user_id,
            @Part("company_name") RequestBody company_name,
            @Part("customer_fname") RequestBody customer_fname,
            @Part("phone") RequestBody phone,
            @Part("alt_phone") RequestBody alt_phone,
            @Part("email") RequestBody email,
            @Part("division") RequestBody division,
            @Part("district") RequestBody district,
            @Part("thana") RequestBody thanaRequestBody,
            @Part("bazar") RequestBody bazar,
            @Part("nid") RequestBody nid,
            @Part("tin") RequestBody tin,
            @Part("due_limit") RequestBody due_limit,
            @Part("country") RequestBody country,
            @Part("typeID") RequestBody typeID,
            @Part("address") RequestBody address,
            @Part("initial_amount") RequestBody initial_amount,
            @Part MultipartBody.Part image,
            @Part("note") RequestBody note


    );

    @FormUrlEncoded
    @POST("purchase/purchase.php?new_purchase=1")
    Call<DuePaymentResponse> addNewPurchase(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("unit[]") List<String> unitList,
            @Field("buying_price[]") List<String> buying_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("total_discount[]") List<String> total_discountList,
            @Field("product_vat[]") List<String> product_vat,
            @Field("payment_type") String payment_type,
            @Field("collected_amount") String collected_amount,
            @Field("due") String due,
            @Field("total_due_amount") String total_due_amount,
            @Field("discount") String discount,
            @Field("custom_discount") String custom_discount,
            @Field("order_date") String order_date,
            @Field("contact_person_name") String contact_person_name,
            @Field("contact_person_phone_no") String contact_person_phone_no,
            @Field("vehicle_no") String vehicle_no,
            @Field("transport_name") String transport_name,
            @Field("delivery_phone") String delivery_phone,
            @Field("delivery_address") String delivery_address,
            @Field("due_payment_date") String due_payment_date,
            @Field("carry_cost") String carry_cost,
            @Field("is_confirm") String is_confirm,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("purchase/purchase.php?search_vehicle=1")
    Call<VehicleSearchResponse> getVehicleData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("keyword") String keyword
    );


    @FormUrlEncoded
    @POST("sales/sale.php?new_sale=1")
    Call<DuePaymentResponse> addNewSale(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("customerID") String customerID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from[]") List<String> sold_fromList,
            @Field("quantity[]") List<String> quantityList,
            @Field("unit[]") List<String> unitList,
            @Field("selling_price[]") List<String> selling_priceList,
            @Field("product_title[]") List<String> product_titleList,
            @Field("total_discount[]") List<String> total_discountList,
            @Field("product_vat[]") List<String> product_vatList,
            @Field("payment_type") String payment_type,
            @Field("collected_amount") String collected_amount,
            @Field("due") String due,
            @Field("total_due_amount") String total_due_amount,
            @Field("discount") String discount,
            @Field("custom_discount") String custom_discount,
            @Field("order_date") String order_date,
            @Field("contact_person_name") String contact_person_name,
            @Field("contact_person_phone_no") String contact_person_phone_no,
            @Field("vehicle_no") String vehicle_no,
            @Field("transport_name") String transport_name,
            @Field("delivery_phone") String delivery_phone,
            @Field("delivery_address") String delivery_address,
            @Field("due_payment_date") String due_payment_date,
            @Field("carry_cost") String carry_cost,
            @Field("is_confirm") String is_confirm,
            @Field("vat") String vat
    );


    @FormUrlEncoded
    @POST("stock/api.php?prod-stock-infos=1")
    Call<ProductStockResponse> getProductStockDataByProductIds(
            @Query("token") String token,
            @Field("productIDs[]") List<String> productIDs,
            @Field("storeID") String selectedStoreId
    );


    @FormUrlEncoded
    @POST("products/products.php?search-items=1")
    Call<SealsRequisitionItemSearchResponse> getSearchProduct(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userID,
            @Field("categoryID[]") List<String> categoryID,
            @Field("key") String key
    );


    @FormUrlEncoded
    @POST("store/store.php?get-child-store=1")
    Call<StoreListByOptionalEnterpriseId> getStoreListByOptionalEnterpriseId(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("storeID") String storeID
    );


    @FormUrlEncoded
    @POST("customer/customer.php?search_transport=1")
    Call<SearchTransport> searchTransport(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("key") String key
    );


    @Multipart
    @POST("monitoring/monitoring-update.php?update_monitoring=1")
    Call<DuePaymentResponse> updateMonitoring(
            @Query("token") String token,
            @Part("id") RequestBody id,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("vendorID") RequestBody vendorID,
            @Part("monitoringDate") RequestBody monitoringDate,
            @Part("publishDate") RequestBody publishDate,
            @Part("zoneID") RequestBody zoneID,
            @Part("monitoringType") RequestBody monitoringType,
            @Part("millID") RequestBody millID,
            @Part("monitoringSummery") RequestBody monitoringSummery,
            @Part MultipartBody.Part document,
            @Part("otherMonitoringTypeName") RequestBody otherMonitoringTypeName
    );

    @FormUrlEncoded
    @POST("monitoring/monitoring-edit.php?get_edit_data=1")
    Call<UpdateMonitoringPageResponse> updateMonitoringPageData(
            @Query("token") String token,
            @Field("id") String id,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("customer/customer-list.php?list=1")
    Call<CustomerListResponse> getCustomerList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("division") String division,
            @Field("district") String district,
            @Field("search_keyword") String search_keyword


    );

    @FormUrlEncoded
    @POST("customer/customer-list.php?trash-list=1")
    Call<CustomerTrashListResponse> getCustomerTrashList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("division") String division,
            @Field("district") String district,
            @Field("search_keyword") String search_keyword


    );


    @FormUrlEncoded
    @POST("monitoring/monitoring-list.php?get_monitoring_list=1")
    Call<MonitoringModel> getMonitoringList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("monitoringDate") String monitoringDate,
            @Field("monitoringDate_to") String monitoringDate_to,
            @Field("publishDate") String publishDate,
            @Field("monitoringType") String monitoringType

    );

    @Multipart
    @POST("monitoring/store-monitoring.php?create_monitoring=1")
    Call<DuePaymentResponse> addNewMonitoring(
            @Query("token") String token,
            @Part("add_monitoring") RequestBody add_monitoring1,
            @Part("user_id") RequestBody user_id,
            @Part("vendorID") RequestBody vendorID,
            @Part("monitoringDate") RequestBody monitoringDate,
            @Part("publishDate") RequestBody publishDate,
            @Part("zoneID") RequestBody zoneID,
            @Part("millID") RequestBody millID,
            @Part("monitoringType") RequestBody monitoringType,
            @Part MultipartBody.Part document,
            @Part("monitoringSummery") RequestBody monitoringSummery,
            @Part("otherMonitoringTypeName") RequestBody otherMonitoringTypeName
    );

    @FormUrlEncoded
    @POST("monitoring/monitoring-form-data.php?get_monitoring_data=1")
    Call<AddMonitoringPageResponse> addMonitoringPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("qcqa/declined-qcqa-list.php?get_declined_list=1")
    Call<DeclineQcQaResponse> getDeclineQcQaList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("testDate_from") String testDate_from,
            @Field("testDate") String testDate,
            @Field("enterprise") String enterprise,
            @Field("model") String model

    );

    @FormUrlEncoded
    @POST("qcqa/pending-qcqa-list.php?get_pending_list=1")
    Call<PendingQcQaResponse> getPendingQcQaList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("testDate_from") String testDate_from,
            @Field("testDate") String testDate,
            @Field("enterprise") String enterprise,
            @Field("model") String model


    );

    @FormUrlEncoded
    @POST("qcqa/create_qcqa.php?create_qcqa=1")
    Call<DuePaymentResponse> addQc_qa(
            @Query("token") String token,
            @Field("storeID") String storeID,
            @Field("testDate") String testDate,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("testID[]") Set<String> testID,
            @Field("parameterValue[]") List<String> parameterValue,
            @Field("model") String model,
            @Field("profile_type_id") String profile_type_id,
            @Field("note") String note
    );


    @FormUrlEncoded
    @POST("qcqa/qcqa-form-data.php?get_qcqa_data=1")
    Call<AddQcQaPageResponse> getAddQcPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("id") String id,
            @Field("type") String type // 2 pending edit , 1 history edit
    );

    @FormUrlEncoded
    @POST("miller/add_miller.php?create_miller=1")
    Call<DuePaymentResponse> submitEmployeeInfo(
            @Query("token") String token,
            @Field("add_profile_others_info_2") String add_profile_others_info_2,
            @Field("profileID") String profile_id,
            @Field("status") String status,
            @Field("fullTimeMale") String fullTimeMale,
            @Field("fullTimeFemale") String fullTimeFemale,
            @Field("partTimeMale") String partTimeMale,
            @Field("partTimeFemail") String partTimeFemail,
            @Field("totalTechMale") String totalTechMale,
            @Field("totalTechFemale") String totalTechFemale,
            @Field("TOTFEM_EMP") String TOTFEM_EMP
    );


    @FormUrlEncoded
    @POST("miller/add_miller.php?create_miller=1")
    Call<DuePaymentResponse> submitQcInfo(
            @Query("token") String token,
            @Field("add_profile_others_info_1") String add_profile_others_info_1,
            @Field("profileID") String profileID,
            @Field("status") String status,
            @Field("haveLaboratory") String haveLaboratory,
            @Field("standardProcedure") String standardProcedure,
            @Field("trainedLaboratoryPerson") String trainedLaboratoryPerson,
            @Field("useTestKit") String useTestKit,
            @Field("laboratoryPerson") String laboratoryPerson,
            @Field("labRemarks") String labRemarks,
            @Field("user_id") String user_id
    );


    @Multipart
    @POST("miller/add_miller.php?create_miller=1")
    Call<DuePaymentResponse> submitLicenseInfo(
            @Query("token") String token,
            @Part("add_certificate_info") RequestBody add_certificate_info,
            @Part("profileID") RequestBody profileID,
            @Part("certificateTypeID[]") List<String> certificateTypeID,
            @Part("issuerName[]") List<String> issuerName,
            @Part("issueDate[]") RequestBody issueDate,
            @Part("certificateDate[]") RequestBody certificateDate,
            @Part MultipartBody.Part certificateImage,
            @Part("renewDate[]") RequestBody renewDateList,
            @Part("remarks[]") List<String> remarksList,
            @Part("user_id[]") RequestBody user_id
    );

    @FormUrlEncoded
    @POST("miller/add_miller.php?create_miller=1")
    Call<DuePaymentResponse> submitMillerCompanyOwnerInfo(
            @Query("token") String token,
            @Field("add_owner_details") String add_owner_details,
            @Field("profileID") String profileID,
            @Field("ownerName[]") List<String> ownerNameList,
            @Field("divisionID[]") List<String> divisionIDList,
            @Field("districtID[]") List<String> districtID,
            @Field("upazilaID[]") List<String> upazilaIDList,
            @Field("nid[]") List<String> nidList,
            @Field("mobile_no[]") List<String> mobile_noList,
            @Field("alt_mobile[]") List<String> alt_mobileList,
            @Field("email[]") List<String> emailList,
            @Field("user_id") String user_id
    );


    @Multipart
    @POST("miller/add_miller.php?create_miller=1")
    Call<MillerProfileInfoSaveResponse> saveMillerProfileInfo(
            @Query("token") String token,
            @Part("add_profile_details") RequestBody add_profile_details,
            @Part("zoneID") RequestBody zoneID,
            @Part("processTypeID") RequestBody processTypeID,
            @Part("DisplayName") RequestBody DisplayName,
            @Part("millTypeID[]") Set<String> millTypeIDList,
            @Part("capacity") RequestBody capacity,
            @Part("millID") RequestBody millID,
            @Part("remarks") RequestBody remarks,
            @Part("countryID") RequestBody countryID,
            @Part("ownerTypeID") RequestBody ownerTypeID,
            @Part("divisionID") RequestBody divisionID,
            @Part("districtID") RequestBody districtID,
            @Part("upazilaID") RequestBody upazilaID,
            @Part MultipartBody.Part profile_photo,
            @Part("agree_term_condition") RequestBody agree_term_condition,
            @Part("declared") RequestBody declared,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("storeID") RequestBody storeID,
            @Part("vendorID") RequestBody vendorID
    );


    @FormUrlEncoded
    @POST("miller/miller-add-data.php?get_upazila=1")
    Call<ThanaListResponse> getThanaListByDistrictId(
            @Query("token") String token,
            @Field("district_id") String district_id
    );

    @FormUrlEncoded
    @POST("miller/miller-add-data.php?get_district=1")
    Call<MillerDistrictResponse> getDistrictListByDivisionId(
            @Query("token") String token,
            @Field("division_id") String division_id
    );


    @FormUrlEncoded
    @POST("miller/miller-add-data.php?get_add_data=1")
    Call<MillerProfileInfoResponse> getMillerProfileInfo(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );


    /**
     * For get DashBoard Expense List
     */
    @FormUrlEncoded
    @POST("expense/expense.php?list=1")
    Call<DashBoardExpenseResponse> dashBoardExpenseList(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );

    /**
     * For Decline ExpenseDuePaymentApprovalDetails
     */
    @FormUrlEncoded
    @POST("payment/approve-payment.php?decline=1")
    Call<DuePaymentResponse> declineDuePaymentApprovalDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("batch") String batch,
            @Field("user_id") String user_id
    );


    /**
     * For Approve ExpenseDuePaymentApprovalDetails
     */
    @FormUrlEncoded
    @POST("payment/approve-payment.php?approve=1")
    Call<DuePaymentResponse> approveDuePaymentApprovalDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("batch") String batch,
            @Field("user_id") String user_id,
            @Field("type") String type
    );


    /**
     * For get ExpenseDuePaymentApprovalDetails
     */
    @FormUrlEncoded
    @POST("payment/approve-payment.php?details=1")
    Call<PendingExpensePaymentDueResponse> getExpenseDuePaymentApprovalDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("batch") String batch,
            @Field("customer") String customer,
            @Field("type") String type
    );


    /**
     * For get cash book from home page
     */
    @FormUrlEncoded
    @POST("accounts/cashbook.php?cashbook=1")
    Call<CashBookResponse> getCashBookForHomePage(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("store") String store,
            @Field("payment") String payment,
            @Field("receipt") String receipt,
            @Field("transactionTypeId") String transactionTypeId
    );

    /**
     * For get day book for homeFragment
     */
    @FormUrlEncoded
    @POST("accounts/cashbook.php?day-book=1")
    Call<DayBookResponse> getDayBookForHomePage(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("store") String store,
            @Field("payment") String payment,
            @Field("receipt") String receipt,
            @Field("transactionTypeId") String transactionTypeId
    );

    @FormUrlEncoded
    @POST("accounts/transection.php?token=&debitor_creditor=1")
    Call<DebitorsAndCreditorsResponse> gteDebitorsData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String usr,
            @Field("payment_type") String payment_type,
            @Field("type") String type
    );

    /**
     * For DashBoardDetails
     */
    @FormUrlEncoded
    @POST("dashboard.php?info=1")
    Call<DashBoardResponse> getDashBoardResponse(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?dashboard-data=1")
    Call<DashboardDataResponse> dashBoardData(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID
    );


    @FormUrlEncoded
    @POST("dashboard/dashboard.php?top_ten_supplier_by_purchase=1")
    Call<TopTenSupplierAndCustomerResponse> getTopTenCustomerList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("type") String type,
            @Field("start") String start,
            @Field("end") String end,
            @Field("limit") String limit
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?current_available_balance=1")
    Call<DashboardBalanceResponse> getBankBalanceList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?todays_expense=1")
    Call<ExpenseResponse> getExpenseList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end,
            @Field("storeID") String storeID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?todays_production_percentage=1")
    Call<ProductionResponse> getProductionList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end,
            @Field("storeID") String storeID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?todaysSaleOrPurchase=1")
    Call<SalePurchaseJustResponse> getJustSalePurchaseList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("type") String type,
            @Field("start") String start,
            @Field("end") String end,
            @Field("storeID") String enterprise
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?dashboard_production=1")
    Call<ProductionResponseJust> getProductionListJust(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("type") String type,
            @Field("start") String start,
            @Field("end") String end,
            @Field("storeID") String storeID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?qc_qa_by_dates=1")
    Call<QcQaResponseTodays> getQcQaList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end,
            @Field("storeID") String storeID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?pie-chart=1")
    Call<PieChartDataresponse> pieChartData(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?todays_purchase_or_sale=1")
    Call<TodaysSaleOrPurchaseResponse> getSaleOrPurchaseList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("type") String type,
            @Field("start") String start,
            @Field("end") String end,
            @Field("storeID") String enterpriseId
    );

    @FormUrlEncoded
    @POST("store/store.php?get_iodine_store=1")
    Call<SetIodineResponse> getIOdineStoreSet(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID
    );

    @FormUrlEncoded
    @POST("store/store.php?set_iodine_store=1")
    Call<DuePaymentResponse> setStoreForIodine(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("set_store") String set_store
    );

    /**
     * For get Decline Pending sales Requisition details
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?decline-requisition=1")
    Call<DuePaymentResponse> declinePendingSalesRequisitionDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * For approve pending sales requisition details
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?approve-requisition=1")
    Call<DuePaymentResponse> approvePendingSalesRequisitionDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * For get pending sales requisition details
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?details=1")
    Call<PendingSalesRequisitionPendingResponse> getPendingSalesRequisitionDetails(
            @Query("token") String token,
            @Query("id") String id,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id
    );


    /**
     * for decline PendingSalesReturnDetails
     */
    @FormUrlEncoded
    @POST("sales/return.php?decline=1")
    Call<DuePaymentResponse> declinePendingSalesReturnDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * for approve PendingSalesReturnDetails
     */
    @FormUrlEncoded
    @POST("sales/return.php?approve=1")
    Call<DuePaymentResponse> approvePendingSalesReturnDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("note") String note
    );


    /**
     * For get PendingSalesReturnDetails
     */
    @FormUrlEncoded
    @POST("sales/return.php?details=1")
    Call<PendingSalesReturnDetailsResponse> getPendingSalesReturnDetailsResponse(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("id") String id
    );

    /**
     * For Decline ReconciliationDetails
     */
    @FormUrlEncoded
    @POST("stock/reconciliation.php?decline=1")
    Call<DuePaymentResponse> DeclineReconciliationDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("note") String note);

    /**
     * For ApproveReconcilationDetails
     */
    @FormUrlEncoded
    @POST("stock/reconciliation.php?approve=1")
    Call<DuePaymentResponse> approveReconciliationDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("note") String note

    );


    /**
     * For get Reconciliation details
     */
    @FormUrlEncoded
    @POST("stock/reconciliation.php?reconcile=1")
    Call<ReconciliationDetailsResponse> getReconciliationDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("id") String id
    );


    /**
     * For Declined pending payment
     */
    @FormUrlEncoded
    @POST("payment/edit.php?decline=1")
    Call<DuePaymentResponse> declineEditedPendingPayment(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String userId,
            @Field("note") String note
    );

    /**
     * For Approve edited payment details
     */
    @FormUrlEncoded
    @POST("payment/edit.php?approve=1")
    Call<DuePaymentResponse> approveEditedPayment(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * get Edited Payment Due Details
     */
    @FormUrlEncoded
    @POST("payment/edit.php?details=1")
    Call<EditedPaymentDueResponse> getEditedPaymentDueDetailsResponseCall(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id
    );

    /**
     * For get debitors from home bottom navigation item
     */
    @FormUrlEncoded
    @POST("debitors/debitors.php?debitors=1")
    Call<CreditorsResponse> getDebitors(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );

    /**
     * For get creditors from home bottom navigation item
     */
    @FormUrlEncoded
    @POST("creditors/creditors.php?creditors=1")
    Call<CreditorsResponse> getCreditors(
            @Query("token") String token,
            @Field("vendorID") String vendorID
    );


    /**
     * For Declined iodization pending
     */
    @FormUrlEncoded
    @POST("production/iodide-edit.php?decline=1")
    Call<DuePaymentResponse> declineIodizationPending(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("note") String note,
            @Field("user_id") String user_id
    );


    /**
     * approve iodization pending details
     */
    @FormUrlEncoded
    @POST("production/iodide-edit.php?approve=1")
    Call<DuePaymentResponse> approveIodizationPending(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("note") String note,
            @Field("user_id") String user_id
    );


    /**
     * For decline transfer
     */
    @FormUrlEncoded
    @POST("stock/transfer.php?decline=1")
    Call<DuePaymentResponse> DeclinePendingTransfer(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("note") String note
    );


    /**
     * for approve pending transfer
     */
    @FormUrlEncoded
    @POST("stock/transfer.php?approve=1")
    Call<DuePaymentResponse> approvePendingTransfer(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String user_id,
//            @Field("paymentID") String paymentID,
            @Field("note") String note,
            @Field("storeID") String storeID,
            @Field("profile_type_id") String profile_type_id
    );


    /**
     * for approve pending expense
     */
    @FormUrlEncoded
    @POST("expense/expense.php?approve=1")
    Call<DuePaymentResponse> approvePendingExpense(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String userId,
            @Field("paymentID") String paymentID,
            @Field("note") String note
    );

    /**
     * for decline pending expense
     */
    @FormUrlEncoded
    @POST("expense/expense.php?decline=1")
    Call<DuePaymentResponse> declinePendingExpense(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id,
            @Field("user_id") String userId,
            @Field("paymentID") String paymentID,
            @Field("note") String note
    );


    /**
     * for get pending expense list from notification
     */
    @FormUrlEncoded
    @POST("expense/expense.php?expense=1")
    Call<PendingExpenseDetails> getPendingExpenseDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id
    );

    /**
     * For get pending transfer details
     */
    @FormUrlEncoded
    @POST("stock/transfer.php?transfer=1")
    Call<PendingTransferDetailsResponse> getPendingTransferDetails(
            @Query("token") String token,
            @Field("id") String id,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );

    /**
     * decline pending Quotation
     */
    @FormUrlEncoded
    @POST("quotation/quotation.php?decline=1")
    Call<DuePaymentResponse> declinePendingQuotation(
            @Query("token") String token,
            @Field("vendorID") String vendorId,
            @Field("id") String id,
            @Field("note") String note,
            @Field("user_id") String userId
    );

    /**
     * approve  pending Quotation
     */
    @FormUrlEncoded
    @POST("quotation/quotation.php?approve=1")
    Call<DuePaymentResponse> approvePendingQuotation(
            @Query("token") String token,
            @Field("vendorID") String vendorId,
            @Field("id") String id,
            @Field("user_id") String userId
    );


    /**
     * for Pending Quotation Details
     */
    @FormUrlEncoded
    @POST("quotation/quotation.php?quot=1")
    Call<PendingQuotationResponse> getPendingQuotationDetails(
            @Query("token") String token,
            @Field("id") String orderId,
            @Field("vendorID") String vendorID
    );


    /**
     * get iodization edited order
     */
    @FormUrlEncoded
    @POST("production/iodide-edit.php?edit-data=1")
    Call<EditedIodizationDetailsResponse> getIodizationEditedOrders(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID
    );

    /**
     * For get Pending Iodization details
     */
    @FormUrlEncoded
    @POST("production/iodization.php?get-data=1")
    Call<PendingIodizationDetailsResponse> getPendingIodizationDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("orderID") String orderID
    );

    /**
     * For decline Edited washing crushing
     */

    @FormUrlEncoded
    @POST("production/edit.php?decline=1")
    Call<DuePaymentResponse> declineEditedWashingCrushing(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );


    /**
     * for approve Edited washing crushing
     */
    @FormUrlEncoded
    @POST("production/edit.php?approve=1")
    Call<DuePaymentResponse> approveEditedWashingCrushing(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * now get edited washing crushing details
     */
    @FormUrlEncoded
    @POST("production/edit.php?edit-data=1")
    Call<EditedWashingCrushingDetailsResponse> getEditedWashingAndCrushingDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID
    );


    /**
     * approve washing crushing details
     */
    @FormUrlEncoded
    @POST("production/washing-crushing.php?approve=1")
    Call<DuePaymentResponse> approveWashingAndCrushing(
            @Query("token") String token,
            @Field("vendorID") String vendorId,
            @Field("orderID") String orderID,
            @Field("user_id") String userId,
            @Field("note") String note
    );

    /**
     * For get PendingWashingDetails for notification
     */
    @FormUrlEncoded
    @POST("production/washing-crushing.php?get-data=1")
    Call<GetPendingWashingCrushingDetailsResponse> getPendingWashingDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String userId
    );


    /**
     * For decline edit sale
     */
    @FormUrlEncoded
    @POST("sales/edit.php?decline=1")
    Call<DuePaymentResponse> declineEditSale(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * For approve sales edit
     */
    @FormUrlEncoded
    @POST("sales/edit.php?approve-edit=1")
    Call<DuePaymentResponse> approveEditSales(
            @Query("token") String token,
            @Field("vendorID") String vendorId,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note,
            @Field("previous_quantity[]") List<String> previousQuantityList
    );


    /**
     * get sale edit details for notification
     */
    @FormUrlEncoded
    @POST("sales/edit.php?edit-data=1")
    Call<EditedPurchaseOrderResponse> getSalesEditDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID
    );


    /**
     * For Decline purchase edit
     */
    @FormUrlEncoded
    @POST("purchase/edit.php?decline=1")
    Call<DuePaymentResponse> declinePurchaseEdit(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String noteVal
    );


    /**
     * For Approve purchase edit
     */
    @FormUrlEncoded
    @POST("purchase/edit.php?approve-edit=1")
    Call<DuePaymentResponse> approvePurchaseEdit(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note,
            @Field("previous_quantity[]") List<String> previous_quantityList
    );


    /**
     * For Purchase edit
     */
    @FormUrlEncoded
    @POST("purchase/edit.php?edit-data=1")
    Call<EditedPurchaseOrderResponse> getEditedPurchaseOrderResponse(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID
    );

    /**
     * for pending sale notification approve
     */
    @FormUrlEncoded
    @POST("sales/approval.php?approve=1")
    Call<DuePaymentResponse> pendingSaleApproveRequest(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * for pending sale notification decline
     */
    @FormUrlEncoded
    @POST("sales/approval.php?decline=1")
    Call<DuePaymentResponse> pendingSaleDeclineRequest(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );


    @Multipart
    @POST("brand/api.php?brand_update=1")
    Call<DuePaymentResponse> editBrand(
            @Query("token") String token,
            @Part("brand_name") RequestBody brand_name,
            @Part("vendorID") RequestBody vendorID,
            @Part("brandID") RequestBody brandID,
            @Part("storeID") RequestBody storeID,
            @Part MultipartBody.Part distributor
    );

    @FormUrlEncoded
    @POST("brand/api.php?brand_delete=1")
    Call<DuePaymentResponse> deleteBrand(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("brandID") String brandID,
            @Field("storeID") String storeID
    );
    
    
    /**
     * for pending sales notification details
     * here PendingPurchaseNotificationDetailsResponse and saleResponse are same for that i use the response class;
     */
    @FormUrlEncoded
    @POST("sales/approval.php?pending-sales=1")
    Call<PendingPurchaseNotificationDetailsResponse> getPendingSalesNotificationDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("orderID") String orderID
    );

    @FormUrlEncoded
    @POST("sales/cancel-order.php?details=1")
    Call<PendingPurchaseNotificationDetailsResponse> getPendingSalesReturnNotificationDetailsNew(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String orderID
    );

    /**
     * for pending purchase notification approve
     */
    @FormUrlEncoded
    @POST("purchase/approve.php?approve=1")
    Call<DuePaymentResponse> pendingPurchaseApproveRequest(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * for pending purchase notification decline
     */
    @FormUrlEncoded
    @POST("purchase/approve.php?decline=1")
    Call<DuePaymentResponse> pendingPurchaseDeclineRequest(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );


    /**
     * For get Pending Purchase notification details from  notification list
     */
    @FormUrlEncoded
    @POST("purchase/approve.php?pending-purchase=1")
    Call<PendingPurchaseNotificationDetailsResponse> getPendingPurchaseNotificationDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("orderID") String orderID
    );

    /**
     * For send Requisition Decline  Request
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?decline-requisition=1")
    Call<DuePaymentResponse> sendRequisitionDeclineRequest(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );

    /**
     * For send Requisition Approved Request
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?approve-requisition=1")
    Call<DuePaymentResponse> sendRequisitionApprovedRequest(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orderID") String orderID,
            @Field("user_id") String user_id,
            @Field("note") String note
    );


    /**
     * for get pending requisition list page info like (startDate,endDate,company,enterprise)
     */
    @FormUrlEncoded
    @POST("misc.php?store-customer=1")
    Call<PendingRequisitionPageInfoResponse> getPendingRequisitionPageInfo(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("store_access") String store_access
    );


    /**
     * For get sales requisition list
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?details=1")
    Call<RequisitionDetailsResponse> getSingleRequisitionDetails(
            @Query("id") String selectedRequisitionId,
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );

    /**
     * For get all requisition list for the current logged user
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?list=1")
    Call<RequisitionListResponse> getRequisitionList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("store") String store,//for will implement
            @Field("start") String start,//for will implement
            @Field("end") String end,//for will implement
            @Field("company") String company//for will implement
    );

    //for pending requisition list response
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?pending-list=1")
    Call<PendingRequisitionResponse> getPendingRequisitionList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("store") String store,//for will implement
            @Field("start") String start,//for will implement
            @Field("end") String end,//for will implement
            @Field("company") String company//for will implement
    );

    //for declined requisition list response
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?declined-list=1")
    Call<DeclinedRequisitionResponse> getDeclinedRequisitionList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("store") String store,//for will implement
            @Field("start") String start,//for will implement
            @Field("end") String end,//for will implement
            @Field("company") String company//for will implement
    );

    /**
     * for add new sales requisition
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?requisition=1")
    Call<DuePaymentResponse> addRequisition(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("productID[]") List<String> productIDList,
            @Field("sold_from") String enterPriceId,
            @Field("unit[]") List<String> unitList,
            @Field("orderID") String lastOrderId,
            @Field("user_id") String currentUserId,
            @Field("customerID") String findingCustomerId,
            @Field("product_title[]") List<String> productTitleList,
            @Field("selling_price[]") List<String> sellingPriceList,
            @Field("quantity[]") List<String> quantityList,
            @Field("total_discount") String total_discount,
            @Field("custom_discount") String custom_discount,//it will be 0 for short time
            @Field("collected_amount") String collected_amount,
            @Field("is_confirm") String is_confirm,//it is 0
            @Field("payment_type") String payment_typeVal,
            @Field("order_date") String order_date,
            @Field("end_order_date") String end_order_date
    );


    /**
     * for get last order
     */
    @FormUrlEncoded
    @POST("lastorderserial.php")
    Call<LastOrderResponse> getLastOrderId(
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("order_type") String orderType
    );


    /**
     * for get sales requisition interPrise list
     */
    @FormUrlEncoded
    @POST("store/store.php?get-enterprise=1")
    Call<GetEnterpriseResponse> getEnterprise(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID
    );

    /**
     * for get user enterprise  list
     */
    @FormUrlEncoded
    @POST("store/store.php?getUserEnterprise=1")
    Call<GetEnterpriseResponse> forGetUserEnterprise(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID
    );


    /**
     * for search requisition item by Name
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?search-items=1")
    Call<SealsRequisitionItemSearchResponse> searchSalesRequisitionItemByName(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("categoryID[]") List<String> categoryID,
            @Field("key") String key);


    /**
     * for get requisition item list
     */
    @FormUrlEncoded
    @POST("sales-requisition/requisition.php?requisition-items=1")
    Call<SalesRequisitionItemListResponse> getSalesRequisitionItemsList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );


    /**
     * for pay expense due
     */
    @FormUrlEncoded
    @POST("expense/exp-due-payment.php?make_payment=1")
    Call<DuePaymentResponse> payExpenseDue(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("customerID") String customerID,
            @Field("permissions") String permissions,
            @Field("profile_type_id") String profile_type_id,
            @Field("orders[]") List<String> orders,
            @Field("collected_paid_amount") String collected_paid_amount,
            @Field("total_due_amount") String total_due_amount,
            @Field("custom_discount") String custom_discount,
            @Field("user_id") String user_id,
            @Field("payment_type_name") String payment_type_name,
            @Field("payment_sub_type") String payment_sub_type,
            @Field("payment_date") String payment_date,
            @Field("payment_remarks") String payment_remarks,
            @Field("user_bankID") String user_bankID,
            @Field("branch") String branch,
            @Field("user_ac_no") String user_ac_no,
            @Field("bankID") String bankID// select account no
    );


    /**
     * for get get expense due orders by selected customer id
     */
    @FormUrlEncoded
    @POST("expense/exp-due-payment.php?exp-payment=1")
    Call<ExpenseDueResponse> getExpenseOrders(
            @Query("token") String token,
            @Field("vendorID") String vendorId,
            @Field("user_id") String userId,
            @Field("customerID") String selectedCustomerId
    );


    /**
     * for search expense vendor
     */
    @FormUrlEncoded
    @POST("exp-vendor/exp-vendor.php?exp-vendor=1")
    Call<CustomerSearchResponse> searchExpenseVendor(
            @Query("token") String token,
            @Field("key") String key,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId
    );


    /**
     * set supplier pay data to server
     */
    @FormUrlEncoded
    @POST("purchase/due-payment.php?due_payment=1")
    Call<DuePaymentResponse> supplierPaymentSend(
            @Query("token") String token,
            @Field("orders[]") Set<String> orders,
            @Field("collected_paid_amount") String collected_paid_amount,
            @Field("total_amount_count") String total_amount_count,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("permissions") String permissions,
            @Field("profile_type_id") String profile_type_id,
            @Field("customerID") String customerID,
            @Field("vendorID") String vendorID,
            @Field("payment_type_name") String payment_type_name,
            @Field("payment_sub_type") String payment_sub_type,
            @Field("custom_discount") String custom_discount,
            @Field("user_bankID") String user_bankID,
            @Field("branch") String branch,
            @Field("user_ac_no") String user_ac_no,
            @Field("payment_remarks") String payment_remarks,
            @Field("payment_date") String payment_date,
            @Field("mainBankID") String bankID,
            @Field("bankID") String account,
            @Field("total_due_amount") String totalDue
    );


    /**
     * get due orders by selected suppliers id and vendor id
     */
    @FormUrlEncoded
    @POST("purchase/due-payment.php?payment=1")
    Call<SupplierOrdersResponse> getSupplierOrders(
            @Query("token") String token,
            @Field("supplierID") String supplierID,
            @Field("vendorID") String vendorID
    );

    /**
     * For search suppliers
     * here CustomerSearchResponse and supplier response are same so i use the same response model here
     */
    @FormUrlEncoded
    @POST("supplier/supplier.php?search_supplier=1")
    Call<CustomerSearchResponse> searchSuppliersByKey(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("key") String key
    );


    @FormUrlEncoded
    @POST("sales/receipt.php?due_receipt=1")
    Call<DuePaymentResponse> payDuyAmount(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orders[]") Set<String> orders,
            @Field("collected_paid_amount") String collected_paid_amount,
            @Field("total_amount_count") String total_amount_count,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("permissions") String permissions,
            @Field("profile_type_id") String profile_type_id,
            @Field("payment_type_name") String payment_type_name,
            @Field("payment_sub_type") String payment_sub_type,
            @Field("bankID") String bankID,
            @Field("payment_date") String payment_date,
            @Field("payment_remarks") String remarks,
            @Field("customerID") String customerID,
            @Field("total_due_amount") String totalDueAmmoount
    );

    @FormUrlEncoded
    @POST("customer/credit-voucher.php?make_payment=1")
    Call<DuePaymentResponse> creditVoucherResponse(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("orders[]") Set<String> orders,
            @Field("collected_paid_amount") String collected_paid_amount,
            @Field("total_amount_count") String total_amount_count,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("permissions") String permissions,
            @Field("profile_type_id") String profile_type_id,
            @Field("payment_type_name") String payment_type_name,
            @Field("payment_sub_type") String payment_sub_type,
            @Field("bankID") String bankID,
            @Field("payment_date") String payment_date,
            @Field("payment_remarks") String remarks,
            @Field("customerID") String customerID,
            @Field("sales_type") String sales_type,
             @Field("mainBankID") String mainbankID
    );


    /**
     * for detect user token  by local database token
     *
     * @param token
     * @param vendorID
     * @return
     */
    @FormUrlEncoded
    @POST("brand/api.php?brand=1")
    Call<CheckUserResponse> checkUserToken(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID

    );

    @FormUrlEncoded
    @POST("bank/bank.php?get_bank_ac=1")
    Call<AccountNumberListResponse> getAccountListByBankId(
            @Field("vendorID") String vendorID,
            @Field("id") String bankNameId,
            @Field("storeID") String storeID
    );


    @FormUrlEncoded
    @POST("auth/auth.php")
    Call<LoginResponse> primaryLogin(
            @Field("username") String userName,
            @Field("password") String password,
            @Field("token") String deviceToken
    );

    /**
     * for search customer for get her due orders
     */
    @FormUrlEncoded
    @POST("customer/customer.php?search_customer=1")
    Call<CustomerSearchResponse> getCustomersByKey(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("key") String key
    );

    @FormUrlEncoded
    @POST("customer/customer-list.php?all=1")
    Call<CustomerAndSupplierResponse> getCustomersAndSupplierByKey(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("keyword") String key
    );


    /**
     * for search supplier for get her due orders
     */
    @FormUrlEncoded
    @POST("supplier/supplier.php?search_supplier=1")
    Call<CustomerSearchResponse> getSuppliersByKey(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("key") String key
    );

    /**
     * for search supplier for get her due orders
     */
    @FormUrlEncoded
    @POST("customer/customer.php?search_referer=1")
    Call<CustomerSearchResponse> getRefPersonByKey(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("key") String key
    );


    @FormUrlEncoded
    @POST("purchase/payment-instruction.php?payment_instruction=1")
    Call<PaymentInstruction> getPaymentInstructionListByVendorIdAndDate(
            @Query("token") String token,
            @Field("user_id") String userId,
            @Field("vendorID") String vendorID,
            @Field("date") String date
    );


    @FormUrlEncoded
    @POST("purchase/payment-instruction.php?add_limit=1")
    Call<AddNewPaymentLimitResponse> submitNewPaymentLimit(
            @Query("token") String token,
            @Field("pay_limit_amount[]") List<String> pay_limit_amount,
            @Field("customerID[]") List<String> customerID,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("note") String note,
            @Field("payment_date") String pyamentDate
    );

    @FormUrlEncoded
    @POST("purchase/payment-instruction.php?list=1")
    Call<PaymentInstruction> getPaymentInstructionList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("supplier") String supplier
    );

    @FormUrlEncoded
    @POST("notification/notification.php?notifications=1")
    Call<NotificationResponse> getNotificationList(
            @Query("token") String token,
            @Query("page") String page,
            @Query("notifications") String notifications,
            @Field("vendorID") String vendorID,
            @Field("user_id") String userId,
            @Field("status") String status,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("sales/receipt.php?receipt=1")
    Call<DueOrdersResponse> dueOrdersByCustomerId(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("customerID") String customerId
    );

    /***
     * Configuration Api
     * */

    @FormUrlEncoded
    @POST("miller/declined-miller-list.php?get_declined_miller=1")
    Call<MillerDeclineResponse> getMillerDecline(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("processTypeID") String processTypeID,
            @Field("millTypeID") String millTypeID,
            @Field("division") String division,
            @Field("district") String district,
            @Field("upazilaID") String upazilaID

    );


    @FormUrlEncoded
    @POST("miller/miller-list.php?get_miller_history=1")
    Call<MillerHistoryResponse> getMillerHistory(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("processTypeID") String processTypeID,
            @Field("millTypeID") String millTypeID,
            @Field("division") String division,
            @Field("district") String district,
            @Field("upazilaID") String upazilaID

    );

    @FormUrlEncoded
    @POST("miller/pending-miller-list.php?get_pending_miller=1")
    Call<MillerPendingResponse> getPendingMillerList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("processTypeID") String processTypeID,
            @Field("millTypeID") String millTypeID,
            @Field("division") String division,
            @Field("district") String district,
            @Field("upazilaID") String upazilaID

    );

    @FormUrlEncoded
    @POST("products/product-trash.php?trashed_product=1")
    Call<TrashResponse> getTrashList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorId,
            @Field("user_id") String userId,
            @Field("storeID") String storeID

    );

    @FormUrlEncoded
    @POST("products/products.php?assign_item_packet=1")
    Call<AssignPacketItemResponse> getItemPacketTag(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String userId,
            @Field("product_title") String product_title,
            @Field("brand") String brand,
            @Field("category") String category

    );

    @FormUrlEncoded
    @POST("products/products.php?packet_item_process=1")
    Call<PacketDropDownResponse> getDropDownPacketItem(
            @Query("token") String token,
            @Field("vendorID") String vendorId,
            @Field("user_id") String userId,
            @Field("productID") String productID

    );

    @FormUrlEncoded
    @POST("brand/api.php?brand=1")
    Call<BrandModel> getBrandList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId
    );

    @FormUrlEncoded
    @POST("category/categories.php?category=1")
    Call<CategoryListResponse> getItemCategoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("category") String category
    );

    @FormUrlEncoded
    @POST("products/product-list.php?get_product=1")
    Call<ItemListResponse> getItemList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorId,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("brand") String brand,
            @Field("product_title") String product_title,
            @Field("category") String category);

    @FormUrlEncoded
    @POST("products/initial-product-list.php?get_initial_product=1")
    Call<InitialItemListResponse> initialItemList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("profile_type_id") String profile_type_id,
            @Field("vendorID") String vendorId,
            @Field("storeID") String storeID,
            @Field("user_id") String user_id,
            @Field("brand") String brand,
            @Field("product_title") String product_title,
            @Field("category") String category);

    @FormUrlEncoded
    @POST("sales/sale.php?pending-list=1")
    Call<SalePendingResponse> getSalePendingList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store


    );

    @FormUrlEncoded
    @POST("sales/sale.php?declined-list=1")
    Call<SaleDeclinedResponse> getSalePendingDeclinedList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("sales/return.php?list=1")
    Call<SalesHistoryResponse> getSaleReturnHistoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("sales/return.php?pending-list=1")
    Call<SalePendingReturnResponse> getSalePendingReturnList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("purchase/purchase.php?list=1")
    Call<PurchaseHistoryResponse> getPurchaseHistoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );


    @FormUrlEncoded
    @POST("purchase/purchase.php?pending-list=1")
    Call<PurchasePendingResponse> getPendingPurchaseList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("sales/receipt.php?list=1")
    Call<AccountReceiptListResponse> getReceiptPaymentHistoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("bank/bank.php?acc_list=1")
    Call<BankAccountListResponse> getBankAccountList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId
    );

    @FormUrlEncoded
    @POST("dashboard/dashboard.php?balance_details=1")
    Call<BankAccountDetailsResponse> getBankAccountDetails(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("id") String bannkId
    );

    @FormUrlEncoded
    @POST("expense/exp-due-payment.php?list=1")
    Call<AccountReceiptListResponse> vendorPayment(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );
    @FormUrlEncoded
    @POST("expense/exp-due-payment.php?pending_list=1")
    Call<AccountReceiptListResponse> vendorPending(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );
    @FormUrlEncoded
    @POST("expense/exp-due-payment.php?declined_list=1")
    Call<AccountReceiptListResponse> vendorDedclined(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("customer/credit-voucher.php?voucher_list=1")
    Call<CreditAndDebitVoucherResponse> getDebitAndCreditVoucherList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type,
            @Field("voucher_type") String voucher_type
    );

    @FormUrlEncoded
    @POST("customer/credit-voucher.php?voucher_edit=1")
    Call<DuePaymentResponse> updateVoucher(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("id") String paymentId,
            @Field("sales_type") String voucherType,
            @Field("paid_amount") String paid_amount,
            @Field("custom_discount") String custom_discount,
            @Field("grand_total") String grand_total,
            @Field("orderID") String orderID,
            @Field("payment_type") String payment_type

    );

    @FormUrlEncoded
    @POST("sales/receipt.php?payment-details-list=1")
    Call<ReceiptDetailsResponse> getAccountDetailsList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("paymentSummaryID") String batch,
            @Field("sales_type") String type
    );


    @FormUrlEncoded
    @POST("purchase/due-payment.php?list=1")
    Call<AccountReceiptListResponse> gePaymentHistoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("purchase/due-payment.php?pending_list=1")
    Call<AccountReceiptListResponse> gePaymentPending(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );
    @FormUrlEncoded
    @POST("purchase/due-payment.php?declined_list=1")
    Call<AccountReceiptListResponse> gePaymentDeclinedist(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("sales/receipt.php?pending_list=1")
    Call<AccountReceiptListResponse> gePaymentPendingList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );
    @FormUrlEncoded
    @POST("sales/receipt.php?declined_list=1")
    Call<AccountReceiptListResponse> gePaymentDeclineList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("store") String store,
            @Field("payment_type") String payment_type
    );



    @FormUrlEncoded
    @POST("purchase/return.php?pending-list=1")
    Call<PurchaseReturnPendingResponse> getPurchaseReturnPendingList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("purchase/return.php?list=1")
    Call<PurchaseHistoryResponse> getPurchaseReturnHistoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("purchase/purchase.php?declined-list=1")
    Call<PurchaseDeclineResponse> getPurchaseDeclineList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("store") String store
    );


    @FormUrlEncoded
    @POST("sales/sale.php?list=1")
    Call<SalesHistoryResponse> getSaleHistory(
            @Query("token") String token,
            @Query("page") String pageNumber,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String profile_type_id,
            @Field("store") String store
    );

    /**
     * for washing & crushing list of production
     */
    @FormUrlEncoded
    @POST("production/washing-crushing.php?list=1")
    Call<WashingCrushingResponse> getWashingList(
            @Query("token") String token,
            @Query("page") String pageNumber,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("item") String item,
            @Field("store") String store);

    /**
     * for washing & crushing list of production
     */
    @FormUrlEncoded
    @POST("production/washing-crushing.php?pending_list=1")
    Call<WashingCrushingResponse> getWashingPendingList(
            @Query("token") String token,
            @Query("page") String pageNumber,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("item") String item,
            @Field("store") String store);

    @FormUrlEncoded
    @POST("production/iodization.php?list=1")
    Call<IodizationHistoryResponse> getIodizationHistory(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("item") String item,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("production/iodization.php?pending-iodization-history=1")
    Call<IodizationPenDingResponse> pendingList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,
            @Field("end") String end,
            @Field("item") String item,
            @Field("store") String store
    );


    @FormUrlEncoded
    @POST("reports/reconcilation-report-form.php?reconcilation_report_form=1")
    Call<ReconciliationPageDataResponse> getReconciliationReportPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("reports/transfered-report-form.php?transfer_report_form=1")
    Call<TransferReportPageDataResponse> transferReportPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("reports/miller-licence-report-form.php?get_miller_by_associaiton=1")
    Call<ReconciliationReportMillerResponse> getReconciliationMiller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access
    );


    @FormUrlEncoded
    @POST("monitoring/monitoring-details.php?id=&get_monitoring_data=1")
    Call<MonitoringViewResponse> getMonitorViewDetails(
            @Query("token") String token,
            @Field("id") String id,  /**Serial id*/
            @Field("vendorID") String vendorId,
            @Field("user_id") String userId

    );

    @FormUrlEncoded
    @POST("qcqa/qcqa-list.php?get_list=1")
    Call<QcHistoryResponse> getQcHistory(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("testDate_from") String testDate_from,
            @Field("testDate (to)") String testDate,
            @Field("enterprise") String enterprise,
            @Field("model") String model

    );


    /**
     * phone number verify for sign up
     */
    @FormUrlEncoded
    @POST("signup/user.php?verify=1")
    Call<SuccessResponse> sendPhoneNumberForVerify(
            @Field("PrimaryMobile") String PrimaryMobile
    );

    /**
     * number and otp send
     **/
    @FormUrlEncoded
    @POST("/signup/user.php?verify_code=1")
    Call<SuccessResponse> sendOtpForNumberVerify(
            @Field("code") String code,
            @Field("PrimaryMobile") String PrimaryMobile


    );

    /**
     * signUp /submit new  userInformation
     **/
    @FormUrlEncoded
    @POST("signup/user.php?signup=1")
    Call<SuccessResponse> sendSignupInfo(
            @Field("FullName") String FullName,
            @Field("user_name") String user_name,
            @Field("password") String password,
            @Field("PrimaryMobile") String PrimaryMobile

    );

    /**
     * forgetPassword number verify
     **/
    @FormUrlEncoded
    @POST("signup/user.php?usi_user_pw_reset=1")
    Call<SuccessResponse> verifyNumberForForgotPw(
            @Field("phone") String PrimaryMobile
    );

    /**
     * forgetPassword number and phone verify
     **/
    @FormUrlEncoded
    @POST("signup/user.php?verify_code=1")
    Call<SuccessResponse> sendNumberAndOtp(
            @Field("code") String code,
            @Field("PrimaryMobile") String PrimaryMobile

    );

    /**
     * password update
     */
    @FormUrlEncoded
    @POST("signup/user.php?pw_update=1")
    Call<SuccessResponse> sendNewPassword(
            @Field("user_name") String username,
            @Field("secretKey") String secretKey,
            @Field("new_password") String new_password);


    /**
     *
     **/

    @FormUrlEncoded
    @POST("sales/sale.php?details=1")
    Call<SalePendingDetailsResponse> getSalePendingDetails(
            @Query("serialID") String serialID,
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id ") String user_id,
            @Field("order_vendorID") String order_vendorID);


    /**
     * -----------------purchase Report Configuration start here -------
     */


/* for purchase report list /

@FormUrlEncoded
@POST("reports/purchase-report.php?purchase_report=1&search=1")
Call<PurchaseReportByDateResponse> getReportByDate(
@Query("token") String token,
@Field("start") String start,
@Field("end") String end,
@Field("associationID") String associationID,
@Field("miller_store") String miller_store,
@Field("store_id") String store_id,
@Field("supplier") String supplier,
@Field("brand[]") List<String> brand,
@Field("category[]") List<String> category
);
/* for store /
@FormUrlEncoded
@POST("reports/purchase-report-form.php? get_miller_store=1")
Call<PurchaseReportStoreResponse> getPurchaseReportStoreSelect(
@Query("token") String token,
@Field("miller_store") String miller_store
);

/* for purchase report pageData /
@FormUrlEncoded
@POST("reports/purchase-report-form.php? purchase_report_form=1")
Call<PurchaseReportResponse> getPurchaseReport(
@Query("token") String token,
@Field("vendorID") String vendorID,
@Field("storeID") String storeID,
@Field("store_access") String store_access,
@Field("profile_type_id") String profile_type_id

);

/* get miller association Id /

@FormUrlEncoded
@POST("reports/purchase-report-form.php?get_miller_by_associaiton=1")
Call<MillerReportByAssociationResponse> getPurchaseReportByAssociationId(
@Query("token") String token,
@Field("profile_type_id") String profile_type_id,
@Field("storeID") String storeID,
@Field("vendorID") String vendorID,
@Field("association_id") String association_id,
@Field("store_access") String store_access

);

/* for purchase store /
@FormUrlEncoded
@POST("reports/purchase-report-form.php?get_miller_store=1")
Call<PurchaseReportStoreResponse> getPurchaseReportStore(
@Query("token") String token,
@Field("miller_store") String miller_store
);

/**----------------- purchase Return Report Configuration start here -------*/

/* for purchase return report /
@FormUrlEncoded
@POST("reports/purchase-return-report-form.php?purchase_return_report_form=1")
Call<PurchaseReturnReportResponse> getPurchaseReturnReport(
@Query("token") String token,
@Field("vendorID") String vendorID,
@Field("storeID") String storeID,
@Field("store_access") String store_access,
@Field("profile_type_id") String profile_type_id

);

@FormUrlEncoded
@POST("reports/purchase-report-form.php?get_miller_store=1")
Call<PurchaseReturnReportStoreResponse> getPurchaseReturnReportStore(
@Query("token") String token,
@Field("miller_store") String miller_store
);
/* get miller of purchase ReturnReport by association Id /

@FormUrlEncoded
@POST("reports/purchase-report-form.php?get_miller_by_associaiton=1")
Call<PurchaseReportReturnMillerListResponse> getMillerListByAssociation(
@Query("token") String token,
@Field("profile_type_id") String profile_type_id,
@Field("storeID") String storeID,
@Field("vendorID") String vendorID,
@Field("association_id") String association_id,
@Field("store_access") String store_access

);

/**
* purchase return list
*/
    @FormUrlEncoded
    @POST("reports/purchase-return-report.php?purchase_report=1&search=1")
    // @POST("reports/purchase-report.php?purchase_report=1&search=1")
    Call<PurchaseReturnListResponse> getPurchaseReturnReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("user_id") String user_id,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("supplier") String supplier,
            @Field("brand[]") List<String> brand,
            @Field("category[]") List<String> category);

    /**
     * ----------------------Sale Report Start here--------------
     */

    @FormUrlEncoded
    @POST("reports/sales-report.php?sales_report=1&search=1")
    Call<SaleReportListResponse> getSaleReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("user_id") String userId,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("supplier") String supplier,
            @Field("brand[]") List<String> brand,
            @Field("category[]") List<String> category
    );

    /**
     * stock I/O report list
     */
    @FormUrlEncoded
    @POST("reports/stock-report.php?stock_report=1&search=1")
    Call<StockIOReportListResponse> getStockIOReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("user_id") String userId,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("brand[]") List<String> brand,
            @Field("category[]") List<String> category
    );


    /**
     * --------------- miller Licence Report Start here---------
     */
    @FormUrlEncoded
    @POST("reports/miller-licence-report-form.php?miller_licence_report=1")
    Call<MillerLicenceReportResponse> getMillerLicencePageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID
    );


    @FormUrlEncoded
    @POST("reports/sales-report-1.php?get_miller_by_associaiton=1")
    Call<MillerLicenceResponse> getLicenceMiller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access

    );

    @FormUrlEncoded
    @POST("reports/miller-licence-report.php?miller_licence_report=1&search=1")
    Call<MillerLicenceReportListResponse> getMillerLicenceReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("certificateTypeID") String certificateTypeID


    );


    @FormUrlEncoded
    @POST("reports/miller-licence-expire-report.php?miller_licence_report=1&search=1")
    Call<MillerLicenceExpireReportListResponse> getMillerLicenceExpireReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("vendorID") String vendorID,
            @Field("certificateTypeID") String certificateTypeID
    );

    @FormUrlEncoded
    @POST("reports/monitoring-report.php?monitoring_report=1&search=1")
    Call<MonitoringReportListResponse> monitoringReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("zoneID") String zoneID,
            @Field("monitoringType ") String monitoringType
    );

    @FormUrlEncoded
    @POST("reports/qc_qa_report.php?search=1&qcqa_report=1")
    Call<QcQaReportListResponse> qcQaReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("miller_store") String miller_store
    );


    /**
     * EMPLOYEE MILLER DATA
     */
    @FormUrlEncoded
    @POST("reports/miller-licence-report-form.php?get_miller_by_associaiton=1")
    Call<EmployeeMillerResponse> getEmployeeMiller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access
    );

    /**
     * for EMPLOYEE list
     */
    @FormUrlEncoded
    @POST("reports/miller-employee-report.php?miller_employee_report=1&search=1")
    Call<EmployeeReportListResponse> getEmployeeReportList(
            @Query("token") String token,
            @Field("miller_store") String miller_store,
            @Field("user_id") String userId


    );


    /**
     * ------------------Reconciliation report start here-----------------
     */


/* Reconciliation for page data /
@FormUrlEncoded
@POST("reports/reconcilation-report-form.php?reconcilation_report_form=1")
Call<ReconciliationPageDataResponse> getReconciliationReportPageData(
@Query("token") String token,
@Field("vendorID") String vendorID,
@Field("storeID") String storeID,
@Field("store_access") String store_access,
@Field("profile_type_id") String profile_type_id
);
/* Reconciliation miller data /
@FormUrlEncoded
@POST("reports/miller-licence-report-form.php?get_miller_by_associaiton=1")
Call<ReconciliationReportMillerResponse> getReconciliationMiller(
@Query("token") String token,
@Field("profile_type_id") String profile_type_id,
@Field("storeID") String storeID,
@Field("vendorID") String vendorID,
@Field("association_id") String association_id,
@Field("store_access") String store_access
);


/** for store*/
    @FormUrlEncoded
    @POST("reports/sales-report-1.php?get_miller_store=1")
    Call<ReconciliationStoreResponse> getReconciliationStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store

    );

    @FormUrlEncoded
    @POST("reports/transfered-report.php?transfered_report=1&search=1")
    Call<TransferReportListResponse> transferReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("store") String store,
            @Field("enterprise") String enterprise,
            @Field("item") String item
    );

    @FormUrlEncoded
    @POST("reports/customer-ledger.php?search=1&customer-ledger=1")
    Call<CustomerReportResponse1> getCustomerReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("customer") String customer,
            @Field("store") String store,
            @Field("date") String date,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST("reports/customer-ledger.php?search=1&customer-ledger=1")
    Call<SupplierResponse1> getSupplierLedger(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("supplier") String customer,
            @Field("store") String store,
            @Field("date") String date,
            @Field("type") String type
    );


    @FormUrlEncoded
    @POST("reports/customer-ledger.php?customer-ledger=1&search=1")
    Call<CustomerReportListResponse> getSupplierReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("supplier") String customer,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("reports/customer-ledger.php?search=1&vendor-ledger=1")
    Call<VendorReportResponse> getVendorReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("vendor") String customer,
            @Field("store") String store,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("expense/expense-report.php?expense_report=1")
    Call<ExpenseReportListReponse> getExpenseReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("store") String store,
            @Field("expense_typeID") String expense_typeID
    );

    @FormUrlEncoded
    @POST("reports/receipt-report-form.php?receipt_report=1")
    Call<ReceiptReportResponse> getReceiptList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String user,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?bank_ledger=1")
    Call<BankLedgerReportResponse> getBankledgerList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("transaction_type") String transaction_type,
            @Field("type") String type,
            @Field("bank") String bank,
            @Field("referenceID") String referenceID
    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?payment-instruction-report=1")
    Call<PaymentInstructionReportResponse> paymentInstructionReportList(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String userr,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?payment_report=1")
    Call<PaymentReportResponse> paymentReport(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String userr,
            @Field("payment_type") String payment_type
    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?transection_report=1")
    Call<TransactionReportResponse> gteInstructionReport(
            @Query("token") String token,
            @Field("type") String type,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("start") String start,
            @Field("end") String end,
            @Field("company") String company,
            @Field("usr") String userr,
            @Field("payment_type") String payment_type,
            @Field("store") String enterprise
    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?day_book=1")
    Call<DayBookReportResponse> getDaybookReport(
            @Query("token") String token,
            @Field("type") String type,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("start") String start,
            @Field("end") String end,
            @Field("store") String store,
            @Field("book_type") String book_type
    );

    @FormUrlEncoded
    @POST("reports/bank-ledger.php?debitor=1")
    Call<DebitorAndCreditorsReportResponse> creditReport(
            @Query("token") String token,
            @Field("debit_credit_type") String type,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_access") String store_access,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("reports/reconcilation-report.php?miller_licence_report=1&search=1")
    Call<ReconciliationReportListResponse> getReconciliationReportReportList(
            @Query("token") String token,
            @Field("reconciliation_type") String reconciliation_type,
            @Field("start") String start,
            @Field("end") String end,
            @Field("user_id") String user_id,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("brand[]") List<String> brand,
            @Field("items[]") List<String> items


    );


    @FormUrlEncoded
    @POST("reports/purchase-report.php?purchase_report=1&search=1")
    Call<PurchaseReportByDateResponse> getReportByDate(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("supplier") String supplier,
            @Field("brand[]") List<String> brand,
            @Field("category[]") List<String> category
    );

    @FormUrlEncoded
    @POST("reports/purchase-report-form.php?purchase_report_form=1")
    Call<PurchaseReportResponse> getPurchaseReport(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID

    );


    @FormUrlEncoded
    @POST("reports/purchase-report-form.php?get_miller_by_associaiton=1")
    Call<MillerReportByAssociationResponse> getPurchaseReportByAssociationId(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access

    );


    @FormUrlEncoded
    @POST("reports/purchase-report-form.php?get_miller_store=1")
    Call<PurchaseReportStoreResponse> getPurchaseReportStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store
    );

    /**
     * ----------------- purchase Return Report Configuration start here -------
     */


    @FormUrlEncoded
    @POST("reports/purchase-return-report-form.php?purchase_return_report_form=1")
    Call<PurchaseReturnReportResponse> getPurchaseReturnReport(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID

    );

    @FormUrlEncoded
    @POST("reports/purchase-report-form.php?get_miller_store=1")
    Call<PurchaseReturnReportStoreResponse> getPurchaseReturnReportStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store
    );


    @FormUrlEncoded
    @POST("reports/purchase-report-form.php? get_miller_store=1")
    Call<PurchaseReportStoreResponse> getPurchaseReportStoreSelect(
            @Query("token") String token,
            @Field("miller_store") String miller_store
    );


    @FormUrlEncoded
    @POST("reports/purchase-report-form.php?get_miller_by_associaiton=1")
    Call<PurchaseReportReturnMillerListResponse> getMillerListByAssociation(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access

    );


    @FormUrlEncoded
    @POST("reports/sales-report-1.php?get_miller_by_associaiton=1")
    Call<SaleReturnReportMillerResponse> getSaleReturnReportMiller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access

    );


    @FormUrlEncoded
    @POST("reports/sales-report-1.php?get_miller_store=1")
    Call<SaleReturnReportStoreResponse> getSaleReturnReportStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store

    );


    @FormUrlEncoded
    @POST("reports/sales-report-1.php?sales_report_form=1")
    Call<SaleReturnReportResponse> getSaleReturnReportPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID
    );


    @FormUrlEncoded
    @POST("reports/sales-return-report.php?sales_return_report=1&search=1")
    Call<SaleReturnReportListResponse> getSaleReturnList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("user_id") String userId,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("supplier") String supplier,
            @Field("brand[]") List<String> brand,
            @Field("category[]") List<String> category
    );

    @FormUrlEncoded
    @POST("reports/sales-report-district-wise.php?sales_report=1&search=1")
    Call<DistrictSaleReportResponse> getDistrictWiseSaleReport(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("associationID") String associationID,
            @Field("miller_store") String miller_store,
            @Field("district") String zone

    );

    @FormUrlEncoded
    @POST("reports/stock-report-form.php?stock_report_form=1")
    Call<StockIOReportResponse> getStockIOReportData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID

    );


    @FormUrlEncoded
    @POST("packaging/packaging.php?list=1")
    Call<PackagingListResponse> getPackagingList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("item") String item,
            @Field("store") String store
    );

    @FormUrlEncoded
    @POST("packeting/packeting.php?list=1")
    Call<PacketingListResponse> getPacketingList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorId,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("item") String item,
            @Field("store") String store
    );


    /**
     * --------------------------stock List start here-------------------------
     */

    @FormUrlEncoded
    @POST("stock/transfer.php?list=1")
    Call<StockTransferHistoryListResponse> getStockTransferHistoryList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("transfer_type") String transfer_type,//here transfer_type ( 102 for transfer out ,,, 101 for transfer in)
            @Field("store") String store,//optional
            @Field("enterprise") String enterprise,//optional
            @Field("start") String start,//optional
            @Field("end") String end,//optional
            @Field("item") String item//optional
    );


    /**
     * pending Transferred List
     */


    @FormUrlEncoded
    @POST("stock/transfer.php?pending-list=1")
    Call<StockPendingTransferredListResponse> getStockPendingTransferredList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("store") String store,//optional
            @Field("enterprise") String enterprise,//optional
            @Field("start") String start,//optional
            @Field("end") String end,//optional
            @Field("item") String item//optional
    );


    /**
     * pending decline List
     */
    @FormUrlEncoded
    @POST("stock/transfer.php?declined-list=1")
    Call<StockDeclineTransferredListResponse> getStockDeclineTransferredList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("store") String store,//optional
            @Field("enterprise") String enterprise,//optional
            @Field("start") String start,//optional
            @Field("end") String end,//optional
            @Field("item") String item//optional
    );


    /**
     * Reconciliation List
     */

    @FormUrlEncoded
    @POST("stock/reconciliation.php?list=1")
    Call<StockReconciliationHistoryListResponse> getStockReconciliationList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,//optional
            @Field("end") String end,//optional
            @Field("store") String store,//optional
            @Field("enterprise") String enterprise,//optional
            @Field("item") String item,//optional
            @Field("type") String type//optional

    );


    /**
     * Stock Pending Reconciliation List
     **/
    @FormUrlEncoded
    @POST("stock/reconciliation.php?pending-list=1")
    Call<StockPendingReconciliationListResponse> getStockPendingReconciliationList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,//optional
            @Field("end") String end,//optional
            @Field("store") String store,//optional
            @Field("enterprise") String enterprise,//optional
            @Field("item") String item,//optional
            @Field("type") String type//optional

    );


    /**
     * Stock Decline Reconciliation List
     */

    @FormUrlEncoded
    @POST("stock/reconciliation.php?declined-list=1")
    Call<StockDeclineReconciliationListResponse> getStockDeclineReconciliationList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("start") String start,//optional
            @Field("end") String end,//optional
            @Field("store") String store,//optional
            @Field("enterprise") String enterprise,//optional
            @Field("item") String item,//optional
            @Field("type") String type//optional

    );


    /**
     * ------------------Supplier -----------------
     */

    @FormUrlEncoded
    @POST("supplier/supplier-list.php?list=1")
    Call<SupplierListResponse> getSupplierList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("typeID") String typeID,
            @Field("country") String country,
            @Field("district") String district,
            @Field("search_keyword") String search_keyword
    );


    @FormUrlEncoded
    @POST("accounts/transection.php?token=&transection-report=1")
    Call<TransactionResponse> getTransactionList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("transection_type") String transection_type,
            @Field("start") String start,
            @Field("end") String end,
            @Field("store") String store,
            @Field("company") String company,
            @Field("usr") String usr,
            @Field("payment_type") String payment_type
    );


    @FormUrlEncoded
    @POST("supplier/supplier-list.php?trash-list=1")
    Call<SupplierTrashListResponse> getSupplierTrashList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("typeID") String typeID,
            @Field("division") String division,
            @Field(" district") String district,
            @Field(" search_keyword") String search_keyword

    );


    /**
     * -------------Miller View Details
     */

    @FormUrlEncoded
    @POST("miller/miller-details.php?get_details=1")
    Call<MIllerViewResponse> getMillerViewDetails(
            @Query("token") String token,
            @Field("sl") String sl

    );


    /**
     * Packaging report Start here
     */

    @FormUrlEncoded
    @POST("reports/packeging-report-form.php?packeging_report=1")
    Call<PacketingPageDataReportResponse> getPacketingReportPageData(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID
    );

    @FormUrlEncoded
    @POST("reports/packeging-report-form.php?get_miller_by_associaiton=1")
    Call<PacketMIllerReportResponse> getPacketMIller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access
    );

    @FormUrlEncoded
    @POST("reports/packeging-report-form.php?get_miller_store=1")
    Call<PacketReportStorteResponse> getPacketStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store
    );

    @FormUrlEncoded
    @POST("reports/packeting-report.php?packeting_report=1&search=1")
    Call<PacketingReportListResponse> getPacketingReportList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("referer") String referer
    );


    @FormUrlEncoded
    @POST("reports/packeging-report.php?packeging_report=1&search=1")
    Call<PackegingReportListResponse> packegingReportList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("referer") String referer
    );

    @FormUrlEncoded
    @POST("reports/production-report.php?purchase_report=1&search=1")
    Call<ProductionReportListResponse> productionReportList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("start") String start,
            @Field("end") String end,
            @Field("associationID") String associationID,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("order_type") String order_type
    );

    @FormUrlEncoded
    @POST("reports/packeging-report.php?packeging_report=1")
    Call<PackegingReportListResponse> getPacketingList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("associationID") String associationID,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("referer") String referer

    );


    /**
     * -----------------------------packeting report start here-----------------------------
     */

    @FormUrlEncoded
    @POST("reports/packeting-report-form.php?packeting_report=1")
    Call<PackagingReportPageDataResponse> getPackagingReportPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID
    );


    @FormUrlEncoded
    @POST("reports/packeting-report-form.php?get_miller_by_associaiton=1")
    Call<PackagingMillerResponse> getPackageMiller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access
    );


    @FormUrlEncoded
    @POST("reports/packeting-report-form.php?get_miller_store=1")
    Call<PackagingStoreResponse> getPackagingStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store
    );


    @FormUrlEncoded
    @POST("reports/packeting-report.php?packeting_report=1&search=1")
    Call<PacketingReportListResponse> getPackagingReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("associationID") String associationID,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id,
            @Field("referer") String referer
    );


    /**
     * --------------------------------IOdine report start here-------------------
     */
    /**
     * --------------------------------Iodine report start here-------------------
     */

    @FormUrlEncoded
    @POST("reports/iodine-used-report-form.php?iodine_report=1")
    Call<IodineReportPageDataResponse> getIodineReportPageData(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("storeID") String storeID,
            @Field("store_access") String store_access,
            @Field("profile_type_id") String profile_type_id,
            @Field("profileID") String profileID
    );


    @FormUrlEncoded
    @POST("reports/iodine-used-report-form.php?get_miller_by_associaiton=1")
    Call<IodineReportMillerResponse> getIodineMiller(
            @Query("token") String token,
            @Field("profile_type_id") String profile_type_id,
            @Field("storeID") String storeID,
            @Field("vendorID") String vendorID,
            @Field("association_id") String association_id,
            @Field("store_access") String store_access
    );


    @FormUrlEncoded
    @POST("reports/iodine-used-report-form.php?get_miller_store=1")
    Call<IodineReportStoreResponse> getIOdineReportStore(
            @Query("token") String token,
            @Field("miller_store") String miller_store
    );


    @FormUrlEncoded
    @POST("reports/iodine-used-report.php?iodine_report=1&token=&search=1")
    Call<IodineReportListResponse> getIodineReportList(
            @Query("token") String token,
            @Field("start") String start,
            @Field("end") String end,
            @Field("miller_store") String miller_store,
            @Field("store_id") String store_id
    );

    /**
     * --------------------- store List start   here ------------
     */
    @FormUrlEncoded
    @POST("store/store.php?list=1")
    Call<StoreListResponse> getStoreList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId
    );

    @Multipart
    @POST("employee/edit-employee.php?edit-employee=1")
    Call<DuePaymentResponse> submitEditUserInfo1(
            @Query("token") String token,
            @Part("vendorID") RequestBody vendorID,
            @Part("user_id") RequestBody user_id,
            @Part("profile_type_id") RequestBody profile_type_id,
            @Part("employee_profile_id") RequestBody employee_profile_id,
            @Part("employee_vendorID") RequestBody employee_vendorID,
            @Part("designation") RequestBody designation,
            @Part("departmentID") RequestBody departmentID,
            @Part("Title") RequestBody Title,
            @Part("DisplayName") RequestBody DisplayName,
            @Part("FullName") RequestBody FullName,
            @Part("Gender") RequestBody Gender,
            @Part("PrimaryMobile") RequestBody PrimaryMobile,
            @Part("Email") RequestBody Email,
            @Part("storeID") RequestBody storeID,
            @Part("About") RequestBody About,
            @Part("DateOfBirth") RequestBody DateOfBirth,
            @Part("BloodGroup") RequestBody BloodGroup,
            @Part("Nationality") RequestBody Nationality,
            @Part("AlternativeEmail") RequestBody AlternativeEmail,
            @Part("OtherContactNumbers") RequestBody OtherContactNumbers,
            @Part("Website") RequestBody Website,
            @Part("CreatedDate") RequestBody joiningDate,
            @Part("update_profile") RequestBody update_profile,
            @Part MultipartBody.Part user_photo
    );

    @FormUrlEncoded
    @POST("stock/api.php?current-stock-list=1")
    Call<StockListResponse> getStockList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("product_title") String product_title,
            @Field("store") String store,
            @Field("brand") String brand,
            @Field("category") String category,
            @Field("enterprise") String enterprise
    );

    @FormUrlEncoded
    @POST("stock/api.php?current-stock-details=1")
    Call<StockInfDetailsResponse> getStockDetails(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("store") String store,
            @Field("product_id") String product_id
    );


    @FormUrlEncoded
    @POST("products/product-list.php?product_varient=1")
    Call<VarientResponse> getVarientList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorId,
            @Field("refProductID") String refProductID
    );

    @FormUrlEncoded
    @POST("store/store.php?update_store=1")
    Call<DuePaymentResponse> editStore(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_no") String enterprize_id,
            @Field("full_name") String full_name,
            @Field("store_name") String shortName,
            @Field("store_address") String store_address,
            @Field("storeID") String storeID
    );

    @FormUrlEncoded
    @POST("store/store.php?create_store=1")
    Call<DuePaymentResponse> addNewstore(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("store_no") String enterprize_id,
            @Field("full_name") String full_name,
            @Field("store_name") String shortName,
            @Field("store_address") String store_address
    );

    @FormUrlEncoded
    @POST("packeting/unpacketing.php?new-unpacketing=1")
    Call<DuePaymentResponse> unPackResponse(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("enterprise") String enterprise,
            @Field("storeID") String storeID,
            @Field("prodIDs") String itemId,
            @Field("customerID") String customerID,
            @Field("itemQtys") String itemQtys,
            @Field("subtotals") String subtotals,
            @Field("order_date") String order_date,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("store/store.php?change_status=1")
    Call<DuePaymentResponse> statusManage(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String enterprize_id

    );

    @FormUrlEncoded
    @POST("miller/add_miller.php?update_review_status=1")
    Call<DuePaymentResponse> millerApprove(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("profile_type_id") String profile_type_id,
            @Field("sl") String sl,
            @Field("reviewStatus") String reviewStatus

    );

    @FormUrlEncoded
    @POST("sales/api.php?qty_details=1")
    Call<HomepageListResponse> homePageList(
            @Query("token") String token,
            @Query("page") String page,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("category") String category,
            @Field("type") String type

    );


    @FormUrlEncoded
    @POST("purchase/return.php?pending-details=1")
    Call<PurchaseDetailsResponse> purchaseDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("purchase/return.php?pending-details=1")
    Call<PurchaseReturnPendingDetailsResponse> purchaseReturnPendingDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("sales/return.php?pending-details=1")
    Call<PurchaseReturnPendingDetailsResponse> saleReturnPendingDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("employee/users.php?status-update=1")
    Call<DuePaymentResponse> checkUserActivation(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("employeeProfileID") String employeeProfileID,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("products/add-product-packet.php?add_packet=1")
    Call<DuePaymentResponse> addNewPacketItem(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("packet_name") String packet_name,
            @Field("quantity") String quantity,
            @Field("productID") String productID,
            @Field("price") String price,
            @Field("unit") String unit,
            @Field("save_packet") String save_packet,
            @Field("storeID") String storeID,
            @Field("is_grouped") String is_grouped

    );

    @FormUrlEncoded
    @POST("products/add-product-packet.php?add_packet=1")
    Call<DuePaymentResponse> addVarient(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("packet_name") String packet_name,
            @Field("quantity") String quantity,
            @Field("productID") String productID,
            @Field("price") String price,
            @Field("unit") String unit,
            @Field("save_packet") String save_packet,
            @Field("storeID") String storeID,
            @Field("is_grouped") String is_grouped
    );

    @FormUrlEncoded
    @POST("products/packet-products.php?packet-product-list=1")
    Call<ItemPacketListResponse> getItemPacketList(
            @Query("token") String token,
            @Field("user_id") String user_id,
            @Field("vendorID") String vendorID,
            @Field("refProductID") String refProductID
    );

    @FormUrlEncoded
    @POST("customer/customer-edit-approval.php?customer-edited-data=1")
    Call<CustomerDetailsResponse> customerDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("typeID") String typeID,
            @Field("customerID") String customerID
    );

    @FormUrlEncoded
    @POST("customer/customer-edit-approval.php?approval=1")
    Call<DuePaymentResponse> approveCustomerEditDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("typeID") String typeID,
            @Field("customerID") String customerID,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("customer/customer-edit-approval.php?decline=1")
    Call<DuePaymentResponse> declineCustomerEditDetails(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("typeID") String typeID,
            @Field("customerID") String customerID,
            @Field("note") String note
    );

    @FormUrlEncoded
    @POST("products/packet-products.php?update-packet-products=1")
    Call<DuePaymentResponse> updateItemPacket(
            @Query("token") String token,
            @Field("vendorID") String vendorID,
            @Field("user_id") String user_id,
            @Field("storeID") String storeID,
            @Field("productID") String productID,
            @Field("packet_name") String packet_name,
            @Field("quantity") String quantity,
            @Field("unit") String unit,
            @Field("price") String price
    );

    @FormUrlEncoded
    @POST("dashboard/app_version.php?app=1")
    Call<GetVersionResponse> getVersionHistory(
            @Field("current_version") String current_version
    );


}
