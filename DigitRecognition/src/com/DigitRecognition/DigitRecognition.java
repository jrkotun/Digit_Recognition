package com.DigitRecognition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class DigitRecognition {

    public static void main(String[] args) {
        // Create frame with title 'Digit Recognition Program'
        JFrame frame = new JFrame("Digit Recognition Program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 400));

        // Create new UI object
        UI canvas = new UI();
        canvas.setPreferredSize(new Dimension(400, 400));

        // Create action for when mouse is clicked to insert a point in that area of sketch pad
        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                canvas.drawPoint(e.getX(), e.getY());
            }
        });

        // Create action for when mouse is clicked and dragged to create points where ever the mouse goes
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                canvas.drawPoint(e.getX(), e.getY());
            }
        });

        // Create clear and submit buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearButton = new JButton("Clear");
        JButton submitButton = new JButton("Submit");

        // Add clear and submit buttons to buttonPanel
        buttonPanel.add(clearButton);
        buttonPanel.add(submitButton);

        // Create predictor object from Predictor Logic class
        PredictorLogic predictor = new PredictorLogic();
        try {
            predictor.loadModel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load the model.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Create functionality for submit button (uses getDrawingImage method from UI class to save user's drawn image)
        submitButton.addActionListener(e -> {
            BufferedImage drawnImage = canvas.getDrawingImage(); // Get the drawing from the canvas
            try {
                int predictedDigit = predictor.classify(drawnImage); // Classify the digit
                JOptionPane.showMessageDialog(frame, "Predicted Digit: " + predictedDigit, "Prediction Result", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e2) {
                e2.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to classify the digit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Create functionality for clear button (uses resetCanvas method from UI to reset Canvas to being blank)
        clearButton.addActionListener(e -> canvas.resetCanvas());

        // Add drawing canvas and button panel to overall JFrame
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}