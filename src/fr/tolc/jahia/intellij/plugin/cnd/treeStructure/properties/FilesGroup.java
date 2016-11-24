/**
 * Copyright (c) 2016 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.properties;

import java.util.Collections;
import java.util.List;

import com.intellij.lang.properties.ResourceBundleManager;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class FilesGroup {
    public static final DataKey<FilesGroup[]> ARRAY_DATA_KEY = DataKey.create("properties.group.resource.bundle.array");
    @NotNull
    private final PsiFile myDefaultPropertiesFile;
    private boolean myValid = true;

    public FilesGroup(@NotNull PsiFile defaultPropertiesFile) {
        this.myDefaultPropertiesFile = defaultPropertiesFile;
    }

    public Project getProject() {
        return this.getDefaultPropertiesFile().getProject();
    }

    @NotNull
    public List<PsiFile> getFiles() {
        return Collections.singletonList(this.myDefaultPropertiesFile);
    }

    @NotNull
    public PsiFile getDefaultPropertiesFile() {
        return this.myDefaultPropertiesFile;
    }

    @NotNull
    public String getBaseName() {
        return ResourceBundleManager.getInstance(this.getProject()).getBaseName(this.myDefaultPropertiesFile.getContainingFile());
    }

    @NotNull
    public VirtualFile getBaseDirectory() {
        return this.myDefaultPropertiesFile.getParent().getVirtualFile();
    }

    public boolean isValid() {
        return this.myValid && this.myDefaultPropertiesFile.getContainingFile().isValid();
    }

    public void invalidate() {
        this.myValid = false;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            FilesGroup resourceBundle = (FilesGroup)o;
            return this.myDefaultPropertiesFile.equals(resourceBundle.myDefaultPropertiesFile);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.myDefaultPropertiesFile.hashCode();
    }

    public String getUrl() {
        return this.getBaseDirectory() + "/" + this.getBaseName();
    }

    public String toString() {
        return "PropertiesGroup:" + this.getBaseName();
    }
}
