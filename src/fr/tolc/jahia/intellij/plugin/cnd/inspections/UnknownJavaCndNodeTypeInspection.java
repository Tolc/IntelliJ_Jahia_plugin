package fr.tolc.jahia.intellij.plugin.cnd.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import fr.tolc.jahia.intellij.plugin.cnd.CndBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class UnknownJavaCndNodeTypeInspection extends LocalInspectionTool {
    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return CndBundle.message("cnd.inspections.group.name");
    }

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return CndBundle.message("cnd.inspections.java.unknown-nodetype");
    }

//    @Nullable
//    @Override
//    public ProblemDescriptor[] checkFile(@NotNull PsiFile file, @NotNull InspectionManager manager, boolean isOnTheFly) {
//        final boolean isNmml = FileUtilRt.extensionEquals(file.getName(), NMMLFileType.DEFAULT_EXTENSION);
//        if (!isNmml || !(file instanceof XmlFile)) {
//            return ProblemDescriptor.EMPTY_ARRAY;
//        }
//        MyVisitor visitor = new MyVisitor();
//        file.accept(visitor);
//
//        if (ContainerUtil.exists(visitor.getResult(), new Condition<XmlTag>() {
//            @Override
//            public boolean value(XmlTag tag) {
//                final XmlAttribute ifAttribute = tag.getAttribute("if");
//                return "debug".equals(ifAttribute != null ? ifAttribute.getValue() : null);
//            }
//        })) {
//            // all good
//            return ProblemDescriptor.EMPTY_ARRAY;
//        }
//
//        final XmlTag lastTag = ContainerUtil.iterateAndGetLastItem(visitor.getResult());
//
//        if (lastTag == null) {
//            return ProblemDescriptor.EMPTY_ARRAY;
//        }
//
//        final ProblemDescriptor descriptor = manager.createProblemDescriptor(
//                lastTag,
//                HaxeBundle.message("haxe.inspections.nme.build.directory.descriptor"),
//                new AddTagFix(),
//                ProblemHighlightType.GENERIC_ERROR_OR_WARNING,
//                isOnTheFly
//        );
//
//        return new ProblemDescriptor[]{descriptor};
//    }
//
//    private static class MyVisitor extends XmlRecursiveElementVisitor {
//        final List<XmlTag> myResult = new ArrayList<XmlTag>();
//
//        public List<XmlTag> getResult() {
//            return myResult;
//        }
//
//        @Override
//        public void visitXmlTag(XmlTag tag) {
//            super.visitXmlTag(tag);
//            if ("set".equals(tag.getName())) {
//                final XmlAttribute name = tag.getAttribute("name");
//                if ("BUILD_DIR".equals(name != null ? name.getValue() : null)) {
//                    myResult.add(tag);
//                }
//            }
//        }
//    }
//
//    private static class AddTagFix implements LocalQuickFix {
//        @NotNull
//        @Override
//        public String getName() {
//            return HaxeBundle.message("haxe.inspections.nme.build.directory.fix.name");
//        }
//
//        @NotNull
//        @Override
//        public String getFamilyName() {
//            return getName();
//        }
//
//        @Override
//        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
//            final PsiElement element = descriptor.getPsiElement();
//            assert element instanceof XmlTag;
//
//            final XmlTag parentTag = ((XmlTag)element).getParentTag();
//            final PsiElement debugTag = parentTag.addAfter(element.copy(), element);
//            assert debugTag instanceof XmlTag;
//
//            appendValue((XmlTag)element, "release");
//            appendValue((XmlTag)debugTag, "debug");
//
//            ((XmlTag)debugTag).setAttribute("if", "debug");
//        }
//
//        private void appendValue(XmlTag element, String release) {
//            XmlAttribute outAttribute = ((XmlTag)element).getAttribute("value");
//            if (outAttribute != null) {
//                outAttribute.setValue(outAttribute.getValue() + "/" + release);
//            }
//        }
//    }
}
