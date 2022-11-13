package com.ismos_salt_erp.view.fragment.monitoring;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.AddQuantityModelLayoutBinding;
import com.ismos_salt_erp.databinding.FragmentAddInitialQuantityBinding;
import com.ismos_salt_erp.serverResponseModel.StoreList;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter {
        AddQuantityModelLayoutBinding binding;
        List<StoreList> storeLists;
        FragmentActivity activity;
        public CustomArrayAdapter( List<StoreList> storeLists,FragmentActivity activity) {
                 super(activity, R.layout.add_quantity_model_layout, R.id.itemName, storeLists);
                this.storeLists=storeLists;
                this.activity=activity;
        }


        @Override
        public int getCount() {
                return storeLists.size(); //counts the total number of elements from the arrayList.
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            //    StoreList current = storeLists.get(position);
            /*    LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
                binding=  DataBindingUtil.inflate( LayoutInflater.from(parent.getContext()), R.layout.add_quantity_model_layout,parent,false);
            //    binding = DataBindingUtil.inflate(inflater,R.layout.add_quantity_model_layout, parent,false);//set layout for displaying items
             /*   for (int i = 0; i < storeLists.size(); i++) {

                }*/
                binding.itemName.setText(storeLists.get(position).getStoreName() + "(" + storeLists.get(position).getEnterpriseName() + ")");

                //get id for image view
                return binding.getRoot();
        }



}

/*extends ArrayAdapter<String> {
        private FragmentActivity context;
        private List<StoreList> storeLists;

        public CustomArrayAdapter(FragmentActivity context1, List<StoreList> storeLists) {
                super(context1, R.layout.fragment_add_initial_quantity);
                this.context = context1;
                this.storeLists = storeLists;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
              *//*  return super.getView(position, convertView, parent);*//*

                LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View rowView =inflater.inflate(R.layout.fragment_add_initial_quantity, parent, false);
                TextView your_first_text_view = (TextView) rowView.findViewById(R.id.itemName);
                EditText your_second_text_view = (EditText) rowView.findViewById(R.id.quantity);
                StoreList current =  storeLists.get(position);
                your_first_text_view.setText(current.getStoreName() + "(" + current.getEnterpriseName() + ")");
                //    your_second_text_view.setText(strings.get(position)); //Instead of the same value use position + 1, or something appropriate


        return rowView;
        }
}
*/



/*extends ArrayAdapter<String>
{
private FragmentActivity context;
private List<StoreList> storeLists;

public CustomArrayAdapter(FragmentActivity context, List<StoreList> storeLists)
        {
        super(context, R.layout.fragment_add_initial_quantity, 0);
        this.context = context;
        this.storeLists = new ArrayList<StoreList>();
        this.storeLists = storeLists;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent)
        {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_add_initial_quantity, parent, false);

        TextView your_first_text_view = (TextView) rowView.findViewById(R.id.itemName);
        EditText your_second_text_view = (EditText) rowView.findViewById(R.id.quantity);
        StoreList current =  storeLists.get(position);
        your_first_text_view.setText(current.getStoreName() + "(" + current.getEnterpriseName() + ")");
    //    your_second_text_view.setText(strings.get(position)); //Instead of the same value use position + 1, or something appropriate

        return rowView;
        }
        }*/