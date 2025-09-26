package net.flansflame.varin_ingots.world.entity;

import net.flansflame.varin_ingots.VarinIngots;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VarinIngots.MOD_ID);

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }
}