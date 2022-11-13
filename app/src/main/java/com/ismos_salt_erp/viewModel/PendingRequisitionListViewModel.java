package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PendingRequisitionPageInfoResponse;
import com.ismos_salt_erp.serverResponseModel.PendingRequisitionResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingRequisitionListViewModel extends ViewModel {
    public PendingRequisitionListViewModel() {
    }

    public MutableLiveData<PendingRequisitionPageInfoResponse> getPenReqDetailsPageInfo(FragmentActivity context) {
        MutableLiveData<PendingRequisitionPageInfoResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String store_access = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        if (store_access.equals("null")) {//only for admin
            store_access = null;
        }
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        Call<PendingRequisitionPageInfoResponse> call = RetrofitClient.getInstance().getApi()
                .getPendingRequisitionPageInfo(token, vendorID,userId, store_access);

        call.enqueue(new Callback<PendingRequisitionPageInfoResponse>() {
            @Override
            public void onResponse(Call<PendingRequisitionPageInfoResponse> call, Response<PendingRequisitionPageInfoResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PendingRequisitionPageInfoResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                progressDialog.dismiss();
            }
        });
        return liveData;
    }


    /**
     * for get pending sales requisition list
     */
    public MutableLiveData<PendingRequisitionResponse> getPendingRequisitionList(FragmentActivity context, String storeId, String startDate, String endDate, String companyId) {
        MutableLiveData<PendingRequisitionResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<PendingRequisitionResponse> call = RetrofitClient.getInstance().getApi().getPendingRequisitionList(token, vendorID, userId, storeId, startDate, endDate, companyId);
        call.enqueue(new Callback<PendingRequisitionResponse>() {
            @Override
            public void onResponse(@NotNull Call<PendingRequisitionResponse> call, @NotNull Response<PendingRequisitionResponse> response) {

                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PendingRequisitionResponse> call, @NotNull Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);

            }
        });

        return liveData;
    }
}
