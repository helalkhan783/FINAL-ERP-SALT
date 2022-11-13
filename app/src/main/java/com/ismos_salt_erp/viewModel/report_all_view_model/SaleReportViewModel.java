package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_report.sale_response.SaleReportListResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleReportViewModel extends CustomViewModel {
    /**
     * Sale report List
     */
    public MutableLiveData<SaleReportListResponse> getSaleReportList(FragmentActivity context, String startDate,
                                                                     String endDate, String millerId, String supplierId, String storeId,
                                                                     String brandId, String categoryId) {
        MutableLiveData<SaleReportListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());

        Call<SaleReportListResponse> call = RetrofitClient.getInstance().getApi().
                getSaleReportList(token, startDate,getUserId(context.getApplication()), endDate, millerId, storeId,
                        supplierId, Arrays.asList(brandId), Arrays.asList(categoryId));
        call.enqueue(new Callback<SaleReportListResponse>() {
            @Override
            public void onResponse(Call<SaleReportListResponse> call, Response<SaleReportListResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null ||response.body().getStatus() == 500) {
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
                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<SaleReportListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;
    }

}
