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
    private static final int ALLOW_TEMPORARY_LENGTH = 10;

    /**
     * Find horizontal and vertical lines. Work only with grayscale picture changed with edge detection method.
     * @param picture where the lines are finding. Picture must be in grayscale.
     */
    public void findLines(Mat picture){
        int height = picture.rows();
        int width = picture.cols();

        // horizontal lines
        for(int y=0; y<height; y++){
            findLineInRow(y, picture);
        }

        // vertical lines
        for (int x=0; x<width; x++){
            findLineInColumn(x, picture);
        }
    }

    /**
     * Method finding lines in row.
     * @param y value of row in picture.
     * @param picture where lines are finding.
     */
    private void findLineInRow(int y, Mat picture){
        int width = picture.cols();
        int startX = -1;
        int endX = -1;
        int emptyRangeCounter = 0;
        for(int x = 0; x < width; x++){
            if(picture.get(y, x)[0] > THRESHOLD_COLOR){
                emptyRangeCounter = 0;
                if(startX < 0){
                    startX = x;
                }else{
                    endX = x;
                }
            }else if(++emptyRangeCounter >= ALLOW_EMPTY_RANGE && startX >= 0){
                addHorizontalLine(startX, endX, y);
                startX = -1;
                endX = -1;
            }
        }
        if(startX >= 0 && endX >= 0){
            addHorizontalLine(startX, endX, y);
        }
    }

    /**
     * Create and add line into horizontalLines list if the line have enough length.
     * @param startX x value where line start.
     * @param endX x value where line end.
     * @param y value of line.
     */
    private void addHorizontalLine(int startX, int endX, int y){
        if(endX - startX + 1 >= ALLOW_TEMPORARY_LENGTH){
            Coordinate start = new Coordinate(startX, y);
            Coordinate end = new Coordinate(endX, y);
            addLine(HorizontalLine.createLine(start, end), this.horizontalLines);
        }
    }

    /**
     * Method finding lines in column.
     * @param x value of column in picture.
     * @param picture where lines are finding.
     */
    private void findLineInColumn(int x, Mat picture){
        int height = picture.rows();
        int startY = -1;
        int endY = -1;
        int emptyRangeCounter = 0;
        for(int y = 0; y < height; y++){
            if(picture.get(y, x)[0] > THRESHOLD_COLOR){
                emptyRangeCounter = 0;
                if(startY < 0){
                    startY = y;
                }else{
                    endY = y;
                }
            }else if(++emptyRangeCounter >= ALLOW_EMPTY_RANGE && startY >= 0){
                addVerticalLine(startY, endY, x);
                startY = -1;
                endY = -1;
            }
        }
        if(startY >= 0 && endY >= 0){
            addVerticalLine(startY, endY, x);
        }
    }

    /**
     * Create and add line into verticalLine list if the line have enough length.
     * @param startY y value where line start.
     * @param endY y value where line end.
     * @param x value of line.
     */
    private void addVerticalLine(int startY, int endY, int x){
        if(endY - startY + 1 >= ALLOW_TEMPORARY_LENGTH){
            Coordinate start = new Coordinate(x, startY);
            Coordinate end = new Coordinate(x, endY);
            addLine(VerticalLine.createLine(start, end), this.verticalLines);
        }
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
