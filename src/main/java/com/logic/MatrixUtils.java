package com.logic;
import java.util.List;

public class MatrixUtils {
    public static char[][] rotateMatrix(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] rotated = new char[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }
    
    public static char[][] mirrorHorizontal(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        char[][] mirrored = new char[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mirrored[i][cols - 1 - j] = matrix[i][j];
            }
        }
        return mirrored;
    }

    public static void printMatrix(char[][] matrix) {
        for (char[] row : matrix) {
            System.out.println(new String(row));
        }
    }

    public static void printList(List<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
