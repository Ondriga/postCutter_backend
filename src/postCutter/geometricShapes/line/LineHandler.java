/*
 * Source code for the backend of Bachelor thesis.
 * LineHandler class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sound.sampled.LineEvent;

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
    /// Map for temporary storing horizontal lines. 
    HashMap<Integer, List<MyLine>> horizontalMap = new HashMap<>();
    /// Map for temporary storing vertical lines.
    HashMap<Integer, List<MyLine>> verticalMap = new HashMap<>();

    /// Constant for color limit to by count as black.
    private static final int THRESHOLD_COLOR = 85;
    /// Constant for allow length of finding lines.
    private static final int ALLOW_TEMPORARY_LENGTH = 10;

    /**
     * Find horizontal and vertical lines. Work only with grayscale picture changed with edge detection method.
     * @param picture where the lines are finding. Picture must be in grayscale.
     */
    public void findLines(Mat picture){

        long nowTime  = System.nanoTime();//TODO profiling

        int height = picture.rows();
        int width = picture.cols();

        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                if(picture.get(y, x)[0] > THRESHOLD_COLOR){
                    Coordinate coordinate = new Coordinate(x, y);
                    addDot(y, this.horizontalMap, HorizontalLine.createLine(coordinate, coordinate));
                    addDot(x, this.verticalMap, VerticalLine.createLine(coordinate, coordinate));
                }
            }
        }

        for(List<MyLine> lines : this.horizontalMap.values()){
            for(MyLine line : lines){
                if(line.length() >= ALLOW_TEMPORARY_LENGTH){
                    addLine(line, this.horizontalLines);
                }
            }
        }
        for(List<MyLine> lines : this.verticalMap.values()){
            for(MyLine line : lines){
                if(line.length() >= ALLOW_TEMPORARY_LENGTH){
                    addLine(line, this.verticalLines);
                }
            }
        }
        this.horizontalMap.clear();
        this.verticalMap.clear();

        double celkovyCas = (System.nanoTime() - nowTime) / 1000000000.0;//TODO profiling
        System.out.printf("NAJDENIE CIAR: %.4f sekund\n", celkovyCas);//TODO profiling

    }

    /**
     * Extend lines in map by black dot. The key in map is position in row if it is dot for vertical lines
     * and column if it is for horizontal lines.
     * @param key position in row for vertical lines or column for horizontal lines
     * @param lineMap horizontal or vertical map with lines.
     * @param newLine vertical or horizontal line representing black dot. 
     */
    private void addDot(int key, HashMap<Integer, List<MyLine>> lineMap, MyLine newLine){
        List<MyLine> lineList = lineMap.get(key);
        if(lineList == null){
            lineList = new ArrayList<>();
            lineMap.put(key, lineList);
        }
        boolean extendFlag = false;
        for (MyLine myLine : lineList) {
            if(myLine.extendByOne(newLine.getStartPoint())){
                extendFlag = true;
            }
        }
        if(!extendFlag){
            lineList.add(newLine);
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
        System.out.println("----------------------------------------------------------");//TODO profiling
        System.out.println("Pocet ciar pred odstraneni kratkych ciar.");//TODO profiling
        System.out.println("Horizontalne ciary ["+horizontalLines.size()+"]");//TODO profiling
        System.out.println("Vertikalne ciary ["+verticalLines.size()+"]");//TODO profiling
        System.out.println("Pocet ciar po odstraneni kratkych ciar.");//TODO profiling

        deleteShortLines(this.horizontalLines, width/3);
        deleteShortLines(this.verticalLines, height/5);

        System.out.println("Horizontalne ciary ["+horizontalLines.size()+"]");//TODO profiling
        System.out.println("Vertikalne ciary ["+verticalLines.size()+"]");//TODO profiling
        System.out.println("----------------------------------------------------------");//TODO profiling
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
