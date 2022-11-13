package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AddNewSaleStoreResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTransferViewModel extends ViewModel {

    public MutableLiveData<AddNewSaleStoreResponse> getAddNewSaleStoreData(FragmentActivity context) {
        MutableLiveData<AddNewSaleStoreResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();


        Call<AddNewSaleStoreResponse> call =
                RetrofitClient.getInstance().getApi().addNewSaleStoreData(token, vendorId, userId, profileTypeId);
        call.enqueue(new Callback<AddNewSaleStoreResponse>() {
            @Override
            public void onResponse(Call<AddNewSaleStoreResponse> call, Response<AddNewSaleStoreResponse> response) {
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
            public void onFailure(Call<AddNewSaleStoreResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> addNewTransfer(
            FragmentActivity context, List<Integer> firstPageStoreIdSet, Set<Integer> productIdSet,
            String transferTo, List<Integer> quantitiesSet, List<Double> buying_pricesSet,
            List<String> unitsSet, List<String> product_titles,String transferFromEnterpriseID,String transferToEnterpriseId,
            String note
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewTransfer(
                token, vendorId, userId, profileTypeId, storeId, firstPageStoreIdSet, productIdSet,
                transferTo, quantitiesSet, buying_pricesSet, unitsSet,
                product_titles,transferFromEnterpriseID,transferToEnterpriseId,note);


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


}
