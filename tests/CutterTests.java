/*
 * Tests for the backend of Bachelor thesis.
 * LineHandlerTests class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;

import postCutter.Cutter;

/**
 * Tests for Cutter class.
 */
public class CutterTests {
    Cutter cutter = new Cutter();
    Mat mat = new Mat(100, 100, CvType.CV_8UC3, new Scalar(0, 0, 0));

    /**
     * Test for do cut without load picture.
     */
    @Test
    public void notLoadedPictureTest(){
        assertNull(cutter.getCroppedImage());
    }

    /**
     * Test for picture cut.
     */
    @Test
    public void doCutTest(){
        for(int i=20; i<=60; i++){
            mat.put(20, i, 255, 255, 255);
            mat.put(60, i, 255, 255, 255);
            mat.put(i, 20, 255, 255, 255);
            mat.put(i, 60, 255, 255, 255);
        }

        cutter.loadPicture(mat);
        Mat picture = cutter.getCroppedImage();
        assertNotNull("Picture crop wasn`t successful" ,picture);
        assertEquals("Picture height isn`t correct.", 40, picture.rows());
        assertEquals("Picture width isn`t correct.", 40, picture.cols());
    }
}
