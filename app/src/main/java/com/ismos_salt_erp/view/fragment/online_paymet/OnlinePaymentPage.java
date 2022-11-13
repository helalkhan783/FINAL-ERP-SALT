package com.ismos_salt_erp.view.fragment.online_paymet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.ismos_salt_erp.R;
import com.ismos_salt_erp.clickHandle.ToolbarClickHandle;
import com.ismos_salt_erp.databinding.FragmentOnlinePaymentPageBinding;
import com.ismos_salt_erp.view.fragment.miller.addNewMiller.CompanyOwnerInfo;
import com.ismos_salt_erp.view.fragment.miller.addNewMiller.MillerEmployeeInformation;
import com.ismos_salt_erp.view.fragment.miller.addNewMiller.MillerLicenseInfo;
import com.ismos_salt_erp.view.fragment.miller.addNewMiller.MillerProfileInformation;
import com.ismos_salt_erp.view.fragment.miller.addNewMiller.MillerQcInformation;

import org.jetbrains.annotations.NotNull;


public class OnlinePaymentPage extends Fragment {
    private FragmentOnlinePaymentPageBinding binding;


    public static boolean isTabPermission = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_online_payment_page, container, false);
        binding.toolbar.toolbarTitle.setText("Online Payment");
        binding.toolbar.setClickHandle(new ToolbarClickHandle() {
            @Override
            public void backBtn() {
                getActivity().onBackPressed();
            }
        });
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Bkash"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Rocket"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Nogod"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Upay"));


        binding.tabLayout.setSelected(true);


        binding.viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                binding.viewPager.setCurrentItem(tab.getPosition());
                binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);


                if (tab.getPosition() != 0) {
                    // infoMessage(getActivity().getApplication(), "Before move next tab need to submit this tab");
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

                    case 3: return new MillerQcInformation();

                }
                return null;
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
        binding.viewPager.setAdapter(fragmentPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));//atar mane hosse fragment scroll er sathe tab o change hobe.


        if (isTabPermission) {
            //if you want to change something new below this is the function
            binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        return binding.getRoot();
    }
}