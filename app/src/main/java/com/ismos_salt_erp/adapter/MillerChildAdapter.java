package com.ismos_salt_erp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.ismos_salt_erp.utils.MillerUtils;
import com.ismos_salt_erp.utils.PermissionUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MillerChildAdapter extends RecyclerView.Adapter<MillerChildAdapter.MyHolder> {
    private Context context;
    List<String> millerNameList;
    List<Integer> millerImageList;
    Bundle bundle = new Bundle();

    public MillerChildAdapter(FragmentActivity activity, List<String> millerNameList, List<Integer> millerImageList) {
        this.context = activity;
        this.millerNameList = millerNameList;
        this.millerImageList = millerImageList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.management_fragment_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Integer currentImage = millerImageList.get(position);
        String currentTitle = millerNameList.get(position);
        holder.itemImage.setImageDrawable(context.getDrawable(currentImage));
        holder.itemImage.animate().rotation(180f).setDuration(5000).start();
        holder.itemText.setText(currentTitle);

        holder.itemView.setOnClickListener(v -> {
            if (millerNameList.get(position).equals(MillerUtils.addnewMiller)) {
                try {
                    String currentProfileTypeId = PreferenceManager.getInstance(context).getUserCredentials().getProfileTypeId();
                    if (!currentProfileTypeId.equals("7")) {
                        Toasty.info(context, "You don't  have permission for access this portion", Toasty.LENGTH_LONG).show();
                        return;
                    }
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1344) || PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1)) {
                        Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_addNewMiller, bundle).onClick(v);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                return;
            }


            if (millerNameList.get(position).equals(MillerUtils.millreProfileList)) {
                bundle.putString("porson", millerNameList.get(position));
                Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_millerAllListFragment, bundle).onClick(v);
            }


            if (millerNameList.get(position).equals(MillerUtils.millerDeclineList)) {
                bundle.putString("porson", millerNameList.get(position));
                Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_millerAllListFragment, bundle).onClick(v);
            }


            if (millerNameList.get(position).equals(MillerUtils.millerHistoryList)) {
                bundle.putString("porson", millerNameList.get(position));
                Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_millerAllListFragment, bundle).onClick(v);
            }


        });
    }

    @Override
    public int getItemCount() {
        return millerNameList.size();
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
