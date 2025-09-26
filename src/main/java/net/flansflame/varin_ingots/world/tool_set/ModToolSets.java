package net.flansflame.varin_ingots.world.tool_set;

import net.flansflame.flans_knowledge_lib.tool_set.ToolSet;
import net.flansflame.flans_knowledge_lib.tool_set.ToolSetRegisterer;
import net.flansflame.varin_ingots.VarinIngots;
import net.flansflame.varin_ingots.world.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModToolSets {
    public static final ToolSetRegisterer TOOL_SETS = new ToolSetRegisterer(VarinIngots.MOD_ID);

    public static final ToolSet VARIN_TOOL = TOOL_SETS.register(new ToolSet(VarinIngots.MOD_ID, "varin", ModItems.VARIN_INGOT, 18, 1.8f, 5, 10f, 4096, Tiers.NETHERITE, new Item.Properties().rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus){
        TOOL_SETS.register(eventBus);
    }
}
