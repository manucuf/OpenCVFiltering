package examples;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Ex11_faceRecognition {
	public static void main(String[] args){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		int i=1;

		Mat mat=null;
		
		//Mat mat = Imgcodecs.imread("D:\\OpenCV\\images\\lena_gray.jpg");
		switch (i) {
			case 1 :
				mat = Imgcodecs.imread("D:\\OpenCV\\images\\lena_color.jpg");
				break;
			case 2 :
				mat = Imgcodecs.imread("D:\\OpenCV\\images\\faces.jpg");
				break;
			case 3 :
				mat = Imgcodecs.imread("D:\\OpenCV\\images\\faces2.jpg");
				break;
			case 4 :
				mat = Imgcodecs.imread("D:\\OpenCV\\images\\lena_gray.jpg",Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
				break;
			default :
				mat = Imgcodecs.imread("D:\\OpenCV\\images\\lena_color.jpg");
				break;
		}
		
		CascadeClassifier faceDetector = new CascadeClassifier();
		//boolean res = faceDetector.load(Ex11_faceRecognition.class.getResource("res/haarcascades/haarcascade_frontalface_alt.xml").getPath());
		//boolean res = faceDetector.load(Ex11_faceRecognition.class.getResource("res/lbpcascades/lbpcascade_frontalface.xml").getPath());
		boolean res = faceDetector.load("D:\\Cloud\\Dropbox\\WS\\J\\OpenCVexamples(3.2.0)\\src\\lbpcascade_frontalface.xml");
		
        MatOfRect faceDetections = new MatOfRect();
        Mat graymat=new Mat();
        if ( mat.channels()==1 ){
    		mat.copyTo(graymat);
        }else{
    		graymat = mat.clone();
    		Imgproc.cvtColor(mat, graymat, Imgproc.COLOR_BGR2GRAY);
    		Imgproc.equalizeHist(graymat, graymat);
        }
        faceDetector.detectMultiScale(graymat, faceDetections);
 
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        //for (Rect rect : faceDetections.toArray()) {
        //    Core.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
        //            new Scalar(0, 255, 0));
        //}

		imshow("Recognised?", mat);
	}
	
	public static void imshow(String windowname, Mat m){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( m.channels() > 1 ) 
			type = BufferedImage.TYPE_3BYTE_BGR;
		int bufferSize = m.channels()*m.cols()*m.rows();
		byte [] b = new byte[bufferSize];
		m.get(0,0,b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		ImageIcon icon=new ImageIcon(image);
		JFrame frame=new JFrame(windowname);
		JLabel lbl=new JLabel(icon);
		frame.add(lbl);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
