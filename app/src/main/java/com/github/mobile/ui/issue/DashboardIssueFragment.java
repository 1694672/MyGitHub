package com.github.mobile.ui.issue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.api.model.Issue;
import com.github.mobile.api.service.PaginationService;
import com.github.mobile.api.service.SearchService;
import com.github.mobile.core.ResourcePager;
import com.github.mobile.ui.PagedItemFragment;
import com.github.mobile.util.AvatarLoader;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.github.mobile.RequestCodes.ISSUE_VIEW;
import static org.eclipse.egit.github.core.service.IssueService.FIELD_FILTER;

/**
 * Fragment to display a pageable list of dashboard issues
 */
public class DashboardIssueFragment extends PagedItemFragment<Issue> {

    /**
     * Filter data argument
     */
    public static final String ARG_FILTER = "filter";

    @Inject
    private SearchService service;

    /*@Inject
    private IssueStore store;*/

    @Inject
    private AvatarLoader avatars;

    private Map<String, String> filterData;

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        filterData = (Map<String, String>) getArguments().getSerializable(
                ARG_FILTER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ISSUE_VIEW) {
            notifyDataSetChanged();
            forceRefresh();
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        startActivityForResult(
                IssuesViewActivity.createIntentWithNewModel(items, position
                        - getListAdapter().getHeadersCount()), ISSUE_VIEW);
    }

    @Override
    protected ResourcePager<Issue> createPager() {
        return new ResourcePager<Issue>() {

            // TODO
            /*@Override
            protected Issue register(Issue resource) {
                return store.addIssue(resource);
            }*/

            @Override
            protected Object getId(Issue resource) {
                return resource.id;
            }

            @Override
            public Iterator<Collection<Issue>> createIterator(int page, int size) {
                return new PaginationService<Issue>(page) {
                    @Override
                    public Collection<Issue> getSinglePage(int page, int itemsPerPage) throws IOException {
                        return service.searchIssues(filterData.get(FIELD_FILTER), page).execute().body().items;
                    }
                }.getIterator();
            }
        };
    }

    @Override
    protected int getLoadingMessage() {
        return R.string.loading_issues;
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_issues_load;
    }

    @Override
    protected SingleTypeAdapter<Issue> createAdapter(
            List<Issue> items) {
        return new DashboardIssueListAdapter(avatars, getActivity().getResources(),
                getActivity().getLayoutInflater(),
                items.toArray(new Issue[items.size()]));
    }
}
