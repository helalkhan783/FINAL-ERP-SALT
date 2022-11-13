package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportByDateResponse;
import com.ismos_salt_erp.serverResponseModel.PurchaseReportResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.serverResponseModel.PurchaseReturnListResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.get_miller_by_association.MillerReportByAssociationResponse;
import com.ismos_salt_erp.view.fragment.all_report.sale_and_purchase_report.purchase_report.purchase_store.PurchaseReportStoreResponse;


import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportViewModel extends CustomViewModel {
    /**
     * for purchase report by the ( Start date And End Date )
     */
    public MutableLiveData<PurchaseReportByDateResponse> getPurchaseReportByDate(FragmentActivity context, String startDate, String endDate
            , String brandId, String millerprofileId, String categoryId, String supplierId, String storeId) {

        MutableLiveData<PurchaseReportByDateResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String user_id = getUserId(context.getApplication());
        String vendorId = getVendorId(context.getApplication());

        Call<PurchaseReportByDateResponse> call = RetrofitClient.getInstance().getApi().getReportByDate(token, startDate, endDate,user_id ,vendorId,millerprofileId, storeId, supplierId, Arrays.asList(brandId), Arrays.asList(categoryId));

        call.enqueue(new Callback<PurchaseReportByDateResponse>() {
            @Override
            public void onResponse(Call<PurchaseReportByDateResponse> call, Response<PurchaseReportByDateResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() ==500) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400) {
                        liveData.setValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                    }
                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PurchaseReportByDateResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }

    /**
     * for purchase report (optional)
     */
    public MutableLiveData<PurchaseReportResponse> getPurchaseReportPageData(FragmentActivity context, String profileId) {

        MutableLiveData<PurchaseReportResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        String storeAccessId = getStoreAccessId(context.getApplication());
        String user_id = getUserId(context.getApplication());

       Call<PurchaseReportResponse> call = RetrofitClient.getInstance().getApi().getPurchaseReport(token, vendorId,user_id, storeId, storeAccessId, profileTypeId, profileId);
        call.enqueue(new Callback<PurchaseReportResponse>() {
            @Override
            public void onResponse(Call<PurchaseReportResponse> call, Response<PurchaseReportResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PurchaseReportResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;
    }


    /**
     * for miller  by association id (optional)
     */
    public MutableLiveData<MillerReportByAssociationResponse> getPurchaseReportByAssociationId(FragmentActivity context, String associationId) {

        MutableLiveData<MillerReportByAssociationResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        String profileTypeId = getProfileTypeId(context.getApplication());
        String storeAccessId = getStoreAccessId(context.getApplication());

        Call<MillerReportByAssociationResponse> call = RetrofitClient.getInstance().getApi()
                .getPurchaseReportByAssociationId(token, profileTypeId, storeId, vendorId, associationId, storeAccessId);
        call.enqueue(new Callback<MillerReportByAssociationResponse>() {
            @Override
            public void onResponse(Call<MillerReportByAssociationResponse> call, Response<MillerReportByAssociationResponse> response) {
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
            public void onFailure(Call<MillerReportByAssociationResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;

    }

    /**
     * for store
     */
    public MutableLiveData<PurchaseReportStoreResponse> getPurchaseReportStore(FragmentActivity context, String millerStoreId) {

        MutableLiveData<PurchaseReportStoreResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());


        Call<PurchaseReportStoreResponse> call = RetrofitClient.getInstance().getApi()
                .getPurchaseReportStoreSelect(token, millerStoreId);
        call.enqueue(new Callback<PurchaseReportStoreResponse>() {
            @Override
            public void onResponse(Call<PurchaseReportStoreResponse> call, Response<PurchaseReportStoreResponse> response) {
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
            public void onFailure(Call<PurchaseReportStoreResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;

    }


/** -----------------return report start here--------------*/

    public MutableLiveData<PurchaseReturnListResponse> getPurchaseReturnReportList(FragmentActivity context, String startDate, String endDate
            , String brandId, String millerProfileId, String categoryId, String supplierId, String storeId) {


        MutableLiveData<PurchaseReturnListResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String user_id = getUserId(context.getApplication());

        Call<PurchaseReturnListResponse> call = RetrofitClient.getInstance().getApi().getPurchaseReturnReportList(token, startDate, endDate,user_id, millerProfileId, storeId, supplierId, Arrays.asList(brandId), Arrays.asList(categoryId));
        call.enqueue(new Callback<PurchaseReturnListResponse>() {
            @Override
            public void onResponse(Call<PurchaseReturnListResponse> call, Response<PurchaseReturnListResponse> response) {
                if (response.isSuccessful()) {

                    if (response == null || response.body().getStatus() == 500) {
                        liveData.setValue(response.body());
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
                }else  liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<PurchaseReturnListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }

}
