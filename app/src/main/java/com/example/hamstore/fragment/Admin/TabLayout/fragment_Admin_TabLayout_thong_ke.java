package com.example.hamstore.fragment.Admin.TabLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hamstore.ADT.TabLayout.ADT_ViewPager_nguoi_dung;
import com.example.hamstore.ADT.TabLayout.ADT_ViewPager_thong_ke;
import com.example.hamstore.R;
import com.google.android.material.tabs.TabLayout;

public class fragment_Admin_TabLayout_thong_ke extends Fragment {

    Context c;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout_viewpager,container,false);
        c = getActivity();
        //ánh xạ
        tabLayout= view.findViewById(R.id.tablayout);
        viewPager= view.findViewById(R.id.viewpager);

        ADT_ViewPager_thong_ke adt_viewPager_thong_ke = new ADT_ViewPager_thong_ke(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adt_viewPager_thong_ke);

        tabLayout.setupWithViewPager(viewPager);


        return view;
    }


}
