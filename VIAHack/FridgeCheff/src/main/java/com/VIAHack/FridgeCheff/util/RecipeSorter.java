package com.VIAHack.FridgeCheff.util;
import com.VIAHack.FridgeCheff.Model.Recipe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class RecipeSorter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String sortRecipesByMissingIngredients(String jsonArray) throws Exception {
        List<Recipe> recipes = mapper.readValue(jsonArray, new TypeReference<>() {});
        recipes.sort(Comparator.comparingInt(r -> r.missedIngredientCount));
        for (Recipe recipe : recipes) {
            System.out.println("===============");
            System.out.println(recipe.title);
            System.out.println(recipe.missedIngredientCount);
        }
        return mapper.writeValueAsString(recipes);
    }
}