package net.flansflame.valine_ingots.component;

import net.flansflame.flans_knowledge_lib.component.IntegerTag;
import net.flansflame.flans_knowledge_lib.component.TagRegisterer;
import net.flansflame.valine_ingots.ValineIngots;

public class ModComponents {
    public static final TagRegisterer TAGS = new TagRegisterer(ValineIngots.MOD_ID);

    public static final IntegerTag REFINE = TAGS.register(new IntegerTag("refine"));

    public static final IntegerTag MULTI_BARRIER_STACK = TAGS.register(new IntegerTag("multi_barrier_stack"));
}
