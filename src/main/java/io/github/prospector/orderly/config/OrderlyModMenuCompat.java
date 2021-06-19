package io.github.prospector.orderly.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

@SuppressWarnings("unused")
public class OrderlyModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OrderlyConfigImpl::createConfigScreen;
    }
}
