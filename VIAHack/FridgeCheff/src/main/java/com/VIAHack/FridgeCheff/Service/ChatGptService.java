package com.VIAHack.FridgeCheff.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;

@Service
public class ChatGptService {

   private String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyCYRPLk9ZgexTzKBFwn8TklFmWqeVMFZAY";

    public String extractIngredients(MultipartFile file) throws Exception {

        byte[] imageBytes = file.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        RestTemplate restTemplate = new RestTemplate();

        String prompt = "List all visible ingredients in this food image. Return only the ingredient names divided by coma. No extra text.";

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt),
                                Map.of(
                                        "inline_data", Map.of(
                                                "mime_type", "image/jpeg",
                                                "data", base64Image
                                        )
                                )
                        ))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(response.getBody());

        String text = rootNode.at("/candidates/0/content/parts/0/text").asText();

        System.out.println(text);

        return text;
    }
    }
