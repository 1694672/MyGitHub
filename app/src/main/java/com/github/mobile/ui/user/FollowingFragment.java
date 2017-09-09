package com.github.mobile.ui.user;

import android.os.Bundle;

import com.github.mobile.R;


/**
 * Fragment to display a list of users being followed
 */
public abstract class FollowingFragment extends PagedUserFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_people);
    }

    @Override
    protected int getLoadingMessage() {
        return R.string.loading_people;
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_people_load;
    }
}
