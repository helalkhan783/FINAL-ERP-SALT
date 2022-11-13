package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AddNewPaymentLimitResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewLimitInstructionViewModel extends CustomViewModel {
    private final MutableLiveData<AddNewPaymentLimitResponse> addNewPaymentInstructionList;

    public AddNewLimitInstructionViewModel() {
        addNewPaymentInstructionList = new MutableLiveData<>();
    }


    public MutableLiveData<AddNewPaymentLimitResponse> getAddNewPaymentInstructionList() {
        if (addNewPaymentInstructionList != null) {
            return addNewPaymentInstructionList;
        }
        return null;
    }


    /**
     * for get add new payment instruction list
     *
     * @param //vendorID
     * @param //date
     */
    public void apiCallForSubmitNewPaymentLimit(FragmentActivity context, List<String> paymentLimitArray, List<String> customerIdArray, String userId, String vendorId, String storeId, String note, String paymentDate) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        Call<AddNewPaymentLimitResponse> call = RetrofitClient.getInstance().getApi().submitNewPaymentLimit(getToken(context.getApplication()),paymentLimitArray, customerIdArray, userId, vendorId, storeId, note, paymentDate);
        call.enqueue(new Callback<AddNewPaymentLimitResponse>() {
            @Override
            public void onResponse(@NotNull Call<AddNewPaymentLimitResponse> call, @NotNull Response<AddNewPaymentLimitResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response == null) {
                        addNewPaymentInstructionList.postValue(null);
                        return;
                    }
                    addNewPaymentInstructionList.postValue(response.body());
                    return;
                }
                addNewPaymentInstructionList.postValue(null);
            }

            @Override
            public void onFailure(@NotNull Call<AddNewPaymentLimitResponse> call, @NotNull Throwable t) {
                Log.d("ERROR", t.getMessage());
                addNewPaymentInstructionList.postValue(null);
                progressDialog.dismiss();
                Toast.makeText(context, "Something Wrong Contact to Support \n" + this.getClass().getSimpleName() + " " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
