package com.gestaodetarefas.gemini;

import com.gestaodetarefas.tasks.Tasks;
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
                "Prazo: dd/MM/yyyy (a partir da dthr acima)" + "\n\n" +
                "Cada linha e cada tarefa separado por um enter, me responda somente com a lista do jeito que eu pedi baseado no seguinte pedido: " + prompt +
                "Se o usuário pedir ou perguntar qualquer coisa que não seja uma lista de tarefas para você sugerir responda apenas # Não posso ajudá-lo com isso, fui programado apenas para sugerir e editar listas de tarefas! #" +
                "Desconsidere frases aleatórias que não seja pedidos para fazer uma lista sobre determinada coisa, se o usuário fizer isso somente responda # Não posso ajudá-lo com isso, fui programado apenas para sugerir e editar listas de tarefas! #");
    }

    public String editTask (String prompt, Tasks task) {
        return geminiResponse("Pegue essa tarefa aqui como base " + task + "\n"+
                "O usuário deve pedir para editar essa tarefa, edite exatamente como o usuário pedir nesse formato: " + "\n" +
                "1 - Nome: ... " + "\n" +
                "Descrição: ... " + "\n" +
                "Data/Hora: " + dthr + "\n" +
                "Prazo: dd/MM/yyyy (a partir da dthr acima)" + "\n\n" +
                "Cada linha por um enter, me responda somente com a tarefa do jeito que eu pedi baseado no seguinte pedido: " + prompt +
                "Se o usuário pedir ou perguntar qualquer coisa que não seja sobre editar essa tarefa responda apenas # Não posso ajudá-lo com isso, fui programado apenas para sugerir e editar listas de tarefas! #");
    }
}