package com.github.mobile.ui.repo;

import com.github.mobile.core.ResourcePager;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.StargazerService;

import java.io.IOException;

/**
 * Fragment to display the list of starred repositories for a {@link User}
 */
public class UserStarredRepositoryListFragment extends UserRepositoryListFragment {

    @Inject
    private StargazerService service;

    @Override
    protected ResourcePager<Repository> createPager() {
        return new ResourcePager<Repository>() {

            @Override
            protected Object getId(Repository resource) {
                return resource.getId();
            }

            @Override
            public PageIterator<Repository> createIterator(int page, int size) {
                try {
                    return service.pageStarred(user.getLogin(), page, size);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
