package net.flansflame.varin_ingots.data;

import net.flansflame.varin_ingots.VarinIngots;
import net.flansflame.varin_ingots.world.item.ModItems;
import net.flansflame.varin_ingots.world.tool_set.ModToolSets;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VarinIngots.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

        registries.addAll(ModItems.ITEMS.getEntries());
        registries.addAll(ModToolSets.TOOL_SETS.REGISTRY.getEntries());

        for (RegistryObject<Item> registry : registries){
            if (registry.get() instanceof SwordItem || registry.get() instanceof PickaxeItem || registry.get() instanceof AxeItem || registry.get() instanceof ShovelItem || registry.get() instanceof HoeItem){
                handheldItem(registry);
            } else {
                simpleItem(registry);
            }
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(VarinIngots.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(VarinIngots.MOD_ID,"item/" + item.getId().getPath()));
    }
}
