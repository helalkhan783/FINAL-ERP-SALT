package com.ismos_salt_erp.view.fragment.monitoring;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentMonitoringViewBinding;
import com.ismos_salt_erp.utils.FileDownloaderHelper;
import com.ismos_salt_erp.utils.RequestPermissionHandler;
import com.ismos_salt_erp.utils.RequestPermissionListener;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.MonitoringDetailsViewModel;

import java.util.ArrayList;
import java.util.List;


public class MonitoringViewFragment extends BaseFragment {
    FragmentMonitoringViewBinding binding;
    private MonitoringDetailsViewModel monitoringDetailsViewModel;
    private RequestPermissionHandler mRequestPermissionHandler;//for handle storage permission
    String slId, document;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monitoring_view, container, false);
        monitoringDetailsViewModel = new ViewModelProvider(this).get(MonitoringDetailsViewModel.class);
        mRequestPermissionHandler = new RequestPermissionHandler();
        binding.toolbar.setText("Monitoring Details");

        getPreviousFragmentData();

        getDataFromViewMOdel();

        binding.back.setOnClickListener(v -> getActivity().onBackPressed());

        binding.document.setOnClickListener(v -> {
            try {
                /**
                 * First get Permission
                 */
                handleButtonClicked();
                /**
                 * now download the file
                 */
                try {
                    FileDownloaderHelper.downloadFile(getActivity(), document);
                } catch (Exception e) {
                    Log.d("ERROR", "" + e.getMessage());
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error", e.getMessage());
            }
        });

        return binding.getRoot();
    }


    private void getPreviousFragmentData() {
        slId = getArguments().getString("slId");
    }


    private void getDataFromViewMOdel() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        monitoringDetailsViewModel.getMonitoringDetails(getActivity(), slId).observe(getViewLifecycleOwner(),
                monitoringViewResponse -> {
                    progressDialog.dismiss();

                    if (monitoringViewResponse == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (monitoringViewResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + monitoringViewResponse.getMessage());
                        return;
                    }


                    document = monitoringViewResponse.getMonitoringDetails().getDocument();


                    try {
                        if (monitoringViewResponse.getMonitoringDetails().getMonitoringSummery() != null) {
                            binding.monitoringSummery.setText(":  " + monitoringViewResponse.getMonitoringDetails().getMonitoringSummery());
                        }
                        if (monitoringViewResponse.getMonitoringDetails().getMonitoringDate() != null) {
                            binding.monitoringDate.setText(":  " + monitoringViewResponse.getMonitoringDetails().getMonitoringDate());
                        }

                        binding.published.setText(":  " + monitoringViewResponse.getMonitoringDetails().getPublishDate());

                        List<String> namelists = new ArrayList<>();
                        namelists.clear();

                        for (int i = 0; i < monitoringViewResponse.getZoneList().size(); i++) {
                            namelists.add(monitoringViewResponse.getZoneList().get(i).getZoneName());
                            if (monitoringViewResponse.getMonitoringDetails().getZoneID() != null) {
                                if (monitoringViewResponse.getZoneList().get(i).getZoneID().equals(monitoringViewResponse.getMonitoringDetails().getZoneID())) {
                                    binding.zone.setText(":  " + monitoringViewResponse.getZoneList().get(i).getZoneName());
                                }
                            }
                        }
                        if (monitoringViewResponse.getMonitoringDetails().getMonitoringType().equals("1")) {
                            binding.monitoringType.setText(":  QC/QA");
                        }
                        if (monitoringViewResponse.getMonitoringDetails().getMonitoringType().equals("2")) {
                            binding.monitoringType.setText(":  Licence");
                        }
                        if (monitoringViewResponse.getMonitoringDetails().getMonitoringType().equals("3")) {
                            binding.monitoringType.setText(":  Stock Availability");
                        }
                        if (monitoringViewResponse.getMonitoringDetails().getMonitoringType().equals("4")) {
                            binding.monitoringType.setText(":  Others");
                        }

                        List<String> millerNameList = new ArrayList<>();
                        for (int i = 0; i < monitoringViewResponse.getMiller().size(); i++) {
                            millerNameList.add(monitoringViewResponse.getMiller().get(i).getDisplayName());
                            if (monitoringViewResponse.getMonitoringDetails().getMillID().equals(monitoringViewResponse.getMiller().get(i).getSl())) {
                                binding.miller.setText(":   " + monitoringViewResponse.getMiller().get(i).getDisplayName());
                            }
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                });
    }

    private void handleButtonClicked() {
        mRequestPermissionHandler.requestPermission(getActivity(), new String[]{
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_EXTERNAL_STORAGE
        }, 123, new RequestPermissionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "request permission success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(getContext(), "request permission failed", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

}