public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
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
}
