package GUI;

import Classes.ART;
import Classes.RT;
import java.awt.Dimension;

public class NumericalPanel extends javax.swing.JPanel {

    // Custom Panels
    RTPanel rtPanel;
    ARTPanel artPanel;

    public NumericalPanel() {
        initComponents();
    }

    public void initializeRandomPanels(RT randomTest, ART adaptiveRandomTest) {
        // Add custom Panels
        rtPanel = new RTPanel(RTDisplayBox.getPreferredSize(), randomTest);
        RTDisplayBox.add(rtPanel);
        artPanel = new ARTPanel(ARTDisplayBox3.getPreferredSize(), adaptiveRandomTest);
        ARTDisplayBox3.add(artPanel);
    }

    public void clearPanels(double failX, double failY, Classes.RT randomTest, Classes.ART adaptiveRandomTest) {
        RTWinCountLabel.setText(0 + "");
        ARTWinCountLabel.setText(0 + "");
        tieCount.setText(0 + "");

        rtPanel.repaintRT(failX, failY, randomTest);
        artPanel.repaintART(failX, failY, adaptiveRandomTest);
    }

    public Dimension getDisplayPanelDimensions() {
        return rtPanel.getSize();
    }

    public Dimension getDisplayBoxDimensions() {
        return RTDisplayBox.getPreferredSize();
    }

    public void repaintRT(double failX, double failY, Classes.RT randomTest) {
        rtPanel.repaintRT(failX, failY, randomTest);
    }

    public void repaintART(double failX, double failY, Classes.ART adaptiveRandomTest) {
        artPanel.repaintART(failX, failY, adaptiveRandomTest);
    }

    // Setters
    public void setTies(String ties) {
        tieCount.setText(ties);
    }

    public void setRTWinCount(String wins) {
        RTWinCountLabel.setText(wins);
    }

    public void setARTWinCount(String wins) {
        ARTWinCountLabel.setText(wins);
    }

