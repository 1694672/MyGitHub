package com.github.mobile.ui.search;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.R;
import com.github.mobile.ui.FragmentPagerAdapter;

/**
 * Adapter to view various pages of search screen
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create search pager adapter
     *
     * @param activity
     */
    public SearchPagerAdapter(AppCompatActivity activity) {
        super(activity);

        resources = activity.getResources();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.tab_repositories);
        case 1:
            return resources.getString(R.string.tab_users);
        default:
            return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return new SearchRepositoryListFragment();
        case 1:
            return new SearchUserListFragment();
        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
