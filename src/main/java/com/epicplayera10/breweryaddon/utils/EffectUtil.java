package com.epicplayera10.breweryaddon.utils;

import java.util.HashMap;

public class EffectUtil {
    private static HashMap<String, String> fixedeffects = new HashMap<>();

    public EffectUtil()
    {
        fixedeffects.put("ABSORPTION", "Absorption");
        fixedeffects.put("BAD_OMEN", "Bad Omen");
        fixedeffects.put("BLINDNESS", "Blindness");
        fixedeffects.put("CONDUIT_POWER", "Conduit Power");
        fixedeffects.put("CONFUSION", "Nausea");
        fixedeffects.put("DAMAGE_RESISTANCE", "Resistance");
        fixedeffects.put("DOLPHINS_GRACE", "Dolphins Grace");
        fixedeffects.put("FAST_DIGGING", "Haste");
        fixedeffects.put("FIRE_RESISTANCE", "Resistance");
        fixedeffects.put("GLOWING", "Glowing");
        fixedeffects.put("HARM", "Instant Damage");
        fixedeffects.put("HEAL", "Instant Health");
        fixedeffects.put("HEATH_BOOST", "Health Boost");
        fixedeffects.put("HERO_OF_THE_VILLAGE", "Hero of the village");
        fixedeffects.put("HUNGER", "Hunger");
        fixedeffects.put("INCREASE_DAMAGE", "Strength");
        fixedeffects.put("INVISIBILITY", "Invisibility");
        fixedeffects.put("JUMP", "Jump Boost");
        fixedeffects.put("LEVITATION", "Levitation");
        fixedeffects.put("LUCK", "Luck");
        fixedeffects.put("NIGHT_VISION", "Night Vision");
        fixedeffects.put("POISON", "Poison");
        fixedeffects.put("REGENERATION", "Regeneration");
        fixedeffects.put("SATURATION", "Saturation");
        fixedeffects.put("SLOW", "Slowness");
        fixedeffects.put("SLOW_DIGGING", "Mining Fatigue");
        fixedeffects.put("SLOW_FALLING", "Slow Falling");
        fixedeffects.put("SPEED", "Speed");
        fixedeffects.put("UNLUCK", "Unluck");
        fixedeffects.put("WATER_BREATHING", "Water Breathing");
        fixedeffects.put("WEAKNESS", "Weakness");
        fixedeffects.put("WITHER", "Wither");
    }

    public static String fixeffect(String effectName)
    {
        return fixedeffects.getOrDefault(effectName, "ERROR");
    }

}
