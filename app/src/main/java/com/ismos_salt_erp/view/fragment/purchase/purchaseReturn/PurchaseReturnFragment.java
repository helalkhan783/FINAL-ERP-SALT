package com.ismos_salt_erp.view.fragment.purchase.purchaseReturn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.PurchaseReturnAdapter;
import com.ismos_salt_erp.databinding.FragmentPurchaseReturnBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.GetPurchaseReturnResponse;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.view.fragment.customers.AddUpDel;
import com.ismos_salt_erp.viewModel.PurchaseReturnViewModel;

import java.util.ArrayList;
import java.util.List;


public class PurchaseReturnFragment extends AddUpDel implements PurchaseReturnItemClick {
    public static FragmentPurchaseReturnBinding binding;
    private PurchaseReturnViewModel purchaseReturnViewModel;
    private MyDatabaseHelper myDatabaseHelper;
    private String NO_DATA_FOUND = "No Data Found";
    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    /**
     * For items
     */
    private GetPurchaseReturnResponse currentResponse;
    private List<String> itemNameList;

    private ArrayAdapter<String> itemArrayAdapter;
    private ArrayList<SalesRequisitionItems> itemsList;
    private PurchaseReturnAdapter adapter;


    private static boolean finalTotalCount = false;
    private boolean allOk = true;
    /**
     * updated quantity product list (LAST QUANTITY UPDATED ALL PRODUCT LIST)
     */
    public static List<SalesRequisitionItems> updatedQuantityProductList = new ArrayList<>();
    public static List<String> updatedQuantityList = new ArrayList<>();
    List<String> currentQuantity;
    PopupWindow mypopupWindow;
    public static String discountPercent = "2";

