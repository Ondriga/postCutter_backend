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
        if(this.getStartPoint().getY() != coordinate.getY()){
            return false;
        }
        if(this.getStartPoint().getX()-ALLOW_EMPTY_RANGE < coordinate.getX() && this.getEndPoint().getX()+ALLOW_EMPTY_RANGE > coordinate.getX()){
            if(this.getStartPoint().getX() > coordinate.getX()){
                this.setStartPoint(coordinate);
            }else if(this.getEndPoint().getX() < coordinate.getX()){
                this.setEndPoint(coordinate);
            }
            return true;
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
    public boolean extendByLine(MyLine line) {
        if(lineCover(this, line) || lineCover(line, this)){
            int y = this.getStartPoint().getY();
            if(line.length() > this.length()){  //Y value select based on length.
                y = line.getStartPoint().getY();
            }
            int x = Math.min(line.getStartPoint().getX(), this.getStartPoint().getX());
            this.setStartPoint(new Coordinate(x, y));
            x = Math.max(line.getEndPoint().getX(), this.getEndPoint().getX());
            this.setEndPoint(new Coordinate(x, y));
            return true;
        }
        return false;
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

    @Override
    public int getLevel() {
        return this.getStartPoint().getY();
    }

    @Override
    public boolean isBefore(MyLine line) {
        return this.getStartPoint().getX() < line.getStartPoint().getX();
    }
}
