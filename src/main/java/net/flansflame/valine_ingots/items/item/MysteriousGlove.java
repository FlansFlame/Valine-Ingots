package net.flansflame.valine_ingots.items.item;

import net.flansflame.valine_ingots.entities.ModEntities;
import net.flansflame.valine_ingots.entities.entity.ValineEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MysteriousGlove extends Item {
    public MysteriousGlove(Properties build) {
        super(build);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();

        if (player == null) return InteractionResult.FAIL;

        if (player.level() instanceof ServerLevel server) {
            ValineEntity valine = ModEntities.VALINE_ENTITY.get().spawn(server, player.getOnPos().above(), MobSpawnType.COMMAND);
        }

        itemStack.shrink(1);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
        component.add(Component.translatable("item.valine_ingots.mysterious_glove.desc"));

        super.appendHoverText(itemStack, level, component, flag);
    }
}
