package com.ismos_salt_erp.view.fragment.miller.addNewMiller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.view.fragment.BaseFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddNewMiller extends BaseFragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static boolean isTabPermission = false;
    @BindView(R.id.toolbarTitle)
    TextView toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_new_miller, container, false);
        ButterKnife.bind(this, view);
        toolbar.setText("Add new miller");
        init();
        /**
         * add tab
         */
        tabLayout.addTab(tabLayout.newTab().setText("Profile Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Owner info"));
        tabLayout.addTab(tabLayout.newTab().setText("License info"));
        tabLayout.addTab(tabLayout.newTab().setText("QC info"));
        tabLayout.addTab(tabLayout.newTab().setText("Employee Info"));
        if (isTabPermission) {
            tabLayout.setSelected(true);
        } else {
            tabLayout.setSelected(false);
        }

        if (!isTabPermission) {
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (isTabPermission) {
                    viewPager.setCurrentItem(tab.getPosition());
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
                if (!isTabPermission) {
                    if (tab.getPosition() != 0) {
                        infoMessage(getActivity().getApplication(), "Before move next tab need to submit this tab");
                    }
                }
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
                        return new MillerProfileInformation();
                    case 1:
                        return new CompanyOwnerInfo();
                    case 2:
                        return new MillerLicenseInfo();
                    case 3:
                        return new MillerQcInformation();
                    case 4:
                        return new MillerEmployeeInformation();
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


        if (isTabPermission) {
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
        }
        return view;
    }


    private void init() {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    @OnClick(R.id.backbtn)
    public void backBtnClick() {
        isTabPermission = false;
        hideKeyboard(getActivity());
        getActivity().onBackPressed();
    }
}