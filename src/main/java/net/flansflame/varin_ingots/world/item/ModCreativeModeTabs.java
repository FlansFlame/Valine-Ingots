package net.flansflame.varin_ingots.world.item;

import net.flansflame.varin_ingots.VarinIngots;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VarinIngots.MOD_ID);

    public static void register(IEventBus eventBus){
        TABS.register(eventBus);
    }
}
