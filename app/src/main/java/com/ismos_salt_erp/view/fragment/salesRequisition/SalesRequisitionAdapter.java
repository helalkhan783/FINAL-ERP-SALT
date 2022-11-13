package com.ismos_salt_erp.view.fragment.salesRequisition;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.SalesRequisitionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SalesRequisitionAdapter extends RecyclerView.Adapter<SalesRequisitionAdapter.MyHolder> {
    FragmentActivity context;
    List<String> allSaleRequisitionTitle;
    List<Integer> allSaleRequisitionImage;
    String positionString;

    public SalesRequisitionAdapter(FragmentActivity activity, List<String> allSaleRequisitionTitle, List<Integer> allSaleRequisitionImage) {
        this.context = activity;
        this.allSaleRequisitionTitle = allSaleRequisitionTitle;
        this.allSaleRequisitionImage = allSaleRequisitionImage;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.management_fragment_model, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Integer currentImage = allSaleRequisitionImage.get(position);
        String currentTitle = allSaleRequisitionTitle.get(position);
        holder.itemText.setText(currentTitle);
        holder.itemImage.setImageDrawable(context.getDrawable(currentImage));
        AnimationTime.animation(holder.itemImage);
        holder.itemView.setOnClickListener(v -> {
            positionString = allSaleRequisitionTitle.get(position);
            goToNext(SalesRequisitionUtil.salesReqListTitle, R.id.action_managementFragment_to_salesRequisitionList, v, 1037);
            goToNext(SalesRequisitionUtil.pendingReqListTitle, R.id.action_managementFragment_to_pendingRequisitionListFragment, v, 1038);
            goToNext(SalesRequisitionUtil.declinedReqListTitle, R.id.action_managementFragment_to_declinedRequisitionListFragment, v, 1039);
            goToNext(SalesRequisitionUtil.newSaleTitle, R.id.action_managementFragment_to_newSaleFragment, v, 1036);

        });


    }

    private void goToNext(String fromName, int path, View v, int permissionCode) {
        try {
            if (positionString.equals(fromName)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(permissionCode)) {
                    Navigation.createNavigateOnClickListener(path).onClick(v);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();

            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return allSaleRequisitionImage.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_home_item)
        ImageView itemImage;
        @BindView(R.id.text_home_item)
        TextView itemText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
