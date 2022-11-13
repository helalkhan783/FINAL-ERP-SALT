package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.IodizationEditedItemsAdapter;
import com.ismos_salt_erp.adapter.IodizationItemsAdapter;
import com.ismos_salt_erp.serverResponseModel.EditedIodizationDetailsResponse;
import com.ismos_salt_erp.viewModel.EditedIodizationDetailsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class IodizationEditDetailsFragment extends Fragment {
    private View view;
    private EditedIodizationDetailsViewModel editedIodizationDetailsViewModel;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.slNumberTv)
    TextView slNumberTv;
    @BindView(R.id.customerName)
    TextView customerName;
    @BindView(R.id.orderDate)
    TextView orderDate;
    @BindView(R.id.previousItemListRv)
    RecyclerView previousItemListRv;
    /**
     * now edited portion
     */

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

    /**
     * now final portion
     */

    @BindView(R.id.totalQuantity)
    TextView totalQuantity;
    @BindView(R.id.outputTv)
    TextView outputTv;
    @BindView(R.id.outputStoreTv)
    TextView outputStoreTv;

    @BindView(R.id.noteEditText)
    EditText noteEditText;


    EditedIodizationDetailsResponse data;
    String orderId;//store orderId from previous fragment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_iodization_edit_details, container, false);
        ButterKnife.bind(this, view);
        editedIodizationDetailsViewModel = ViewModelProviders.of(this).get(EditedIodizationDetailsViewModel.class);
        /**
         * get data from previous fragment
         */
        getDataFromPreviousFragment();
        /**
         * now get edited iodization details from server data from server
         */
        getEditedIodizationDetailsFromServer();
        return view;
    }

    private void getEditedIodizationDetailsFromServer() {
        editedIodizationDetailsViewModel.getEditedEditedIodizationDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        Toasty.error(getContext(), "Something Wrong", Toasty.LENGTH_LONG).show();
                        return;
                    }
                    data = response;
                    /**
                     * set previous
                     */
                    slNumberTv.setText(orderId);
                    customerName.setText("" + response.getCurrentOrder().getContactPerson());
                    orderDate.setText("" + response.getCurrentOrderDetails().getRequisitionDate());

                    /**
                     * now set previous item to recyclerview
                     */
                    IodizationItemsAdapter adapter = new IodizationItemsAdapter(getActivity(), response.getCurrentOrderDetails().getItems());
                    previousItemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    previousItemListRv.setHasFixedSize(true);
                    previousItemListRv.setAdapter(adapter);

                    /**
                     * now next current section
                     */
                    enterPrisenameTv.setText(response.getEditedOrder().getStoreName());
                    refferNameTv.setText(response.getEditedCustomer().getCustomerFname());
                    editedDateTv.setText(response.getEditedOrderDetails().getDate());
                    editedNote.setText(response.getEditedOrder().getNote());

                    /**
                     * now set edited item to recyclerview
                     */
                    IodizationEditedItemsAdapter itemsAdapter = new IodizationEditedItemsAdapter(getActivity(), response.getEditedOrderDetails().getItems());
                    editedItemListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                    editedItemListRv.setAdapter(itemsAdapter);

                    /**
                     * now set the final option
                     */
                    double total = 0;
                    for (int i = 0; i < response.getEditedOrderDetails().getItems().size(); i++) {
                        total += Double.parseDouble(response.getEditedOrderDetails().getItems().get(i).getQuantity()) * 1;
                    }
                    totalQuantity.setText(String.valueOf(total));
                    outputTv.setText(response.getEditedOrderDetails().getOutputItem());
                    outputStoreTv.setText(response.getOutput_store());
                });
    }

    private void getDataFromPreviousFragment() {
        toolbar.setText(getArguments().getString("pageName"));
        orderId = getArguments().getString("RefOrderId");
    }


    @OnClick(R.id.approveBtn)
    public void approveBtn() {
        String noteVal = noteEditText.getText().toString();
        if (noteVal.isEmpty()) {
            noteEditText.setError("Note Mandatory");
            noteEditText.requestFocus();
            return;
        }


        editedIodizationDetailsViewModel.approvePendingIodizationDetails(getActivity(), orderId, noteVal)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        Toasty.info(getContext(), "Approved", Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }else {
                        Toasty.info(getContext(), ""+response.getMessage(), Toasty.LENGTH_LONG).show();

                    }
                });
    }

    @OnClick(R.id.declineBtn)
    public void declineBtn() {
        String noteVal = noteEditText.getText().toString();
        if (noteVal.isEmpty()) {
            noteEditText.setError("Note Mandatory");
            noteEditText.requestFocus();
            return;
        }


        editedIodizationDetailsViewModel.declinePendingIodizationDetails(getActivity(), orderId, noteVal)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response.getStatus() == 200) {
                        Toasty.info(getContext(), "Declined", Toasty.LENGTH_LONG).show();
                        getActivity().onBackPressed();
                    }
                });
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        getActivity().onBackPressed();
    }
}