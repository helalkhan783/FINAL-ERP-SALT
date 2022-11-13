package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.RequisitionDetailsResponse;
import com.ismos_salt_erp.serverResponseModel.RequisitionListResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRequisitionListViewModel extends ViewModel {
    private ProgressDialog progressDialog;

    public SalesRequisitionListViewModel() {
    }


    /**
     * for get single sale requisition details
     */
    public MutableLiveData<RequisitionDetailsResponse> getSingleRequisitionDetails(FragmentActivity context, String selectedProductId) {
        MutableLiveData<RequisitionDetailsResponse> liveData = new MutableLiveData<>();
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        Call<RequisitionDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getSingleRequisitionDetails(selectedProductId, token, vendorID,userId);

        call.enqueue(new Callback<RequisitionDetailsResponse>() {
            @Override
            public void onResponse(Call<RequisitionDetailsResponse> call, Response<RequisitionDetailsResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500){
                        liveData.postValue(null);
                        return;
                    } if (  response.body().getStatus() == 400){
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
                else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<RequisitionDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });
        return liveData;
    }


    /**
     * for get sales requisition list
     *
     * @param context
     * @return
     */
    public MutableLiveData<RequisitionListResponse> getSalesRequisitionList(FragmentActivity context) {
        MutableLiveData<RequisitionListResponse> liveData = new MutableLiveData<>();

        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<RequisitionListResponse> call = RetrofitClient.getInstance().getApi().getRequisitionList(token, vendorID,userId, "", "", "", "");
        call.enqueue(new Callback<RequisitionListResponse>() {
            @Override
            public void onResponse(Call<RequisitionListResponse> call, Response<RequisitionListResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400){
                        liveData.postValue(response.body());
                        return;
                    }

                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<RequisitionListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }
}
