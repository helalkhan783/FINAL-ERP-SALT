package com.ismos_salt_erp.viewModel;

import android.app.ProgressDialog;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.PaymentInstruction;
import com.ismos_salt_erp.utils.CustomViewModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInstructionViewModel extends CustomViewModel {
    private ProgressDialog progressDialog;
    private final MutableLiveData<PaymentInstruction> paymentInstructionList;

    public PaymentInstructionViewModel() {
        paymentInstructionList = new MutableLiveData<>();
    }


    public MutableLiveData<PaymentInstruction> getPaymentInstructionList() {
        if (paymentInstructionList != null) {
            return paymentInstructionList;
        }
        return null;
    }


    /**
     * for get payment instruction list
     *
     * @param vendorID
     * @param //date
     */
    public void apiCallForGetPaymentInstructionList(FragmentActivity context, String vendorID, String date) {

        progressDialog = new ProgressDialog(context);
        progressDialog.show();


        Call<PaymentInstruction> call1 = RetrofitClient.getInstance().getApi().getPaymentInstructionListByVendorIdAndDate(getToken(context.getApplication()),getUserId(context.getApplication()),vendorID, date);

        Call<PaymentInstruction> call = RetrofitClient.getInstance().getApi().getPaymentInstructionListByVendorIdAndDate(getToken(context.getApplication()),getUserId(context.getApplication()),vendorID, date);
        call.enqueue(new Callback<PaymentInstruction>() {
            @Override
            public void onResponse(@NotNull Call<PaymentInstruction> call, @NotNull Response<PaymentInstruction> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response == null) {
                        paymentInstructionList.postValue(null);
                        return;
                    }
                    paymentInstructionList.postValue(response.body());
                    return;
                } else {
                    paymentInstructionList.postValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PaymentInstruction> call, @NotNull Throwable t) {
                Log.d("ERROR", t.getMessage());
                paymentInstructionList.postValue(null);
                progressDialog.dismiss();
            }
        });
    }

}
