package com.ismos_salt_erp.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.SliderListBindingBinding;
import com.ismos_salt_erp.serverResponseModel.SliderList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    private FragmentActivity activity;
    private List<SliderList> sliderLists;

    public SliderAdapter(FragmentActivity activity, List<SliderList> sliderLists) {
        this.activity = activity;
        this.sliderLists = sliderLists;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SliderListBindingBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.slider_list_binding, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

            SliderList sliderList = sliderLists.get(position);
            try {
                Glide.with(activity)
                        .load(sliderList.getImage())
                        .fitCenter().error(R.drawable.banner)
                        .placeholder(R.drawable.banner)
                        .into(holder.binding.banner);
            } catch (Exception e) {
            }



    }

    @Override
    public int getItemCount() {
        return sliderLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final SliderListBindingBinding binding;

        public ViewHolder(final SliderListBindingBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
