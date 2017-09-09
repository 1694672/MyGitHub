package com.github.mobile.ui.user;

import com.github.mobile.accounts.AccountUtils;
import com.github.mobile.core.ResourcePager;

import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.event.Event;

/**
 * Fragment to display an organization's news
 */
public class OrganizationNewsFragment extends UserNewsFragment {

    @Override
    protected ResourcePager<Event> createPager() {
        return new EventPager() {

            @Override
            public PageIterator<Event> createIterator(int page, int size) {
                String account = AccountUtils.getLogin(getActivity());
                return service.pageUserOrgEvents(account, org.getLogin(), page,
                        size);
            }
        };
    }
}
