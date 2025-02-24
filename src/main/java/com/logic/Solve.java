package com.logic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.control.MainController;

import javafx.application.Platform;

public class Solve {
    public static int moveCount = 0;
    public static boolean isSolved = false; 

    public static boolean Solve(char[][] board, List<char[][]> pieces, int index, MainController controller) {
        if (isComplete(board)) return true;
        else{
            char[][] piece = pieces.get(index);
            char pieceLabel = (char) ('A' + index);
        
            List<char[][]> orientations = getAllOrientations(piece);
        
            for (char[][] orientation : orientations) { 
                for (int row = 0; row < board.length; row++) {
                    for (int col = 0; col < board[0].length; col++) {
                        if (canPlacePiece(board, orientation, row, col)) {
                            placePiece(board, orientation, row, col, pieceLabel);
                            moveCount++;
        
                            Platform.runLater(() -> controller.updateMoveCount(moveCount, 0));
        
                            // clearScreen();
                            // MatrixUtils.printMatrix(board);
        
                            Platform.runLater(() -> controller.updateBoard(board));
        
                            if (Solve(board, pieces, index + 1, controller)) return true;
        
                            removePiece(board, orientation, row, col);
                            // clearScreen();
                            // MatrixUtils.printMatrix(board);
                            Platform.runLater(() -> controller.updateBoard(board));
                        }
                    }
                }
            }
            return false;
        }
    }

    public static void moveMatrix(List<int[][]> L1, List<int[][]> L2) {
        if (!L1.isEmpty()) {
            int[][] matrix = L1.remove(0); 
            L2.add(matrix);
        }
    }
  
    public static List<char[][]> getAllOrientations(char[][] piece) {
        List<char[][]> orientations = new ArrayList<>();
    
        orientations.add(piece);
        Arrays.stream(new int[]{0, 1, 2, 3}).forEach(i -> {
            char[][] rotated = MatrixUtils.rotateMatrix(orientations.get(orientations.size() - 1));
            orientations.add(rotated);
        });
    
        char[][] flipped = MatrixUtils.mirrorHorizontal(piece);
        orientations.add(flipped);
        Arrays.stream(new int[]{0, 1, 2, 3}).forEach(i -> {
            char[][] rotated = MatrixUtils.rotateMatrix(orientations.get(orientations.size() - 1));
            orientations.add(rotated);
        });
    
        return orientations;
    }
    

    public static boolean canPlacePiece(char[][] board, char[][] piece, int startRow, int startCol) {
        if (startRow + piece.length > board.length || startCol + piece[0].length > board[0].length) {
            return false; 
        }
    
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != '.' && board[startRow + i][startCol + j] != '.') {
                    return false; 
                }
            }
        }
    
        return true;
    }
    

  public static void removePiece(char[][] board, char[][] piece, int startRow, int startCol) {
    for (int i = 0; i < piece.length; i++) {
        for (int j = 0; j < piece[i].length; j++) {
            if (piece[i][j] != '.') {
                board[startRow + i][startCol + j] = '.';
            }
        }
    }
  }

  

  public static void placePiece(char[][] board, char[][] piece, int startRow, int startCol, char pieceLabel) {
    for (int i = 0; i < piece.length; i++) {
        for (int j = 0; j < piece[i].length; j++) {
            if (piece[i][j] != '.') {
                board[startRow + i][startCol + j] = pieceLabel;
            }
        }
    }
  }

  public static void printBlockList(List<char[][]> blockList) {
    System.out.println("List of Puzzle Pieces:");
    for (int index = 0; index < blockList.size(); index++) {
        System.out.println("Piece " + (char) ('A' + index) + ":");
        MatrixUtils.printMatrix(blockList.get(index));
        System.out.println();
    }
  }

  public static char[][] createEmptyBoard(int N, int M) {
        char[][] board = new char[N][M];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        return board;
    }
  public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
  }

    public static boolean isComplete(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '.') {
                    return false;                
                }
            }        
        }
        return true;
    }
}
