package net.flansflame.valine_ingots.entities;

import net.flansflame.valine_ingots.entities.renderer.FallingValineSpearRenderer;
import net.flansflame.valine_ingots.entities.renderer.ValineRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderer {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.VALINE_ENTITY.get(), renderManager -> new ValineRenderer<>(renderManager, "valine"));
        event.registerEntityRenderer(ModEntities.FALLING_VALINE_SPEAR_ENTITY.get(), renderManager -> new FallingValineSpearRenderer<>(renderManager, "falling_valine_spear"));
    }
}
