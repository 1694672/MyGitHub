package com.github.mobile.ui.user;

import org.eclipse.egit.github.core.User;

/**
 * Callback interface when the selected organization changes
 */
public interface OrganizationSelectionListener {

    /**
     * Organization selection changed
     *
     * @param organization
     */
    void onOrganizationSelected(User organization);
}