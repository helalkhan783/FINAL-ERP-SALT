package com.ismos_salt_erp.viewModel.report_all_view_model;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.ProductionReportListResponse;
import com.ismos_salt_erp.utils.CustomViewModel;
import com.ismos_salt_erp.view.fragment.all_report.packaging_report.list.PacketingReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.list.PackegingReportListResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.miller.PacketMIllerReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.page_data_response.PacketingPageDataReportResponse;
import com.ismos_salt_erp.view.fragment.all_report.packeting_report.store.PacketReportStorteResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PacketingReportViewModel extends CustomViewModel {
    /**
     * for page data
     */
    public MutableLiveData<PacketingPageDataReportResponse> getPacketReportPageData(FragmentActivity context, String profileID) {
        MutableLiveData<PacketingPageDataReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String profileType = getProfileTypeId(context.getApplication());
        String storeAccesId = getStoreAccessId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        String user_id = getUserId(context.getApplication());
        Call<PacketingPageDataReportResponse> call = RetrofitClient.getInstance().getApi().getPacketingReportPageData(token,user_id, vendorId, storeId, storeAccesId, profileType, profileID);
        call.enqueue(new Callback<PacketingPageDataReportResponse>() {
            @Override
            public void onResponse(Call<PacketingPageDataReportResponse> call, Response<PacketingPageDataReportResponse> response) {
                try {
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
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                    liveData.postValue(null);
                    return;
                }
            }
            @Override
            public void onFailure(Call<PacketingPageDataReportResponse> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }
    /**
     * miller
     */
    public MutableLiveData<PacketMIllerReportResponse> getPacketMillerByAssociation(FragmentActivity context, String associationId) {
        MutableLiveData<PacketMIllerReportResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendorId = getVendorId(context.getApplication());
        String profileType = getProfileTypeId(context.getApplication());
        String storeAccesId = getStoreAccessId(context.getApplication());
        String storeId = getStoreId(context.getApplication());
        Call<PacketMIllerReportResponse> call = RetrofitClient.getInstance().
                getApi().
                getPacketMIller(token, profileType, storeId, vendorId, associationId, storeAccesId);


        call.enqueue(new Callback<PacketMIllerReportResponse>() {
            @Override
            public void onResponse(Call<PacketMIllerReportResponse> call, Response<PacketMIllerReportResponse> response) {
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
            public void onFailure(Call<PacketMIllerReportResponse> call, Throwable t) {

                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }
    /**
     * stock   packaging report list
     */

    public MutableLiveData<PackegingReportListResponse> getPacketingReportList(FragmentActivity context, String startDate, String endDate, String associationId, String millerId, String storeId, String referrerId) {
        MutableLiveData<PackegingReportListResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());

        Call<PackegingReportListResponse> call = RetrofitClient.getInstance().getApi().getPacketingList(token,
                startDate, endDate, associationId, millerId, storeId, referrerId);
        call.enqueue(new Callback<PackegingReportListResponse>() {
            @Override
            public void onResponse(Call<PackegingReportListResponse> call, Response<PackegingReportListResponse> response) {
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
            public void onFailure(Call<PackegingReportListResponse> call, Throwable t) {
                Log.d("ERROR", "onFailure: " + t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }
    /**
     * for store
     */
    public MutableLiveData<PacketReportStorteResponse> getPacketStore(FragmentActivity context, String millerStoreId) {

        MutableLiveData<PacketReportStorteResponse> liveData = new MutableLiveData<>();

        String token = getToken(context.getApplication());


        Call<PacketReportStorteResponse> call = RetrofitClient.getInstance().getApi()
                .getPacketStore(token, millerStoreId);
        call.enqueue(new Callback<PacketReportStorteResponse>() {
            @Override
            public void onResponse(Call<PacketReportStorteResponse> call, Response<PacketReportStorteResponse> response) {
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
                        response.body().getMillerList();
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<PacketReportStorteResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });
        return liveData;

    }


    public MutableLiveData<PacketingReportListResponse> gePacketingReportList(FragmentActivity context, String starDate , String endDate ,   String mllerId , String storeId , String referrerId) {

        String token = getToken(context.getApplication());

        MutableLiveData<PacketingReportListResponse> liveData = new MutableLiveData<>();

        Call<PacketingReportListResponse> call = RetrofitClient.getInstance().getApi().getPacketingReportList(token,getUserId(context.getApplication()),starDate,endDate,mllerId,storeId,referrerId);
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

    public MutableLiveData<PackegingReportListResponse> getPackegingList(FragmentActivity context, String starDate , String endDate  , String mllerId , String storeId , String referrerId) {

        String token = getToken(context.getApplication());

        MutableLiveData<PackegingReportListResponse> liveData = new MutableLiveData<>();

        Call<PackegingReportListResponse> call = RetrofitClient.getInstance().getApi().packegingReportList(token,getUserId(context.getApplication()),starDate,endDate, mllerId,storeId,referrerId);
        call.enqueue(new Callback<PackegingReportListResponse>() {
            @Override
            public void onResponse(Call<PackegingReportListResponse> call, Response<PackegingReportListResponse> response) {
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
            public void onFailure(Call<PackegingReportListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
                return;
            }
        });

        return liveData;

    }
    public MutableLiveData<ProductionReportListResponse> getProductionList(FragmentActivity context, String starDate , String endDate , String associationId , String mllerId , String storeId , String productionId) {

        String token = getToken(context.getApplication());

        MutableLiveData<ProductionReportListResponse> liveData = new MutableLiveData<>();

        Call<ProductionReportListResponse> call = RetrofitClient.getInstance().getApi().productionReportList(token,getUserId(context.getApplication()),starDate,endDate,associationId,mllerId,storeId,productionId);
        call.enqueue(new Callback<ProductionReportListResponse>() {
            @Override
            public void onResponse(Call<ProductionReportListResponse> call, Response<ProductionReportListResponse> response) {
                if (response.isSuccessful()){
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
                }else liveData.postValue(null);

            }

            @Override
            public void onFailure(Call<ProductionReportListResponse> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
                liveData.setValue(null);
                return;
            }
        });

        return liveData;

    }



}
