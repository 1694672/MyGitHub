package com.github.mobile.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.github.kevinsawicki.wishlist.ViewUtils;
import com.github.mobile.Intents.Builder;
import com.github.mobile.R;
import com.github.mobile.api.model.Project;
import com.github.mobile.api.model.ProjectColumn;
import com.github.mobile.core.project.RefreshProjectColumnsTask;
import com.github.mobile.ui.TabPagerActivity;
import com.github.mobile.ui.repo.RepositoryViewActivity;
import com.github.mobile.util.AvatarLoader;
import com.github.mobile.util.ToastUtils;
import com.google.inject.Inject;

import org.eclipse.egit.github.core.Repository;

import java.util.Collection;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static com.github.mobile.Intents.EXTRA_POSITION;
import static com.github.mobile.Intents.EXTRA_PROJECT;
import static com.github.mobile.Intents.EXTRA_REPOSITORY;

/**
 * Activity to view a project's columns
 */
public class ProjectViewActivity extends TabPagerActivity<ProjectPagerAdapter> {

    /**
     * Create intent for this activity
     *
     * @param repository
     * @param project
     * @return intent
     */
    public static Intent createIntent(Repository repository, Project project) {
        return new Builder("project.VIEW").repo(repository).project(project).toIntent();
    }

    @Inject
    private AvatarLoader avatars;

    private Repository repository;

    private List<ProjectColumn> columns;

    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = getSerializableExtra(EXTRA_REPOSITORY);
        Project project = getSerializableExtra(EXTRA_PROJECT);
        loadingBar = finder.find(R.id.pb_loading);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(project.name);
        actionBar.setSubtitle(repository.generateId());
        avatars.bind(actionBar, repository.getOwner());

        ViewUtils.setGone(loadingBar, false);
        setGone(true);
        new RefreshProjectColumnsTask(this, project) {
            @Override
            protected void onSuccess(Collection<ProjectColumn> items) throws Exception {
                super.onSuccess(items);

                columns = (List<ProjectColumn>) items;
                configurePager();
            }

            @Override
            protected void onException(Exception e) throws RuntimeException {
                super.onException(e);

                ToastUtils.show(ProjectViewActivity.this, R.string.error_repo_load);
                ViewUtils.setGone(loadingBar, true);
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = RepositoryViewActivity.createIntent(repository);
            intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP | FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private void configurePager() {
        configureTabPager();
        ViewUtils.setGone(loadingBar, true);
        setGone(false);
        int initialPosition = getIntExtra(EXTRA_POSITION);
        if (initialPosition > -1 && initialPosition < adapter.getCount()) {
            pager.setItem(initialPosition);
        }
    }

    @Override
    protected ProjectPagerAdapter createAdapter() {
        return new ProjectPagerAdapter(this, columns);
    }

    @Override
    protected int getContentView() {
        return R.layout.tabbed_progress_pager;
    }
}
