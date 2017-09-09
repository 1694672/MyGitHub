package com.github.mobile.ui.repo;

import android.content.Context;

import com.github.mobile.core.ResourcePager;
import com.github.mobile.core.user.UserEventMatcher.UserPair;
import com.github.mobile.ui.NewsFragment;
import com.github.mobile.ui.issue.IssuesViewActivity;
import com.github.mobile.ui.user.EventPager;
import com.github.mobile.ui.user.UserViewActivity;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;

import static com.github.mobile.Intents.EXTRA_REPOSITORY;

/**
 * Fragment to display a news feed for a specific repository
 */
public class RepositoryNewsFragment extends NewsFragment {

    private Repository repo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        setShowRepoName(false);
        repo = getSerializableExtra(EXTRA_REPOSITORY);
    }

    @Override
    protected ResourcePager<Event> createPager() {
        return new EventPager() {

            @Override
            public PageIterator<Event> createIterator(int page, int size) {
                return service.pageEvents(repo, page, size);
            }
        };
    }

    /**
     * Start an activity to view the given repository
     *
     * @param repository
     */
    @Override
    protected void viewRepository(Repository repository) {
        if (!repo.generateId().equals(repository.generateId()))
            super.viewRepository(repository);
    }

    @Override
    protected void viewIssue(Issue issue, Repository repository) {
        startActivity(IssuesViewActivity.createIntent(issue, repo));
    }

    @Override
    protected boolean viewUser(User user) {
        if (repo.getOwner().getId() != user.getId()) {
            startActivity(UserViewActivity.createIntent(user));
            return true;
        }
        return false;
    }

    @Override
    protected void viewUser(UserPair users) {
        if (!viewUser(users.from))
            viewUser(users.to);
    }
}
