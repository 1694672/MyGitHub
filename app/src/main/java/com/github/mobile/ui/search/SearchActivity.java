package com.github.mobile.ui.search;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.mobile.R;
import com.github.mobile.ui.TabPagerActivity;
import com.github.mobile.ui.user.HomeActivity;
import com.github.mobile.util.ToastUtils;
import com.github.mobile.util.TypefaceUtils;

import static android.app.SearchManager.QUERY;
import static android.content.Intent.ACTION_SEARCH;
import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * Activity to view search results
 */
public class SearchActivity extends TabPagerActivity<SearchPagerAdapter> {

    private SearchRepositoryListFragment repoFragment;

    private SearchUserListFragment userFragment;

    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        configurePager();

        searchView = new SearchView(getSupportActionBar().getThemedContext());
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchActivity.class)));

        // Hide the magnifying glass icon
        searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon).setLayoutParams(new LinearLayout.LayoutParams(0, 0));

        // Add the SearchView to the toolbar
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(searchView, layoutParams);

        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu options) {
        getMenuInflater().inflate(R.menu.search, options);

        return super.onCreateOptionsMenu(options);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.m_clear:
            RepositorySearchSuggestionsProvider.clear(this);
            ToastUtils.show(this, R.string.search_history_cleared);
            return true;
        case android.R.id.home:
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected SearchPagerAdapter createAdapter() {
        return new SearchPagerAdapter(this);
    }

    @Override
    protected String getIcon(int position) {
        switch (position) {
        case 0:
            return TypefaceUtils.ICON_REPO;
        case 1:
            return TypefaceUtils.ICON_PERSON;
        default:
            return super.getIcon(position);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(QUERY);

            // Update the SearchView's query
            searchView.setQuery(query, false);

            // Prevents the suggestions dropdown from showing after we submit
            searchView.post(new Runnable() {
                @Override public void run() {
                    searchView.clearFocus();
                }
            });

            search(query);
        }
    }

    private void search(final String query) {
        RepositorySearchSuggestionsProvider.save(this, query);

        findFragments();

        if (repoFragment != null && userFragment != null) {
            repoFragment.setListShown(false);
            userFragment.setListShown(false);

            repoFragment.refreshWithProgress();
            userFragment.refreshWithProgress();
        }
    }

    private void configurePager() {
        configureTabPager();
    }

    private void findFragments() {
        if (repoFragment == null || userFragment == null) {
            FragmentManager fm = getSupportFragmentManager();
            repoFragment = (SearchRepositoryListFragment) fm.findFragmentByTag(
                    "android:switcher:" + pager.getId() + ":" + 0);
            userFragment = (SearchUserListFragment) fm.findFragmentByTag(
                    "android:switcher:" + pager.getId() + ":" + 1);
        }
    }
}
