/*
 * Source code for the backend of Bachelor thesis.
 * MyLine class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.line;

import postCutter.geometricShapes.Coordinate;

/**
 * Representing line.
 * 
 *                        A
 *                        +
 *   A+-----+B     or     |
 *                        +
 *                        B
 */
public abstract class MyLine {
    /// First coordinate of line.
    private Coordinate startPoint;
    /// Second coordinate of line.
    private Coordinate endPoint;
    /// Constant for allow empty space size in line.
    protected static final int ALLOW_EMPTY_RANGE = 5;

    /**
     * Constructor.
     * @param startPoint first coordinate.
     * @param endPoint second coordinate.
     */
    protected MyLine(Coordinate startPoint, Coordinate endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Getter for first coordinate.
     * @return start of line.
     */
    public Coordinate getStartPoint(){
        return this.startPoint;
    }

    /**
     * Getter for second coordinate.
     * @return end of line.
     */
    public Coordinate getEndPoint(){
        return this.endPoint;
    }

    /**
     * Setter for start coordinate of line.
     * @param startPoint new start of line.
     */
    protected void setStartPoint(Coordinate startPoint){
        this.startPoint = startPoint;
    }

    /**
     * Setter for end coordinate of line.
     * @param endPoint new end of line.
     */
    protected void setEndPoint(Coordinate endPoint){
        this.endPoint = endPoint;
    }

    /**
     * Length of line in number of pixel.
     * @return length of line.
     */
    public abstract int length();

    /**
     * Try extend line by one dot.
     * @param coordinate position of dot.
     * @return true if extend was successful or dot is on line, otherwise false.
     */
    public abstract boolean extendByOne(Coordinate coordinate);

    /**
     * Try merge line with other. Extend is successful if lines are covering. Extend do not check lines levels.
     * @param line for merge.
     * @return true if lines are covering otherwise false.
     */
    public abstract boolean extendByLine(MyLine line);

    /**
     * Check if line have similar length and position in x way if they are horizontal lines and y way if they are vertical.
     * @param line for check with this line.
     * @return true if they are similar, otherwise false.
     */
    public abstract boolean isSimilar(MyLine line);

    /**
     * Level of line is x value in horizontal line and y value in vertical line.
     * @return level of line.
     */
    public abstract int getLevel();

    /**
     * Compare x value in start points of horizontal lines and y value of vertical lines.
     * @param line for compare.
     * @return true if this line have x/y value smaller than line.
     */
    public abstract boolean isBefore(MyLine line);

    /**
     * Override for equals function of the MyLine object
     * Needed for comparing two lines
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){ // Test for identity
            return true;
        }
        if (!(obj instanceof Coordinate)){ // Test before casting
            return false;
        }
        MyLine line_obj = (MyLine) obj; // Casting
        return (line_obj.getStartPoint().equals(this.getStartPoint()) && line_obj.getEndPoint().equals(this.getEndPoint()));
    }

    /**
     * Override for hashCode function of the MyLine object
     */
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 420 + this.getStartPoint().hashCode();
        hash = hash * 42 + this.getEndPoint().hashCode();
        return hash;
    }

    /**
     * Return string representation of this line. The format is 'A[x1, y1] B[x2, y2]',
     * where A is coordinate of start point and B is coordinate of end point.
     */
    @Override
    public String toString() {
        return "A" + this.startPoint + " B" + this.endPoint;
    }
}
