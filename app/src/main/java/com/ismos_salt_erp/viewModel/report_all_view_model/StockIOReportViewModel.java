package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.stock_in_out_report.list_response.StockIOReportListResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockIOReportViewModel extends CustomViewModel {

/** stock I/O report list*/

public  MutableLiveData<StockIOReportListResponse>  getStockIOReportList(FragmentActivity context, String startDate, String endDate,  String millerId, String storeId, String supplierId, String brandId, String itemId ){
    MutableLiveData<StockIOReportListResponse> liveData = new MutableLiveData<>();

    String token =getToken(context.getApplication());

    Call<StockIOReportListResponse> call = RetrofitClient.getInstance().getApi().getStockIOReportList(token,
            startDate,getUserId(context.getApplication()),endDate,millerId,storeId, Arrays.asList(brandId),Arrays.asList(itemId));
    call.enqueue(new Callback<StockIOReportListResponse>() {
        @Override
        public void onResponse(Call<StockIOReportListResponse> call, Response<StockIOReportListResponse> response) {
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
            }
            else liveData.postValue(null);
        }

        @Override
        public void onFailure(Call<StockIOReportListResponse> call, Throwable t) {
            Log.d("ERROR", "onFailure: "+t.getMessage());
            liveData.setValue(null);
        }
    });

    return  liveData;

}


}
