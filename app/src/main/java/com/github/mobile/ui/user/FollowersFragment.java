package com.github.mobile.ui.user;

import android.os.Bundle;

import com.github.mobile.R;


/**
 * Fragment to display a list of followers
 */
public abstract class FollowersFragment extends PagedUserFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_followers);
    }

    @Override
    protected int getLoadingMessage() {
        return R.string.loading_followers;
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_followers_load;
    }
}
