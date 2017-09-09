package com.github.mobile.ui.repo;

import android.content.Context;
import android.view.LayoutInflater;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.util.AvatarLoader;

import org.eclipse.egit.github.core.Contributor;

/**
 * List adapter for a list of contributors
 */
public class ContributorListAdapter extends SingleTypeAdapter<Contributor> {

    private final Context context;

    private final AvatarLoader avatars;

    /**
     * Create contributor list adapter
     *
     * @param context
     * @param elements
     * @param avatars
     */
    public ContributorListAdapter(final Context context,
            final Contributor[] elements, final AvatarLoader avatars) {
        super(LayoutInflater.from(context), R.layout.contributor_item);

        this.context = context.getApplicationContext();
        this.avatars = avatars;
        setItems(elements);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).getId();
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.iv_avatar, R.id.tv_login, R.id.tv_contributions };
    }

    @Override
    protected void update(int position, Contributor contributor) {
        avatars.bind(imageView(0), contributor);
        setText(1, contributor.getLogin());
        setText(2, context.getString(R.string.contributions, contributor.getContributions()));
    }
}
