package com.github.mobile.ui.gist;

import android.content.Intent;

import com.github.mobile.accounts.GitHubAccount;
import com.github.mobile.core.ResourcePager;
import com.github.mobile.core.gist.GistPager;
import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.PageIterator;

import static android.app.Activity.RESULT_OK;
import static com.github.mobile.RequestCodes.GIST_CREATE;
import static com.github.mobile.RequestCodes.GIST_VIEW;

/**
 * Fragment to display a list of Gists
 */
public class MyGistsFragment extends GistsFragment {

    @Inject
    private Provider<GitHubAccount> accountProvider;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == GIST_CREATE || requestCode == GIST_VIEW)
                && RESULT_OK == resultCode) {
            forceRefresh();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected ResourcePager<Gist> createPager() {
        return new GistPager(store) {

            @Override
            public PageIterator<Gist> createIterator(int page, int size) {
                return service.pageGists(accountProvider.get().getUsername(),
                        page, size);
            }
        };
    }
}
