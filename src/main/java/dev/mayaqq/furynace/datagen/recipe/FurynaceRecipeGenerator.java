package dev.mayaqq.furynace.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.List;
import java.util.function.Consumer;

public class FurynaceRecipeGenerator extends FabricRecipeProvider {
    public FurynaceRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        RecipeProvider.offerSmelting(exporter, List.of(Items.GUNPOWDER), RecipeCategory.COMBAT , Items.CHARCOAL, 0.1F, 201, "explod");
    }
}
