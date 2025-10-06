package net.flansflame.valine_ingots.client.screen.custom;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.client.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ValineIngots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModScreensRegisterer {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.VALINE_EXTRACTOR.get(), ValineExtractorScreen::new);
        });
    }
}
