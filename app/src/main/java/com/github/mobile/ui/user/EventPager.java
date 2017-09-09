package com.github.mobile.ui.user;

import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.event.Event;

/**
 * Pager over events
 */
public abstract class EventPager extends ResourcePager<Event> {

    @Override
    protected Object getId(Event resource) {
        return resource.getId();
    }

    @Override
    protected Event register(Event resource) {
        return NewsListAdapter.isValid(resource) ? resource : null;
    }
}
