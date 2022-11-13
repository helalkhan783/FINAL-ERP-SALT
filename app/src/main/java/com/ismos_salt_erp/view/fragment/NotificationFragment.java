package com.ismos_salt_erp.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.NotificationListAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.manage_notification_click.GotoDetails;
import com.ismos_salt_erp.serverResponseModel.LoginResponse;
import com.ismos_salt_erp.serverResponseModel.NotificationListResponse;

import com.ismos_salt_erp.view.fragment.accounts.receiveDue.DueCollectionFragment;
import com.ismos_salt_erp.viewModel.CurrentPermissionViewModel;
import com.ismos_salt_erp.viewModel.NotificationListViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


import org.jetbrains.annotations.NotNull;

public class NotificationFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener , GotoDetails {
    List<NotificationListResponse> notificationListResponseList = new ArrayList<>();

    private NotificationListViewModel notificationListViewModel;
    private CurrentPermissionViewModel currentPermissionViewModel;
    String vendorId, token;


    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not
    @BindView(R.id.toolbarTitle)
    TextView toolbar;
    @BindView(R.id.notification_list_rv)
    RecyclerView notificationListRv;
    @BindView(R.id.empty_notification_list_warning)
    TextView emtyWarning;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    View view;
    @BindView(R.id.expendableEnterPriseList)
    ExpandableLayout expendableEnterPriseList;
    @BindView(R.id.filterBtnForNotification)
    ImageButton filterBtnForNotification;
    @BindView(R.id.status)
    SmartMaterialSpinner status;
    @BindView(R.id.dateOfBirth)
    TextView dateOfBirth;
    @BindView(R.id.customerSearchBtn)
    Button search;
    @BindView(R.id.customerResetBtn)
    Button resetBtn;
    /**
     * for pagination
     */
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public static int pageNumber = 1, isFirstLoad = 0;

    private boolean endScroll = false;
    private ProgressDialog progressDialog;
    private LinearLayoutManager linearLayoutManager;
    List<String> statusList = new ArrayList<>();
    String satutusId, positionSelected;
    String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        notificationListViewModel = ViewModelProviders.of(this).get(NotificationListViewModel.class);
        currentPermissionViewModel = new ViewModelProvider(this).get(CurrentPermissionViewModel.class);
        DueCollectionFragment.HIDE_KEYBOARD(getActivity());

        getDataFromPreviousFragment();

        initComponent();

        /**
         * get current user notification list from server
         */
        getNotificationList();

