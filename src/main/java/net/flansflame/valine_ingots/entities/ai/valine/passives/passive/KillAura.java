package net.flansflame.valine_ingots.entities.ai.valine.passives.passive;

import net.flansflame.flans_knowledge_lib.Utils;
import net.flansflame.valine_ingots.damagesource.ModDamageTypes;
import net.flansflame.valine_ingots.entities.ai.valine.passives.ValinePassiveSkill;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.flansflame.valine_ingots.tag.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class KillAura extends ValinePassiveSkill {

    public static int KILL_AURA_USE_TIME = 10;

    @Override
    public void onPassiveSkill(ValineEntity valine, Entity entity) {
        if (entity instanceof Player player) {
            if (valine.tickCount % KILL_AURA_USE_TIME == 0 && player.level() instanceof ServerLevel server) {

                boolean unProtected = false;
                for (ItemStack armor : player.getArmorSlots()) {
                    if (!armor.is(ModTags.Items.HAS_ANTI_VALINE_PROTECTION)) unProtected = true;
                }

                if (unProtected) {
                    player.hurt(Utils.createDamageSource(server, ModDamageTypes.VALINE_ATTACK), player.getMaxHealth() >= 20f ? player.getMaxHealth() / 10f : 2f);
                }
            }
        }
    }
}
