package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.CustomerSearchResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.SupplierOrdersResponse;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierDueViewModel extends ViewModel {
    MutableLiveData<CustomerSearchResponse> customerSearchResponseMutableLiveData;
    MutableLiveData<SupplierOrdersResponse> supplierOrdersResponseMutableLiveData;

    public SupplierDueViewModel() {
        customerSearchResponseMutableLiveData = new MutableLiveData<>();
        supplierOrdersResponseMutableLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<CustomerSearchResponse> getAllSuppliers() {
        return customerSearchResponseMutableLiveData;
    }

    /**
     * for get supplier order list
     *
     * @return
     */
    public MutableLiveData<SupplierOrdersResponse> getSupplierOrders() {
        return supplierOrdersResponseMutableLiveData;
    }

    /**
     * api call for pay supplier due
     */
    public MutableLiveData<DuePaymentResponse> apiCallForPaySupplierDue(FragmentActivity context, String token, Set<String> customerOrderIdList, String collectedPaidAmount, String totalDuee, String storeId, String userId, String permissions, String profileTypeId, String customerId, String vendorId, String paymentTypeVal, String paymentSubType,
                                                                        String customDiscount, String bankId, String branch,
                                                                        String accountNo, String remarks, String date, String selectedPaymentToOption,String accountId,String tota_due) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().supplierPaymentSend(
                token, customerOrderIdList, collectedPaidAmount, totalDuee, storeId, userId, permissions, profileTypeId, customerId, vendorId,
                paymentTypeVal, paymentSubType, customDiscount, bankId, branch, accountNo, remarks, date, selectedPaymentToOption,accountId,tota_due);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    /**
     * api call for get supplier order
     */
    public void apiCallForGetSupplierOrder(FragmentActivity context, String supplierId) {
        String currentUserToken = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String currentUserVendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<SupplierOrdersResponse> call = RetrofitClient.getInstance().getApi().getSupplierOrders(
                currentUserToken, supplierId, currentUserVendorId);
        call.enqueue(new Callback<SupplierOrdersResponse>() {
            @Override
            public void onResponse(Call<SupplierOrdersResponse> call, Response<SupplierOrdersResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        supplierOrdersResponseMutableLiveData.postValue(null);
                        return;
                    }
                    supplierOrdersResponseMutableLiveData.postValue(response.body());
                } else {
                    supplierOrdersResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SupplierOrdersResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                supplierOrdersResponseMutableLiveData.postValue(null);
            }
        });


    }


    public void apiCallForSearchSuppliers(String token, String vendorId, String text) {
        Call<CustomerSearchResponse> call = RetrofitClient.getInstance().getApi().searchSuppliersByKey(token, vendorId, text);
        call.enqueue(new Callback<CustomerSearchResponse>() {
            @Override
            public void onResponse(Call<CustomerSearchResponse> call, Response<CustomerSearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        customerSearchResponseMutableLiveData.postValue(null);
                        return;
                    }
                    customerSearchResponseMutableLiveData.postValue(response.body());
                    return;
                } else {
                    customerSearchResponseMutableLiveData.postValue(response.body());
                    return;
                }
            }

            @Override
            public void onFailure(Call<CustomerSearchResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                customerSearchResponseMutableLiveData.postValue(null);
            }
        });
    }
}
