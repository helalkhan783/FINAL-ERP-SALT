package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.EditReconcilationPageResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReconcilationViewModel extends ViewModel {


    public MutableLiveData<DuePaymentResponse> addNewReconcilation(
            FragmentActivity context, String selectedEnterprise, List<Integer> productIdList,
            List<String> quantityList, List<Double> sellingPriseList, List<String> unitList,
            List<String> productTitleList, String selectedStore, String reconcilationType,
            String note
    ) {


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
        String date = df.format(Calendar.getInstance().getTime());


        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().submitAddNewReconcilation(
                token, vendorId, userId, profileTypeId, selectedEnterprise, productIdList, quantityList, sellingPriseList,
                unitList, productTitleList, Collections.singletonList(selectedStore), reconcilationType, date,
                note, 0
        );

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<EditReconcilationPageResponse> getEditReconcilationPageData(
            FragmentActivity context, String sid
    ) {
        MutableLiveData<EditReconcilationPageResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<EditReconcilationPageResponse> call
                = RetrofitClient.getInstance().getApi().getReconcilationPageData(token, vendorId, userId,sid);
        call.enqueue(new Callback<EditReconcilationPageResponse>() {
            @Override
            public void onResponse(Call<EditReconcilationPageResponse> call, Response<EditReconcilationPageResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EditReconcilationPageResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitReconcilationEditData(
            FragmentActivity context, String orderSid, String orderId, Set<Integer> productIdList,
            List<String> quantityList, List<Double> sellingPriseList,
            List<String> productTitleList, List<String> soldFromList, List<String> oldSoldFromList,
            List<String> previousQuantityList, String note, String damageStatus
    ) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd");
        String date = df.format(Calendar.getInstance().getTime());
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .submitConfirmEditReconciliationData(
                        token, profileTypeId, userId, orderSid, orderId, vendorId, storeId, productIdList, productTitleList, quantityList,
                        sellingPriseList, soldFromList, oldSoldFromList, previousQuantityList, note, damageStatus, date
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        liveData.postValue(response.body());
                    } catch (Exception e) {
                        liveData.postValue(null);
                    }
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
