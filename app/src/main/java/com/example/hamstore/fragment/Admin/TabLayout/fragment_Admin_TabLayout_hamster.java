package com.example.hamstore.fragment.Admin.TabLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hamstore.ADT.TabLayout.ADT_ViewPager2_hamster;
import com.example.hamstore.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class fragment_Admin_TabLayout_hamster extends Fragment {

    Context c;
    TabLayout tabLayout;
    ViewPager2 viewpager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tablayout_viewpager2,container,false);
        c = getActivity();
        //ánh xạ
        tabLayout= view.findViewById(R.id.tablayout);
        viewpager2= view.findViewById(R.id.viewpager2);

        ADT_ViewPager2_hamster adt_viewPager_hamster = new ADT_ViewPager2_hamster(getActivity());
        viewpager2.setAdapter(adt_viewPager_hamster);

        new TabLayoutMediator(tabLayout, viewpager2, (tab, position) -> {
                switch (position){
                    case 0:
                        tab.setText("Winter");
                        break;
                    case 1:
                        tab.setText("Robo");
                        break;
                    case 2:
                        tab.setText("Bear");
                        break;
                    case 3:
                        tab.setText("Campbell");
                        break;
                }
        }).attach();


        return view;
    }


}
