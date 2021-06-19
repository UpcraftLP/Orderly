package io.github.prospector.orderly;

import io.github.prospector.orderly.api.UIManager;
import io.github.prospector.orderly.config.OrderlyConfigManager;
import io.github.prospector.orderly.ui.DefaultUIStyle;
import io.github.prospector.orderly.ui.SaoUIStyle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Orderly implements ClientModInitializer {

    public static final String MODID = "orderly";
    private static FabricKeyBinding toggleKey;
    private static final Logger log = LogManager.getLogger(MODID);

    public static Logger getLogger() {
        return log;
    }

    static {
        //configure debug logging if certain flags are set. this also ensures compatibility with mainline Mesh-Library debug behaviour, without directly depending on the library
        if (Boolean.getBoolean("fabric.development") || Boolean.getBoolean("orderly.debug") || Boolean.getBoolean("mesh.debug") || Boolean.getBoolean("mesh.debug.logging")) {
            Configurator.setLevel(MODID, Level.ALL);
        }
    }

    @Override
    public void onInitializeClient() {
        //TODO find a good place for registering those
        final Identifier defaultStyle = new Identifier(MODID, "default");
        final Identifier saoStyle = new Identifier(MODID, "sao_like");
        UIManager.registerStyle(defaultStyle, DefaultUIStyle::getInstance);
        UIManager.registerStyle(saoStyle, SaoUIStyle::new);
        UIManager.setCurrentStyle(defaultStyle);
        OrderlyConfigManager.init();
        toggleKey = FabricKeyBinding.Builder.create(new Identifier(Orderly.MODID, "toggle"), InputUtil.Type.KEYSYM, InputUtil.UNKNOWN_KEY.getCode(), "key.categories.misc").build();
        KeyBindingRegistry.INSTANCE.register(toggleKey);
        ClientTickCallback.EVENT.register(event -> {
            if (event.isWindowFocused() && toggleKey.wasPressed()) {
                OrderlyConfigManager.getConfig().toggleDraw();
                OrderlyConfigManager.save();
            }
        });
    }
}
