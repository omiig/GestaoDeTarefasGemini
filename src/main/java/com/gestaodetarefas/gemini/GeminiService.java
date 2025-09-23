package com.gestaodetarefas.gemini;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.GenerativeModel;

import java.io.IOException;

public class GeminiService {

    private final GenerativeModel model;


    public GeminiService(String apiKey) {
        this.model = new GenerativeModel("gemini-1.5-flash", apiKey);
    }

    public String gerarTarefa(String prompt) throws IOException {
        GenerateContentResponse response = model.generateContent(prompt);
        return response.text();
    }
}
