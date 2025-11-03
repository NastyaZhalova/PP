package org.example;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Hotel> hotels = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("hotel.txt"))) {
            String city;
            while ((city = reader.readLine()) != null) {
                String hotelLine = reader.readLine();
                if (hotelLine == null) {
                    System.out.println("Ошибка: отсутствует строка с отелем после города " + city);
                    break;
                }
                String[] parts = hotelLine.trim().split(" ");
                if (parts.length != 2) {
                    System.out.println("Ошибка: строка отеля должна содержать название и количество звёзд: " + hotelLine);
                    continue;
                }
                String name = parts[0];
                int stars;
                try {
                    stars = Integer.parseInt(parts[1]);
                    if (stars < 2 || stars > 4) {
                        System.out.println("Ошибка: количество звёзд должно быть 2, 3 или 4: " + hotelLine);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: неверный формат звёзд: " + parts[1]);
                    continue;
                }
                hotels.add(new Hotel(city.trim(), name, stars));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл hotel.txt не найден.");
            return;
        }


        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Отели по городам:");
        Map<String, List<Hotel>> byCity = new TreeMap<>();
        for (Hotel h : hotels) {
            if (!byCity.containsKey(h.getCity())) {
                byCity.put(h.getCity(), new ArrayList<>());
            }
            byCity.get(h.getCity()).add(h);
        }
        for (String city : byCity.keySet()) {
            System.out.println(city + ":");
            List<Hotel> list = byCity.get(city);
            list.sort(Comparator.comparingInt(Hotel::getStars).reversed());
            for (Hotel h : list) {
                System.out.println(h);
            }
        }

        System.out.print("\n2. Введите название города: ");
        String cityQuery = scanner.nextLine().trim();
        boolean foundCity = false;
        for (Hotel h : hotels) {
            if (h.getCity().equalsIgnoreCase(cityQuery)) {
                System.out.println(h);
                foundCity = true;
            }
        }
        if (!foundCity) System.out.println("Нет отелей в этом городе.");

        System.out.print("\n3. Введите название отеля: ");
        String hotelQuery = scanner.nextLine().trim();
        Set<String> cities = new TreeSet<>();
        for (Hotel h : hotels) {
            if (h.getName().equalsIgnoreCase(hotelQuery)) {
                cities.add(h.getCity());
            }
        }
        if (cities.isEmpty()) {
            System.out.println("Таких отелей нет.");
        } else {
            System.out.println("Города с отелем " + hotelQuery + ":");
            for (String c : cities) {
                System.out.println(c);
            }
        }
    }
}
