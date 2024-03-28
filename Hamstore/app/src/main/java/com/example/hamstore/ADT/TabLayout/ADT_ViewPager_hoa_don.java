package com.example.hamstore.ADT.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hamstore.fragment.Admin.fragment_Admin_hoa_don_chua_thanh_toan;
import com.example.hamstore.fragment.Admin.fragment_Admin_hoa_don_da_thanh_toan;

public class ADT_ViewPager_hoa_don extends FragmentStatePagerAdapter {
    public ADT_ViewPager_hoa_don(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_Admin_hoa_don_da_thanh_toan();
            case 1:
                return new fragment_Admin_hoa_don_chua_thanh_toan();
            default:
                return new fragment_Admin_hoa_don_da_thanh_toan();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Đã thanh toán";
                break;
            case 1:
                title = "Chưa thanh toán";
                break;
        }
        return title;
    }
}
