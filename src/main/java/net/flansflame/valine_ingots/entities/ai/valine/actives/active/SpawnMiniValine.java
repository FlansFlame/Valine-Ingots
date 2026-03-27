package net.flansflame.valine_ingots.entities.ai.valine.actives.active;

import net.flansflame.valine_ingots.entities.ModEntities;
import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineActiveSkill;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;

public class SpawnMiniValine extends ValineActiveSkill {

    private static final int SPAWN_COUNT = 6;
    private static final int SPAWN_RADIUS = 12;
    private static final int TRY_COUNT = 8;
    private static final float HEIGHT = 5;

    public SpawnMiniValine(String animationId, SoundEvent attackSound, boolean activateEvenIfNotNear) {
        super(animationId, attackSound, activateEvenIfNotNear);
    }

    @Override
    public void onAttack(ValineEntity valine, LivingEntity target, float amount) {

        if (target == null) return;

        if (valine.level() instanceof ServerLevel server) {
            for (int i = 0; i < SPAWN_COUNT; i++) {
                Vec3 pos = null;
                for (int j = 0; j < TRY_COUNT && (pos == null || !server.getBlockState(BlockPos.containing(pos)).isAir()); j++) {
                    pos = new Vec3(randomiseCord(valine.getX()), valine.getY() + HEIGHT, randomiseCord(valine.getZ()));
                }
                ModEntities.MINI_VALINE.get().spawn(server, BlockPos.containing(pos), MobSpawnType.COMMAND);
            }
        }
    }

    private static float randomiseCord(double cord) {
        return (float) (cord + Mth.nextInt(RandomSource.create(), -SPAWN_RADIUS, SPAWN_RADIUS));
    }
}
