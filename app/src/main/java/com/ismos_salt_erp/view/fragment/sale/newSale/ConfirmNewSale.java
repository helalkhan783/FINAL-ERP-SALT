package com.ismos_salt_erp.view.fragment.sale.newSale;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.custom_confirm_sale_adapter.ConfirmSaleAdapterOne;
import com.ismos_salt_erp.databinding.FragmentConfirmNewSaleBinding;
import com.ismos_salt_erp.localDatabase.MyDatabaseHelper;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItems;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class ConfirmNewSale extends BaseFragment implements ItemClickOne {
    public static FragmentConfirmNewSaleBinding binding;
    PopupWindow mypopupWindow;
    private MyDatabaseHelper myDatabaseHelper;


    double totalPrice = 0, discount = 0;

    Cursor cursor;

    public static String discountPercent = "2";
    ConfirmSaleAdapterOne adapter;

    private boolean isOk = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_confirm_new_sale, container, false);
        binding.toolbar.toolbarTitle.setText("Add New Sale");
        myDatabaseHelper = new MyDatabaseHelper(getContext());
        myDatabaseHelper = new MyDatabaseHelper(getContext());


        /**
         * show quantity updated product list
         */
        showSelectedDataToRecyclerView();

        binding.toolbar.setClickHandle(() -> {
            hideKeyboard(getActivity());
            Navigation.findNavController(getView()).popBackStack();
        });


        binding.discountAndRemarksBox.percentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageImage();
                setPopUpWindow();
                mypopupWindow.showAsDropDown(binding.discountAndRemarksBox.percentCard, 0, 0);
            }
        });


        discountCalculation(discountPercent);


        getLocalData();


        binding.discountAndRemarksBox.discountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // binding.discountAndRemarksBox.paidAmount.setText("");


                discountCalculation(discountPercent);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.discountAndRemarksBox.carringCostEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // binding.discountAndRemarksBox.paidAmount.setText("");
                discountCalculation(discountPercent);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.discountAndRemarksBox.vatEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // binding.discountAndRemarksBox.paidAmount.setText("");

                discountCalculation(discountPercent);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.discountAndRemarksBox.confirmPaymetBtn.setOnClickListener((View.OnClickListener) v -> {
            Bundle bundle = new Bundle();

            bundle.putString("selectedEnterpriseId", getArguments().getString("selectedEnterpriseId"));
            bundle.putString("selectedStoreId", getArguments().getString("selectedStoreId"));
            bundle.putString("grandTotal", binding.discountAndRemarksBox.grandTotal.getText().toString().trim());
            bundle.putString("vat", binding.discountAndRemarksBox.vatEt.getText().toString().trim());
            bundle.putString("carringCost", binding.discountAndRemarksBox.carringCostTv.getText().toString().trim());


            Navigation.findNavController(getView()).navigate(R.id.action_confirmNewSale_to_millerEmployeeInformation, bundle);
        });

        return binding.getRoot();
    }

    private void manageImage() {
        if (!isOk) {
            binding.discountAndRemarksBox.percentCard.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
            isOk = true;
        } else {
            binding.discountAndRemarksBox.percentCard.setImageResource(R.drawable.baseline_arrow_drop_up_24);
            isOk = false;
        }
    }

    private void calculateGrandTotal(double subTotal) {
        double vatPercent = 0.0000;
        String carringCost = binding.discountAndRemarksBox.carringCostEt.getText().toString().trim();
        String vatCost = binding.discountAndRemarksBox.vatEt.getText().toString().trim();
        String dis = binding.discountAndRemarksBox.totalDiscountTv.getText().toString().trim();
        if (dis.isEmpty()) {
            dis = "0";
        }
        if (vatCost.isEmpty()) {
            vatCost = "0.0000";
        }
// vat count as percent of total
        double vat = subTotal - Double.parseDouble(dis);

        vatPercent = vat * Double.parseDouble(vatCost) / 100;

        if (carringCost.isEmpty()) {
            carringCost = "0.0000";
        }

        double totalExtraCost = 0.0, grandTotalWithCarringCost = 0.0;
        String total = binding.discountAndRemarksBox.grandTotal.getText().toString().trim();
        totalExtraCost = Double.parseDouble(carringCost) + vatPercent;
        grandTotalWithCarringCost = Double.parseDouble(total) + totalExtraCost;
        binding.discountAndRemarksBox.grandTotal.setText("" + DataModify.addFourDigit(String.valueOf(grandTotalWithCarringCost)));
        binding.discountAndRemarksBox.vatTv.setText("" + DataModify.addFourDigit(String.valueOf(vatPercent)));
        binding.discountAndRemarksBox.carringCostTv.setText("" + DataModify.addFourDigit(String.valueOf(carringCost)));


    }


    public void discountCalculation(String discountType) {

        try {
            double discount = 0, total = 0, totalPrice1 = 0;
            String discountString = binding.discountAndRemarksBox.discountEt.getText().toString().trim();
            if (discountString.isEmpty()) {
                discountString = "0.0";
            }
            discount = Double.parseDouble(discountString);

            total = Double.parseDouble(binding.discountAndRemarksBox.total.getText().toString());

            if (discountType.equals("1")) {
                totalPrice1 = total - discount;
            }
            if (discountType.equals("2")) {
                discount = total * discount / 100;
                totalPrice1 = total - discount;

            }
            if (total < discount) {
                binding.discountAndRemarksBox.discountEt.setError("Invalid discount");
                binding.discountAndRemarksBox.discountEt.requestFocus();
                return;
            }
            binding.discountAndRemarksBox.grandTotal.setText(DataModify.addFourDigit(String.valueOf(totalPrice1)));
            binding.discountAndRemarksBox.totalDiscountTv.setText(DataModify.addFourDigit(String.valueOf(discount)));


            calculateGrandTotal(total);

        } catch (Exception e) {
        }

    }


    private void showSelectedDataToRecyclerView() {
        List<SalesRequisitionItems> selectedItem = new ArrayList<>();
        selectedItem.addAll(AddNewSale.list);//get static data from (AddNewSale) Fragment
        /**
         * set set selected data to recyclerview
         */
        adapter = new ConfirmSaleAdapterOne(getActivity(), selectedItem, ConfirmNewSale.this);
        binding.selectedProductsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.selectedProductsRv.setAdapter(adapter);
    }


    private void getLocalData() {

        cursor = myDatabaseHelper.displayAllData();
        if (cursor.getCount() == 0) {//means didn't have any data
            return;
        }
        AddNewSale.list.clear();
        while (cursor.moveToNext()) {
            SalesRequisitionItems item = new SalesRequisitionItems();
            item.setProductID(cursor.getString(1));
            item.setProductTitle(cursor.getString(2));
            item.setQuantity(cursor.getString(3));
            item.setUnit(cursor.getString(4));
            item.setUnit_name(cursor.getString(5));
            item.setPrice(cursor.getString(6));
            item.setDiscount(cursor.getString(7));
            item.setTotalPrice(cursor.getString(8));
            AddNewSale.list.add(item);

        }


        totalPrice = 0;
        for (int i = 0; i < AddNewSale.list.size(); i++) {
            totalPrice += Double.parseDouble(AddNewSale.list.get(i).getTotalPrice());
            discount += Double.parseDouble(AddNewSale.list.get(i).getDiscount());
        }
        double finalTotalPrice = totalPrice + discount;
        binding.discountAndRemarksBox.total.setText(DataModify.addFourDigit(String.valueOf(finalTotalPrice)));
        discountCalculation(discountPercent);
    }


    @Override
    public void insertQuantity(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId) {

    }

    @Override
    public void update(int position, String quantity, SalesRequisitionItemsResponse currentItem, String price, String discount, String totalPric, String productId) {
        double totalPriceP = 0.0;
        totalPriceP = Double.parseDouble(quantity) * Double.parseDouble(price);
        //  getDatFromLocal();

    }

    @Override
    public void updateConfirm(int position, String quantity, SalesRequisitionItems currentItem, String price, String discount, String totalPric, String productId) {
        double totalPrice = 0.0;
        totalPrice = Double.parseDouble(quantity) * Double.parseDouble(price);

        updateDataToLocal(currentItem.getProductID(), currentItem.getProductTitle(), quantity, currentItem.getUnit(), currentItem.getUnit_name(), price, discount, String.valueOf(totalPrice));

    }

    @Override
    public void removeBtn(int position) {
        if (AddNewSale.list.size() == 1) {
            // adapter.notifyItemRangeRemoved(0, AddNewSale.list.size());
            return;
        }

        binding.selectedProductsRv.getAdapter().notifyItemRangeChanged(position, AddNewSale.list.size());
        int value = myDatabaseHelper.deleteData(AddNewSale.list.get(position).getProductID());
        if (value > 0) {
            AddNewSale.list.remove(position);
            showSelectedDataToRecyclerView();
        }

        getLocalData();


    }

    private void updateDataToLocal(String productID, String productTitle, String quantity, String unit, String unit_name, String price, String discount, String totalPrice) {
        Boolean isUpdated = myDatabaseHelper
                .updateData(productID, productTitle, quantity, unit, unit_name, price, discount, totalPrice);

        if (isUpdated) {
            //  Toast.makeText(getContext(), "Updated ", Toast.LENGTH_SHORT).show();
        } else {
            //  Toast.makeText(getContext(), "failed update ", Toast.LENGTH_SHORT).show();
        }
        getLocalData();
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
            manageImage();
            discountPercent = "2";
            binding.discountAndRemarksBox.discountSelectorTv.setText("%");

            discountCalculation(discountPercent);
            mypopupWindow.dismiss();
        });

        fixed.setOnClickListener(v -> {
            manageImage();
            discountPercent = "1";
            binding.discountAndRemarksBox.discountSelectorTv.setText("Fixed");

            discountCalculation(discountPercent);
            mypopupWindow.dismiss();
        });


    }


}