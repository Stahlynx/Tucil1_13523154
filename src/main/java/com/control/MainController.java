package com.control;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.logic.PuzzleData;
import com.logic.ReadFile;
import com.logic.SaveFile;
import com.logic.Solve;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private TextField fileInput;
    @FXML
    private Button solveButton;
    @FXML
    private Label resultLabel;
    @FXML
    private GridPane boardGrid; 
    @FXML
    private Label moveLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Button saveButton;
    @FXML
    private TextField nameInput;
    
    private char[][] board;
    private char[][] solutionBoard;
    private static final int BLOCK_SIZE = 40; 
    private final Map<Character, Color> pieceColors = new HashMap<>();
    private final Random random = new Random();

    @FXML
    private void initialize() {
        drawEmptyBoard();
    }
    @FXML
    private void savePuzzle() {
        SaveFile.savePuzzleAsTxt(solutionBoard);  
        SaveFile.savePuzzleAsPng(boardGrid); 
    }

    public void updateMoveCount(int moves, long timeElapsed) {
        Platform.runLater(() -> {
            moveLabel.setText("Moves: " + moves);
            timeLabel.setText("Time: " + timeElapsed + " ms");
        });
    }

    private void drawEmptyBoard() {
        boardGrid.getChildren().clear();
        for (int row = 0; row < 5; row++) { 
            for (int col = 0; col < 5; col++) {
                drawCell(row, col, '.');
            }
        }
    }

    private void drawCell(int row, int col, char value) {
        StackPane cell = new StackPane();
        Rectangle block = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        block.setStroke(Color.BLACK);

        if (value == '.') {
            block.setFill(Color.LIGHTGRAY);
        } else {
            block.setFill(Color.LIGHTBLUE);
        }

        Label text = new Label(String.valueOf(value));
        cell.getChildren().addAll(block, text);
        boardGrid.add(cell, col, row);
    }

    private void setupSaveButton() {
        saveButton.setDisable(true); 
        saveButton.setOnAction(e -> savePuzzle());
    }

    public void updateBoard(char[][] board) {
        Platform.runLater(() -> {
            boardGrid.getChildren().clear();
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[0].length; col++) {
                    StackPane cell = new StackPane();
                    Rectangle block = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
    
                    char piece = board[row][col];
    
                    if (piece == '.') {
                        block.setFill(Color.LIGHTGRAY); 
                    } else {
                        pieceColors.putIfAbsent(piece, getRandomColor());
                        block.setFill(pieceColors.get(piece)); 
                    }
    
                    block.setStroke(Color.BLACK);
                    cell.getChildren().add(block);
                    boardGrid.add(cell, col, row);
                }
            }
        });
    }
    
    private Color getRandomColor() {
        return Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }


    @FXML
    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Puzzle File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            fileInput.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void solvePuzzle() {
        String filename = fileInput.getText().trim();
        if (filename.isEmpty()) {
            resultLabel.setText("Please select a file.");
            return;
        }

        PuzzleData data = ReadFile.parseFile(filename);
        int N = data.getN();
        int M = data.getM();
        List<char[][]> blockList = data.getBlockList();

        int sum = 0;

        for (char[][] piece : blockList) {
            sum += PuzzleData.countCharsInPiece(piece);
        }

        if (sum != N * M) {
            resultLabel.setText("No Solution Possible.");
        } else {
            char[][] board = Solve.createEmptyBoard(N, M);
            Solve.moveCount = 0;

            final long startTime = System.currentTimeMillis();

            new Thread(() -> {
                boolean solved = Solve.Solve(board, blockList, 0, this);
                long elapsedTime = System.currentTimeMillis() - startTime; 

                Platform.runLater(() -> {
                    if (solved) {
                        resultLabel.setText("Solution Found!");
                        solutionBoard = board;
                        updateBoard(board);
                        saveButton.setDisable(false);
                    } else {
                        resultLabel.setText("No Solution Possible.");
                    }

                    updateMoveCount(Solve.moveCount, elapsedTime);
                });
            }).start();
        }
    }
}
