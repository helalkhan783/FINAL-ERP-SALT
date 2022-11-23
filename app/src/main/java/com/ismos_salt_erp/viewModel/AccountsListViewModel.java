package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AccountReceiptListResponse;
import com.ismos_salt_erp.serverResponseModel.CreditAndDebitVoucherResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.ReceiptDetailsResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountsListViewModel extends CustomViewModel {

    public MutableLiveData<AccountReceiptListResponse> receiptHistory(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().getReceiptPaymentHistoryList(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
                else {liveData.postValue(null);}
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<CreditAndDebitVoucherResponse> getDebitAndCreditVoucherList(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type, String voucherType) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<CreditAndDebitVoucherResponse> liveData = new MutableLiveData<>();

        Call<CreditAndDebitVoucherResponse> call = RetrofitClient.getInstance().getApi().getDebitAndCreditVoucherList(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type, voucherType);

        call.enqueue(new Callback<CreditAndDebitVoucherResponse>() {
            @Override
            public void onResponse(Call<CreditAndDebitVoucherResponse> call, Response<CreditAndDebitVoucherResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<CreditAndDebitVoucherResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> updateVoucher(FragmentActivity activity, String paymentId, String voucherType, String paidAmount, String customDiscount, String grandTotal, String orderID, String paymentType) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().updateVoucher(
                token, userId, vendorId, paymentId, voucherType, paidAmount, customDiscount, grandTotal, orderID, paymentType);

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }


    public MutableLiveData<AccountReceiptListResponse> getPaymentHistoryList(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().gePaymentHistoryList(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> paymentPending(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().gePaymentPending(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> paymentDeclined(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().gePaymentDeclinedist(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> reciptPending(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().gePaymentPendingList
      (
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> receiptDeclined(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().  gePaymentDeclineList(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> vendorPayment(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().vendorPayment(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }

                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> vendorPending(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().vendorPending(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }

                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<AccountReceiptListResponse> vendorDeclined(FragmentActivity activity, String pageNumber, String starDate, String endDate, String company, String user, String store, String payment_type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<AccountReceiptListResponse> liveData = new MutableLiveData<>();

        Call<AccountReceiptListResponse> call = RetrofitClient.getInstance().getApi().vendorDedclined(
                token, pageNumber, userId, vendorId, starDate, endDate, company, user, store, payment_type);

        call.enqueue(new Callback<AccountReceiptListResponse>() {
            @Override
            public void onResponse(Call<AccountReceiptListResponse> call, Response<AccountReceiptListResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }

                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<AccountReceiptListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }

    public MutableLiveData<ReceiptDetailsResponse> getAccountDetailsList(FragmentActivity activity, String batchNo,String type) {
        String token = getToken(activity.getApplication());
        String userId = getUserId(activity.getApplication());
        String vendorId = getVendorId(activity.getApplication());
        MutableLiveData<ReceiptDetailsResponse> liveData = new MutableLiveData<>();

        Call<ReceiptDetailsResponse> call = RetrofitClient.getInstance().getApi().getAccountDetailsList(
                token, userId, vendorId, batchNo,type);

        call.enqueue(new Callback<ReceiptDetailsResponse>() {
            @Override
            public void onResponse(Call<ReceiptDetailsResponse> call, Response<ReceiptDetailsResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }

                        if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }

                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }

                }
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ReceiptDetailsResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.postValue(null);
                return;
            }
        });

        return liveData;
    }


}
