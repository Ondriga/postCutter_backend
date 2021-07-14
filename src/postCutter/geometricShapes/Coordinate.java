/*
 * Source code for the backend of Bachelor thesis.
 * Coordinate class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

package postCutter.geometricShapes;

/**
 * Representing one coordinate.
 */
public class Coordinate {
    /// X value of coordinate.
    private int x;
    /// Y value of coordinate.
    private int y;

    /**
     * Constructor.
     * @param x value of coordinate.
     * @param y value of coordinate.
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x value.
     * @return x.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Getter for y value.
     * @return y.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Setter for x value.
     * @param x new x value.
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Setter for y value.
     * @param y new y value.
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * Override for equals function of the Coordinate object
     * Needed for comparing two coordinates
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){ // Test for identity
            return true;
        }
        if (!(obj instanceof Coordinate)){ // Test before casting
            return false;
        }
        Coordinate coord_obj = (Coordinate) obj; // Casting
        return (coord_obj.getX() == (this.getX()) && coord_obj.getY() == (this.getY()));
    }

    /**
     * Override for hashCode function of the Coordinate object
     */
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 42 + this.getX();
        hash = hash * 42 + this.getY();
        return hash;
    }

    /**
     * Return string representation of this coordinate. The format is '[x, y]',
     * where x is x value and y is y value.
     */
    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }
}
