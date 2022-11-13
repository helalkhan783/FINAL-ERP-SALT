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
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.SettingsUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyHolder> {
    private Context context;
    List<String> settingNameList;
    List<Integer> settingsImageList;

    View view;

    public SettingsAdapter(FragmentActivity activity, List<String> settingNameList, List<Integer> settingsImageList, View view) {
        this.context = activity;
        this.settingNameList = settingNameList;
        this.settingsImageList = settingsImageList;
        this.view = view;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.management_fragment_model, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String currentTitle = settingNameList.get(position);
        Integer currentImage = settingsImageList.get(position);
        holder.itemImage.setImageDrawable(context.getDrawable(currentImage));
        AnimationTime.animation(holder.itemImage);
        holder.itemText.setText(currentTitle);
        holder.itemView.setOnClickListener(v -> {
            if (settingNameList.get(position).equals(SettingsUtil.storeList)) {
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1123)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("porson", SettingsUtil.storeList);
                        Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_storeListFragment, bundle).onClick(v);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }


                }catch (Exception e){
                    Log.e("ERROR",""+e.getMessage());
                }
            }
            if (settingNameList.get(position).equals(SettingsUtil.setIodine)) {
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1261)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("porson", SettingsUtil.setIodine);
                        Navigation.createNavigateOnClickListener(R.id.action_managementFragment_to_iodinesetFragment, bundle).onClick(v);
                        return;
                    } else {
                        Toasty.info(context, "You don't have permission for access this portion", Toasty.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Log.e("ERROR",""+e.getMessage());
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return settingNameList.size();
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
