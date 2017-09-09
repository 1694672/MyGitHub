package com.github.mobile.ui.user;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.R;
import com.github.mobile.ui.FragmentPagerAdapter;
import com.github.mobile.ui.repo.UserOwnedRepositoryListFragment;
import com.github.mobile.ui.repo.UserStarredRepositoryListFragment;
import com.github.mobile.ui.team.TeamListFragment;

import static com.github.mobile.ui.user.UserViewActivity.TAB_ACTIVITY;
import static com.github.mobile.ui.user.UserViewActivity.TAB_FOLLOWEES;
import static com.github.mobile.ui.user.UserViewActivity.TAB_FOLLOWERS;
import static com.github.mobile.ui.user.UserViewActivity.TAB_REPOSITORIES;
import static com.github.mobile.ui.user.UserViewActivity.TAB_STARS;

/**
 * Pager adapter for a user's different views
 */
public class UserPagerAdapter extends FragmentPagerAdapter {

    private final boolean isOrg;

    private final boolean isMember;

    private final Resources resources;

    /**
     * @param activity
     */
    public UserPagerAdapter(final AppCompatActivity activity, final boolean isOrg, final boolean isMember) {
        super(activity);

        resources = activity.getResources();
        this.isOrg = isOrg;
        this.isMember = isMember;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
        case TAB_ACTIVITY:
            return new UserCreatedNewsFragment();
        case TAB_REPOSITORIES:
            return new UserOwnedRepositoryListFragment();
        case TAB_STARS:
            return isOrg ? new OrgMembersFragment() : new UserStarredRepositoryListFragment();
        case TAB_FOLLOWERS:
            return isOrg ? new TeamListFragment() : new UserFollowersFragment();
        case TAB_FOLLOWEES:
            return new UserFollowingFragment();
        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        if (isOrg) {
            return isMember ? 4: 3;
        }
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case TAB_ACTIVITY:
            return resources.getString(R.string.tab_news);
        case TAB_REPOSITORIES:
            return resources.getString(R.string.tab_repositories);
        case TAB_STARS:
            return resources.getString(isOrg ? R.string.tab_members : R.string.tab_stars);
        case TAB_FOLLOWERS:
            return resources.getString(isOrg ? R.string.tab_teams : R.string.tab_followers);
        case TAB_FOLLOWEES:
            return resources.getString(R.string.tab_following);
        default:
            return null;
        }
    }
}
