package net.flansflame.valine_ingots.world.item;

import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.world.item.custom.UpgradeArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ValineIngots.MOD_ID);

    public static final RegistryObject<Item> VALINE_INGOT = ITEMS.register("valine_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> VALINE_HELMET = ITEMS.register("valine_helmet",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VALINE_CHESTPLATE = ITEMS.register("valine_chestplate",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VALINE_LEGGINGS = ITEMS.register("valine_leggings",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VALINE_BOOTS = ITEMS.register("valine_boots",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> CREATIVE_ANTI_MATTER_PELLET = ITEMS.register("creative_anti_matter_pellet",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
