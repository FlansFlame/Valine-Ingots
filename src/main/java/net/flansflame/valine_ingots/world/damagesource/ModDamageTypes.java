package net.flansflame.valine_ingots.world.damagesource;

import net.flansflame.valine_ingots.ValineIngots;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {
    ResourceKey<DamageType> VALINE_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ValineIngots.MOD_ID, "valine_attack"));
    ResourceKey<DamageType> VALINE_SPEAR_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ValineIngots.MOD_ID, "valine_spear_attack"));
}