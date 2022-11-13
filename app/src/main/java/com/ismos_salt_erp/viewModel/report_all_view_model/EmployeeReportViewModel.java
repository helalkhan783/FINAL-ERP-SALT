package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.employee_report.list.EmployeeReportListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeReportViewModel extends CustomViewModel {
    /** for list */
public  MutableLiveData<EmployeeReportListResponse> getEmployeeReportList(FragmentActivity context,  String millerId ){
        MutableLiveData<EmployeeReportListResponse> liveData = new MutableLiveData<>();

        String token =getToken(context.getApplication());

        Call<EmployeeReportListResponse> call = RetrofitClient.getInstance().getApi(). getEmployeeReportList(token,millerId,getUserId(context.getApplication()));
        call.enqueue(new Callback<EmployeeReportListResponse>() {
            @Override
            public void onResponse(Call<EmployeeReportListResponse> call, Response<EmployeeReportListResponse> response) {
                if (response.isSuccessful()){
                    if (response == null || response.body().getStatus() == 500){
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400){
                        liveData.setValue(response.body());
                        return;}
                    if (response.body().getStatus() == 200){
                        liveData.setValue(response.body());
                        return;
                    }
                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<EmployeeReportListResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: "+t.getMessage());
                liveData.setValue(null);
            }
        });

        return  liveData;

    }

}
