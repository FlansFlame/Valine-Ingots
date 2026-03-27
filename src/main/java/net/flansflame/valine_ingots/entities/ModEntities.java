package net.flansflame.valine_ingots.entities;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.entities.entity.LaserTurretEntity;
import net.flansflame.valine_ingots.entities.entity.MiniValineEntity;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.flansflame.valine_ingots.entities.entity.FallingValineSpearEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ValineIngots.MOD_ID);

    public static final RegistryObject<EntityType<ValineEntity>> VALINE_ENTITY = ENTITIES.register("valine",
            () -> EntityType.Builder.of(ValineEntity::new, MobCategory.MISC)
                    .sized(0.5f, 2.4f)
                    .build(new ResourceLocation(ValineIngots.MOD_ID, "valine").toString()));

    public static final RegistryObject<EntityType<FallingValineSpearEntity>> FALLING_VALINE_SPEAR_ENTITY = ENTITIES.register("falling_valine_spear",
            () -> EntityType.Builder.of(FallingValineSpearEntity::new, MobCategory.MISC)
                    .sized(0.5f, 1.9f)
                    .build(new ResourceLocation(ValineIngots.MOD_ID, "falling_valine_spear").toString()));

    public static final RegistryObject<EntityType<LaserTurretEntity>> LASER_TURRET = ENTITIES.register("laser_turret",
            () -> EntityType.Builder.of(LaserTurretEntity::new, MobCategory.MISC)
                    .sized(0.5f, 1.9f)
                    .build(new ResourceLocation(ValineIngots.MOD_ID, "laser_turret").toString()));

    public static final RegistryObject<EntityType<MiniValineEntity>> MINI_VALINE = ENTITIES.register("mini_valine",
            () -> EntityType.Builder.of(MiniValineEntity::new, MobCategory.MISC)
                    .sized(0.5f, 1.6f)
                    .build(new ResourceLocation(ValineIngots.MOD_ID, "mini_valine").toString()));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(VALINE_ENTITY.get(), ValineEntity.createAttributes().build());
        event.put(MINI_VALINE.get(), MiniValineEntity.createAttributes().build());
    }
}