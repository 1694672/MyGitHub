package com.github.mobile.ui;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * Pager that stores current fragment
 */
public abstract class FragmentStatePagerAdapter extends
        android.support.v4.app.FragmentStatePagerAdapter implements
        FragmentProvider {

    private final AppCompatActivity activity;

    private Fragment selected;

    /**
     * @param activity
     */
    public FragmentStatePagerAdapter(final AppCompatActivity activity) {
        super(activity.getSupportFragmentManager());

        this.activity = activity;
    }

    @Override
    public Fragment getSelected() {
        return selected;
    }

    @Override
    public void setPrimaryItem(final ViewGroup container, final int position,
            final Object object) {
        super.setPrimaryItem(container, position, object);

        boolean changed = false;
        if (object instanceof Fragment) {
            changed = object != selected;
            selected = (Fragment) object;
        } else {
            changed = object != null;
            selected = null;
        }

        if (changed)
            activity.invalidateOptionsMenu();
    }
}
