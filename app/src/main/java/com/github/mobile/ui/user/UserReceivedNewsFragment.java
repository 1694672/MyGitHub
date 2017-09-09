package com.github.mobile.ui.user;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;

/**
 * News that a given user has received
 */
public class UserReceivedNewsFragment extends UserNewsFragment {

    @Override
    protected ResourcePager<Event> createPager() {
        return new EventPager() {

            @Override
            public PageIterator<Event> createIterator(int page, int size) {
                return service.pageUserReceivedEvents(org.getLogin(), false,
                        page, size);
            }
        };
    }
}
