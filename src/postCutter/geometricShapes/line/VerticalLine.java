/*
 * Source code for the backend of Bachelor thesis.
 * VerticalLine class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import postCutter.geometricShapes.Coordinate;

/**
 * Represented vertical line.
 */
public class VerticalLine extends MyLine implements Comparable<VerticalLine> {
    private VerticalLine(Coordinate startPoint, Coordinate endPoint){
        super(startPoint, endPoint);
    }

    /**
     * Static factory method for create vertical object with correct order of coordinates.
     * @param one first coordinate.
     * @param two second coordinate.
     * @return if coordinates have different y value return null, otherwise new vertical object.
     */
    public static VerticalLine createLine(Coordinate one, Coordinate two){
        if(one.getX() == two.getX()){
            if(one.getY() < two.getY()){
                return new VerticalLine(one, two);
            }
            return new VerticalLine(two, one);
        }
        return null;
    }
    
    @Override
    public boolean extendByOne(Coordinate coordinate) {
        if(this.getStartPoint().getX() != coordinate.getX()){
            return false;
        }
        if(this.getStartPoint().getY()-ALLOW_EMPTY_RANGE < coordinate.getY() && this.getEndPoint().getY()+ALLOW_EMPTY_RANGE > coordinate.getY()){
            if(this.getStartPoint().getY() > coordinate.getY()){
                this.setStartPoint(coordinate);
            }else if(this.getEndPoint().getY() < coordinate.getY()){
                this.setEndPoint(coordinate);
            }
            return true;
        }
        return false;
    }

    @Override
    public int length() {
        return this.getEndPoint().getY() - this.getStartPoint().getY() + 1;
    }

    @Override
    public int compareTo(VerticalLine o) {
        return this.getStartPoint().getX() - o.getStartPoint().getX();
    }

    /**
     * Check if lines are covering each other. X value is abandon. For compare is used allowed variation.
     * @param line1 first line.
     * @param line2 second line.
     * @return true if lines are covering, otherwise false.
     */
    private boolean lineCover(MyLine line1, MyLine line2){
        if(line1.getStartPoint().getY() - ALLOW_EMPTY_RANGE <= line2.getStartPoint().getY()
        && line1.getEndPoint().getY() + ALLOW_EMPTY_RANGE >= line2.getStartPoint().getY()){
            return true;
        }
        if(line1.getStartPoint().getY() - ALLOW_EMPTY_RANGE <= line2.getEndPoint().getY()
        && line1.getEndPoint().getY() + ALLOW_EMPTY_RANGE >= line2.getEndPoint().getY()){
            return true;
        }
        return false;
    }

    @Override
    public boolean extendByLine(MyLine line) {
        if(lineCover(this, line) || lineCover(line, this)){
            int x = this.getStartPoint().getX();
            if(line.length() > this.length()){  //X value select based on length.
                x = line.getStartPoint().getX();
            }
            int y = Math.min(line.getStartPoint().getY(), this.getStartPoint().getY());
            this.setStartPoint(new Coordinate(x, y));
            y = Math.max(line.getEndPoint().getY(), this.getEndPoint().getY());
            this.setEndPoint(new Coordinate(x, y));
            return true;
        }
        return false;
    }

    /**
     * Check if y values of 2 lines are similar. For compare is used allowed variation.
     * @param line1Y y value of first line.
     * @param line2Y y value of second line.
     * @return true if x values are similar, otherwise false.
     */
    private boolean isYCorrect(int line1Y, int line2Y){
        return Math.abs(line1Y - line2Y) <= ALLOW_EMPTY_RANGE;
    }

    @Override
    public boolean isSimilar(MyLine line) {
        return isYCorrect(this.getStartPoint().getY(), line.getStartPoint().getY()) &&
        isYCorrect(this.getEndPoint().getY(), line.getEndPoint().getY());
    }

    @Override
    public int getLevel() {
        return this.getStartPoint().getX();
    }

    @Override
    public boolean isBefore(MyLine line) {
        return this.getStartPoint().getY() < line.getStartPoint().getY();
    }
}
