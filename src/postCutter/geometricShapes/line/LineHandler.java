package postCutter.geometricShapes.line;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;

import postCutter.geometricShapes.Coordinate;

public final class LineHandler {
    private List<VerticalLine> verticalLines = new LinkedList<>();
    private List<HorizontalLine> horizontalLines = new LinkedList<>();

    private static final int TRASH_HOLD_COLOR = 85;
    private static final int ALLOW_EMPTY_RANGE = 5;
    private static final int ALLOW_POSITION_MOVE = 2;

    //TODO work only with grayscale
    public void findLines(Mat picture){
        int rows = picture.rows();
        int cols = picture.cols();
        Mat pictureClone = picture.clone();

        // horizontal lines
        for(int j=0; j<cols; j++){
            for(int i=0; i<rows; i++){
                if(picture.get(i, j)[0] > TRASH_HOLD_COLOR){
                    addHorizontalLine(findHorizontalLine(new Coordinate(j, i), picture));
                }
            }
        }
        Collections.sort(this.horizontalLines);

        // vertical lines
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if(pictureClone.get(i, j)[0] > TRASH_HOLD_COLOR){
                    addVerticalLine(findVerticalLine(new Coordinate(j, i), pictureClone));
                }
            }
        }
        Collections.sort(this.verticalLines);
    }

    private boolean checkYInPicture(int y, int maxY){
        return y >= 0 && y <= maxY;
    }

    private boolean checkPixelYWay(int x, int y, Mat picture){
        boolean found = false;
        for(int i=-1 * ALLOW_POSITION_MOVE; i<=ALLOW_POSITION_MOVE; i++){
            if(!checkYInPicture(y+i, picture.rows()-1)){
                continue;
            }
            if(picture.get(y+i, x)[0] > TRASH_HOLD_COLOR){
                picture.put(y+i, x, 0);
                found = true;
            }
        }
        return found;
    }

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

    private void addHorizontalLine(HorizontalLine line){
        if(line != null){
            for(HorizontalLine listLine : this.horizontalLines){
                switch(listLine.extendByLine(line)){
                    case -1:
                        break;
                    case 0:
                        this.horizontalLines.removeIf(o -> (o.equals(listLine)));
                        addHorizontalLine(listLine);
                        return;
                    default:
                        this.horizontalLines.add(line);
                        return;
                }
            }
            this.horizontalLines.add(line);
        }
    }

    private boolean checkXInPicture(int x, int maxX){
        return x >= 0 && x <= maxX;
    }

    private boolean checkPixelXWay(int x, int y, Mat picture){
        boolean found = false;
        for(int i=-1 * ALLOW_POSITION_MOVE; i<=ALLOW_POSITION_MOVE; i++){
            if(!checkXInPicture(x+i, picture.cols()-1)){
                continue;
            }
            if(picture.get(y, x+i)[0] > TRASH_HOLD_COLOR){
                picture.put(y, x+i, 0);
                found = true;
            }
        }
        return found;
    }

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

    private void addVerticalLine(VerticalLine line){
        if(line != null){
            for(VerticalLine listLine : this.verticalLines){
                switch(listLine.extendByLine(line)){
                    case -1:
                        break;
                    case 0:
                        this.verticalLines.removeIf(o -> (o.equals(listLine)));
                        addVerticalLine(listLine);
                        return;
                    default:
                        this.verticalLines.add(line);
                        return;
                }
            }
            this.verticalLines.add(line);
        }
    }

    public void deleteNoise(int width, int height){
        this.horizontalLines.removeIf(o -> (o.length() < width/3));
        this.verticalLines.removeIf(o -> (o.length() < height/5));
    }

    public List<MyLine> getHorizontalLines(){
        return new LinkedList<>(this.horizontalLines);
    }

    public List<MyLine> getVerticalLines(){
        return new LinkedList<>(this.verticalLines);
    }
}
