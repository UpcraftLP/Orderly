package io.github.prospector.orderly.api;

import io.github.prospector.orderly.Orderly;
import io.github.prospector.orderly.ui.DefaultUIStyle;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public final class UIManager {

    private static final Map<Identifier, Supplier<UIStyle>> STYLES = new HashMap<>();
    private static UIStyle current;
    private static Identifier currentID;

    public static void registerStyle(Identifier identifier, Supplier<UIStyle> style) {
        if (STYLES.putIfAbsent(identifier, style) != null) {
            Orderly.getLogger().error("attempted to override UI style {}, this is not allowed!", identifier, new IllegalStateException(identifier + " registered twice"));
        }
    }

    public static void setCurrentStyle(Identifier style) {
        current = STYLES.computeIfAbsent(style, k -> DefaultUIStyle::getInstance).get();
        currentID = style;
    }

    public static UIStyle getCurrentStyle() {
        return current;
    }

    public static Identifier getCurrentID() {
        return currentID;
    }

    public Set<Identifier> getRegisteredStyles() {
        return Collections.unmodifiableSet(STYLES.keySet());
    }
}
