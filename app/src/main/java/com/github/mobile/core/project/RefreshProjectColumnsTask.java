package com.github.mobile.core.project;

import android.accounts.Account;
import android.content.Context;
import android.util.Log;

import com.github.mobile.api.model.Project;
import com.github.mobile.api.model.ProjectColumn;
import com.github.mobile.api.service.PaginationService;
import com.github.mobile.api.service.ProjectService;
import com.github.mobile.ui.ProgressDialogTask;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.Collection;

/**
 * Task to refresh a project's columns
 */
public class RefreshProjectColumnsTask extends ProgressDialogTask<Collection<ProjectColumn>> {

    private static final String TAG = "RefreshProjectColumnsTask";

    @Inject
    private ProjectService service;

    private final Project project;

    /**
     * Create task for context and project
     *
     * @param context
     * @param project
     */
    public RefreshProjectColumnsTask(Context context, Project project) {
        super(context);

        this.project = project;
    }

    @Override
    protected Collection<ProjectColumn> run(Account account) throws Exception {
        return new PaginationService<ProjectColumn>() {
            @Override
            public Collection<ProjectColumn> getSinglePage(int page, int itemsPerPage) throws IOException {
                return service.getColumns(project.id, page).execute().body();
            }
        }.getAll();
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        super.onException(e);

        Log.d(TAG, "Exception loading columns for project", e);
    }
}
