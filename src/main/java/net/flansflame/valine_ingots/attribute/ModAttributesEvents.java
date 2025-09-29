package net.flansflame.valine_ingots.attribute;

import net.flansflame.valine_ingots.component.ModComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModAttributesEvents {

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        float damage = event.getAmount();
        AttributeInstance instance = entity.getAttribute(ModAttributes.MULTI_BARRIER.get());
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (instance == null || itemStack.isEmpty()) return;
        double barrier = instance.getValue();

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