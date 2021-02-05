/*
 * Source code for the backend of Bachelor thesis.
 * PostCutter class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import postCutter.Cutter;
import postCutter.edgeDetection.*;
import postCutter.geometricShapes.line.*;
import postCutter.geometricShapes.rectangle.*;

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
import org.opencv.core.Scalar;

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

    private boolean flagFinal = false;

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
        JButton buttonFinal = new JButton("Final");
        buttonNext.addActionListener(e -> changeImage(1));
        buttonPrevious.addActionListener(e -> changeImage(-1));
        buttonFinal.addActionListener(e -> toggleFinal(buttonFinal));
        panelButtonsPhoto.add(buttonPrevious);
        panelButtonsPhoto.add(buttonNext);
        panelButtonsPhoto.add(buttonFinal);

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

    private void toggleFinal(JButton button){
        if(this.flagFinal){
            button.setText("FINAL");
        }else{
            button.setText("METHODS");
        }
        this.flagFinal = !this.flagFinal;
        changeImage(0);
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
            Mat picture = EdgeDetector.getGrayScale(path);
            if(!flagFinal){
                Mat pictureChange = edgeDetector.highlightEdge(picture);
                labelChange.setIcon(getResizedIcon(mat2BufferedImage(pictureChange), labelChange.getSize()));
                labelLines.setIcon(getResizedIcon(highlightLines(pictureChange), labelLines.getSize()));
            }else{
                Cutter cutter = new Cutter(picture);
                Mat canvas = new Mat(picture.rows(), picture.cols(), CvType.CV_8U, new Scalar(255));
                printLines(canvas, cutter.getHorizontalLines());
                printLines(canvas, cutter.getVerticalLines());
                labelChange.setIcon(getResizedIcon(mat2BufferedImage(canvas), labelChange.getSize()));
                labelLines.setIcon(getResizedIcon(mat2BufferedImage(getRectanglePrinted(picture, cutter.getRectangle())), labelLines.getSize()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    private void printLines(Mat picture, List<MyLine> lines){
        for(MyLine line : lines){
            if(line.getStartPoint().getY() == line.getEndPoint().getY()){
                for(int i=line.getStartPoint().getX(); i<=line.getEndPoint().getX(); i++){
                    picture.put(line.getStartPoint().getY(), i, 0);
                }
            }else{
                for(int i=line.getStartPoint().getY(); i<=line.getEndPoint().getY(); i++){
                    picture.put(i, line.getStartPoint().getX(), 0);
                }
            }
        }
    }

    private Mat getRectanglePrinted(Mat picture, MyRectangle rectangle){
        Mat canvas = new Mat(picture.rows(), picture.cols(), CvType.CV_8U, new Scalar(255));
        if(rectangle != null){
            for(int i = rectangle.getCornerA().getX(); i <= rectangle.getCornerB().getX(); i++){
                canvas.put(rectangle.getCornerA().getY(), i, 0);
                canvas.put(rectangle.getCornerB().getY(), i, 0);
            }
            for(int i = rectangle.getCornerA().getY(); i <= rectangle.getCornerB().getY(); i++){
                canvas.put(i, rectangle.getCornerA().getX(), 0);
                canvas.put(i, rectangle.getCornerB().getX()-1, 0);
            }
        }
        return canvas;
    }

    private BufferedImage highlightLines(Mat picture){
        Mat linesMat = new Mat(picture.rows(), picture.cols(), CvType.CV_8U, new Scalar(255));
        
        LineHandler lineHandler = new LineHandler();
        lineHandler.findLines(picture);
        lineHandler.deleteNoise(picture.cols(), picture.rows());
        for(MyLine line : lineHandler.getHorizontalLines()){
            for(int i=line.getStartPoint().getX(); i<=line.getEndPoint().getX(); i++){
                linesMat.put(line.getStartPoint().getY(), i, 80);
            }
        }
        for(MyLine line : lineHandler.getVerticalLines()){
            for(int i=line.getStartPoint().getY(); i<=line.getEndPoint().getY(); i++){
                linesMat.put(i, line.getStartPoint().getX(), 80);
            }
        }

        RectangleHandler rectangleHandler = new RectangleHandler();
        rectangleHandler.findRectangle(lineHandler.getHorizontalLines(), picture.cols()-1, picture.rows());
        MyRectangle rectangle = rectangleHandler.getRectangle();

        if(rectangle != null){
            for(int i = rectangle.getCornerA().getX(); i <= rectangle.getCornerB().getX(); i++){
                linesMat.put(rectangle.getCornerA().getY(), i, 0);
                linesMat.put(rectangle.getCornerB().getY(), i, 0);
            }
            for(int i = rectangle.getCornerA().getY(); i <= rectangle.getCornerB().getY(); i++){
                linesMat.put(i, rectangle.getCornerA().getX(), 0);
                linesMat.put(i, rectangle.getCornerB().getX(), 0);
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
