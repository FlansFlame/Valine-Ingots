package net.flansflame.valine_ingots.world.item;

import net.flansflame.flans_knowledge_lib.tool_set.CustomToolSets;
import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.world.item.custom.PaxelItem;
import net.flansflame.valine_ingots.world.item.custom.UpgradeArmorItem;
import net.flansflame.valine_ingots.world.tool_set.ModToolSets;
import net.flansflame.valine_ingots.world.tool_set.custom.UpgradeSwordItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ValineIngots.MOD_ID);

    public static final RegistryObject<Item> VALINE_NUGGET = ITEMS.register("valine_nugget",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> VALINE_INGOT = ITEMS.register("valine_ingot",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> INCOMPLETE_VALINE_UPGRADE_CHIP = ITEMS.register("incomplete_valine_upgrade_chip",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> VALINE_UPGRADE_CHIP = ITEMS.register("valine_upgrade_chip",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> VALINE_SPEAR = ITEMS.register("valine_spear",
            () -> new UpgradeSwordItem(ModToolSets.VALINE_TOOL.getTier(), 12, 2.4f, new Item.Properties().rarity(Rarity.RARE).fireResistant(), new CustomToolSets.Builder().build()));
    public static final RegistryObject<Item> VALINE_PAXEL = ITEMS.register("valine_paxel",
            () -> new PaxelItem(ModToolSets.VALINE_TOOL.getTier(), 18, 2f,new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> VALINE_MACHINE_UPGRADE_TIER_1 = ITEMS.register("valine_machine_upgrade_tier_1",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VALINE_MACHINE_UPGRADE_TIER_2 = ITEMS.register("valine_machine_upgrade_tier_2",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VALINE_MACHINE_UPGRADE_TIER_3 = ITEMS.register("valine_machine_upgrade_tier_3",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VALINE_MACHINE_UPGRADE_TIER_4 = ITEMS.register("valine_machine_upgrade_tier_4",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> VALINE_HELMET = ITEMS.register("valine_helmet",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> VALINE_CHESTPLATE = ITEMS.register("valine_chestplate",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> VALINE_LEGGINGS = ITEMS.register("valine_leggings",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(Rarity.RARE).fireResistant()));
    public static final RegistryObject<Item> VALINE_BOOTS = ITEMS.register("valine_boots",
            () -> new UpgradeArmorItem(ModArmorMaterials.VALINE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static final RegistryObject<Item> CREATIVE_ANTI_MATTER_PELLET = ITEMS.register("creative_anti_matter_pellet",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
