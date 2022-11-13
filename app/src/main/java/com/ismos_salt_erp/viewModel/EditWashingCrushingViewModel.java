package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.EditWashingCrushingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditWashingCrushingViewModel extends ViewModel {


    public MutableLiveData<DuePaymentResponse> washingCrushingStockMessageCheck(
            FragmentActivity context, String orderId, String orderSerial, List<String> productIdList, List<String> soldFromList,
            List<String> quantityList, List<String> productTitleList, List<String> previousQuantityList, List<String> oldSoldFromList
    ) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().washingCrushingStockMessageCheck(
                        token, profileTypeId, vendorID, storeId, orderId, orderSerial, userID, productIdList, soldFromList,
                        quantityList, productTitleList, previousQuantityList, oldSoldFromList
                );


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                    }

                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "" + t.getMessage());
            }
        });

        return liveData;
    }


    public MutableLiveData<EditWashingCrushingResponse> getEditWashingCrushingInfo(FragmentActivity context, String orderSerial) {
        MutableLiveData<EditWashingCrushingResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<EditWashingCrushingResponse> call = RetrofitClient.getInstance().getApi()
                .getEditWashingCrushingInfo(token, vendorID,userID ,orderSerial);
        call.enqueue(new Callback<EditWashingCrushingResponse>() {
            @Override
            public void onResponse(Call<EditWashingCrushingResponse> call, Response<EditWashingCrushingResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                    }

                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EditWashingCrushingResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "" + t.getMessage());
            }
        });
        return liveData;

    }


    /**
     * For submit edited washing crushing ingo from server
     */
    public MutableLiveData<DuePaymentResponse> submitEditedWashingCrushingData(
            FragmentActivity context, String orderId, String orderSerial, String customerId,
            List<String> productIdList, List<String> sold_fromList, List<String> quantityList,
            List<String> selling_priceList, List<String> productTitleList, List<String> discountList,
            List<String> previousQuantityList, List<String> oldSoldFromList, String orderDate,
            String totalAmount, String note, String old_destination_store, String stage,
            String destination_store) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().
                        submitEditedWashingCrushingData(
                                token, profileTypeId, vendorID, storeId, orderId, orderSerial, userID,
                                customerId, productIdList, sold_fromList, quantityList, selling_priceList,
                                productTitleList, discountList, previousQuantityList, oldSoldFromList,
                                orderDate, totalAmount, note, old_destination_store, stage, destination_store);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }

                    liveData.postValue(response.body());
                }
                else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "" + t.getMessage());
            }
        });

        return liveData;
    }

}
