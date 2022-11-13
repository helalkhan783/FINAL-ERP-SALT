package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayDueAmountViewModel extends ViewModel {
    public PayDueAmountViewModel() {
    }

    /**
     * api call for due payment for current user selected user
     */
    public MutableLiveData<DuePaymentResponse> apiCallForPayDueAmount(FragmentActivity context, String vendorId, Set<String> orders, String collectedPaidAmount, String totalDuee, String storeId, String userId, String permissions,
                                                                      String profileTypeId, String paymentTypeVal, String paymentSubType, String bankId, String date, String payment_remarks,String customerId,String totalDueAmount) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .payDuyAmount(
                        PreferenceManager.getInstance(context).getUserCredentials().getToken(),
                        vendorId, orders, collectedPaidAmount, totalDuee, storeId, userId, permissions,
                        profileTypeId, paymentTypeVal, paymentSubType, bankId, date, payment_remarks,customerId,totalDueAmount
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
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

  public MutableLiveData<DuePaymentResponse> creditVoucherResponse(FragmentActivity context, String vendorId, Set<String> orders, String collectedPaidAmount, String totalDuee, String storeId, String userId, String permissions,
                                                                      String profileTypeId, String paymentTypeVal, String paymentSubType, String bankId, String date, String payment_remarks,String customerID,String sales_type,String mainBankID ) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .creditVoucherResponse(
                        PreferenceManager.getInstance(context).getUserCredentials().getToken(),
                        vendorId, orders, collectedPaidAmount, totalDuee, storeId, userId, permissions,
                        profileTypeId, paymentTypeVal, paymentSubType, bankId, date, payment_remarks,customerID,sales_type,mainBankID
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
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


}
