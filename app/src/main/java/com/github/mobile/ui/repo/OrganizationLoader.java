package com.github.mobile.ui.repo;

import android.accounts.Account;
import android.app.Activity;
import android.util.Log;

import com.github.mobile.R;
import com.github.mobile.accounts.AuthenticatedUserLoader;
import com.github.mobile.core.user.UserComparator;
import com.github.mobile.persistence.AccountDataManager;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.egit.github.core.User;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Load of a {@link List} or {@link User} organizations
 */
public class OrganizationLoader extends AuthenticatedUserLoader<List<User>> {

    private static final String TAG = "OrganizationLoader";

    private final Provider<UserComparator> userComparatorProvider;

    private final AccountDataManager accountDataManager;

    /**
     * Create organization loader
     *
     * @param activity
     * @param accountDataManager
     * @param userComparatorProvider
     */
    @Inject
    public OrganizationLoader(Activity activity,
            AccountDataManager accountDataManager,
            Provider<UserComparator> userComparatorProvider) {
        super(activity);

        this.accountDataManager = accountDataManager;
        this.userComparatorProvider = userComparatorProvider;
    }

    @Override
    protected List<User> getAccountFailureData() {
        return Collections.emptyList();
    }

    @Override
    public List<User> load(final Account account) {
        List<User> orgs;
        try {
            orgs = accountDataManager.getOrgs(false);
        } catch (final IOException e) {
            Log.e(TAG, "Exception loading organizations", e);
            ToastUtils.show(activity, e, R.string.error_orgs_load);
            return Collections.emptyList();
        }
        Collections.sort(orgs, userComparatorProvider.get());
        return orgs;
    }
}
