package com.DigitRecognition;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI extends JPanel {
    private Image drawingImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
    private Graphics2D g2 = (Graphics2D) drawingImage.getGraphics();
    private Color backgroundColor = Color.WHITE;
    private Color penColor = Color.BLACK;

    {
        g2.setColor(backgroundColor); // Set background to white
        g2.fillRect(0, 0, 400, 400); // Set drawing size
        g2.setColor(penColor); // Set "pen" color to black
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawingImage, 0, 0, null);
    }

    // Method to draw on the canvas
    public void drawPoint(int x, int y) {
        g2.fillOval(x, y, 12, 12);
        repaint();
    }

    // Method to clear the canvas
    public void resetCanvas() {
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, 400, 400);
        g2.setColor(penColor);
        repaint();
    }

    // Method to take user's drawn image in the canvas and transform it for use into the MNIST model
    public BufferedImage getDrawingImage() {
        // Create a new BufferedImage with 28x28 size and gray scale
        BufferedImage resizedImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = resizedImage.createGraphics();

        // Draw the current drawing onto the resized image
        g2d.drawImage(drawingImage, 0, 0, 28, 28, null);
        
        // Create a centered 28x28 output image
        BufferedImage centeredImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2dCentered = centeredImage.createGraphics();
        g2dCentered.setColor(Color.WHITE);
        g2dCentered.fillRect(0, 0, 28, 28); // Fill background with white color
        int offsetX = (28 - 20) / 2;
        int offsetY = (28 - 20) / 2;
        g2dCentered.drawImage(resizedImage, offsetX, offsetY, 20, 20, null); // Center the resized image within a 20x20 box
        g2dCentered.dispose();

        return centeredImage;
    }
}