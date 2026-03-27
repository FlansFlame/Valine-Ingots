package net.flansflame.valine_ingots.entities.entity;

import net.flansflame.flans_knowledge_lib.Utils;
import net.flansflame.flans_knowledge_lib.mixin_accesor.IEntityMixinAccessor;
import net.flansflame.flans_knowledge_lib.world.entity.IOnRemoved;
import net.flansflame.valine_ingots.damagesource.ModDamageTypes;
import net.flansflame.valine_ingots.entities.ModEntities;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;

public class LaserTurretEntity extends Entity implements GeoEntity, IOnRemoved, IImmune2ValineEP {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final int KILL_COUNT = 200;
    private static final int LASER_RADIUS = 10;
    private static final int LASER_SEGMENTS = 20;

    private float angle = 0;

    public LaserTurretEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();

        angle += 12f;
        if (angle >= 360f) angle -= 360f;

        double rad = Math.toRadians(angle);
        Vec3 origin = this.position().add(0, 0.5, 0);
        Vec3 dir = new Vec3(Math.cos(rad), 0, Math.sin(rad));

        for (int i = 0; i < LASER_SEGMENTS; i++) {
            double t = i / (double) LASER_SEGMENTS;
            Vec3 pos = origin.add(dir.scale(LASER_RADIUS * t));

            if (this.level() instanceof ClientLevel client) {
                client.addParticle(DustParticleOptions.REDSTONE, pos.x, pos.y, pos.z, 0, 0, 0);
            } else if (this.level() instanceof ServerLevel server) {
                List<LivingEntity> entities = server.getEntitiesOfClass(LivingEntity.class, new AABB(BlockPos.containing(pos)).inflate(0.5f));

                for (LivingEntity entity : entities) {
                    if (entity == null) continue;
                    entity.hurt(Utils.createDamageSource(server, ModDamageTypes.VALINE_ATTACK), 6f);
                }
            }
        }

        if (this.shouldDie()) {
            this.exDeath();
        }
    }

    public boolean shouldDie() {
        return this.tickCount >= KILL_COUNT;
    }

    /*GECKOLIB*/
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    public <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    /*SETTINGS*/
    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale((float) 1);
    }

    @Override
    public boolean canChangeDimensions() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }


    /*INVINCIBILITY*/
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void remove(RemovalReason reason) {
        return;
    }

    private boolean removed;

    @Override
    public void flansKnowledgeLib$onRemoved() {
        if (!removed) {
            if (this.level() instanceof ServerLevel server) {
                LaserTurretEntity entityToSpawn = ModEntities.LASER_TURRET.get().spawn(server, this.blockPosition(), MobSpawnType.COMMAND);
                if (entityToSpawn != null) {
                    entityToSpawn.setUUID(this.getUUID());
                    entityToSpawn.setYRot(this.getYRot());
                    entityToSpawn.setYHeadRot(this.getYHeadRot());
                    entityToSpawn.setPos(new Vec3(this.getX(), this.getY(), this.getZ()));
                }
            }
            removed = true;
        }
    }

    @Override
    public void kill() {
        return;
    }

    public void exDeath() {
        this.level().broadcastEntityEvent(this, (byte) 60);

        if (this.getRemovalReason() == null) {
            ((IEntityMixinAccessor) this).flansKnowledgeLib$setRemovalReason(RemovalReason.DISCARDED);
        }

        if (this.getRemovalReason() == null) return;

        if (this.getRemovalReason().shouldDestroy()) {
            this.stopRiding();
        }

        this.getPassengers().forEach(Entity::stopRiding);
        ((IEntityMixinAccessor) this).flansKnowledgeLib$getLevelCallback().onRemove(RemovalReason.KILLED);
        this.invalidateCaps();
    }


    /*SYNCED_DATA*/
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void defineSynchedData() {
    }
}