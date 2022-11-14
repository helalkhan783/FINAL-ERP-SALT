package com.ismos_salt_erp.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.EditedPurchaseAdapter;
import com.ismos_salt_erp.adapter.EditedPurchaseEditAdapter;
import com.ismos_salt_erp.adapter.EditedSalePreviousAdapter;
import com.ismos_salt_erp.adapter.EditedSaleUpdateAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.CurrentOrderDetails;
import com.ismos_salt_erp.serverResponseModel.EditedOrderDetail;
import com.ismos_salt_erp.serverResponseModel.EditedPurchaseOrderResponse;
import com.ismos_salt_erp.serverResponseModel.Item;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.accounts.receiveDue.DueCollectionFragment;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.ApproveDeclinePurchaseEditViewModel;
import com.ismos_salt_erp.viewModel.EditSaleApproveDeclineViewModel;
import com.ismos_salt_erp.viewModel.EditSalesViewModel;
import com.ismos_salt_erp.viewModel.EditedPurchaseOrderViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class PurchaseEditDetails extends AddUpDel {
    private View view;
    private EditedPurchaseOrderViewModel editedPurchaseOrderViewModel;
    private ApproveDeclinePurchaseEditViewModel approvePurchaseEditViewModel;
    private EditSaleApproveDeclineViewModel editSaleApproveDeclineViewModel;
    private EditSalesViewModel editSalesViewModel;
    EditedPurchaseOrderResponse editedPurchaseOrderResponse;//for catch  the EditedPurchaseOrderResponse
    EditedPurchaseOrderResponse editedSaleOrderResponse;//for catch  the EditedSaleOrderResponse
    @BindView(R.id.toolbarTitle)
    TextView toolBar;
    @BindView(R.id.slNumber)
    TextView slNumber;
    @BindView(R.id.supplierName)
    TextView supplierName;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.orderDate)
    TextView orderDate;
    /*@BindView(R.id.orderTime)
    TextView orderTime;*/
    @BindView(R.id.alltotalAmount)
    TextView totalAmount;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.previousProductListRv)
    RecyclerView productListRv;


    //    now find the edit options
    @BindView(R.id.slNumberEdit)
    TextView slNumberEdit;
    @BindView(R.id.supplierNameEdit)
    TextView supplierNameEdit;
    @BindView(R.id.phoneEdit)
    TextView phoneEdit;
    @BindView(R.id.orderDateEdit)
    TextView orderDateEdit;
    @BindView(R.id.emailEdit)
    TextView emailEdit;
    @BindView(R.id.totalAmountEdit)
    TextView totalAmountEdit;
    @BindView(R.id.productListRvEdit)
    RecyclerView productListRvEdit;
    @BindView(R.id.approveDeclinedOptions)
    RelativeLayout approveDeclinedOptions;


    @BindView(R.id.NoteEt)
    EditText noteEt;

    String orderId, portion;//for store data from previous fragment
    private boolean approval;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_purchase_edit_details, container, false);
        ButterKnife.bind(this, view);
        editedPurchaseOrderViewModel = ViewModelProviders.of(this).get(EditedPurchaseOrderViewModel.class);
        approvePurchaseEditViewModel = ViewModelProviders.of(this).get(ApproveDeclinePurchaseEditViewModel.class);
        editSalesViewModel = ViewModelProviders.of(this).get(EditSalesViewModel.class);
        editSaleApproveDeclineViewModel = ViewModelProviders.of(this).get(EditSaleApproveDeclineViewModel.class);
        /**
         * get Data from previous fragment
         */
        getDataFromPreviousFragment();
        /**
         * now get Data from server and show in UI
         */

        if (portion.equals("EDIT_PURCHASE")) {//for approve or decline EDIT_PURCHASE
            toolBar.setText("Purchase Edit Details");
            /**
             * now check approval permission
             */
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1308)) {//here 1308 is a permission for approve and decline edited purchase
                approveDeclinedOptions.setVisibility(View.GONE);
            } else {
                approveDeclinedOptions.setVisibility(View.VISIBLE);
            }

            /**
             * now get purchase edit details from server
             */
            getPageDetailsFromServer();
        }

        if (portion.equals("EDIT_SALE")) {//for approve or decline EDIT_SALE
            toolBar.setText("Sale Edit Details");
            /**
             * now check approval permission
             */
            if (!PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserPermissions()).contains(1286)) {
                approveDeclinedOptions.setVisibility(View.GONE);
            } else {
                approveDeclinedOptions.setVisibility(View.VISIBLE);
            }
            /**
             * now get sale edit details from server
             */
            getPageDetailsFromServerForEditSale();
        }

        return view;
    }

    private void getPageDetailsFromServerForEditSale() {
        editSalesViewModel.getEditableSalesDetails(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    try {
                        editedSaleOrderResponse = response;//for store the edit sale response

                        /**
                         * first set data to previous order info UI
                         */
                        CurrentOrderDetails currentOrderDetails = response.getCurrentOrderDetails();//contain previous order data
                        slNumber.setText(":  #"+response.getCurrentOrder().getOrderSerial());
                        supplierName.setText(":  " + currentOrderDetails.getCustomer().getCompanyName() + "@" + currentOrderDetails.getCustomer().getCustomerFname());
                        phone.setText(":  " + currentOrderDetails.getCustomer().getPhone());
                        orderDate.setText(":  " + response.getCurrentOrderDetails().getRequisitionDate());
                        // orderTime.setText(response.getCurrentOrderDetails().get);
                        //email.setText(response.getCurrentOrderDetails().getE);//will implement
                        address.setText(":  " + currentOrderDetails.getCustomer().getAddress());


      /*              double countTotal = 0;
                    for (int i = 0; i < currentOrderDetails.getItems().size(); i++) {
                        countTotal += Double.parseDouble(currentOrderDetails.getItems().get(i).getSellingPrice()) * Double.parseDouble(currentOrderDetails.getItems().get(i).getQuantity());
                    }

                    totalAmount.setText(String.valueOf(countTotal));*/

                        /**
                         * now set previous productList to view
                         */
                        EditedSalePreviousAdapter adapter = new EditedSalePreviousAdapter(getActivity(), currentOrderDetails.getItems());
                        productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRv.setAdapter(adapter);


                        /**
                         *
                         * now set the updated purchase data from server
                         */
                        List<EditedOrderDetail> editedOrderDetails = response.getEditedOrderDetails();
                        /**
                         * set edited product list FOR Recycler view
                         */
                        EditedSaleUpdateAdapter adapter1 = new EditedSaleUpdateAdapter(getActivity(), editedOrderDetails, currentOrderDetails.getItems());

                        productListRvEdit.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRvEdit.setAdapter(adapter1);

                        slNumberEdit.setText(":  #"+response.getCurrentOrder().getOrderSerial());
                        if (response.getEditedCustomer() != null) {
                            supplierNameEdit.setText(":  " + response.getEditedCustomer().getCompanyName() + "@" + response.getEditedCustomer().getCustomerFname());
                            phoneEdit.setText(":  " + response.getEditedCustomer().getPhone());
                            orderDateEdit.setText(":  " + response.getEditedOrder().getOrderDate() + "" + response.getEditedOrder().getOrderTime());
                        }


//                    double total = 0;
//                    for (int i = 0; i < editedOrderDetails.size(); i++) {
//                        total += Double.parseDouble(editedOrderDetails.get(i).getSellingPrice()) * Double.parseDouble(editedOrderDetails.get(i).getQuantity());
//                    }
//                    totalAmountEdit.setText(String.valueOf(total));

                        /**
                         * now set color on updated property like (Supplier,Date)
                         */
                        String previousCustomerName = "" + currentOrderDetails.getCustomer().getCompanyName() + "@" + currentOrderDetails.getCustomer().getCustomerFname();
                        String currentCustomerName = "" + response.getEditedCustomer().getCompanyName() + "@" + response.getEditedCustomer().getCustomerFname();

                        if (!previousCustomerName.equals(currentCustomerName)) {
                            supplierNameEdit.setTextColor(getResources().getColor(R.color.successColor));
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", e.getMessage());
                    }

                });
    }

    @SuppressLint("SetTextI18n")
    private void getPageDetailsFromServer() {


        editedPurchaseOrderViewModel.getEditedPurchaseOrderResponse(getActivity(), orderId)
                .observe(getViewLifecycleOwner(), response -> {
                    try {

                        editedPurchaseOrderResponse = response;//for store the response
                        /**
                         * first set data to previous order info UI
                         */
                        CurrentOrderDetails currentOrderDetails = response.getCurrentOrderDetails();//contain previous order data
                        slNumber.setText(":  #"+response.getCurrentOrder().getOrderSerial());
                        supplierName.setText(":  "+currentOrderDetails.getCustomer().getCompanyName() + "@" + currentOrderDetails.getCustomer().getCustomerFname());
                        phone.setText(":  "+currentOrderDetails.getCustomer().getPhone());
                        orderDate.setText(":  "+response.getCurrentOrderDetails().getRequisitionDate());
                        // orderTime.setText(response.getCurrentOrderDetails().get);
                        //email.setText(response.getCurrentOrderDetails().getE);//will implement
                        address.setText(":  "+currentOrderDetails.getCustomer().getAddress());


                       /* double countTotal = 0;
                        for (int i = 0; i < currentOrderDetails.getItems().size(); i++) {
                            countTotal += Double.parseDouble(currentOrderDetails.getItems().get(i).getBuyingPrice()) * Double.parseDouble(currentOrderDetails.getItems().get(i).getQuantity());
                        }

                        totalAmount.setText(String.valueOf(countTotal));*/

                        /**
                         * now set previous product list to view
                         */
                        EditedPurchaseAdapter adapter = new EditedPurchaseAdapter(getActivity(), currentOrderDetails.getItems());
                        productListRv.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRv.setAdapter(adapter);

                        /**
                         *
                         * now set the updated purchase data from server
                         */
                        List<EditedOrderDetail> editedOrderDetails = response.getEditedOrderDetails();
                        /**
                         * set edited product list
                         */
                        EditedPurchaseEditAdapter adapter1 = new EditedPurchaseEditAdapter(getActivity(), editedOrderDetails, currentOrderDetails.getItems());

                        productListRvEdit.setLayoutManager(new LinearLayoutManager(getContext()));
                        productListRvEdit.setAdapter(adapter1);
                        slNumberEdit.setText(":  #"+response.getCurrentOrder().getOrderSerial());
                        if (response.getEditedCustomer() != null) {
                            supplierNameEdit.setText(":  " + response.getEditedCustomer().getCompanyName() + "@" + response.getEditedCustomer().getCustomerFname());
                            phoneEdit.setText(":  " + response.getEditedCustomer().getPhone());
                            orderDateEdit.setText(":  " + response.getEditedOrder().getOrderDate() + " " + response.getEditedOrder().getOrderTime());
                        }

                        String previousCustomerName = "" + currentOrderDetails.getCustomer().getCompanyName() + "@" + currentOrderDetails.getCustomer().getCustomerFname();
                        String currentCustomerName = "" + response.getEditedCustomer().getCompanyName() + "@" + response.getEditedCustomer().getCustomerFname();

                        if (!previousCustomerName.equals(currentCustomerName)) {
                            supplierNameEdit.setTextColor(getResources().getColor(R.color.successColor));
                        }
                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                });


    }

    private void getDataFromPreviousFragment() {
        orderId = getArguments().getString("RefOrderId");
        portion = getArguments().getString("portion");
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        DueCollectionFragment.HIDE_KEYBOARD(getActivity());//before back to previous fragment hide keyboard from screen
        getActivity().onBackPressed();
    }

    @OnClick(R.id.approveBtn)
    public void approvePurchaseEditBtn() {
        hideKeyboard(getActivity());
        if (noteEt.getText().toString().isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }
        approval = true;

        showDialog(getString(R.string.approve_dialog_title));
    }


    @OnClick(R.id.declineBtn)
    public void purchaseDeclineBtn() {
        hideKeyboard(getActivity());
        String noteVal = noteEt.getText().toString();
        if (noteVal.isEmpty()) {
            noteEt.setError("Note Mandatory");
            noteEt.requestFocus();
            return;
        }

        approval = false;

        showDialog(getString(R.string.decline_dialog_title));


    }

    @Override
    public void save() {
        if (approval == true) {
            if (portion.equals("EDIT_PURCHASE")) {
                List<String> previousQuantityList = new ArrayList<>();

                if (editedPurchaseOrderResponse == null) {
                    Toasty.success(getContext(), "You don't have any updated orders", Toasty.LENGTH_LONG).show();
                    return;
                }

                List<Item> previousItems = editedPurchaseOrderResponse.getCurrentOrderDetails().getItems();
                for (int i = 0; i < previousItems.size(); i++) {
                    previousQuantityList.add(previousItems.get(i).getQuantity());
                }

                approvePurchaseEditViewModel.approvePurchaseEdit(getActivity(), noteEt.getText().toString(), orderId, previousQuantityList)
                        .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                            if (duePaymentResponse.getStatus() == 200) {
                                Toasty.success(getActivity(), "Purchase Edit Approved", Toasty.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            }
                        });
            }


            if (portion.equals("EDIT_SALE")) {
                //will implement
                //Toast.makeText(getContext(), "Will implement", Toast.LENGTH_SHORT).show();
                if (editedSaleOrderResponse == null) {//if don't have any order right now
                    Toasty.success(getContext(), "You don't have any updated orders", Toasty.LENGTH_LONG).show();
                    return;
                }


                /**
                 * now get all previous quantity list from approve
                 */
                List<String> previousQuantityList = new ArrayList<>();//this is the previous quantity list for send server
                List<Item> previousItems = editedSaleOrderResponse.getCurrentOrderDetails().getItems();
                for (int i = 0; i < previousItems.size(); i++) {
                    previousQuantityList.add(previousItems.get(i).getQuantity());
                }

                editSaleApproveDeclineViewModel.approveEditSale(getActivity(), orderId, noteEt.getText().toString(), previousQuantityList)
                        .observe(getViewLifecycleOwner(), duePaymentResponse -> {
                            Toasty.success(getActivity(), "Sale Edit Approved", Toasty.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                        });
            }
        } else {
            if (portion.equals("EDIT_PURCHASE")) {
                approvePurchaseEditViewModel.declinePurchaseEdit(getActivity(), orderId, noteEt.getText().toString())
                        .observe(getViewLifecycleOwner(), response -> {
                            if (response.getStatus() == 200) {
                                Toasty.success(getActivity(), "Purchase Edit Declined", Toasty.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            }
                        });
            }
            if (portion.equals("EDIT_SALE")) {

                //will implement
                //Toast.makeText(getContext(), "Will implement", Toast.LENGTH_SHORT).show();
                editSaleApproveDeclineViewModel.declineEditSale(getActivity(), orderId, noteEt.getText().toString())
                        .observe(getViewLifecycleOwner(), response -> {
                            if (response.getStatus() == 200) {
                                Toasty.success(getActivity(), "Sale Edit Declined", Toasty.LENGTH_LONG).show();
                                getActivity().onBackPressed();
                            }
                        });
            }

        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}