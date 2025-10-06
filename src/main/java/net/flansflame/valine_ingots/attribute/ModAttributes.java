package net.flansflame.valine_ingots.attribute;

import net.flansflame.valine_ingots.ValineIngots;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ValineIngots.MOD_ID);

    public static final RegistryObject<Attribute> MULTI_BARRIER = ATTRIBUTES.register("multi_barrier",
            () -> new RangedAttribute(ValineIngots.MOD_ID + ":multi_barrier", 0f, 0f, 1f).setSyncable(true));

    public static final RegistryObject<Attribute> CRITICAL = ATTRIBUTES.register("critical",
            () -> new RangedAttribute(ValineIngots.MOD_ID + ":critical", 0f, 0f, 1f).setSyncable(true));

    public static void register(IEventBus eventBus){
        ATTRIBUTES.register(eventBus);
    }
}