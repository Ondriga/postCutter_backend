package line;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class LineHandler {
    private List<MyLine> verticalLines = new ArrayList<>();
    private List<MyLine> horizontalLines = new ArrayList<>();

    //TODO work only with grayscale
    public void findLines(Mat picture){
        int rows = picture.rows();
        int cols = picture.cols();

        addPicturesEdges(rows, cols);

        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                double[] data = picture.get(i, j);
                if(data[0] == 0){
                    int nextPixel = 0;
                    if(i != rows-1){
                        nextPixel = (int) picture.get(i+1, j)[0];
                    }
                    addLine(new Coordinate(j, i), nextPixel, this.verticalLines);
                    if(j != cols-1){
                        nextPixel = (int) picture.get(i, j+1)[0];
                    }
                    addLine(new Coordinate(j, i), nextPixel, this.horizontalLines);
                }
            }
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

    private void addLine(Coordinate coordinate, int nextPixel, List<MyLine> lines){
        for(MyLine line : lines){
            if(line.extendByOne(coordinate)){
                return;
            }
        }
        if(nextPixel == 0){
            MyLine tmp = MyLine.createLine(coordinate, coordinate);
            if(tmp != null){
                lines.add(tmp);
            }
        }
    }

    public List<MyLine> getHorizontalLines(){
        return this.horizontalLines;
    }

    public List<MyLine> getVerticalLines(){
        return this.verticalLines;
    }
}
