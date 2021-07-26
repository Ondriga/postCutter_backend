/*
 * Source code for the backend of Bachelor thesis.
 * HorizontalLine class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import postCutter.geometricShapes.Coordinate;

/**
 * Represented horizontal line.
 */
public class HorizontalLine extends MyLine implements Comparable<HorizontalLine> {
    private HorizontalLine(Coordinate startPoint, Coordinate endPoint){
        super(startPoint, endPoint);
    }

    /**
     * Static factory method for create horizontalLine object with correct order of coordinates.
     * @param one first coordinate.
     * @param two second coordinate.
     * @return if coordinates have different y value return null, otherwise new horizontalLine object.
     */
    public static HorizontalLine createLine(Coordinate one, Coordinate two){
        if(one.getY() == two.getY()){
            if(one.getX() < two.getX()){
                return new HorizontalLine(one, two);
            }
            return new HorizontalLine(two, one);
        }
        return null;
    }

    @Override
    public boolean extendByOne(Coordinate coordinate) {
        if(this.getStartPoint().getY() == coordinate.getY()){
            if(this.getStartPoint().getX()-1 == coordinate.getX()){
                this.setStartPoint(coordinate);
                return true;
            }else if(this.getEndPoint().getX()+1 == coordinate.getX()){
                this.setEndPoint(coordinate);
                return true;
            }
            if(this.getStartPoint().getX() <= coordinate.getX() &&
            this.getEndPoint().getX() >= coordinate.getX()){
                return true;
            }
        }
        return false;
    }

    @Override
    public int length() {
        return this.getEndPoint().getX() - this.getStartPoint().getX() + 1;
    }

    @Override
    public int compareTo(HorizontalLine o) {
        return this.getStartPoint().getY() - o.getStartPoint().getY();
    }

    /**
     * Get information, if the line have same y value as this object. For compare is used allowed variation.
     * @param line for compare.
     * @return 0 if the y value of line is like y value of this object. -1 if y value is lower. 1 if y value is bigger.
     */
    private int checkHeight(MyLine line){
        if(this.getStartPoint().getY() - ALLOW_POSITION_MOVE <= line.getStartPoint().getY()){
            if(this.getStartPoint().getY() + ALLOW_POSITION_MOVE >= line.getStartPoint().getY()){
                return 0;
            }
            return -1;
        }
        return 1;
    }

    /**
     * Check if lines are covering each other. Y value is abandon. For compare is used allowed variation.
     * @param line1 first line.
     * @param line2 second line.
     * @return true if lines are covering, otherwise false.
     */
    private boolean lineCover(MyLine line1, MyLine line2){
        if(line1.getStartPoint().getX() - ALLOW_EMPTY_RANGE <= line2.getStartPoint().getX()
        && line1.getEndPoint().getX() + ALLOW_EMPTY_RANGE >= line2.getStartPoint().getX()){
            return true;
        }
        if(line1.getStartPoint().getX() - ALLOW_EMPTY_RANGE <= line2.getEndPoint().getX()
        && line1.getEndPoint().getX() + ALLOW_EMPTY_RANGE >= line2.getEndPoint().getX()){
            return true;
        }
        return false;
    }

    @Override
    public int extendByLine(MyLine line) {
        int positionFlag = checkHeight(line);
        if(positionFlag == 0){
            if(lineCover(this, line) || lineCover(line, this)){
                int y = this.getStartPoint().getY();
                if(line.length() > this.length()){  //Y value select based on length.
                    y = line.getStartPoint().getY();
                }
                int x = Math.min(line.getStartPoint().getX(), this.getStartPoint().getX());
                this.setStartPoint(new Coordinate(x, y));
                x = Math.max(line.getEndPoint().getX(), this.getEndPoint().getX());
                this.setEndPoint(new Coordinate(x, y));
            }else{
                return -1;
            }
        }
        return positionFlag;
    }

    /**
     * Check if x values of 2 lines are similar. For compare is used allowed variation.
     * @param line1X x value of first line.
     * @param line2X x value of second line.
     * @return true if x values are similar, otherwise false.
     */
    private boolean isXCorrect(int line1X, int line2X){
        return Math.abs(line1X - line2X) <= ALLOW_EMPTY_RANGE;
    }

    @Override
    public boolean isSimilar(MyLine line) {
        return isXCorrect(this.getStartPoint().getX(), line.getStartPoint().getX()) &&
        isXCorrect(this.getEndPoint().getX(), line.getEndPoint().getX());
    }
}
