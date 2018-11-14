package opencvproject;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Graphics;


public class CameraFrame extends JFrame {

    private JPanel contentPane;

    private VideoController video = new VideoController();


    public CameraFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);
        setTitle("Filter App");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new FlowLayout());

        new CameraThread(this).start();

        setContentPane(contentPane);

        CommandFrame commandFrame = new CommandFrame();
        commandFrame.delegate = video;

        commandFrame.setLocation(getX(), getY() + getHeight() + 24);

    }


    public void paint(Graphics g){
        g = contentPane.getGraphics();
        g.drawImage(video.getOneFrame(), 0, 0, this);
    }

//    public void paintComponents(Graphics g) {
//        super.paintComponents(g);
//        g = contentPane.getGraphics();
//        g.drawImage(video.getOneFrame(), 0, 0, this);
//    }



}
