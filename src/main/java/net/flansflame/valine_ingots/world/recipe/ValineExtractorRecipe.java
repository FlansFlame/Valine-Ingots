package net.flansflame.valine_ingots.world.recipe;

import com.google.gson.JsonObject;
import net.flansflame.valine_ingots.ValineIngots;
import net.flansflame.valine_ingots.world.block_entity.custom.ValineExtractorBlockEntity;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class ValineExtractorRecipe implements Recipe<SimpleContainer> {

    /*VARIABLES*/
    private final Ingredient ingredient;
    private final Ingredient additional;
    private final ItemStack result;
    private final Integer tick;
    private final ResourceLocation id;



    /*CONSTRUCTOR*/
    public ValineExtractorRecipe(Ingredient ingredient, Ingredient additional, ItemStack result, Integer tick, ResourceLocation id) {
        this.ingredient = ingredient;
        this.additional = additional;
        this.result = result;
        this.tick = tick;
        this.id = id;
    }



    /*INITIALISES-GETTERS-SETTERS*/
    @Override
    public boolean matches(SimpleContainer container, Level level) {
        if (level.isClientSide) {
            return false;
        }

        return ingredient.test(container.getItem(ValineExtractorBlockEntity.INPUT_SLOT)) && additional.test(container.getItem(ValineExtractorBlockEntity.ADDITIONAL_SLOT));
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int hight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return result.copy();
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Ingredient getAdditional() {
        return additional;
    }

    public Integer getTick() {
        return tick;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ValineExtractorRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "valine_extracting";
    }



    /*OTHERS*/
    public static class Serializer implements RecipeSerializer<ValineExtractorRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(ValineIngots.MOD_ID, "valine_extracting");

        @Override
        public ValineExtractorRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            Integer tick = GsonHelper.getAsInt(json, "tick");

            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getNonNull(json, "ingredient"));
            Ingredient additional = Ingredient.of(new ItemStack(Items.IRON_NUGGET));

            return new ValineExtractorRecipe(ingredient, additional, output, tick, id);
        }

        @Override
        public ValineExtractorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            Ingredient additional = Ingredient.fromNetwork(buf);

            ItemStack output = buf.readItem();
            Integer tick = buf.readInt();

            return new ValineExtractorRecipe(ingredient, additional, output, tick, id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ValineExtractorRecipe recipe) {
            recipe.getIngredient().toNetwork(buf);
            recipe.getAdditional().toNetwork(buf);

            buf.writeItemStack(recipe.getResultItem(null), false);
            buf.writeInt(recipe.getTick());
        }
    }
}
