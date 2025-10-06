package net.flansflame.valine_ingots.data;

import net.flansflame.valine_ingots.world.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Set;

public class ModLootTableSubProvider extends BlockLootSubProvider {
    public ModLootTableSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        ArrayList<RegistryObject<Block>> registries = new ArrayList<>();
        registries.addAll(ModBlocks.BLOCKS.getEntries());

        for (RegistryObject<Block> registry : registries){
            this.dropSelf(registry.get());
        }
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}