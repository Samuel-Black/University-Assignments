package GUI;

import Classes.ART;
import Classes.Coordinate;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class ARTPanel extends JPanel {

    double failX;
    double failY;
    ART adaptiveRandomTest;

    public ARTPanel(Dimension size, ART adapt) {
        this.setSize(size);
        this.adaptiveRandomTest = adapt;
        this.setVisible(true);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.black));
    }

    public void repaintART(double failX, double failY, Classes.ART adaptiveRandomTest) {
        this.failX = failX;
        this.failY = failY;
        this.adaptiveRandomTest = adaptiveRandomTest;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Paint Failure Area
        g2d.setPaint(Colors.darkRed);
        Shape fRegion = new Rectangle2D.Double(failX, failY, adaptiveRandomTest.getFailureRadius(), adaptiveRandomTest.getFailureRadius());
        g2d.fill(fRegion);
        g2d.draw(fRegion);

        // Display test coordinates
        for (Coordinate c : adaptiveRandomTest.getCoordinates()) {
            g2d.setPaint(Color.GREEN);
            Shape line = new Rectangle2D.Double(c.getX(), c.getY(), 5, 5);
            g2d.fill(line);
            g2d.setPaint(Color.BLACK);
            g2d.draw(line);
        }
        g2d.dispose();
    }
}
