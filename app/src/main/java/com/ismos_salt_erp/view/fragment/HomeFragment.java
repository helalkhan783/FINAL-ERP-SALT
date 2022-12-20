package com.ismos_salt_erp.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.SliderAdapter;
import com.ismos_salt_erp.databinding.FragmentHomeBinding;
import com.ismos_salt_erp.dialog.MyApplication;
import com.ismos_salt_erp.navigatehelaper.LicenceData;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.notification.NotificationUtil;
import com.ismos_salt_erp.navigatehelaper.HomePageHelperClass;
import com.ismos_salt_erp.permission.VersionName;
import com.ismos_salt_erp.utils.AccountReportUtils;
import com.ismos_salt_erp.utils.AccountsUtil;
import com.ismos_salt_erp.utils.AnimationTime;
import com.ismos_salt_erp.utils.CrispUtil;
import com.ismos_salt_erp.utils.HomeUtils;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.PermissionUtil;
import com.ismos_salt_erp.utils.ReportUtils;
import com.ismos_salt_erp.utils.UrlUtil;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.viewModel.DashBoardViewModel;
import com.ismos_salt_erp.viewModel.HomePageViewModel;
import com.ismos_salt_erp.viewModel.LogoutViewModel;
import com.ismos_salt_erp.viewModel.PermissionViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;
import im.crisp.client.ChatActivity;
import im.crisp.client.Crisp;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private PermissionViewModel permissionViewModel;
    private LogoutViewModel logoutViewModel;
    private DashBoardViewModel dashBoardViewModel;
    private HomePageViewModel homePageViewModel;
    String receipt, payment, expense;
    int destinationPath;
    FragmentHomeBinding binding;

    ShimmerFrameLayout container;

    Bundle bundle1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        permissionViewModel = ViewModelProviders.of(this).get(PermissionViewModel.class);
        logoutViewModel = ViewModelProviders.of(this).get(LogoutViewModel.class);
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        homePageViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);
        setClick();
        pageData();
        customiseDrawer();


        OnBackPressedCallback callback = new OnBackPressedCallback(true /** enabled by default**/) {
            @Override
            public void handleOnBackPressed() {
                MyApplication.exitApp(getActivity());//for show exit app dialog
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        binding.homeRvPortion.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            HomePageHelperClass homePageHelperClass = new HomePageHelperClass(getActivity(), getView());
            destinationPath = R.id.action_homeFragment_to_accountsListFragment2;

            switch (item.getItemId()) {

                case R.id.dayBook:
                    homePageHelperClass.homePageToAccountListFragment(null, AccountsUtil.dayBook, destinationPath);
                    break;

                case R.id.cashBook:
                    homePageHelperClass.homePageToAccountListFragment(null, AccountsUtil.cash, destinationPath);
                    break;

                case R.id.creditor:
                    homePageHelperClass.homePageToAccountListFragment(null, AccountReportUtils.creditors, R.id.action_homeFragment_to_transactionListFragment);
                    break;
                case R.id.debitor:
                    homePageHelperClass.homePageToAccountListFragment(null, AccountReportUtils.debitors, R.id.action_homeFragment_to_transactionListFragment);
                    break;
                case R.id.home:
                    try {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        List<Fragment> fragments = fm.getFragments();
                        Fragment lastFragment = fragments.get(fragments.size() - 1);

                        if (lastFragment instanceof HomeFragment) {
                        } else {
                            Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_self);
                        }
                    } catch (Exception e) {
                    }
                    break;

            }
            return true;
        });

        // sweper refresh layout
        binding.refresh.setColorSchemeColors(getResources().getColor(R.color.app_color));
        binding.refresh.setOnRefreshListener(() -> pageData());

        //for auto scrolling textView
        autoScrollTv();
        // image animation
        setAnimationToImage();
        return binding.getRoot();
    }


    private void customiseDrawer() {
        int width = (getResources().getDisplayMetrics().widthPixels / 2) + 100;
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) binding.navbar.getLayoutParams();
        params.width = width;
        binding.navbar.setLayoutParams(params);
        NotificationUtil.IS_LOGGED_IN = true;
    }


    @SuppressLint("SetTextI18n")
    private void pageData() {

        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        homePageViewModel.getHomePageData(getActivity())
                .observe(getViewLifecycleOwner(), response -> {
                    binding.shimmerViewContainerLay.setVisibility(View.VISIBLE);
                    binding.shimmerViewContainerAccess.shimmerViewContainer.startShimmer();
                    if (response == null || response.getStatus() == 400 || response.getStatus() == 500) {
                        errorMessage(getActivity().getApplication(), "");
                        binding.shimmerViewContainerAccess.shimmerViewContainer.stopShimmer();
                        binding.shimmerViewContainerLay.setVisibility(View.GONE);
                        return;
                    }
                    /**navigation  data set*/
                    try {

                        binding.homeRvPortion.trackingSummary.corporationTitle.setText("" + response.getEnterpriseInfo().getFullName());
                        binding.homeRvPortion.trackingSummary.shippingAgent.setText("" + response.getEnterpriseInfo().getStoreAddress());

                        binding.homeRvPortion.totalSale.setText("" + DataModify.addFourDigit(response.getTotalSale()));
                        receipt = response.getTotalReceipt();
                        payment = response.getTotapayment();
                        expense = response.getTotalExpensePaid();
                        /*if (receipt == null) {
                            receipt = "0.00";
                        }
                        if (payment == null) {
                            payment = "0.00";
                        }
                        if (expense == null || expense == "0") {
                            expense = "0.00";
                        }*/
                        bundle1 = new Bundle();
                        bundle1.putString("receipt", response.getTotalReceipt());
                        bundle1.putString("payment", response.getTotapayment());
                        bundle1.putString("expense", response.getTotalExpensePaid());
                        binding.homeRvPortion.purchaseRawSalt.setText("" + receipt);//receipt
                        binding.homeRvPortion.edibleRawSalt.setText("" + payment);//payment
                        binding.homeRvPortion.industrialSaltSale.setText("" + expense);//expense


                        binding.refresh.setRefreshing(false);
                        binding.shimmerViewContainerAccess.shimmerViewContainer.stopShimmer();
                        binding.shimmerViewContainerLay.setVisibility(View.GONE);


                        binding.drawer.profileName.setText("" + response.getUserInfo().getDisplayName());

                        String storeName = response.getEnterpriseInfo().getStoreName(), userName = response.getUserInfo().getUserName();
                        if (storeName == null) {
                            storeName = "";
                        }
                        if (userName == null) {
                            userName = "";
                        }

                        binding.drawer.enterPriseShortName.setText("" + storeName + "\n" + userName);

                        Glide.with(getContext())
                                .load(response.getUserInfo().getProfilePhoto())
                                .error(R.drawable.image)
                                .placeholder(R.drawable.image)
                                .into(binding.drawer.profileImageView);

                    } catch (Exception e) {
                    }

                    // now set company information

                    TextDrawable drawable = null;
                    try {
                        if (response.getEnterpriseInfo().getCompanyLogo() == null) {
                            if (response.getEnterpriseInfo().getFullName() != null) {
                                String firstFont;
                                String name = String.valueOf(response.getEnterpriseInfo().getFullName());
                                firstFont = String.valueOf(name.charAt(0)).toUpperCase();

                                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                                int color = generator.getColor(firstFont);
                                drawable = TextDrawable.builder().buildRound(firstFont, color);//radius in px color);
                            }
                            binding.homeRvPortion.trackingSummary.logo.setImageDrawable(drawable);
                        } else {
                            Glide.with(getContext())
                                    .load(response.getEnterpriseInfo().getStoreLogo())
                                    .centerInside()
                                    .error(R.drawable.dash_icon)
                                    .into(binding.homeRvPortion.trackingSummary.logo);
                        }
                    } catch (Exception e) {
                    }

                    try {

                        // set banner
                        if (!response.getSliderLists().isEmpty()) {
                            try {
                                binding.homeRvPortion.sliserRvLayout.setVisibility(View.VISIBLE);
                                SliderAdapter sliderAdapter = new SliderAdapter(getActivity(), response.getSliderLists());
                                binding.homeRvPortion.sliderRv.setLayoutManager(new LinearLayoutManager(getContext()));
                                binding.homeRvPortion.sliderRv.setAdapter(sliderAdapter);
                                binding.homeRvPortion.manageView.setVisibility(View.GONE);
                            } catch (Exception e) {
                            }
                        }


                        binding.homeRvPortion.receiptBdt.setText("" + MtUtils.priceUnit);
                        binding.homeRvPortion.paymenttBdt.setText("" + MtUtils.priceUnit);
                        binding.homeRvPortion.expenseBdt.setText("" + MtUtils.priceUnit);
                        binding.homeRvPortion.totalSaleBdt.setText("" + MtUtils.priceUnit);
                        /**
                         * check licence validity
                         */
                        new LicenceData(getActivity(), response.getLicenceExpire()).showLicenceData();


                    } catch (Exception e) {
                        binding.refresh.setRefreshing(false);
                        binding.shimmerViewContainerAccess.shimmerViewContainer.stopShimmer();
                        binding.shimmerViewContainerLay.setVisibility(View.GONE);

                    }
                });
    }

    private void setClick() {
        binding.homeRvPortion.item.setOnClickListener(this);
        binding.homeRvPortion.salee.setOnClickListener(this);
        binding.homeRvPortion.purchase.setOnClickListener(this);
        binding.homeRvPortion.production.setOnClickListener(this);
        binding.homeRvPortion.stock.setOnClickListener(this);
        binding.homeRvPortion.monitoring.setOnClickListener(this);
        binding.homeRvPortion.customer.setOnClickListener(this);
        binding.homeRvPortion.supplier.setOnClickListener(this);
        binding.homeRvPortion.qcqa.setOnClickListener(this);
        binding.homeRvPortion.user.setOnClickListener(this);
        binding.homeRvPortion.report.setOnClickListener(this);
        binding.homeRvPortion.accounts.setOnClickListener(this);
        binding.homeRvPortion.bankManagement.setOnClickListener(this);
        binding.homeRvPortion.salesReq.setOnClickListener(this);
        binding.homeRvPortion.purchaseRequisition.setOnClickListener(this);
        binding.homeRvPortion.receiptView.setOnClickListener(this);
        binding.homeRvPortion.paymentView.setOnClickListener(this);
        binding.homeRvPortion.expenseView.setOnClickListener(this);
        binding.homeRvPortion.seeMoreBtn.setOnClickListener(this);
        binding.homeRvPortion.reconciliation.setOnClickListener(this);
        binding.drawer.millerProfile.setOnClickListener(this);
        binding.drawer.settings.setOnClickListener(this);
        binding.drawer.inbox.setOnClickListener(this);
        binding.drawer.onLineSupport.setOnClickListener(this);
        binding.drawer.about.setOnClickListener(this);
        binding.drawer.rate.setOnClickListener(this);
        binding.drawer.share.setOnClickListener(this);
        binding.drawer.feedback.setOnClickListener(this);
        binding.drawer.privacy.setOnClickListener(this);
        binding.drawer.profilePortion.setOnClickListener(this);
        binding.drawer.logout.setOnClickListener(this);
        binding.drawer.qrScan.setOnClickListener(this);
        binding.tolvaLayout.navBtn.setOnClickListener(this);
        binding.tolvaLayout.bellBtn.setOnClickListener(this);
        binding.homeRvPortion.saleCircle.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        HomePageHelperClass homePageHelperClass = new HomePageHelperClass(getActivity(), getView());
        destinationPath = R.id.action_homeFragment_to_accountsListFragment;
        switch (v.getId()) {
            case R.id.about:
                bundle.putString("pageName", "About");
                bundle.putString("url", UrlUtil.aboutUrl);
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_appWebViewFragmnet, bundle);
                break;
            case R.id.privacy:
                bundle.putString("pageName", "Privacy Policy");
                bundle.putString("url", UrlUtil.privacyUti);
                break;
            case R.id.feedback:
                this.openFrrdBackActivity();
                break;

            case R.id.share:
                this.share();
                break;
            case R.id.rate:
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.ismos_salt_erp")));
                } catch (Exception e) {
                }

                break;

            case R.id.onLineSupport:
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    return;
                }
                Crisp.configure(getContext(), CrispUtil.crispSecretKey);
                Intent crispIntent = new Intent(getActivity(), ChatActivity.class);
                startActivity(crispIntent);
                break;
            case R.id.inbox:
                homePageHelperClass.navigate("Inbox");
                break;
            case R.id.item:
                homePageHelperClass.navigate(HomeUtils.itemManagement);
                break;
            case R.id.salee:
                homePageHelperClass.navigate(HomeUtils.sales);
                break;
            case R.id.purchase:
                homePageHelperClass.navigate(HomeUtils.purchases);
                break;
            case R.id.production:
                homePageHelperClass.navigate(HomeUtils.production);
                break;
            case R.id.stock:
                homePageHelperClass.navigate(HomeUtils.stock);
                break;
            case R.id.monitoring:
                homePageHelperClass.navigate(HomeUtils.monitoring);
                break;
            case R.id.customer:
                homePageHelperClass.navigate(HomeUtils.customers);
                break;
            case R.id.supplier:
                homePageHelperClass.navigate(HomeUtils.suppliers);
                break;
            case R.id.qcqa:
                homePageHelperClass.navigate(HomeUtils.qcQa);
                break;

            case R.id.user:
                homePageHelperClass.navigate(HomeUtils.user);
                break;

            case R.id.report:
                homePageHelperClass.navigate(HomeUtils.report);
                break;
            case R.id.accounts:
                homePageHelperClass.navigate(HomeUtils.account);
                break;

            case R.id.bankManagement:
                homePageHelperClass.navigate("bankManagement");
                break;
            case R.id.salesReq:
                homePageHelperClass.navigate(HomeUtils.salesReq);
                break;
            case R.id.purchaseRequisition:
                homePageHelperClass.navigate("Purchase Requisition");
                break;

            case R.id.reconciliation:
                homePageHelperClass.navigate(HomeUtils.reconciliation);
                break;

            case R.id.millerProfile:
                homePageHelperClass.navigate("Miller");
                break;

            case R.id.settings:
                homePageHelperClass.navigate("Settings");
                break;

            case R.id.receiptView:
                bundle = new Bundle();
                bundle.putString("portion", getString(R.string.receipt_history_for_dashboard));
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_transactionListFragmentt, bundle);
                //    homePageHelperClass.homePageToAccountListFragment(receipt, AccountsUtil.receiptList, destinationPath);
                break;

            case R.id.paymentView:

                bundle = new Bundle();
                bundle.putString("portion", getString(R.string.payment_history_for_dashboard));
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_transactionListFragmentt, bundle);

                homePageHelperClass.homePageToAccountListFragment(payment, AccountsUtil.paymentList, destinationPath);
                break;

            case R.id.expenseView:
              //  homePageHelperClass.homePageToAccountListFragment(expense, AccountsUtil.vendorPaymentList, destinationPath);
                break;
            case R.id.seeMoreBtn:

                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_dashboardFragment, bundle1);
                break;

            case R.id.profilePortion:
                binding.drawerLayout.closeDrawers();
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_profileFragment);
                break;

            case R.id.logout:
                if (!(isInternetOn(getActivity()))) {
                    infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
                    return;
                }
                logoutViewModel.logout(getActivity()).observe(getViewLifecycleOwner(), message -> {
                    binding.drawerLayout.closeDrawers();
                    Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);
                });

                break;

            case R.id.navBtn:
                if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT))
                    binding.drawerLayout.closeDrawer(Gravity.LEFT);
                else binding.drawerLayout.openDrawer(Gravity.LEFT);
                break;

            case R.id.bellBtn:
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_notificationFragment);
                break;

            case R.id.qrScan:
                binding.drawerLayout.closeDrawers();
                try {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else {
                        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_qrScanFragment);
                    }
                } catch (Exception e) {
                }
                break;


            case R.id.saleCircle:
                try {
                    if (PermissionUtil.currentUserPermissionList(PreferenceManager.getInstance(getContext()).getUserCredentials().getPermissions()).contains(1277)) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            bundle.putString("startDate", DataModify.currentDate());
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            bundle.putString("endDate", DataModify.currentDate());
                        }
                        bundle.putString("portion", ReportUtils.saleReport);
                        bundle.putString("pageName", "Sale Report");
                        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_purchaseReturnListFragment, bundle);
                        return;
                    } else {
                        Toasty.info(getContext(), PermissionUtil.permissionMessage, Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                }
                break;


        }
    }


    @Override
    public void onStart() {
        super.onStart();
        binding.drawer.versionCodeTV.setText("VERSION " + VersionName.getVersionName(getActivity()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_qrScanFragment);
            } else {
                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationUtil.IS_LOGGED_IN = false;
    }

    private void autoScrollTv() {
        binding.homeRvPortion.saleRequisitionTv.setSelected(true);
        binding.homeRvPortion.purchaseRequisitionTv.setSelected(true);
        binding.homeRvPortion.bankManagementTv.setSelected(true);
        binding.homeRvPortion.bankManagementTv.setSelected(true);
        binding.homeRvPortion.stockManagemetTv.setSelected(true);
    }

    private void setAnimationToImage() {
        AnimationTime.animation(binding.homeRvPortion.itemImageV);
        AnimationTime.animation(binding.homeRvPortion.purchaseImageV);
        AnimationTime.animation(binding.homeRvPortion.pReqImageV);
        AnimationTime.animation(binding.homeRvPortion.productionImageV);
        AnimationTime.animation(binding.homeRvPortion.saleImage);
        AnimationTime.animation(binding.homeRvPortion.sReqImageV);
        AnimationTime.animation(binding.homeRvPortion.stockImageV);
        AnimationTime.animation(binding.homeRvPortion.reconciliationImageV);
        AnimationTime.animation(binding.homeRvPortion.bankImageV);
        AnimationTime.animation(binding.homeRvPortion.accountImageV);
        AnimationTime.animation(binding.homeRvPortion.customerImageV);
        AnimationTime.animation(binding.homeRvPortion.supplierImageV);
        AnimationTime.animation(binding.homeRvPortion.qcqaImage);
        AnimationTime.animation(binding.homeRvPortion.userImage);
        AnimationTime.animation(binding.homeRvPortion.reportImageV);
        AnimationTime.animation(binding.homeRvPortion.imageExpenseSubItem);
        AnimationTime.animation(binding.homeRvPortion.imageExpenseSubItemArrow);
        AnimationTime.animation(binding.homeRvPortion.imageRevenueSubItem);
        AnimationTime.animation(binding.homeRvPortion.imageRevenueSubItemArrow);
        AnimationTime.animation(binding.homeRvPortion.imageTransactionSubItem);
        AnimationTime.animation(binding.homeRvPortion.imageTransactionSubItemArrow);
        AnimationTime.animation(binding.tolvaLayout.bellBtn);
        AnimationTime.animation(binding.tolvaLayout.navBtn);
    }
}