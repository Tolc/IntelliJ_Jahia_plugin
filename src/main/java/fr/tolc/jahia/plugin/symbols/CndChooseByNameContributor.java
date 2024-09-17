package fr.tolc.jahia.plugin.symbols;

import com.intellij.navigation.ChooseByNameContributorEx;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.indexing.FindSymbolParameters;
import com.intellij.util.indexing.IdFilter;
import fr.tolc.jahia.language.cnd.CndUtil;
import fr.tolc.jahia.language.cnd.psi.CndNodetype;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

//TODO: namespaces, properties and subnodes
public class CndChooseByNameContributor implements ChooseByNameContributorEx {

    @Override
    public void processNames(@NotNull Processor<? super String> processor, @NotNull GlobalSearchScope scope, @Nullable IdFilter filter) {
        Project project = Objects.requireNonNull(scope.getProject());
        List<String> propertyKeys = ContainerUtil.map(CndUtil.findNodetypes(project), CndNodetype::getName);
        ContainerUtil.process(propertyKeys, processor);
    }

    @Override
    public void processElementsWithName(@NotNull String name, @NotNull Processor<? super NavigationItem> processor, @NotNull FindSymbolParameters parameters) {
        List<NavigationItem> properties = ContainerUtil.map(CndUtil.findNodetypes(parameters.getProject(), name), nodetype -> (NavigationItem) nodetype);
        ContainerUtil.process(properties, processor);
    }
}
