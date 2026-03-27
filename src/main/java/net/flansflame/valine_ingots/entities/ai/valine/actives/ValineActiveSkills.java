package net.flansflame.valine_ingots.entities.ai.valine.actives;

import net.flansflame.valine_ingots.entities.ai.valine.actives.active.*;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;

public class ValineActiveSkills {
    public static final ArrayList<ValineActiveSkill> ACTIVE_SKILLS = new ArrayList<>();

    public static final BoomActiveSkill BOOM = register(new BoomActiveSkill("boom", SoundEvents.GENERIC_EXPLODE, true));
    public static final FallingSpearActiveSkill FALLING_SPEAR = register(new FallingSpearActiveSkill("drop", true));
    public static final Laser LASER = register(new Laser("cast", SoundEvents.TRIDENT_RETURN, true));
    public static final SpawnMiniValine SPAWN_MINI_VALINE = register(new SpawnMiniValine("cast", SoundEvents.ENCHANTMENT_TABLE_USE, true));
    public static final Teleport TELEPORT = register(new Teleport("teleport", true));

    private static <T extends ValineActiveSkill> T register(T activeSkill) {
        ACTIVE_SKILLS.add(activeSkill);
        return activeSkill;
    }
}
