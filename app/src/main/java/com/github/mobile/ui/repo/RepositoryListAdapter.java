package com.github.mobile.ui.repo;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.ViewUtils;
import com.github.mobile.util.TypefaceUtils;

/**
 * Adapter for a list of repositories
 *
 * @param <V>
 */
public abstract class RepositoryListAdapter<V> extends SingleTypeAdapter<V> {

    /**
     * Create list adapter
     *
     * @param viewId
     * @param inflater
     * @param elements
     */
    public RepositoryListAdapter(int viewId, LayoutInflater inflater,
            Object[] elements) {
        super(inflater, viewId);

        setItems(elements);
    }

    /**
     * Update repository details
     *
     * @param description
     * @param language
     * @param watchers
     * @param forks
     * @param isPrivate
     * @param isFork
     * @param mirrorUrl
     */
    protected void updateDetails(final String description,
            final String language, final int watchers, final int forks,
            final boolean isPrivate, final boolean isFork,
            final String mirrorUrl) {
        if (TextUtils.isEmpty(mirrorUrl))
            if (isPrivate)
                setText(0, TypefaceUtils.ICON_LOCK);
            else if (isFork)
                setText(0, TypefaceUtils.ICON_REPO_FORKED);
            else
                setText(0, TypefaceUtils.ICON_REPO);
        else {
            if (isPrivate)
                setText(0, TypefaceUtils.ICON_MIRROR);
            else
                setText(0, TypefaceUtils.ICON_MIRROR);
        }

        if (!TextUtils.isEmpty(description))
            ViewUtils.setGone(setText(1, description), false);
        else
            setGone(1, true);

        if (!TextUtils.isEmpty(language))
            ViewUtils.setGone(setText(2, language), false);
        else
            setGone(2, true);

        setNumber(3, watchers);
        setNumber(4, forks);
    }
}
