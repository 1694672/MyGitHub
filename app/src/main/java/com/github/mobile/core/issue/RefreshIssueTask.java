package com.github.mobile.core.issue;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserTask;
import com.github.mobile.api.model.ReactionSummary;
import com.github.mobile.api.model.TimelineEvent;
import com.github.mobile.api.service.IssueService;
import com.github.mobile.api.service.PaginationService;
import com.github.mobile.util.HtmlUtils;
import com.github.mobile.util.HttpImageGetter;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Issue;

import java.io.IOException;
import java.util.Collection;

/**
 * Task to load and store an {@link Issue}
 */
public class RefreshIssueTask extends AuthenticatedUserTask<FullIssue> {

    private static final String TAG = "RefreshIssueTask";

    @Inject
    private IssueService service;

    @Inject
    private IssueStore store;

    private final IRepositoryIdProvider repositoryId;

    private final int issueNumber;

    private final HttpImageGetter bodyImageGetter;

    private final HttpImageGetter commentImageGetter;

    /**
     * Create task to refresh given issue
     *
     * @param context
     * @param repositoryId
     * @param issueNumber
     * @param bodyImageGetter
     * @param commentImageGetter
     */
    public RefreshIssueTask(Context context,
            IRepositoryIdProvider repositoryId, int issueNumber,
            HttpImageGetter bodyImageGetter, HttpImageGetter commentImageGetter) {
        super(context);

        this.repositoryId = repositoryId;
        this.issueNumber = issueNumber;
        this.bodyImageGetter = bodyImageGetter;
        this.commentImageGetter = commentImageGetter;
    }

    @Override
    public FullIssue run(Account account) throws Exception {
        Issue issue = store.refreshIssue(repositoryId, issueNumber);
        bodyImageGetter.encode(issue.getId(), issue.getBodyHtml());

        final String[] repo = repositoryId.generateId().split("/");
        PaginationService<TimelineEvent> paginationService = new PaginationService<TimelineEvent>(1, PaginationService.ITEMS_PER_PAGE_MAX) {
            @Override
            public Collection<TimelineEvent> getSinglePage(int page, int itemsPerPage) throws IOException {
                return service.getTimeline(repo[0], repo[1], issueNumber, page, itemsPerPage).execute().body();
            }
        };
        Collection<TimelineEvent> timelineEvents = paginationService.getAll();

        for (TimelineEvent comment : timelineEvents) {
            if (TimelineEvent.EVENT_COMMENTED.equals(comment.event)) {
                String formatted = HtmlUtils.format(comment.body_html).toString();
                comment.body_html = formatted;
                commentImageGetter.encode(comment.id, formatted);
            }
        }

        ReactionSummary reactions = new ReactionSummary();
        try {
            reactions = service.getIssue(repo[0], repo[1], issueNumber).execute().body().reactions;
        } catch (Exception e) {
            // Reactions are in a preview state, API can change, so make sure we don't crash if it does.
        }

        return new FullIssue(issue, reactions, timelineEvents);
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading issue", e);
    }
}
