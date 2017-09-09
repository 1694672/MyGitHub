package com.github.mobile.ui.team;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.R;
import com.github.mobile.ui.FragmentPagerAdapter;

/**
 * Pager adapter for a team's different views
 */
public class TeamPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * @param activity
     */
    public TeamPagerAdapter(final AppCompatActivity activity) {
        super(activity);

        resources = activity.getResources();
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
        case 0:
            return new TeamMembersFragment();
        case 1:
            return new TeamRepositoryListFragment();
        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.tab_members);
        case 1:
            return resources.getString(R.string.tab_repositories);
        default:
            return null;
        }
    }
}
