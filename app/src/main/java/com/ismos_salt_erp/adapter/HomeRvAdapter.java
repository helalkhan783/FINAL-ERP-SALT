package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.HomeFragmentModelBinding;
import com.ismos_salt_erp.utils.HomeUtils;

import java.util.List;

public class HomeRvAdapter extends RecyclerView.Adapter<HomeRvAdapter.MyHolder> {
    private final String root = " Management";
    FragmentActivity context;
    List<Integer> imageList;
    List<String> itemNameList;
    View view;

    public HomeRvAdapter(FragmentActivity context, List<Integer> imageList, List<String> itemNameList,View view) {
        this.context = context;
        this.imageList = imageList;
        this.itemNameList = itemNameList;
        this.view = view;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeFragmentModelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.home_fragment_model, parent, false);
       // View view = LayoutInflater.from(context).inflate(R.layout.home_fragment_model, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Integer currentImage = imageList.get(holder.getAdapterPosition());
        String currentTitle = itemNameList.get(holder.getAdapterPosition());
        holder.binding.imageview.setImageDrawable(ContextCompat.getDrawable(context,currentImage));
        holder.binding.nameTv.setText(currentTitle);

        holder.binding.getRoot().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            /**
             * if user click salesRequisition
             */
     /*   if (currentTitle.equals(HomeUtils.sales)) {
            bundle.putString("Item", itemNameList.get(position));
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_salesRequisitionManagementFragment, bundle).onClick(v);
            return;
        }*/
          /*  if (itemNameList.get(holder.getAdapterPosition()).equals("Miller"){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
             }
*/
            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.itemManagement)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
            }


            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.qcQa)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                 Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
            }
            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.monitoring)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                //Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);

            }

            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.customers)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                //  Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);

            }

            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.sales)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
//                    Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);

            }

            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.purchases)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                // Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);

            }
            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.production)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                //Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
            }

            if (itemNameList.get(holder.getAdapterPosition()).equals(HomeUtils.report)){
                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
                //Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
            }



//                bundle.putString("Item", itemNameList.get(holder.getAdapterPosition()));
//                //Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_managementFragment, bundle).onClick(v);
//                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_homeFragment_to_managementFragment, bundle);
        });



//        holder.itemView.setOnClickListener(v -> {
//
//
//        });
    }

    @Override
    public int getItemCount() {
        return itemNameList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

/*        @BindView(R.id.imageview)
        ImageView itemImage;
        @BindView(R.id.nameTv)
        TextView itemText;*/
        private HomeFragmentModelBinding binding;

        public MyHolder(HomeFragmentModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
          //  ButterKnife.bind(this, itemView);
        }
    }

}
