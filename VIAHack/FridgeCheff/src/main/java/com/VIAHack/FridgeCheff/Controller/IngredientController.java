    package com.VIAHack.FridgeCheff.Controller;

    import com.VIAHack.FridgeCheff.Service.ChatGptService;
    import com.VIAHack.FridgeCheff.Service.RecipeApiService;
    import com.VIAHack.FridgeCheff.Service.RecipeInstructionService;
    import com.VIAHack.FridgeCheff.util.RecipeSorter;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    import org.springframework.web.reactive.function.client.WebClient;

    @RestController
    @RequestMapping("/api")
    @CrossOrigin(origins = "*")
    public class IngredientController {


        private final WebClient webClient;
        private final ChatGptService chatGptService;
        private final RecipeApiService recipeApiService;
        private final RecipeInstructionService recipeInstructionService;


        public IngredientController(ChatGptService chatGptService,
                                    RecipeApiService recipeApiService,
                                    WebClient.Builder webClientBuilder, RecipeInstructionService recipeInstructionService) {
            this.chatGptService = chatGptService;
            this.recipeApiService = recipeApiService;
            this.webClient = webClientBuilder.build();
            this.recipeInstructionService = recipeInstructionService;
        }



        @PostMapping("/upload-and-get-recipes")
        public ResponseEntity<?> uploadAndGetRecipes(@RequestParam("file") MultipartFile file) {
            try {
                String ingredientsText = chatGptService.extractIngredients(file);
                ingredientsText.replaceAll("\\s+", "");
                System.out.println(ingredientsText);
                String unsortedJson = recipeApiService.getRecipes(ingredientsText);
                String sortedJson = RecipeSorter.sortRecipesByMissingIngredients(unsortedJson);
                return ResponseEntity.ok(sortedJson);

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error: " + e.getMessage());
            }
        }

        @PostMapping("/upload-and-get-reipes-manualy")
        public ResponseEntity<?> uploadAndGetRecipesManualy(@RequestParam("query") String queryParam) {
            try {
                String ingredientsText = queryParam.replaceAll("\\s+", "");
                String unsortedJson = recipeApiService.getRecipes(ingredientsText);
                String sortedJson = RecipeSorter.sortRecipesByMissingIngredients(unsortedJson);
                return ResponseEntity.ok(sortedJson);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error: " + e.getMessage());
            }
        }

        @GetMapping("/instructions/{id}")
        public ResponseEntity<?> getInstructions(@PathVariable int id) {
            try {
                String instructions = recipeInstructionService.getAnalyzedInstructions(id);
                return ResponseEntity.ok(instructions);
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Error: " + e.getMessage());
            }
        }



    }