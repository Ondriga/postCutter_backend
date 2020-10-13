import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.GridLayout;

public class Main {
    private static JFrame frame = new JFrame("EDGE DETECTOR");
    private static JButton buttonPrevious = new JButton("PREV");
    private static JButton buttonNext = new JButton("NEXT");
    private static JLabel labelOrigin = new JLabel();
    private static JLabel labelChange = new JLabel();
    private static JPanel panelButtons = new JPanel();
    private static JPanel panelImages = new JPanel();

    private static int fileIndex = 0;
    private static String[] pathNames;

    public static void main(String[] args){
        File f = new File("screenshots");
        pathNames = f.list();
        if(pathNames.length == 0){
            System.err.println("NO FILES");
            System.exit(1);
        }

        setUpGui();
    }

    private static void setUpGui(){
        frame.setVisible(true);
        frame.setSize(500, 500);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buttonNext.addActionListener(e -> changeImage(1));
        buttonPrevious.addActionListener(e -> changeImage(-1));
        labelOrigin.setHorizontalAlignment(SwingConstants.CENTER);
        labelChange.setHorizontalAlignment(SwingConstants.CENTER);

        panelButtons.add(buttonPrevious);
        panelButtons.add(buttonNext);
        frame.add(panelButtons, BorderLayout.PAGE_END);

        panelImages.setLayout(new GridLayout(1, 2));
        panelImages.add(labelOrigin);
        panelImages.add(labelChange);
        frame.add(panelImages, BorderLayout.CENTER);
    }

    private static void changeImage(int moveBy){
        fileIndex += moveBy;
        if(fileIndex < 0){
            fileIndex = pathNames.length - 1;
        }else if(fileIndex > pathNames.length - 1){
            fileIndex = 0;
        }
        String path = "screenshots/" + pathNames[fileIndex];
        labelOrigin.setIcon(getResizedIcon(path, labelOrigin.getSize()));
        labelChange.setIcon(getResizedIcon(path, labelChange.getSize()));
    }

    private static ImageIcon getResizedIcon(String imagePath, Dimension labelDimension){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imagePath));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dimension newDimension = getDimension(labelDimension, new Dimension(img.getWidth(), img.getHeight())); 
        Image dimg = img.getScaledInstance((int) newDimension.getWidth(), (int) newDimension.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    private static Dimension getDimension(Dimension labelDimension, Dimension imageDimension){
        double newWidth = 0;
        double newHeight = 0;
        double ration;
        if(imageDimension.getWidth() > imageDimension.getHeight()){
            newWidth = labelDimension.getWidth();
            ration = imageDimension.getWidth() / labelDimension.getWidth();
            newHeight = imageDimension.getHeight() / ration;
        }else{
            newHeight = labelDimension.getHeight();
            ration = imageDimension.getHeight() / labelDimension.getHeight();
            newWidth = imageDimension.getWidth() / ration;
        }
        Dimension dimension = new Dimension();
        dimension.setSize(newWidth, newHeight);
        return dimension;
    }
}
