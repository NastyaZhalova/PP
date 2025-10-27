package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Device[] devices = {
                new Light("Гостиная"),
                new Thermostat("Термостат"),
                new Alarm("Охрана")
        };

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите событие (Night, Cold, Intrusion), или 'exit' для выхода:");

        while (true) {
            System.out.print("> ");
            String event = scanner.nextLine().trim();

            if (event.equalsIgnoreCase("exit")) {
                System.out.println("Выход из системы умного дома.");
                break;
            }

            System.out.println("\nСобытие: " + event);
            for (Device d : devices) {
                d.respondToEvent(event);
            }
            System.out.println();
        }

        scanner.close();
    }
}
