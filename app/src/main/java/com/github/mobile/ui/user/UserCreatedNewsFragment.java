package com.github.mobile.ui.user;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;

/**
 * News that a given user has created
 */
public class UserCreatedNewsFragment extends UserNewsFragment {

    @Override
    protected ResourcePager<Event> createPager() {
        return new EventPager() {

            @Override
            public PageIterator<Event> createIterator(int page, int size) {
                return service
                        .pageUserEvents(org.getLogin(), false, page, size);
            }
        };
    }
}
