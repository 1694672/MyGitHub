package com.github.mobile.ui.user;

import android.content.Context;

import com.github.mobile.core.ResourcePager;
import com.github.mobile.core.user.UserPager;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;

import static com.github.mobile.Intents.EXTRA_USER;

/**
 * Fragment to display a list of followers
 */
public class UserFollowersFragment extends FollowersFragment {

    private User user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        user = getSerializableExtra(EXTRA_USER);
    }

    @Override
    protected ResourcePager<User> createPager() {
        return new UserPager() {

            @Override
            public PageIterator<User> createIterator(int page, int size) {
                return service.pageFollowers(user.getLogin(), page, size);
            }
        };
    }
}
