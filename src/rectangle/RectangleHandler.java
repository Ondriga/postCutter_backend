package rectangle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import line.*;

public final class RectangleHandler {
    private static final int TRASH_HOLD = 5;
    private static final int MIN_X = 0;

    public MyRectangle getRectangle(List<MyLine> lines, int maxX, int height){
        List<MyLine> firstHalfLines = new LinkedList<>();
        List<MyLine> secondHalfLines = new LinkedList<>();
        for(MyLine line : lines){
            if(line.getStartPoint().getY() < height/2){
                firstHalfLines.add(line);
            }else{
                secondHalfLines.add(line);
            }
        }
        List<MyLine> firstHalfReverseLines = new LinkedList<>(secondHalfLines);
        Collections.reverse(firstHalfReverseLines);
        List<MyLine> secondHalfReverseLines = new LinkedList<>(firstHalfLines);
        Collections.reverse(secondHalfReverseLines);
        
        int startY = getYLimit(maxX, secondHalfReverseLines);
        int endY = getYLimit(maxX, secondHalfLines);

        if(startY == -1 && endY != -1){
            startY = endY;
            endY = getYLimit(maxX, firstHalfReverseLines);
        }
        if(startY != -1 && endY == -1){
            endY = startY;
            startY = getYLimit(maxX, firstHalfLines);
        }

        if(startY == endY){
            return null;
        }

        Coordinate cornerA = new Coordinate(MIN_X, startY);
        Coordinate cornerB = new Coordinate(maxX, endY);

        return MyRectangle.createRectangle(cornerA, cornerB);
    }

    private boolean isLeftXCorrect(int minX, int leftLineX){
        return leftLineX >= minX && leftLineX < minX + TRASH_HOLD;
    }

    private boolean isRightXCorrect(int maxX, int rightLineX){
        return rightLineX <= maxX && rightLineX > maxX - TRASH_HOLD;
    }

    private boolean isInInterval(int minX, int maxX, int leftLineX, int rightLineX){
        return isLeftXCorrect(minX, leftLineX) || isRightXCorrect(maxX, rightLineX);
    }

    private int getYLimit(int maxX, List<MyLine> lines){
        for(MyLine line : lines){
            if(isInInterval(MIN_X, maxX, line.getStartPoint().getX(), line.getEndPoint().getX())){
                return line.getStartPoint().getY();
            }
        }
        return -1;
    }
}
