package com.github.mobile.ui.repo;

import android.app.Activity;
import android.os.Bundle;

import com.github.mobile.ui.user.OrganizationSelectionListener;
import com.github.mobile.ui.user.OrganizationSelectionProvider;

import org.eclipse.egit.github.core.User;

import static com.github.mobile.Intents.EXTRA_USER;

/**
 * Fragment to display the list of starred repositories
 */
public class StarredRepositoryListFragment extends UserStarredRepositoryListFragment
        implements OrganizationSelectionListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Activity activity = getActivity();
        User currentOrg = ((OrganizationSelectionProvider) activity).addListener(this);
        if (currentOrg == null && savedInstanceState != null)
            currentOrg = (User) savedInstanceState.getSerializable(EXTRA_USER);
        user = currentOrg;

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onOrganizationSelected(User organization) {
        user = organization;
    }
}
