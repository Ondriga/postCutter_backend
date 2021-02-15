package postCutter.geometricShapes.line;

import postCutter.geometricShapes.Coordinate;

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

    private int checkWidth(MyLine line){
        if(this.getStartPoint().getX() - ALLOW_POSITION_MOVE <= line.getStartPoint().getX()){
            if(this.getStartPoint().getX() + ALLOW_POSITION_MOVE >= line.getStartPoint().getX()){
                return 0;
            }
            return -1;
        }
        return 1;
    }

    private boolean lineCover(MyLine line1, MyLine line2){
        if(line1.getStartPoint().getY() - ALLOW_EMPTY_RANGE <= line2.getStartPoint().getY()
        && line1.getEndPoint().getY() + ALLOW_EMPTY_RANGE >= line2.getStartPoint().getY()){
            return true;
        }
        if(line1.getStartPoint().getY() - ALLOW_EMPTY_RANGE <= line2.getEndPoint().getY()
        && line1.getEndPoint().getY() + ALLOW_EMPTY_RANGE >= line2.getEndPoint().getY()){
            return true;
        }
        return false;
    }

    @Override
    public int extendByLine(MyLine line) {
        int positionFlag = checkWidth(line);
        if(positionFlag == 0){
            if(lineCover(this, line) || lineCover(line, this)){
                int x = this.getStartPoint().getX();
                if(line.length() > this.length()){
                    x = line.getStartPoint().getX();
                }
                Coordinate startPoint;
                if(line.getStartPoint().getY() < this.getStartPoint().getY()){
                    startPoint = new Coordinate(x, line.getStartPoint().getY());
                }else{
                    startPoint = new Coordinate(x, this.getStartPoint().getY());
                }
                this.setStartPoint(startPoint);
                Coordinate endPoint;
                if(line.getEndPoint().getY() > this.getEndPoint().getY()){
                    endPoint = new Coordinate(x, line.getEndPoint().getY());
                }else{
                    endPoint = new Coordinate(x, this.getEndPoint().getY());
                }
                this.setEndPoint(endPoint);
            }else{
                return -1;
            }
        }
        return positionFlag;
    }

    private boolean isYCorrect(int line1Y, int line2Y){
        return Math.abs(line1Y - line2Y) <= ALLOW_EMPTY_RANGE;
    }

    @Override
    public boolean isSimilar(MyLine line) {
        return isYCorrect(this.getStartPoint().getY(), line.getStartPoint().getY()) &&
        isYCorrect(this.getEndPoint().getY(), line.getEndPoint().getY());
    }
}
