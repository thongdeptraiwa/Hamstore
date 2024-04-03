package com.example.hamstore.ADT.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_thong_ke_doanh_thu;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_thong_ke_san_pham;

public class ADT_ViewPager_thong_ke extends FragmentStatePagerAdapter {
    public ADT_ViewPager_thong_ke(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_Admin_thong_ke_san_pham();
            case 1:
                return new fragment_Admin_thong_ke_doanh_thu();
            default:
                return new fragment_Admin_thong_ke_san_pham();
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
                title = "Sản phẩm";
                break;
            case 1:
                title = "Doanh thu";
                break;
        }
        return title;
    }
}
