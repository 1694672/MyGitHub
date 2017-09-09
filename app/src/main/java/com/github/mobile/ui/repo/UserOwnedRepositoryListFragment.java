package com.github.mobile.ui.repo;

import com.github.mobile.core.ResourcePager;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.service.RepositoryService;

/**
 * Fragment to display the list of owned repositories for a {@link User}
 */
public class UserOwnedRepositoryListFragment extends UserRepositoryListFragment {

    @Inject
    private RepositoryService service;

    @Override
    protected ResourcePager<Repository> createPager() {
        return new ResourcePager<Repository>() {

            @Override
            protected Object getId(Repository resource) {
                return resource.getId();
            }

            @Override
            public PageIterator<Repository> createIterator(int page, int size) {
                if (User.TYPE_ORG.equals(user.getType())) {
                    return service.pageOrgRepositories(user.getLogin(), page, size);
                }
                return service.pageRepositories(user.getLogin(), page, size);
            }
        };
    }
}
