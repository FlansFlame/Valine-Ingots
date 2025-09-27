package net.flansflame.varin_ingots.world.item;

import net.flansflame.varin_ingots.VarinIngots;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VarinIngots.MOD_ID);

    public static final RegistryObject<Item> VARIN_INGOT = ITEMS.register("varin_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> VARIN_HELMET = ITEMS.register("varin_helmet",
            () -> new ArmorItem(ModArmorMaterials.VARIN, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VARIN_CHESTPLATE = ITEMS.register("varin_chestplate",
            () -> new ArmorItem(ModArmorMaterials.VARIN, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VARIN_LEGGINGS = ITEMS.register("varin_leggings",
            () -> new ArmorItem(ModArmorMaterials.VARIN, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VARIN_BOOTS = ITEMS.register("varin_boots",
            () -> new ArmorItem(ModArmorMaterials.VARIN, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
