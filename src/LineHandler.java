import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

public class LineHandler {
    private List<MyLine> verticalLines = new ArrayList<>();
    private List<MyLine> horizonatLines = new ArrayList<>();

    //TODO delete
    public void test(Mat picture){
        findLines(picture, verticalLines);
    }

    //TODO work only with grayscale
    private void findLines(Mat picture, List<MyLine> lines){
        int rows = picture.rows(); //TODO Calculates number of rows
        int cols = picture.cols(); //TODO Calculates number of columns

        //Add first edge of the picture.
        lines.add(new MyLine(new Coordinate(0, 0), new Coordinate(cols-1, 0)));

        for (int i=0; i<rows; i++)
        {
            for (int j=0; j<cols; j++)
            {
                double[] data = picture.get(i, j); //TODO Stores element in an array
            }
        }

        //Add secund edge of the picture.
        lines.add(new MyLine(new Coordinate(0, rows-1), new Coordinate(cols-1, rows-1)));
    }
}
