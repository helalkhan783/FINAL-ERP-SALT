package com.ismos_salt_erp.viewModel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AddNewItemResponse;
import com.ismos_salt_erp.serverResponseModel.AddNewItemSubmitResponse;
import com.ismos_salt_erp.serverResponseModel.DuePaymentResponse;
import com.ismos_salt_erp.serverResponseModel.EditItemResponse;
import com.ismos_salt_erp.serverResponseModel.ItemCodeResponse;
import com.ismos_salt_erp.serverResponseModel.ItemStoreList;

import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewItemViewModel extends ViewModel {

    public MutableLiveData<AddNewItemResponse> getAddNewPageData(FragmentActivity context) {
        MutableLiveData<AddNewItemResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();

        Call<AddNewItemResponse> call = RetrofitClient.getInstance().getApi().getAddNewItemPageData(token, vendorId);
        call.enqueue(new Callback<AddNewItemResponse>() {
            @Override
            public void onResponse(Call<AddNewItemResponse> call, Response<AddNewItemResponse> response) {
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
            public void onFailure(Call<AddNewItemResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<ItemCodeResponse> getItemCodeByCatId(FragmentActivity context, String categoryId) {
        MutableLiveData<ItemCodeResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();

        Call<ItemCodeResponse> call = RetrofitClient.getInstance().getApi().getItemCodeByCatId(token, categoryId);
        call.enqueue(new Callback<ItemCodeResponse>() {
            @Override
            public void onResponse(Call<ItemCodeResponse> call, Response<ItemCodeResponse> response) {
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
            public void onFailure(Call<ItemCodeResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<AddNewItemSubmitResponse> submitAddNewItemInfo(
            FragmentActivity context, String categoryId, String productTitle, String unit, String brandID, String weight,
            String product_details, MultipartBody.Part product_image
    ) {
        MutableLiveData<AddNewItemSubmitResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();


        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody storeid = RequestBody.create(MediaType.parse("multipart/form-data"), storeId);
        RequestBody categoryid = RequestBody.create(MediaType.parse("multipart/form-data"), categoryId);
        RequestBody producttitle = RequestBody.create(MediaType.parse("multipart/form-data"), productTitle);
        RequestBody unitt = RequestBody.create(MediaType.parse("multipart/form-data"), unit);
        RequestBody brandid = RequestBody.create(MediaType.parse("multipart/form-data"), brandID);
        RequestBody product_dimensions = RequestBody.create(MediaType.parse("multipart/form-data"), weight);
        RequestBody productDetails = RequestBody.create(MediaType.parse("multipart/form-data"), product_details);
        RequestBody is_grouped = RequestBody.create(MediaType.parse("multipart/form-data"), "0");
        RequestBody selling_price = RequestBody.create(MediaType.parse("multipart/form-data"), "0");


        Call<AddNewItemSubmitResponse> call = RetrofitClient.getInstance().getApi().
                submitAddNewItemInfo(
                        token, vendorid, userid, storeid, categoryid, producttitle, unitt, brandid, product_dimensions,
                        productDetails, product_image, is_grouped, selling_price);
        call.enqueue(new Callback<AddNewItemSubmitResponse>() {
            @Override
            public void onResponse(Call<AddNewItemSubmitResponse> call, Response<AddNewItemSubmitResponse> response) {
                if (response.isSuccessful()) {
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

                    liveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<AddNewItemSubmitResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<ItemStoreList> getItemList(FragmentActivity context, String productId) {
        MutableLiveData<ItemStoreList> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String user_id = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        Call<ItemStoreList> call = RetrofitClient.getInstance().getApi().getItemStoreList(token, productId, vendorId,user_id);
        call.enqueue(new Callback<ItemStoreList>() {
            @Override
            public void onResponse(Call<ItemStoreList> call, Response<ItemStoreList> response) {
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

                }else liveData.postValue(null);
            }

            @Override
            public void onFailure(Call<ItemStoreList> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitConfirmAddNewItem(FragmentActivity context, String id, List<String> quantityList, List<String> storeList) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();
        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String storeId = PreferenceManager.getInstance(context).getUserCredentials().getStoreID();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().submitConfirmAddNewItemInfo(
                token, userId, vendorId, storeId, id, Collections.singletonList("0"), quantityList, storeList
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
                liveData.postValue(null);
            }
        });


        return liveData;
    }


    public MutableLiveData<DuePaymentResponse> submitEditItemInfo(
            FragmentActivity context, String productId, String productTitle, String categoryId, String unit, String brandId, String product_dimensions,
            String note, MultipartBody.Part product_image
    ) {
        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();


        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userid = PreferenceManager.getInstance(context).getUserCredentials().getUserId();

        RequestBody vendorid = RequestBody.create(MediaType.parse("multipart/form-data"), vendorId);
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), userid);
        RequestBody productid = RequestBody.create(MediaType.parse("multipart/form-data"), productId);
        RequestBody producttitle = RequestBody.create(MediaType.parse("multipart/form-data"), productTitle);
        RequestBody categoryid = RequestBody.create(MediaType.parse("multipart/form-data"), categoryId);
        RequestBody unitt = RequestBody.create(MediaType.parse("multipart/form-data"), unit);
        RequestBody brandid = RequestBody.create(MediaType.parse("multipart/form-data"), brandId);
        RequestBody productDimensions = RequestBody.create(MediaType.parse("multipart/form-data"), product_dimensions);


        RequestBody notee = null;
        if (note != null) {
            notee = RequestBody.create(MediaType.parse("multipart/form-data"), note);
        }

        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi()
                .submitItemEditInformation(
                        token, vendorid, userId, productid, producttitle, categoryid, unitt, brandid, productDimensions,
                        notee, product_image
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
                liveData.postValue(null);
            }
        });


        return liveData;
    }

    public MutableLiveData<EditItemResponse> getEditItemPageData(FragmentActivity context, String productId) {

        MutableLiveData<EditItemResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();


        Call<EditItemResponse> call = RetrofitClient.getInstance().getApi().getEditPageData(token, vendorId, productId);
        call.enqueue(new Callback<EditItemResponse>() {
            @Override
            public void onResponse(Call<EditItemResponse> call, Response<EditItemResponse> response) {
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
            public void onFailure(Call<EditItemResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;

    }

    /**
     * for edit and add Item price
     */


    public MutableLiveData<DuePaymentResponse> editItemPrice(FragmentActivity context, String productId, String productPriceId, String sellingPrice, String old_selling_price) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().editItemPriceData(token, vendorId, userId, productId, productPriceId, sellingPrice, old_selling_price);
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
                liveData.postValue(null);
            }
        });

        return liveData;
    }

    /**
     * for edit and add Item price
     */
    public MutableLiveData<DuePaymentResponse> addNewPrice(FragmentActivity context, String productId, String sellingPrice) {

        MutableLiveData<DuePaymentResponse> liveData = new MutableLiveData<>();

        String token = PreferenceManager.getInstance(context).getUserCredentials().getToken();
        String vendorId = PreferenceManager.getInstance(context).getUserCredentials().getVendorID();
        String userId = PreferenceManager.getInstance(context).getUserCredentials().getUserId();


        Call<DuePaymentResponse> call = RetrofitClient.getInstance().getApi().addNewPrice(token, vendorId, userId, productId, sellingPrice);
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
                liveData.postValue(null);
            }
        });

        return liveData;

    }


}
