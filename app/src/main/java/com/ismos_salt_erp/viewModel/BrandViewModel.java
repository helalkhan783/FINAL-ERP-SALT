package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.BrandModel;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandViewModel extends CustomViewModel {
    public MutableLiveData<BrandModel> getBrandList(FragmentActivity context, String pageNumber) {
        MutableLiveData<BrandModel> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId =getUserId(context.getApplication());
        Call<BrandModel> call = RetrofitClient.getInstance().getApi().getBrandList(token, pageNumber,userId,vendorId);

        call.enqueue(new Callback<BrandModel>() {
            @Override
            public void onResponse(Call<BrandModel> call, Response<BrandModel> response) {
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
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        Log.d("Error",e.getMessage());
                        liveData.postValue(null);
                    }


                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<BrandModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }



    public MutableLiveData<DuePaymentResponse> editBrand(
            FragmentActivity context, MultipartBody.Part distributorImage, String brandName,String brandId
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeID);
        RequestBody brandname = RequestBody.create(MediaType.parse("multipart/form-data"), brandName);
        RequestBody brandID = RequestBody.create(MediaType.parse("multipart/form-data"), brandId);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .editBrand(token, brandname, vendorid,  brandID,storeid, distributorImage);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.setValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.setValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        liveData.postValue(null);
                    }


                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> deleteBrand(FragmentActivity context, String brandId) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .deleteBrand(token,vendorId,brandId,storeID);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.setValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.setValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        liveData.postValue(null);
                    }


                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> addNewBrand(
            FragmentActivity context, MultipartBody.Part distributorImage, String brandName
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody userIdD= RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeID);
        RequestBody brandname = RequestBody.create(MediaType.parse("multipart/form-data"), brandName);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .addNewBrand(token, brandname, vendorid, userIdD,storeid, distributorImage);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.setValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.setValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        liveData.postValue(null);
                    }


                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }





    public MutableLiveData<DuePaymentResponse> deleteStore(FragmentActivity context, String storeId) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
     //   String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .deleteStore(token,getUserId(context.getApplication()),vendorId,storeId);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.setValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.setValue(response.body());
                            return;
                        }
                    }catch (Exception e){
                        liveData.postValue(null);
                    }


                }else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }

}
