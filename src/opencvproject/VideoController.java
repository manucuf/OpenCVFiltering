package opencvproject;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class VideoController implements IFilter {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private VideoCapture capture;
    private Mat image = new Mat();
    private Mat result = new Mat();

    private boolean isFilming = true;

    public String fileName = "";

    VideoController(){
        this.capture = new VideoCapture(0);
        //capture.open(0);
    }

    BufferedImage getOneFrame() {
        if (isFilming) {
            capture.read(image);
            return convertMatToImage(image);
        } else {
            return convertMatToImage(result);
        }
    }

    private BufferedImage convertMatToImage(Mat mat) {

        BufferedImage img = null;

        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int w = mat.cols();
        int h = mat.rows();
        // if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != type)
            img = new BufferedImage(w, h, type);

        WritableRaster raster = img.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return img;
    }


    @Override
    public void onSnapshot(FilterType filterType) {

        this.isFilming = false;

        switch (filterType) {
            case FindContoursBW:
                ContoursFinder contoursFinder = new ContoursFinder();
                result = contoursFinder.findContours(image, false);
                break;
            case FindContoursColor:
                ContoursFinder contoursColorFinder = new ContoursFinder();
                result = contoursColorFinder.findContours(image, true);
                break;
            case FindContoursBWNegative:
                ContoursFinder contoursFinderNegative = new ContoursFinder();
                result = contoursFinderNegative.findContoursNegative(image, false);
                break;
            case FindContoursColorNegative:
                ContoursFinder contoursFinderColorNegative = new ContoursFinder();
                result = contoursFinderColorNegative.findContoursNegative(image, true);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onSaving() {
        this.isFilming = true;
        return saveFile(result);
    }

    @Override
    public void onDiscarding() {
        this.isFilming = true;
    }

    public boolean saveFile(Mat output) {
        boolean result;

        String home = System.getProperty("user.home");

        int fileNumber = 0;
        fileName = home + "/Downloads/output" + fileNumber + ".png";
        File fileOut = new File(fileName);

        while (fileOut.exists()) {
            fileNumber++;
            fileName = home + "/Downloads/output" + fileNumber + ".png";
            fileOut = new File(fileName);
        }

        try {
            fileOut.createNewFile();
            Imgcodecs.imwrite(fileName, output); // Salvataggio su file
            System.out.println("Writing complete");
            result = true;
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }
}
