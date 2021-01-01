package GUI;

import Classes.Coordinate;
import Classes.RT;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

// Override the default JPanel
public class RTPanel extends JPanel {

    double failX;
    double failY;
    RT randomTest;

    public RTPanel(Dimension size, RT randomTest) {
        this.setSize(size);
        this.randomTest = randomTest;
        this.setVisible(true);
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.black));
    }

    public void repaintRT(double failX, double failY, RT randomTest) {
        this.failX = failX;
        this.failY = failY;
        this.randomTest = randomTest;
        System.out.println(randomTest.getFailureRadius());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Paint Failure Area
        g2d.setPaint(Colors.darkRed);
        Shape fRegion = new Rectangle2D.Double(failX, failY, randomTest.getFailureRadius(), randomTest.getFailureRadius());
        g2d.fill(fRegion);
        g2d.draw(fRegion);

        // Display test coordinates
        for (Coordinate c : randomTest.getCoordinates()) {
            g2d.setPaint(Color.GREEN);
            Shape line = new Rectangle2D.Double(c.getX(), c.getY(), 5, 5);
            g2d.fill(line);
            g2d.setPaint(Color.BLACK);
            g2d.draw(line);
        }
        g2d.dispose();
    }
}
