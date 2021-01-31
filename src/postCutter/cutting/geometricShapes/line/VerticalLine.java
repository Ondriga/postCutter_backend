package postCutter.cutting.geometricShapes.line;

import postCutter.cutting.geometricShapes.Coordinate;

public class VerticalLine extends MyLine implements Comparable<VerticalLine> {
    private VerticalLine(Coordinate startPoint, Coordinate endPoint){
        super(startPoint, endPoint);
    }

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
        if(this.getStartPoint().getX() == coordinate.getX()){
            if(this.getStartPoint().getY()-1 == coordinate.getY()){
                this.setStartPoint(coordinate);
                return true;
            }else if(this.getEndPoint().getY()+1 == coordinate.getY()){
                this.setEndPoint(coordinate);
                return true;
            }
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
}
