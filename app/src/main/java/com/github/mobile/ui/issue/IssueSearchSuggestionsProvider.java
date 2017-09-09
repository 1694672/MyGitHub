package com.github.mobile.ui.issue;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;

/**
 * Suggestions provider for recently searched for issue queries
 */
public class IssueSearchSuggestionsProvider extends
        SearchRecentSuggestionsProvider {

    private static final String AUTHORITY = "jp.forkhub.search.suggest.recent.issues";

    /**
     * Save query to history
     *
     * @param context
     * @param query
     */
    public static void save(Context context, String query) {
        suggestions(context).saveRecentQuery(query, null);
    }

    /**
     * Clear query history
     *
     * @param context
     */
    public static void clear(Context context) {
        suggestions(context).clearHistory();
    }

    private static SearchRecentSuggestions suggestions(Context context) {
        return new SearchRecentSuggestions(context, AUTHORITY,
                DATABASE_MODE_QUERIES);
    }

    /**
     * Create suggestions provider for searched for issue queries
     */
    public IssueSearchSuggestionsProvider() {
        setupSuggestions(AUTHORITY, DATABASE_MODE_QUERIES);
    }
}
