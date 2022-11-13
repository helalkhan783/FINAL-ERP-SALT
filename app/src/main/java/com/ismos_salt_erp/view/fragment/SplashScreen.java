package com.ismos_salt_erp.view.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;


import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.Task;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.dialog.MyApplication;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.retrofit.RetrofitClient;
import com.ismos_salt_erp.serverResponseModel.CheckUserResponse;
import com.ismos_salt_erp.serverResponseModel.GetVersionResponse;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.InternetConnection;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends BaseFragment {

    private View view;
    public static String verSionName;
    public static boolean showPopupLicence;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        ButterKnife.bind(this, view);

        AnimationTime.animation(view.findViewById(R.id.imageView));
        /**
         * at first check the internet connection
         */
        new Handler().postDelayed(() -> {
            try {
                /**
                 * Check application version status
                 */
                if (!InternetConnection.isOnline(getActivity())) {
                    Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_internetConnectionFaildFragment);
                    return;
                }
                applicationUpdateControl();
                //  Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginFragment);

            } catch (Exception e) {
                Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginFragment);
            }
        }, 1500);

        //  }
        return view;
    }




    public void apiCallForDetectUser(FragmentActivity context) {
        if (!InternetConnection.isOnline(context)) {
            Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_internetConnectionFaildFragment);
            return;
        }
        if (PreferenceManager.getInstance(context).getUserCredentials() == null) {
            Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginFragment);
          //  Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_internetConnectionFaildFragment);
            return;
        }


        Call<CheckUserResponse> call = RetrofitClient.getInstance().getApi().checkUserToken(
                PreferenceManager.getInstance(context).getUserCredentials().getToken(),//token
                PreferenceManager.getInstance(context).getUserCredentials().getUserId(),
                PreferenceManager.getInstance(context).getUserCredentials().getVendorID()//vendorID
                //vendorID
        );
        call.enqueue(new Callback<CheckUserResponse>() {
            @Override
            public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus() == 200) {
                            if (PreferenceManager.getInstance(getContext()).getUserCredentials().getToken() != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("name", PreferenceManager.getInstance(getContext()).getUserCredentials().getDisplayName());
                                bundle.putString("profileImage", String.valueOf(PreferenceManager.getInstance(getContext()).getUserCredentials().getProfilePhoto()));
                                bundle.putString("phone", PreferenceManager.getInstance(getContext()).getUserCredentials().getPhone());
                                Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_homeFragment, bundle);
                                showPopupLicence = true;
                            }
                        }
                    } catch (Exception e) {
                        Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginFragment);
                        Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                try {
                    if (response.body().getStatus() == 400) {//means if Expired token and response is 400 the go to login  activity
                        getActivity().runOnUiThread(() -> PreferenceManager.getInstance(getContext()).deleteUserCredentials());
                        getActivity().runOnUiThread(() -> PreferenceManager.getInstance(getContext()).deleteUserPermission());
                        Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginFragment);
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                getActivity().runOnUiThread(() -> PreferenceManager.getInstance(getContext()).deleteUserCredentials());
                Navigation.findNavController(getView()).navigate(R.id.action_splashScreen_to_loginFragment);
            }
        });
    }

    private void applicationUpdateControl() {
        try {
            PackageInfo packageInfo = getContext().getPackageManager()
                    .getPackageInfo(getContext().getPackageName(), 0);
            String packageName = packageInfo.packageName;
            String versionName = packageInfo.versionName;


            RetrofitClient.getInstance().getApi().getVersionHistory(versionName)
                    .enqueue(new Callback<GetVersionResponse>() {
                        @Override
                        public void onResponse(Call<GetVersionResponse> call, Response<GetVersionResponse> response) {
                            try {
                                if (response.body() == null) {
                                    errorMessage(getActivity().getApplication(), "Something Wrong");
                                    return;
                                }
                                if (response.body().getStatus() == 400) {
                                    infoMessage(getActivity().getApplication(), "" + response.body().getMessage());
                                    return;
                                }

                                if (response.body().getStatus() == 200) {

                                    AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getContext());
                                    Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
                                    // Checks that the platform will allow the specified type of update.
                                    appUpdateInfoTask.addOnSuccessListener(result -> {

                                        if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                requestUpdate(result);
                                            if (response.body().getIsMaintanance() == 1) {
                                                MyApplication.updateAppDialog(getActivity(), response, packageName, versionName);
                                                return;
                                            }
                                            // force update
                                            if (response.body().getIsForceUpdate() == 1) {
                                                MyApplication.updateAppDialog(getActivity(), response, packageName, versionName);
                                                return;
                                            }


                                        }
                                    });





                                }
                            } catch (Exception e) {
                                Log.e("ERROR", e.getLocalizedMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<GetVersionResponse> call, Throwable t) {

                        }
                    });

            apiCallForDetectUser(getActivity());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
    }
}