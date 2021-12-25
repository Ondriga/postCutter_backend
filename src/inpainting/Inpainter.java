/*
 * Source code for the backend of Bachelor thesis.
 * Inpainter class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package inpainting;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.photo.Photo;

import postCutter.geometricShapes.rectangle.MyRectangle;

/**
 * Noninstantiable class for inpainting.
 */
public class Inpainter {
    /// Constant for inpainting pixel radius.
    private static final int INPAINT_RADIUS = 3;

    /**
     * Suppress default constructor for noninstantiability.
     */
    private Inpainter(){
        throw new AssertionError();
    }

    /**
     * Paint pixel under rectangle in white.
     * @param mat must have all pixels black.
     * @param rectangle rectangle for painting.
     */
    private static void paintMask(Mat mat, MyRectangle rectangle){
        for(int y = rectangle.getCornerA().getY(); y <= rectangle.getCornerB().getY(); y++){
            for(int x = rectangle.getCornerA().getX(); x <= rectangle.getCornerB().getX(); x++){
                mat.put(y, x, 255);
            }
        }
    }

    /**
     * Inpaint picture by rectangle with telea algorithm.
     * @param picture for inpainting.
     * @param rectangle define area for inpainting.
     * @return inpainting picture.
     */
    public static Mat inpaintingTelea(Mat picture, MyRectangle rectangle) {
        return inpainting(picture, rectangle, Photo.INPAINT_TELEA);
    }

    /**
     * Inpaint picture by rectangle with ns algorithm.
     * @param picture for inpainting.
     * @param rectangle define area for inpainting.
     * @return inpainting picture.
     */
    public static Mat inpaintingNS(Mat picture, MyRectangle rectangle) {
        return inpainting(picture, rectangle, Photo.INPAINT_NS);
    }

    /**
     * Inpaint picture by rectangle.
     * @param picture for inpainting.
     * @param rectangle define area for inpainting.
     * @param flags choose the method for inpainting.
     * @return inpainting picture.
     */
    private static Mat inpainting(Mat picture, MyRectangle rectangle, int flags){
        Mat dst = new Mat(picture.rows(), picture.cols(), picture.type());
        Mat mask = new Mat(picture.rows(), picture.cols(), CvType.CV_8UC1, new Scalar(0));
        paintMask(mask, rectangle);
        Photo.inpaint(picture, mask, dst, INPAINT_RADIUS, flags);
        return dst;
    }
}
