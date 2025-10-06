package net.flansflame.valine_ingots.world.recipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.world.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ValineExtractorRecipeCategory implements IRecipeCategory<ValineExtractorRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(ValineIngots.MOD_ID, "valine_extracting");
    public static final ResourceLocation TEXTURES = new ResourceLocation(ValineIngots.MOD_ID, "textures/gui/valine_extractor.png");

    private final IDrawable background;
    private final IDrawable icon;

    public static final RecipeType<ValineExtractorRecipe> TYPE =
            new RecipeType<>(UID, ValineExtractorRecipe.class);

    public ValineExtractorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURES, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.VALINE_EXTRACTOR.get()));
    }

    @Override
    public RecipeType<ValineExtractorRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.valine_ingots.valine_extractor.gui");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void draw(ValineExtractorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int tick = recipe.getTick();
        if (tick > 0){
            int tickInSeconds = tick / 20;
            Component tickInString = Component.translatable("block." + ValineIngots.MOD_ID + ".valine_extractor.category.tick", tickInSeconds);
            guiGraphics.drawString(Minecraft.getInstance().font, tickInString, 80 + 19, 59 + 9, -8355712, false);
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ValineExtractorRecipe recipe, IFocusGroup focuses) {
        IRecipeCategory.super.createRecipeExtras(builder, recipe, focuses);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ValineExtractorRecipe recipe, IFocusGroup group) {
        builder.addSlot(RecipeIngredientRole.INPUT, 70 ,12).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 92 ,12).addIngredients(recipe.getAdditional());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 58).addItemStack(recipe.getResultItem(null));
    }
}
