package com.ismos_salt_erp.view.fragment.miller.editmiller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.view.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditMillerFragment extends BaseFragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;


    private String slId;//from previous selected item

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_miller, container, false);
        ButterKnife.bind(this, view);
        init();
        getDataFromPreviousFragment();
        toolbar.setText("Update miller");
        /**
         * add tab
         */
        tabLayout.addTab(tabLayout.newTab().setText("Profile Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Owner info"));
        tabLayout.addTab(tabLayout.newTab().setText("License info"));
        tabLayout.addTab(tabLayout.newTab().setText("QC info"));
        tabLayout.addTab(tabLayout.newTab().setText("Employee Info"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //below inside fragment we use getChildFragmentManager()
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @NotNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        Log.d("SLID",slId);
                        return new MillerProfileInformationEdit(slId);
                    case 1:
                       // return new MillerCompanyOwnerInfoEdit(slId);
                        return new MillerOwnerListEdit(slId,"OWNER_INFO_EDIT");
                    case 2:
                        //return new MillerLicenseInfoEdit(slId);
                        return new MillerOwnerListEdit(slId,"LICENSE_INFO_EDIT");
                    case 3:
                        return new MillerQcInformationEdit(slId);
                    case 4:
                        return new MillerEmployeeInformationEdit(slId);
                }
                return null;
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));//atar mane hosse fragment scroll er sathe tab o change hobe.

        //if you want to change something new below this is the function
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //if you want to change something new
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void getDataFromPreviousFragment() {
        assert getArguments() != null;
        slId = getArguments().getString("slID");
    }

    private void init() {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        hideKeyboard(getActivity());
        getActivity().onBackPressed();
    }

}