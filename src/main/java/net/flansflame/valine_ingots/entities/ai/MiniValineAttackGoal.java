package net.flansflame.valine_ingots.entities.ai;

import net.flansflame.flans_knowledge_lib.Utils;
import net.flansflame.valine_ingots.damagesource.ModDamageTypes;
import net.flansflame.valine_ingots.entities.entity.MiniValineEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MiniValineAttackGoal extends MeleeAttackGoal {
    public MiniValineAttackGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier, true);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity entity, double targetDistance) {
        double d0 = this.getAttackReachSqr(entity);
        if (targetDistance <= d0 && this.getTicksUntilNextAttack() <= 0) {
            this.resetAttackCooldown();

            if (entity.level() instanceof ServerLevel server)
                entity.hurt(Utils.createDamageSource(server, ModDamageTypes.VALINE_ATTACK, this.mob), MiniValineEntity.ATTACK_DAMAGE);
        }
    }
}
