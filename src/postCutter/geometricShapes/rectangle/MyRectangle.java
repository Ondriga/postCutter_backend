package postCutter.geometricShapes.rectangle;

import postCutter.geometricShapes.Coordinate;

public class MyRectangle {
    private Coordinate cornerA;
    private Coordinate cornerB;

    private MyRectangle (Coordinate cornerA, Coordinate cornerB){
        this.cornerA = cornerA;
        this.cornerB = cornerB;
    }

    public static MyRectangle createRectangle(Coordinate cornerA, Coordinate cornerB){
        if(cornerA.getX() < cornerB.getX() && cornerA.getY() < cornerB.getY()){
            return new MyRectangle(cornerA, cornerB);
        }
        return null;
    }

    public Coordinate getCornerA(){
        return cornerA;
    }

    public Coordinate getCornerB(){
        return cornerB;
    }
}
