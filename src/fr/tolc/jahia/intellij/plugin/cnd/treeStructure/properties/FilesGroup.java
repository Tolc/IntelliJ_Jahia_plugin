/**
 * Copyright (c) 2016 by Bank Lombard Odier & Co Ltd, Geneva, Switzerland. This software is subject
 * to copyright protection under the laws of Switzerland and other countries. ALL RIGHTS RESERVED.
 */
package fr.tolc.jahia.intellij.plugin.cnd.treeStructure.properties;

import java.util.List;

import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import fr.tolc.jahia.intellij.plugin.cnd.model.ViewModel;
import org.jetbrains.annotations.NotNull;

public class FilesGroup {
    public static final DataKey<FilesGroup[]> ARRAY_DATA_KEY = DataKey.create("properties.group.resource.bundle.array");
    @NotNull
    private final List<PsiFile> myFiles;
    private final ViewModel myViewModel;

    public FilesGroup(@NotNull ViewModel viewModel, @NotNull List<PsiFile> files) {
        this.myViewModel = viewModel;
        this.myFiles = files;
    }

    public Project getProject() {
        return this.getDefaultFile().getProject();
    }

    @NotNull
    public List<PsiFile> getFiles() {
        return this.myFiles;
    }

    public ViewModel getViewModel() {
        return myViewModel;
    }

    @NotNull
    public PsiFile getDefaultFile() {
        return this.myFiles.get(0);
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        } else if(o != null && this.getClass() == o.getClass()) {
            FilesGroup filesGroup = (FilesGroup)o;
            return this.getDefaultFile().equals(filesGroup.getDefaultFile());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getDefaultFile().hashCode() ^ myViewModel.hashCode();
    }

    public String toString() {
        return "FilesGroup:";
    }
}