    public void setDarkMode() {
        // RT/ART Headers and panels
        this.setBackground(Colors.contentDark);
        this.rtHeaderPanel.setBackground(Colors.headerDark);
        this.artHeaderPanel.setBackground(Colors.headerDark);
        this.RTDisplayBox.setBackground(java.awt.Color.red);
        this.ARTDisplayBox3.setBackground(Colors.panelDark);
        this.resultPanel.setBackground(Colors.headerDark);
        this.scorePanel.setBackground(Colors.headerDark);
        this.rtPanel.setBackground(Colors.panelDark);
        this.artPanel.setBackground(Colors.panelDark);

        // x, y coords
        xField.setBackground(Colors.headerDark);
        yField.setBackground(Colors.headerDark);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rtHeaderPanel = new javax.swing.JPanel();
        rtTitle = new javax.swing.JLabel();
        RTDisplayBox = new javax.swing.JPanel();
        ARTDisplayBox3 = new javax.swing.JPanel();
        artHeaderPanel = new javax.swing.JPanel();
        artTitle = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        resultPanel = new javax.swing.JPanel();
        scorePanel = new javax.swing.JPanel();
        RTWinsLabel = new javax.swing.JLabel();
        RTWinCountLabel = new javax.swing.JLabel();
        ARTWinsLabel = new javax.swing.JLabel();
        ARTWinCountLabel = new javax.swing.JLabel();
        TieLabel = new javax.swing.JLabel();
        tieCount = new javax.swing.JLabel();
        xField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        yField = new javax.swing.JTextField();

        rtHeaderPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        rtTitle.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        rtTitle.setForeground(new java.awt.Color(255, 255, 255));
        rtTitle.setText("RT");

        javax.swing.GroupLayout rtHeaderPanelLayout = new javax.swing.GroupLayout(rtHeaderPanel);
        rtHeaderPanel.setLayout(rtHeaderPanelLayout);
        rtHeaderPanelLayout.setHorizontalGroup(
            rtHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtHeaderPanelLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(rtTitle)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        rtHeaderPanelLayout.setVerticalGroup(
            rtHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtHeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rtTitle)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        RTDisplayBox.setBackground(new java.awt.Color(255, 255, 255));
        RTDisplayBox.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 0, 0)));
        RTDisplayBox.setEnabled(false);
        RTDisplayBox.setPreferredSize(new java.awt.Dimension(350, 350));
        RTDisplayBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RTMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout RTDisplayBoxLayout = new javax.swing.GroupLayout(RTDisplayBox);
        RTDisplayBox.setLayout(RTDisplayBoxLayout);
        RTDisplayBoxLayout.setHorizontalGroup(
            RTDisplayBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );
        RTDisplayBoxLayout.setVerticalGroup(
            RTDisplayBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        ARTDisplayBox3.setBackground(new java.awt.Color(255, 255, 255));
        ARTDisplayBox3.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.black));
        ARTDisplayBox3.setPreferredSize(new java.awt.Dimension(350, 350));
        ARTDisplayBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ARTMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ARTDisplayBox3Layout = new javax.swing.GroupLayout(ARTDisplayBox3);
        ARTDisplayBox3.setLayout(ARTDisplayBox3Layout);
        ARTDisplayBox3Layout.setHorizontalGroup(
            ARTDisplayBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );
        ARTDisplayBox3Layout.setVerticalGroup(
            ARTDisplayBox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        artHeaderPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        artTitle.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        artTitle.setForeground(new java.awt.Color(255, 255, 255));
        artTitle.setText("ART");

        javax.swing.GroupLayout artHeaderPanelLayout = new javax.swing.GroupLayout(artHeaderPanel);
        artHeaderPanel.setLayout(artHeaderPanelLayout);
        artHeaderPanelLayout.setHorizontalGroup(
            artHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artHeaderPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(artTitle)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        artHeaderPanelLayout.setVerticalGroup(
            artHeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artHeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(artTitle)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Results");

        resultPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darcula.color1"));

        RTWinsLabel.setForeground(new java.awt.Color(255, 255, 255));
        RTWinsLabel.setText("RT Wins:");

        RTWinCountLabel.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        RTWinCountLabel.setForeground(new java.awt.Color(187, 134, 252));
        RTWinCountLabel.setText("0");

        ARTWinsLabel.setForeground(new java.awt.Color(255, 255, 255));
        ARTWinsLabel.setText("ART Wins:");

        ARTWinCountLabel.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        ARTWinCountLabel.setForeground(new java.awt.Color(187, 134, 252));
        ARTWinCountLabel.setText("0");

        TieLabel.setForeground(new java.awt.Color(255, 255, 255));
        TieLabel.setText("Ties:");

        tieCount.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        tieCount.setForeground(new java.awt.Color(187, 134, 252));
        tieCount.setText("0");

        javax.swing.GroupLayout scorePanelLayout = new javax.swing.GroupLayout(scorePanel);
        scorePanel.setLayout(scorePanelLayout);
        scorePanelLayout.setHorizontalGroup(
            scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scorePanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(scorePanelLayout.createSequentialGroup()
                        .addComponent(RTWinsLabel)
                        .addGap(26, 26, 26)
                        .addComponent(RTWinCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                    .addGroup(scorePanelLayout.createSequentialGroup()
                        .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ARTWinsLabel)
                            .addComponent(TieLabel))
                        .addGap(18, 18, 18)
                        .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ARTWinCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tieCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        scorePanelLayout.setVerticalGroup(
            scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scorePanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RTWinsLabel)
                    .addComponent(RTWinCountLabel))
                .addGap(18, 18, 18)
                .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ARTWinsLabel)
                    .addComponent(ARTWinCountLabel))
                .addGap(18, 18, 18)
                .addGroup(scorePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TieLabel)
                    .addComponent(tieCount))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(scorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(511, Short.MAX_VALUE))
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, resultPanelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(scorePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        xField.setForeground(new java.awt.Color(153, 153, 153));
        xField.setText("  x");
        xField.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Y:");

        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("X:");

        yField.setForeground(new java.awt.Color(153, 153, 153));
        yField.setText("  y");
        yField.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(514, 514, 514)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(RTDisplayBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(rtHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(artHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ARTDisplayBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(artHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ARTDisplayBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rtHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(RTDisplayBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(xField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(yField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void RTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RTMouseClicked
        xField.setText(evt.getX() + "");
        yField.setText(evt.getY() + "");
    }//GEN-LAST:event_RTMouseClicked

    private void ARTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ARTMouseClicked
        xField.setText(evt.getX() + "");
        yField.setText(evt.getY() + "");
    }//GEN-LAST:event_ARTMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ARTDisplayBox3;
    private javax.swing.JLabel ARTWinCountLabel;
    private javax.swing.JLabel ARTWinsLabel;
    public javax.swing.JPanel RTDisplayBox;
    private javax.swing.JLabel RTWinCountLabel;
    private javax.swing.JLabel RTWinsLabel;
    private javax.swing.JLabel TieLabel;
    private javax.swing.JPanel artHeaderPanel;
    private javax.swing.JLabel artTitle;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JPanel rtHeaderPanel;
    private javax.swing.JLabel rtTitle;
    private javax.swing.JPanel scorePanel;
    private javax.swing.JLabel tieCount;
    private javax.swing.JTextField xField;
    private javax.swing.JTextField yField;
    // End of variables declaration//GEN-END:variables
}
