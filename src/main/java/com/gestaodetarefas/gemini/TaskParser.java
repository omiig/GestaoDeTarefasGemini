package com.gestaodetarefas.gemini;

import com.gestaodetarefas.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskParser {

    public static List<Tasks> parseTasks(String responseFromGemini) {
        List<Tasks> tasks = new ArrayList<>();

        String[] rawTasks = responseFromGemini.trim().split("\\n\\s*\\n");

        for (String raw : rawTasks) {
            String[] lines = raw.split("\\n");

            String name = "";
            String description = "";
            String dthr = "";
            String prazo = "";

            for (String line : lines) {
                if (line.contains("Nome:")) {
                    name = line.replaceFirst(".*Nome:", "").trim();
                } else if (line.contains("Descrição:")) {
                    description = line.replaceFirst(".*Descrição:", "").trim();
                } else if (line.contains("Data/Hora:")) {
                    dthr = line.replaceFirst(".*Data/Hora:", "").trim();
                } else if (line.contains("Prazo:")) {
                    prazo = line.replaceFirst(".*Prazo:", "").trim();
                }
            }

            Tasks task = new Tasks(Tasks.idCounter++, name, description, dthr, prazo);
            tasks.add(task);
        }

        return tasks;
    }

    public static Tasks parseEditTask (String responseFromGemini) {
        String[] rawTasks = responseFromGemini.trim().split("\\n\\s*\\n");
        String name = "";
        String description = "";
        String dthr = "";
        String prazo = "";

        for (String raw : rawTasks) {
            String[] lines = raw.split("\\n");

            for (String line : lines) {
                if (line.contains("Nome:")) {
                    name = line.replaceFirst(".*Nome:", "").trim();
                } else if (line.contains("Descrição:")) {
                    description = line.replaceFirst(".*Descrição:", "").trim();
                } else if (line.contains("Data/Hora:")) {
                    dthr = line.replaceFirst(".*Data/Hora:", "").trim();
                } else if (line.contains("Prazo:")) {
                    prazo = line.replaceFirst(".*Prazo:", "").trim();
                }
            }
        }

        Tasks task = new Tasks(Tasks.idCounter++, name, description, dthr, prazo);
        return task;
    }
}
