package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.LastOrderResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscountViewModel extends ViewModel {


    public DiscountViewModel() {
    }

    public MutableLiveData<Double> getTotalAfterDiscount(Double totalTk, Double discountNote) {
        MutableLiveData<Double> currentTotal = new MutableLiveData<>();
        double total = totalTk * (discountNote / 100);
        currentTotal.postValue(total);
        return currentTotal;
    }

    public MutableLiveData<Integer> getLastOrderId(FragmentActivity context) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        MutableLiveData<Integer> lastOrderId = new MutableLiveData<>();
        Call<LastOrderResponse> call = RetrofitClient.getInstance().getApi()
                .getLastOrderId(PreferenceManager.getInstance(context).getUserCredentials().getVendorID(),PreferenceManager.getInstance(context).getUserCredentials().getUserId(), "25");
        call.enqueue(new Callback<LastOrderResponse>() {
            @Override
            public void onResponse(Call<LastOrderResponse> call, Response<LastOrderResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body() == null) {
                        lastOrderId.postValue(0);
                        return;
                    }

                    assert response.body() != null;
                    if (response.body().getOrderID() != null) {
                        progressDialog.dismiss();
                        lastOrderId.postValue(response.body().getOrderID());
                    }
                }
            }

            @Override
            public void onFailure(Call<LastOrderResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return lastOrderId;
    }
}