    private boolean approval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase_return, container, false);
        purchaseReturnViewModel = new ViewModelProvider(this).get(PurchaseReturnViewModel.class);
        myDatabaseHelper = new MyDatabaseHelper(getContext());

        binding.toolbar.toolbarTitle.setText("Purchase Return");
        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            getActivity().onBackPressed();
        });
        /**
         * After Search
         */
        binding.search.setOnClickListener(v -> {

            if (binding.itemSearchEt.getText().toString().isEmpty()) {
                return;
            }

            getProductBySearchKey(binding.itemSearchEt.getText().toString());

            binding.layoutAccess.subTotal.setText("");
            binding.layoutAccess.compensetion.setText("");
            binding.layoutAccess.returnAmount.setText("");
            binding.layoutAccess.returnDue.setText("");
            binding.layoutAccess.paidAmount.setText("");
        });

        /**
         * For Submit orders
         */
        binding.layoutAccess.submit.setOnClickListener(v -> {

            if (currentResponse == null) {
                infoMessage(getActivity().getApplication(), "Select Order First");
                return;
            }
            hideKeyboard(getActivity());
            approval = true;

            showDialog(getString(R.string.return_dialog_title));
        });
        /**
         * For cancel whole order
         */
        binding.layoutAccess.cancelOrders.setOnClickListener(v -> {

            if (currentResponse == null) {
                infoMessage(getActivity().getApplication(), "Select Order First");
                return;
            }

            String orderId = currentResponse.getOrder().getOrderID();
            if (orderId == null) {
                infoMessage(getActivity().getApplication(), "select Orders");
                return;
            }
            approval = false;
            showDialog(getString(R.string.order_cance_dialog_title));

        });

        binding.layoutAccess.percentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setPopUpWindow();
                mypopupWindow.showAsDropDown(binding.layoutAccess.percentCard, 0, 0);
            }
        });
        binding.layoutAccess.compensetion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                discountCalculation();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.layoutAccess.paidAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String paidAmoount = binding.layoutAccess.paidAmount.getText().toString();
                String returnAmount = binding.layoutAccess.returnAmount.getText().toString();

                if (paidAmoount.isEmpty()) {
                    paidAmoount = "0";
                }
                if (returnAmount.isEmpty()) {
                    returnAmount = "0";
                }

                double amount = 0.0;
                amount = Double.parseDouble(returnAmount) - Double.parseDouble(paidAmoount);

                binding.layoutAccess.returnDue.setText("" + amount);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return binding.getRoot();
    }

    private void setPopUpWindow() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_popup_layout_one, null);

        mypopupWindow = new PopupWindow(view, 420, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        LinearLayout percent, fixed;
        TextView percentTv, fixedTv;
        percent = (LinearLayout) view.findViewById(R.id.percent);
        fixed = (LinearLayout) view.findViewById(R.id.fixed);
        percentTv = (TextView) view.findViewById(R.id.percentTv);
        fixedTv = (TextView) view.findViewById(R.id.fixedTv);

        if (discountPercent.equals("2")) {
            percentTv.setTextColor(Color.parseColor("#9964AE"));
        }
        if (discountPercent.equals("1")) {
            fixedTv.setTextColor(Color.parseColor("#9964AE"));
        }


        percent.setOnClickListener(v -> {

            discountPercent = "2";
            binding.layoutAccess.discountSelectorTv.setText("%");

            discountCalculation();
            mypopupWindow.dismiss();
        });

        fixed.setOnClickListener(v -> {

            discountPercent = "1";
            binding.layoutAccess.discountSelectorTv.setText("Fixed");

            discountCalculation();
            mypopupWindow.dismiss();
        });


    }

    private void discountCalculation() {
        double discount = 0, total = 0, totalPrice1 = 0;
        String subTotal = binding.layoutAccess.subTotal.getText().toString();
        String dis = binding.layoutAccess.compensetion.getText().toString();
        String paidAmount = binding.layoutAccess.paidAmount.getText().toString();
        if (subTotal.isEmpty()) {
            subTotal = "0";
        }
        total = Double.parseDouble(subTotal);

        if (dis.isEmpty()) {
            dis = "0";
        }
        if (paidAmount.isEmpty()) {
            paidAmount = "0";
        }
        discount = Double.parseDouble(dis);

        if (discountPercent.equals("1")) {
            totalPrice1 = total - discount;
        }
        if (discountPercent.equals("2")) {
            discount = total * discount / 100;
            totalPrice1 = total - discount;

        }

        binding.layoutAccess.returnAmount.setText("" + totalPrice1);
        binding.layoutAccess.returnDue.setText("" + totalPrice1);

        String returnAmount = binding.layoutAccess.returnAmount.getText().toString();
        if (returnAmount.isEmpty()) {
            returnAmount = "0";
        }

        double amount = 0.0;
        amount = Double.parseDouble(returnAmount) - Double.parseDouble(paidAmount);

        binding.layoutAccess.returnDue.setText("" + amount);
    }

    private void setSubTotal(Double toatl) {
        binding.layoutAccess.subTotal.setText("" + toatl);
    }

    private void cancelWholePurchaseOrders() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        purchaseReturnViewModel.purchaseWholeOrderCancel(getActivity(), currentResponse.getOrder().getOrderID())
                .observe(getViewLifecycleOwner(), response -> {
                    progressDialog.dismiss();
                    try {
                        if (response == null) {
                            errorMessage(getActivity().getApplication(), "Something Wrong");
                            return;
                        }
                        if (response.getStatus() == 400) {
                            infoMessage(getActivity().getApplication(), "" + response.getMessage());
                            return;
                        }
                        successMessage(getActivity().getApplication(), "" + response.getMessage());
                        getActivity().onBackPressed();
                    } catch (Exception e) {
                        Log.d("ERROR", "error");
                    }
                });

    }

    private void getProductBySearchKey(String currentText) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check your Internet Connection");
            return;
        }
        if (currentText.isEmpty()) {
            return;
        }
        hideKeyboard(getActivity());

        purchaseReturnViewModel.getPurchaseReturnPageData(getActivity(), currentText)
                .observe(getViewLifecycleOwner(), response -> {
                    if (response == null) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (response.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), "" + response.getMessage());
                        binding.rvLayout.setVisibility(View.GONE);
                        return;
                    }
                    binding.rvLayout.setVisibility(View.VISIBLE);
                    currentResponse = response;

                    if (response.getHas_returned_item() == true) {
                        binding.layoutAccess.cancelOrders.setVisibility(View.GONE);
                    }
                    if (!response.getHas_returned_item() == true) {
                        binding.layoutAccess.cancelOrders.setVisibility(View.VISIBLE);
                    }

                    binding.rvLayout.setVisibility(View.VISIBLE);


                    adapter = new PurchaseReturnAdapter(getActivity(), response.getOrderDetails().getItems(), PurchaseReturnFragment.this);
                    binding.productList.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.productList.setAdapter(adapter);
                    /**
                     * now set Current quantity
                     */
                    currentQuantity = new ArrayList<>();
                    currentQuantity.clear();
                    for (int i = 0; i < response.getOrderDetails().getItems().size(); i++) {
                        currentQuantity.add("0");
                    }

                });


    }

    @Override
    public void insertQuantity(int position, String quantity) {
        try {

            double total = 0.0;
            for (int i = 0; i < currentResponse.getOrderDetails().getItems().size(); i++) {
                TextView tv = (TextView) binding.productList.getLayoutManager().findViewByPosition(i).findViewById(R.id.subTotal);
                String toatl = tv.getText().toString();
                if (toatl.isEmpty()) {
                    toatl = "0";
                }
                total = total + Double.parseDouble(toatl);
            }

            setSubTotal(total);
            discountCalculation();

            String currentMainQuantity = quantity;
            if (quantity.isEmpty()) {
                currentMainQuantity = "0";
            }
            currentQuantity.remove(position);
            currentQuantity.add(position, currentMainQuantity);

        } catch (Exception e) {
            Log.d("ERROR", "" + e.getMessage());
        }
    }


    private void submit() {


        List<String> orderDetailsIdList = new ArrayList<>();
        List<Integer> productIdList = new ArrayList<>();
        List<String> buyingPriceList = new ArrayList<>();
        List<String> productTitleList = new ArrayList<>();
        List<String> productUnitList = new ArrayList<>();
        List<String> soldFromList = new ArrayList<>();
        List<String> storeIds = new ArrayList<>();
        orderDetailsIdList.clear();

        productIdList.clear();
        buyingPriceList.clear();
        productTitleList.clear();
        productUnitList.clear();
        soldFromList.clear();
        storeIds.clear();
        Double currentTotal = 0.0, currentTotal1 = 0.0;
        try {
            for (int i = 0; i < currentResponse.getOrderDetails().getItems().size(); i++) {
                try {
                    storeIds.add(currentResponse.getOrderDetails().getItems().get(i).getStoreID());
                    orderDetailsIdList.add(currentResponse.getOrderDetails().getItems().get(i).getOrder_detailsID());
                    productIdList.add(Integer.parseInt(currentResponse.getOrderDetails().getItems().get(i).getProductID()));
                    buyingPriceList.add(currentResponse.getOrderDetails().getItems().get(i).getBuyingPrice());
                    productTitleList.add(currentResponse.getOrderDetails().getItems().get(i).getItem());
                    productUnitList.add(currentResponse.getOrderDetails().getItems().get(i).getUnitID());
                    soldFromList.add(currentResponse.getOrderDetails().getItems().get(i).getSoldFrom());
                    Double total = Double.parseDouble(currentResponse.getOrderDetails().getItems().get(i).getQuantity()) * Double.parseDouble(currentResponse.getOrderDetails().getItems().get(i).getBuyingPrice());
                    currentTotal += total;

                    Double total1 = Double.parseDouble(currentQuantity.get(i)) * Double.parseDouble(currentResponse.getOrderDetails().getItems().get(i).getBuyingPrice());
                    currentTotal1 += total1;
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            Log.d("ERROR", "ERROR");

        }
        if (discountPercent.equals("1")) {
            discountPercent = "2";
        }
        if (discountPercent.equals("2")) {
            discountPercent = "1";
        }

        double paidAmount = Double.parseDouble(currentResponse.getPaymentInfo().getPaidAmount()) - currentTotal1;
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        purchaseReturnViewModel.submitPurchaseReturn(
                getActivity(), currentResponse.getOrder().getOrderID(),
                String.valueOf(currentTotal), String.valueOf(paidAmount), orderDetailsIdList,
                productIdList, currentQuantity, buyingPriceList, productTitleList, productUnitList, soldFromList,
                storeIds,
                binding.layoutAccess.subTotal.getText().toString(),
                binding.layoutAccess.compensetion.getText().toString(),
                discountPercent,
                binding.layoutAccess.returnAmount.getText().toString(),
                binding.layoutAccess.paidAmount.getText().toString()
        ).observe(getViewLifecycleOwner(), response -> {
            progressDialog.dismiss();
            try {
                if (response == null) {
                    errorMessage(getActivity().getApplication(), "Something Wrong");
                    return;
                }
                if (response.getStatus() == 400) {
                    infoMessage(getActivity().getApplication(), "" + response.getMessage());
                    return;
                }
                successMessage(getActivity().getApplication(), "" + response.getMessage());
                getActivity().onBackPressed();
            } catch (Exception e) {
                Log.d("ERROR", "ERROR");
            }
        });
    }


    @Override
    public void save() {
        if (approval == true) {
            submit();
        } else {
            cancelWholePurchaseOrders();
        }
    }

    @Override
    public void imageUri(Intent uri) {

    }
}