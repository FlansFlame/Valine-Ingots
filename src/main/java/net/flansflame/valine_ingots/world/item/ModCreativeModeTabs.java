package net.flansflame.valine_ingots.world.item;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.world.tool_set.ModToolSets;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ValineIngots.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VALINE_INGOT.get()))
                    .title(Component.translatable("tab." + ValineIngots.MOD_ID + ".valine"))
                    .displayItems((pParameters, pOutput) -> {

                        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

                        registries.addAll(ModItems.ITEMS.getEntries());
                        registries.addAll(ModToolSets.TOOL_SETS.REGISTRY.getEntries());

                        registries.remove(ModItems.CREATIVE_ANTI_MATTER_PELT);

                        for (RegistryObject<Item> registry : registries){
                            pOutput.accept(registry.get());
                        }

                        pOutput.accept(ModItems.CREATIVE_ANTI_MATTER_PELT.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }
}
