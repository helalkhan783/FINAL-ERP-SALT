package com.ismos_salt_erp.view.fragment.salesRequisition;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.SalesRequisitionDetailsProductsAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.SingleRequisitionDetailsResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.PendingRequisitionApproveDeclineViewModel;
import com.ismos_salt_erp.viewModel.SalesRequisitionListViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingleSalesRequisitionDetails extends BaseFragment {
    private SalesRequisitionListViewModel salesRequisitionListViewModel;
    private PendingRequisitionApproveDeclineViewModel pendingRequisitionApproveDeclineViewModel;
    private View view;
    @BindView(R.id.customerNameEt)
    TextView customerNameEt;
    @BindView(R.id.companyNameTv)
    TextView companyNameTv;
    @BindView(R.id.phoneEt)
    TextView phoneEt;
    @BindView(R.id.paymentType)
    TextView paymentType;
    @BindView(R.id.addressTv)
    TextView addressTv;
    @BindView(R.id.startDateTv)
    TextView startDateTv;
    @BindView(R.id.endDateTv)
    TextView endDateTv;
    @BindView(R.id.requisitionNumberTv)
    TextView requisitionNumberTv;
    @BindView(R.id.totalAmountTvMain)
    TextView totalAmountTv;
    @BindView(R.id.discountTv)
    TextView discountTv;
    @BindView(R.id.collectedAmountTv)
    TextView collectedAmountTv;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.productListRv)
    RecyclerView productListRv;


    private LinearLayout pendingView;
    private Button approveBtn;
    private Button declineBtn;
    LinearLayout noteView;
    @BindView(R.id.NoteEt)
    EditText noteEt;

    /**
     * for get data from previous fragment
     */
    String selectedRequisitionId;
    boolean isPendingDetails = false;
    SingleRequisitionDetailsResponse response;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.single_sales_requisition, container, false);
        ButterKnife.bind(this, view);
        salesRequisitionListViewModel = ViewModelProviders.of(this).get(SalesRequisitionListViewModel.class);
        pendingRequisitionApproveDeclineViewModel = ViewModelProviders.of(this).get(PendingRequisitionApproveDeclineViewModel.class);
        intit();
        getDataFromPreviousFragment();
        /**
         * load requisition details in recyclerview
         */
        loadDetailsInRv();
        /**
         * if this fragment come from pending requisition fragment then  approve and decline btn should be VISIBLE
         */
        if (isPendingDetails) {
            pendingView.setVisibility(View.VISIBLE);
            noteView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void intit() {
        pendingView = (LinearLayout) view.findViewById(R.id.pendingView);
        approveBtn = view.findViewById(R.id.approveBtn);
        declineBtn = view.findViewById(R.id.declineBtn);
        noteView = view.findViewById(R.id.noteView);
    }


    private void loadDetailsInRv() {
        salesRequisitionListViewModel.getSingleRequisitionDetails(getActivity(), selectedRequisitionId).observe(getViewLifecycleOwner(), singleDetails -> {
         if (singleDetails == null ){
             errorMessage(getActivity().getApplication(), "Something wrong");
             return;
         }

         if (singleDetails.getStatus() == 400){
             infoMessage(getActivity().getApplication(), singleDetails.getMessage());
             getActivity().onBackPressed();
             return;
         }

            response = singleDetails.getDetails();//will access this data for show main this fragment
             productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
            SalesRequisitionDetailsProductsAdapter adapter = new SalesRequisitionDetailsProductsAdapter(getActivity(), response.getItems());
            productListRv.setAdapter(adapter);
            /**
             * load data to view
             */
            loadDataToView();
        });
    }

    private void loadDataToView() {
        customerNameEt.setText(":  "+response.getCustomer().getCustomerFname());
        companyNameTv.setText(":  "+response.getCustomer().getCompanyName());
        phoneEt.setText(":  "+response.getCustomer().getPhone());
        paymentType.setText(":  "+response.getPaymentType());
        addressTv.setText(":  "+response.getCustomer().getAddress());
        startDateTv.setText(":  "+response.getRequisitionDate());
        endDateTv.setText(":  "+response.getRequisitionEndDate());
        requisitionNumberTv.setText(":  #" + selectedRequisitionId);
        totalAmountTv.setText(":  "+ DataModify.addFourDigit(String.valueOf(response.getTotalAmount())) + MtUtils.priceUnit);
        discountTv.setText(":  "+ String.valueOf(response.getDiscount()) + MtUtils.priceUnit);
        collectedAmountTv.setText(":  "+DataModify.addFourDigit(String.valueOf(response.getCollected())) +  MtUtils.priceUnit);
    }

    private void getDataFromPreviousFragment() {
        toolbar.setText("Requisition Details");
        selectedRequisitionId = getArguments().getString("id");
        //isPendingDetails = getArguments().getBoolean("isPending");//for confirm this page come from (Pending requisition list)
        //PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1500)
        if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1306)){
            isPendingDetails=true;
        }
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.approveBtn)
    public void approveBtn() {
        String note = noteEt.getText().toString();
        if (note.isEmpty()) {
            noteEt.setError("Note mandatory");
            noteEt.requestFocus();
            return;
        }
        if (!(isInternetOn(getActivity()))) {
            hideKeyboard(getActivity());
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage("Do you want to approve ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {

                    //selectedRequisitionId //this is the current order id
                    pendingRequisitionApproveDeclineViewModel.sendRequisitionApprovedRequest(getActivity(), selectedRequisitionId, note)
                            .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                                successMessage(getActivity().getApplication(), duePaymentResponse.getMessage());
                                getActivity().onBackPressed();
                            });
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();

    }

    @OnClick(R.id.declineBtn)
    public void declineBtn() {
        String note = noteEt.getText().toString();

        if (note.isEmpty()) {
            noteEt.setError("Note mandatory");
            noteEt.requestFocus();
            return;
        }
        if (!(isInternetOn(getActivity()))) {
            hideKeyboard(getActivity());
            infoMessage(getActivity().getApplication(), "Please check your internet connection");
            return;
        }


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage("Do you want to decline ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                (dialog, which) -> {
                    //selectedRequisitionId //this is the current order id
                    pendingRequisitionApproveDeclineViewModel.sendRequisitionDeclineRequest(getActivity(), selectedRequisitionId, note)
                            .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                                successMessage(getActivity().getApplication(), duePaymentResponse.getMessage());
                                getActivity().onBackPressed();
                            });
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }


}