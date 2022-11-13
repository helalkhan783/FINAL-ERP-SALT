package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.TransferReportListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.list_response.ReconciliationReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.miller_response.ReconciliationReportMillerResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.page_data_response.ReconciliationPageDataResponse;
import com.ismos_salt_erp.view.fragment.all_report.reconcilation_report.store.ReconciliationStoreResponse;
import com.ismos_salt_erp.viewModel.TransferReportPageDataResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReconciliationReportViewModel extends CustomViewModel {

    public MutableLiveData<TransferReportPageDataResponse> transferReportPageData(FragmentActivity context) {
        MutableLiveData<TransferReportPageDataResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        Call<TransferReportPageDataResponse> call = RetrofitClient.getInstance().
                getApi().
                transferReportPageData(token,vendorId,getUserId(context.getApplication()));


        call.enqueue(new Callback<TransferReportPageDataResponse>() {
            @Override
            public void onResponse(Call<TransferReportPageDataResponse> call, Response<TransferReportPageDataResponse> response) {
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
            public void onFailure(Call<TransferReportPageDataResponse> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }


    /**
     * for page data
     */
    public MutableLiveData<ReconciliationPageDataResponse> getReconciliationPageData(FragmentActivity context,String profileID) {
        MutableLiveData<ReconciliationPageDataResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String profileType = getProfileTypeId(context.getApplication());
        String storeAccesId = getStoreAccessId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        String user_id = getUserId(context.getApplication());
        Call<ReconciliationPageDataResponse> call = RetrofitClient.getInstance().
                getApi().
                getReconciliationReportPageData(token, vendorId, storeId, storeAccesId, profileType,profileID,user_id);


        call.enqueue(new Callback<ReconciliationPageDataResponse>() {
            @Override
            public void onResponse(Call<ReconciliationPageDataResponse> call, Response<ReconciliationPageDataResponse> response) {
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
            public void onFailure(Call<ReconciliationPageDataResponse> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }

    /**
     * miller
     */
    public MutableLiveData<ReconciliationReportMillerResponse> getReconciliationMiller(FragmentActivity context, String associationId) {
        MutableLiveData<ReconciliationReportMillerResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String profileType = getProfileTypeId(context.getApplication());
        String storeAccesId = getStoreAccessId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        Call<ReconciliationReportMillerResponse> call = RetrofitClient.getInstance().
                getApi().
                getReconciliationMiller(token, profileType, storeId, vendorId, associationId, storeAccesId);


        call.enqueue(new Callback<ReconciliationReportMillerResponse>() {
            @Override
            public void onResponse(Call<ReconciliationReportMillerResponse> call, Response<ReconciliationReportMillerResponse> response) {
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
            public void onFailure(Call<ReconciliationReportMillerResponse> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }

    /**
     * stock I/O report list
     */
    public MutableLiveData<ReconciliationReportListResponse> getReconciliationReportList(FragmentActivity context, String reconciliation_type, String startDate, String endDate,   String millerId, String storeId, String brandId, String itemId) {
        MutableLiveData<ReconciliationReportListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String user_id = getUserId(context.getApplication());

        Call<ReconciliationReportListResponse> call = RetrofitClient.getInstance().getApi().getReconciliationReportReportList(token, reconciliation_type,
                startDate, endDate,user_id, millerId, storeId, Arrays.asList(brandId), Arrays.asList(itemId));
        call.enqueue(new Callback<ReconciliationReportListResponse>() {
            @Override
            public void onResponse(Call<ReconciliationReportListResponse> call, Response<ReconciliationReportListResponse> response) {
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
            public void onFailure(Call<ReconciliationReportListResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }


    /**
     * for store
     */

    public MutableLiveData<ReconciliationStoreResponse> getSaleReturnReportStore(FragmentActivity context, String millerStoreId) {

        MutableLiveData<ReconciliationStoreResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());


        Call<ReconciliationStoreResponse> call = RetrofitClient.getInstance().getApi()
                .getReconciliationStore(token, millerStoreId);
        call.enqueue(new Callback<ReconciliationStoreResponse>() {
            @Override
            public void onResponse(Call<ReconciliationStoreResponse> call, Response<ReconciliationStoreResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
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
                        response.body().getMillerList();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<ReconciliationStoreResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;

    }



    public MutableLiveData<TransferReportListResponse> transferReportList(FragmentActivity context, String fromStore, String toStore, String startDate, String endDate, String transferItemId) {
        MutableLiveData<TransferReportListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String userId = getUserId(context.getApplication());

        Call<TransferReportListResponse> call = RetrofitClient.getInstance().getApi().transferReportList(token,vendorId,userId,startDate,endDate,fromStore,toStore,transferItemId );
        call.enqueue(new Callback<TransferReportListResponse>() {
            @Override
            public void onResponse(Call<TransferReportListResponse> call, Response<TransferReportListResponse> response) {
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
            public void onFailure(Call<TransferReportListResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }


}
