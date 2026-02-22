package net.flansflame.valine_ingots.entities.ai.valine.active.custom;

import net.flansflame.flans_knowledge_lib.Utils;
import net.flansflame.valine_ingots.entities.ai.valine.active.ValineActiveSkill;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.flansflame.valine_ingots.damagesource.ModDamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class BoomActiveSkill extends ValineActiveSkill {

    public static final float DAMAGE_MULTIPLIER = 3f;
    public static final float RADIUS = 12f;

    public BoomActiveSkill(String animationId, SoundEvent attackSound, boolean activateEvenIfNotNear) {
        super(animationId, attackSound, activateEvenIfNotNear);
    }

    @Override
    public void onAttack(ValineEntity valine, LivingEntity target, float amount) {
        if (target == null) return;

        double x = valine.getX();
        double y = valine.getY();
        double z = valine.getZ();

        if (valine.level() instanceof ServerLevel server) {
            final Vec3 _center = new Vec3(x, y, z);
            List<LivingEntity> _entfound = server.getEntitiesOfClass(LivingEntity.class, new AABB(_center, _center)
                    .inflate(RADIUS), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (LivingEntity entity : _entfound) {
                if (entity != valine) {
                    entity.hurt(Utils.createDamageSource(server, ModDamageTypes.VALINE_ATTACK, valine), amount * DAMAGE_MULTIPLIER);
                }
            }
            server.playSound(null, valine.blockPosition(), this.getAttackSound(), SoundSource.HOSTILE);
        }
    }
}
