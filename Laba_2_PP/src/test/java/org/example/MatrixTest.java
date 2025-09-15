package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixTest {

    @Test
    public void testWithEvenColumns() {
        int[][] matrix = {
                {2, 3, 4},
                {4, 5, 6},
                {6, 7, 8}
        };
        String expected = "1 3 ";
        String actual = Matrix.Evencolumns(matrix, 3, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void testWithoutEvenColumns() {
        int[][] matrix = {
                {1, 3, 5},
                {7, 9, 11},
                {13, 15, 17}
        };
        String expected = "Таких нет";
        String actual = Matrix.Evencolumns(matrix, 3, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void testWithNegativesOnDiagonal() {
        int[][] matrix = {
                {-1, 2, 3},
                {4, -5, 6},
                {7, 8, -9}
        };
        String expected  = "1, 10\n2, 5\n3, 0\n";;
        String actual = Matrix.Checknegativeelements(matrix, 3, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void testWithoutNegativesOnDiagonal() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        String expected = "Таких нет";
        String actual = Matrix.Checknegativeelements(matrix, 3, 3);
        assertEquals(expected, actual);
    }
}
