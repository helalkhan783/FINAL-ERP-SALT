package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.CustomerEditResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerListResponse;
import com.ismos_salt_erp.serverResponseModel.CustomerTrashListResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerViewModel extends ViewModel {
    public MutableLiveData<CustomerListResponse> getCustomerList(FragmentActivity context, String pageNumber, String searchByName, String division, String district) {
        MutableLiveData<CustomerListResponse> liveData = new MutableLiveData<>();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<CustomerListResponse> call = RetrofitClient.getInstance().getApi().getCustomerList(token, pageNumber, userId, vendorId, division, district, searchByName);
        call.enqueue(new Callback<CustomerListResponse>() {
            @Override
            public void onResponse(Call<CustomerListResponse> call, Response<CustomerListResponse> response) {
                if (response.isSuccessful()) {

                    if (response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<CustomerTrashListResponse> getCustomerTrashList(FragmentActivity context, String pageNumber, String division, String district,String search_keyword) {
        MutableLiveData<CustomerTrashListResponse> liveData = new MutableLiveData<>();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<CustomerTrashListResponse> call = RetrofitClient.getInstance().getApi().getCustomerTrashList(token, pageNumber, userId, vendorId, division, district,search_keyword);
        call.enqueue(new Callback<CustomerTrashListResponse>() {
            @Override
            public void onResponse(Call<CustomerTrashListResponse> call, Response<CustomerTrashListResponse> response) {
                if (response.isSuccessful()) {

                    if (response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(response.body());
                        return;
                    }
                    if (response.body().getStatus() == 200) {
                        liveData.postValue(response.body());

                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerTrashListResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> addNewCustomer(
            FragmentActivity context, String companyName, String customer_fname, String phone, String alt_phone,
            String email, String divisionId, String districtId, String thanaId, String bazar, String nid,
            String tin, String due_limit, String country, String typeID, String address, String initial_amount,
            MultipartBody.Part image, String note) {



        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeId);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody companyname = RequestBody.create(MediaType.parse("multipart/form-data"), companyName);
        RequestBody customerFname = RequestBody.create(MediaType.parse("multipart/form-data"), customer_fname);
        RequestBody phOne = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody altPhone = RequestBody.create(MediaType.parse("multipart/form-data"), alt_phone);
        RequestBody email_address = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody division = RequestBody.create(MediaType.parse("multipart/form-data"), divisionId);
        RequestBody district = RequestBody.create(MediaType.parse("multipart/form-data"), districtId);
        RequestBody thana = RequestBody.create(MediaType.parse("multipart/form-data"), thanaId);
        RequestBody bazarr = RequestBody.create(MediaType.parse("multipart/form-data"), bazar);
        RequestBody nidd = RequestBody.create(MediaType.parse("multipart/form-data"), nid);
        RequestBody tinn = RequestBody.create(MediaType.parse("multipart/form-data"), tin);
        RequestBody dueLimit = RequestBody.create(MediaType.parse("multipart/form-data"), due_limit);
        RequestBody countryy = RequestBody.create(MediaType.parse("multipart/form-data"), country);
        RequestBody typeid = RequestBody.create(MediaType.parse("multipart/form-data"), typeID);
        RequestBody addresss = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody initialAmount = RequestBody.create(MediaType.parse("multipart/form-data"), initial_amount);
        RequestBody notee = RequestBody.create(MediaType.parse("multipart/form-data"), note);

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewCustomer(
                token, vendorid, storeid, userid, companyname, customerFname, phOne, altPhone,
                email_address, division, district, thana, bazarr, nidd, tinn,
                dueLimit, countryy, typeid, addresss, initialAmount, image, notee
        );


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            return;
                        } if (response.body().getStatus() == 500) {
                            liveData.postValue(null);
                            return;
                        }

                        liveData.postValue(response.body());
                    }
                    else {
                        liveData.postValue(null);
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
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


    public MutableLiveData<DuePaymentResponse> addNewForeignSupplier(
            FragmentActivity context, String companyName, String customer_fname, String phone, String alt_phone,
            String email, String divisionId, String districtId, String thanaId, String bazar, String nid,
            String tin, String due_limit, String country, String typeID, String address, String initial_amount,
            MultipartBody.Part image, String note, String licence) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeId);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody companyname = RequestBody.create(MediaType.parse("multipart/form-data"), companyName);
        RequestBody customerFname = RequestBody.create(MediaType.parse("multipart/form-data"), customer_fname);
        RequestBody phOne = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody altPhone = RequestBody.create(MediaType.parse("multipart/form-data"), alt_phone);
        RequestBody email_address = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody licencee = RequestBody.create(MediaType.parse("multipart/form-data"), licence);

        RequestBody divisionMain = RequestBody.create(MediaType.parse("multipart/form-data"), divisionId);

        /*RequestBody district = RequestBody.create(MediaType.parse("multipart/form-data"), districtId);
        RequestBody thana = RequestBody.create(MediaType.parse("multipart/form-data"), thanaId);
        RequestBody bazarr = RequestBody.create(MediaType.parse("multipart/form-data"), bazar);
        RequestBody nidd = RequestBody.create(MediaType.parse("multipart/form-data"), nid);*/
        RequestBody tinn = RequestBody.create(MediaType.parse("multipart/form-data"), tin);
        RequestBody dueLimit = RequestBody.create(MediaType.parse("multipart/form-data"), due_limit);
        RequestBody countryy = RequestBody.create(MediaType.parse("multipart/form-data"), country);
        RequestBody typeid = RequestBody.create(MediaType.parse("multipart/form-data"), typeID);
        RequestBody addresss = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody initialAmount = RequestBody.create(MediaType.parse("multipart/form-data"), initial_amount);
        RequestBody notee = RequestBody.create(MediaType.parse("multipart/form-data"), note);

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewForeignSupplier(
                token, vendorid, storeid, userid, companyname, customerFname, phOne, altPhone,
                email_address, divisionMain, null, null, null, null, tinn,
                dueLimit, countryy, typeid, addresss, initialAmount, image, notee
        );


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response == null) {
                            liveData.postValue(null);
                            return;
                        }
                        if (response.body().getStatus() == 400) {
                            liveData.postValue(response.body());
                            Log.d("ERROR", "" + response.body().getMessage());
                            return;
                        }

                        liveData.postValue(response.body());
                        return;
                    } else {
                        liveData.postValue(null);
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                    liveData.postValue(null);
                    return;
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


    /**
     * previous data customer edit  response
     */
    public MutableLiveData<CustomerEditResponse> customerEditResponse(FragmentActivity context, String customerId, String typeId) {
        MutableLiveData<CustomerEditResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<CustomerEditResponse> call = RetrofitClient.getInstance().getApi().customerEditResponse(token, vendorId, storeId, userId, customerId, typeId);

        call.enqueue(new Callback<CustomerEditResponse>() {
            @Override
            public void onResponse(Call<CustomerEditResponse> call, Response<CustomerEditResponse> response) {
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

                        liveData.postValue(response.body());

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        liveData.postValue(null);
                        return;
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerEditResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                liveData.postValue(null);
            }
        });

        return liveData;
    }


    /**
     * edit customer or update response
     */

    public MutableLiveData<DuePaymentResponse> editCustomer(
            FragmentActivity context, String companyName, String customer_fname, String phone, String alt_phone,
            String email, String divisionId, String districtId, String thanaId, String bazar, String nid,
            String tin, String due_limit, String country, String typeID, String address, String initial_amount, String edit_Amount,
            MultipartBody.Part newImage, String oldImage, String note, String editNote, String customerID) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeId);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody companyname = RequestBody.create(MediaType.parse("multipart/form-data"), companyName);
        RequestBody customerFname = RequestBody.create(MediaType.parse("multipart/form-data"), customer_fname);
        RequestBody phOne = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        RequestBody altPhone = RequestBody.create(MediaType.parse("multipart/form-data"), alt_phone);
        RequestBody email_address = RequestBody.create(MediaType.parse("multipart/form-data"), email);
        RequestBody division = RequestBody.create(MediaType.parse("multipart/form-data"), divisionId);
        RequestBody district = RequestBody.create(MediaType.parse("multipart/form-data"), districtId);
        RequestBody thana = RequestBody.create(MediaType.parse("multipart/form-data"), thanaId);
        RequestBody bazarr = RequestBody.create(MediaType.parse("multipart/form-data"), bazar);
        RequestBody nidd = RequestBody.create(MediaType.parse("multipart/form-data"), nid);
        RequestBody tinn = RequestBody.create(MediaType.parse("multipart/form-data"), tin);
        RequestBody dueLimit = null;
        if (due_limit != null) {
            dueLimit = RequestBody.create(MediaType.parse("multipart/form-data"), due_limit);
        }
        RequestBody countryy = RequestBody.create(MediaType.parse("multipart/form-data"), country);
        RequestBody typeid = RequestBody.create(MediaType.parse("multipart/form-data"), typeID);
        RequestBody addresss = RequestBody.create(MediaType.parse("multipart/form-data"), address);
        RequestBody initialAmount = null;
        if (initial_amount != null) {
            initialAmount = RequestBody.create(MediaType.parse("multipart/form-data"), initial_amount);
        }
        RequestBody editAmount = null;
        if (edit_Amount != null) {
            editAmount = RequestBody.create(MediaType.parse("multipart/form-data"), edit_Amount);
        }
        RequestBody notee = RequestBody.create(MediaType.parse("multipart/form-data"), note);
        RequestBody editnote = RequestBody.create(MediaType.parse("multipart/form-data"), editNote);
        RequestBody oldimage = RequestBody.create(MediaType.parse("multipart/form-data"), oldImage);
        RequestBody customerId = RequestBody.create(MediaType.parse("multipart/form-data"), customerID);

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().editCustomer(
                token, vendorid, storeid, userid, companyname, customerFname, phOne, altPhone,
                email_address, division, district, thana, bazarr, nidd, tinn,
                dueLimit, countryy, typeid, addresss, initialAmount, editAmount, oldimage, newImage, notee, editnote, customerId
        );

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
                            return;

                        }

                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                        liveData.postValue(null);
                    }
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

    public MutableLiveData<DuePaymentResponse> updateDueLimit(FragmentActivity context, String customerId, String dueLimit) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().updateDueLimit(token, vendorId, userId, customerId, dueLimit);


        call.enqueue(new Callback<DuePaymentResponse>() {
            @Override
            public void onResponse(Call<DuePaymentResponse> call, Response<DuePaymentResponse> response) {

                if (response.isSuccessful()) {
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

                    }

                }else  liveData.postValue(null);

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
