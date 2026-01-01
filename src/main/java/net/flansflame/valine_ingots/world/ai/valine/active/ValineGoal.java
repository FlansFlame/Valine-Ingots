package net.flansflame.valine_ingots.world.ai.valine.active;

import net.flansflame.valine_ingots.world.entity.custom.ValineEntity;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ValineGoal extends MeleeAttackGoal {

    public static final float ATTACK_STAGE_RANGE_MULTIPLIER = 3f;
    public static final int ATTACK_START_TICK = 60;
    public static final int DELAY_TICK = 100;

    public ValineGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier, true);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity entity, double targetDistance) {
        double attackRange = this.getAttackReachSqr(entity) * ATTACK_STAGE_RANGE_MULTIPLIER;

        if (this.mob instanceof ValineEntity valine && getTicksUntilNextAttack() <= 0) {
            valine.setAttackPhase(Mth.nextInt(RandomSource.create(), 0, ValineActiveSkills.ACTIVE_SKILLS.size() - 1));
            ValineActiveSkill activeSkill = ValineActiveSkills.ACTIVE_SKILLS.get(valine.getAttackPhase());

            if (activeSkill.activateEvenIfNotNear()) {
                this.resetAttackCooldown();

                activeSkill.beforeAttack(valine, entity);

                valine.setAttackCount(ATTACK_START_TICK);
            } else {
                if (targetDistance <= attackRange) {
                    this.resetAttackCooldown();

                    activeSkill.beforeAttack(valine, entity);

                    valine.setAttackCount(ATTACK_START_TICK);
                }
            }
        }
    }

    @Override
    protected int adjustedTickDelay(int delay) {
        return super.adjustedTickDelay(DELAY_TICK);
    }
}
