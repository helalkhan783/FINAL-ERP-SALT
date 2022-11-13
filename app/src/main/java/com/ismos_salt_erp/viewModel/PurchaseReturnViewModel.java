package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.GetPurchaseReturnResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseReturnViewModel extends ViewModel {

    public MutableLiveData<GetPurchaseReturnResponse> getPurchaseReturnPageData(
            FragmentActivity context, String orderSerial
    ) {
        MutableLiveData<GetPurchaseReturnResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<GetPurchaseReturnResponse> call =
                RetrofitClient.getInstance().getApi().getPurchaseReturnDetails(token, vendorId, orderSerial);
        call.enqueue(new Callback<GetPurchaseReturnResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseReturnResponse> call, Response<GetPurchaseReturnResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseReturnResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitPurchaseReturn(FragmentActivity context, String orderId,
                                                                    String grandTotalValue, String paidAmount, List<String> orderDetailsId,
                                                                    List<Integer> productIdList, List<String> returnQuantity, List<String> buyPriceList, List<String> titleList,
                                                                    List<String> unitList, List<String> soldFromList, List<String> storeIDs, String subtotal,
                                                                    String compensession,
                                                                    String compensessionType,
                                                                    String returnAmount,
                                                                    String returnPaidAmount) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().submitPurchaseReturn(
                        token, profileTypeId, userId, vendorId, orderId, storeId, grandTotalValue,
                        orderDetailsId, productIdList, returnQuantity, buyPriceList, titleList,
                        unitList, soldFromList, paidAmount, storeIDs,
                        subtotal,
                        compensession,
                        compensessionType,
                        returnAmount,
                        returnPaidAmount

                        );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<GetPurchaseReturnResponse> getSalesReturnPageData(
            FragmentActivity context, String orderSerial
    ) {
        MutableLiveData<GetPurchaseReturnResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<GetPurchaseReturnResponse> call =
                RetrofitClient.getInstance().getApi().getSalesReturnDetails(token, vendorId, orderSerial);
        call.enqueue(new Callback<GetPurchaseReturnResponse>() {
            @Override
            public void onResponse(Call<GetPurchaseReturnResponse> call, Response<GetPurchaseReturnResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetPurchaseReturnResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> submitSalesReturn(
            FragmentActivity context,
            String orderId, String grandTotalValue, String paidAmount, List<String> orderDetailsId,
            List<Integer> productIdList, List<String> returnQuantity, List<String> buyPriceList, List<String> titleList,
            List<String> unitList, List<String> soldFromList,List<String> storeIDs,
            String subtotal,
            String compensession,
            String compensessionType,
            String returnAmount,
            String returnPaidAmount
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().submitSalesReturn(
                token, profileTypeId, userId, vendorId, orderId, storeId, grandTotalValue, paidAmount,
                orderDetailsId, productIdList, returnQuantity, buyPriceList, titleList,
                unitList, soldFromList,storeIDs,subtotal,compensession,compensessionType,returnAmount,returnPaidAmount
        );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> salesWholeOrderCancel(
            FragmentActivity context,
            String orderId
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().salesWholeOrderCancel(
                        token, profileTypeId, userId, vendorId, storeId, orderId
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {

                        if (response == null) {
                            liveData.setValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        }
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                            return;
                        }
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> purchaseWholeOrderCancel(
            FragmentActivity context,
            String orderId
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        Call<DuePaymentResponse> call =
                RetrofitClient.getInstance().getApi().purchaseWholeOrderCancel(
                        token, profileTypeId, userId, vendorId, storeId, orderId
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        try {

                            if (response == null) {
                                liveData.setValue(null);
                                return;
                            }
                            if (response.body().getStatus() == 400) {
                                liveData.postValue(response.body());
                                return;
                            }
                            if (response.body().getStatus() == 200) {
                                liveData.postValue(response.body());
                                return;
                            }
                        } catch (Exception e) {
                            liveData.postValue(null);
                        }
                    } else {
                        liveData.postValue(null);
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approvePurchaseReturn(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .purchaseReturnApprove(token, vendorId, userId, id, note);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        try {

                            if (response == null) {
                                liveData.setValue(null);
                                return;
                            }
                            if (response.body().getStatus() == 400) {
                                liveData.postValue(response.body());
                                return;
                            }
                            if (response.body().getStatus() == 200) {
                                liveData.postValue(response.body());
                                return;
                            }
                        } catch (Exception e) {
                            liveData.postValue(null);
                        }
                    } else {
                        liveData.postValue(null);
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> declinePurchaseReturn(FragmentActivity context, String id, String note) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .purchaseReturnDecline(token, vendorId, userId, id, note);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        try {
                            if (response == null) {
                                liveData.setValue(null);
                                return;
                            }
                            if (response.body().getStatus() == 400) {
                                liveData.postValue(response.body());
                                return;
                            }
                            if (response.body().getStatus() == 200) {
                                liveData.postValue(response.body());
                                return;
                            }
                        } catch (Exception e) {
                            liveData.postValue(null);
                        }
                    } else {
                        liveData.postValue(null);
                    }
                } catch (Exception e) {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


}
