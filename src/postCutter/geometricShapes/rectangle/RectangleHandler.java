/*
 * Source code for the backend of Bachelor thesis.
 * RectangleHandler class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import postCutter.geometricShapes.Coordinate;
import postCutter.geometricShapes.line.*;

/**
 * This class is made for find and store rectangle from lists of horizontal and vertical lines.
 */
public final class RectangleHandler {
    /// Constant for allowed empty spaces between lines.
    private static final int THRESHOLD = 5;
    /// Constant for min x value of picture.
    private static final int MIN_X = 0;

    /// Rectangle.
    private MyRectangle rectangle = null;

    /**
     * First finding rectangle, that are on max width of picture. Than try find smaller rectangle. In the end the rectangle is stored.
     * @param horizontalLines list of horizontal lines.
     * @param verticalLines list of vertical lines.
     * @param width of picture.
     * @param height of picture.
     */
    public void findRectangle(List<MyLine> horizontalLines, List<MyLine> verticalLines, int width, int height){
        this.rectangle = MyRectangle.createRectangle(new Coordinate(0, 0), new Coordinate(width - 1, height - 1));
        List<MyLine> firstHalfLines = new ArrayList<>();
        List<MyLine> secondHalfLines = new ArrayList<>();
        for(MyLine line : horizontalLines){
            if(line.getStartPoint().getY() < height/2){
                firstHalfLines.add(line);
            }else{
                secondHalfLines.add(line);
            }
        }
        List<MyLine> firstHalfReverseLines = new ArrayList<>(secondHalfLines);
        Collections.reverse(firstHalfReverseLines);
        List<MyLine> secondHalfReverseLines = new ArrayList<>(firstHalfLines);
        Collections.reverse(secondHalfReverseLines);
        
        int startY = getYLimit(width, secondHalfReverseLines);
        int endY = getYLimit(width, secondHalfLines);

        if(startY == -1 && endY != -1){
            startY = endY;
            endY = getYLimit(width, firstHalfReverseLines);
        }
        if(startY != -1 && endY == -1){
            endY = startY;
            startY = getYLimit(width, firstHalfLines);
        }

        if(startY < 0 || endY < 0 || endY - startY < height/5){
            findSmallerRectangle(horizontalLines, verticalLines);
            return;
        }

        Coordinate cornerA = new Coordinate(MIN_X, startY);
        Coordinate cornerB = new Coordinate(width-1, endY);

        this.rectangle = MyRectangle.createRectangle(cornerA, cornerB);
    }

    /**
     * Check if start x value of line is in allowed interval form value minX.
     * @param minX left border for line.
     * @param leftLineX start x value of line.
     * @return true if x value of line is in allowed interval, otherwise false.
     */
    private boolean isLeftXCorrect(int minX, int leftLineX){
        return leftLineX >= minX && leftLineX < minX + THRESHOLD;
    }

    /**
     * Check if end x value of line is in allowed interval from value maxX.
     * @param maxX right border for line.
     * @param rightLineX end x value of line.
     * @return true if x value of line is in allowed interval, otherwise false.
     */
    private boolean isRightXCorrect(int maxX, int rightLineX){
        return rightLineX <= maxX && rightLineX > maxX - THRESHOLD;
    }

    /**
     * Find y value of horizontal line, that start from left side of picture to the right side.
     * If that line isn`t found, than find line, that start from left side or end on right side of picture.
     * @param width of picture.
     * @param horizontalLines list of horizontal lines.
     * @return y value of horizontal line, that respond conditions. If line didn`t find, than -1.
     */
    private int getYLimit(int width, List<MyLine> horizontalLines){
        int limitY = -1;
        for(MyLine line : horizontalLines){
            if(limitY == -1 && (isLeftXCorrect(MIN_X, line.getStartPoint().getX()) || isRightXCorrect(width, line.getEndPoint().getX()))){
                limitY = line.getStartPoint().getY();
            }
            if(isLeftXCorrect(MIN_X, line.getStartPoint().getX()) && isRightXCorrect(width, line.getEndPoint().getX())){
                return line.getStartPoint().getY();
            }
        }
        return limitY;
    }

    /**
     * Check if exist horizontal line between 2 vertical lines. This horizontal line must be on the end of vertical lines.
     * @param leftX left border for horizontal lines.
     * @param rightX right border for horizontal lines.
     * @param y value for horizontal line.
     * @param horizontalLines list of horizontal lines.
     * @return true if line was found, otherwise false.
     */
    private boolean isYLimitCorrect(int leftX, int rightX, int y, List<MyLine> horizontalLines){
        for(MyLine line : horizontalLines){
            if(line.getStartPoint().getY() == y && (isLeftXCorrect(leftX, line.getStartPoint().getX()) ||
            isRightXCorrect(rightX, line.getEndPoint().getX()))){
                return true;
            }
        }
        return false;
    }

    /**
     * Finding smaller rectangle, than width of picture.
     * @param horizontalLines list of horizontal lines.
     * @param verticalLines list of vertical lines.
     */
    private void findSmallerRectangle(List<MyLine> horizontalLines, List<MyLine> verticalLines){
        List<MyLine> verticalFirstHalf = verticalLines.subList(0, verticalLines.size()/2);
        List<MyLine> verticalSecondReverseHalf = verticalLines.subList(verticalLines.size()/2, verticalLines.size());
        Collections.reverse(verticalSecondReverseHalf);
        for(MyLine line1 : verticalFirstHalf){ //Finding left side of rectangle.
            for(MyLine line2 : verticalSecondReverseHalf){ //Finding right side of rectangle.
                if(line1.isSimilar(line2)){
                    int startY = Math.max(line1.getStartPoint().getY(), line2.getStartPoint().getY());
                    int endY = Math.max(line1.getEndPoint().getY(), line2.getEndPoint().getY());
                    
                    //Finding top and bottom side of rectangle.
                    if(isYLimitCorrect(line1.getStartPoint().getX(), line2.getStartPoint().getX(), startY, horizontalLines) &&
                    isYLimitCorrect(line1.getStartPoint().getX(), line2.getStartPoint().getX(), endY, horizontalLines)){
                        Coordinate cornerA = new Coordinate(line1.getStartPoint().getX(), startY);
                        Coordinate cornerB = new Coordinate(line2.getStartPoint().getX(), endY);
                        this.rectangle = MyRectangle.createRectangle(cornerA, cornerB);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Getter for rectangle.
     * @return rectangle.
     */
    public MyRectangle getRectangle() {
        return rectangle;
    }

    /**
     * This method clear rectangle.
     */
    public void clear(){
        this.rectangle = null;
    }
}
