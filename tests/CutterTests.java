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
import postCutter.MyProgress;
import postCutter.geometricShapes.Coordinate;

/**
 * Tests for Cutter class.
 */
public class CutterTests {
    Cutter cutter = new Cutter();
    Mat mat = new Mat(100, 100, CvType.CV_8UC3, new Scalar(0, 0, 0));
    Mat largeMat = new Mat(10000, 10000, CvType.CV_8UC3, new Scalar(0, 0, 0));

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
        cutter.loadPicture(mat);
        Coordinate cornerA = new Coordinate(19, 19);
        Coordinate cornerB = new Coordinate(61, 61);
        cutter.getRectangle().setCornerA(cornerA);
        cutter.getRectangle().setCornerB(cornerB);
        Mat picture = cutter.getCroppedImage();

        assertNotNull("Picture crop wasn`t successful" ,picture);
        assertEquals("Picture height isn`t correct.", 40, picture.rows());
        assertEquals("Picture width isn`t correct.", 40, picture.cols());
    }

    /**
     * Test for stop flag trigger.
     */
    @Test
    public void stopFlagTest(){
        Progress progress = new Progress();
        Cutter cutter2 = new Cutter(progress);

        progress.setStopFlag();
        cutter2.loadPicture(largeMat);

        assertEquals("Horizontal lines should be empty.", 0, cutter2.getHorizontalLines().size());
        assertEquals("Vertical lines should be empty.", 0, cutter2.getVerticalLines().size());
        assertNull(cutter2.getRectangle());
    }

    /**
     * Class for test stop flag.
     */
    private class Progress extends MyProgress{
        @Override
        public void update(int progress) {}
    }
}
