package net.flansflame.valine_ingots.entities.ai.valine.actives.active;

import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineActiveSkill;
import net.flansflame.valine_ingots.entities.ModEntities;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;

public class FallingSpearActiveSkill extends ValineActiveSkill {

    private static final int SPEAR_COUNT = 256;
    private static final int SPAWN_RADIUS = 24;
    private static final int TRY_COUNT = 8;
    private static final float HEIGHT = 5;

    public FallingSpearActiveSkill(String animationId, boolean activateEvenIfNotNear) {
        super(animationId, activateEvenIfNotNear);
    }

    @Override
    public void onAttack(ValineEntity valine, LivingEntity target, float amount) {

        if (target == null) return;

        if (valine.level() instanceof ServerLevel server) {
            for (int i = 0; i < SPEAR_COUNT; i++) {
                Vec3 pos = null;
                for (int j = 0; j < TRY_COUNT && (pos == null || !server.getBlockState(BlockPos.containing(pos)).isAir()); j++) {
                    pos = new Vec3(randomiseCord(valine.getX()), valine.getY() + HEIGHT, randomiseCord(valine.getZ()));
                }
                ModEntities.FALLING_VALINE_SPEAR_ENTITY.get().spawn(server, BlockPos.containing(pos), MobSpawnType.COMMAND);
            }
        }
    }

    private static float randomiseCord(double cord) {
        return (float) (cord + Mth.nextInt(RandomSource.create(), -SPAWN_RADIUS, SPAWN_RADIUS));
    }
}
