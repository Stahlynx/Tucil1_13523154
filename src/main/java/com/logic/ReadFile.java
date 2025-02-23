package com.logic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFile {
    public static void main (String[] args) {
        PuzzleData puzzle = parseFile("test3.txt");
        for (int i = 0; i < puzzle.blockList.size(); i++) {
            MatrixUtils.printMatrix(puzzle.blockList.get(i));
            System.out.println();
        }       
    }

    public static PuzzleData parseFile(String filename) {
        int N = 0, M = 0, P = 0;
        List<char[][]> blockList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine();
            if (line != null) {
                String[] dimensions = line.split(" ");
                N = Integer.parseInt(dimensions[0]);
                M = Integer.parseInt(dimensions[1]);
                P = Integer.parseInt(dimensions[2]);
            }
            
            br.readLine(); 
            
            blockList = parseBlocks(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new PuzzleData(N, M, P, blockList);
    }

    private static List<char[][]> parseBlocks(String filename) {
        List<char[][]> puzzlePieces = new ArrayList<>();
    
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); 
            br.readLine(); 
    
            List<String> currentPiece = new ArrayList<>();
            char currentSymbol = '\0';
    
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue; 
                
                char firstChar = '\0';
                for (char c : line.toCharArray()) {
                    if (Character.isLetter(c)) { 
                        firstChar = c;
                        break;
                    }
                }
    
                if (firstChar == '\0') {
                    currentPiece.add(line);
                    continue;
                }
    
                if (currentPiece.isEmpty() || firstChar == currentSymbol) {
                    currentPiece.add(line);
                } else {
                    puzzlePieces.add(convertToMatrix(currentPiece));
                    currentPiece.clear();
                    currentPiece.add(line);
                }
    
                currentSymbol = firstChar;
            }
    
            if (!currentPiece.isEmpty()) {
                puzzlePieces.add(convertToMatrix(currentPiece));
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
        return puzzlePieces;
    }

    private static char[][] convertToMatrix(List<String> shape) {
        int rows = shape.size();
        int cols = shape.stream().mapToInt(String::length).max().orElse(0);
    
        char[][] matrix = new char[rows][cols];
    
        for (int i = 0; i < rows; i++) {
            Arrays.fill(matrix[i], '.'); 
    
            String rowStr = shape.get(i).stripTrailing(); 
            int rowLength = rowStr.length(); 
    
            for (int j = 0; j < rowLength; j++) {
                if (rowStr.charAt(j) != ' ') {
                    matrix[i][j] = rowStr.charAt(j);
                }
            }
        }
        return matrix;
    }
    
    public static void saveBoardToFile(char[][] board, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../test/" + filename))) {
            for (char[] row : board) {
                writer.write(row);
                writer.newLine();
            }
            System.out.println("Board saved to test/" + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
