package postCutter.cutting.geometricShapes.line;

import postCutter.cutting.geometricShapes.Coordinate;

public class HorizontalLine extends MyLine implements Comparable<HorizontalLine> {
    private HorizontalLine(Coordinate startPoint, Coordinate endPoint){
        super(startPoint, endPoint);
    }

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
        if(this.getStartPoint().getY() == coordinate.getY()){
            if(this.getStartPoint().getX()-1 == coordinate.getX()){
                this.setStartPoint(coordinate);
                return true;
            }else if(this.getEndPoint().getX()+1 == coordinate.getX()){
                this.setEndPoint(coordinate);
                return true;
            }
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
}
