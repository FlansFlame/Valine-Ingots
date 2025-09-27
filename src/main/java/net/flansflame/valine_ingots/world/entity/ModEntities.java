package net.flansflame.valine_ingots.world.entity;

import net.flansflame.valine_ingots.ValineIngots;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ValineIngots.MOD_ID);

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}