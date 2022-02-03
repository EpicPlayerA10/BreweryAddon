package com.epicplayera10.breweryaddon.utils;

import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class EffectUtil {
    private static final HashMap<PotionEffectType, String> namedEffects = new HashMap<>();

    static {
        namedEffects.put(PotionEffectType.ABSORPTION, "Absorption");
        namedEffects.put(PotionEffectType.BAD_OMEN, "Bad Omen");
        namedEffects.put(PotionEffectType.BLINDNESS, "Blindness");
        namedEffects.put(PotionEffectType.CONDUIT_POWER, "Conduit Power");
        namedEffects.put(PotionEffectType.CONFUSION, "Nausea");
        namedEffects.put(PotionEffectType.DAMAGE_RESISTANCE, "Resistance");
        namedEffects.put(PotionEffectType.DOLPHINS_GRACE, "Dolphins Grace");
        namedEffects.put(PotionEffectType.FAST_DIGGING, "Haste");
        namedEffects.put(PotionEffectType.FIRE_RESISTANCE, "Resistance");
        namedEffects.put(PotionEffectType.GLOWING, "Glowing");
        namedEffects.put(PotionEffectType.HARM, "Instant Damage");
        namedEffects.put(PotionEffectType.HEAL, "Instant Health");
        namedEffects.put(PotionEffectType.HEALTH_BOOST, "Health Boost");
        namedEffects.put(PotionEffectType.HERO_OF_THE_VILLAGE, "Hero of the village");
        namedEffects.put(PotionEffectType.HUNGER, "Hunger");
        namedEffects.put(PotionEffectType.INCREASE_DAMAGE, "Strength");
        namedEffects.put(PotionEffectType.INVISIBILITY, "Invisibility");
        namedEffects.put(PotionEffectType.JUMP, "Jump Boost");
        namedEffects.put(PotionEffectType.LEVITATION, "Levitation");
        namedEffects.put(PotionEffectType.LUCK, "Luck");
        namedEffects.put(PotionEffectType.NIGHT_VISION, "Night Vision");
        namedEffects.put(PotionEffectType.POISON, "Poison");
        namedEffects.put(PotionEffectType.REGENERATION, "Regeneration");
        namedEffects.put(PotionEffectType.SATURATION, "Saturation");
        namedEffects.put(PotionEffectType.SLOW, "Slowness");
        namedEffects.put(PotionEffectType.SLOW_DIGGING, "Mining Fatigue");
        namedEffects.put(PotionEffectType.SLOW_FALLING, "Slow Falling");
        namedEffects.put(PotionEffectType.SPEED, "Speed");
        namedEffects.put(PotionEffectType.UNLUCK, "Unluck");
        namedEffects.put(PotionEffectType.WATER_BREATHING, "Water Breathing");
        namedEffects.put(PotionEffectType.WEAKNESS, "Weakness");
        namedEffects.put(PotionEffectType.WITHER, "Wither");
    }

    public static String fixEffect(PotionEffectType effect) {
        return namedEffects.getOrDefault(effect, "ERROR");
    }

}
