package com.ismos_salt_erp.view.fragment.purchase.newPurchase;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

public class HandleDiscount  {
    //for discount variable;
   private FragmentActivity activity;
    private CheckBox fixedDiscoutBox, discountPerchantBox;
    private  EditText discountEt, totalTv,dueEt,paidAmountEt;
    private TextView discountTv, grandTotal,totalTvEt;
    public  String discountSenToServer,purchaseEdit;
    // for paid vriable
    String discountType1;

    public HandleDiscount(FragmentActivity activity,String purchaseEdit, EditText discountEt, CheckBox fixedDiscoutBox, CheckBox discountPerchantBox,
                          TextView discount, TextView grandTotal, EditText totalTv,TextView total,EditText paidAmountEt,EditText dueEt) {
        this.activity = activity;
        this.purchaseEdit = purchaseEdit;
        this.fixedDiscoutBox = fixedDiscoutBox;
        this.discountPerchantBox = discountPerchantBox;
        this.discountEt = discountEt;
        this.totalTv = totalTv;
        this.discountTv = discount;
        this.grandTotal = grandTotal;
        this.totalTvEt = total;
        this.paidAmountEt = paidAmountEt;
        this.dueEt = dueEt;


    }

    public  void calculation(){
        discountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                paidAmountEt.setText("");
                String discount = discountEt.getText().toString();
                if (fixedDiscoutBox.isChecked()) {
                    discountPerchantBox.setSelected(false);
                    discountCalculation(discount,"2");
                }

                if (discountPerchantBox.isChecked()) {
                    fixedDiscoutBox.setSelected(false);
                    discountCalculation(discount,"1");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    public void discountCalculation(String d,String discountType) {
        discountType = discountType1;
       try {
           double discount = 0, total = 0, totalPrice1=0;

           discount =  Double.parseDouble(d);

           total = Double.parseDouble(totalTvEt.getText().toString());

           if (discountType1.equals("2")){
               totalPrice1 = total - discount;
           }
           if (discountType1.equals("1")){
               discount = total*discount/100;
               totalPrice1 = total - discount;
               //  totalPrice1 = total - discount;
           }
           if (total < discount) {
               discountEt.setError("Invalid discount");
               discountEt.requestFocus();
               return;
           }
           discountTv.setText(String.valueOf(discount));
           grandTotal.setText(String.valueOf(totalPrice1));
           totalTv.setText(String.valueOf(totalPrice1));
           dueEt.setText(String.valueOf(totalPrice1));
       }catch (Exception e){
        }

    }

    public  void forPaidVariable(){
        paidAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String paidAmount = paidAmountEt.getText().toString();
                countAmount(paidAmount);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void countAmount(String paid) {
      try {
          double paidAmount = 0, totalPrice = 0;
          paidAmount = Double.parseDouble(paid);
          totalPrice = Double.parseDouble(totalTv.getText().toString());
          if (paidAmount <= totalPrice) {
              double due =  totalPrice - paidAmount;
              dueEt.setText(String.valueOf(due));
          }
          if (paidAmount > totalPrice ) {
              paidAmountEt.setError("Invalid Amount");
              paidAmountEt.requestFocus();
              return;
          }
      }
      catch (Exception e){
       }
    }

    //for edit
    public  void calculation(String discountType){
        discountType1 = discountType;
        discountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s != null){
                    discountTv.setText("");
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               try {
                   String discount = discountEt.getText().toString().trim();
                   paidAmountEt.setText("");
                   if (discountType1.equals("2")){
                       discountCalculation(discount,discountType1,"");
                       return;
                   }
                   if (discountType1.equals("1")){
                       discountSenToServer = discountEt.getText().toString();
                       discountSenToServer = discountSenToServer + "%";
                       discountCalculation(discount,discountType1,"");
                       return;
                   }
                   if (discountType1.equals("0")){
                       if (fixedDiscoutBox.isChecked()) {
                           discountCalculation(discount,"2");
                       }
                       if (discountPerchantBox.isChecked()) {
                           discountCalculation(discount,"1");
                       }
                   }
               }catch (Exception e){}
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void discountCalculation(String d,String discountType,String ok) {
        try {
            double discount = 0, total = 0, totalPrice1=0;

            discount =  Double.parseDouble(d);

            total = Double.parseDouble(totalTvEt.getText().toString());

            if (discountType.equals("2")){
                totalPrice1 = total - discount;
            }
            if (discountType.equals("1")){
                discount = total*discount/100;
                totalPrice1 = total - discount;
                //  totalPrice1 = total - discount;
            }
            if (total < discount) {
                discountEt.setError("Invalid discount");
                discountEt.requestFocus();
                return;
            }
            discountTv.setText(String.valueOf(discount));
            grandTotal.setText(String.valueOf(totalPrice1));
            totalTv.setText(String.valueOf(totalPrice1));
            dueEt.setText(String.valueOf(totalPrice1));
        }catch (Exception e){
         }

    }


    public  void forPaidVariable(String editPurchase){
        paidAmountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String paidAmount = paidAmountEt.getText().toString();
                countAmount(paidAmount,"");
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void countAmount(String paid,String kk) {
        try {
            double paidAmount = 0, totalPrice = 0;
            paidAmount = Double.parseDouble(paid);
            totalPrice = Double.parseDouble(totalTv.getText().toString());
            if (paidAmount <= totalPrice) {
                double due =  totalPrice - paidAmount;
                dueEt.setText(String.valueOf(due));
            }
            if (paidAmount > totalPrice ) {
                paidAmountEt.setError("Invalid Amount");
                paidAmountEt.requestFocus();
                return;
            }
        }
        catch (Exception e){
         }
    }




}
