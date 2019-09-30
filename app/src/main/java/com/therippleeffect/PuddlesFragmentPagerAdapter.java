package com.therippleeffect;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PuddlesFragmentPagerAdapter extends FragmentPagerAdapter {
    Context mcontext;
    public PuddlesFragmentPagerAdapter (Context context, FragmentManager fm){
        super(fm);
        mcontext=context;
    }
    @Override
    public Fragment getItem(int position) {
        if (position==0){ return new MyPuddlesFragment();}
        else if (position==1){return new MyRipplesFragment();}
        else return new SearchForPuddles();
    }

    @Override
    public int getCount() {return 3;}

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "My Puddles";
        }
        else if (position ==1)
            return "My Ripples";
        else return "Search for new Puddles";
    }
}
