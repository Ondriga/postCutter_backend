/*
 * Source code for the backend of Bachelor thesis.
 * LineHandler class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;

import postCutter.edgeDetection.EdgeDetector;
import postCutter.geometricShapes.Coordinate;
import postCutter.MyProgress;

/**
 * This class is made for find and store lines in picture changed by edge detection method.
 */
public final class LineHandler {
    /// List of vertical lines found in picture.
    private List<MyLine> verticalLines = new ArrayList<>();
    /// List of horizontal lines found in picture.
    private List<MyLine> horizontalLines = new ArrayList<>();
    /// Map for temporary storing horizontal lines. 
    private HashMap<Integer, List<MyLine>> horizontalMap = new HashMap<>();
    /// Map for temporary storing vertical lines.
    private HashMap<Integer, List<MyLine>> verticalMap = new HashMap<>();

    /// Constant for color limit to by count as black.
    private static final int THRESHOLD_COLOR = 85;

    /**
     * Find horizontal and vertical lines. Work only with grayscale picture changed with edge detection method.
     * @param picture where the lines are finding. Picture must be in grayscale.
     */
    public void findLines(Mat picture, MyProgress progress){
        for(int y=0; y<picture.rows(); y++){
            if(progress != null){
                if(progress.shouldStop()){
                    return;
                }
                progress.increase();
            }
            for(int x=0; x<picture.cols(); x++){
                if(progress != null && progress.shouldStop()){
                    return;
                }
                if(picture.get(y, x)[0] >THRESHOLD_COLOR){
                    Coordinate coordinate = new Coordinate(x, y);
                    x = addDot(y, this.horizontalMap, HorizontalLine.createLine(coordinate, coordinate));
                    addDot(x, this.verticalMap, VerticalLine.createLine(coordinate, coordinate));
                }
            }
        }
    }

    /**
     * Extend lines in map by black dot. The key in map is position in row if it is dot for vertical lines
     * and column if it is for horizontal lines.
     * @param key position in row for vertical lines or column for horizontal lines
     * @param lineMap horizontal or vertical map with lines.
     * @param dotLine vertical or horizontal line representing black dot. 
     * @return x value of last known white pixel in row.
     */
    private int addDot(int key, HashMap<Integer, List<MyLine>> lineMap, MyLine dotLine){
        List<MyLine> lineList = lineMap.get(key);
        if(lineList == null){
            lineList = new ArrayList<>();
            lineMap.put(key, lineList);
        }
        for(int i=0; i<lineList.size(); i++){
            if(lineList.get(i).extendByOne(dotLine.getStartPoint())){
                return lineList.get(i).getEndPoint().getX();
            }
            if(dotLine.isBefore(lineList.get(i))){
                lineList.add(i, dotLine);
                return dotLine.getEndPoint().getX();
            }
        }
        lineList.add(dotLine);
        return dotLine.getEndPoint().getX();
    }

    /**
     * Iterate via all lists at all levels. Take 2 rows/columns and merge lines in this levels. higher level store
     * to finalLines list and lower level return to the linesMap at correct level.
     * @param linesMap map of all lines with key, which represent lines level.
     * @param finalLines list of merged lines for later use.
     */
    private void addLines(HashMap<Integer, List<MyLine>> linesMap, List<MyLine> finalLines){
        for(int key : linesMap.keySet()){
            LineMerger lineMerger = new LineMerger(linesMap.get(key), linesMap.get(key + 1), finalLines, key);
            List<MyLine> lines = lineMerger.mergeLinesAndStore();
            if(!lines.isEmpty()){
                linesMap.put(key + 1, lines);
            }
        }
    }

    /**
     * Store lines to final lists and delete all lines from lists, that are smaller than 1/3 width or 1/5 height of picture.
     * @param width of picture.
     * @param height of picture.
     */
    public void storeLinesAndDeleteNoise(int width, int height){
        addLines(this.horizontalMap, this.horizontalLines);
        addLines(this.verticalMap, this.verticalLines);

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
     * This method clear horizontal and vertical lists and maps of lines.
     */
    public void clear(){
        this.horizontalLines.clear();
        this.verticalLines.clear();
        this.horizontalMap.clear();
        this.verticalMap.clear();
    }
}
