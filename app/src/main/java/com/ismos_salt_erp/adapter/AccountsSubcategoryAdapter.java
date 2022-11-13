package com.ismos_salt_erp.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.ManagementFragmentModelBindingBinding;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.view.fragment.accounts.AccountsListFragment;

import java.util.List;

import es.dmoral.toasty.Toasty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountsSubcategoryAdapter extends RecyclerView.Adapter<AccountsSubcategoryAdapter.MyViewholder> {
    FragmentActivity context;
    List<Integer> accountsChildImageList;
    List<String> accountsChildNameList;
    LifecycleOwner lifecycleOwner;

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManagementFragmentModelBindingBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.management_fragment_model_binding, parent, false);
        return new MyViewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        Integer currentImage = accountsChildImageList.get(position);
        String currentTitle = accountsChildNameList.get(position);

        holder.binding.textHomeItem.setText(currentTitle);

        holder.binding.imageHomeItem.setImageDrawable(ContextCompat.getDrawable(context, currentImage));

        AnimationTime.animation(holder.binding.imageHomeItem);


        holder.binding.setClickHandle(() -> {
            Bundle bundle = new Bundle();
            if (accountsChildNameList.get(holder.getAdapterPosition()).equals(AccountsUtil.receiveDue)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1058)) {
                    bundle.putString("name", AccountsUtil.receiveDue);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_dueCollectionFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }
            gotoNext(holder, position , AccountsUtil.receiptList, 1059, "Receipt History List");
            gotoNext(holder, position, AccountsUtil.receiptPendingList, 1059, "Receipt Prnding List");
            gotoNext(holder,position, AccountsUtil.receiptDeclinedList, 1059, "Receipt Declined List");


            if (accountsChildNameList.get(position).equals(AccountsUtil.creditVoucherHistory)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1703)) {
                    bundle.putString("portion", AccountsUtil.creditVoucherHistory);
                    bundle.putString("PageName", AccountsUtil.creditVoucherHistory);
                    AccountsListFragment.pageNumber = 1;
                    AccountsListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_accountsListFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.bankBalanceList)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1685)) {
                    bundle.putString("portion", AccountsUtil.bankBalanceList);
                    bundle.putString("pageName", AccountsUtil.bankBalanceList);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_bankAllListFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }

            if (accountsChildNameList.get(position).equals(AccountsUtil.bnkTranxList)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1059)) {
                    bundle.putString("portion", AccountsUtil.bnkTranxList);
                    bundle.putString("pageName", AccountsUtil.bnkTranxList);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_bankAllListFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }


            if (accountsChildNameList.get(position).equals(AccountsUtil.debitVoucherHistory)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1264)) {
                    bundle.putString("portion", AccountsUtil.debitVoucherHistory);
                    bundle.putString("PageName", AccountsUtil.debitVoucherHistory);
                    AccountsListFragment.pageNumber = 1;
                    AccountsListFragment.isFirstLoad = 0;
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_accountsListFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.payDue)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1062)) {
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_payDueToSupplier);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;


            }

            if (accountsChildNameList.get(position).equals(AccountsUtil.payDueExpense)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1688)) {
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_searchExpenseDue);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;


            }


            if (accountsChildNameList.get(position).equals(AccountsUtil.payInstruction)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1073)) {
                    bundle.putString("name", AccountsUtil.payInstruction);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_paymentInstruction, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;

            }

            if (accountsChildNameList.get(position).equals(AccountsUtil.payInstructionList)) {


                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1336)) {
                    bundle.putString("name", AccountsUtil.payInstructionList);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_paymentInstructionList, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }


            gotoNext(holder,position,AccountsUtil.paymentList,1063,"Payment History List");
            gotoNext(holder,position,AccountsUtil.paymentPendingList,1063,"Payment Pending List");
            gotoNext(holder,position,AccountsUtil.paymentDeclinedList,1063,"Payment Declined List");
            gotoNext(holder,position,AccountsUtil.vendorPaymentList,1689,AccountsUtil.vendorPaymentList);
            gotoNext(holder,position,AccountsUtil.pendingVendorPayment,1689,AccountsUtil.pendingVendorPayment);
            gotoNext(holder,position,AccountsUtil.declinedVendorPayments,1689,AccountsUtil.declinedVendorPayments);


//             if (accountsChildNameList.get(position).equals(AccountsUtil.vendorPaymentList)) {
//                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1689)) {
//                    bundle.putString("portion", AccountsUtil.vendorPaymentList);
//                    bundle.putString("PageName", AccountsUtil.vendorPaymentList);
//                    AccountsListFragment.pageNumber = 1;
//                    AccountsListFragment.isFirstLoad = 0;
//                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_accountsListFragment, bundle);
//                    return;
//                }
//                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
//                return;
//
//
//            }

            if (accountsChildNameList.get(position).equals(AccountsUtil.cash)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1071)) {
                    bundle.putString("portion", AccountsUtil.cash);
                    bundle.putString("PageName", "Cash Book");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_accountsListFragment2, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.dayBook)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1072)) {
                    bundle.putString("portion", AccountsUtil.dayBook);
                    bundle.putString("PageName", "Day Book");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_accountsListFragment2, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;


            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.transactionIn)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1262)) {
                    bundle.putString("portion", AccountsUtil.transactionIn);
                    bundle.putString("PageName", "Transaction In");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_transactionListFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;


            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.transactionOut)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1263)) {
                    bundle.putString("portion", AccountsUtil.transactionOut);
                    bundle.putString("PageName", "Transaction Out");
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_transactionListFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;


            }

            if (accountsChildNameList.get(position).equals(AccountsUtil.creditVoucher)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1265)) {
                    bundle.putString("portion", AccountsUtil.creditVoucher);
                    bundle.putString("PageName", AccountsUtil.creditVoucher);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_creditVoucherFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.creditVoucherHistory)) {
                Toast.makeText(context, "" + AccountsUtil.creditVoucherHistory, Toast.LENGTH_SHORT).show();
            }

            if (accountsChildNameList.get(position).equals(AccountsUtil.debitVoucher)) {
                if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(1699)) {
                    bundle.putString("portion", AccountsUtil.debitVoucher);
                    bundle.putString("PageName", AccountsUtil.debitVoucher);
                    Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_creditVoucherFragment, bundle);
                    return;
                }
                Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                return;
            }
            if (accountsChildNameList.get(position).equals(AccountsUtil.debitVoucherHistory)) {
                Toast.makeText(context, "" + AccountsUtil.debitVoucherHistory, Toast.LENGTH_SHORT).show();

            }


        });
    }

    private void gotoNext(MyViewholder holder, Integer possition, String s1, int permission, String pageName) {
        if (accountsChildNameList.get(possition).equals(s1)) {
            if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(context).getUserCredentials().getPermissions()).contains(permission)) {
                Bundle bundle = new Bundle();
                bundle.putString("portion", s1);
                bundle.putString("PageName", pageName);
                AccountsListFragment.pageNumber = 1;
                AccountsListFragment.isFirstLoad = 0;
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_managementFragment_to_accountsListFragment, bundle);
                return;
            }
            Toasty.info(context, PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public int getItemCount() {
        return accountsChildImageList.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder {
        private ManagementFragmentModelBindingBinding binding;

        public MyViewholder(ManagementFragmentModelBindingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
