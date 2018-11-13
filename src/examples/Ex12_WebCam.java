package examples;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class Ex12_WebCam {
	public static void main(String[] args) throws InterruptedException{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
	    VideoCapture camera = new VideoCapture(0);
	    Thread.sleep(1000);

	    if(!camera.isOpened()){
	        System.out.println("Camera Error");
	    }

	    Mat frame = new Mat();
	    Mat filtered = new Mat();

	    camera.read(frame);
	    JLabel jlabel1 = imshow("USB CAMERA (original)", frame);
	    JLabel jlabel2 = imshow("USB CAMERA (filtered)", frame);
	    
	    while(true){
	    	camera.read(frame);
	    	imupdate(jlabel1, frame);

	    	//NO FILTERING
	    	//filtered = frame;

	    	//NEGATIVE
	    	//Core.bitwise_not(frame, filtered);
	    	//frame.convertTo(filtered, -1, -1, 256);
	    	
	    	//BRIGHTNESS
	    	//int briGain = 50;
	    	//frame.convertTo(filtered, -1, 1, briGain);

	    	//CONTRAST
	    	//double conGain = 0.5;
	    	//frame.convertTo(filtered, -1, conGain, (127-(127*conGain)));

	    	//CONTRAST EQUALIZATION
	    	//Imgproc.equalizeHist(frame, filtered);
	    	
	    	//FACE RECOGNITION
			//CascadeClassifier faceDetector = new CascadeClassifier(Ex12_WebCam.class.getResource("res/haarcascades/haarcascade_frontalface_alt.xml").getPath());
			CascadeClassifier faceDetector = new CascadeClassifier(Ex12_WebCam.class.getResource("res/lbpcascades/lbpcascade_frontalface.xml").getPath());
			
	        if ( frame.channels()==1 ){
	        	frame.copyTo(filtered);
	        }else{
	        	filtered = frame.clone();
	    		Imgproc.cvtColor(frame, filtered, Imgproc.COLOR_BGR2GRAY);
	    		Imgproc.equalizeHist(filtered, filtered);
	        }
	        MatOfRect faceDetections = new MatOfRect();
	        faceDetector.detectMultiScale(filtered, faceDetections);
	        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	        
	    	imupdate(jlabel2, filtered);
	    }
	}
    
	public static void imupdate(JLabel jlabel, Mat m){
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
		jlabel.setIcon(icon);
	}
	
	public static JLabel imshow(String windowname, Mat m){
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
		JFrame jframe=new JFrame(windowname);
		JLabel lbl=new JLabel(icon);
		jframe.add(lbl);
		jframe.pack();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		
		return lbl;
	}	
}

