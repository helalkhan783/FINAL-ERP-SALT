package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
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

public class EditedPurchaseOrderViewModel extends ViewModel {
    ProgressDialog progressDialog;

    public EditedPurchaseOrderViewModel() {
    }

    public MutableLiveData<EditedPurchaseOrderResponse> getEditedPurchaseOrderResponse(FragmentActivity context, String orderId) {
        progressDialog = new ProgressDialog(context);
        progressDialog.show();

        MutableLiveData<EditedPurchaseOrderResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        Call<EditedPurchaseOrderResponse> call = RetrofitClient.getInstance().getApi()
                .getEditedPurchaseOrderResponse(token, vendorId, orderId);
        call.enqueue(new Callback<EditedPurchaseOrderResponse>() {
            @Override
            public void onResponse(Call<EditedPurchaseOrderResponse> call, Response<EditedPurchaseOrderResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditedPurchaseOrderResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
             }
        });

        return liveData;

    }
}
