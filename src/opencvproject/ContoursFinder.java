package opencvproject;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContoursFinder {


    private Mat srcGray = new Mat();

    private static final int MAX_THRESHOLD = 255;
    private int threshold = 100;
    private Random rng = new Random(12345);


    public Mat findContours(Mat src, boolean colored) {

        Imgproc.cvtColor(src, srcGray, Imgproc.COLOR_BGR2GRAY); // Converte da RGB a GREY
        Imgproc.blur(srcGray, srcGray, new Size(3, 3)); // suppongo filtro gaussiano 3x3

        Mat cannyOutput = new Mat();
        Imgproc.Canny(srcGray, cannyOutput, threshold, threshold * 2); // Scontorna con algoritmo Canny
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat(); // Matrice gerarchica dei contorni
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE); // Crea una lista di punti di contorno e la salva in contours
        Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3); // Crea una matrice delle stesse dimensioni di cannyOutput

        if (!colored) return cannyOutput;

        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256)); // Crea un colore casuale
            Imgproc.drawContours(drawing, contours, i, color, 2, Core.LINE_8, hierarchy, 0, new Point()); // Assegna un colore a ogni linea di contorno
        }

        return drawing;
    }

    public Mat findContoursNegative(Mat src, boolean colored) {

        Mat result = new Mat();
        Mat contours = findContours(src, colored);
        Core.bitwise_not(contours, result);

        return result;

    }


}
