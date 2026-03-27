package net.flansflame.valine_ingots.entities.ai.valine.actives;

import net.flansflame.flans_knowledge_lib.Utils;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.flansflame.valine_ingots.damagesource.ModDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class ValineActiveSkill {
    private final String animationId;
    private final SoundEvent attackSound;
    private final boolean activateEvenIfNotNear;

    public ValineActiveSkill(String animationId, boolean activateEvenIfNotNear) {
        this(animationId, null, activateEvenIfNotNear);
    }

    public ValineActiveSkill(String animationId, SoundEvent attackSound, boolean activateEvenIfNotNear) {
        this.animationId = animationId;
        this.attackSound = attackSound;
        this.activateEvenIfNotNear = activateEvenIfNotNear;
    }

    public final boolean isEmpty() {
        return animationId.isEmpty();
    }

    public final String getAnimationId() {
        return animationId;
    }

    public final SoundEvent getAttackSound() {
        return attackSound;
    }

    public final boolean activateEvenIfNotNear() {
        return activateEvenIfNotNear;
    }

    protected final double getAttackReach(LivingEntity entity, LivingEntity target) {
        return entity.getBbWidth() * 2 * entity.getBbWidth() * 2 + target.getBbWidth();
    }

    /*Overrides*/
    public void onAttack(ValineEntity valine, LivingEntity target, float amount) {

        if (target == null || valine.getPerceivedTargetDistanceSquareForMeleeAttack(target) > getAttackReach(valine, target) * ValineGoal.ATTACK_STAGE_RANGE_MULTIPLIER)
            return;

        valine.swing(InteractionHand.MAIN_HAND);
        if (valine.level() instanceof ServerLevel server) {
            target.hurt(Utils.createDamageSource(server, ModDamageTypes.VALINE_ATTACK, valine), amount);

            if (this.getAttackSound() != null) {
                server.playSound(null, valine.blockPosition(), this.getAttackSound(), SoundSource.HOSTILE);
            }
        }
    }

    public void beforeAttack(ValineEntity valine, LivingEntity target) {
        valine.trigger(valine, valine.level(), this.getAnimationId());
    }
}