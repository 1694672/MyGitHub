package com.github.mobile.ui.search;

import android.content.Context;
import android.view.LayoutInflater;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.api.model.User;
import com.github.mobile.util.AvatarLoader;

/**
 * Adapter for a list of searched users
 */
public class SearchUserListAdapter extends SingleTypeAdapter<User> {

    private final AvatarLoader avatars;

    /**
     * Create user list adapter
     *
     * @param context
     * @param elements
     * @param avatars
     */
    public SearchUserListAdapter(final Context context,
            final User[] elements, final AvatarLoader avatars) {
        super(LayoutInflater.from(context), R.layout.user_item);

        this.avatars = avatars;
        setItems(elements);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).id;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.iv_avatar, R.id.tv_login };
    }

    @Override
    protected void update(final int position, final User user) {
        avatars.bind(imageView(0), user);
        setText(1, user.login);
    }
}
