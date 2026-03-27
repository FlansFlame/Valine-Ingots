package net.flansflame.valine_ingots.entities.ai.valine.actives.active;

import net.flansflame.valine_ingots.entities.ModEntities;
import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineActiveSkill;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;

public class Laser extends ValineActiveSkill {
    public Laser(String animationId, SoundEvent attackSound, boolean activateEvenIfNotNear) {
        super(animationId, attackSound, activateEvenIfNotNear);
    }

    @Override
    public void onAttack(ValineEntity valine, LivingEntity target, float amount) {
        if (valine.level() instanceof ServerLevel server) {
            var spawnEntity = ModEntities.LASER_TURRET.get().spawn(server, valine.getOnPos().above(), MobSpawnType.COMMAND);
            if (spawnEntity != null) {
                //spawnEntity.setPos(new Vec3(spawnEntity.getX() + 0.5, spawnEntity.getY() + 0.5, spawnEntity.getZ() + 0.5));
            }
        }
    }
}
