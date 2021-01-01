package GUIDataInputs;

import GUI.Colors;
import GUI.TestFrame;
import ObjectCreation.AddDouble;
import java.lang.reflect.Constructor;
import javax.swing.JButton;

public class DoubleInputFrame extends NNGUIInput {

    public DoubleInputFrame(int argNum, Constructor con) {
        initComponents();
        setDarkMode();

        // Update panel details
        this.conLabel.setText(con.getName() + " constructor");
        this.argNumberLabel.setText(argNum + "");
    }

    // ------- Getters --------- //
    public JButton getAcceptButton() {
        return this.acceptButton;
    }

    // ------- Operators --------- //
    public Object createObject() {

        double lowerBound = 0;
        double upperBound = 1;

        if (r1.isSelected()) {
            lowerBound = 0;
            upperBound = Double.MAX_VALUE;
        }

        if (r2.isSelected()) {
            lowerBound = Double.MIN_VALUE;
            upperBound = 0;
        }

        if (r3.isSelected()) {
            lowerBound = Double.MIN_VALUE;
            upperBound = Double.MAX_VALUE;
        }

        if (r4.isSelected()) {
            if (validateUserChoice()) {
                lowerBound = Double.parseDouble(minInput.getText());
                upperBound = Double.parseDouble(maxInput.getText());
            }
        }

        return (Object) (new AddDouble(lowerBound, upperBound));
    }

    public boolean validateUserChoice() {
        // Test userInput parameters
        if (r4.isSelected()) {
            try {
                Double.parseDouble(minInput.getText());
                Double.parseDouble(maxInput.getText());
            } catch (NumberFormatException e) {
                TestFrame.infoBox("Input must be of Double value", "Bad Input");
                return false;
            } catch (Exception e) {
                TestFrame.infoBox(e.getMessage(), "Exception occurred");
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioGroup = new javax.swing.ButtonGroup();
        mainPanel = new javax.swing.JPanel();
        inputPanel = new javax.swing.JPanel();
        r1 = new javax.swing.JRadioButton();
        r2 = new javax.swing.JRadioButton();
        r3 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        userDefinedInputPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        maxInput = new javax.swing.JTextField();
        minInput = new javax.swing.JTextField();
        r4 = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        conLabel = new javax.swing.JLabel();
        headerInfoPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        argTypeLabel = new javax.swing.JLabel();
        argNumberLabel = new javax.swing.JLabel();
        acceptButton = new javax.swing.JButton();

        r1.setBackground(new java.awt.Color(36, 41, 46));
        radioGroup.add(r1);
        r1.setSelected(true);

        r2.setBackground(new java.awt.Color(36, 41, 46));
        radioGroup.add(r2);

        r3.setBackground(new java.awt.Color(36, 41, 46));
        radioGroup.add(r3);

        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("0  to  DOUBLE_MAX");

        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("DOUBLE_MIN  to  0");

        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("DOUBLE_MIN  to  DOUBLE_MAX");

        userDefinedInputPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Min:");

        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("Max:");

        maxInput.setBackground(new java.awt.Color(153, 153, 153));
        maxInput.setForeground(new java.awt.Color(0, 0, 0));
        maxInput.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        minInput.setBackground(new java.awt.Color(153, 153, 153));
        minInput.setForeground(new java.awt.Color(0, 0, 0));
        minInput.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        r4.setBackground(new java.awt.Color(51, 56, 66));
        radioGroup.add(r4);

        javax.swing.GroupLayout userDefinedInputPanelLayout = new javax.swing.GroupLayout(userDefinedInputPanel);
        userDefinedInputPanel.setLayout(userDefinedInputPanelLayout);
        userDefinedInputPanelLayout.setHorizontalGroup(
            userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userDefinedInputPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(maxInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minInput, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addComponent(r4)
                .addGap(34, 34, 34))
        );
        userDefinedInputPanelLayout.setVerticalGroup(
            userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userDefinedInputPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(userDefinedInputPanelLayout.createSequentialGroup()
                        .addGroup(userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(minInput, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(20, 20, 20)
                        .addComponent(maxInput, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(userDefinedInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(userDefinedInputPanelLayout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(jLabel10))
                        .addGroup(userDefinedInputPanelLayout.createSequentialGroup()
                            .addGap(3, 3, 3)
                            .addComponent(r4))))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(238, 238, 238));
        jLabel11.setText("User Defined");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(233, 233, 233));
        jLabel5.setText("Select range values");

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(r2)
                    .addComponent(r3)
                    .addComponent(r1))
                .addGap(100, 100, 100))
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel11))
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userDefinedInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(r1))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(r2)
                    .addComponent(jLabel7))
                .addGap(21, 21, 21)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(r3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(38, 38, 38)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userDefinedInputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        conLabel.setForeground(new java.awt.Color(204, 204, 204));
        conLabel.setText("constructor info");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Arg:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Type:");

        argTypeLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        argTypeLabel.setForeground(new java.awt.Color(187, 134, 252));
        argTypeLabel.setText("DOUBLE");

        argNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        argNumberLabel.setForeground(new java.awt.Color(187, 134, 252));
        argNumberLabel.setText("#NUM");

        javax.swing.GroupLayout headerInfoPanelLayout = new javax.swing.GroupLayout(headerInfoPanel);
        headerInfoPanel.setLayout(headerInfoPanelLayout);
        headerInfoPanelLayout.setHorizontalGroup(
            headerInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerInfoPanelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(argNumberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(argTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        headerInfoPanelLayout.setVerticalGroup(
            headerInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerInfoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(headerInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(argTypeLabel)
                    .addComponent(argNumberLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(headerInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(conLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(conLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(headerInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        acceptButton.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        acceptButton.setForeground(new java.awt.Color(187, 134, 252));
        acceptButton.setText("Accept");
        acceptButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(187, 134, 252)));

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 443, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(acceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(acceptButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setDarkMode() {
        // Panels
        this.headerPanel.setBackground(Colors.headerDark);
        this.headerInfoPanel.setBackground(Colors.headerDark);
        this.mainPanel.setBackground(Colors.backgroundDark);
        this.inputPanel.setBackground(Colors.backgroundDark);
        this.userDefinedInputPanel.setBackground(Colors.contentDark);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JLabel argNumberLabel;
    private javax.swing.JLabel argTypeLabel;
    private javax.swing.JLabel conLabel;
    private javax.swing.JPanel headerInfoPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField maxInput;
    private javax.swing.JTextField minInput;
    private javax.swing.JRadioButton r1;
    private javax.swing.JRadioButton r2;
    private javax.swing.JRadioButton r3;
    private javax.swing.JRadioButton r4;
    private javax.swing.ButtonGroup radioGroup;
    private javax.swing.JPanel userDefinedInputPanel;
    // End of variables declaration//GEN-END:variables
}
