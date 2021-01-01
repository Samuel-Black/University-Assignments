package Classes;

// Our class that spawns a failure region and also has functions to
//  test whether or not a genereated coordinate falls inside that region
public class FailureRegion extends Coordinate {

    private double regionDiameter;

    public FailureRegion(double x, double y, double diameter) {
        super(x, y, diameter);
        regionDiameter = diameter;
    }

    public double getFailureRadius() {
        return regionDiameter;
    }

    public void setFailureRadius(double rad) {
        regionDiameter = rad;
    }

    public void fixFailureZoneCoordinates(double panelSize) {
        if ((this.x + regionDiameter) > (panelSize - 2)) {
            this.x = (panelSize - regionDiameter - 2);
        }
        if ((this.y + regionDiameter) > (panelSize - 2)) {
            this.y = (panelSize - regionDiameter - 2);
        }
    }

    public boolean hasFailed(Coordinate coord) {
        if (testX(coord) && testY(coord)) {
            return true;
        }
        return false;
    }

    public boolean testX(Coordinate coord) {
        if ((coord.getX() + coord.getDiameter()) >= this.x && coord.getX() <= (this.x + regionDiameter)) // Point is placed in JPanel at top left, so test if coord + diameter is >= failure x
        {
            return true;
        }
        return false;
    }

    public boolean testY(Coordinate coord) {
        if ((coord.getY() + coord.getDiameter()) >= this.y && coord.getY() <= (this.y + regionDiameter)) // Point is placed in JPanel at top left, so test if coord + diameter is >= failure y
        {
            return true;
        }
        return false;
    }
}
