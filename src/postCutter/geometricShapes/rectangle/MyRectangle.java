/*
 * Source code for the backend of Bachelor thesis.
 * MyRectangle class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes.rectangle;

import postCutter.geometricShapes.Coordinate;

/**
 * Representing rectangle.
 */
public class MyRectangle {
    /// Top left coordinate of rectangle.
    private Coordinate cornerA;
    /// Bottom right coordinate fo rectangle.
    private Coordinate cornerB;

    /**
     * Constructor.
     * @param cornerA top left coordinate of rectangle.
     * @param cornerB bottom right coordinate of rectangle.
     */
    private MyRectangle (Coordinate cornerA, Coordinate cornerB){
        this.cornerA = cornerA;
        this.cornerB = cornerB;
    }

    /**
     * Static refractor for generate new rectangles.
     * @param cornerA first coordinate.
     * @param cornerB second coordinate.
     * @return null if cornerA x,y value isn`t smaller than x,y value of cornerB, otherwise new MyRectangle object.
     */
    public static MyRectangle createRectangle(Coordinate cornerA, Coordinate cornerB){
        if(cornerA.getX() < cornerB.getX() && cornerA.getY() < cornerB.getY()){
            return new MyRectangle(cornerA, cornerB);
        }
        return null;
    }

    /**
     * Getter for coordinate of corner A.
     * @return cornerA
     */
    public Coordinate getCornerA(){
        return cornerA;
    }

    /**
     * Getter for coordinate of corner B.
     * @return cornerB
     */
    public Coordinate getCornerB(){
        return cornerB;
    }

    /**
     * Setter for cornerA.
     * @param cornerA new cornerA.
     */
    public void setCornerA(Coordinate cornerA){
        this.cornerA = cornerA;
    }

    /**
     * Setter for cornerB.
     * @param cornerB new cornerB.
     */
    public void setCornerB(Coordinate cornerB){
        this.cornerB = cornerB;
    }

    /**
     * Getter for width of rectangle.
     * @return rectangle width
     */
    public int getWidth(){
        return cornerB.getX() - cornerA.getX() + 1;
    }

    /**
     * Getter for height of rectangle.
     * @return rectangle height
     */
    public int getHeight(){
        return cornerB.getY() - cornerA.getY() + 1;
    }

    /**
     * Return string representation of this rectangle. The format is 'corner A[x1, y1] corner B[x2, y2]',
     * where A is coordinate of top left corner and B is coordinate of bottom right corner.
     */
    @Override
    public String toString() {
        return "corner A" + this.cornerA + " corner B" + this.cornerB;
    }
}
