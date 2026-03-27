package net.flansflame.valine_ingots.entities.ai.valine.actives.active;

import net.flansflame.valine_ingots.entities.ai.valine.actives.ValineActiveSkill;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class Teleport extends ValineActiveSkill {
    public Teleport(String animationId, boolean activateEvenIfNotNear) {
        super(animationId, activateEvenIfNotNear);
    }

    @Override
    public void onAttack(ValineEntity valine, LivingEntity target, float amount) {
        double x = valine.getX();
        double y = valine.getY();
        double z = valine.getZ();

        if (target == null) {
            if (valine.level() instanceof ServerLevel server) {
                final Vec3 _center = new Vec3(x, y, z);
                List<Player> _entfound = server.getEntitiesOfClass(Player.class, new AABB(_center, _center)
                        .inflate(ValineEntity.PASSIVE_SKILL_RADIUS), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();

                if (_entfound.isEmpty()) return;

                for (Player player : _entfound) {
                    valine.setPos(new Vec3(player.getX(), player.getY(), player.getZ()));
                    break;
                }
            }
        } else {
            valine.setPos(new Vec3(target.getX(), target.getY(), target.getZ()));
        }
    }
}
