package com.github.mobile.ui.team;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.mobile.R;
import com.github.mobile.ThrowableLoader;
import com.github.mobile.api.model.Team;
import com.github.mobile.ui.ItemListFragment;
import com.github.mobile.ui.repo.RepositoryViewActivity;
import com.github.mobile.ui.repo.UserRepositoryListAdapter;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.TeamService;

import java.util.List;

import static com.github.mobile.Intents.EXTRA_TEAM;
import static com.github.mobile.Intents.EXTRA_USER;
import static com.github.mobile.RequestCodes.REPOSITORY_VIEW;

/**
 * Fragment to display a list of repositories for a {@link Team}
 */
public class TeamRepositoryListFragment extends ItemListFragment<Repository> {

    @Inject
    private TeamService service;

    private Team team;

    private User org;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        team = getSerializableExtra(EXTRA_TEAM);
        org = getSerializableExtra(EXTRA_USER);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_repositories);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_repos_load;
    }

    @Override
    protected SingleTypeAdapter<Repository> createAdapter(List<Repository> items) {
        return new UserRepositoryListAdapter(getActivity().getLayoutInflater(),
                items.toArray(new Repository[items.size()]), org);
    }

    @Override
    public Loader<List<Repository>> onCreateLoader(int id, Bundle args) {
        return new ThrowableLoader<List<Repository>>(getActivity(), items) {

            @Override
            public List<Repository> loadData() throws Exception {
                return service.getRepositories((int) team.id);
            }
        };
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        Repository repo = (Repository) list.getItemAtPosition(position);
        startActivityForResult(RepositoryViewActivity.createIntent(repo),
                REPOSITORY_VIEW);
    }
}
