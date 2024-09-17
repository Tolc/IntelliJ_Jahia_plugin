package fr.tolc.jahia.plugin.messages;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

public final class CndBundle {

    @NonNls
    private static final String BUNDLE = "messages.CndBundle";
    private static final DynamicBundle INSTANCE = new DynamicBundle(CndBundle.class, BUNDLE);

    private CndBundle() {
    }

    public static @NotNull @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
        return INSTANCE.getMessage(key, params);
    }

    public static Supplier<@Nls String> lazyMessage(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, Object @NotNull ... params) {
        return INSTANCE.getLazyMessage(key, params);
    }

}
