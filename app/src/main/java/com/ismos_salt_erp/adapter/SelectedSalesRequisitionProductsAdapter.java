package com.ismos_salt_erp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.serverResponseModel.SalesRequisitionItemsResponse;
import com.ismos_salt_erp.view.fragment.salesRequisition.NewSaleFragment;
import com.ismos_salt_erp.view.fragment.salesRequisition.NewSalesRequisitionFragmentInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedSalesRequisitionProductsAdapter extends RecyclerView.Adapter<SelectedSalesRequisitionProductsAdapter.MyHolder> {
    FragmentActivity context;
    List<SalesRequisitionItemsResponse> salesRequisitionItemsResponsesList;

    public SelectedSalesRequisitionProductsAdapter(FragmentActivity activity, List<SalesRequisitionItemsResponse> salesRequisitionItemsResponsesList) {
        this.context = activity;
        this.salesRequisitionItemsResponsesList = salesRequisitionItemsResponsesList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_product_item_sale2, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        SalesRequisitionItemsResponse currentProduct = salesRequisitionItemsResponsesList.get(position);
        holder.productName.setText(currentProduct.getProductTitle());
        holder.productPrice.setText(NewSalesRequisitionFragmentInfo.selectedPriceList.get(position));
        holder.productQuantity.setText(NewSaleFragment.getAllQuantity().get(position));
    }

    @Override
    public int getItemCount() {
        return salesRequisitionItemsResponsesList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.productName)
        TextView productName;
        @BindView(R.id.productPrice)
        EditText productPrice;
        @BindView(R.id.productQuantity)
        EditText productQuantity;
        @BindView(R.id.img_btn_add)
        ImageButton addQuantityBtn;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
