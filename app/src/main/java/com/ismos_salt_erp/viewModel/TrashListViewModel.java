package com.ismos_salt_erp.viewModel;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.ismos_salt_erp.retrofit.PacketDropDownResponse;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.AssignPacketItemResponse;
import com.ismos_salt_erp.serverResponseModel.TrashResponse;
import com.ismos_salt_erp.utils.CustomViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrashListViewModel extends CustomViewModel {

    public MutableLiveData<TrashResponse> getTrashList(FragmentActivity context, String pageNumber) {
        MutableLiveData<TrashResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String profileIdType = getProfileTypeId(context.getApplication());
        String storId = getStoreId(context.getApplication());

        Call<TrashResponse> call = RetrofitClient.getInstance().getApi().getTrashList(token, pageNumber, profileIdType, vendoId, getUserId(context.getApplication()), storId);
        call.enqueue(new Callback<TrashResponse>() {
            @Override
            public void onResponse(Call<TrashResponse> call, Response<TrashResponse> response) {
                try {
                    if (response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(response.body());
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                        return;
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<TrashResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }
  public MutableLiveData<AssignPacketItemResponse> getItemPacketList(FragmentActivity context, String pageNumber,String title,String brandId,String categoryId) {
        MutableLiveData<AssignPacketItemResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String profileIdType = getProfileTypeId(context.getApplication());
        String storId = getStoreId(context.getApplication());

        Call<AssignPacketItemResponse> call = RetrofitClient.getInstance().getApi().getItemPacketTag(token,pageNumber,vendoId,getUserId(context.getApplication()),title,brandId,categoryId);
        call.enqueue(new Callback<AssignPacketItemResponse>() {
            @Override
            public void onResponse(Call<AssignPacketItemResponse> call, Response<AssignPacketItemResponse> response) {
                try {
                    if (response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(response.body());
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                        return;
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<AssignPacketItemResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }


  public MutableLiveData<PacketDropDownResponse> getDropDoenPacketItem(FragmentActivity context, String productId) {
        MutableLiveData<PacketDropDownResponse> liveData = new MutableLiveData<>();
        String token = getToken(context.getApplication());
        String vendoId = getVendorId(context.getApplication());
        String profileIdType = getProfileTypeId(context.getApplication());
        String storId = getStoreId(context.getApplication());

        Call<PacketDropDownResponse> call = RetrofitClient.getInstance().getApi().getDropDownPacketItem(token,vendoId,getUserId(context.getApplication()), productId);
        call.enqueue(new Callback<PacketDropDownResponse>() {
            @Override
            public void onResponse(Call<PacketDropDownResponse> call, Response<PacketDropDownResponse> response) {
                try {
                    if (response == null || response == null) {
                        liveData.setValue(null);
                        return;
                    }
                    if (response.body().getStatus() == 400) {
                        liveData.setValue(response.body());
                        return;
                    }

                    if (response.body().getStatus() == 200) {
                        liveData.setValue(response.body());
                        return;
                    }
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PacketDropDownResponse> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
                liveData.setValue(null);
            }
        });

        return liveData;

    }

}
