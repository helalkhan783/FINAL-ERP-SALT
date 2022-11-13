package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DeclinedRequisitionListResponse;
import com.ismos_salt_erp.serverResponseModel.DeclinedRequisitionResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeclinedRequisitionListViewModel extends ViewModel {
    private ProgressDialog progressDialog;

    public DeclinedRequisitionListViewModel() {
    }

    /**
     * for get sales declined requisition list
     */
    public MutableLiveData<DeclinedRequisitionResponse> getDeclinedRequisitionList(FragmentActivity context) {
        MutableLiveData<DeclinedRequisitionResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DeclinedRequisitionResponse> call = RetrofitClient.getInstance().getApi().getDeclinedRequisitionList(token, vendorID, userId, "", "", "", "");
        call.enqueue(new Callback<DeclinedRequisitionResponse>() {
            @Override
            public void onResponse(@NotNull Call<DeclinedRequisitionResponse> call, @NotNull Response<DeclinedRequisitionResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 500 || response == null) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                } else liveData.postValue(null);
            }

            @Override
            public void onFailure(@NotNull Call<DeclinedRequisitionResponse> call, @NotNull Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
             }
        });

        return liveData;
    }
}
