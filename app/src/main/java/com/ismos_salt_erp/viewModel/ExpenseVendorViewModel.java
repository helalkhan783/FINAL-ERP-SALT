package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.CustomerSearchResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.ExpenseDueResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseVendorViewModel extends ViewModel {
    private ProgressDialog progressDialog;
    MutableLiveData<CustomerSearchResponse> customerResponseMutableLiveData;
    MutableLiveData<ExpenseDueResponse> expenseDueResponseMutableLiveData;

    public ExpenseVendorViewModel() {

        customerResponseMutableLiveData = new MutableLiveData<>();
        expenseDueResponseMutableLiveData = new MutableLiveData<>();
    }


    /**
     * api call for pay expense due
     */
    public MutableLiveData<DuePaymentResponse> apiCallForPayExpenseDue(FragmentActivity context, String storeId, String customerId,
                                                                       List<String> orders, String collectedPaidAmount, String totalDueAmount, String customDiscount,
                                                                       String userId, String paymentTypeName, String paymentSubType, String paymentDate,
                                                                       String paymentRemarks, String userBankId, String branch, String userAccountNo, String bankID) {


        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String permissions = PreferenceManager.getInstance(context).getUserCredentials().getPermissions();
        String profile_type_id = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();

        progressDialog.show();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .payExpenseDue(
                        token, vendorId, storeId, customerId, permissions, profile_type_id, orders, collectedPaidAmount, totalDueAmount,
                        customDiscount, userId, paymentTypeName, paymentSubType, paymentDate, paymentRemarks,
                        userBankId, branch, userAccountNo, bankID);

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                }
                liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                progressDialog.dismiss();
            }
        });
        return liveData;
    }


    /**
     * for get all due orders expense by customer and vendor i
     *
     * @return
     */
    public MutableLiveData<ExpenseDueResponse> getExpenseVendorByVendorAndCustomerId() {
        return expenseDueResponseMutableLiveData;
    }


    /**
     * api call for get due order by selected customer id and vendor id
     */
    public void apiCallForGetExpenseVendorByVendorAndCustomerId(FragmentActivity context, String selectedCustomerId) {
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<ExpenseDueResponse>
                call = RetrofitClient.getInstance().getApi()
                .getExpenseOrders(token, vendorId,userId, selectedCustomerId);
        call.enqueue(new Callback<ExpenseDueResponse>() {
            @Override
            public void onResponse(Call<ExpenseDueResponse> call, Response<ExpenseDueResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        expenseDueResponseMutableLiveData.postValue(null);
                        return;
                    }
                    expenseDueResponseMutableLiveData.postValue(response.body());
                    return;
                } else {
                    expenseDueResponseMutableLiveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<ExpenseDueResponse> call, Throwable t) {
                expenseDueResponseMutableLiveData.postValue(null);
            }
        });

    }

    /**
     * for get search ExpenseVendors
     */
    public MutableLiveData<CustomerSearchResponse> getSearchExpenseVendors() {
        return customerResponseMutableLiveData;
    }

    /**
     * api call for get ForSearchExpenseVendors
     */
    public void apiCallForSearchExpenseVendor(FragmentActivity context, String text) {
        String currentUserToken = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String currentVendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        Call<CustomerSearchResponse> call = RetrofitClient.getInstance().getApi().searchExpenseVendor(currentUserToken, text, currentVendorId,userId);
        call.enqueue(new Callback<CustomerSearchResponse>() {
            @Override
            public void onResponse(Call<CustomerSearchResponse> call, Response<CustomerSearchResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        customerResponseMutableLiveData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerSearchResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
    }
}
