public class MyLine {
    private final Coordinate startPoint;
    private final Coordinate endPoint;

    public MyLine(Coordinate startPoint, Coordinate endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Coordinate getStartPoint(){
        return this.startPoint;
    }

    public Coordinate getEndPoint(){
        return this.endPoint;
    }

    public int length(){
        if(this.startPoint.getX() == this.endPoint.getX()){
            return Math.abs(this.startPoint.getY() - this.endPoint.getY());
        }else{
            return Math.abs(this.startPoint.getX() - this.endPoint.getX());
        }
    }
}
