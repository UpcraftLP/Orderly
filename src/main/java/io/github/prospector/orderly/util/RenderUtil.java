package io.github.prospector.orderly.util;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public final class RenderUtil {

    private static final ItemStack ICON_ARTHROPODS = new ItemStack(Items.SPIDER_EYE);
    private static final ItemStack ICON_UNDEAD = new ItemStack(Items.ROTTEN_FLESH);
    private static final ItemStack ICON_DEFAULT = new ItemStack(Items.SKELETON_SKULL);
    private static final ItemStack ICON_BOSSES = new ItemStack(Items.WITHER_SKELETON_SKULL);

    private RenderUtil() {
        throw new IllegalStateException("util class");
    }

    public static ItemStack getIcon(LivingEntity entity, boolean boss) {
        if (boss) {
            return ICON_BOSSES;
        }
        EntityGroup attr = entity.getGroup();
        if (attr == EntityGroup.ARTHROPOD) {
            return ICON_ARTHROPODS;
        } else if (attr == EntityGroup.UNDEAD) {
            return ICON_UNDEAD;
        } else {
            return ICON_DEFAULT;
        }
    }

    public static int getColor(LivingEntity entity, boolean colorByType, boolean boss) {
        if (colorByType) {
            int r = 0;
            int g = 255;
            int b = 0;
            if (boss) {
                r = 128;
                g = 0;
                b = 128;
            }
            if (entity instanceof Monster) { //MobEntity is a red herring
                r = 255;
                g = 0;
                b = 0;
            }
            return 0xff000000 | r << 16 | g << 8 | b;
        } else {
            float health = MathHelper.clamp(entity.getHealth(), 0.0F, entity.getMaxHealth());
            float hue = Math.max(0.0F, (health / entity.getMaxHealth()) / 3.0F - 0.07F);
            return Color.HSBtoRGB(hue, 1.0F, 1.0F);
        }
    }

    public static int getAlpha(int argb) {
        return (argb >> 24) & 0xFF;
    }

    public static int getRed(int argb) {
        return (argb >> 16) & 0xFF;
    }

    public static int getGreen(int argb) {
        return (argb >> 8) & 0xFF;
    }

    public static int getBlue(int argb) {
        return argb & 0xFF;
    }
}
