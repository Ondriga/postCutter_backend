package line;
public class MyLine {
    private Coordinate startPoint;
    private Coordinate endPoint;

    private MyLine(Coordinate startPoint, Coordinate endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public static MyLine createLine(Coordinate one, Coordinate two){
        if(one.getX() == two.getX()){
            if(one.getY() < two.getY()){
                return new MyLine(one, two);
            }
            return new MyLine(two, one);
        }else if(one.getY() == two.getY()){
            if(one.getX() < two.getX()){
                return new MyLine(one, two);
            }
            return new MyLine(two, one);
        }
        return null;
    }

    public Coordinate getStartPoint(){
        return this.startPoint;
    }

    public Coordinate getEndPoint(){
        return this.endPoint;
    }

    public int length(){
        if(this.startPoint.getX() == this.endPoint.getX()){
            return (this.endPoint.getY() - this.startPoint.getY() + 1);
        }else{
            return (this.endPoint.getX() - this.startPoint.getX() + 1);
        }
    }

    public boolean extendByOne(Coordinate coordinate){
        if(this.startPoint.getX() == this.endPoint.getX()){
            if(this.startPoint.getX() != coordinate.getX()){
                return false;
            }
            if((this.startPoint.getY() - 1) == coordinate.getY()){
                this.startPoint = coordinate;
                return true;
            }
            if((this.endPoint.getY() + 1) == coordinate.getY()){
                this.endPoint = coordinate;
                return true;
            }
        }else{
            if(this.startPoint.getY() != coordinate.getY()){
                return false;
            }
            if((this.startPoint.getX() - 1) == coordinate.getX()){
                this.startPoint = coordinate;
                return true;
            }
            if((this.endPoint.getX() + 1) == coordinate.getX()){
                this.endPoint = coordinate;
                return true;
            }
        }
        return false;
    }
}
