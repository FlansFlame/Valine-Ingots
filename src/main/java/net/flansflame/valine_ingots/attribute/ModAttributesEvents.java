package net.flansflame.valine_ingots.attribute;

import net.flansflame.valine_ingots.component.ModComponents;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.flansflame.valine_ingots.items.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ModAttributesEvents {

    @SubscribeEvent
    public static void onDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        float modDamage = event.getAmount();

        /*MULTI_BARRIER*/
        /*hard coded ONLY for valine_ingots:valine_chestplate*/
        if (modDamage > 0f) {
            ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
            if (!itemStack.isEmpty() && itemStack.is(ModItems.VALINE_CHESTPLATE.get())) {
                double barrier = 1 - ModComponents.REFINE.get(itemStack) * 0.015;

                if (ModComponents.MULTI_BARRIER_STACK.get(itemStack) <= 0) {
                    modDamage = (float) (modDamage * barrier);

                    if (entity.level() instanceof ServerLevel server) {
                        server.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.GLASS_BREAK, SoundSource.MASTER, 1f, 1f);
                    }
                    ModComponents.MULTI_BARRIER_STACK.set(itemStack, 4);
                } else {
                    ModComponents.MULTI_BARRIER_STACK.remove(itemStack);
                    if (ModComponents.MULTI_BARRIER_STACK.get(itemStack) <= 0 && entity.level() instanceof ServerLevel server) {
                        server.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.CROSSBOW_LOADING_END, SoundSource.MASTER, 1f, 1f);
                    }
                }
            }
        }

        /*CRITICAL*/
        /*hard coded ONLY for valine_ingots:valine_helmet*/
        if (modDamage > 0f) {
            DamageSource source = event.getSource();
            Entity eSourceEntity = source.getDirectEntity();

            if (eSourceEntity instanceof LivingEntity sourceEntity) {
                ItemStack itemStack = sourceEntity.getItemBySlot(EquipmentSlot.HEAD);
                if (!itemStack.isEmpty() && itemStack.is(ModItems.VALINE_HELMET.get())) {
                    double critical = ModComponents.REFINE.get(itemStack);

                    if (Mth.nextInt(RandomSource.create(), 0, 100) <= critical) {
                        if (sourceEntity.level() instanceof ServerLevel server) {
                            server.playSound(null, sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(), SoundEvents.TRIDENT_RETURN, SoundSource.MASTER, 2f, 1f);
                        }

                        modDamage = modDamage * 2;
                    }
                }
            }
        }

        /*ANTI_VALINE_PROTECTION*/
        /*hard coded ONLY for valine_ingots:valine_helmet, chestplate, leggings, boots*/
        if (modDamage > 0) {
            DamageSource source = event.getSource();
            Entity eSourceEntity = source.getDirectEntity();

            if (eSourceEntity instanceof ValineEntity) {
                final float SINGLE_MULTIPLIER = 0.004f;
                float multiplier = 0;

                ArrayList<EquipmentSlot> armorSlots = new ArrayList<>(List.of(EquipmentSlot.values()));
                armorSlots.remove(EquipmentSlot.MAINHAND);
                armorSlots.remove(EquipmentSlot.OFFHAND);

                for (EquipmentSlot armorSlot : armorSlots) {
                    ItemStack itemStack = entity.getItemBySlot(armorSlot);
                    if (isValineArmor(itemStack)){
                        int refine = ModComponents.REFINE.get(itemStack);
                        if (refine > 0){
                            multiplier += SINGLE_MULTIPLIER * refine;
                        }
                    }
                }

                modDamage -= modDamage * multiplier;
            }
        }

        event.setAmount(modDamage);
    }

    public static boolean isValineArmor(ItemStack itemStack) {
        if (itemStack == null) return false;

        return itemStack.is(ModItems.VALINE_HELMET.get()) ||
                itemStack.is(ModItems.VALINE_CHESTPLATE.get()) ||
                itemStack.is(ModItems.VALINE_LEGGINGS.get()) ||
                itemStack.is(ModItems.VALINE_BOOTS.get());
    }
}