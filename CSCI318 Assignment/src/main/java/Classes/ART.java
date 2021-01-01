package Classes;

import java.util.ArrayList;

public class ART extends TestAlgorithms {

    public ART(double failureRate, int panelSize, int coordDiameter) {
        super(failureRate, panelSize, coordDiameter);
    }

    @Override
    public Coordinate generateCoordinate() {
        double furthestDistance = 0; // Instantiate double to MIN

        ArrayList<Coordinate> compare = new ArrayList<>();
        ArrayList<Double> leastDistance = new ArrayList<>();

        Coordinate furthestCoordinate = null;

        for (int i = 0; i < 10; i++) {
            double x = randomGenerator() * panelSize;
            double y = randomGenerator() * panelSize;
            compare.add(new Coordinate(x, y, coordDiameter));
            fixCoordDiameter(compare.get(compare.size() - 1));
            leastDistance.add(Double.MAX_VALUE);
        }

        for (Coordinate c1 : coords) {
            for (int i = 0; i < leastDistance.size(); i++) {
                if (getDistance(c1, compare.get(i)) < leastDistance.get(i)) // Get the minimum distance for each randomly generated point
                {
                    leastDistance.set(i, getDistance(c1, compare.get(i)));
                }
            }
        }

        for (int i = 0; i < leastDistance.size(); i++) {
            if (leastDistance.get(i) > furthestDistance) {
                furthestDistance = leastDistance.get(i);
                furthestCoordinate = compare.get(i);
            }
        }

        try {
            coords.add(furthestCoordinate);
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        return coords.get(coords.size() - 1);
    }

    private double getDistance(Coordinate coord1, Coordinate coord2) {
        double x1 = coord1.getX();
        double y1 = coord1.getY();

        double x2 = coord2.getX();
        double y2 = coord2.getY();

        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

        return distance;
    }
}
