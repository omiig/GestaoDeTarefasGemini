package com.gestaodetarefas.gemini;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeminiService {
    Client client = new Client();
    Date currentDateHour = new Date();
    String date = new SimpleDateFormat("dd/MM/yyyy").format(currentDateHour);
    String hour = new SimpleDateFormat("HH:mm:ss").format(currentDateHour);
    String dthr = date + " " + hour;

    private String respSuggestTaskList;

    public String geminiResponse (String question) {
        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        question,
                        null);

        return response.text();
    }

    public String suggestTaskList(String prompt) {
        return geminiResponse("Gere uma lista de tarefas no formato:" + "\n" +
                "1 - Nome: ... " + "\n" +
                "Descrição: ..." + "\n" +
                "Data/Hora: " + dthr + "\n" +
                "Prazo: dd/MM/yyyy " + "\n\n" +
                "Cada linha e cada tarefa separado por um enter, me responda somente com a lista do jeito que eu pedi baseado no seguinte pedido: " + prompt);
    }

    public String getRespSuggestTaskList() {
        return respSuggestTaskList;
    }
}