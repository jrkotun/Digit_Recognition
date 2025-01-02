# Digit Recognition

This project implements a digit recognition system with an interactive GUI, allowing users to draw digits using a mousepad. The drawn input is processed and fed into a neural network, trained on the MNIST dataset, to predict the handwritten digit.

## Project Description

The project is broken up into 3 different Java files: PredictorLogic, UI, and DigitRecognition. 

PredictorLogic.java: This file contains the core logic for building, training, and using the neural network for digit recognition. It trains a multi-layer perceptron model on the MNIST dataset, saves the trained model, and provides functionality to classify user-drawn digits from an input image. It includes methods to preprocess input images, normalize pixel values, and predict the digit with the trained model.

UI.java: This file implements the graphical user interface (GUI) for the digit recognition system. It provides a drawing canvas where users can sketch digits with a mouse. The canvas includes functionality to draw points, reset the drawing area, and preprocess the user's sketch by resizing it to 28x28 pixels for compatibility with the MNIST-trained neural network.

DigitRecognition.java: This is the main file that initializes and orchestrates the digit recognition program. It creates a user-friendly graphical interface with a drawing canvas, clear and submit buttons, and mouse event handling for drawing digits. The submit button processes the user's sketch, classifies the digit using the trained neural network, and displays the prediction. The clear button resets the drawing canvas for a new input from the user.

## How to Install and Run the Project

Follow the steps below to set up and run the digit recognition system in Eclipse:

### Prerequisites
- **Java Development Kit (JDK)**: Version 8 or later.
- **Eclipse IDE**: Install most recent version of Eclipse.
- **Maven Plugin**: Should be included in most recent Eclipse versions.

### Installation Steps

1. **Clone the Repository**
Clone the project repository to your local machine using the following command:
```bash
git clone https://github.com/jrkotun/Digit_Recognition.git
```

2. **Import Project into Eclipse**  
   1. Open Eclipse and select your workspace.  
   2. Go to `File > Import`.  
   3. Select `Maven > Existing Maven Projects` and click `Next`.  
   4. Browse to the location of the cloned repository and click `Finish`.

3. **Verify Dependencies**  
   - Eclipse should automatically download the required Maven dependencies specified in the `pom.xml`.  
   - If dependencies are missing, right-click the project in the `Package Explorer` and select `Maven > Update Project`.

4. **Run the Application**  
   1. Open the `DigitRecognition.java` file in `DigitRecognition/src/com/DigitRecognition`.
   2. Right-click anywhere in the editor and select `Run As > Java Application`.

### Usage
- **Drawing a Digit**: Use your mouse to draw a digit on the canvas.
- **Clearing the Canvas**: Click the "Clear" button to reset the canvas.
- **Predicting the Digit**: Click the "Submit" button to classify your drawing and see the predicted digit.

## Next Steps for Project
1. **Investigate the Cause of Low Digit Recognition Accuracy**  
   - Given the low validation loss, the issue may lie in the format of the user input when fed into the model.  
   - Another potential factor could be the stratification of data within the training and validation sets, which may need optimization.

2. **Integrate User Input to Enhance Model Training**  
   - Capture user input and corresponding labels, then incorporate this data into the training set to further fine-tune and improve the model's performance.

3. **Enable Image Upload Functionality for Predictions**  
   - Implement a feature that allows users to upload images of hand-drawn digits, enabling predictions to be made and adding these images to the training set for continuous model improvement.

