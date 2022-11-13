package com.ismos_salt_erp.view.fragment.all_report.lisence_report;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.list_response.MillerLicenceReportListResponse;
import com.ismos_salt_erp.serverResponseModel.miller_response.MillerLicenceResponse;
import com.ismos_salt_erp.serverResponseModel.response.MillerLicenceReportResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LicenceReportViewModel extends CustomViewModel {
    public MutableLiveData<MillerLicenceReportResponse> getLicencePageData(FragmentActivity context, String profileID) {

        MutableLiveData<MillerLicenceReportResponse> liveData = new MutableLiveData<>();


        String token = getToken(context.getApplication());
        String storeID = getStoreId(context.getApplication());
        String storeAccessId = getStoreAccessId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        String profileId = getProfileId(context.getApplication());
        String user_id = getUserId(context.getApplication());
        Call<MillerLicenceReportResponse> call = RetrofitClient.getInstance().getApi()
                .getMillerLicencePageData(token, vendorId,user_id, storeID, storeAccessId, profileTypeId, profileId);


        call.enqueue(new Callback<MillerLicenceReportResponse>() {
            @Override
            public void onResponse(Call<MillerLicenceReportResponse> call, Response<MillerLicenceReportResponse> response) {
              try {
                  if (response.isSuccessful()){
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

                  }
              }
              catch (Exception e){
                  Log.d("Error",e.getMessage());
                  liveData.postValue(null);
              }
            }


            @Override
            public void onFailure(Call<MillerLicenceReportResponse> call, Throwable t) {
                liveData.setValue(null);
                Log.d("Error", "onFailure: " + t.getMessage());

            }
        });

        return liveData;

    }
    public MutableLiveData<MillerLicenceResponse> getMillerData(FragmentActivity context, String associationId) {


        MutableLiveData<MillerLicenceResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String storeID = getStoreId(context.getApplication());
        String storeAccessId = getStoreAccessId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        Call<MillerLicenceResponse> call = RetrofitClient.getInstance().getApi().getLicenceMiller(token, profileTypeId, storeID, vendorId, associationId, storeAccessId);


        call.enqueue(new Callback<MillerLicenceResponse>() {
            @Override
            public void onResponse(Call<MillerLicenceResponse> call, Response<MillerLicenceResponse> response) {
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
            }


            @Override
            public void onFailure(Call<MillerLicenceResponse> call, Throwable t) {
                liveData.setValue(null);
                Log.d("Error", "onFailure: " + t.getMessage());

            }
        });

        return liveData;

    }
    /**
     * for list
     */
    public MutableLiveData<MillerLicenceReportListResponse> licenceReportList(FragmentActivity context, String startDate, String endDate,  String millerId, String certificateTypeID) {
        MutableLiveData<MillerLicenceReportListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());

        Call<MillerLicenceReportListResponse> call = RetrofitClient.getInstance().getApi().getMillerLicenceReportList(token, startDate, endDate, millerId, certificateTypeID);
        call.enqueue(new Callback<MillerLicenceReportListResponse>() {
            @Override
            public void onResponse(Call<MillerLicenceReportListResponse> call, Response<MillerLicenceReportListResponse> response) {
                if (response.isSuccessful()) {
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
                }
            }

            @Override
            public void onFailure(Call<MillerLicenceReportListResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }
}
