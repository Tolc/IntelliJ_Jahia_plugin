package fr.tolc.jahia.language.cnd.psi;

import com.intellij.psi.tree.TokenSet;

public interface CndTokenSets {

    TokenSet IDENTIFIERS = TokenSet.create(CndTypes.NS_NAME, CndTypes.NT_NAME);

    TokenSet COMMENTS = TokenSet.create(CndTypes.COMMENT);
}
