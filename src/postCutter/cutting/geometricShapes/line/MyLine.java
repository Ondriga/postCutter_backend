package postCutter.cutting.geometricShapes.line;

import postCutter.cutting.geometricShapes.Coordinate;

public abstract class MyLine {
    private Coordinate startPoint;
    private Coordinate endPoint;

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
}
