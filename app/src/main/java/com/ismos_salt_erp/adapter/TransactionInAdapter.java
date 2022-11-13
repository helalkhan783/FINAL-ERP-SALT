package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.TransactionInListLayoutBinding;
import com.ismos_salt_erp.serverResponseModel.TransactionInList;
import com.ismos_salt_erp.utils.ImageBaseUrl;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransactionInAdapter extends RecyclerView.Adapter<TransactionInAdapter.viewHolder> {
    private Context context;
    List<TransactionInList> lists;
    View view;

    public TransactionInAdapter(Context context, List<TransactionInList> lists, View view) {
        this.context = context;
        this.lists = lists;
        this.view = view;
    }

    @NonNull
    @NotNull
    @Override
    public TransactionInAdapter.viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        TransactionInListLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.
                from(parent.getContext()), R.layout.transaction_in_list_layout, parent, false);
        return new TransactionInAdapter.viewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransactionInAdapter.viewHolder holder, int position) {
        TransactionInList currentList = lists.get(position);

         if (currentList.getPaymentDateTime() != null) {
            holder.itembinding.date.setText(":  " + currentList.getPaymentDateTime());
        }
          if (currentList.getOrderID() != null) {
            holder.itembinding.refNumber.setText(":  " + currentList.getOrderID());
        }
          if (currentList.getCompanyName() != null) {
            holder.itembinding.companyName.setText(":  " + currentList.getCompanyName() +" @ " + currentList.getCustomerFname());
        }
          if (currentList.getTotalAmount() != null) {
            holder.itembinding.amount.setText(":  " + DataModify.addFourDigit(currentList.getTotalAmount())+ MtUtils.priceUnit);
        }

          if (currentList.getPaymentType() != null) {
              holder.itembinding.transactionType.setText(":  "  +currentList.getPaymentType());

          }

          if (currentList.getCustomerFname() != null) {
            holder.itembinding.processdBy.setText(" " + currentList.getFullName());
        }

        try {
            Glide.with(context).load(ImageBaseUrl.image_base_url + currentList.getProfilePhoto()).centerCrop().
                    error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).
                    into(holder.itembinding.purchaseImage);

        } catch (NullPointerException e) {
            Log.d("ERROR", e.getMessage());
            Glide.with(context).load(R.mipmap.ic_launcher).into(holder.itembinding.purchaseImage);
        }

    /*    holder.itembinding.view.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId", currentList.getId());
            bundle.putString("orderVendorId", currentList.getOrderVendorID());
            bundle.putString("portion", "PENDING_SALE");
            bundle.putString("porson", "SaleHistoryDetails");
            bundle.putString("grandTotal", currentList.getGrandTotal());
            SaleAllListFragment.pageNumber = 1;

            Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_saleAllListFragment_to_pendingPurchaseDetailsFragment, bundle);

        });
        holder.itembinding.setClickHandle(new SaleHistoryClickHandle() {
            @Override
            public void edit() {
                try {
                    myDatabaseHelper.deleteAllData();
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());
                }

                String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
                if (currentProfileTypeId != null) {
                    if (!currentProfileTypeId.equals("7")) {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                        return;
                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("sid", lists.get(holder.getAdapterPosition()).getSerialID());
                SaleAllListFragment.pageNumber = 1;
                Navigation.findNavController(holder.itembinding.getRoot()).navigate(R.id.action_saleAllListFragment_to_editSaleData, bundle);
            }

            @Override
            public void view() {

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TransactionInListLayoutBinding itembinding;

        public viewHolder(@NonNull @NotNull TransactionInListLayoutBinding itembinding) {
            super(itembinding.getRoot());
            this.itembinding = itembinding;
        }
    }
}
