package com.github.mobile.ui.comment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.util.AvatarLoader;
import com.github.mobile.util.HttpImageGetter;
import com.github.mobile.util.TimeUtils;

import org.eclipse.egit.github.core.Comment;

/**
 * Adapter for a list of {@link Comment} objects
 */
public class CommentListAdapter extends SingleTypeAdapter<Comment> {

    private final AvatarLoader avatars;

    private final HttpImageGetter imageGetter;

    /**
     * Create list adapter
     *
     * @param inflater
     * @param avatars
     * @param imageGetter
     */
    public CommentListAdapter(LayoutInflater inflater, AvatarLoader avatars,
                              HttpImageGetter imageGetter) {
        super(inflater, R.layout.comment_item);

        this.avatars = avatars;
        this.imageGetter = imageGetter;
    }

    @Override
    protected void update(int position, Comment comment) {
        imageGetter.bind(textView(0), comment.getBodyHtml(), comment.getId());
        avatars.bind(imageView(4), comment.getUser());

        setText(1, comment.getUser() == null ? "ghost" : comment.getUser().getLogin());
        setText(2, TimeUtils.getRelativeTime(comment.getCreatedAt()));
        setGone(3, !comment.getUpdatedAt().after(comment.getCreatedAt()));
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).getId();
    }

    @Override
    protected View initialize(View view) {
        view = super.initialize(view);

        textView(view, 0).setMovementMethod(LinkMovementMethod.getInstance());
        setGone(5, true);
        setGone(6, true);

        return view;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_comment_body, R.id.tv_comment_author,
                R.id.tv_comment_date, R.id.tv_comment_edited, R.id.iv_avatar,
                R.id.iv_comment_edit, R.id.iv_comment_delete };
    }
}
