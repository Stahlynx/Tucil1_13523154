package com.logic;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static long startTime;    

    // public static void main(String[] args) {
    //     Scanner scanner = new Scanner(System.in);
    //     System.out.print("Masukkan file: ");
    //     String filename = scanner.nextLine(); 
    //     filename = "../bin/" + filename;

    //     PuzzleData data = ReadFile.parseFile(filename);
    //     int N = data.N;
    //     int M = data.M;
    //     List<char[][]> blockList = data.blockList;
    //     int sum = 0;

    //     for (int i = 0; i < blockList.size(); i++) {
    //         char[][] piece = blockList.get(i);
    //         int totalChars = PuzzleData.countCharsInPiece(piece);
    //         sum += totalChars;
    //     }

    //     if (sum != N*M){
    //         System.out.println(" No Solution Possible.");
    //     } else{
    //         char[][] board = Solve.createEmptyBoard(N, M);
    //         System.out.println("Solving IQ Puzzler Pro...");
        
    //         Solve.moveCount = 0;
    //         startTime = System.currentTimeMillis();
        
    //         boolean solved = Solve.Solve(board, blockList, 0);
        
    //         long elapsedTime = System.currentTimeMillis() - startTime; 
        
    //         if (solved) {
    //             System.out.println(" Solution Found!");
    //             ColorBoard.printBoardWithColors(board);
    //         } else {
    //             System.out.println(" No Solution Possible.");
    //         }
        
    //         System.out.println("\nWaktu pencarian : " + (elapsedTime) + " ms");
    //         System.out.println("\nBanyak kasus yang ditinjau: " + Solve.moveCount);
    //         System.out.println();
            
    //         boolean end = false;
    //         while (!end) {
    //             System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak) ");
    //             String choice = scanner.nextLine(); 
    //             if (choice.equals("ya")){
    //                 System.out.println("Nama file output: ");
    //                 String savename = scanner.nextLine();
    //                 ReadFile.saveBoardToFile(board, savename +".txt");
    //                 Image.saveBoardAsImage(board, savename +".png");
    //                 end = true;
    //             } else if (choice.equals("tidak")){
    //                 end = true;
    //             } else{
    //                 System.out.println("Pilihan tidak valid\n");
    //                 System.out.println();
    //             }
    //         } 
    //         scanner.close();
    //     }
    // }
}