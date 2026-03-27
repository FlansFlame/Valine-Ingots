package net.flansflame.valine_ingots.entities.entity;

import net.flansflame.flans_knowledge_lib.mixin_accesor.IEntityMixinAccessor;
import net.flansflame.flans_knowledge_lib.world.entity.IBossBar;
import net.flansflame.flans_knowledge_lib.world.entity.IOnRemoved;
import net.flansflame.valine_ingots.entities.ModEntities;
import net.flansflame.valine_ingots.entities.ai.MiniValineAttackGoal;
import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineActiveSkill;
import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineActiveSkills;
import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineGoal;
import net.flansflame.valine_ingots.entities.ai.valine.passives.ValinePassiveSkill;
import net.flansflame.valine_ingots.entities.ai.valine.passives.ValinePassiveSkills;
import net.flansflame.valine_ingots.items.ModItems;
import net.flansflame.valine_ingots.tag.ModTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.ArrayList;
import java.util.List;

public class MiniValineEntity extends Monster implements GeoEntity, IOnRemoved {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public static final EntityDataAccessor<Float> EX_HP = SynchedEntityData.defineId(MiniValineEntity.class, EntityDataSerializers.FLOAT);

    public static float MAX_EX_HP = 65f;
    public static float ATTACK_DAMAGE = 20f;

    public MiniValineEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    /*ATTACKS*/
    @Override
    public void tick() {
        if (!this.isAlive() && this.isDeadOrDying()) {
            this.exDeath();
        } else {
            this.unsetRemoved();
        }

        if (this.getExHp() > MAX_EX_HP) {
            this.setExHp(MAX_EX_HP);
        }

        super.tick();
    }


    /*GECKOLIB*/
    public void create(AnimatableManager.ControllerRegistrar controllerRegistrar, String id) {
        controllerRegistrar.add(new AnimationController<>(this, id + "_controller", state -> PlayState.STOP)
                .triggerableAnim(id, RawAnimation.begin().then(id, Animation.LoopType.PLAY_ONCE)));
    }

    public void create(AnimatableManager.ControllerRegistrar controllerRegistrar, String id, Animation.LoopType loopType) {
        controllerRegistrar.add(new AnimationController<>(this, id + "_controller", state -> PlayState.STOP)
                .triggerableAnim(id, RawAnimation.begin().then(id, loopType)));
    }

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

    public void trigger(MiniValineEntity valine, Level level, String id) {
        if (level instanceof ServerLevel) valine.triggerAnim(id + "_controller", id);
    }


    /*SETTINGS*/
    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

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
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MiniValineAttackGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder.add(Attributes.MAX_HEALTH, MAX_EX_HP);
        builder.add(Attributes.MOVEMENT_SPEED, 0.3f);
        builder.add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE);
        builder.add(Attributes.ATTACK_SPEED, 2.7);
        return builder;
    }

    public float getAttackDamage() {
        AttributeInstance attackAttribute = this.getAttribute(Attributes.ATTACK_DAMAGE);
        return attackAttribute != null ? (float) attackAttribute.getValue() : 0f;
    }

    @Override
    public void checkDespawn() {
    }


    /*INVINCIBILITY*/
    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity instanceof Player player && player.getMainHandItem().is(ModTags.Items.CAN_DAMAGE_VALINE)) {
            this.damageExHp(amount);
            return super.hurt(this.damageSources().outOfBorder(), 0f);
        }
        return false;
    }

    @Override
    public float getHealth() {
        return this.getExHp();
    }

    @Override
    public void setHealth(float amount) {
        return;
    }

    @Override
    public void heal(float amount) {
        return;
    }

    public void damageExHp(float exHp) {
        float modExHp = exHp;
        if (this.getExHp() - exHp < 0) {
            modExHp += this.getExHp() - exHp;
        }
        this.addExHp(-modExHp);
    }

    public void exHeal(float amount) {
        if (amount <= 0f) return;

        float exHealth = this.getHealth();
        if (exHealth > 0f) {
            this.setHealth(this.getExHp() + amount);
        }
    }

    @Override
    public boolean isAlive() {
        return !this.isRemoved() && this.getExHp() > 0.0F;
    }

    @Override
    public boolean isDeadOrDying() {
        return this.getExHp() <= 0.0F;
    }

    @Override
    public void remove(RemovalReason reason) {
        return;
    }

    public void setBrain(Brain<?> brain) {
        this.brain = brain;
    }

    private boolean removed;

    @Override
    public void flansKnowledgeLib$onRemoved() {
        if (!removed) {
            if (this.level() instanceof ServerLevel server) {
                var entityToSpawn = ModEntities.MINI_VALINE.get().spawn(server, this.blockPosition(), MobSpawnType.COMMAND);
                if (entityToSpawn != null) {
                    entityToSpawn.setExHp(this.getExHp());
                    entityToSpawn.setUUID(this.getUUID());
                    entityToSpawn.setYRot(this.getYRot());
                    entityToSpawn.setYHeadRot(this.getYHeadRot());
                    entityToSpawn.setPos(new Vec3(this.getX(), this.getY(), this.getZ()));
                    entityToSpawn.setBrain(this.getBrain());
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
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);

            if (this.getRemovalReason() == null) {
                ((IEntityMixinAccessor) this).flansKnowledgeLib$setRemovalReason(RemovalReason.KILLED);
            }

            if (this.getRemovalReason() == null) return;

            if (this.getRemovalReason().shouldDestroy()) {
                this.stopRiding();
            }

            this.getPassengers().forEach(Entity::stopRiding);
            ((IEntityMixinAccessor) this).flansKnowledgeLib$getLevelCallback().onRemove(RemovalReason.KILLED);
            this.invalidateCaps();
            this.brain.clearMemories();
        }
    }


    /*SYNCED_DATA*/
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("ExHp")) this.setExHp(tag.getFloat("ExHp"));
        else this.setExHp(MAX_EX_HP);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("ExHp", this.getExHp());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EX_HP, MAX_EX_HP);
    }

    public void setExHp(float exHp) {
        this.entityData.set(EX_HP, exHp);
    }

    public float getExHp() {
        return this.entityData.get(EX_HP);
    }

    public void addExHp(float exHp) {
        this.setExHp(this.getExHp() + exHp);
    }
}
