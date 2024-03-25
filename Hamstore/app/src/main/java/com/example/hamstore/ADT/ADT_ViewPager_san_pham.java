package com.example.hamstore.ADT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hamstore.fragment.fragment_Admin_TabLayout_hamster;
import com.example.hamstore.fragment.fragment_Admin_hoa_don_chua_thanh_toan;
import com.example.hamstore.fragment.fragment_Admin_hoa_don_da_thanh_toan;
import com.example.hamstore.fragment.fragment_Admin_phu_kien;
import com.example.hamstore.fragment.fragment_Admin_thuc_an;

public class ADT_ViewPager_san_pham extends FragmentStatePagerAdapter {
    public ADT_ViewPager_san_pham(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_Admin_TabLayout_hamster();
            case 1:
                return new fragment_Admin_phu_kien();
            case 2:
                return new fragment_Admin_thuc_an();
            default:
                return new fragment_Admin_TabLayout_hamster();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Hamster";
                break;
            case 1:
                title = "Phụ kiện";
                break;
            case 2:
                title = "Thức ăn";
                break;
        }
        return title;
    }
}
