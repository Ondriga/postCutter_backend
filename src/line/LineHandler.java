package line;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;

public class LineHandler {
    private List<MyLine> verticalLines = new LinkedList<>();
    private List<MyLine> horizontalLines = new LinkedList<>();

    private static final int TRASH_HOLD = 85;

    //TODO work only with grayscale
    public void findLines(Mat picture){
        int rows = picture.rows();
        int cols = picture.cols();
        Mat pictureClone = picture.clone();

        addPicturesEdges(rows, cols);

        // horizontal lines
        for(int j=0; j<cols; j++){
            for(int i=0; i<rows; i++){
                if(picture.get(i, j)[0] > TRASH_HOLD){
                    findHorizontalLine(new Coordinate(j, i), picture);
                }
            }
        }

        // vertical lines
        for (int i=0; i<rows; i++){
            for (int j=0; j<cols; j++){
                if(pictureClone.get(i, j)[0] > TRASH_HOLD){
                    findVerticalLine(new Coordinate(j, i), pictureClone);
                }
            }
        }
    }

    private void findHorizontalLine(Coordinate start, Mat picture){
        int height = picture.rows();
        int width = picture.cols();
        Coordinate end = start;
        int startX = start.getX();
        int startY = start.getY();
        for(int x = startX+1; x < width; x++){
            if(picture.get(startY, x)[0] > TRASH_HOLD){
                picture.put(startY, x, 0);     
            }else if(startY > 0 && picture.get(startY-1, x)[0] > TRASH_HOLD){
                picture.put(startY-1, x, 0);
            }else if(startY < height-1 && picture.get(startY+1, x)[0] > TRASH_HOLD){ 
                picture.put(startY+1, x, 0);
            }else{
                break;
            }
            end = new Coordinate(x, startY);
        }
        MyLine line = MyLine.createLine(start, end);
        if(line != null && line.length() >= 2){
            picture.put(startY, startX, 0);
            this.horizontalLines.add(line);
        }
    }

    private void findVerticalLine(Coordinate start, Mat picture){
        int height = picture.rows();
        int width = picture.cols();
        Coordinate end = start;
        int startX = start.getX();
        int startY = start.getY();
        for(int y = startY+1; y < height; y++){
            if(picture.get(y, startX)[0] > TRASH_HOLD){
                picture.put(y, startX, 0);
            }else if(startX > 0 && picture.get(y, startX-1)[0] > TRASH_HOLD){
                picture.put(y, startX-1, 0);
            }else if(startX < width-1 && picture.get(y, startX+1)[0] > TRASH_HOLD){ 
                picture.put(y, startX+1, 0);
            }else{
                break;
            }
            end = new Coordinate(startX, y);
        }
        MyLine line = MyLine.createLine(start, end);
        if(line != null && line.length() >= 2){
            picture.put(startY, startX, 0);
            this.verticalLines.add(line);
        }
    }

    private void addPicturesEdges(int rows, int cols){
        MyLine tmp = MyLine.createLine(new Coordinate(0, 0), new Coordinate(0, rows-1));
        if(tmp != null){
            this.verticalLines.add(tmp);
        }
        tmp = MyLine.createLine(new Coordinate(cols-1, 0), new Coordinate(cols-1, rows-1));
        if(tmp != null){
            this.verticalLines.add(tmp);
        }
        tmp = MyLine.createLine(new Coordinate(0, 0), new Coordinate(cols-1, 0));
        if(tmp != null){
            this.horizontalLines.add(tmp);
        }
        tmp = MyLine.createLine(new Coordinate(0, rows-1), new Coordinate(cols-1, rows-1));
        if(tmp != null){
            this.horizontalLines.add(tmp);
        }
    }

    public List<MyLine> getHorizontalLines(){
        return this.horizontalLines;
    }

    public List<MyLine> getVerticalLines(){
        return this.verticalLines;
    }
}
