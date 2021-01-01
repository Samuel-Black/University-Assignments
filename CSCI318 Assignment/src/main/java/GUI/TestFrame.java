package GUI;

import Classes.GUIController;
import Classes.NNGUIController;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TestFrame extends javax.swing.JFrame {

    GUIController gControl;
    NNGUIController nngControl;
    // Card Layout for main display
    JPanel cards;
    NumericalPanel nPanel;
    NonNumericalPanel nonNPanel;
    final static String NUMERICALPANEL = "Card with numerical tools";
    final static String NONNUMERICALPANEL = "Card with non-numerical tools";

    public TestFrame() {
        initComponents();
        this.setVisible(true);
        setLocationRelativeTo(null);

        // Set GUI text
        introTextLabel.setText("<html><p> A simple representation of the difference between 'Random Testing' (RT) and 'Adaptive Random Testing' (ART).</p><br/> <p>  Simply adjust the parameters below to run different test scenarios. </p><br/> <p>  Alternatively, to test your program using random class, select: 'Non-numerical Input'. </p> </html>");

        // Setup main display Cards
        populateMainDisplayCards();

        // Initialize GUI controller (must happen after populateCards())
        gControl = new GUIController(this);
        nngControl = new NNGUIController(nonNPanel);

        // Initialize random Panels
        nPanel.initializeRandomPanels(gControl.getRT(), gControl.getART());

        setDarkMode();
        handleClose();
    }

    // -- Getters --------------------------------------------------------------
    public int getSpeedSliderValue() {
        return speedSlider.getValue();
    }

    public int getRunTimeSlider() {
        return runTimeSlider.getValue();
    }

    public String getFailureRateInput() {
        return failureRateInput.getText();
    }

    public Dimension getDisplayPanelDimensions() {
        return mainDisplayPanel.getSize();
    }

    public Dimension getDisplayBoxDimensions() {
        return nPanel.getDisplayBoxDimensions();
    }

    // -- Setters --------------------------------------------------------------
    public void setTies(String ties) {
        nPanel.setTies(ties);
    }

    public void setRTWinCount(String wins) {
        nPanel.setRTWinCount(wins);
    }

    public void setARTWinCount(String wins) {
        nPanel.setARTWinCount(wins);
    }

    // -- UI Mutators ----------------------------------------------------------
    public void disableStartButton() {
        startTest.setEnabled(false);
    }

    public void enableStartButton() {
        startTest.setEnabled(true);
    }

    public void repaintRT(double failX, double failY, Classes.RT randomTest) {
        nPanel.repaintRT(failX, failY, randomTest);
    }

    public void repaintART(double failX, double failY, Classes.ART adaptiveRandomTest) {
        nPanel.repaintART(failX, failY, adaptiveRandomTest);
    }

    // -- Frame Building -------------------------------------------------------
    public void populateMainDisplayCards() {
        // Create cards and Card Panel

        cards = new JPanel(new CardLayout());
        cards.setSize(mainDisplayPanel.getSize());
        mainDisplayPanel.add(cards);
        nPanel = new NumericalPanel();
        nPanel.setSize(cards.getSize());
        nonNPanel = new NonNumericalPanel();
        nonNPanel.setSize(cards.getSize());

        mainDisplayPanel.add(cards);
        cards.setBackground(Colors.contentDark);

        cards.add(nPanel, NUMERICALPANEL);
        cards.add(nonNPanel, NONNUMERICALPANEL);

        CardLayout cl = (CardLayout) (cards.getLayout());
        cl.show(cards, NUMERICALPANEL);
    }

    private void setDarkMode() {
        // Header panel background
        this.getContentPane().setBackground(Colors.backgroundDark);
        this.titlePanel.setBackground(Colors.headerDark);
        this.inputPanelWest.setBackground(Colors.backgroundDark);
        this.inputPanel1.setBackground(Colors.backgroundDark);
        this.cards.setBackground(Colors.headerDark);
        this.mainDisplayPanel.setBackground(Colors.contentDark);

        // Failure rate text field
        this.failureRateInput.setBackground(Colors.inputDark);
        this.failureRateInput.setForeground(Color.WHITE);
        this.failureRateInput.setCaretColor(Color.WHITE);
        this.failureRateInput.setSelectedTextColor(Color.WHITE);

        // Controls 
        this.startTest.setBackground(Colors.headerDark);
        this.numericalCheckBox.setBackground(Colors.backgroundDark);
        this.speedSlider.setBackground(Colors.backgroundDark);
        this.runTimeSlider.setBackground(Colors.backgroundDark);

        // Sub Panels
        nonNPanel.setDarkMode();
        nPanel.setDarkMode();
    }

    // Universal popup box
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, "<html><h3><font color='white'>" + infoMessage + "</font></h3></html>", "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    // Input popup boxes
    public static String inputBox(String inputMessage) {
        return JOptionPane.showInputDialog(null, inputMessage, "Input Box", JOptionPane.PLAIN_MESSAGE, null, null, "input").toString();
    }

    //Error popup boxes
    public static void errorBox(String message) {
        JOptionPane.showMessageDialog(null, message, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titlePanel = new javax.swing.JPanel();
        homeTitle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        homeTitle1 = new javax.swing.JLabel();
        inputPanelWest = new javax.swing.JPanel();
        inputPanel1 = new javax.swing.JPanel();
        failureRateInput = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        speedSliderLabel = new javax.swing.JLabel();
        runAmtLabel = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        speedSlider = new javax.swing.JSlider();
        runTimeSlider = new javax.swing.JSlider();
        runTimeLabel = new javax.swing.JLabel();
        introTextLabel = new javax.swing.JLabel();
        introHeadingLabel = new javax.swing.JLabel();
        parameterHeadingLabel = new javax.swing.JLabel();
        startTest = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        numericalCheckBox = new javax.swing.JCheckBox();
        mainDisplayPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        titlePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        homeTitle.setBackground(new java.awt.Color(255, 255, 255));
        homeTitle.setFont(new java.awt.Font("Merriweather Black", 1, 48)); // NOI18N
        homeTitle.setForeground(new java.awt.Color(255, 255, 255));
        homeTitle.setText("Inavlid.");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/random.png"))); // NOI18N

        homeTitle1.setBackground(new java.awt.Color(255, 255, 255));
        homeTitle1.setFont(new java.awt.Font("Courier New", 1, 48)); // NOI18N
        homeTitle1.setForeground(new java.awt.Color(255, 153, 153));
        homeTitle1.setText("input");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel4)
                .addGap(48, 48, 48)
                .addComponent(homeTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(homeTitle1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18))
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(homeTitle)
                    .addComponent(homeTitle1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inputPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        failureRateInput.setBackground(new java.awt.Color(102, 102, 102));
        failureRateInput.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        failureRateInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        failureRateInput.setText("0.2");
        failureRateInput.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        failureRateInput.setSelectionColor(new java.awt.Color(102, 0, 102));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Error Size");
        jLabel1.setToolTipText("The size of the failure zone in relation to the testing panel (1 = 100%, 0.1 = 10%))");

        speedSliderLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        speedSliderLabel.setForeground(new java.awt.Color(255, 255, 255));
        speedSliderLabel.setText("Test Speed");
        speedSliderLabel.setToolTipText("Test speed is disabled when: 'The run times is greater than 1'");

        runAmtLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        runAmtLabel.setForeground(new java.awt.Color(255, 255, 255));
        runAmtLabel.setText("Run Times ");
        runAmtLabel.setToolTipText("The amount of comparisons between RT and ART that are run");

        speedLabel.setForeground(new java.awt.Color(255, 255, 255));
        speedLabel.setText("1");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("0 - 1");

        speedSlider.setForeground(new java.awt.Color(153, 153, 153));
        speedSlider.setMaximum(300);
        speedSlider.setMinimum(1);
        speedSlider.setValue(0);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });

        runTimeSlider.setMaximum(10000);
        runTimeSlider.setMinimum(1);
        runTimeSlider.setValue(1);
        runTimeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                runTimeSliderStateChanged(evt);
            }
        });

        runTimeLabel.setForeground(new java.awt.Color(255, 255, 255));
        runTimeLabel.setText("1");

        javax.swing.GroupLayout inputPanel1Layout = new javax.swing.GroupLayout(inputPanel1);
        inputPanel1.setLayout(inputPanel1Layout);
        inputPanel1Layout.setHorizontalGroup(
            inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputPanel1Layout.createSequentialGroup()
                            .addComponent(runAmtLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(runTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(runTimeSlider, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(speedSlider, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(inputPanel1Layout.createSequentialGroup()
                            .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(speedSliderLabel)
                                .addComponent(failureRateInput, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(inputPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(29, 29, 29))
                                .addComponent(speedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        inputPanel1Layout.setVerticalGroup(
            inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(failureRateInput, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(28, 28, 28)
                .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speedSliderLabel)
                    .addComponent(speedLabel))
                .addGap(18, 18, 18)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(runAmtLabel)
                    .addComponent(runTimeLabel))
                .addGap(18, 18, 18)
                .addComponent(runTimeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        introTextLabel.setForeground(new java.awt.Color(204, 204, 204));
        introTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        introTextLabel.setText("intro text here");
        introTextLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        introHeadingLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        introHeadingLabel.setForeground(new java.awt.Color(255, 255, 255));
        introHeadingLabel.setText("Simulator Info");

        parameterHeadingLabel.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        parameterHeadingLabel.setForeground(new java.awt.Color(187, 134, 252));
        parameterHeadingLabel.setText("Test Parameters");

        startTest.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        startTest.setForeground(new java.awt.Color(187, 134, 252));
        startTest.setText("Start");
        startTest.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(187, 134, 252)));
        startTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTestActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Non-numerical Input:");

        numericalCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numericalCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputPanelWestLayout = new javax.swing.GroupLayout(inputPanelWest);
        inputPanelWest.setLayout(inputPanelWestLayout);
        inputPanelWestLayout.setHorizontalGroup(
            inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelWestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelWestLayout.createSequentialGroup()
                        .addComponent(parameterHeadingLabel)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelWestLayout.createSequentialGroup()
                        .addGroup(inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(inputPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(inputPanelWestLayout.createSequentialGroup()
                        .addGroup(inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(introHeadingLabel)
                            .addGroup(inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, inputPanelWestLayout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(numericalCheckBox))
                                .addComponent(introTextLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        inputPanelWestLayout.setVerticalGroup(
            inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelWestLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(introHeadingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(introTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inputPanelWestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(numericalCheckBox))
                .addGap(46, 46, 46)
                .addComponent(parameterHeadingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(startTest, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout mainDisplayPanelLayout = new javax.swing.GroupLayout(mainDisplayPanel);
        mainDisplayPanel.setLayout(mainDisplayPanelLayout);
        mainDisplayPanelLayout.setHorizontalGroup(
            mainDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 953, Short.MAX_VALUE)
        );
        mainDisplayPanelLayout.setVerticalGroup(
            mainDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(titlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inputPanelWest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(mainDisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainDisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputPanelWest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void handleClose() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // -- Events/Action Listeners ----------------------------------------------
    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
        speedLabel.setText(speedSlider.getValue() + "");
    }//GEN-LAST:event_speedSliderStateChanged

    private void runTimeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_runTimeSliderStateChanged
        runTimeLabel.setText(runTimeSlider.getValue() + "");
        if (runTimeSlider.getValue() > 1) {
            speedSlider.setValue(0);
            speedSlider.setEnabled(false);
            speedLabel.setText("0");
            speedSliderLabel.setForeground(Color.DARK_GRAY);
        } else {
            speedSlider.setEnabled(true);
            speedSlider.setForeground(Color.GRAY);
            speedSliderLabel.setForeground(Color.WHITE);
            speedLabel.setText("1");
        }
    }//GEN-LAST:event_runTimeSliderStateChanged

    private void startTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTestActionPerformed
        if (!numericalCheckBox.isSelected()) {
            gControl.clearNumericalPanels(nPanel); // Pass Numerical Panel
            gControl.startTest();
        } else {
            nngControl.startTest(this.startTest);
        }
    }//GEN-LAST:event_startTestActionPerformed

    private void numericalCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numericalCheckBoxActionPerformed
        CardLayout cl = (CardLayout) (cards.getLayout());
        if (numericalCheckBox.isSelected()) {
            inputPanel1.setVisible(false);
            parameterHeadingLabel.setVisible(false);
            cl.show(cards, NONNUMERICALPANEL);
        } else {
            inputPanel1.setVisible(true);
            parameterHeadingLabel.setVisible(true);
            cl.show(cards, NUMERICALPANEL);
        }
    }//GEN-LAST:event_numericalCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField failureRateInput;
    private javax.swing.JLabel homeTitle;
    private javax.swing.JLabel homeTitle1;
    private javax.swing.JPanel inputPanel1;
    private javax.swing.JPanel inputPanelWest;
    private javax.swing.JLabel introHeadingLabel;
    private javax.swing.JLabel introTextLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel mainDisplayPanel;
    private javax.swing.JCheckBox numericalCheckBox;
    private javax.swing.JLabel parameterHeadingLabel;
    private javax.swing.JLabel runAmtLabel;
    private javax.swing.JLabel runTimeLabel;
    private javax.swing.JSlider runTimeSlider;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JLabel speedSliderLabel;
    private javax.swing.JButton startTest;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables

}
