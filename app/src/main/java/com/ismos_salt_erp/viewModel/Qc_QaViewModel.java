package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AddQcQaPageResponse;
import com.ismos_salt_erp.serverResponseModel.DeclineQcQaResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.GetEditQcQaResponse;
import com.ismos_salt_erp.serverResponseModel.PendingQcQaResponse;
import com.ismos_salt_erp.serverResponseModel.Qc_qaDetailsResponse;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Qc_QaViewModel extends ViewModel {


    public MutableLiveData<AddQcQaPageResponse> getAddQcPageData(FragmentActivity context, String slid,String type) {

        MutableLiveData<AddQcQaPageResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String storeAccess = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        String profileTypeId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId());


        Call<AddQcQaPageResponse> call = RetrofitClient.getInstance().getApi().getAddQcPageData(
                token, vendorId, userId, storeId, storeAccess, profileTypeId, slid,type
        );
        call.enqueue(new Callback<AddQcQaPageResponse>() {
            @Override
            public void onResponse(Call<AddQcQaPageResponse> call, Response<AddQcQaPageResponse> response) {
                if (response.isSuccessful()) {
                    if (response.isSuccessful()) {
                        try {
                            if (response == null || response.body().getStatus() == 500) {
                                liveData.postValue(null);
                                return;
                            }

                            if (response.body().getStatus() == 400) {
                                liveData.postValue(response.body());
                                return;
                            }
                            if (response.body().getStatus() == 200) {
                                liveData.postValue(response.body());
                            } else {
                                liveData.postValue(null);
                            }
                        } catch (Exception e) {
                            liveData.postValue(null);

                        }
                    } else {
                        liveData.postValue(null);
                    }
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<AddQcQaPageResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> addQc_qa(
            FragmentActivity context, String testDate, Set<String> testId, List<String> parameterValueList,
            String model, String profile_type_id, String note, String storeId) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String user_id = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        // String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();

        //   List<String> testIdList = Collections.singletonList(testId);
        // List<String> parameterValueList = Collections.singletonList(parameterValue);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addQc_qa(
                token, storeId, testDate, vendorId, user_id, testId, parameterValueList, model, profile_type_id,
                note);
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
                        if (response.body().getStatus() == 200) {
                            liveData.postValue(response.body());
                        } else {
                            liveData.postValue(null);
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<PendingQcQaResponse> getPendingQcQaList(FragmentActivity context, String pageNumber, String fromDate, String toDate, String enterPriseId, String model) {
        MutableLiveData<PendingQcQaResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<PendingQcQaResponse> call = RetrofitClient.getInstance().
                getApi().getPendingQcQaList(token, pageNumber, userId, vendorId, fromDate, toDate, enterPriseId, model);
        call.enqueue(new Callback<PendingQcQaResponse>() {
            @Override
            public void onResponse(Call<PendingQcQaResponse> call, Response<PendingQcQaResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PendingQcQaResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<DeclineQcQaResponse> getDeclineQcQaList(FragmentActivity context, String pageNumber, String fromDate, String toDate, String enterPriseId, String model) {

        MutableLiveData<DeclineQcQaResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DeclineQcQaResponse> call = RetrofitClient.getInstance().getApi().getDeclineQcQaList(token, pageNumber, vendorId, userId, fromDate, toDate, enterPriseId, model);
        call.enqueue(new Callback<DeclineQcQaResponse>() {
            @Override
            public void onResponse(Call<DeclineQcQaResponse> call, Response<DeclineQcQaResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response == null || response.body().getStatus() == 500) {
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
                        } else {
                            liveData.postValue(null);
                            return;
                        }
                    } catch (Exception e) {
                         liveData.postValue(null);
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DeclineQcQaResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<GetEditQcQaResponse> getQCQaPageData(FragmentActivity context, String qcID) {
        MutableLiveData<GetEditQcQaResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String store_access = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess());
        String profile_type_id = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId());

        Call<GetEditQcQaResponse> call = RetrofitClient.getInstance().getApi().getQCQaPageData(
                token, vendorId, storeID, store_access, profile_type_id, qcID);


        call.enqueue(new Callback<GetEditQcQaResponse>() {
            @Override
            public void onResponse(Call<GetEditQcQaResponse> call, Response<GetEditQcQaResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null || response.body().getStatus() == 500) {
                        liveData.postValue(null);
                        return;
                    }

                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<GetEditQcQaResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> updateQcQaInformation(
            FragmentActivity context, List<String> testIdList, List<String> parameterValueList,
            String model, String id, String status, String qcId, String testDate, String note, List<String> ref_slID, List<String> details_slIDList,
            List<String> slIDList) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String profile_type_id = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId());
        String userId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getUserId());


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .updateQcQa(
                        token, userId, profile_type_id, vendorId, testIdList, parameterValueList, model, id,
                        status, qcId, testDate, note, ref_slID, details_slIDList, storeID, slIDList
                );
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    if (response == null) {
                        liveData.postValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.postValue(response.body());
                        return;
                    }
                    liveData.postValue(response.body());
                    return;
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<Qc_qaDetailsResponse> getQcQaPageDetails(FragmentActivity context, String id,String type) {
        MutableLiveData<Qc_qaDetailsResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeID = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String storeAccess = PreferenceManager.getInstance(context).getUserCredentials().getStoreAccess();
        String profile_type_id = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId());
        String userId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getUserId());

        Call<Qc_qaDetailsResponse> call = RetrofitClient.getInstance().getApi()
                .getQcQaDetailsBySlid(token, vendorId, storeID, storeAccess, profile_type_id, id, userId,type);

        call.enqueue(new Callback<Qc_qaDetailsResponse>() {
            @Override
            public void onResponse(Call<Qc_qaDetailsResponse> call, Response<Qc_qaDetailsResponse> response) {
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
                        liveData.postValue(response.body());
                    } else
                        liveData.postValue(null);
                    return;
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<Qc_qaDetailsResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> approveDeclineQcQaDetails(
            FragmentActivity context, String reviewStatus, String declineRemarks, String approve_decline, String id
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getUserId());


        Call<DuePaymentResponse> call
                = RetrofitClient.getInstance().getApi()
                .approveDeclineQcQa(token, reviewStatus, declineRemarks, userId, approve_decline, id);
        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response == null) {
                    liveData.postValue(null);
                    return;
                }
                if (response.body().getStatus() == 400) {
                    liveData.postValue(response.body());
                    return;
                }
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }


}
