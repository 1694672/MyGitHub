package com.github.mobile.ui.gist;

import com.github.mobile.core.ResourcePager;
import com.github.mobile.core.gist.GistPager;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.client.PageIterator;

/**
 * Fragment to display a list of Gists
 */
public class StarredGistsFragment extends GistsFragment {

    @Override
    protected ResourcePager<Gist> createPager() {
        return new GistPager(store) {

            @Override
            public PageIterator<Gist> createIterator(int page, int size) {
                return service.pageStarredGists(page, size);
            }
        };
    }
}
