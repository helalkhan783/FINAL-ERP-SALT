package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AddMonitoringPageResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.MonitoringModel;
import com.ismos_salt_erp.serverResponseModel.UpdateMonitoringPageResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitoringViewModel extends ViewModel {

    public MutableLiveData<AddMonitoringPageResponse> getAppMonitoringPageData(FragmentActivity context) {

        MutableLiveData<AddMonitoringPageResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<AddMonitoringPageResponse> call = RetrofitClient.getInstance().getApi()
                .addMonitoringPageData(token, vendorId,userID);

        call.enqueue(new Callback<AddMonitoringPageResponse>() {
            @Override
            public void onResponse(Call<AddMonitoringPageResponse> call, Response<AddMonitoringPageResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AddMonitoringPageResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> addNewMonitoring(
            FragmentActivity context, String monitoringDate, String publishDate, String zoneID, String millID, String monitoringType,
            MultipartBody.Part document, String monitoringSummary, String otherMonitoringTypename) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody addMonitoring = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
        RequestBody monitoringdate = RequestBody.create(MediaType.parse("multipart/form-data"), monitoringDate);
        RequestBody publishdate = RequestBody.create(MediaType.parse("multipart/form-data"), publishDate);
        RequestBody zoneiD = RequestBody.create(MediaType.parse("multipart/form-data"), zoneID);
        RequestBody milliD = RequestBody.create(MediaType.parse("multipart/form-data"), millID);
        RequestBody monitoringtype = RequestBody.create(MediaType.parse("multipart/form-data"), monitoringType);
        RequestBody monitoringsummary = RequestBody.create(MediaType.parse("multipart/form-data"), monitoringSummary);
        RequestBody otherMonitoringTypeName = RequestBody.create(MediaType.parse("multipart/form-data"), otherMonitoringTypename);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewMonitoring(
                token, addMonitoring, userid, vendorid, monitoringdate,
                publishdate, zoneiD, milliD, monitoringtype, document, monitoringsummary, otherMonitoringTypeName
        );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    } else {
                        liveData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
                Log.d("ERROR", "ERROR");
            }
        });


        return liveData;
    }


    public MutableLiveData<MonitoringModel> getMonitoringList(FragmentActivity context, String pageNumber, String startDate, String endDate, String PublishedDate, String monitoringProcessType) {
        MutableLiveData<MonitoringModel> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<MonitoringModel> call = RetrofitClient.getInstance().getApi().getMonitoringList(token, pageNumber, vendorId, userId, startDate, endDate, PublishedDate, monitoringProcessType);
        call.enqueue(new Callback<MonitoringModel>() {
            @Override
            public void onResponse(Call<MonitoringModel> call, Response<MonitoringModel> response) {
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
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                } else {
                    liveData.postValue(null);
                    return;
                }

            }

            @Override
            public void onFailure(Call<MonitoringModel> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }


    public MutableLiveData<UpdateMonitoringPageResponse> getUpdateMonitoringPageData(FragmentActivity context, String id) {
        MutableLiveData<UpdateMonitoringPageResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<UpdateMonitoringPageResponse> call = RetrofitClient.getInstance().getApi()
                .updateMonitoringPageData(token, id, vendorId,userId);
        call.enqueue(new Callback<UpdateMonitoringPageResponse>() {
            @Override
            public void onResponse(Call<UpdateMonitoringPageResponse> call, Response<UpdateMonitoringPageResponse> response) {
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
                    return;
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateMonitoringPageResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> updateMonitoring(
            FragmentActivity context, String id, String monitoringDate, String publishDate, String zoneId, String monitoringType,
            String millId, String monitoringSummary, MultipartBody.Part document, String otherMonitoringTypename) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        RequestBody iD = RequestBody.create(MediaType.parse("multipart/form-data"), id);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody profileTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), profileTypeId);
        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody monitoringdate = RequestBody.create(MediaType.parse("multipart/form-data"), monitoringDate);
        RequestBody publishdate = RequestBody.create(MediaType.parse("multipart/form-data"), publishDate);
        RequestBody zoneid = RequestBody.create(MediaType.parse("multipart/form-data"), zoneId);
        RequestBody monitoringtype = RequestBody.create(MediaType.parse("multipart/form-data"), monitoringType);
        RequestBody millid = RequestBody.create(MediaType.parse("multipart/form-data"), millId);
        RequestBody monitoringsummary = RequestBody.create(MediaType.parse("multipart/form-data"), monitoringSummary);
        RequestBody otherMonitoringTypeName = RequestBody.create(MediaType.parse("multipart/form-data"), otherMonitoringTypename);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().updateMonitoring(
                token, iD, userid, profileTypeid, vendorid, monitoringdate, publishdate, zoneid, monitoringtype, millid, monitoringsummary,
                document, otherMonitoringTypeName);

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
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }


}
