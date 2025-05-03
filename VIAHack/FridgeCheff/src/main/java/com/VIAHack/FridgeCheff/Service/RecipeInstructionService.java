package com.VIAHack.FridgeCheff.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RecipeInstructionService {

    private final WebClient webClient;

    @Value("${rapidapi.key}")
    private String rapidApiKey;

    public RecipeInstructionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .build();
    }

    public String getAnalyzedInstructions(int recipeId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recipes/{id}/analyzedInstructions")
                        .queryParam("stepBreakdown", true)
                        .build(recipeId))
                .header("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .header("x-rapidapi-key", rapidApiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}