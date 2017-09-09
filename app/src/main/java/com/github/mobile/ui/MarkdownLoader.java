package com.github.mobile.ui;

import android.accounts.Account;
import android.content.Context;
import android.text.Html.ImageGetter;
import android.util.Log;

import com.github.mobile.accounts.AuthenticatedUserLoader;
import com.github.mobile.util.HtmlUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.service.MarkdownService;

import java.io.IOException;

import static org.eclipse.egit.github.core.service.MarkdownService.MODE_MARKDOWN;

/**
 * Markdown loader
 */
public class MarkdownLoader extends AuthenticatedUserLoader<CharSequence> {

    private static final String TAG = "MarkdownLoader";

    private final ImageGetter imageGetter;

    private final IRepositoryIdProvider repository;

    private final String raw;

    private boolean encode;

    @Inject
    private MarkdownService service;

    /**
     * @param context
     * @param repository
     * @param raw
     * @param imageGetter
     * @param encode
     */
    public MarkdownLoader(Context context, IRepositoryIdProvider repository,
            String raw, ImageGetter imageGetter, boolean encode) {
        super(context);

        this.repository = repository;
        this.raw = raw;
        this.imageGetter = imageGetter;
        this.encode = encode;
    }

    @Override
    protected CharSequence getAccountFailureData() {
        return null;
    }

    @Override
    public CharSequence load(Account account) {
        try {
            String html;
            if (repository != null)
                html = service.getRepositoryHtml(repository, raw);
            else
                html = service.getHtml(raw, MODE_MARKDOWN);

            if (encode)
                return HtmlUtils.encode(html, imageGetter);
            else
                return html;
        } catch (IOException e) {
            Log.d(TAG, "Loading rendered markdown failed", e);
            return null;
        }
    }
}
