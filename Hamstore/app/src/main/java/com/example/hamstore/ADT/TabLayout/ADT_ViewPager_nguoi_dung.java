package com.example.hamstore.ADT.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hamstore.fragment.Admin.nguoi_dung.fragment_Admin_nguoi_dung_khoa;
import com.example.hamstore.fragment.Admin.nguoi_dung.fragment_Admin_nguoi_dung_mo;

public class ADT_ViewPager_nguoi_dung extends FragmentStatePagerAdapter {
    public ADT_ViewPager_nguoi_dung(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_Admin_nguoi_dung_mo();
            case 1:
                return new fragment_Admin_nguoi_dung_khoa();
            default:
                return new fragment_Admin_nguoi_dung_mo();
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
                title = "Mở";
                break;
            case 1:
                title = "Khóa";
                break;
        }
        return title;
    }
}
