package opencvproject;
import org.opencv.core.*;

import java.awt.EventQueue;


public class FilterApp {

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CameraFrame frame = new CameraFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

//    static void printImage(Mat image) {
//        System.out.println("Imported image matrix: ");
//
//        for (int i = 0; i < image.rows(); i++) {
//            for (int j = 0; j < image.cols(); j++) {
//                //values.add((int)image.get(i,j)[0]);
//                System.out.print((int)image.get(i,j)[0] + ", ");
//            }
//            System.out.println();
//        }
//    }



}
