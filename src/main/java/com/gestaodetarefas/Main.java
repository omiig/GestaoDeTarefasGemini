package com.gestaodetarefas;

import com.gestaodetarefas.gemini.GeminiService;
import com.gestaodetarefas.gemini.TaskParser;
import com.gestaodetarefas.tasks.Tasks;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Tasks> list = new ArrayList<>();
        Menu menu = new Menu();
        GeminiService gemini = new GeminiService();
        String useAI = "";
        boolean running = true;

        System.out.println("================================");
        System.out.println("* Sistema de Gestão de Tarefas *");
        System.out.println("================================");
        System.out.println();

        int maxId = 0;
        File file = new File("texts" + File.separator + "tasks.txt");

        file.getParentFile().mkdirs();

        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] partIdNome = line.split(" - Nome: ");
                int id = Integer.parseInt(partIdNome[0].trim());
                String name = partIdNome[1].trim();

                String descLine = br.readLine();
                String desc = descLine.replace("Descrição: ", "").trim();

                String dateHourLine = br.readLine();
                String dthr = dateHourLine.replace("Data/Hora: ", "").trim();

                String prazoLine = br.readLine();
                String prazo = prazoLine.replace("Prazo: ", "").trim();

                br.readLine();

                list.add(new Tasks(id, name, desc, dthr, prazo));

                if (id > maxId) maxId = id;
            }
        } catch (FileNotFoundException e) {} catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        Tasks.idCounter = maxId + 1;

        while (running) {
            switch (menu.menuMain()) {
                case 1:
                    if (list.isEmpty()) {
                        System.out.println("Sua lista está vazia! Adicione tarefas para ver");
                    } else {
                        for (Tasks taskLoop : list) {
                            System.out.print(taskLoop.getId() + " - ");
                            System.out.println("Nome: " + taskLoop.getName());
                            System.out.println("Descrição: " + taskLoop.getDescription());
                            System.out.println("Data/Hora: " + taskLoop.getDthr());

                            if (taskLoop.getPrazo().equals("0")) {
                                System.out.println("Prazo: Sem Prazo Definido");
                            } else {
                                System.out.println("Prazo: " + taskLoop.getPrazo());
                            }
                            System.out.println();
                        }
                    }
                    continue;

                case 2:
                    System.out.print("Gostaria de usar o Gemini? ");
                    useAI = scanner.nextLine();

                    if (useAI.equalsIgnoreCase("sim")) {
                        System.out.print("\nDigite um prompt para gerar tarefas automáticas, aperte ENTER e aguarde: ");
                        String prompt = scanner.nextLine();
                        System.out.println();

                        String suggestedTaskList = gemini.suggestTaskList(prompt);
                        System.out.println(suggestedTaskList);

                        String resp = "";

                        while (true) {
                            System.out.println("""
                                Digite S para adicionar a sugestão em sua lista
                                Digite N para não adicionar a sugestão em sua lista
                                Digite G para gerar uma nova sugestão
                                """);

                            resp = scanner.nextLine();

                            if (resp.equalsIgnoreCase("S")) {
                               List <Tasks> geminiList = TaskParser.parseTasks(suggestedTaskList);
                               list.addAll(geminiList);
                                System.out.println("Lista sugerida adicionada com sucesso!");
                                break;

                            } else if (resp.equalsIgnoreCase("N")) {
                                System.out.println("Voltando a aplicação!");
                                break;
                            } else if (resp.equalsIgnoreCase("G")) {
                                System.out.print("Digite outro prompt caso queira: ");
                                String resp2 = scanner.nextLine();

                                if (resp2.isEmpty()) {
                                    suggestedTaskList = gemini.suggestTaskList(prompt);
                                } else {
                                    prompt += resp2;
                                    suggestedTaskList = gemini.suggestTaskList(prompt);
                                }

                                System.out.println(suggestedTaskList);
                            } else {
                                System.out.println("Operação não reconhecida!");
                            }
                        }
                    } else {
                        Tasks task = new Tasks();

                        System.out.print("Nome da tarefa: ");
                        task.setName(scanner.nextLine());

                        System.out.print("\nDescrição da tarefa: ");
                        task.setDescription(scanner.nextLine());

                        System.out.print("\nPrazo da tarefa ou 0 para deixar sem prazo: ");
                        task.setPrazo(scanner.nextLine());

                        list.add(task);

                        System.out.println("Tarefa adicionada com sucesso!");
                    }
                    continue;

                case 3:
                    System.out.print("Digite o número da tarefa que você quer remover: ");
                    int resp = scanner.nextInt();
                    scanner.nextLine();
                    boolean find = false;

                    Tasks taskFind = null;
                        
                    for (Tasks taskLoop : list) {
                        if (taskLoop.getId() == resp) {
                            taskFind = taskLoop;
                            find = true;
                            break;
                        }
                    }
                        
                    if (!find) {
                        System.out.println("Não encontrei essa tarefa! Tente novamente");
                    } else {
                        System.out.print(taskFind.getId() + " - ");
                        System.out.println("Nome: " + taskFind.getName());
                        System.out.println("Descrição: " + taskFind.getDescription());

                        if (taskFind.getPrazo().equals("0")) {
                            System.out.println("Prazo: Sem Prazo Definido");
                        } else {
                            System.out.println("Prazo: " + taskFind.getPrazo());
                        }

                        System.out.println();

                        System.out.println("Deseja excluir essa tarefa?");
                        String resp2 = scanner.next();

                        if (resp2.equalsIgnoreCase("sim")) {
                            list.remove(taskFind);
                            System.out.println("Tarefa excluída com sucesso!");
                            for (Tasks minusID : list) {
                                if (minusID.getId() > taskFind.getId()) {
                                    minusID.setId(minusID.getId() - 1);
                                }
                            }
                        } else {
                            System.out.println("Voltando para aplicação");
                        }
                    }
                    continue;
                case 4:
                    System.out.print("Digite o número da tarefa que você quer editar: ");
                    resp = scanner.nextInt();
                    scanner.nextLine();

                    find = false;
                    taskFind = null;

                    for (Tasks taskLoop : list) {
                        if (taskLoop.getId() == resp) {
                            taskFind = taskLoop;
                            find = true;
                            break;
                        }
                    }

                    if (!find) {
                        System.out.println("Não encontrei essa tarefa! Tente novamente");
                    } else {
                        System.out.print(taskFind.getId() + " - ");
                        System.out.println("Nome: " + taskFind.getName());
                        System.out.println("Descrição: " + taskFind.getDescription());
                        if (taskFind.getPrazo().equals("0")) {
                            System.out.println("Prazo: Sem Prazo Definido");
                        } else {
                            System.out.println("Prazo: " + taskFind.getPrazo());
                        }
                        System.out.println();

                        System.out.print("Você quer editar essa tarefa? ");
                        String resp2 = scanner.nextLine();

                        if (resp2.equalsIgnoreCase("sim")) {
                            System.out.print("Gostaria de usar o Gemini? ");
                            useAI = scanner.nextLine();

                            if (useAI.equalsIgnoreCase("sim")) {
                                System.out.print("\nDigite um prompt sobre como você deseja editar essa tarefa, aperte ENTER e aguarde: ");
                                String prompt = scanner.nextLine();
                                String geminiEditedTask = gemini.editTask(prompt, taskFind);
                                System.out.println(geminiEditedTask);

                                System.out.println("""
                                        Digite S para confirmar a edição a sugestão em sua lista
                                        Digite N para não confirmar a edição a sugestão em sua lista
                                        """);

                                String resp3 = scanner.nextLine();
                                if (resp3.equalsIgnoreCase("s")) {
                                    Tasks geminiEdited = TaskParser.parseEditTask(geminiEditedTask);
                                    taskFind.setName(geminiEdited.getName());
                                    taskFind.setDescription(geminiEdited.getDescription());
                                    taskFind.setPrazo(geminiEdited.getPrazo());
                                    System.out.println("Tarefa editada com sucesso!");
                                } else {
                                    System.out.println("Voltando a aplicação!");
                                }

                            } else {
                                System.out.print("Nome da tarefa: ");
                                taskFind.setName(scanner.nextLine());

                                System.out.print("\nDescrição da tarefa: ");
                                taskFind.setDescription(scanner.nextLine());

                                System.out.print("\nPrazo: ");
                                taskFind.setPrazo(scanner.nextLine());

                                System.out.println("Tarefa editada com sucesso!");
                            }
                        } else {
                            System.out.println("Voltando para aplicação");
                        }
                    }
                    continue;

                case 5:
                    file.getParentFile().mkdirs();

                    try (FileWriter arq = new FileWriter(file);
                         PrintWriter gravador = new PrintWriter(arq)){

                        if (list.isEmpty()) {
                            System.out.println("Sua lista está vazia! Adicione tarefas para salvar");
                        } else {
                            for (Tasks taskLoop : list) {
                                gravador.print(taskLoop.getId() + " - ");
                                gravador.println("Nome: " + taskLoop.getName());
                                gravador.println("Descrição: " + taskLoop.getDescription());
                                gravador.println("Data/Hora: " + taskLoop.getDthr());

                                if (taskLoop.getPrazo().equals("0")) {
                                    gravador.println("Prazo: Sem Prazo Definido");
                                } else {
                                    gravador.println("Prazo: " + taskLoop.getPrazo());
                                }

                                gravador.println();
                            }

                            arq.close();
                            System.out.println("Aquivo gravado com sucesso em \"" + file.getPath() +"\"");
                        }
                    } catch (IOException e) {
                        System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
                    }
                    continue;
                case 6:
                    System.out.println("Encerrando a aplicação!");
                    running = false;
            }
        }
    }
}