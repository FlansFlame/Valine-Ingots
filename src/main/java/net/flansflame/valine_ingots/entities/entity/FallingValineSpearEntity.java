package net.flansflame.valine_ingots.entities.entity;

import net.flansflame.flans_knowledge_lib.mixin_accesor.IEntityMixinAccessor;
import net.flansflame.flans_knowledge_lib.world.entity.IOnRemoved;
import net.flansflame.valine_ingots.entities.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class FallingValineSpearEntity extends Entity implements GeoEntity, IOnRemoved {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final int FALL_TICK = 20;
    public static final int DEATH_TICK = 60;
    public static final float SINGLE_FALL_AMOUNT = 1.2f;

    public FallingValineSpearEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.shouldFall()) {
            this.setPos(new Vec3(this.getX(), this.getY() - SINGLE_FALL_AMOUNT, this.getZ()));

        }
        if (this.shouldDie()) {
            this.exDeath();
        }
    }

    public boolean shouldFall() {
        return tickCount >= FALL_TICK && this.level().getBlockState(this.getOnPos()).isAir();
    }

    public boolean shouldDie() {
        return tickCount >= DEATH_TICK && !this.level().getBlockState(this.getOnPos()).isAir();
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
                FallingValineSpearEntity entityToSpawn = ModEntities.FALLING_VALINE_SPEAR_ENTITY.get().spawn(server, this.blockPosition(), MobSpawnType.COMMAND);
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
        if (this.level() instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY() + 0.5, this.getZ(), 1, 0, 0, 0, 0);
            server.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE);
        }

        this.level().broadcastEntityEvent(this, (byte) 60);

        if (this.getRemovalReason() == null) {
            ((IEntityMixinAccessor) this).flansKnowledgeLib$setRemovalReason(RemovalReason.DISCARDED);
        }

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