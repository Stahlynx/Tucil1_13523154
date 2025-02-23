package com.logic;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class SaveFile {
    public static void savePuzzleAsTxt(char[][] board) {
        if (board == null || board.length == 0) {
            System.out.println("Error: The board is empty or null.");
            return;
        }

        try {
            File directory = new File(System.getProperty("user.dir") + "/test");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "Solution.txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (int row = 0; row < board.length; row++) {
                    writer.write(board[row]);
                    writer.newLine();
                }
                System.out.println("Puzzle saved as Solution.txt.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePuzzleAsPng(GridPane boardGrid) {
        if (boardGrid == null) {
            System.out.println("Error: The boardGrid is null.");
            return;
        }

        try {
            File directory = new File(System.getProperty("user.dir") + "/test");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "Solution.png");

            Image image = boardGrid.snapshot(null, null);

            BufferedImage bufferedImage = javafxToBufferedImage(image);

            ImageIO.write(bufferedImage, "png", file);
            System.out.println("Puzzle saved as Solution.png.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage javafxToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        PixelReader pixelReader = image.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                bufferedImage.setRGB(x, y, color.hashCode());
            }
        }

        return bufferedImage;
    }
}
