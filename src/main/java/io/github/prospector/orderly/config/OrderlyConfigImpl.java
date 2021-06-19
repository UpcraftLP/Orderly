package io.github.prospector.orderly.config;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.annotations.SerializedName;
import io.github.prospector.orderly.Orderly;
import io.github.prospector.orderly.api.config.OrderlyConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
public class OrderlyConfigImpl implements OrderlyConfig {

    private static final transient String[] blacklistDefaults = new String[]{"minecraft:armor_stand", "minecraft:bee", "minecraft:cod", "minecraft:pufferfish", "minecraft:salmon", "minecraft:shulker", "minecraft:tropical_fish", "illuminations:firefly"};
    private static final transient String[] bossDefaults = new String[]{"minecraft:ender_dragon", "minecraft:wither"};
    /**
     * whether the mod is enabled
     */
    private boolean draw = true;
    private int maxDistance = 24;
    /**
     * whether to render health bars when the HUD is disabled by pressing F1
     */
    private boolean renderInF1 = false;
    /**
     * scale modifier for the health bar
     */
    private float healthBarScale = 1.0F;
    private double heightAbove = 0.6;
    /**
     * whether to draw the background
     */
    private boolean drawBackground = true;
    private int backgroundPadding = 2;
    private int backgroundHeight = 6;
    private int barHeight = 4;
    private int plateSize = 25;
    private int plateSizeBoss = 50;
    private boolean showAttributes = true;
    private boolean showArmor = true;
    private boolean groupArmor = true;
    private boolean colorByType = false;
    /**
     * (negative) offset for the health bar text relative to the entity name
     */
    private int hpTextHeight = 14;
    @SerializedName("show_max_hp") //need this to not have weird names in the config
    private boolean showMaxHP = true;
    @SerializedName("show_current_hp") //need this to not have weird names in the config
    private boolean showCurrentHP = true;
    private boolean showPercentage = true;
    private boolean showOnPlayers = true;
    private boolean showOnBosses = true;
    private boolean showOnlyFocused = false;
    private boolean enableDebugInfo = false;
    private Set<String> blacklist = Sets.newHashSet(blacklistDefaults);
    private Set<String> bosses = Sets.newHashSet(bossDefaults);

