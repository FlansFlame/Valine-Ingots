package net.flansflame.valine_ingots.client.screen;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.client.screen.custom.ValineExtractorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ValineIngots.MOD_ID);

    public static final RegistryObject<MenuType<ValineExtractorMenu>> VALINE_EXTRACTOR =
            register(ValineExtractorMenu::new, "valine_extractor_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(IContainerFactory<T> factory, String id) {
        return MENU_TYPES.register(id, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
