package net.flansflame.valine_ingots.entities.ai.valine.passives;

import net.flansflame.valine_ingots.entities.ai.valine.passives.passive.ExplodeProjectile;
import net.flansflame.valine_ingots.entities.ai.valine.passives.passive.KillAura;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ValinePassiveSkills {
    public static final ArrayList<ValinePassiveSkill> PASSIVE_SKILLS = new ArrayList<>();

    public static final ValinePassiveSkill KILL_AURA = register(KillAura::new);
    public static final ValinePassiveSkill EXPLODE_PROJECTILES = register(ExplodeProjectile::new);


    private static <T extends ValinePassiveSkill> T register(Supplier<T> sup) {
        T passiveSkill = sup.get();
        PASSIVE_SKILLS.add(passiveSkill);
        return passiveSkill;
    }
}
