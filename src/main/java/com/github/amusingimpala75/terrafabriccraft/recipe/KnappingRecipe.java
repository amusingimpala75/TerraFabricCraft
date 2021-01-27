package com.github.amusingimpala75.terrafabriccraft.recipe;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.screenhandler.KnappingScreenHandler;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class KnappingRecipe implements Recipe<KnappingScreenHandler> {

    private final KnappingType type;
    private final ItemStack output;
    private final boolean[][] clickedSquares;

    public KnappingRecipe(KnappingType type, ItemStack output, boolean[][] clickedSquares) {
        this.type = type;
        this.output = output;
        this.clickedSquares = clickedSquares;
    }

    @Override
    public boolean matches(KnappingScreenHandler inv, World world) {
        if (inv.getClickedSquares() == clickedSquares && inv.getKnappingType().equals(type)) {
            return true;
        }
        return false;
    }

    @Override
    public ItemStack craft(KnappingScreenHandler inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.output.copy();
    }

    @Override
    public Identifier getId() {
        return TerraFabricCraft.getId("knapping");
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return TerraFabricCraftRecipes.KNAPPING_RECIPE;
    }

    @Override
    public RecipeType<?> getType() {
        return TerraFabricCraftRecipes.KNAPPING_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<KnappingRecipe> {

        @Override
        public KnappingRecipe read(Identifier id, JsonObject json) {
            String type = JsonHelper.getString(json, "material");
            JsonObject outputSubObject = JsonHelper.getObject(json, "output");
            Item outputItem = JsonHelper.getItem(outputSubObject, "item");
            int outputCount;
            if (outputSubObject.has("count")) {
                outputCount = JsonHelper.getInt(outputSubObject, "count");
            } else {
                outputCount = 1;
            }
            boolean[][] clickedSquares = new boolean[5][5];
            JsonArray clickedSquaresJsonArray = JsonHelper.getArray(json, "layout");
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    char val = clickedSquaresJsonArray.get((x*5)+y).getAsCharacter();
                    if (val == 'O') {
                        clickedSquares[x][y] = false;
                    } else if (val == ' ') {
                        clickedSquares[x][y] = true;
                    } else {
                        throw new JsonParseException("Value in recipe with output: "+outputItem.getTranslationKey()+"contained erroneous knapping key for punch-out slot!");
                    }
                }
            }
            return new KnappingRecipe(KnappingType.fromString(type), new ItemStack(outputItem, outputCount), clickedSquares);
        }

        @Override
        public KnappingRecipe read(Identifier id, PacketByteBuf buf) {
            String type = buf.readString();
            ItemStack output = buf.readItemStack();
            boolean[][] clickedSlots = new boolean[5][5];
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    clickedSlots[x][y] = buf.readBoolean();
                }
            }
            return new KnappingRecipe(KnappingType.fromString(type), output, clickedSlots);
        }

        @Override
        public void write(PacketByteBuf buf, KnappingRecipe recipe) {
            buf.writeString(recipe.getGroup());
            buf.writeItemStack(recipe.output);
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    buf.writeBoolean(recipe.clickedSquares[x][y]);
                }
            }
        }
    }
}
