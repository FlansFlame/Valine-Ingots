package net.flansflame.valine_ingots.screens;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.screens.menu.ValineExtractorMenu;
import net.flansflame.valine_ingots.screens.screen.ValineExtractorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = ValineIngots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ValineIngots.MOD_ID);

    public static final RegistryObject<MenuType<ValineExtractorMenu>> VALINE_EXTRACTOR =
            register(ValineExtractorMenu::new, "valine_extractor_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String id) {
        return MENU_TYPES.register(id, () -> IForgeMenuType.create(factory));
    }

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.VALINE_EXTRACTOR.get(), ValineExtractorScreen::new);
        });
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
