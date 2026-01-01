package net.flansflame.valine_ingots.world.ai.valine.active.custom;

import net.flansflame.valine_ingots.world.ai.valine.active.ValineActiveSkill;
import net.minecraft.sounds.SoundEvent;

public class BoomActiveSkill extends ValineActiveSkill {
    public BoomActiveSkill(String animationId, SoundEvent attackSound, boolean activateEvenIfNotNear) {
        super(animationId, attackSound, activateEvenIfNotNear);
    }
}
