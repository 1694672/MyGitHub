package com.github.mobile.ui.user;

import com.github.mobile.core.ResourcePager;
import com.github.mobile.core.user.UserPager;

import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;

/**
 * Fragment to display a list of followers
 */
public class MyFollowersFragment extends FollowersFragment {

    @Override
    protected ResourcePager<User> createPager() {
        return new UserPager() {

            @Override
            public PageIterator<User> createIterator(int page, int size) {
                return service.pageFollowers(page, size);
            }
        };
    }
}