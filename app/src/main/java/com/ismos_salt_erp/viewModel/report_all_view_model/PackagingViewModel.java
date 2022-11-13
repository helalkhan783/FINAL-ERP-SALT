package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.list.PacketingReportListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PackagingViewModel extends CustomViewModel {
    /**
     * for list
     */

    public MutableLiveData<PacketingReportListResponse> getPacketingList(FragmentActivity context, String starDate ,String endDate ,String associationId ,String mllerId ,String storeId ,String referrerId) {

        String token = getToken(context.getApplication());

        MutableLiveData<PacketingReportListResponse> liveData = new MutableLiveData<>();


        Call<PacketingReportListResponse> call = RetrofitClient.getInstance().getApi().getPackagingReportList(token,starDate,endDate,associationId,mllerId,storeId,referrerId);
        call.enqueue(new Callback<PacketingReportListResponse>() {
            @Override
            public void onResponse(Call<PacketingReportListResponse> call, Response<PacketingReportListResponse> response) {
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

            @Override
            public void onFailure(Call<PacketingReportListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
                return;
            }
        });

        return liveData;

    }


}
