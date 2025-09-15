package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows = 0;
        int cols = 0;

        try {
            System.out.println("Введите два положительных целых числа (строки и столбцы):");
            rows = s.nextInt();
            cols = s.nextInt();

            if (rows <= 0 || cols <= 0) {
                throw new IllegalArgumentException("Размеры матрицы должны быть положительными.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Ошибка: нужно вводить целые числа.");
            return;
        }

        int[][] matrix = new int[rows][cols];
        Random rand = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(1001) - 500;
            }
        }
        System.out.println("Элементы матрицы");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf(matrix[i][j] + " ");
            }
            System.out.printf("\n");
        }
        System.out.println("Номера столбцов, все элементы которых четны:");
        System.out.println(Matrix.Evencolumns(matrix, cols, rows));
        System.out.println("Столбец с отрицательным элементом на главной диагонали");
        System.out.println(Matrix.Checknegativeelements(matrix, cols, rows));
        }
    }
