package postCutter.cutting.geometricShapes.line;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;

import postCutter.cutting.geometricShapes.Coordinate;

public final class LineHandler {
    private List<VerticalLine> verticalLines = new LinkedList<>();
    private List<HorizontalLine> horizontalLines = new LinkedList<>();

    private static final int TRASH_HOLD = 85;
    private static final int ALLOW_EMPTY_RANGE = 5;

    //TODO work only with grayscale
    public void findLines(Mat picture){
        int rows = picture.rows();
        int cols = picture.cols();
        Mat pictureClone = picture.clone();

        // horizontal lines
        for(int j=0; j<cols; j++){
            for(int i=0; i<rows; i++){
                if(picture.get(i, j)[0] > TRASH_HOLD){
                    findHorizontalLine(new Coordinate(j, i), picture);
                }
            }
        }
        Collections.sort(this.horizontalLines);

        // vertical lines
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if(pictureClone.get(i, j)[0] > TRASH_HOLD){
                    findVerticalLine(new Coordinate(j, i), pictureClone);
                }
            }
        }
        Collections.sort(this.verticalLines);
    }

    private void findHorizontalLine(Coordinate start, Mat picture){
        int height = picture.rows();
        int width = picture.cols();
        Coordinate end = start;
        int startX = start.getX();
        int startY = start.getY();
        int emptyRangeCounter = 1;
        for(int x = startX; x < width; x++){
            if(picture.get(startY, x)[0] > TRASH_HOLD){
                picture.put(startY, x, 0);     
            }else if(startY > 0 && picture.get(startY-1, x)[0] > TRASH_HOLD){
                picture.put(startY-1, x, 0);
            }else if(startY < height-1 && picture.get(startY+1, x)[0] > TRASH_HOLD){ 
                picture.put(startY+1, x, 0);
            }else{
                if(emptyRangeCounter >= ALLOW_EMPTY_RANGE){
                    break;
                }
                emptyRangeCounter++;
                continue;
            }
            end = new Coordinate(x, startY);
            emptyRangeCounter = 1;
        }
        HorizontalLine line = HorizontalLine.createLine(start, end);
        if(line != null && line.length() >= width/3){
            this.horizontalLines.add(line);
        }
    }

    private void findVerticalLine(Coordinate start, Mat picture){
        int height = picture.rows();
        int width = picture.cols();
        Coordinate end = start;
        int startX = start.getX();
        int startY = start.getY();
        int emptyRangeCounter = 1;
        for(int y = startY; y < height; y++){
            if(picture.get(y, startX)[0] > TRASH_HOLD){
                picture.put(y, startX, 0);
            }else if(startX > 0 && picture.get(y, startX-1)[0] > TRASH_HOLD){
                picture.put(y, startX-1, 0);
            }else if(startX < width-1 && picture.get(y, startX+1)[0] > TRASH_HOLD){ 
                picture.put(y, startX+1, 0);
            }else{
                if(emptyRangeCounter >= ALLOW_EMPTY_RANGE){
                    break;
                }
                emptyRangeCounter++;
                continue;
            }
            end = new Coordinate(startX, y);
            emptyRangeCounter = 1;
        }
        VerticalLine line = VerticalLine.createLine(start, end);
        if(line != null && line.length() >= height/4){
            this.verticalLines.add(line);
        }
    }

    public List<MyLine> getHorizontalLines(){
        return new LinkedList<>(this.horizontalLines);
    }

    public List<MyLine> getVerticalLines(){
        return new LinkedList<>(this.verticalLines);
    }
}
