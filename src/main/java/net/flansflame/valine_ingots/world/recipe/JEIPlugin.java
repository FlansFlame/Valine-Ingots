package net.flansflame.valine_ingots.world.recipe;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.client.screen.custom.ValineExtractorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ValineIngots.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ValineExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ValineExtractorRecipe> extractorRecipes = recipeManager.getAllRecipesFor(ValineExtractorRecipe.Type.INSTANCE);

        registration.addRecipes(ValineExtractorRecipeCategory.TYPE, extractorRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ValineExtractorScreen.class, 80, 30, 20, 26, ValineExtractorRecipeCategory.TYPE);
    }
}
