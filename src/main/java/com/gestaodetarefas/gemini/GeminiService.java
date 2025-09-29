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
        return geminiResponse(
                "Você é um assistente de gestão de tarefas. " +
                        "Sua função é EXCLUSIVAMENTE sugerir listas de tarefas. " +
                        "Se o usuário pedir qualquer coisa que não seja para criar uma lista de tarefas, " +
                        "responda apenas exatamente com: '# Não posso ajudá-lo com isso, fui programado apenas para sugerir e editar listas de tarefas! #'.\n\n" +

                        "Formato obrigatório da resposta (sem explicações, apenas a lista):\n" +
                        "1 - Nome: ...\n" +
                        "Descrição: ...\n" +
                        "Data/Hora: " + dthr + "\n" +
                        "Prazo: dd/MM/yyyy (a partir da dthr acima)\n\n" +

                        "Cada tarefa deve ser separada por uma linha em branco.\n\n" +

                        "Pedido do usuário: " + prompt
        );
    }

    public String editTask (String prompt, Tasks task) {
        return geminiResponse(
                "Você é um assistente de gestão de tarefas. " +
                        "Sua função é EXCLUSIVAMENTE editar a tarefa fornecida. " +
                        "Se o usuário pedir qualquer coisa que não seja sobre editar esta tarefa, " +
                        "responda apenas exatamente com: '# Não posso ajudá-lo com isso, fui programado apenas para sugerir e editar listas de tarefas! #'.\n\n" +

                        "Aqui está a tarefa atual:\n" +
                        "Nome: " + task.getName() + "\n" +
                        "Descrição: " + task.getDescription() + "\n" +
                        "Data/Hora: " + task.getDthr() + "\n" +
                        "Prazo: " + task.getPrazo() + "\n\n" +

                        "Edite a tarefa conforme o pedido do usuário, mantendo o formato:\n" +
                        "1 - Nome: ...\n" +
                        "Descrição: ...\n" +
                        "Data/Hora: " + dthr + "\n" +
                        "Prazo: dd/MM/yyyy (a partir da dthr acima)\n\n" +

                        "Pedido do usuário: " + prompt
        );

    }
}