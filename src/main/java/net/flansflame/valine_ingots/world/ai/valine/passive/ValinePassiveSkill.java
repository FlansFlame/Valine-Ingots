package net.flansflame.valine_ingots.world.ai.valine.passive;

import net.flansflame.valine_ingots.world.entity.custom.ValineEntity;
import net.minecraft.world.entity.Entity;

public abstract class ValinePassiveSkill {
    public void onPassiveSkill(ValineEntity valine, Entity entity){}
    public void asPassiveSkill(ValineEntity valine){}
}
