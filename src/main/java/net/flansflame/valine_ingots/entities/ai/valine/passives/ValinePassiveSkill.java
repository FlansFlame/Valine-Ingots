package net.flansflame.valine_ingots.entities.ai.valine.passives;

import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.minecraft.world.entity.Entity;

public abstract class ValinePassiveSkill {
    public void onPassiveSkill(ValineEntity valine, Entity entity){}
    public void asPassiveSkill(ValineEntity valine){}
}