        /** for pagination **/
        notificationListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { //check for scroll down
                    expendableEnterPriseList.collapse();
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (endScroll) {
                                return;
                            }
                            loading = false;
                            pageNumber += 1;

                            getNotificationList();
                            loading = true;
                        }
                    }
                }
            }
        });

        filterBtnForNotification.setOnClickListener(v -> {
            if (expendableEnterPriseList.isExpanded()) {
                expendableEnterPriseList.setExpanded(false);
                return;
            }
            expendableEnterPriseList.setExpanded(true);
        });
        statusList.add("Approved");
        statusList.add("Pending");
        statusList.add("Declined");
        status.setItem(statusList);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    satutusId = "1";
                }
                if (position == 1) {
                    satutusId = "2";
                }
                if (position == 2) {
                    satutusId = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateOfBirth.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dialog = DatePickerDialog.newInstance(
                     NotificationFragment.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Initial day selection
            );
            dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationListResponseList.clear();
                isFirstLoad = 0;
                pageNumber = 1;
                getNotificationList();
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = null;
                satutusId = null;
                notificationListResponseList.clear();
                status.clearSelection();
                dateOfBirth.setText("");
                isFirstLoad = 0;
                pageNumber = 1;
                getNotificationList();


            }
        });

        return view;
    }

    private void getNotificationList() {
        progressDialog = new ProgressDialog(getContext());
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        if (pageNumber == 1) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
        }

        if (pageNumber > 1) {
//            binding.progress.setVisibility(View.VISIBLE);
//            binding.progress.setProgress(20);
//            binding.progress.setMax(100);
        }
        getNotificationListResponse(token, vendorId);

    }

    private void getDataFromPreviousFragment() {
        vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        token = PreferenceManager.getInstance(getContext()).getUserCredentials().getToken();
        toolbar.setText("Notifications");
    }


    private void initComponent() {
        refreshLayout.setColorSchemeResources(R.color.colorG, R.color.colorG, R.color.colorG);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            (new Handler()).postDelayed(() -> {
                refreshLayout.setRefreshing(false);
                getNotificationList();
            }, 500);
            Toasty.info(getActivity(), "Notification updated", Toasty.LENGTH_LONG).show();
        });
    }


    @SuppressLint("SetTextI18n")
    private void getNotificationListResponse(String token, String vendorID) {
        isDataFetching = true;
        notificationListViewModel.apiCallForGetNotificationList(getActivity(), String.valueOf(pageNumber), token, vendorID, satutusId, date)
                .observe(getViewLifecycleOwner(), notificationResponse -> {
                    progressDialog.dismiss();
                    if (notificationResponse == null || notificationResponse.getStatus() == 500) {
                        errorMessage(getActivity().getApplication(), "Something Wrong");
                        return;
                    }
                    if (notificationResponse.getStatus() == 400) {
                        infoMessage(getActivity().getApplication(), " " + notificationResponse.getMessage());
                        return;
                    }
                    //  notificationListResponseList.clear();
                    notificationListResponseList.addAll(notificationResponse.getNotifications());
                    //  notificationListResponseList = notificationResponse.getNotifications();


                    if (notificationResponse.getNotifications().isEmpty() || notificationResponse.getNotifications() == null) {
                        if (isFirstLoad > 0) {
                            endScroll = true;
                            pageNumber -= 1;
                            return;
                        }
                        notificationListRv.setVisibility(View.GONE);
                        emtyWarning.setVisibility(View.VISIBLE);
                        emtyWarning.setText("No data found");

                    } else {
                        isFirstLoad += 1;

                        emtyWarning.setVisibility(View.GONE);
                        notificationListRv.setVisibility(View.VISIBLE);

                        /**
                         * now set current all notification to view
                         */
                        notificationListRv.setHasFixedSize(true);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        notificationListRv.setLayoutManager(linearLayoutManager);
                        /**
                         * now set notification list to recyclerView
                         */
                        NotificationListAdapter adapter = new NotificationListAdapter(getActivity(), notificationListResponseList,NotificationFragment.this);
                        notificationListRv.setAdapter(adapter);
                    }
                });
        //notificationListViewModel.getNotificationList().observe(getViewLifecycleOwner(), notificationResponse -> {

        //});
    }

    @OnClick(R.id.backbtn)
    public void backClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pageNumber = 1;
        notificationListResponseList.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        date = null;
        satutusId = null;
        filterBtnForNotification.setVisibility(View.VISIBLE);

        try {
            /**
             * now update current credentials
             */
            updateCurrentUserPermission(getActivity());
        } catch (Exception e) {
            Log.d("ERROR", "" + e.getLocalizedMessage());
        }

    }


    public void updateCurrentUserPermission(FragmentActivity activity) {
        if (!(isInternetOn(getActivity()))) {
            infoMessage(getActivity().getApplication(), "Please Check Your Internet Connection");
            return;
        }
        currentPermissionViewModel.getCurrentUserRealtimePermissions(
                PreferenceManager.getInstance(activity).getUserCredentials().getToken(),
                PreferenceManager.getInstance(activity).getUserCredentials().getUserId(),
                PreferenceManager.getInstance(activity).getUserCredentials().getUserName()
        ).observe(getViewLifecycleOwner(), response -> {
            if (response == null) {
                Toasty.error(activity, "Something Wrong", Toasty.LENGTH_LONG).show();
                return;
            }
            if (response.getStatus() == 400) {
                Toasty.info(activity, "" + response.getMessage(), Toasty.LENGTH_LONG).show();
                return;
            }
            try {
                LoginResponse loginResponse = PreferenceManager.getInstance(activity).getUserCredentials();
                if (loginResponse != null) {
                    loginResponse.setPermissions(response.getMessage());
                    loginResponse.setToken(response.getToken());
                    PreferenceManager.getInstance(activity).saveUserCredentials(loginResponse);
                }
            } catch (Exception e) {
                infoMessage(getActivity().getApplication(), "" + e.getMessage());
                Log.d("ERROR", "" + e.getMessage());
            }
        });
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear;
        if (month == 12) {
            month = 1;
        } else {
            month = monthOfYear + 1;
        }
        String mainMonth, mainDay;
        if (month <= 9) {
            mainMonth = "0" + month;
        } else {
            mainMonth = String.valueOf(month);
        }
        if (dayOfMonth <= 9) {
            mainDay = "0" + dayOfMonth;
        } else {
            mainDay = String.valueOf(dayOfMonth);
        }
        String selectedDate = mainDay + "-" + mainMonth + "-" + year;//set the selected date
        dateOfBirth.setText(selectedDate);
        date = dateOfBirth.getText().toString();
    }

    @Override
    public void goToDetails(Integer type,String customerId,String status,String approval,String batchId){


        /**
         * if current notification getOrderApproval status is  = 2 then we can understand this is pending notification otherWise next page will  be
         * only view part
         *
         * Implement the logic below all condition
         * send status by bundle here status
         * 0 means next should be only details hide approve and decline btn
         * 1 means next should be approve and delineable
         *
         */


        /**
         * here 5 is a unique key for approve and decline pending purchase only
         */


        if (type == 22) {

            Bundle bundle = new Bundle();
            bundle.putString("typeKey", String.valueOf(type));
            bundle.putString("customerId", customerId);
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending  Customer Details");
            } else {
                bundle.putString("pageName", "Customer Details");
            }

        Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_customerDetailsFragment, bundle);
        }
        if (type == 23) {
            /**
             * local supplier
             */
            Bundle bundle = new Bundle();
            bundle.putString("typeKey", String.valueOf(type));
            bundle.putString("customerId", customerId);
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Local Supplier Pending Details");
            } else {
                bundle.putString("pageName", "Local Supplier Details");
            }
            //here blanks means supplier details
            Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_blankFragment, bundle);


        }
        if (type == 24) {
            /**
             * foreign supplier
             */
            Bundle bundle = new Bundle();
            bundle.putString("typeKey", String.valueOf(type));
            bundle.putString("customerId", customerId);
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Foreign Supplier Pending Details");
            } else {
                bundle.putString("pageName", "Foreign Supplier Details");
            }
            Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_customerDetailsFragment, bundle);

         }

        if (type == 5) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            bundle.putString("portion", "PENDING_PURCHASE");
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Purchase Details");
            } else {
                bundle.putString("pageName", "Purchase Details");
            }
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_pendingPurchaseDetailsFragment4, bundle);

             return;
        }

        if (type == 20) {
            Bundle bundle = new Bundle();
            bundle.putString("RefOrderId",customerId);
            // bundle.putString("portion", "PurchaseReturnDetails");
            bundle.putString("portion", "PENDING_PURCHASE");
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Purchase Return Details");
            } else {
                bundle.putString("pageName", "Purchase Return History Details");
            }
            bundle.putString("enterprise", "current.getStoreName()");

            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_purchaseReturnPendingDetailsFragment, bundle);

             return;
        }
        if (type == 25) {
            Bundle bundle = new Bundle();
            bundle.putString("SL_ID", customerId);
            bundle.putString("portion", "QC_QA_DETAILS");
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Qc-Qa Details");
            } else {
                bundle.putString("pageName", "Qc-Qa Details");
            }
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_qcqaDetailsFragment, bundle);


            return;
        }


        /**
         * here 2 is a unique key for approve and decline pending sales only
         */
        if (type == 2) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            bundle.putString("portion", "PENDING_SALE");
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Sales Details");
                Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_pendingPurchaseDetailsFragment, bundle);


                return;
            }
            bundle.putString("pageName", "Sales Details");
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_pendingPurchaseDetailsFragment4, bundle);

         }

        /**
         * here 6 is a unique key for approve and decline edit purchase  only
         */
        if (type == 6) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);

            if (!(approval.equals("2"))) {
                bundle.putString("portion", "PENDING_PURCHASE");//same page show key also same
                bundle.putString("porson", "PurchaseHistoryDetails");
                bundle.putString("pageName", "Purchase Details");
                Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_pendingPurchaseDetailsFragment, bundle);
                return;
            }
            bundle.putString("pageName", "Pending Purchase Details");
            bundle.putString("portion", "EDIT_PURCHASE");

            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_purchaseEditDetails, bundle);

        }


        /**
         * here 8 is a unique key for approve and decline edit SALE  only
         */
        if (type == 8) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            if (!(approval.equals("2"))) {
                //bundle.putString("status", "2");
                bundle.putString("portion", "PENDING_SALE");
                bundle.putString("porson", "SaleHistoryDetails");
                Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_pendingPurchaseDetailsFragment, bundle);


                return;
            } else {
                bundle.putString("pageName", "Pending Sales Details");
                bundle.putString("portion", "EDIT_SALE");
                Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_purchaseEditDetails, bundle);

             }
        }


        /**
         * for get Washing & Crushing Details
         */
        if (type == 7) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Washing & Crushing Details");
            } else {
                bundle.putString("pageName", "Washing & Crushing Details");
            }
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_getPendingWashingAndCrushingDetails, bundle);


         }

        /**
         * here 9 is a unique key for get edited Washing & Crushing Details
         */
        if (type == 9) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            bundle.putString("pageName", "Edited Washing & Crushing");
            //bundle.putString("portion", "EDIT_SALE");

            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_editedWashigAndCrushingDetails, bundle);

       //     Navigation.createNavigateOnClickListener(R.id.action_notificationFragment_to_editedWashigAndCrushingDetails, bundle).onClick(v);
        }
        /**
         * here 11 is a unique key for get pending iodization details
         */
        if (type == 11) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey",String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Iodization Details");
            } else {
                bundle.putString("pageName", "Iodization Details");
            }
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_pendingIodizationDetailsFragment, bundle);

         }

        /**
         * here 10 is a unique key for get edited iodization details
         */
        /**
         * get edited iodization details have (IodizationEditDetailsFragment) design complele without data
         * unComplete for wrong API
         */
        if (type == 10) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", customerId);
            bundle.putString("pageName", "Pending Iodization Details");
            bundle.putString("portion", "PENDING_IODIZATION_DETAILS");
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_iodizationEditDetailsFragment, bundle);


         }

        /**
         * for get actual order id remove first Q from the Ref_orderID
         */
        String refOrderId = customerId;
        if (refOrderId.startsWith("Q")) {
            refOrderId = refOrderId.substring(1);
        }
        if (refOrderId.startsWith("ex")) {
            refOrderId = refOrderId.substring(2);
        }

        /**
         * here 12  is a unique key for show Pending Quotation Details
         */
        if (type== 12) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("pageName", "Pending Quotation Details");
            bundle.putString("portion", "PENDING_QUOTATION_DETAILS");
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_pendingQuotationDetails, bundle);


        }
        /**
         * here 13  is a unique key for show Pending Transfer Details
         */
        if (type == 13) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey",String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Transfer Details");
            } else {
                bundle.putString("pageName", "Transfer Details");
            }

            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_transferDetailsFragment, bundle);

         }
        /**
         * here 14 is a unique key for show pending expense details
         */

        if (type== 14) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey",String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("pageName", "Pending Expense Details");
            bundle.putString("portion", "PENDING_EXPENSE_DETAILS");
            bundle.putString("status", status);
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_pendingExpenseDetails, bundle);
        }

        /**
         * here 15 is a unique key for show pending Edited Payment Details
         */
        if (type == 15) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("pageName", "Edited Payment Details");
            bundle.putString("portion", "EDITED_PENDING_PURCHASE_DETAILS");
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_pendingEditedPurchasePendingDetails, bundle);

         }

        /**
         * here 16 is a unique key for show pending reconciliation details
         */
        if (type == 16) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("portion", "PENDING_RECONCILIATION_DETAILS");
            if (approval.equals("2")) {
                bundle.putString("status", "2");
                bundle.putString("pageName", "Pending Reconciliation Details");
            } else {
                bundle.putString("pageName", "Reconciliation Details");
            }
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_reconciliationDetailsFragment, bundle);

         }

        /**
         * here 17 is a unique key for show pending reconciliation details
         */
        if (type == 17) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey",String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("portion", "SALES_RETURNS_DETAILS");
            bundle.putString("pageName", "Sales Return Details");
            if (approval.equals("2")) {
                bundle.putString("status", "2");
            }
            // Navigation.createNavigateOnClickListener(R.id.action_notificationFragment_to_pendingSalesReturnDetails, bundle).onClick(v);
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_purchaseReturnPendingDetailsFragment, bundle);

         }


        if (type == 18) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            //bundle.putString("pageName", "Sales Return Details");
            //bundle.putString("portion", "SALES_RETURNS_DETAILS");
            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_showSalesRequisition, bundle);


        }
        //here 4 means receipt or  payment details
        //here 36 means expense or vendor payment
        //here 37 means Payment or  payment details
        if (type == 4 || type == 36 || type == 37) {
            Bundle bundle = new Bundle();
            bundle.putString("typeKey", String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("batch", batchId);
            bundle.putString("customer", customerId);
            bundle.putString("status", status);

            Navigation.findNavController(getView()).navigate(R.id.action_notificationFragment_to_expenseDuePaymentApproveDetails, bundle);

         }


        if (type == 21) {
            Bundle bundle = new Bundle();
            bundle.putString("TypeKey", String.valueOf(type));
            bundle.putString("RefOrderId", refOrderId);
            bundle.putString("portion", "SALES_WHOLE_ORDER_CANCEL");
            if (approval.equals("2")) {
                bundle.putString("pageName", "Pending Sales Return Cancel Details");
                bundle.putString("status", "2");
                Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_pendingPurchaseDetailsFragment, bundle);
                 return;
            } else {
                bundle.putString("pageName", "Sales Return  Details");
            }
            // bundle.putString("portion", "SALES_RETURNS_CANCEL_WHOLE_ORDER_DETAILS");
            Navigation.findNavController(getView()).navigate(R.id.notificationListFragment_to_pendingPurchaseDetailsFragment, bundle);




        }


    }
}