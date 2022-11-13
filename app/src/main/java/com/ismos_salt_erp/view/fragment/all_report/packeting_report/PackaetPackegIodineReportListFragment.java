/*
package com.miserp.view.fragment.all_report.packeting_report;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miserp.R;
import com.miserp.adapter.IodineReportListAdapter;
import com.miserp.adapter.PackegingReportListAdapter;
import com.miserp.adapter.PackettingReportListAdapter;
import com.miserp.clickHandle.ToolbarClickHandle;
import com.miserp.databinding.FragmentPackaetPackegIodineReportListBinding;
import com.miserp.utils.ReportUtils;
import com.miserp.view.fragment.BaseFragment;
import com.miserp.view.fragment.all_report.view_model.IodineReportViewModel;
import com.miserp.view.fragment.all_report.iodine_used_report.list.IodineReportList;
import com.miserp.view.fragment.all_report.iodine_used_report.list.IodineReportListResponse;
import com.miserp.view.fragment.all_report.view_model.PackagingViewModel;
import com.miserp.view.fragment.all_report.packaging_report.list.PacketingReportListResponse;
import com.miserp.view.fragment.all_report.packaging_report.list.ReportPacketingList;
import com.miserp.view.fragment.all_report.view_model.PacketingReportViewModel;
import com.miserp.view.fragment.all_report.packeting_report.list.PackegingReportListResponse;
import com.miserp.view.fragment.all_report.packeting_report.list.ReportPackegingList;

import java.util.List;

public class PackaetPackegIodineReportListFragment extends BaseFragment {
    private FragmentPackaetPackegIodineReportListBinding binding;
    private PacketingReportViewModel packetingReportViewModel;
    private PackagingViewModel packagingViewModel;
    private IodineReportViewModel iodineReportViewModel;

    private String portion, startDate, endDate, referrerId, storeId, millerprofileId, selectAssociationId; //get previous fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_packaet_packeg_iodine_report_list, container, false);

        packetingReportViewModel = new ViewModelProvider(this).get(PacketingReportViewModel.class);
        packagingViewModel = new ViewModelProvider(this).get(PackagingViewModel.class);
        iodineReportViewModel = new ViewModelProvider(this).get(IodineReportViewModel.class);


        getPreviousFragmentData();
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });

        */
/** for Packaging Report *//*

        if (portion.equals(ReportUtils.PacketingReport)){
            binding.toolbar.toolbarTitle.setText("Packaging Report List");
            getPackegingReportListFromViewModel();
        }

        */
/** for packeting Report *//*

        if (portion.equals(ReportUtils.packagingReport)){
            binding.toolbar.toolbarTitle.setText("Packeting Report List");
            getPackettingReportData();
        }


 */
/** for packeting Report *//*

        if (portion.equals(ReportUtils.iodineUsedReport)){
            binding.toolbar.toolbarTitle.setText("Iodine Report List");
            getIodineReportList();
        }



        return binding.getRoot();
    }

    private void getIodineReportList() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        iodineReportViewModel.getIodineReportList(getActivity(),startDate,endDate,selectAssociationId,millerprofileId,storeId).observe(getViewLifecycleOwner(), new Observer<IodineReportListResponse>() {
            @Override
            public void onChanged(IodineReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getIodineReport() == null||response.getIodineReport().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        setIOdineDataInRv(response.getIodineReport());
                    }
            }}
        });
    }

    private void setIOdineDataInRv(List<IodineReportList> iodineReport) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        IodineReportListAdapter adapter = new IodineReportListAdapter(getActivity(), iodineReport);
        binding.recyclerView.setAdapter(adapter);
    }

    private void getPackettingReportData() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        packagingViewModel.getPacketingList(getActivity(),startDate,endDate,selectAssociationId,millerprofileId,storeId,referrerId).observe(getViewLifecycleOwner(), new Observer<PacketingReportListResponse>() {
            @Override
            public void onChanged(PacketingReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getPacketingList() == null||response.getPacketingList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        setPacketingDataInRv(response.getPacketingList());
                    }
            }}
        });

    }

    private void setPacketingDataInRv(List<ReportPacketingList> packetingList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        PackettingReportListAdapter adapter = new PackettingReportListAdapter(getActivity(), packetingList);
        binding.recyclerView.setAdapter(adapter);

    }

    private void getPackegingReportListFromViewModel() {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        packetingReportViewModel.getPacketingReportList(getActivity(),startDate,endDate,selectAssociationId,millerprofileId,storeId,referrerId).observe(getViewLifecycleOwner(), new Observer<PackegingReportListResponse>() {
            @Override
            public void onChanged(PackegingReportListResponse response) {
                progressDialog.dismiss();
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "something wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(requireActivity().getApplication(), response.getMessage());
                    return;
                }
                if (response.getStatus() == 200) {
                    if (response.getPackegingList() == null || response.getPackegingList().isEmpty()) {
                        binding.recyclerView.setVisibility(View.GONE);
                        binding.textView.setVisibility(View.VISIBLE);
                    } else {
                        setDataInRv(response.getPackegingList());
                    }
                }
            }
        });
    }

    private void setDataInRv(List<ReportPackegingList> packegingList) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        PackegingReportListAdapter adapter = new PackegingReportListAdapter(getActivity(), packegingList);
        binding.recyclerView.setAdapter(adapter);
    }

    private void getPreviousFragmentData() {
        assert getArguments() != null;
        startDate = getArguments().getString("startDate");
        endDate = getArguments().getString("endDate");
        portion = getArguments().getString("portion");
        millerprofileId = getArguments().getString("millerProfileId");
        selectAssociationId = getArguments().getString("selectAssociationId");
        storeId = getArguments().getString("storeId");
        referrerId = getArguments().getString("referrerId");
    }



}*/
