package net.flansflame.valine_ingots.toolsets;

import net.flansflame.flans_knowledge_lib.tool_set.ToolSetRegisterer;
import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.items.ModItems;
import net.flansflame.valine_ingots.toolsets.toolset.UpgradeToolSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModToolSets {
    public static final ToolSetRegisterer TOOL_SETS = new ToolSetRegisterer(ValineIngots.MOD_ID);

    public static final UpgradeToolSet VALINE_TOOL = (UpgradeToolSet) TOOL_SETS.register(new UpgradeToolSet(ValineIngots.MOD_ID, "valine", ModItems.VALINE_INGOT, 18, 1.8f, 5, 10f, 4096, Tiers.NETHERITE, new Item.Properties().rarity(Rarity.RARE).fireResistant()));

    public static void register(IEventBus eventBus) {
        TOOL_SETS.register(eventBus);
    }
}
