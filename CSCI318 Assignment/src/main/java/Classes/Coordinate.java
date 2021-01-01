package Classes;

// This class represents each coordinate object created on the test frame.
// These are stored in a list to track all plotted tests
public class Coordinate {

    protected double x;
    protected double y;
    protected double diameter; // Added diameter to Coordinate class

    public Coordinate(double x, double y, double diameter) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
