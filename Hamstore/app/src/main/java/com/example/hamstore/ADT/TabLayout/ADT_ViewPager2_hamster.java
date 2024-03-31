package com.example.hamstore.ADT.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.hamstore.fragment.Admin.san_pham.fragment_Admin_hamster_bear;
import com.example.hamstore.fragment.Admin.san_pham.fragment_Admin_hamster_campbell;
import com.example.hamstore.fragment.Admin.san_pham.fragment_Admin_hamster_robo;
import com.example.hamstore.fragment.Admin.san_pham.fragment_Admin_hamster_winter_white;

public class ADT_ViewPager2_hamster extends FragmentStateAdapter {


    public ADT_ViewPager2_hamster(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new fragment_Admin_hamster_winter_white();
            case 1:
                return new fragment_Admin_hamster_robo();
            case 2:
                return new fragment_Admin_hamster_bear();
            case 3:
                return new fragment_Admin_hamster_campbell();
            default:
                return new fragment_Admin_hamster_winter_white();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
