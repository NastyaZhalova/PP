package org.example;

public class Matrix {
    public static String Evencolumns(int[][] matrix, int cols, int rows) {
        int paritycheck = 0;
        boolean chek = false;
        String answer = "";
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (matrix[i][j] % 2 != 0) {
                    paritycheck = 1;
                }
            }
            if (paritycheck == 0) {
                answer += (j + 1) + " ";
                chek = true;
            }
            paritycheck = 0;
        }
        if (!chek) {
            answer = "Таких нет";
        }
        return answer;
    }

    public static String Checknegativeelements(int[][] matrix, int cols, int rows){
        int negativetest = 0;
        int sum = 0;
        boolean chek = false;
        String answer = "";
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                sum += matrix[i][j];
                if (matrix[i][j] < 0 && i == j) {
                    negativetest = 1;
                }
            }
            if (negativetest == 1) {
                answer += (j + 1) + ", " + sum + "\n";
                chek = true;
            }
            sum = 0;
            negativetest = 0;
        }
        if (!chek){
            answer = "Таких нет";
        }
        return answer;
    }
}
