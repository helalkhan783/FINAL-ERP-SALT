package com.ismos_salt_erp.viewModel;


import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.SupplierListResponse;
import com.ismos_salt_erp.serverResponseModel.SupplierTrashListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupplierViewModel extends CustomViewModel {
    /**
     * for supplier list
     */
    public MutableLiveData<SupplierListResponse> getSupplierList(FragmentActivity context, String pageNumber, String supplierType, String supplierCountryId, String supplierDistrictList,String search_keyword) {
        MutableLiveData<SupplierListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorid = getVendorId(context.getApplication());
        Call<SupplierListResponse> call = RetrofitClient.getInstance().getApi().getSupplierList(token, pageNumber, userId, vendorid, supplierType, supplierCountryId, supplierDistrictList,search_keyword);


        call.enqueue(new Callback<SupplierListResponse>() {
            @Override
            public void onResponse(Call<SupplierListResponse> call, Response<SupplierListResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                try {
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() == 200) {
                    liveData.postValue(response.body());
                    return;
                }
            }

            @Override
            public void onFailure(Call<SupplierListResponse> call, Throwable t) {
                liveData.setValue(null);
                Log.d("Error", "onFailure: " + t.getMessage());
                return;
            }
        });


        return liveData;
    }

    /**
     * for supplier trash list
     */
    public MutableLiveData<SupplierTrashListResponse> getSupplierTrashList(FragmentActivity context, String page, String supplierType, String customerDivisionId, String customerDistrictId,String search) {
        MutableLiveData<SupplierTrashListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String userId = getUserId(context.getApplication());
        String vendorid = getVendorId(context.getApplication());
        Call<SupplierTrashListResponse> call = RetrofitClient.getInstance().getApi().getSupplierTrashList(token, page, userId, vendorid, supplierType, customerDivisionId, customerDistrictId,search);


        call.enqueue(new Callback<SupplierTrashListResponse>() {
            @Override
            public void onResponse(Call<SupplierTrashListResponse> call, Response<SupplierTrashListResponse> response) {

           if (response.isSuccessful()){
               if (response == null) {
                   liveData.postValue(null);
                   return;
               }
               if (response.body().getStatus() == 500){
                   liveData.postValue(null);
                   return;
               }
               if (response.body().getStatus() == 400) {
                   liveData.postValue(response.body());
                   return;
               }
               if (response.body().getStatus() == 200) {
                   liveData.postValue(  response.body());
                   return;
               }
                }

            }

            @Override
            public void onFailure(Call<SupplierTrashListResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("Error", "onFailure: " + t.getMessage());
                return;
            }
        });


        return liveData;
    }

}
