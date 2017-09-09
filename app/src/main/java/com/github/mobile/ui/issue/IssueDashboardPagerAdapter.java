package com.github.mobile.ui.issue;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.mobile.R;
import com.github.mobile.accounts.AccountUtils;
import com.github.mobile.ui.FragmentStatePagerAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.github.mobile.ui.issue.DashboardIssueFragment.ARG_FILTER;
import static org.eclipse.egit.github.core.service.IssueService.DIRECTION_DESCENDING;
import static org.eclipse.egit.github.core.service.IssueService.FIELD_DIRECTION;
import static org.eclipse.egit.github.core.service.IssueService.FIELD_FILTER;
import static org.eclipse.egit.github.core.service.IssueService.FIELD_SORT;
import static org.eclipse.egit.github.core.service.IssueService.SORT_UPDATED;

/**
 * Pager adapter for the issues dashboard
 */
public class IssueDashboardPagerAdapter extends FragmentStatePagerAdapter {

    private final Resources resources;

    private final String loggedUser;

    /**
     * Create pager adapter
     *
     * @param activity
     */
    public IssueDashboardPagerAdapter(final AppCompatActivity activity) {
        super(activity);

        resources = activity.getResources();
        loggedUser = AccountUtils.getLogin(activity);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(final int position) {
        String filter = "is:open ";
        switch (position) {
        case 0:
            filter += "author:" + loggedUser;
            break;
        case 1:
            filter += "assignee:" + loggedUser;
            break;
        case 2:
            filter += "mentions:" + loggedUser;
            break;
        default:
            return null;
        }
        final Map<String, String> filterData = new HashMap<String, String>();
        filterData.put(FIELD_FILTER, filter);
        filterData.put(FIELD_SORT, SORT_UPDATED);
        filterData.put(FIELD_DIRECTION, DIRECTION_DESCENDING);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_FILTER, (Serializable) filterData);
        DashboardIssueFragment fragment = new DashboardIssueFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.tab_created);
        case 1:
            return resources.getString(R.string.tab_assigned);
        case 2:
            return resources.getString(R.string.tab_mentioned);
        default:
            return null;
        }
    }
}
