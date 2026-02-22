package net.flansflame.valine_ingots.tag;

import net.flansflame.valine_ingots.ValineIngots;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Blocks{

        public static final TagKey<Block> PAXEL_MINEABLE = create("mineable/paxel");

        private static TagKey<Block> create(String name) {
            return BlockTags.create(new ResourceLocation(ValineIngots.MOD_ID, name));
        }
    }

    public static class Items{

        public static final TagKey<Item> VALINE_MACHINE_UPGRADES = create("valine_machine_upgrades");
        public static final TagKey<Item> CAN_DAMAGE_VALINE = create("can_damage_valine");

        private static TagKey<Item> create(String name) {
            return ItemTags.create(new ResourceLocation(ValineIngots.MOD_ID, name));
        }
    }
}
