package com.example.cdruecker.spoon;

import android.app.ActionBar;
import android.app.ActionBar.*;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by cdruecker on 7/21/14.
 */
public class TabListener implements ActionBar.TabListener {
    private Fragment mFragment;

    public TabListener(Fragment fragment) {
        this.mFragment = fragment;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        ft.replace(android.R.id.content, mFragment);
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        ft.remove(mFragment);
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // nothing done here
    }
}