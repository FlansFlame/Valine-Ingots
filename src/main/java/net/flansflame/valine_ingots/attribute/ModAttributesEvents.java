package net.flansflame.valine_ingots.attribute;

import net.flansflame.valine_ingots.component.ModComponents;
import net.flansflame.valine_ingots.world.item.ModItems;
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

@Mod.EventBusSubscriber
public class ModAttributesEvents {

    @SubscribeEvent
    public static void onDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        float damage = event.getAmount();

        /*MULTI_BARRIER*/
        /*hard coded ONLY for valine_ingots:valine_chestplate*/
        {
            ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
            if (!itemStack.isEmpty() && itemStack.is(ModItems.VALINE_CHESTPLATE.get())) {
                double barrier = 1 - ModComponents.REFINE.get(itemStack) * 0.015;

                if (ModComponents.MULTI_BARRIER_STACK.get(itemStack) <= 0) {
                    event.setAmount((float) (damage * barrier));

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
        {
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

                        event.setAmount(damage * 2);
                    }
                }
            }
        }
    }
}