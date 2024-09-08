package fr.tolc.jahia.language.cnd;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.Map;

public class CndColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Comments", CndSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("Namespaces", CndSyntaxHighlighter.NAMESPACE),
            new AttributesDescriptor("Nodetypes", CndSyntaxHighlighter.NODETYPE),
            new AttributesDescriptor("Keywords", CndSyntaxHighlighter.KEYWORD),
            new AttributesDescriptor("Properties", CndSyntaxHighlighter.CND_PROPERTY),
            new AttributesDescriptor("Properties//Hidden", CndSyntaxHighlighter.CND_PROPERTY_HIDDEN),
            new AttributesDescriptor("Properties//Mandatory", CndSyntaxHighlighter.CND_PROPERTY_MANDATORY),
            new AttributesDescriptor("Properties//Protected", CndSyntaxHighlighter.CND_PROPERTY_PROTECTED),
            new AttributesDescriptor("Properties//Multiple", CndSyntaxHighlighter.CND_PROPERTY_MULTIPLE),
            new AttributesDescriptor("Properties//Internationalized", CndSyntaxHighlighter.CND_PROPERTY_INTERNATIONALIZED),
            new AttributesDescriptor("Properties//Not searchable", CndSyntaxHighlighter.CND_PROPERTY_NOT_SEARCHABLE),
            new AttributesDescriptor("Types", CndSyntaxHighlighter.TYPE),
            new AttributesDescriptor("Sub nodes", CndSyntaxHighlighter.CND_SUBNODE),
            new AttributesDescriptor("Properties & sub nodes attributes", CndSyntaxHighlighter.ATTRIBUTE),
            new AttributesDescriptor("Separators", CndSyntaxHighlighter.SEPARATOR),
            new AttributesDescriptor("Bad characters", CndSyntaxHighlighter.BAD_CHARACTER),
    };

    @Override
    public Icon getIcon() {
        return CndIcons.CND_FILE;
    }

    @Override
    public @NotNull SyntaxHighlighter getHighlighter() {
        return new CndSyntaxHighlighter();
    }


    @Override
    public @NotNull String getDemoText() {
        return """
                /* Core jahia definitions */
                
                <nt = 'http://www.jcp.org/jcr/nt/1.0'>
                <mix = 'http://www.jcp.org/jcr/mix/1.0'>
                <jcr = 'http://www.jcp.org/jcr/1.0'>
                <j = 'http://www.jahia.org/jahia/1.0'>
                <jnt = 'http://www.jahia.org/jahia/nt/1.0'>
                <jmix = 'http://www.jahia.org/jahia/mix/1.0'>
                
                //---------------------------------------------------------
                // Default metadata
                //---------------------------------------------------------
                
                [jmix:categorized] mixin
                 extends = nt:hierarchyNode, jnt:content, jnt:page
                 itemtype = classification
                 - j:defaultCategory (weakreference, category[autoSelectParent=false]) facetable hierarchical multiple
                
                [jmix:lastPublished] mixin
                 itemtype = metadata
                 - j:published (boolean) = false hidden
                 - j:lastPublished (date) protected onconflict=ignore
                 - j:lastPublishedBy (string) protected nofulltext onconflict=ignore
                 - j:workInProgress (boolean) = false hidden nofulltext onconflict=ignore
                 - j:workInProgressStatus (string) hidden nofulltext onconflict=ignore < 'DISABLED', 'ALL_CONTENT', 'LANGUAGES'
                 - j:workInProgressLanguages (string) hidden nofulltext onconflict=ignore multiple
                
                [jmix:tagged] mixin
                 extends = nt:hierarchyNode, jnt:content, jnt:page
                 itemtype = metadata
                // j:tags and j:newTag deprecated
                 - j:tagList (string,tag[autocomplete=10,separator=',']) facetable nofulltext multiple
                 - j:tags (weakreference) multiple hidden
                 - j:newTag (string) hidden indexed=no
                
                [jmix:accessControlled] mixin
                 + j:acl (jnt:acl) = jnt:acl mandatory autocreated
                
                { bad characters""";
    }


    @Override
    public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @Override
    public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @Override
    public ColorDescriptor @NotNull [] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @Override
    public @NotNull @NlsContexts.ConfigurableName String getDisplayName() {
        return "CND";
    }
}
