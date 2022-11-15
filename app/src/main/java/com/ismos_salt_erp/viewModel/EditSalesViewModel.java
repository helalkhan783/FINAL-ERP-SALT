package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.EditedPurchaseOrderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSalesViewModel extends ViewModel {
    public EditSalesViewModel() {
    }


    public MutableLiveData<EditedPurchaseOrderResponse> getEditableSalesDetails(FragmentActivity context, String orderId) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        MutableLiveData<EditedPurchaseOrderResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        progressDialog.show();
        Call<EditedPurchaseOrderResponse> call = RetrofitClient.getInstance().getApi()
                .getSalesEditDetails(token, vendorId, orderId);
        call.enqueue(new Callback<EditedPurchaseOrderResponse>() {
            @Override
            public void onResponse(Call<EditedPurchaseOrderResponse> call, Response<EditedPurchaseOrderResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400 || response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }

                }else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<EditedPurchaseOrderResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                liveData.postValue(null);
             }
        });

        return liveData;
    }
}
