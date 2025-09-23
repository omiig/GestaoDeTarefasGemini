package com.gestaodetarefas;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    public int menuMain() {
        int resp = 0;

        while (resp < 1 || resp >6) {
            System.out.println("""
                * O que você deseja fazer? *
                
                1 - Vizualizar sua lista de tarefas
                2 - Adicionar uma nova tarefa
                3 - Remover uma tarefa
                4 - Editar uma tarefa
                5 - Salvar lista de tarefas em txt.
                6 - Sair
                """);

            resp = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            if (resp < 1 || resp > 6) {
                System.out.println("Operação não reconhecida!");
                System.out.println("Tente novamente!");
                continue;
            }

            break;

        }
        return resp;
    }
}
