package com.VIAHack.FridgeCheff.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = false)
public class Recipe {
    public int id;
    public String title;
    public String image;
    public String imageType;
    public int usedIngredientCount;
    public int missedIngredientCount;
    public int likes;

    public List<Ingredient> missedIngredients;
    public List<Ingredient> usedIngredients;
    public List<Ingredient> unusedIngredients;

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }
}