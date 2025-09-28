package net.flansflame.valine_ingots.world.tool_set.custom;

import net.flansflame.flans_knowledge_lib.tool_set.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class UpgradeToolSet extends ToolSet {
    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability);
    }

    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, Item.Properties build) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability, build);
    }

    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, CustomToolSets builder) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability, builder);
    }

    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, Tier tier) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability, tier);
    }

    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, Tier tier, Item.Properties build) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability, tier, build);
    }

    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, Tier tier, CustomToolSets builder) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability, tier, builder);
    }

    public UpgradeToolSet(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, Tier tier, Item.Properties build, CustomToolSets builder) {
        super(modId, id, ingredient, attackDamage, attackSpeed, toolLevel, digSpeed, durability, tier, build, builder);
    }

    public void setUp(String modId, String id, RegistryObject<Item> ingredient, int attackDamage, float attackSpeed, int toolLevel, float digSpeed, int durability, Tier tier, Item.Properties build, CustomToolSets builder) {
        this.ID = id;
        this.NEEDS_THIS_TOOL = BlockTags.create(new ResourceLocation(modId, "needs_" + this.ID + "_tool"));
        this.TIER = TierSortingRegistry.registerTier(new ForgeTier(toolLevel, durability, digSpeed, 0.0F, 30, this.NEEDS_THIS_TOOL, () -> {
            return Ingredient.of(new ItemLike[]{(ItemLike) ingredient.get()});
        }), new ResourceLocation(modId, this.ID), List.of(tier), List.of());

        this.SWORD = this.create(new ToolSetItem(this.ID + "_sword", () -> {
            return new UpgradeSwordItem(this.TIER, attackDamage, attackSpeed, build, builder);
        }));
        this.PICKAXE = this.create(new ToolSetItem(this.ID + "_pickaxe", () -> {
            return new UpgradePickaxeItem(this.TIER, attackDamage, attackSpeed, build, builder);
        }));
        this.AXE = this.create(new ToolSetItem(this.ID + "_axe", () -> {
            return new UpgradeAxeItem(this.TIER, attackDamage, attackSpeed, build, builder);
        }));
        this.SHOVEL = this.create(new ToolSetItem(this.ID + "_shovel", () -> {
            return new UpgradeShovelItem(this.TIER, attackDamage, attackSpeed, build, builder);
        }));
        this.HOE = this.create(new ToolSetItem(this.ID + "_hoe", () -> {
            return new UpgradeHoeItem(this.TIER, attackDamage, attackSpeed, build, builder);
        }));
    }
}
