package com.example.hamstore.ADT.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_cho_xac_nhan;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_da_giao_hang;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_da_xac_nhan;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_dang_van_chuyen;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_hoan_thanh;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_hoan_tra;
import com.example.hamstore.fragment.Admin.hoa_don.fragment_Admin_hoa_don_huy_bo;

public class ADT_ViewPager_hoa_don extends FragmentStatePagerAdapter {
    public ADT_ViewPager_hoa_don(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_Admin_hoa_don_cho_xac_nhan();
            case 1:
                return new fragment_Admin_hoa_don_da_xac_nhan();
            case 2:
                return new fragment_Admin_hoa_don_dang_van_chuyen();
            case 3:
                return new fragment_Admin_hoa_don_huy_bo();
            case 4:
                return new fragment_Admin_hoa_don_da_giao_hang();
            case 5:
                return new fragment_Admin_hoa_don_hoan_tra();
            case 6:
                return new fragment_Admin_hoa_don_hoan_thanh();
            default:
                return new fragment_Admin_hoa_don_cho_xac_nhan();
        }

    }

    @Override
    public int getCount() {
        return 7;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Chờ xác nhận";
                break;
            case 1:
                title = "Đã xác nhận";
                break;
            case 2:
                title = "Đang vận chuyển";
                break;
            case 3:
                title = "Hủy bỏ";
                break;
            case 4:
                title = "Đã giao hàng";
                break;
            case 5:
                title = "Hoàn trả";
                break;
            case 6:
                title = "Hoàn thành";
                break;
        }
        return title;
    }
}
