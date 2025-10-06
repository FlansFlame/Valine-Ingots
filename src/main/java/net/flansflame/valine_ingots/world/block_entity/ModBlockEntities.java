package net.flansflame.valine_ingots.world.block_entity;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.world.block.ModBlocks;
import net.flansflame.valine_ingots.world.block_entity.custom.ValineExtractorBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ValineIngots.MOD_ID);

    public static final RegistryObject<BlockEntityType<ValineExtractorBlockEntity>> VALINE_EXTRACTOR =
            BLOCK_ENTITIES.register("valine_extractor_block_entity", () ->
                    BlockEntityType.Builder.of(ValineExtractorBlockEntity::new,
                            ModBlocks.VALINE_EXTRACTOR.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}