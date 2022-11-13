package com.ismos_salt_erp.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;


import com.ismos_salt_erp.R;
import com.ismos_salt_erp.databinding.FragmentAppWebViewFragmnetBinding;
import com.ismos_salt_erp.permission.VersionName;
import com.ismos_salt_erp.utils.AccountsUtil;


public class AppWebViewFragmnet extends BaseFragment {
    FragmentAppWebViewFragmnetBinding binding;
    String url, pageName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_app_web_view_fragmnet, container, false);
        getPreviousData();
        binding.toolbar.toolbarTitle.setText("" + pageName);
        if (pageName.equals("Privacy") ||pageName.equals("Privacy Policy") || pageName.equals("Terms") || pageName.equals("Help Center")) {
            binding.bottomNavigation.setVisibility(View.GONE);
            binding.webviewLayout.setVisibility(View.VISIBLE);
            binding.aboutLayout.setVisibility(View.GONE);
            try {
                WebSettings webSettings = binding.webview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                binding.webview.setWebViewClient(new WebViewClient());
                binding.webview.loadUrl(url);
            } catch (Exception e) {
                Log.d("ERROR", e.getMessage());
            }
        }
        if (pageName.equals("About")){
            binding.webviewLayout.setVisibility(View.GONE);
            binding.aboutLayout.setVisibility(View.VISIBLE);
            binding.versionName.setText("Current Version -  " + VersionName.getVersionName(getActivity()));

        }
        binding.toolbar.setClickHandle(() -> getActivity().onBackPressed());



        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.dayBook:
                    try {
                        bundle.putString("portion", AccountsUtil.dayBook);
                        bundle.putString("PageName", "Day Book");
                        Navigation.findNavController(getView()).navigate(R.id.action_AppWebViewFragmnet_to_accountsListFragment2, bundle);
                    } catch (Exception e) {

                    }

                    break;

                case R.id.cashBook:
                    try {
                        bundle.putString("portion", AccountsUtil.cash);
                        bundle.putString("PageName", "Cash");
                        Navigation.findNavController(getView()).navigate(R.id.action_AppWebViewFragmnet_to_accountsListFragment2, bundle);
                    } catch (Exception e) {
                    }

                    break;

                case R.id.creditor:
                    bundle.putString("portion", "Creditors");
                    bundle.putString("PageName", "Creditors");
                    Navigation.findNavController(getView()).navigate(R.id.action_AppWebViewFragmnet_to_transactionListFragment, bundle);
                    break;
                case R.id.debitor:
                    bundle.putString("portion", "Debitors");
                    bundle.putString("PageName", "Debitors");
                    Navigation.findNavController(getView()).navigate(R.id.action_AppWebViewFragmnet_to_transactionListFragment, bundle);
                    break;
                case R.id.home:
                    try {

                        Navigation.findNavController(getView()).navigate(R.id.action_AppWebViewFragmnet_to_homeFragment);

                    } catch (Exception e) {
                        Log.d("ERROR", "" + e.getMessage());
                    }
                    break;


            }
            return true;
        });

        return binding.getRoot();
    }

    private void getPreviousData() {
        url = getArguments().getString("url");
        pageName = getArguments().getString("pageName");
    }

}