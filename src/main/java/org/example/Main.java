package org.example;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static double Log (int k, double x){
        double e;
        e = Math.pow(10, (-k));
        double temp;
        temp = x;
        double sum = -x;
        double n = 2;
        //double check = -x;
        while (Math.abs(temp / n) >= e){
            temp = temp * x;
            //check = temp / n;
            sum = sum - temp / n;
            n += 1;
        }
        return sum;
    }
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Введите k и x");
        Scanner s = new Scanner (System.in);
        int k = s.nextInt();
        double x = s.nextDouble();
        double sum = Log(k, x);
        double res;
        res = Math.log(1 - x);
        if (Math.abs(sum) < 0.0005) {
            System.out.printf("Результат суммы: %.3f%n", 0.0);
        } else {
            System.out.printf("Результат суммы: %.3f%n", sum);
        }
        System.out.printf("Результат log: %.3f%n", res);
    }
}