package com.ismos_salt_erp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.WashingCrushingEditedItemAdapter;
import com.ismos_salt_erp.adapter.WashingCrushingItemAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.EditedItemsResponse;
import com.ismos_salt_erp.serverResponseModel.EditedWashingCrushingDetailsResponse;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.EditedWashingAndCurshingViewmodel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditedWashingAndCrushingDetails extends AddUpDel {
    private View view;
    private EditedWashingAndCurshingViewmodel editedWashingAndCurshingViewmodel;
    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.slNumber)
    TextView slNumber;
    @BindView(R.id.customerName)
    TextView customerName;
    @BindView(R.id.orderDate)
    TextView orderDate;
    @BindView(R.id.previousItemListRv)
    RecyclerView previousItemListRv;

    //now find edited portion

    @BindView(R.id.enterPrisenameTv)
    TextView enterPrisenameTv;
    @BindView(R.id.refferNameTv)
    TextView refferNameTv;
    @BindView(R.id.editedDateTv)
    TextView editedDateTv;
    @BindView(R.id.editedNote)
    TextView editedNote;
    @BindView(R.id.editedItemListRv)
    RecyclerView editedItemListRv;


    // now final step
    @BindView(R.id.totalQuantity)
    TextView totalQuantity;
    @BindView(R.id.outputTv)
    TextView outputTv;
    @BindView(R.id.outputStoreTv)
    TextView outputStoreTv;

    @BindView(R.id.noteEText)
    EditText note;

    @BindView(R.id.pendingView)
    LinearLayout pendingView;

    String orderId,status;
    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.edited_washing_crushing_details, container, false);
        ButterKnife.bind(this, view);
        editedWashingAndCurshingViewmodel = ViewModelProviders.of(this).get(EditedWashingAndCurshingViewmodel.class);
        getDataFromPreviousFragment();
        /**
         * now get info from server
         */
        getDataFromServer();
        return view;
    }


    private void getDataFromServer() {
        editedWashingAndCurshingViewmodel.getEditedWashingAndCrushingDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        /**
                         * first set data to previous view
                         */
                        setDatatoPreviousView(response);
                        /**
                         * after that set data to edited view
                         */
                        setDataToEditedView(response);
                        /**
                         * now set the final portion as like web version
                         */
                        setFinalPortion(response);
                    }

                    else {
                        errorMes("");
                        getActivity().onBackPressed();
                    }
                });
    }

    private void setFinalPortion(EditedWashingCrushingDetailsResponse response) {
        List<EditedItemsResponse> editedItem = response.getEditedOrderDetails().getItems();
        int quantityTotal = 0;
        for (int i = 0; i < editedItem.size(); i++) {
            quantityTotal += Integer.parseInt(editedItem.get(i).getQuantity());
        }
        totalQuantity.setText(String.valueOf(quantityTotal));
        outputTv.setText(response.getEditedOrderDetails().getOutputItem());
        outputStoreTv.setText(response.getEditedOrderDetails().getItems().get(0).getStore());

    }

    private void setDataToEditedView(EditedWashingCrushingDetailsResponse response) {
        // enterPrisenameTv.setText();
        refferNameTv.setText(response.getEditedCustomer().getCustomerFname());
        editedDateTv.setText(response.getEditedOrderDetails().getDate());
        editedNote.setText(response.getEditedOrder().getNote());
        /**
         * now set edited item to recyclerview
         */
        WashingCrushingEditedItemAdapter adapter = new WashingCrushingEditedItemAdapter(getActivity(), response.getEditedOrderDetails().getItems());
        editedItemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        editedItemListRv.setHasFixedSize(true);
        editedItemListRv.setAdapter(adapter);
    }

    private void setDatatoPreviousView(EditedWashingCrushingDetailsResponse response) {
        slNumber.setText("#" + orderId);
        customerName.setText(response.getCurrentOrderDetails().getCustomer().getCustomerFname()
                + "@" + response.getCurrentOrderDetails().getCustomer().getPhone());
        orderDate.setText(response.getCurrentOrderDetails().getRequisitionDate());

        enterPrisenameTv.setText(response.getCurrentOrder().getStoreName());

        WashingCrushingItemAdapter adapter = new WashingCrushingItemAdapter(getActivity(), response.getCurrentOrderDetails().getItems());
        previousItemListRv.setHasFixedSize(true);
        previousItemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        previousItemListRv.setAdapter(adapter);


    }

    private void getDataFromPreviousFragment() {
        toolBar.setText(getArguments().getString("pageName"));
        orderId = getArguments().getString("RefOrderId");
        status = getArguments().getString("status");
        if (status.equals("2") && PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserPermissions()).contains(1430)){
            pendingView.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();

    }


    @OnClick(R.id.approveBtn)
    public void approveEditedDetails() {
         if (note.getText().toString().isEmpty()) {
            note.setError("Note Mandatory");
            note.requestFocus();
            return;
        }
        approval = true;
        showDialog(getString(R.string.approve_dialog_title));
    }

    @OnClick(R.id.declineBtn)
    public void declineEditedDetails() {


        if (note.getText().toString().isEmpty()) {
            note.setError("Note Mandatory");
            note.requestFocus();
            return;
        }
        approval = false;
        showDialog(getString(R.string.decline_dialog_title));


    }

    @Override
    public void save() {
        if (approval == true) {
                appsovrConfirm();
            } else {
                decline();
            }

    }

    private void decline() {
        editedWashingAndCurshingViewmodel.declineEditedWashingCrushing(getActivity(), orderId, note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        message("" + response.getMessage());
                        getActivity().onBackPressed();
                    }
                    else {
                        errorMes(""+response.getMessage() == null ?  "" : response.getMessage());
                    }
                });
    }

    private void appsovrConfirm() {
        editedWashingAndCurshingViewmodel.approveEditedWashingCrushing(getActivity(), orderId, note.getText().toString())
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        message("" + response.getMessage());
                        getActivity().onBackPressed();
                    }
                    else {
                        errorMes(""+response.getMessage() == null ?  "" : response.getMessage());
                    }
                });
    }

    @Override
    public void imageUri(Intent uri) {

    }
}