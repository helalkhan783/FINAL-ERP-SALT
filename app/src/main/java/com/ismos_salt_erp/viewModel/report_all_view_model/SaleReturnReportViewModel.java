package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DistrictSaleReportResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.sale_return_report.sale_return_report_list.SaleReturnReportListResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleReturnReportViewModel extends CustomViewModel {
    /**
     * sale return report list
     */
    public MutableLiveData<SaleReturnReportListResponse> saleReturnReportList(FragmentActivity context, String startDate, String endDate,  String millerId, String storeId, String supplierId, String brandId, String itemId) {
        MutableLiveData<SaleReturnReportListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());

        Call<SaleReturnReportListResponse> call = RetrofitClient.getInstance().getApi().getSaleReturnList(token,
                startDate,getUserId(context.getApplication()), endDate,   millerId, storeId, supplierId, Arrays.asList(brandId), Arrays.asList(itemId));
        call.enqueue(new Callback<SaleReturnReportListResponse>() {
            @Override
            public void onResponse(Call<SaleReturnReportListResponse> call, Response<SaleReturnReportListResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() ==500) {
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
                }liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<SaleReturnReportListResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }
    public MutableLiveData<DistrictSaleReportResponse> getDistrictWiseSaleReport(FragmentActivity context, String startDate, String endDate, String associationId, String mill, String zone) {
        MutableLiveData<DistrictSaleReportResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());

        Call<DistrictSaleReportResponse> call = RetrofitClient.getInstance().getApi().
                getDistrictWiseSaleReport(token,getUserId(context.getApplication()),startDate,endDate,associationId,mill,zone);
        call.enqueue(new Callback<DistrictSaleReportResponse>() {
            @Override
            public void onResponse(Call<DistrictSaleReportResponse> call, Response<DistrictSaleReportResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
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
                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DistrictSaleReportResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }

}