    static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableText(String.format("config.%s.title", Orderly.MODID)));
        OrderlyConfigImpl config = OrderlyConfigManager.getConfig();
        builder.getOrCreateCategory(new LiteralText("general"))
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.draw", Orderly.MODID)), config.canDraw()).setDefaultValue(true).setSaveConsumer(b -> config.draw = b).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.maxDistance", Orderly.MODID)), config.getMaxDistance()).setDefaultValue(24).setSaveConsumer(i -> config.maxDistance = i).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.renderInF1", Orderly.MODID)), config.canRenderInF1()).setDefaultValue(false).setSaveConsumer(b -> config.renderInF1 = b).build())
                .addEntry(ConfigEntryBuilder.create().startFloatField(new TranslatableText(String.format("config.%s.healthBarScale", Orderly.MODID)), config.getHealthBarScale()).setDefaultValue(1.0F).setSaveConsumer(d -> config.healthBarScale = d).build())
                .addEntry(ConfigEntryBuilder.create().startDoubleField(new TranslatableText(String.format("config.%s.heightAbove", Orderly.MODID)), config.getHeightAbove()).setDefaultValue(0.6D).setSaveConsumer(d -> config.heightAbove = d).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.drawBackground", Orderly.MODID)), config.drawsBackground()).setDefaultValue(true).setSaveConsumer(b -> config.drawBackground = b).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.backgroundPadding", Orderly.MODID)), config.getBackgroundPadding()).setDefaultValue(2).setSaveConsumer(i -> config.backgroundPadding = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.backgroundHeight", Orderly.MODID)), config.getBackgroundHeight()).setDefaultValue(6).setSaveConsumer(i -> config.backgroundHeight = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.barHeight", Orderly.MODID)), config.getBarHeight()).setDefaultValue(4).setSaveConsumer(i -> config.barHeight = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.plateSize", Orderly.MODID)), config.getPlateSize()).setDefaultValue(25).setSaveConsumer(i -> config.plateSize = i).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.plateSizeBoss", Orderly.MODID)), config.getPlateSizeBoss()).setDefaultValue(50).setSaveConsumer(i -> config.plateSizeBoss = i).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showAttributes", Orderly.MODID)), config.canShowAttributes()).setDefaultValue(true).setSaveConsumer(b -> config.showAttributes = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showArmor", Orderly.MODID)), config.canShowArmor()).setDefaultValue(true).setSaveConsumer(b -> config.showArmor = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.groupArmor", Orderly.MODID)), config.canShowGroupArmor()).setDefaultValue(true).setSaveConsumer(b -> config.groupArmor = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.colorByType", Orderly.MODID)), config.colorByType()).setDefaultValue(false).setSaveConsumer(b -> config.colorByType = b).build())
                .addEntry(ConfigEntryBuilder.create().startIntField(new TranslatableText(String.format("config.%s.hpTextHeight", Orderly.MODID)), config.getHpTextHeight()).setDefaultValue(14).setSaveConsumer(i -> config.hpTextHeight = i).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showMaxHP", Orderly.MODID)), config.canShowMaxHP()).setDefaultValue(true).setSaveConsumer(b -> config.showMaxHP = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showCurrentHP", Orderly.MODID)), config.showCurrentHP()).setDefaultValue(true).setSaveConsumer(b -> config.showCurrentHP = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showPercentage", Orderly.MODID)), config.canShowPercentage()).setDefaultValue(true).setSaveConsumer(b -> config.showPercentage = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showOnPlayers", Orderly.MODID)), config.canShowOnPlayers()).setDefaultValue(true).setSaveConsumer(b -> config.showOnPlayers = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showOnBosses", Orderly.MODID)), config.canShowOnBosses()).setDefaultValue(true).setSaveConsumer(b -> config.showOnBosses = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.showOnlyFocused", Orderly.MODID)), config.showingOnlyFocused()).setDefaultValue(false).setSaveConsumer(b -> config.showOnlyFocused = b).build())
                .addEntry(ConfigEntryBuilder.create().startBooleanToggle(new TranslatableText(String.format("config.%s.enableDebugInfo", Orderly.MODID)), config.isDebugInfoEnabled()).setDefaultValue(false).setSaveConsumer(b -> config.enableDebugInfo = b).build())
                .addEntry(ConfigEntryBuilder.create().startStrList(new TranslatableText(String.format("config.%s.blacklist", Orderly.MODID)), Lists.newArrayList(config.getBlacklist())).setCellErrorSupplier(value -> Optional.ofNullable(!Identifier.isValid(value) ? new TranslatableText("config.orderly.error.invalid_identifier") : null)).setDefaultValue(Lists.newArrayList(blacklistDefaults)).setExpanded(true).setSaveConsumer(strings -> config.blacklist = strings.stream().filter(Identifier::isValid).map(Identifier::new).map(Identifier::toString).collect(Collectors.toSet())).build())
                .addEntry(ConfigEntryBuilder.create().startStrList(new TranslatableText(String.format("config.%s.bosses", Orderly.MODID)), Lists.newArrayList(config.getBosses())).setCellErrorSupplier(value -> Optional.ofNullable(!Identifier.isValid(value) ? new TranslatableText("config.orderly.error.invalid_identifier") : null)).setDefaultValue(Lists.newArrayList(bossDefaults)).setExpanded(true).setSaveConsumer(strings -> config.bosses = strings.stream().filter(Identifier::isValid).map(Identifier::new).map(Identifier::toString).collect(Collectors.toSet())).build());
        builder.setSavingRunnable(OrderlyConfigManager::save);
        return builder.build();
    }

    @Override
    public boolean canDraw() {
        return draw;
    }

    @Override
    public int getMaxDistance() {
        return maxDistance;
    }

    @Override
    public boolean canRenderInF1() {
        return renderInF1;
    }

    @Override
    public float getHealthBarScale() {
        return healthBarScale;
    }

    @Override
    public double getHeightAbove() {
        return heightAbove;
    }

    @Override
    public boolean drawsBackground() {
        return drawBackground;
    }

    @Override
    public int getBackgroundPadding() {
        return backgroundPadding;
    }

    @Override
    public int getBackgroundHeight() {
        return backgroundHeight;
    }

    @Override
    public int getBarHeight() {
        return barHeight;
    }

    @Override
    public int getPlateSize() {
        return plateSize;
    }

    @Override
    public int getPlateSizeBoss() {
        return plateSizeBoss;
    }

    @Override
    public boolean canShowAttributes() {
        return showAttributes;
    }

    @Override
    public boolean canShowArmor() {
        return showArmor;
    }

    @Override
    public boolean canShowGroupArmor() {
        return groupArmor;
    }

    @Override
    public boolean colorByType() {
        return colorByType;
    }

    @Override
    public int getHpTextHeight() {
        return hpTextHeight;
    }

    @Override
    public boolean canShowMaxHP() {
        return showMaxHP;
    }

    @Override
    public boolean showCurrentHP() {
        return showCurrentHP;
    }

    @Override
    public boolean canShowPercentage() {
        return showPercentage;
    }

    @Override
    public boolean canShowOnPlayers() {
        return showOnPlayers;
    }

    @Override
    public boolean canShowOnBosses() {
        return showOnBosses;
    }

    @Override
    public boolean showingOnlyFocused() {
        return showOnlyFocused;
    }

    @Override
    public boolean isDebugInfoEnabled() {
        return enableDebugInfo;
    }

    @Override
    public Set<String> getBlacklist() {
        return blacklist;
    }

    @Override
    public Set<String> getBosses() {
        return bosses;
    }

    public void toggleDraw() {
        draw = !draw;
    }
