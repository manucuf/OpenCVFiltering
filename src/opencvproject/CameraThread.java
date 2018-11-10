package opencvproject;

import javax.swing.*;

public class CameraThread extends Thread {

    JFrame frame;

    public CameraThread(JFrame frame) {
        this.frame = frame;
    }

    public void run() {

        for (;;){
            frame.repaint();
            try { Thread.sleep(30);
            } catch (InterruptedException e) {    }
        }

    }
}
