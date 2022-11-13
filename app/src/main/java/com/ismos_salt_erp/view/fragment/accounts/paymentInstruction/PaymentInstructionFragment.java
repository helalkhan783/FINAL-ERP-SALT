package com.ismos_salt_erp.view.fragment.accounts.paymentInstruction;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ismos_salt_erp.R;
import com.ismos_salt_erp.adapter.AddNewLimitPaymentInstructionAdapter;
import com.ismos_salt_erp.adapter.PaymentInstructionAdapter;
import com.ismos_salt_erp.localDatabase.PreferenceManager;
import com.ismos_salt_erp.serverResponseModel.AddNewLimitInstructionResponse;
import com.ismos_salt_erp.serverResponseModel.PaymentInstructionResponse;
import com.ismos_salt_erp.utils.MtUtils;
import com.ismos_salt_erp.utils.replace.DataModify;
import com.ismos_salt_erp.utils.replace.ReplaceCommaFromString;
import com.ismos_salt_erp.view.fragment.BaseFragment;
import com.ismos_salt_erp.viewModel.AddNewLimitInstructionViewModel;
import com.ismos_salt_erp.viewModel.PaymentInstructionViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PaymentInstructionFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {
    List<PaymentInstructionResponse> paymentInstructionResponseList;
    List<AddNewLimitInstructionResponse> addNewLimitInstructionResponseList;
    private PaymentInstructionViewModel paymentInstructionViewModel;
    private AddNewLimitInstructionViewModel addNewLimitInstructionViewModel;
    private int mYear, mMonth, mDay;
    String vendorId, currentDate, userId, storeId;
    AddNewLimitPaymentInstructionAdapter adapter;

    private boolean isDataFetching = false; // variable to detect whether server data fetch is loading or not

    @BindView(R.id.dateEditText)
    EditText dateEditText;
    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.resetButton)
    Button resetButton;
    @BindView(R.id.payment_instruction_rv)
    RecyclerView paymentInstructionRv;
    @BindView(R.id.empty_instruction_list_warning)
    TextView emtyWarning;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.add_new_limit_rv)
    RecyclerView addNewLimitRv;
    @BindView(R.id.add_new_limit_button)
    Button addNewLimitButton;
    @BindView(R.id.note_edittext)
    EditText noteEdittext;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.submit_button)
    Button submitButton;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;

    View view;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment_instruction, container, false);
        ButterKnife.bind(this, view);
        paymentInstructionViewModel = ViewModelProviders.of(this).get(PaymentInstructionViewModel.class);
        addNewLimitInstructionViewModel = ViewModelProviders.of(this).get(AddNewLimitInstructionViewModel.class);
        vendorId = PreferenceManager.getInstance(getContext()).getUserCredentials().getVendorID();
        userId = PreferenceManager.getInstance(getContext()).getUserCredentials().getUserId();
        storeId = PreferenceManager.getInstance(getContext()).getUserCredentials().getStoreID();
        currentDate = getCurrentDate();
        addNewLimitButton.setVisibility(View.VISIBLE);
        initComponent();


        dateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /**
                 * get current date payment instruction from server
                 */
                getCurrentDateDataFromServer();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        dateEditText.setOnClickListener(v -> {

            java.util.Calendar now = java.util.Calendar.getInstance();
            com.wdullaer.materialdatetimepicker.date.DatePickerDialog dialog = DatePickerDialog.newInstance(
                    this,
                    now.get(java.util.Calendar.YEAR), // Initial year selection
                    now.get(java.util.Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );
            dialog.show(getActivity().getSupportFragmentManager(), "Datepickerdialog");
        });
        addNewLimitButton.setOnClickListener(v ->
                showAddNewLimitPaymentInstructionList()
        );
        searchButton.setOnClickListener(v -> {
            try {
                paymentInstructionResponseList.clear();
                addNewLimitInstructionResponseList.clear();
                getPaymentInstructionResponse(vendorId, dateEditText.getText().toString());
                showPaymentInstructionList();
            } catch (Exception e) {
                Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
            }
        });
        resetButton.setOnClickListener(v -> {
            try {
                // Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                if (dateEditText.getText().toString().equals(currentDate)) {
                    return;
                }
                dateEditText.setText(getCurrentDate());
                paymentInstructionResponseList.clear();
                addNewLimitInstructionResponseList.clear();
                getPaymentInstructionResponse(vendorId, dateEditText.getText().toString());
            } catch (Exception e) {
                Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
            }
        });
        submitButton.setOnClickListener(v -> {
            try {
                //if payment limit edit text is empty then return
                if (checkThePaymentLimitIsEmptyOrNot()) {
                    infoMessage(getActivity().getApplication(), "Please enter payment limit");
                    return;
                }

                //if there is no data to add new payment limit then simply return
                if (addNewLimitInstructionResponseList.isEmpty()) {
                    return;
                }

                //check if the payment limit is greater than the due amount or not
                //if payment limit is greater than due amount then don't submit the request
                //submit the request only when payment limit is equal or less than the due amount
                if (checkThePaymentLimitIsValidOrNot()) {
                    Toast.makeText(getContext(), "Payment limit should be equal or less than due limit", Toast.LENGTH_SHORT).show();
                    return;
                }


               /* String note = noteEdittext.getText().toString().trim();
                if (note.isEmpty()) {
                    noteEdittext.setError("Note is mandatory.");
                    noteEdittext.requestFocus();
                    return;
                }
*/
                submitNewPaymentLimit(getPaymentLimitAmount(), AddNewLimitPaymentInstructionAdapter.customerIdArray, userId, vendorId, storeId, noteEdittext.getText().toString(), dateEditText.getText().toString());

//            //reload the instruction list
//            paymentInstructionResponseList.clear();
//            addNewLimitInstructionResponseList.clear();
//            getPaymentInstructionResponse(vendorId, dateEditText.getText().toString());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();
                noteEdittext.setText("");
            } catch (Exception e) {
                Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
            }
        });

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initComponent() {
        toolbarTitle.setText("Payment Instruction");
        dateEditText.setText(getCurrentDate());
        /**
         * get current date  payment instruction from server
         */
        getCurrentDateDataFromServer();
    }


    public String getCurrentDate() {
        return getCustomCurrentDateTime("yyy-MM-dd");
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCurrentDateDataFromServer() {
        getPaymentInstructionResponse(vendorId, dateEditText.getText().toString());
        setTotalDue(addNewLimitInstructionResponseList);
        adapter = new AddNewLimitPaymentInstructionAdapter(getActivity(), addNewLimitInstructionResponseList);
        refreshLayout.setColorSchemeResources(R.color.colorG, R.color.colorG, R.color.colorG);
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            (new Handler()).postDelayed(() -> {
                refreshLayout.setRefreshing(false);
                getPaymentInstructionResponse(vendorId, dateEditText.getText().toString());
            }, 500);
            Toasty.info(getActivity(), "Data updated", Toasty.LENGTH_LONG).show();
        });
    }

    @SuppressLint("SetTextI18n")
    private void getPaymentInstructionResponse(String vendorID, String date) {
        isDataFetching = true;

        paymentInstructionRv.setHasFixedSize(true);
        paymentInstructionRv.setLayoutManager(new GridLayoutManager(getContext(), 1));

        paymentInstructionViewModel.apiCallForGetPaymentInstructionList(getActivity(), vendorID, date);
        paymentInstructionViewModel.getPaymentInstructionList().observe(getViewLifecycleOwner(), paymentInstruction -> {
            try {
                if (paymentInstruction.getPaymentInstructionResponseList() != null) {
                    paymentInstructionResponseList = paymentInstruction.getPaymentInstructionResponseList();
                    addNewLimitInstructionResponseList = paymentInstruction.getAddNewPaymentInstructionsList();
                    /**
                     * now set the recycler view
                     */
                    PaymentInstructionAdapter adapter = new PaymentInstructionAdapter(getActivity(), paymentInstructionResponseList);
                    paymentInstructionRv.setAdapter(adapter);
                }
            } catch (Exception e) {
                Log.d(this.getClass().getSimpleName(), e.getLocalizedMessage());
            }

        });
    }

    private void showPaymentInstructionList() {
        paymentInstructionRv.setHasFixedSize(true);
        paymentInstructionRv.setLayoutManager(new GridLayoutManager(getContext(), 1));

        PaymentInstructionAdapter adapter = new PaymentInstructionAdapter(getActivity(), paymentInstructionResponseList);
        paymentInstructionRv.setAdapter(adapter);
    }

    private void showAddNewLimitPaymentInstructionList() {

        addNewLimitRv.setHasFixedSize(true);
        addNewLimitRv.setLayoutManager(new GridLayoutManager(getContext(), 1));

       setTotalDue(addNewLimitInstructionResponseList);
        AddNewLimitPaymentInstructionAdapter adapter = new AddNewLimitPaymentInstructionAdapter(getActivity(), addNewLimitInstructionResponseList);
        addNewLimitRv.setAdapter(adapter);
    }

    private void setTotalDue(List<AddNewLimitInstructionResponse> addNewLimitInstructionResponseList) {
     try {
         double totalDue = 0.0;
         for (int i = 0; i < addNewLimitInstructionResponseList.size(); i++) {
             totalDue= totalDue +addNewLimitInstructionResponseList.get(i).getDue();
         }
         note.setText("Total Due : "+DataModify.addFourDigit(String.valueOf(totalDue)) + " " +MtUtils.priceUnit);
     }catch (Exception e){}
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void submitNewPaymentLimit(List<String> paymentLimitArray, List<String> customerIdArray, String userId, String vendorId, String storeId, String note, String paymentDate) {
        isDataFetching = true;

        addNewLimitInstructionViewModel.apiCallForSubmitNewPaymentLimit(getActivity(), paymentLimitArray, customerIdArray, userId, vendorId, storeId, note, paymentDate);
        addNewLimitInstructionViewModel.getAddNewPaymentInstructionList().observe(getViewLifecycleOwner(), addNewPaymentResponse -> {

            String message;
            message = addNewPaymentResponse.getMessage();
            // Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            successMessage(getActivity().getApplication(), message);
            //emtyWarning.setVisibility(View.VISIBLE);
            //emtyWarning.setText(message);
        });
    }

    private List<String> getPaymentLimitAmount() {
        List<String> paymentLimitAmount = new ArrayList<>();

        for (int i = 0; i < addNewLimitRv.getChildCount(); i++) {
            String value = ((EditText) addNewLimitRv.getLayoutManager().findViewByPosition(i).findViewById(R.id.payment_limit_edittext)).getText().toString();
            paymentLimitAmount.add(value);
        }
        return paymentLimitAmount;
    }


    private boolean checkThePaymentLimitIsValidOrNot() {
        for (int i = 0; i < addNewLimitRv.getChildCount(); i++) {
            String value = ((EditText) Objects.requireNonNull(Objects.requireNonNull(addNewLimitRv.getLayoutManager()).findViewByPosition(i)).findViewById(R.id.payment_limit_edittext)).getText().toString();
          if (value.isEmpty()){
              value = "0";
          }
            if (Integer.parseInt(value) > addNewLimitInstructionResponseList.get(i).getDue()) {
                ((EditText) Objects.requireNonNull(addNewLimitRv.getLayoutManager().findViewByPosition(i)).findViewById(R.id.payment_limit_edittext)).setError("Limit amount can't be greater than due amount");
                ((EditText) Objects.requireNonNull(addNewLimitRv.getLayoutManager().findViewByPosition(i)).findViewById(R.id.payment_limit_edittext)).requestFocus();
                return true;

            }

         }
        return false;
    }

    private boolean checkThePaymentLimitIsEmptyOrNot() {
        double value1 = 0.0;
        for (int i = 0; i < addNewLimitRv.getChildCount(); i++) {
            String value = ((EditText) Objects.requireNonNull(Objects.requireNonNull(addNewLimitRv.getLayoutManager()).findViewByPosition(i)).findViewById(R.id.payment_limit_edittext)).getText().toString();
            if (value.isEmpty()) {
                value = "0.0";
            }
            value1 += Double.parseDouble(value);
            if (value1 <= 0) {
                return true;
            }
        }
        return false;
    }


    @OnClick(R.id.backbtn)
    public void backClick() {
        getActivity().onBackPressed();
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
        String selectedDate = year + "-" + mainMonth + "-" + mainDay;//set the selected date
        dateEditText.setText(selectedDate);
    }
}