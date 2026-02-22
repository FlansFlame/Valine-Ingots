package net.flansflame.valine_ingots.items.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.attribute.ModAttributes;
import net.flansflame.valine_ingots.component.ModComponents;
import net.flansflame.valine_ingots.items.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UpgradeArmorItem extends ArmorItem {

    public UpgradeArmorItem(ArmorMaterial material, Type type, Properties build) {
        super(material, type, build);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        ItemStack subItemStack;
        if (hand == InteractionHand.MAIN_HAND) {
            subItemStack = player.getOffhandItem();
        } else {
            subItemStack = player.getMainHandItem();
        }

        if (ModComponents.REFINE.get(itemStack) < 50 && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(subItemStack.getItem())).toString().equals("mekanism:pellet_antimatter")) {
            ModComponents.REFINE.add(itemStack);
            return InteractionResultHolder.success(itemStack);
        } else if (ModComponents.REFINE.get(itemStack) < 50 && subItemStack.is(ModItems.CREATIVE_ANTI_MATTER_PELLET.get())) {
            if (player.isShiftKeyDown()) {
                ModComponents.REFINE.set(itemStack, 50);
            } else {
                ModComponents.REFINE.add(itemStack);
            }
            return InteractionResultHolder.success(itemStack);
        }
        return super.use(level, player, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> component, TooltipFlag tooltipFlag) {
        int refine = ModComponents.REFINE.get(itemStack);

        component.add(Component.literal(""));
        if (refine > 49) {
            component.add(Component.translatable("item." + ValineIngots.MOD_ID + ".valine_armors.desc.refine.end"));
            component.add(Component.literal(" §5" + refine + " / 50"));
        } else if (refine > 0) {
            component.add(Component.translatable("item." + ValineIngots.MOD_ID + ".valine_armors.desc.refine"));
            component.add(Component.literal(" §e" + refine + " / 50"));
        } else {
            component.add(Component.translatable("item." + ValineIngots.MOD_ID + ".valine_armors.desc.refine.none"));
            component.add(Component.literal(" §7" + refine + " / 50"));
        }
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        if (ModComponents.REFINE.get(itemStack) > 0) {
            return true;
        } else {
            return super.isFoil(itemStack);
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot pSlot, ItemStack itemStack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> modifiers = ImmutableMultimap.builder();
        modifiers.putAll(getDefaultAttributeModifiers(pSlot));

        if (pSlot == this.type.getSlot()) {
            int refine = ModComponents.REFINE.get(itemStack);

            UUID uuid = null;
            Attribute attribute = null;
            double amount = 0;
            switch (this.type) {
                case HELMET -> {
                    uuid = UUID.fromString("fa43d0e6-0433-4c59-9ffc-ec3f59e6c101");
                    attribute = ModAttributes.CRITICAL.get();
                    amount = refine * 0.01;
                }
                case CHESTPLATE -> {
                    uuid = UUID.fromString("a10e7855-10ae-4a6a-9774-a95240acc045");
                    attribute = ModAttributes.MULTI_BARRIER.get();
                    amount = refine * 0.015;
                }
                case LEGGINGS -> {
                    uuid = UUID.fromString("f221e4e1-075a-446c-8cd3-9dfbc3a9a1bd");
                    attribute = Attributes.MOVEMENT_SPEED;
                    amount = refine * 0.002;
                }
                case BOOTS -> {
                    uuid = UUID.fromString("359c8f0b-0d9f-4214-bc34-43834a5cd00b");
                    attribute = Attributes.MAX_HEALTH;
                    amount = refine * 2;
                }
            }
            modifiers.put(attribute,
                    new AttributeModifier(uuid, "", amount, AttributeModifier.Operation.ADDITION));

            modifiers.put(ModAttributes.ANTI_VALINE_PROTECTION.get(),
                    new AttributeModifier(UUID.fromString("956684b2-9866-4646-a886-6d85f4f07f52"), "", refine * 0.004f, AttributeModifier.Operation.ADDITION));
        }
        return modifiers.build();
    }
}