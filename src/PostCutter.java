/*
 * Source code for the backend of Bachelor thesis.
 * PostCutter class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import edgeDetection.*;
import line.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.GridLayout;

/**
 * Main application class
 * Create gui and show everything to the user
 */
public class PostCutter extends JFrame{
    /// Component for display origin picture
    private JLabel labelOrigin = new JLabel();
    /// Component for display changed picture
    private JLabel labelChange = new JLabel();
    /// Component for view lines or rectangles in pictures
    private JLabel labelLines = new JLabel();
    /// Panel contain buttons "next" and "previous"
    private JPanel panelButtonsPhoto = new JPanel();
    /// Panel contain labels "labelOrigin" and "labelChange"
    private JPanel panelImages = new JPanel();
    /// Label for actual method name
    private JLabel methodName = new JLabel();
    /// Panel contain buttons for edge detection methods
    private JPanel panelButtonsMeth = new JPanel();

    /// Index of actual displaying picture
    private int fileIndex = 0;
    /// Array with paths to pictures
    private static String[] pathNames;

    /// ArrayList contain classes of edge detection methods
    private List<EdgeDetector> edgeMethods = new ArrayList<>();
    /// Actual using edge detection method object 
    private EdgeDetector edgeDetector;

    public static void main(String[] args){
        File f = new File("screenshots");
        pathNames = f.list();
        if(pathNames.length == 0){
            System.err.println("NO FILES");
            System.exit(1);
        }
        new PostCutter();
    }

    /**
     * Constructor
     * Load edge detection methods and create gui
     */
    public PostCutter(){
        edgeMethods.add(new Prewitt("PREWITT OPERATOR"));
        edgeMethods.add(new Sobel("SOBEL OPERATOR"));
        edgeMethods.add(new Laplace("LAPLACE OPERATOR"));
        edgeMethods.add(new Canny("CANNY"));
        edgeDetector = edgeMethods.get(0);

        this.setTitle("EDGE DETECTOR");
        this.setVisible(true);
        this.setSize(500, 500);
        this.setExtendedState(Frame.MAXIMIZED_BOTH); 
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setUpGuiComponents();

        this.add(methodName, BorderLayout.PAGE_START);

        this.add(panelButtonsPhoto, BorderLayout.PAGE_END);
        this.add(panelImages, BorderLayout.CENTER);
        this.add(panelButtonsMeth, BorderLayout.LINE_END);
    }

    /**
     * Create and set up components for gui
     */
    private void setUpGuiComponents(){
        JButton buttonPrevious = new JButton("PREV");
        JButton buttonNext = new JButton("NEXT");
        buttonNext.addActionListener(e -> changeImage(1));
        buttonPrevious.addActionListener(e -> changeImage(-1));
        panelButtonsPhoto.add(buttonPrevious);
        panelButtonsPhoto.add(buttonNext);

        panelButtonsMeth.setLayout(new BoxLayout(panelButtonsMeth, BoxLayout.PAGE_AXIS));
        // Create buttons for all edge detection methods
        for(int i=0; i<edgeMethods.size(); i++){
            createMethodButton(edgeMethods.get(i).getMethodName(), i);
        }

        methodName.setText(edgeDetector.getMethodName());

        methodName.setHorizontalAlignment(SwingConstants.CENTER);
        labelOrigin.setHorizontalAlignment(SwingConstants.CENTER);
        labelChange.setHorizontalAlignment(SwingConstants.CENTER);
         
        panelImages.setLayout(new GridLayout(1, 3));
        panelImages.add(labelOrigin);
        panelImages.add(labelChange);
        panelImages.add(labelLines);
    }

    /**
     * Change actual using method by index and change picture by new actual method
     * @param methodIndex Method index which will became actual method
     */
    private void changeMethod(int methodIndex){
        edgeDetector = edgeMethods.get(methodIndex);
        methodName.setText(edgeDetector.getMethodName());
        changeImage(0);
    }

    /**
     * Create button for edge detection method and add this button to "panelButtonsMeth"
     * @param methodName Name of edge detection method
     * @param methodIndex Index under which is method object store in ArrayList "edgeMethods" 
     */
    private void createMethodButton(String methodName, int methodIndex){
        JButton button = new JButton(methodName);
        button.addActionListener(e -> changeMethod(methodIndex));
        panelButtonsMeth.add(button);
    }

    /**
     * Load and display new picture. This method pretend like the pictures are stored in cyclic buffer.
     * @param moveBy Integer by how much to move in cyclic buffer of pictures
     */
    private void changeImage(int moveBy){
        fileIndex += moveBy;
        if(fileIndex < 0){
            fileIndex = pathNames.length - 1;
        }else if(fileIndex > pathNames.length - 1){
            fileIndex = 0;
        }
        String path = "screenshots/" + pathNames[fileIndex];
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
            labelOrigin.setIcon(getResizedIcon(img, labelOrigin.getSize()));
            Mat pictureChange = edgeDetector.highlightEdge(path);
            labelChange.setIcon(getResizedIcon(mat2BufferedImage(pictureChange), labelChange.getSize()));

            labelLines.setIcon(getResizedIcon(highlightLines(pictureChange), labelLines.getSize()));

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    private BufferedImage highlightLines(Mat picture){
        Mat linesMat = new Mat(picture.rows(), picture.cols(), CvType.CV_8U);
        
        for (int i=0; i<linesMat.rows(); i++)
        {
            for (int j=0; j<linesMat.cols(); j++)
            {
                linesMat.put(i, j, 255); //Puts element back into matrix
            }
        }
        
        LineHandler lineHandler = new LineHandler();
        lineHandler.findLines(picture);
        for(MyLine line : lineHandler.getHorizontalLines()){
            if(line.length() >= 540){
                for(int i=line.getStartPoint().getX(); i<=line.getEndPoint().getX(); i++){
                    linesMat.put(line.getStartPoint().getY(), i, 0);
                }
            }
        }
        for(MyLine line : lineHandler.getVerticalLines()){
            if(line.length() >= 960){
                for(int i=line.getStartPoint().getY(); i<=line.getEndPoint().getY(); i++){
                    linesMat.put(i, line.getStartPoint().getX(), 0);
                }
            }
        }

        return mat2BufferedImage(linesMat);
    }

    /**
     * Resize picture that it fit inside the label.
     * @param img Picture to resize
     * @param labelDimension Dimension of label
     * @return
     */
    private ImageIcon getResizedIcon(BufferedImage img, Dimension labelDimension){
        Dimension newDimension = getDimension(labelDimension, new Dimension(img.getWidth(), img.getHeight())); 
        Image dimg = img.getScaledInstance((int) newDimension.getWidth(), (int) newDimension.getHeight(), Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    /**
     * Get new dimension that will fit inside the label.
     * @param labelDimension Dimension of label
     * @param imageDimension Dimension of picture
     * @return new dimension for picture
     */
    private Dimension getDimension(Dimension labelDimension, Dimension imageDimension){
        double newWidth = 0;
        double newHeight = 0;
        double ration;
        // If picture is oriented landscape.
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

    /**
     * Convert "Mat" into "BufferedImage"
     * @param mat picture stored like matrix
     * @return picture stored like BufferedImage
     */
    public static BufferedImage mat2BufferedImage(Mat mat){
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else {
            return null;
        }
        int imageDataLength = mat.channels()*mat.rows()*mat.cols();
        byte [] buffer = new byte[imageDataLength];
        mat.get(0, 0, buffer);
        BufferedImage grayImage = new BufferedImage(mat.width(), mat.height(), type);
        grayImage.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), buffer);

        return grayImage;
    }
}