//            v_maxDistance = builder.define("Max Distance", maxDistance);
//            v_renderInF1 = builder.define("Render with Interface Disabled (F1)", renderInF1);
//            v_heightAbove = builder.define("Height Above Mob", heightAbove);
//            v_drawBackground = builder.define("Draw Background", drawBackground);
//            v_backgroundPadding = builder.define("Background Padding", backgroundPadding);
//            v_backgroundHeight = builder.define("Background Height", backgroundHeight);
//            v_barHeight = builder.define("Health Bar Height", barHeight);
//            v_plateSize = builder.define("Plate Size", plateSize);
//            v_plateSizeBoss = builder.define("Plate Size (Boss)", plateSizeBoss);
//            v_showAttributes = builder.define("Show Attributes", showAttributes);
//            v_showArmor = builder.define("Show Armor", showArmor);
//            v_groupArmor = builder.define("Group Armor (condense 5 iron icons into 1 diamond icon)", groupArmor);
//            v_colorByType = builder.define("Color Health Bar by Type (instead of health percentage)", colorByType);
//            v_hpTextHeight = builder.define("HP Text Height", hpTextHeight);
//            v_showMaxHP = builder.define("Show Max HP", showMaxHP);
//            v_showCurrentHP = builder.define("Show Current HP", showCurrentHP);
//            v_showPercentage = builder.define("Show HP Percentage", showPercentage);
//            v_showOnPlayers = builder.define("Display on Players", showOnPlayers);
//            v_showOnBosses = builder.define("Display on Bosses", showOnBosses);
//            v_showOnlyFocused = builder.define("Only show the health bar for the entity looked at", showOnlyFocused);
//            v_enableDebugInfo = builder.define("Show Debug Info with F3", enableDebugInfo);
//            v_blacklist = builder.comment("Blacklist uses entity IDs, not their display names. Use F3 to see them in the Orderly bar.")
}
