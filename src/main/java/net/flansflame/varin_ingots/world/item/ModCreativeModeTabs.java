package net.flansflame.varin_ingots.world.item;

import net.flansflame.varin_ingots.VarinIngots;
import net.flansflame.varin_ingots.world.tool_set.ModToolSets;
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
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VarinIngots.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VARIN_INGOT.get()))
                    .title(Component.translatable("tab." + VarinIngots.MOD_ID + ".varin"))
                    .displayItems((pParameters, pOutput) -> {

                        ArrayList<RegistryObject<Item>> registries = new ArrayList<>();

                        registries.addAll(ModItems.ITEMS.getEntries());
                        registries.addAll(ModToolSets.TOOL_SETS.REGISTRY.getEntries());

                        for (RegistryObject<Item> registry : registries){
                            pOutput.accept(registry.get());
                        }

                    })
                    .build());

    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }
}
