package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.GetPreviousMillerInfoResponse;
import com.ismos_salt_erp.serverResponseModel.GetPreviousSaleInfoResponse;

import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateMillerViewModel extends ViewModel {


    public MutableLiveData<GetPreviousMillerInfoResponse> getPreviousMillerInfoBySid(FragmentActivity context, String sid) {
        MutableLiveData<GetPreviousMillerInfoResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();

        Call<GetPreviousMillerInfoResponse> call = RetrofitClient.getInstance().
                getApi().getMillerUpdatePageInfo(token, sid);
        call.enqueue(new Callback<GetPreviousMillerInfoResponse>() {
            @Override
            public void onResponse(Call<GetPreviousMillerInfoResponse> call, Response<GetPreviousMillerInfoResponse> response) {
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
            public void onFailure(Call<GetPreviousMillerInfoResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });
        return liveData;

    }

    /**
     * for update miller profile information
     */
    public MutableLiveData<DuePaymentResponse> submitUpdateMillerProfileInformation(
            FragmentActivity context, String remarks, String capacity, String selectedZoneId, String selectedOwnerTypeId,
            String processTypeId, String displayName, Set<String> millerTypeIdList, String millId,
            String divisionId, String districtId, String thanaId, String sID, String profileIdOldFromGetPageData, MultipartBody.Part profile_photo,
            String profileDetailsId, String refId, String isSubmit, String associationID) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody profileTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), profileTypeId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeId);
        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody selectedZoneid = RequestBody.create(MediaType.parse("multipart/form-data"), selectedZoneId);
        RequestBody selectedOwnerTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), selectedOwnerTypeId);
        RequestBody processTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), processTypeId);
        RequestBody displayname = RequestBody.create(MediaType.parse("multipart/form-data"), displayName);
        RequestBody millid = RequestBody.create(MediaType.parse("multipart/form-data"), millId);
        RequestBody remark = RequestBody.create(MediaType.parse("multipart/form-data"), remarks);
        RequestBody capacit = RequestBody.create(MediaType.parse("multipart/form-data"), capacity);
        RequestBody divisionid = RequestBody.create(MediaType.parse("multipart/form-data"), divisionId);
        RequestBody districtid = RequestBody.create(MediaType.parse("multipart/form-data"), districtId);
        RequestBody thanaid = RequestBody.create(MediaType.parse("multipart/form-data"), thanaId);
        RequestBody sid = RequestBody.create(MediaType.parse("multipart/form-data"), sID);
        RequestBody profileIdOldFromGetPagedata = RequestBody.create(MediaType.parse("multipart/form-data"), profileIdOldFromGetPageData);
        RequestBody profileDetailsid = RequestBody.create(MediaType.parse("multipart/form-data"), profileDetailsId);
        RequestBody refid = RequestBody.create(MediaType.parse("multipart/form-data"), refId);

        RequestBody issubmit = null;

        if (isSubmit != null) {
            issubmit = RequestBody.create(MediaType.parse("multipart/form-data"), isSubmit);
        }

        RequestBody associationiD = RequestBody.create(MediaType.parse("multipart/form-data"), associationID);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                updateMillerProfileInformation(
                        token, userid, profileTypeid, remark, capacit, storeid, vendorid, selectedZoneid, selectedOwnerTypeid, processTypeid,
                        displayname, millerTypeIdList, millid, divisionid, districtid, thanaid, sid, profileIdOldFromGetPagedata, profileDetailsid,
                        refid, issubmit, associationiD, profile_photo
                );

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.d("RESPONSE", String.valueOf(response.message()));

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
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> updateMillerOwnerInfo(
            FragmentActivity context, String ownerName, String profileId, String divisionID, String districtID, String thanaId,
            String nid, String mobile_no, String altMobile, String email, String id
    ) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        // String profileId = PreferenceManager.getInstance(context).getUserCredentials().getProfileId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .updateMillerOwnerInfo(
                        token, userId, profileTypeId, storeId, vendorId, ownerName, profileId, divisionID, districtID, thanaId,
                        nid, mobile_no, altMobile, email, id
                );

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("RESPONSE", String.valueOf(response.message()));

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
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });

        return liveData;

    }


    public MutableLiveData<DuePaymentResponse> updateMillerCertificateInfo(
            FragmentActivity context, String certificateTypeID,String profileID,  String slID, String issuerName, String issueDate, String certificateDate,
            MultipartBody.Part certificateImage, String renewDate, String remarks, String status/*, String reviewStatus, String reviewTime,
            String reviewBy, String ref_slId*/
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
         String userId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getUserId());
        String profile_type_id = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId());
        String store_id = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreID());
        String vendor_id = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getVendorID());

        RequestBody slid = RequestBody.create(MediaType.parse("multipart/form-data"), slID);
        RequestBody certificateTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), certificateTypeID);
        RequestBody profileid = RequestBody.create(MediaType.parse("multipart/form-data"), profileID);
        RequestBody issuername = RequestBody.create(MediaType.parse("multipart/form-data"), issuerName);
        RequestBody issuedate = RequestBody.create(MediaType.parse("multipart/form-data"), issueDate);
        RequestBody certificatedate = RequestBody.create(MediaType.parse("multipart/form-data"), certificateDate);
        RequestBody renewdate = RequestBody.create(MediaType.parse("multipart/form-data"), renewDate);
        RequestBody remarkS = RequestBody.create(MediaType.parse("multipart/form-data"), remarks);
        RequestBody statuS = RequestBody.create(MediaType.parse("multipart/form-data"), status);
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), userId);


        RequestBody profileTypeId = RequestBody.create(MediaType.parse("multipart/form-data"), profile_type_id);
        RequestBody storeId = RequestBody.create(MediaType.parse("multipart/form-data"), store_id);
        RequestBody vendorId = RequestBody.create(MediaType.parse("multipart/form-data"), vendor_id);



       /* RequestBody reviewstatus = RequestBody.create(MediaType.parse("multipart/form-data"), reviewStatus);
        RequestBody reviewtime = RequestBody.create(MediaType.parse("multipart/form-data"), reviewTime);
        RequestBody reviewby = RequestBody.create(MediaType.parse("multipart/form-data"), reviewBy);
        RequestBody refslId = RequestBody.create(MediaType.parse("multipart/form-data"), ref_slId);*/


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .updateMillerCertificateInfo(
                        token, slid, certificateTypeid, profileid, issuername, issuedate, certificatedate, certificateImage, renewdate, remarkS,
                        statuS, user_id, profileTypeId, storeId, vendorId
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

                }else {
                    liveData.postValue(null);
                    return;
                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> updateQcInformation(
            FragmentActivity context, String status, String labRemarks, String laboratoryPerson,
            String useTestKit, String trainedLaboratoryPerson, String standardProcedure, String haveLaboratory,
            String id, String main_id
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String profileID = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileId());
        String userId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getUserId());

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                updateQcInformation(
                        token, userId, status, labRemarks, laboratoryPerson,
                        useTestKit, trainedLaboratoryPerson, standardProcedure,
                        haveLaboratory, profileID, id, main_id);

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

                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });

        return liveData;

    }


    public MutableLiveData<DuePaymentResponse> updateEmployeeInfo(
            FragmentActivity context, String status, String totalTechFemale, String totalTechMale,
            String partTimeFemail, String partTimeMale, String fullTimeFemale, String fullTimeMale,
            String id
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String profileID = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileId());
        String userId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getUserId());
        String profileTypeId = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId());
        String storeID = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getStoreID());
        String vendorID = String.valueOf(PreferenceManager.getInstance(context).getUserCredentials().getVendorID());


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                updateEmployeeInfo(
                        token, "1", userId, profileTypeId, storeID, vendorID,
                        status, totalTechFemale, totalTechMale, partTimeFemail, partTimeMale,
                        fullTimeFemale, fullTimeMale, profileID, id
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

                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });

        return liveData;

    }


    public MutableLiveData<GetPreviousSaleInfoResponse> getEditSalePageInfo(FragmentActivity context, String orderSerial) {
        MutableLiveData<GetPreviousSaleInfoResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<GetPreviousSaleInfoResponse> call = RetrofitClient.getInstance().getApi().getEditSaleInfo(token, vendorID, orderSerial);

        call.enqueue(new Callback<GetPreviousSaleInfoResponse>() {
            @Override
            public void onResponse(Call<GetPreviousSaleInfoResponse> call, Response<GetPreviousSaleInfoResponse> response) {
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

                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetPreviousSaleInfoResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<GetPreviousSaleInfoResponse> getEditPurchasePageInfo(FragmentActivity context, String orderSerial) {
        MutableLiveData<GetPreviousSaleInfoResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<GetPreviousSaleInfoResponse> call = RetrofitClient.getInstance().getApi().getEditPurchaseInfo(token, vendorID, orderSerial);

        call.enqueue(new Callback<GetPreviousSaleInfoResponse>() {
            @Override
            public void onResponse(Call<GetPreviousSaleInfoResponse> call, Response<GetPreviousSaleInfoResponse> response) {
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
                }
            }

            @Override
            public void onFailure(Call<GetPreviousSaleInfoResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitMillerApproval(
            FragmentActivity context, String sid
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorID = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                millerDetailsSubmitApproval(
                        token, userId, sid, "1"
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

                }
            }

            @Override
            public void onFailure(Call<DuePaymentResponse> call, Throwable t) {
                Log.d("ERROR", "ERROR");
                liveData.postValue(null);
            }
        });

        return liveData;

    }


}
