package com.example.hamstore.ADT.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hamstore.fragment.Users.fragment_don_mua_da_xu_ly;
import com.example.hamstore.fragment.Users.fragment_don_mua_dang_xu_ly;

public class ADT_ViewPager_don_mua extends FragmentStatePagerAdapter {
    public ADT_ViewPager_don_mua(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_don_mua_dang_xu_ly();
            case 1:
                return new fragment_don_mua_da_xu_ly();
            default:
                return new fragment_don_mua_dang_xu_ly();
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
                title = "Đang xử lý";
                break;
            case 1:
                title = "Đã xử lý";
                break;
        }
        return title;
    }
}
