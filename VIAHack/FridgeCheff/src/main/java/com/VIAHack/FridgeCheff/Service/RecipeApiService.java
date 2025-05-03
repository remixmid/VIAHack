package com.VIAHack.FridgeCheff.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecipeApiService {

    private final WebClient webClient;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    public RecipeApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .build();
    }

    public String getRecipes(String queryParam) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipes/findByIngredients")
                        .queryParam("ingredients", queryParam)
                        .queryParam("number", 25)
                        .queryParam("ignorePantry", true)
                        .queryParam("ranking", 2)
                        .build())
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", rapidApiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
