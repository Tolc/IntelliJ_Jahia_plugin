package org.jahia.services.content.mod;

import org.jahia.services.content.JCRNodeWrapper;

import javax.jcr.RepositoryException;

/**
 * This interface exists solely for IntelliJ completion purposes
 * @see org.jahia.services.content.JCRNodeWrapper
 */
public interface JCRNodeWrapperMod extends JCRNodeWrapper {

    @Override
    LazyPropertyIteratorMod getProperties() throws RepositoryException;
}
