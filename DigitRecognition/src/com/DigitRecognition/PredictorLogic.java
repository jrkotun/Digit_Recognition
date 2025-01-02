package com.DigitRecognition;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class PredictorLogic {
    private MultiLayerNetwork model;
    private static Logger log = LoggerFactory.getLogger(PredictorLogic.class);

    // Method to build the model graph
    public void loadModel() {
        try {
            int batchSize = 128; // Batch size for each epoch
            int rngSeed = 123; // Random seed for reproducibility
            int numEpochs = 15; // Number of epochs to perform
            int numRows = 28; // Number of rows (28 pixels)
            int numColumns = 28; // Number of columns (28 pixels)
            int outputNum = 10; // Number of output classes
            
            // Create training and validation datasets
            DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, true, rngSeed);
            DataSetIterator mnistValidation = new MnistDataSetIterator(batchSize, false, rngSeed);

            log.info("Building model...");
            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                    .seed(rngSeed)
                    .updater(new Nesterovs(0.005, 0.9))
                    .l2(1e-4)
                    .list()
                    .layer(0, new DenseLayer.Builder()
                            .nIn(numRows * numColumns)
                            .nOut(256)
                            .activation(Activation.RELU)
                            .weightInit(WeightInit.XAVIER)
                            .build())
                    .layer(1, new DenseLayer.Builder()
                            .nIn(256)
                            .nOut(128)
                            .activation(Activation.RELU)
                            .weightInit(WeightInit.XAVIER)
                            .dropOut(0.7)
                            .build())
                    .layer(2, new OutputLayer.Builder(LossFunction.NEGATIVELOGLIKELIHOOD)
                            .nIn(128)
                            .nOut(outputNum)
                            .activation(Activation.SOFTMAX)
                            .weightInit(WeightInit.XAVIER)
                            .build())
                    .build();

            model = new MultiLayerNetwork(conf);
            model.init();

            log.info("Training model...");
            for (int i = 0; i < numEpochs; i++) {
                model.fit(mnistTrain);
                log.info("Epoch {} completed.", i + 1);
                
                // Compute validation loss
                double validationLoss = 0.0;
                int count = 0;
                while (mnistValidation.hasNext()) {
                    DataSet batch = mnistValidation.next();
                    validationLoss += model.score(batch);
                    count++;
                }
                validationLoss /= count; // Average loss
                mnistValidation.reset();

                log.info("Epoch {}: Validation Loss = {}", i + 1, validationLoss);
            }
            

        } catch (Exception e) {
            log.error("An error occurred while loading the model", e);
        }
    }

    // Method to classify the digit from a BufferedImage
    public int classify(BufferedImage inputImage) throws Exception {
        // Resize to 28x28 and convert to grayscale
        BufferedImage resizedImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(inputImage, 0, 0, 28, 28, null);
        g.dispose();

        // Extract pixel values and normalize
        WritableRaster raster = resizedImage.getRaster();
        float[] inputData = new float[28 * 28];
        for (int i = 0; i < 28; i++) {
            for (int j = 0; j < 28; j++) {
                inputData[i * 28 + j] = raster.getSample(j, i, 0) / 255.0f;
            }
        }

        // Create input array and predict
        INDArray input = Nd4j.create(inputData).reshape(1, 28 * 28);
        INDArray output = model.output(input);

        // Find predicted class
        return Nd4j.argMax(output, 1).getInt(0);
    }
}
