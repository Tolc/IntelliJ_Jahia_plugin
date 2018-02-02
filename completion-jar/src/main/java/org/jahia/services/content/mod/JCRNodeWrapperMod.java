package org.jahia.services.content.mod;

import org.jahia.services.content.JCRNodeWrapper;

import javax.jcr.RepositoryException;

/**
 * This interface exists solely for IntelliJ completion purposes
 * @author tolc
 * @see JCRNodeWrapper
 */
public interface JCRNodeWrapperMod extends JCRNodeWrapper {

    @Override
    LazyPropertyIteratorMod getProperties() throws RepositoryException;
}
