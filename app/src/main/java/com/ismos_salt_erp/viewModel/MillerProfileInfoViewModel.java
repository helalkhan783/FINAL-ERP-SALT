package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.MillerDistrictResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoResponse;
import com.ismos_salt_erp.serverResponseModel.MillerProfileInfoSaveResponse;
import com.ismos_salt_erp.serverResponseModel.ThanaListResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MillerProfileInfoViewModel extends ViewModel {
    public MutableLiveData<MillerProfileInfoResponse> getProfileInfoResponse(FragmentActivity context) {
        MutableLiveData<MillerProfileInfoResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<MillerProfileInfoResponse> call = RetrofitClient.getInstance().getApi().getMillerProfileInfo(token, vendorId);

        call.enqueue(new Callback<MillerProfileInfoResponse>() {
            @Override
            public void onResponse(Call<MillerProfileInfoResponse> call, Response<MillerProfileInfoResponse> response) {

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
                            return;
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                        liveData.postValue(null);
                    }
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MillerProfileInfoResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }

    public MutableLiveData<MillerDistrictResponse> getDistrictListByDivisionId(FragmentActivity context, String divisionId) {
        MutableLiveData<MillerDistrictResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        Call<MillerDistrictResponse> call = RetrofitClient.getInstance().getApi()
                .getDistrictListByDivisionId(token, divisionId);
        call.enqueue(new Callback<MillerDistrictResponse>() {
            @Override
            public void onResponse(Call<MillerDistrictResponse> call, Response<MillerDistrictResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<MillerDistrictResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
            }
        });
        return liveData;
    }

    public MutableLiveData<ThanaListResponse> getThanaListByDistrictId(FragmentActivity context, String districtId) {
        MutableLiveData<ThanaListResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        Call<ThanaListResponse> call = RetrofitClient.getInstance().getApi().getThanaListByDistrictId(token, districtId);
        call.enqueue(new Callback<ThanaListResponse>() {
            @Override
            public void onResponse(Call<ThanaListResponse> call, Response<ThanaListResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ThanaListResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
            }
        });
        return liveData;
    }

    public MutableLiveData<MillerProfileInfoSaveResponse> saveMillerProfileInfo(
            FragmentActivity context,
            String zoneID, String processTypeID, String DisplayName, Set<String> millTypeID, String capacity,
            String millID, String remarks, String countryID, String ownerTypeID, String divisionID, String districtID,
            String upazilaID, MultipartBody.Part profile_photo, String agree_term_condition, String declared) {
        MutableLiveData<MillerProfileInfoSaveResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userID = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String profile_typeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        RequestBody zoneid = RequestBody.create(MediaType.parse("multipart/form-data"), zoneID);
        RequestBody processTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), processTypeID);
        RequestBody displayName = RequestBody.create(MediaType.parse("multipart/form-data"), DisplayName);
        RequestBody capacityy = RequestBody.create(MediaType.parse("multipart/form-data"), capacity);
        RequestBody millid = RequestBody.create(MediaType.parse("multipart/form-data"), millID);
        RequestBody remarkss = RequestBody.create(MediaType.parse("multipart/form-data"), remarks);
        RequestBody countryid = RequestBody.create(MediaType.parse("multipart/form-data"), countryID);
        RequestBody ownerTypeid = RequestBody.create(MediaType.parse("multipart/form-data"), ownerTypeID);
        RequestBody divisionid = RequestBody.create(MediaType.parse("multipart/form-data"), divisionID);
        RequestBody districtid = RequestBody.create(MediaType.parse("multipart/form-data"), districtID);
        RequestBody upazilaid = RequestBody.create(MediaType.parse("multipart/form-data"), upazilaID);
        RequestBody agree_termscondition = RequestBody.create(MediaType.parse("multipart/form-data"), agree_term_condition);
        RequestBody declare = RequestBody.create(MediaType.parse("multipart/form-data"), declared);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userID);
        RequestBody profile_typeid = RequestBody.create(MediaType.parse("multipart/form-data"), profile_typeId);

        RequestBody storeid = null;
        if (storeId != null) {
            storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeId);
        }
        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody add_profiledetails = RequestBody.create(MediaType.parse("multipart/form-data"), "1");


        Call<MillerProfileInfoSaveResponse> call = RetrofitClient.getInstance().getApi().saveMillerProfileInfo(
                token, add_profiledetails, zoneid, processTypeid, displayName, millTypeID, capacityy, millid,
                remarkss, countryid, ownerTypeid, divisionid, districtid, upazilaid, profile_photo,
                agree_termscondition, declare, userid, profile_typeid, storeid, vendorid
        );
        call.enqueue(new Callback<MillerProfileInfoSaveResponse>() {
            @Override
            public void onResponse(Call<MillerProfileInfoSaveResponse> call, Response<MillerProfileInfoSaveResponse> response) {
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
                    }
                }
            }

            @Override
            public void onFailure(Call<MillerProfileInfoSaveResponse> call, Throwable t) {
                Log.d("ERROR", "" + t.getMessage());
                liveData.postValue(null);
            }
        });
        return liveData;
    }

    public MutableLiveData<DuePaymentResponse> submitMillerCompanyOwnerInfo(
            FragmentActivity context, String profileInfoId, String ownerName, String divisionID, String districtID, String upazilaID,
            String nid, String mobile_no, String altmobile, String email) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String user_id = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        List<String> ownerNameList = Arrays.asList(ownerName);
        List<String> divisionIDList = Arrays.asList(divisionID);
        List<String> districtIDList = Arrays.asList(districtID);
        List<String> upazilaIDList = Arrays.asList(upazilaID);
        List<String> nidList = Arrays.asList(nid);
        List<String> mobileList = Arrays.asList(mobile_no);
        List<String> altmobileList = Arrays.asList(altmobile);
        List<String> emailList = Collections.singletonList(email);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                submitMillerCompanyOwnerInfo(
                        token, "1", profileInfoId, ownerNameList,
                        divisionIDList, districtIDList,
                        upazilaIDList, nidList,
                        mobileList, altmobileList, emailList,
                        user_id);

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
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }
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

    public MutableLiveData<DuePaymentResponse> submitLicenseInfoDetails(
            FragmentActivity context, String serverProfileId, String addCertificateInfo, String certificateTypeId, String issuerName, String issueDate, String certificateDate,
            MultipartBody.Part certificateImage, String ranewDateList, String remarksDateList) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String user_id = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        RequestBody addCertificateinfo = RequestBody.create(MediaType.parse("multipart/form-data"), addCertificateInfo);
        RequestBody profileid = RequestBody.create(MediaType.parse("multipart/form-data"), serverProfileId);
        RequestBody userID = RequestBody.create(MediaType.parse("multipart/form-data"), user_id);
        RequestBody isSueDate = RequestBody.create(MediaType.parse("multipart/form-data"), issueDate);
        RequestBody certiFicateDate = RequestBody.create(MediaType.parse("multipart/form-data"), certificateDate);
        RequestBody raneWDateList = RequestBody.create(MediaType.parse("multipart/form-data"), ranewDateList);


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                submitLicenseInfo(
                        token, addCertificateinfo, profileid, Collections.singletonList(certificateTypeId), Collections.singletonList(issuerName),
                        isSueDate, certiFicateDate, certificateImage,
                        raneWDateList,
                        Collections.singletonList(remarksDateList), userID);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }
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


    public MutableLiveData<DuePaymentResponse> submitQcInformation(
            FragmentActivity context, String profileIdFromServer, String status, String haveLaboratory,
            String standardProcedure, String trainedLaboratoryPerson, String useTestKit, String laboratoryPerson,
            String labRemarks) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String user_id = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                submitQcInfo(
                        token, "1", profileIdFromServer, status, haveLaboratory,
                        standardProcedure, trainedLaboratoryPerson, useTestKit, laboratoryPerson,
                        labRemarks, user_id);

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }
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


    public MutableLiveData<DuePaymentResponse> submitEmployeeInfo(
            FragmentActivity context, String profileIdFromServer,
            String fullTimeMale, String fullTimeFemale, String partTimeMale, String partTimeFemale,
            String totaltechMale, String totaltechFemale, String TOTFEM_EMP) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().
                submitEmployeeInfo(token, "1", profileIdFromServer, "1",
                        fullTimeMale, fullTimeFemale, partTimeMale, partTimeFemale, totaltechMale, totaltechFemale,
                        TOTFEM_EMP);

        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());
                        return;
                    }
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


}
