package com.github.mobile.core.issue;

import com.github.mobile.api.model.ReactionSummary;
import com.github.mobile.api.model.TimelineEvent;

import org.eclipse.egit.github.core.Issue;

import java.util.Collection;

/**
 * Issue model with comments
 */
public class FullIssue {

    private final Issue issue;

    private ReactionSummary reactions;

    private Collection<TimelineEvent> events;

    /**
     * Create wrapper for issue, reactions, comments and events
     *
     * @param issue
     * @param reactions
     * @param events
     */
    public FullIssue(final Issue issue, final ReactionSummary reactions,
            final Collection<TimelineEvent> events) {
        this.issue = issue;
        this.reactions = reactions;
        this.events = events;
    }

    /**
     * @return issue
     */
    public Issue getIssue() {
        return issue;
    }

    /**
     * @return reactions
     */
    public ReactionSummary getReactions() {
        return reactions;
    }

    /**
     * @return events
     */
    public Collection<TimelineEvent> getEvents() {
        return events;
    }
}
