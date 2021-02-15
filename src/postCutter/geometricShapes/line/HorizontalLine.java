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
            return -1;
        }
        return 1;
    }

    private boolean lineCover(MyLine line1, MyLine line2){
        if(line1.getStartPoint().getX() - ALLOW_EMPTY_RANGE <= line2.getStartPoint().getX()
        && line1.getEndPoint().getX() + ALLOW_EMPTY_RANGE >= line2.getStartPoint().getX()){
            return true;
        }
        if(line1.getStartPoint().getX() - ALLOW_EMPTY_RANGE <= line2.getEndPoint().getX()
        && line1.getEndPoint().getX() + ALLOW_EMPTY_RANGE >= line2.getEndPoint().getX()){
            return true;
        }
        return false;
    }

    @Override
    public int extendByLine(MyLine line) {
        int positionFlag = checkHeight(line);
        if(positionFlag == 0){
            if(lineCover(this, line) || lineCover(line, this)){
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

    private boolean isXCorrect(int line1X, int line2X){
        return Math.abs(line1X - line2X) <= ALLOW_EMPTY_RANGE;
    }

    @Override
    public boolean isSimilar(MyLine line) {
        return isXCorrect(this.getStartPoint().getX(), line.getStartPoint().getX()) &&
        isXCorrect(this.getEndPoint().getX(), line.getEndPoint().getX());
    }
}
