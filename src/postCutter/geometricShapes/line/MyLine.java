package postCutter.geometricShapes.line;

import postCutter.geometricShapes.Coordinate;

public abstract class MyLine {
    private Coordinate startPoint;
    private Coordinate endPoint;
    protected static final int ALLOW_POSITION_MOVE = 2;
    protected static final int ALLOW_EMPTY_RANGE = 5;

    protected MyLine(Coordinate startPoint, Coordinate endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Coordinate getStartPoint(){
        return this.startPoint;
    }

    public Coordinate getEndPoint(){
        return this.endPoint;
    }

    protected void setStartPoint(Coordinate startPoint){
        this.startPoint = startPoint;
    }

    protected void setEndPoint(Coordinate endPoint){
        this.endPoint = endPoint;
    }

    public abstract int length();

    public abstract boolean extendByOne(Coordinate coordinate);

    public abstract int extendByLine(MyLine line);

    public abstract boolean isSimilar(MyLine line);

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
}
