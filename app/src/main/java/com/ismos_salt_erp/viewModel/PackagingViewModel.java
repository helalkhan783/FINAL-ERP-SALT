package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.NextPackagingId;
import com.ismos_salt_erp.serverResponseModel.PackageWeightAndDimensions;
import com.ismos_salt_erp.serverResponseModel.PackagingOriginItemsResponse;
import com.ismos_salt_erp.serverResponseModel.PackagingStockByRequiredId;
import com.ismos_salt_erp.serverResponseModel.PktNameListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagingViewModel extends ViewModel {


    public MutableLiveData<PackagingOriginItemsResponse> getPackagingOriginItems(FragmentActivity context) {
        MutableLiveData<PackagingOriginItemsResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<PackagingOriginItemsResponse> call = RetrofitClient.getInstance().getApi()
                .getPackagingOriginItems(token, vendorID);
        call.enqueue(new Callback<PackagingOriginItemsResponse>() {
            @Override
            public void onResponse(Call<PackagingOriginItemsResponse> call, Response<PackagingOriginItemsResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() != 200) {
                    liveData.postValue(null);
                    return;
                }
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<PackagingOriginItemsResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");

            }
        });
        return liveData;
    }

    public MutableLiveData<PktNameListResponse> getPktNameListByItemNameId(FragmentActivity context, String itemNameId) {
        MutableLiveData<PktNameListResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<PktNameListResponse> call = RetrofitClient.getInstance().
                getApi().getPktNameListByItemNameId(token, vendorId, itemNameId);
        call.enqueue(new Callback<PktNameListResponse>() {
            @Override
            public void onResponse(Call<PktNameListResponse> call, Response<PktNameListResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<PktNameListResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });


        return liveData;
    }


    public MutableLiveData<PackageWeightAndDimensions> getPackageWeightAndDimensions(FragmentActivity context, String packedId, String selectedStoreId) {
        MutableLiveData<PackageWeightAndDimensions> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();

        Call<PackageWeightAndDimensions> call = RetrofitClient.getInstance().getApi()
                .getPackageOthersDetailsByPacked(token, packedId, selectedStoreId);
        call.enqueue(new Callback<PackageWeightAndDimensions>() {
            @Override
            public void onResponse(Call<PackageWeightAndDimensions> call, Response<PackageWeightAndDimensions> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PackageWeightAndDimensions> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<PackagingStockByRequiredId> getPackagingStockByRequiredId(FragmentActivity context, String selectedStoreId, List<String> productIdList) {
        MutableLiveData<PackagingStockByRequiredId> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<PackagingStockByRequiredId> call
                = RetrofitClient.getInstance().getApi().getPackagingStockByRequiredId(token, productIdList, selectedStoreId, vendorId);
        call.enqueue(new Callback<PackagingStockByRequiredId>() {
            @Override
            public void onResponse(Call<PackagingStockByRequiredId> call, Response<PackagingStockByRequiredId> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PackagingStockByRequiredId> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<NextPackagingId> getNextPackagingId(FragmentActivity context) {
        MutableLiveData<NextPackagingId> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<NextPackagingId> call = RetrofitClient.getInstance().getApi().getNextPackagingId(token, vendorId);
        call.enqueue(new Callback<NextPackagingId>() {
            @Override
            public void onResponse(Call<NextPackagingId> call, Response<NextPackagingId> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NextPackagingId> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> confirmPackaging(
            FragmentActivity context, String selectedStoreId, String note, List<String> itemIdList,
            List<String> convertedIdList, List<String> sizeList, List<String> quantityList, List<String> packedProductList,
            List<String> subtotalList, List<String> packagingNotes, String packagingId, String date, String refCustomerId,String soldFrom
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .confirmPackaging(
                        token, vendorId, selectedStoreId, userId, note, profileTypeId, itemIdList, convertedIdList, sizeList,
                        quantityList, packedProductList, subtotalList, packagingNotes, packagingId,
                        date, refCustomerId,soldFrom);
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
                    if (response.body().getStatus() != 200) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });


        return liveData;

    }

}
