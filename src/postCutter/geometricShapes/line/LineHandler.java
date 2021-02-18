/*
 * Source code for the backend of Bachelor thesis.
 * LineHandler class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;

import postCutter.geometricShapes.Coordinate;

/**
 * This class is made for find and store lines in picture changed by edge detection method.
 */
public final class LineHandler {
    /// List of vertical lines found in picture.
    private List<VerticalLine> verticalLines = new ArrayList<>();
    /// List of horizontal lines found in picture.
    private List<HorizontalLine> horizontalLines = new ArrayList<>();

    /// Constant for color limit to by count as black.
    private static final int THRESHOLD_COLOR = 85;
    /// Constant for allow empty space size in line.
    private static final int ALLOW_EMPTY_RANGE = 5;
    /// Constant for allow position threshold for pixel or line. 
    private static final int ALLOW_POSITION_MOVE = 2;

    /**
     * Find horizontal and vertical lines. Work only with grayscale picture changed with edge detection method.
     * @param picture where the lines are finding. Picture must be in grayscale.
     */
    public void findLines(Mat picture){
        int rows = picture.rows();
        int cols = picture.cols();
        Mat pictureClone = picture.clone();

        // horizontal lines
        for(int j=0; j<cols; j++){
            for(int i=0; i<rows; i++){
                if(picture.get(i, j)[0] > THRESHOLD_COLOR){
                    if(j+1<cols && picture.get(i, j+1)[0] > THRESHOLD_COLOR){
                        addHorizontalLine(findHorizontalLine(new Coordinate(j, i), picture));
                    }
                }
            }
        }

        // vertical lines
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if(pictureClone.get(i, j)[0] > THRESHOLD_COLOR){
                    if(i+1< rows && pictureClone.get(i+1, j)[0] > THRESHOLD_COLOR){
                        addVerticalLine(findVerticalLine(new Coordinate(j, i), pictureClone));
                    }
                }
            }
        }
    }

    /**
     * Check if y value is from picture.
     * @param y value.
     * @param maxY max y value of picture.
     * @return true if y is in interval <0, maxY>, otherwise false.
     */
    private boolean isYInPicture(int y, int maxY){
        return y >= 0 && y <= maxY;
    }

    /**
     * Provide check if next pixel in x way is also black. There are implemented check if black pixel isn`t moved in y way.
     * @param x value of pixel.
     * @param y value of pixel.
     * @param picture where the line is finding.
     * @return true if the pixel or pixel in near y neighborhood is black, otherwise false.
     */
    private boolean checkPixelYWay(int x, int y, Mat picture){
        boolean found = false;
        for(int i=-1 * ALLOW_POSITION_MOVE; i<=ALLOW_POSITION_MOVE; i++){
            if(!isYInPicture(y+i, picture.rows()-1)){
                continue;
            }
            if(picture.get(y+i, x)[0] > THRESHOLD_COLOR){
                picture.put(y+i, x, 0);
                found = true;
            }
        }
        return found;
    }

    /**
     * Method finding end of line.
     * @param start first coordinate of line.
     * @param picture where line is finding.
     * @return null if line length is smaller than allowed empty range, otherwise new horizontalLine object.
     */
    private HorizontalLine findHorizontalLine(Coordinate start, Mat picture){
        int width = picture.cols();
        int startX = start.getX();
        int startY = start.getY();
        Coordinate end = new Coordinate(startX, startY);
        int emptyRangeCounter = 0;
        for(int x = startX; x < width; x++){
            if(checkPixelYWay(x, startY, picture)){
                end.setX(x);
                emptyRangeCounter = 0;
            }else{
                if(++emptyRangeCounter >= ALLOW_EMPTY_RANGE){
                    break;
                }
            }
        }
        HorizontalLine line = HorizontalLine.createLine(start, end);
        if(line != null && line.length() >= ALLOW_EMPTY_RANGE){
            return line;
        }
        return null;
    }

    /**
     * Add line into list of horizontal lines or merge with one of line in this list.
     * @param line new line, which is supposed to be add to list of horizontal lines.
     */
    private void addHorizontalLine(HorizontalLine line){
        if(line != null){
            for(int i=0; i<this.horizontalLines.size(); i++){
                switch(this.horizontalLines.get(i).extendByLine(line)){
                    case -1:
                        break;
                    case 0:
                        line = this.horizontalLines.get(i);
                        this.horizontalLines.remove(i--);
                        break;
                    default:
                        this.horizontalLines.add(i, line);
                        return;
                }
            }
            this.horizontalLines.add(line);
        }
    }

    /**
     * Check if x value is from picture.
     * @param x value.
     * @param maxX max x value of picture.
     * @return true if x is in interval <0, maxX>, otherwise false.
     */
    private boolean isXInPicture(int x, int maxX){
        return x >= 0 && x <= maxX;
    }

    /**
     * Provide check if next pixel in y way is also black. There are implemented check if black pixel isn`t moved in x way.
     * @param x value of pixel.
     * @param y value of pixel.
     * @param picture where the line is finding.
     * @return true if the pixel or pixel in near x neighborhood is black, otherwise false.
     */
    private boolean checkPixelXWay(int x, int y, Mat picture){
        boolean found = false;
        for(int i=-1 * ALLOW_POSITION_MOVE; i<=ALLOW_POSITION_MOVE; i++){
            if(!isXInPicture(x+i, picture.cols()-1)){
                continue;
            }
            if(picture.get(y, x+i)[0] > THRESHOLD_COLOR){
                picture.put(y, x+i, 0);
                found = true;
            }
        }
        return found;
    }

    /**
     * Method finding end of line.
     * @param start first coordinate of line.
     * @param picture where line is finding.
     * @return null if line length is smaller than allowed empty range, otherwise new verticalLine object.
     */
    private VerticalLine findVerticalLine(Coordinate start, Mat picture){
        int height = picture.rows();
        int startX = start.getX();
        int startY = start.getY();
        Coordinate end = new Coordinate(startX, startY);
        int emptyRangeCounter = 0;
        for(int y = startY; y < height; y++){
            if(checkPixelXWay(startX, y, picture)){
                end.setY(y);
                emptyRangeCounter = 0;
            }else{
                if(++emptyRangeCounter >= ALLOW_EMPTY_RANGE){
                    break;
                }
            }
        }
        VerticalLine line = VerticalLine.createLine(start, end);
        if(line != null && line.length() >= ALLOW_EMPTY_RANGE){
            return line;
        }
        return null;
    }

    /**
     * Add line into list of vertical lines or merge with one of line in this list.
     * @param line new line, which is supposed to be add to list of vertical lines.
     */
    private void addVerticalLine(VerticalLine line){
        if(line != null){
            for(int i=0; i<this.verticalLines.size(); i++){
                switch(this.verticalLines.get(i).extendByLine(line)){
                    case -1:
                        break;
                    case 0:
                        line = this.verticalLines.get(i);
                        this.verticalLines.remove(i--);
                        break;
                    default:
                        this.verticalLines.add(i, line);
                        return;
                }
            }
            this.verticalLines.add(line);
        }
    }

    /**
     * Delete all lines from lists, that are smaller than 1/3 width or 1/5 height of picture.
     * @param width of picture.
     * @param height of picture.
     */
    public void deleteNoise(int width, int height){
        this.horizontalLines.removeIf(o -> (o.length() < width/3));
        this.verticalLines.removeIf(o -> (o.length() < height/5));
    }

    /**
     * Getter for horizontal lines.
     * @return
     */
    public List<MyLine> getHorizontalLines(){
        return new ArrayList<>(this.horizontalLines);
    }

    /**
     * Getter for vertical lines.
     * @return
     */
    public List<MyLine> getVerticalLines(){
        return new ArrayList<>(this.verticalLines);
    }
}
