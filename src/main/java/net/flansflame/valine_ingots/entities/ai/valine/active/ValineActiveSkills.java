package net.flansflame.valine_ingots.entities.ai.valine.active;

import net.flansflame.valine_ingots.entities.ai.valine.active.custom.BoomActiveSkill;
import net.flansflame.valine_ingots.entities.ai.valine.active.custom.FallingSpearActiveSkill;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;

public class ValineActiveSkills {
    public static final ArrayList<ValineActiveSkill> ACTIVE_SKILLS = new ArrayList<>();

    public static final BoomActiveSkill BOOM = register(new BoomActiveSkill("boom", SoundEvents.GENERIC_EXPLODE, true));
    public static final FallingSpearActiveSkill FALLING_SPEAR = register(new FallingSpearActiveSkill("drop", true));

    private static <T extends ValineActiveSkill> T register(T activeSkill) {
        ACTIVE_SKILLS.add(activeSkill);
        return activeSkill;
    }
}
