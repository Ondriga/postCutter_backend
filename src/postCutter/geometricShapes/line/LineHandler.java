/*
 * Source code for the backend of Bachelor thesis.
 * LineHandler class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import postCutter.geometricShapes.Coordinate;

/**
 * This class is made for find and store lines in picture changed by edge detection method.
 */
public final class LineHandler {
    /// List of vertical lines found in picture.
    private List<MyLine> verticalLines = new ArrayList<>();
    /// List of horizontal lines found in picture.
    private List<MyLine> horizontalLines = new ArrayList<>();

    /// Constant for color limit to by count as black.
    private static final int THRESHOLD_COLOR = 85;
    /// Constant for allow empty space size in line.
    private static final int ALLOW_EMPTY_RANGE = 5;
    /// Constant for allow position threshold for pixel or line. 
    private static final int ALLOW_POSITION_MOVE = 2;
    /// Constant for allow length of finding lines.
    private static final int ALLOW_TEMPORARY_LENGTH = 50;

    /**
     * Find horizontal and vertical lines. Work only with grayscale picture changed with edge detection method.
     * @param picture where the lines are finding. Picture must be in grayscale.
     */
    public void findLines(Mat picture){
        int rows = picture.rows();
        int cols = picture.cols();
        Mat pictureClone = picture.clone();

        // horizontal lines
        for(int j=0; j<cols; j += ALLOW_POSITION_MOVE + 1){
            for(int i=0; i<rows; i++){
                if(picture.get(i, j)[0] > THRESHOLD_COLOR){
                    if(j+1<cols && picture.get(i, j+1)[0] > THRESHOLD_COLOR){
                        addLine(findHorizontalLine(new Coordinate(j, i), picture), this.horizontalLines);
                    }
                }
            }
        }

        // vertical lines
        for (int i=0; i<rows; i += ALLOW_POSITION_MOVE + 1){
            for (int j=0; j<cols; j++){
                if(pictureClone.get(i, j)[0] > THRESHOLD_COLOR){
                    if(i+1< rows && pictureClone.get(i+1, j)[0] > THRESHOLD_COLOR){
                        addLine(findVerticalLine(new Coordinate(j, i), pictureClone), this.verticalLines);
                    }
                }
            }
        }
    }

    /**
     * Check if x or y value is in picture.
     * @param value x or y value.
     * @param maxValue max x or y value of picture.
     * @return true if value is in interval <0, maxValue>, otherwise false.
     */
    private boolean isValueInPicture(int value, int maxValue){
        return value >= 0 && value <= maxValue;
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
            if(!isValueInPicture(y+i, picture.rows()-1)){
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
        if(line != null && line.length() >= ALLOW_TEMPORARY_LENGTH){
            return line;
        }
        return null;
    }

    /**
     * Add line into list of lines or merge with one of line in this list.
     * @param newLine new line, which is supposed to be add to list of lines.
     * @param lines list of lines.
     */
    private void addLine(MyLine newLine, List<MyLine> lines){
        if(newLine != null){
            for(int i=0; i<lines.size(); i++){
                switch(lines.get(i).extendByLine(newLine)){
                    case -1:
                        break;
                    case 0:
                        newLine = lines.get(i);
                        lines.remove(i--);
                        break;
                    default:
                        lines.add(i, newLine);
                        return;
                }
            }
            lines.add(newLine);
        }
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
            if(!isValueInPicture(x+i, picture.cols()-1)){
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
     * @return null if line length is smaller than allowed temporary length, otherwise new verticalLine object.
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
        if(line != null && line.length() >= ALLOW_TEMPORARY_LENGTH){
            return line;
        }
        return null;
    }

    /**
     * Delete all lines from lists, that are smaller than 1/3 width or 1/5 height of picture.
     * @param width of picture.
     * @param height of picture.
     */
    public void deleteNoise(int width, int height){
        deleteShortLines(this.horizontalLines, width/3);
        deleteShortLines(this.verticalLines, height/5);
    }

    /**
     * Delete all lines from list, that are shorter than length.
     * @param lines list of lines.
     * @param length required length.
     */
    private void deleteShortLines(List<MyLine> lines, int length){
        for(int i = 0; i < lines.size(); i++){
            if(lines.get(i).length() < length){
                lines.remove(i--);
            }
        }
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

    /**
     * This method clear horizontal and vertical lists of lines.
     */
    public void clear(){
        this.horizontalLines.clear();
        this.verticalLines.clear();
    }
}
