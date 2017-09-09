package com.github.mobile.ui.gist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.ui.FragmentStatePagerAdapter;

import static com.github.mobile.Intents.EXTRA_GIST_ID;

/**
 * Adapter to page through an array of Gists
 */
public class GistsPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] ids;

    /**
     * @param activity
     * @param gistIds
     */
    public GistsPagerAdapter(AppCompatActivity activity, String[] gistIds) {
        super(activity);

        this.ids = gistIds;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new GistFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_GIST_ID, ids[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return ids.length;
    }
}
