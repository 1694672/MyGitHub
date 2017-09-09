package com.github.mobile.ui.user;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.github.mobile.R;
import com.github.mobile.ui.FragmentPagerAdapter;
import com.github.mobile.ui.repo.RepositoryListFragment;
import com.github.mobile.ui.repo.StarredRepositoryListFragment;
import com.github.mobile.ui.team.TeamListFragment;

import java.util.HashSet;
import java.util.Set;

/**
 * Pager adapter for a user's different views
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private boolean defaultUser;

    private final FragmentManager fragmentManager;

    private final Resources resources;

    private final Set<String> tags = new HashSet<String>();

    /**
     * @param activity
     * @param defaultUser
     */
    public HomePagerAdapter(final AppCompatActivity activity,
            final boolean defaultUser) {
        super(activity);

        fragmentManager = activity.getSupportFragmentManager();
        resources = activity.getResources();
        this.defaultUser = defaultUser;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return defaultUser ? new UserReceivedNewsFragment()
                    : new OrganizationNewsFragment();
        case 1:
            return new RepositoryListFragment();
        case 2:
            return defaultUser ? new StarredRepositoryListFragment()
                    : new OrgMembersFragment();
        case 3:
            return defaultUser ? new MyFollowersFragment() : new TeamListFragment();
        case 4:
            return new MyFollowingFragment();
        default:
            return null;
        }
    }

    /**
     * This methods clears any fragments that may not apply to the newly
     * selected org.
     *
     * @param isDefaultUser
     * @return this adapter
     */
    public HomePagerAdapter clearAdapter(boolean isDefaultUser) {
        defaultUser = isDefaultUser;

        if (tags.isEmpty())
            return this;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (String tag : tags) {
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment != null)
                transaction.remove(fragment);
        }
        transaction.commitNow();
        tags.clear();

        return this;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        if (fragment instanceof Fragment)
            tags.add(((Fragment) fragment).getTag());
        return fragment;
    }

    @Override
    public int getCount() {
        return defaultUser ? 5 : 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.tab_news);
        case 1:
            return resources.getString(R.string.tab_repositories);
        case 2:
            return resources.getString(defaultUser ? R.string.tab_stars
                    : R.string.tab_members);
        case 3:
            return resources.getString(defaultUser ? R.string.tab_followers_self
                    : R.string.tab_teams);
        case 4:
            return resources.getString(R.string.tab_following_self);
        default:
            return null;
        }
    }
}
