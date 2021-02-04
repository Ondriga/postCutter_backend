package postCutter.geometricShapes.line;

import postCutter.geometricShapes.Coordinate;

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

    private int checkHeight(MyLine line){
        if(this.getStartPoint().getY() - ALLOW_POSITION_MOVE <= line.getStartPoint().getY()){
            if(this.getStartPoint().getY() + ALLOW_POSITION_MOVE >= line.getStartPoint().getY()){
                return 0;
            }
            return 1;
        }
        return -1;
    }

    private boolean lineCover(MyLine line){
        if(this.getStartPoint().getX() - ALLOW_EMPTY_RANGE <= line.getStartPoint().getX()
        && this.getEndPoint().getX() + ALLOW_EMPTY_RANGE >= line.getStartPoint().getX()){
            return true;
        }
        if(this.getStartPoint().getX() - ALLOW_EMPTY_RANGE <= line.getEndPoint().getX()
        && this.getEndPoint().getX() + ALLOW_EMPTY_RANGE >= line.getEndPoint().getX()){
            return true;
        }
        return false;
    }

    @Override
    public int extendByLine(MyLine line) {
        int positionFlag = checkHeight(line);
        if(positionFlag == 0){
            if(lineCover(line)){
                int y = this.getStartPoint().getY();
                if(line.length() > this.length()){
                    y = line.getStartPoint().getY();
                }
                Coordinate startPoint;
                if(line.getStartPoint().getX() < this.getStartPoint().getX()){
                    startPoint = new Coordinate(line.getStartPoint().getX(), y);
                }else{
                    startPoint = new Coordinate(this.getStartPoint().getX(), y);
                }
                this.setStartPoint(startPoint);
                Coordinate endPoint;
                if(line.getEndPoint().getX() > this.getEndPoint().getX()){
                    endPoint = new Coordinate(line.getEndPoint().getX(), y);
                }else{
                    endPoint = new Coordinate(this.getEndPoint().getX(), y);
                }
                this.setEndPoint(endPoint);
            }else{
                return -1;
            }
        }
        return positionFlag;
    }
}
