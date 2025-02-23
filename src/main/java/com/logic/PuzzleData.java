package com.logic;
import java.util.List;

public class PuzzleData {
    int N, M, P;
    List<char[][]> blockList;

    public PuzzleData(int N, int M, int P, List<char[][]> blockList) {
        this.N = N;
        this.M = M;
        this.P = P;
        this.blockList = blockList;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public List<char[][]> getBlockList() {
        return blockList;
    }

    public static int countCharsInPiece(char[][] piece) {
        int count = 0;
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != '.' && piece[i][j] != ' ') { 
                    count++;
                }
            }
        }
        return count;
    }
}