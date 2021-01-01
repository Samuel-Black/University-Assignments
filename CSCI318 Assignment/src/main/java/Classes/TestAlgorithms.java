package Classes;

import Program.CSCI318;
import java.util.ArrayList;

// Testing is the parent class for both algorithms.
public abstract class TestAlgorithms {

    int panelSize;  // Size of the testing area (e.g 350px x 350px)
    protected int coordDiameter;
    FailureRegion fRegion;
    protected ArrayList<Coordinate> coords;

    public TestAlgorithms(double failureRate, int panelSize, int coordDiameter) {
        generateFailureRegion(failureRate);
        this.panelSize = panelSize;
        this.coordDiameter = coordDiameter;
        coords = new ArrayList<>();
    }

    public ArrayList<Coordinate> getCoordinates() {
        return this.coords;
    }

    public void clearCoordinates() {
        coords.clear();
    }

    public void setFailureRadius(double rad) {
        fRegion.setFailureRadius(rad);
    }

    public void fixFailureZoneCoordinates() {
        fRegion.fixFailureZoneCoordinates(panelSize);
    }

    public double getFailureRadius() {
        return fRegion.getFailureRadius();
    }

    public double getFailureX() {
        return fRegion.getX();
    }

    public double getFailureY() {
        return fRegion.getY();
    }

    public double getPanelSize() {
        return this.panelSize;
    }

    public double getCoordDiameter() {
        return this.coordDiameter;
    }

    public Coordinate generateCoordinate() {
        double x = randomGenerator() * panelSize;
        double y = randomGenerator() * panelSize;

        coords.add(new Coordinate(x, y, coordDiameter));
        fixCoordDiameter(coords.get(coords.size() - 1));

        return coords.get(coords.size() - 1);
    }

    public void fixCoordDiameter(Coordinate coord) // Stop the x and y coordinates generated from going outside the JPanel
    {
        if (coord.getX() < 2) { // JPanel seems to cut things off by about 2 pixels. Sized at 348 but actual size around 348, maybe because of border.
            coord.setX(2);
        }
        if (coord.getX() > (panelSize - coordDiameter - 2)) {
            coord.setX(panelSize - coordDiameter - 2);
        }
        if ((coord.getY()) < 2) {
            coord.setY(2);
        }
        if (coord.getY() > (panelSize - coordDiameter - 2)) {
            coord.setY(panelSize - coordDiameter - 2);
        }
    }

    public boolean hasFailed() {
        return fRegion.hasFailed(coords.get(coords.size() - 1));
    }

    public void generateFailureRegion(double failureRate) {
        double x = randomGenerator() * panelSize;
        double y = randomGenerator() * panelSize;
        fRegion = new FailureRegion(x, y, failureRate);
        fRegion.fixFailureZoneCoordinates(panelSize);
    }

    public void setFailureRegion(double x, double y, double failureRate) {
        fRegion = new FailureRegion(x, y, failureRate);
        fRegion.fixFailureZoneCoordinates(panelSize);
    }

    static double randomGenerator() {
        return CSCI318.generator.nextDouble();
    }
}
