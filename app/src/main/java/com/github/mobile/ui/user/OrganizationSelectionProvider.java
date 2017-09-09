package com.github.mobile.ui.user;

import org.eclipse.egit.github.core.User;

/**
 * Interface to register and unregister a {@link OrganizationSelectionListener}
 */
public interface OrganizationSelectionProvider {

    /**
     * Add selection listener
     *
     * @param listener
     * @return the currently selected organization
     */
    User addListener(OrganizationSelectionListener listener);

    /**
     * Remove selection listener
     *
     * @param listener
     * @return this selection provider
     */
    OrganizationSelectionProvider removeListener(
            OrganizationSelectionListener listener);
}
