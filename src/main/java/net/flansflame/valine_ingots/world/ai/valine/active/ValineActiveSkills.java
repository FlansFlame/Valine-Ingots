package net.flansflame.valine_ingots.world.ai.valine.active;

import net.flansflame.valine_ingots.world.ai.valine.active.custom.BoomActiveSkill;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;

public class ValineActiveSkills {
    public static final ArrayList<ValineActiveSkill> ACTIVE_SKILLS = new ArrayList<>();

    public static final BoomActiveSkill BOOM = register(new BoomActiveSkill("boom", SoundEvents.GENERIC_EXPLODE, true));

    private static <T extends BoomActiveSkill> T register(T activeSkill) {
        ACTIVE_SKILLS.add(activeSkill);
        return activeSkill;
    }
}
