package postCutter.geometricShapes.rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import postCutter.geometricShapes.Coordinate;
import postCutter.geometricShapes.line.*;

public final class RectangleHandler {
    private static final int TRASH_HOLD = 5;
    private static final int MIN_X = 0;

    private MyRectangle rectangle = null;

    public void findRectangle(List<MyLine> horizontalLines, List<MyLine> verticalLines, int width, int height){
        this.rectangle = null;
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

    private boolean isLeftXCorrect(int minX, int leftLineX){
        return leftLineX >= minX && leftLineX < minX + TRASH_HOLD;
    }

    private boolean isRightXCorrect(int maxX, int rightLineX){
        return rightLineX <= maxX && rightLineX > maxX - TRASH_HOLD;
    }

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

    private boolean isYLimitCorrect(int leftX, int rightX, int y, List<MyLine> horizontalLines){
        for(MyLine line : horizontalLines){
            if(line.getStartPoint().getY() == y && (isLeftXCorrect(leftX, line.getStartPoint().getX()) ||
            isRightXCorrect(rightX, line.getEndPoint().getX()))){
                return true;
            }
        }
        return false;
    }

    private void findSmallerRectangle(List<MyLine> horizontalLines, List<MyLine> verticalLines){
        List<MyLine> verticalFirstHalf = verticalLines.subList(0, verticalLines.size()/2);
        List<MyLine> verticalSecondReverseHalf = verticalLines.subList(verticalLines.size()/2, verticalLines.size());
        Collections.reverse(verticalSecondReverseHalf);
        for(MyLine line1 : verticalFirstHalf){
            for(MyLine line2 : verticalSecondReverseHalf){
                if(line1.isSimilar(line2)){
                    int startY = Math.max(line1.getStartPoint().getY(), line2.getStartPoint().getY());
                    int endY = Math.max(line1.getEndPoint().getY(), line2.getEndPoint().getY());
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

    public MyRectangle getRectangle() {
        return rectangle;
    }
}
