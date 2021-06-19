package io.github.prospector.orderly.config;

import com.google.gson.*;
import io.github.prospector.orderly.Orderly;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrderlyConfigManager {

    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor(r -> new Thread(r, "Orderly Config Manager"));
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    private static OrderlyConfigImpl config;
    private static Path configFile;

    public static OrderlyConfigImpl getConfig() {
        return config != null ? config : init();
    }

    public static OrderlyConfigImpl init() {
        configFile = FabricLoader.getInstance().getConfigDirectory().toPath().resolve(Orderly.MODID + ".json");
        if (!Files.exists(configFile)) {
            Orderly.getLogger().info("creating orderly config file ({})", configFile::getFileName);
            save().join();
        }
        load().thenApply(c -> config = c).join();
        return Objects.requireNonNull(config, "failed to init config");
    }

    public static CompletableFuture<OrderlyConfigImpl> load() {
        return CompletableFuture.supplyAsync(() -> {
            try (BufferedReader reader = Files.newBufferedReader(configFile)) {
                return GSON.fromJson(reader, OrderlyConfigImpl.class);
            } catch (IOException | JsonParseException e) {
                Orderly.getLogger().error("unable to read config file, restoring defaults!", e);
                save();
                return new OrderlyConfigImpl();
            }
        }, EXECUTOR);
    }

    public static CompletableFuture<Void> save() {
        Orderly.getLogger().trace("saving orderly config file to {}", configFile);
        return CompletableFuture.runAsync(() -> {
            try (BufferedWriter writer = Files.newBufferedWriter(configFile)) {
                GSON.toJson(Optional.ofNullable(config).orElseGet(OrderlyConfigImpl::new), writer);
            } catch (IOException | JsonIOException e) {
                Orderly.getLogger().error("unable to write config file", e);
            }
        }, EXECUTOR);
    }
}
