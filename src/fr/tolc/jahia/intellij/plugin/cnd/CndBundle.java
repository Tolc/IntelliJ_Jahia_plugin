package fr.tolc.jahia.intellij.plugin.cnd;

import java.lang.ref.Reference;
import java.util.ResourceBundle;

import com.intellij.CommonBundle;
import com.intellij.reference.SoftReference;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

public class CndBundle {
    private static Reference<ResourceBundle> bundle;

    @NonNls
    private static final String BUNDLE = "fr.tolc.jahia.intellij.plugin.cnd.CndBundle";

    public static String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = null;

        if (CndBundle.bundle != null) bundle = CndBundle.bundle.get();

        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE);
            CndBundle.bundle = new SoftReference<ResourceBundle>(bundle);
        }
        return bundle;
    }
}
