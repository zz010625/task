package com.example.a6_lv5.adapter.FragmentStateAdapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a6_lv5.fragment.GeneralFragment;

import java.util.ArrayList;

public class MyFragmentStateAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments;
    private int position = 0;

    public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    //重写方法 使删除fragment时界面能够得到刷新
    @Override
    public long getItemId(int position) {
        GeneralFragment generalFragment = (GeneralFragment) fragments.get(position);
        this.position = position;
        return super.getItemId(generalFragment.getFragmentId());


    }
}

