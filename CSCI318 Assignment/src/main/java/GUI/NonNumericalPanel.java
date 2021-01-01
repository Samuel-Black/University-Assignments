package GUI;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class NonNumericalPanel extends javax.swing.JPanel {

    private Scanner input = new Scanner(System.in);
    private File selectedFile = null;
    private URLClassLoader classLoader;
    private String mainClass = "";
    private List<String> classList = new ArrayList<String>();
    private List<String> constructorList = new ArrayList<String>();

    public NonNumericalPanel() {
        initComponents();
        this.stopButton.setVisible(false);
        this.saveButton.setVisible(false);
    }

    //---- Getters ----
    public File getSourceCodeDir() {
        return selectedFile;
    }

    public String getSelectedFile() {
        return selectedFile.getName();
    }

    public String getSelectedFilePath() {
        return selectedFile.getAbsolutePath();
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    public String getSelectedClass() {
        return (String) classComboBox.getSelectedItem();
    }

    public int getSelectedConstructorIndex() {
        return constructorComboBox.getSelectedIndex();
    }

    public JButton getStopButton() {
        return this.stopButton;
    }

    public JButton getSaveButton() {
        return this.saveButton;
    }

    public String getMainClass() {
        return mainClass;
    }

    //---- Setters ----
    public void setTestCases(int testCases) {
        rtTestCaseCount.setText(testCases + "");
    }

    public void setDetectedErrors(int errorCount) {
        rtErrorCount.setText(errorCount + "");
    }

    public void setARTTestCases(int RTtestCases) {
        artTestCaseCount.setText(RTtestCases + "");
    }

    public void setARTDetectedErrors(int errorCount) {
        artErrorCount.setText(errorCount + "");
    }

    public void displayErrors(HashMap<String, ArrayList<Object>> errorsFound, boolean rt) {
        // Select correct text pane
        JTextPane textArea = rtTextArea;
        if (!rt) {
            textArea = artTextArea;
        }
        textArea.setText("");

        // Display errors, if any
        try {
            StyledDocument doc = textArea.getStyledDocument();
            Style style = textArea.addStyle("", null);

            if (errorsFound.size() > 0) {

                StyleConstants.setForeground(style, Colors.darkModeText);
                doc.insertString(doc.getLength(), "The testing revealed the following error cases:\n", style);

                for (String err : errorsFound.keySet()) {

                    StyleConstants.setForeground(style, Color.WHITE);
                    doc.insertString(doc.getLength(), "\n" + err + "\n", style);

                    for (Object obj : errorsFound.get(err)) {

                        String[] components = obj.toString().split(" ");
                        for (int i = 0; i < components.length; i++) {
                            StyleConstants.setForeground(style, Colors.glowGreen);
                            String objVal = components[i];
                            objVal = objVal.trim();

                            // Set size allocated for each information piece
                            if (objVal.length() < 12) {
                                objVal = String.format("%-16s", components[i]);
                            } else if (objVal.length() < 24) {
                                objVal = String.format("%-28s", components[i]);
                            } else {
                                objVal = String.format("%-" + (objVal.length() + 4) + "s", components[i]);
                            }

                            doc.insertString(doc.getLength(), objVal, style);

                            /*
                            System.out.print(objVal);
                            for (int x = 0; x < objVal.length(); x++) {
                                System.out.print((int) objVal.charAt(x) + " ");
                            }
                            System.out.print("\n");
                             */
                        }
                        doc.insertString(doc.getLength(), "\n", style);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setARTFMeasure(double fMeasure) {
        artFMeasureOutputLabel.setText(fMeasure + "");
    }

    public void setRTFMeasure(double fMeasure) {
        rtFMeasureOutputLabel.setText(fMeasure + "");
    }

    public void setARTPMeasure(double pMeasure) {
        artPMeasureOutputLabel.setText(pMeasure + "");
    }

    public void setRTPMeasure(double pMeasure) {
        rtPMeasureOutputLabel.setText(pMeasure + "");
    }

    private void setMainClass() {
        //get main class name
        JarFile j;
        try {
            j = new JarFile(selectedFile);
            mainClass = j.getManifest().getMainAttributes().getValue("Main-Class");
            System.out.println("Main class: " + mainClass);
            //close jar file
            j.close();

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public Constructor<?>[] getClassConstructors() {
        //clear input
        constructorList.clear();
        constructorLabel.setForeground(new Color(204, 204, 204));
        constructorLabel.setText("Select a constructor");
        Constructor<?>[] allConstructors = null;
        try {
            //get constrcutors 
            String className = classComboBox.getSelectedItem().toString().trim();
            Class<?> clazz = classLoader.loadClass(className);
            allConstructors = clazz.getDeclaredConstructors();

            if (allConstructors.length == 0) {
                constructorLabel.setText("No Constructors found");
                constructorLabel.setForeground(Colors.errorRed);
            }
            for (int i = 0; i < allConstructors.length; i++) {
                String crntCon = "";
                String conName = allConstructors[i].getName().toString();
                crntCon += conName.substring(conName.indexOf(".") + 1) + '(';
                Class[] crntParams = allConstructors[i].getParameterTypes();

                for (int p = 0; p < crntParams.length; p++) {
                    // Add parameters one at a time
                    String param = crntParams[p].getName().toString();
                    while (param.contains(".")) {
                        param = param.substring(param.indexOf(".") + 1);
                    }
                    crntCon += param;
                    if (p < (crntParams.length - 1)) {
                        crntCon += ", ";
                    }
                }
                crntCon += ')';
                constructorList.add(crntCon);
            }
            //display in combo box
            constructorComboBox.setModel(new DefaultComboBoxModel(constructorList.toArray()));
            constructorComboBox.setSelectedIndex(0);

            return allConstructors;

        } catch (ClassNotFoundException e) {
            constructorLabel.setText("No Constructors found");
            constructorLabel.setForeground(Colors.errorRed);
        }

        return allConstructors;
    }

    public Constructor getSelectedConstructor() {
        Constructor[] allConstructors = getClassConstructors();
        int selectedConstructor = getSelectedConstructorIndex();
        try {
            classLoader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (allConstructors.length > 0) {
            return allConstructors[selectedConstructor];
        }
        return null;
    }

    //list all classes in UserCode
    private void displayClassList() {

        // Clear input
        classList.clear();
        classSelectLabel.setText("Select a class");
        classSelectLabel.setForeground(new Color(204, 204, 204));

        //list classes
        setMainClass(); //get name of main class
        //display class list (except main class)
        ZipInputStream zip;
        try {
            zip = new ZipInputStream(new FileInputStream(selectedFile.getAbsolutePath()));
            for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {

                if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                    // This ZipEntry represents a .class file
                    String className = entry.getName().replace('/', '.'); // including ".class"
                    className = className.substring(0, className.length() - ".class".length());
                    //add to class list if class is public and is NOT an interface
                    try {
                        Class<?> clazz = classLoader.loadClass(className);
                        String classModifier = Modifier.toString(clazz.getModifiers());
                        if (classModifier.contains("public") && !clazz.isInterface()
                                && !className.equalsIgnoreCase(mainClass)) {
                            classList.add(clazz.getName());
                            System.out.println("added: " + clazz.getName());

                        } else {
                            System.out.println("Unloaded class: " + className);
                        }

                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        //e.printStackTrace();
                        System.out.println("Class named " + className + " is not added to the combo box");
                    }
                }
            }
            //close zip stream
            zip.close();
            //if no class found
            if (classList.size() == 0) {
                classSelectLabel.setText("No Public Class found");
                classSelectLabel.setForeground(Colors.errorRed);
            }

            //reinitialised class combo box
            classComboBox.setModel(new DefaultComboBoxModel(classList.toArray()));
            classComboBox.setSelectedIndex(0);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            classSelectLabel.setText("No java executable file found in selected folder");
            classSelectLabel.setForeground(Colors.errorRed);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void initClassLoader() {
        try {
            classLoader = new URLClassLoader(
                    new URL[]{selectedFile.toURI().toURL()},
                    null
            );
            //classLoader = URLClassLoader.newInstance(new URL[] {selectedFile.toURI().toURL()}, null);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void clearRTPanel() {
        rtTextArea.setText("");
        rtTestCaseCount.setText("0");
        rtErrorCount.setText("0");
    }

    public void clearARTPanel() {
        artTextArea.setText("");
        artTestCaseCount.setText("0");
        artErrorCount.setText("0");
    }

    public void setDarkMode() {
        // Panels
        this.setBackground(Colors.contentDark);
        this.rtOutputPanel.setBackground(Colors.backgroundDark);
        this.artOutputPanel.setBackground(Colors.backgroundDark);
        this.rtHeaderPanel.setBackground(Colors.headerDark);
        this.artHeaderPanel.setBackground(Colors.headerDark);
        this.rtInfoPanel.setBackground(Colors.backgroundDark);
        this.artInfoPanel.setBackground(Colors.backgroundDark);
        this.rtTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.black));
        this.artTextArea.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.black));
        this.inputPanel.setBackground(Colors.contentDark);
        this.rtErrorPanel.setBackground(Colors.backgroundDark);
        this.artErrorPanel.setBackground(Colors.backgroundDark);
        this.buttonPanel1.setBackground(Colors.contentDark);
        this.buttonPanel2.setBackground(Colors.contentDark);

        // Text Areas
        this.rtTextArea.setBackground(Colors.panelDark);
        this.artTextArea.setBackground(Colors.panelDark);

        // Inputs
        this.fileChooserButton.setBackground(Colors.headerDark);
        this.classComboBox.setBackground(Colors.headerDark);
        this.constructorComboBox.setBackground(Colors.headerDark);

        // Buttons
        this.stopButton.setBackground(Colors.errorRed);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rtOutputPanel = new javax.swing.JPanel();
        rtInfoPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        rtErrorCount = new javax.swing.JLabel();
        rtTestCaseCount = new javax.swing.JLabel();
        rtFMeasureLabel = new javax.swing.JLabel();
        rtPMeasureLabel = new javax.swing.JLabel();
        rtFMeasureOutputLabel = new javax.swing.JLabel();
        rtPMeasureOutputLabel = new javax.swing.JLabel();
        rtHeaderPanel = new javax.swing.JPanel();
        rtTitle = new javax.swing.JLabel();
        rtErrorPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        rtTextArea = new javax.swing.JTextPane();
        artOutputPanel = new javax.swing.JPanel();
        artInfoPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        artTestCaseCount = new javax.swing.JLabel();
        artErrorCount = new javax.swing.JLabel();
        artFMeasureLabel = new javax.swing.JLabel();
        artPMeasureLabel = new javax.swing.JLabel();
        artFMeasureOutputLabel = new javax.swing.JLabel();
        artPMeasureOutputLabel = new javax.swing.JLabel();
        artHeaderPanel = new javax.swing.JPanel();
        artTitle = new javax.swing.JLabel();
        artErrorPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        artTextArea = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        inputPanel = new javax.swing.JPanel();
        selectedFileLabel = new javax.swing.JLabel();
        classSelectLabel = new javax.swing.JLabel();
        constructorLabel = new javax.swing.JLabel();
        constructorComboBox = new javax.swing.JComboBox<>();
        classComboBox = new javax.swing.JComboBox<>();
        fileChooserButton = new javax.swing.JButton();
        buttonPanel1 = new javax.swing.JPanel();
        stopButton = new javax.swing.JButton();
        buttonPanel2 = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(953, 956));

        rtOutputPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        rtOutputPanel.setPreferredSize(new java.awt.Dimension(783, 260));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Test Cases");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Error Detected:");

        rtErrorCount.setForeground(new java.awt.Color(204, 204, 204));
        rtErrorCount.setText("0");

        rtTestCaseCount.setForeground(new java.awt.Color(204, 204, 204));
        rtTestCaseCount.setText("0");

        rtFMeasureLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rtFMeasureLabel.setForeground(new java.awt.Color(255, 255, 255));
        rtFMeasureLabel.setText("F-Measure");

        rtPMeasureLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rtPMeasureLabel.setForeground(new java.awt.Color(255, 255, 255));
        rtPMeasureLabel.setText("P-Measure");

        rtFMeasureOutputLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rtFMeasureOutputLabel.setForeground(new java.awt.Color(187, 134, 252));
        rtFMeasureOutputLabel.setText("0");
        rtFMeasureOutputLabel.setPreferredSize(new java.awt.Dimension(4, 16));

        rtPMeasureOutputLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rtPMeasureOutputLabel.setForeground(new java.awt.Color(187, 134, 252));
        rtPMeasureOutputLabel.setText("0");

        javax.swing.GroupLayout rtInfoPanelLayout = new javax.swing.GroupLayout(rtInfoPanel);
        rtInfoPanel.setLayout(rtInfoPanelLayout);
        rtInfoPanelLayout.setHorizontalGroup(
            rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rtInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(rtFMeasureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rtPMeasureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(rtPMeasureOutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(rtFMeasureOutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rtTestCaseCount, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                        .addComponent(rtErrorCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        rtInfoPanelLayout.setVerticalGroup(
            rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtInfoPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rtTestCaseCount))
                .addGap(18, 18, 18)
                .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rtErrorCount))
                .addGap(18, 18, 18)
                .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtFMeasureLabel)
                    .addComponent(rtFMeasureOutputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(rtInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtPMeasureLabel)
                    .addComponent(rtPMeasureOutputLabel))
                .addContainerGap(8, Short.MAX_VALUE))
        );

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

        rtErrorPanel.setPreferredSize(new java.awt.Dimension(454, 238));

        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("Error Log");

        rtTextArea.setEditable(false);
        rtTextArea.setBorder(null);
        rtTextArea.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        rtTextArea.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(rtTextArea);

        javax.swing.GroupLayout rtErrorPanelLayout = new javax.swing.GroupLayout(rtErrorPanel);
        rtErrorPanel.setLayout(rtErrorPanelLayout);
        rtErrorPanelLayout.setHorizontalGroup(
            rtErrorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtErrorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rtErrorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rtErrorPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                .addContainerGap())
        );
        rtErrorPanelLayout.setVerticalGroup(
            rtErrorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtErrorPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout rtOutputPanelLayout = new javax.swing.GroupLayout(rtOutputPanel);
        rtOutputPanel.setLayout(rtOutputPanelLayout);
        rtOutputPanelLayout.setHorizontalGroup(
            rtOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtOutputPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(rtOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rtHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(rtOutputPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(rtInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rtErrorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        rtOutputPanelLayout.setVerticalGroup(
            rtOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rtOutputPanelLayout.createSequentialGroup()
                .addGroup(rtOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(rtOutputPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rtErrorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rtOutputPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(rtHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rtInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        artOutputPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        artOutputPanel.setPreferredSize(new java.awt.Dimension(783, 260));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Test Cases:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Error Detected:");

        artTestCaseCount.setForeground(new java.awt.Color(204, 204, 204));
        artTestCaseCount.setText("0");

        artErrorCount.setForeground(new java.awt.Color(204, 204, 204));
        artErrorCount.setText("0");

        artFMeasureLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        artFMeasureLabel.setForeground(new java.awt.Color(255, 255, 255));
        artFMeasureLabel.setText("F-Measure");

        artPMeasureLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        artPMeasureLabel.setForeground(new java.awt.Color(255, 255, 255));
        artPMeasureLabel.setText("P-Measure");

        artFMeasureOutputLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        artFMeasureOutputLabel.setForeground(new java.awt.Color(187, 134, 252));
        artFMeasureOutputLabel.setText("0");

        artPMeasureOutputLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        artPMeasureOutputLabel.setForeground(new java.awt.Color(187, 134, 252));
        artPMeasureOutputLabel.setText("0");

        javax.swing.GroupLayout artInfoPanelLayout = new javax.swing.GroupLayout(artInfoPanel);
        artInfoPanel.setLayout(artInfoPanelLayout);
        artInfoPanelLayout.setHorizontalGroup(
            artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, artInfoPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(artInfoPanelLayout.createSequentialGroup()
                        .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(artTestCaseCount, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(artErrorCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(artInfoPanelLayout.createSequentialGroup()
                        .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(artPMeasureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(artFMeasureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(artFMeasureOutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(artPMeasureOutputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(13, 13, 13))
        );
        artInfoPanelLayout.setVerticalGroup(
            artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artInfoPanelLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(artTestCaseCount))
                .addGap(18, 18, 18)
                .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(artErrorCount))
                .addGap(18, 18, 18)
                .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(artFMeasureLabel)
                    .addComponent(artFMeasureOutputLabel))
                .addGap(18, 18, 18)
                .addGroup(artInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(artPMeasureLabel)
                    .addComponent(artPMeasureOutputLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        artTextArea.setEditable(false);
        artTextArea.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        artTextArea.setForeground(new java.awt.Color(255, 255, 255));
        artTextArea.setPreferredSize(new java.awt.Dimension(62, 14));
        jScrollPane1.setViewportView(artTextArea);

        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("Error Log");

        javax.swing.GroupLayout artErrorPanelLayout = new javax.swing.GroupLayout(artErrorPanel);
        artErrorPanel.setLayout(artErrorPanelLayout);
        artErrorPanelLayout.setHorizontalGroup(
            artErrorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artErrorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(artErrorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(artErrorPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 514, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        artErrorPanelLayout.setVerticalGroup(
            artErrorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artErrorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout artOutputPanelLayout = new javax.swing.GroupLayout(artOutputPanel);
        artOutputPanel.setLayout(artOutputPanelLayout);
        artOutputPanelLayout.setHorizontalGroup(
            artOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artOutputPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(artOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(artHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(artOutputPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(artInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(artErrorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        artOutputPanelLayout.setVerticalGroup(
            artOutputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(artOutputPanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(artHeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(artInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, artOutputPanelLayout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(artErrorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        selectedFileLabel.setForeground(new java.awt.Color(204, 204, 204));
        selectedFileLabel.setText("No file slected...");

        classSelectLabel.setForeground(new java.awt.Color(204, 204, 204));
        classSelectLabel.setText("Select a class");

        constructorLabel.setForeground(new java.awt.Color(204, 204, 204));
        constructorLabel.setText("Select a constructor");

        constructorComboBox.setForeground(new java.awt.Color(204, 204, 204));
        constructorComboBox.setBorder(null);

        classComboBox.setForeground(new java.awt.Color(204, 204, 204));
        classComboBox.setBorder(null);
        classComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                updateClassesHandler(evt);
            }
        });

        fileChooserButton.setForeground(new java.awt.Color(255, 255, 255));
        fileChooserButton.setText("Select File");
        fileChooserButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 134, 252)));
        fileChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inputPanelLayout.createSequentialGroup()
                        .addComponent(constructorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(constructorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(inputPanelLayout.createSequentialGroup()
                            .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(classSelectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(inputPanelLayout.createSequentialGroup()
                            .addComponent(fileChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(selectedFileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectedFileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(classSelectLabel))
                .addGap(18, 18, 18)
                .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(constructorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(constructorLabel))
                .addGap(15, 15, 15))
        );

        stopButton.setForeground(new java.awt.Color(255, 255, 255));
        stopButton.setText("Clear");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanel1Layout = new javax.swing.GroupLayout(buttonPanel1);
        buttonPanel1.setLayout(buttonPanel1Layout);
        buttonPanel1Layout.setHorizontalGroup(
            buttonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );
        buttonPanel1Layout.setVerticalGroup(
            buttonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        saveButton.setBackground(new java.awt.Color(51, 51, 51));
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("Save Report");

        javax.swing.GroupLayout buttonPanel2Layout = new javax.swing.GroupLayout(buttonPanel2);
        buttonPanel2.setLayout(buttonPanel2Layout);
        buttonPanel2Layout.setHorizontalGroup(
            buttonPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        buttonPanel2Layout.setVerticalGroup(
            buttonPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))
                    .addComponent(artOutputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE)
                    .addComponent(rtOutputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 875, Short.MAX_VALUE))
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 28, Short.MAX_VALUE)
                .addComponent(rtOutputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(artOutputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void fileChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserButtonActionPerformed
        FileChooserPanel fCPanel = new FileChooserPanel();

        fCPanel.setVisible(true);
        this.setEnabled(false);

        File file = fCPanel.getFile();
        if (file != null) {
            if (!file.getAbsolutePath().contains(" ")) {
                selectedFileLabel.setForeground(Colors.darkModeText);
                selectedFileLabel.setText(file.getName());
                selectedFile = file;
                initClassLoader();
                displayClassList();
                getClassConstructors();
                this.setEnabled(true);
            } else {
                selectedFileLabel.setForeground(Colors.errorRed);
                selectedFileLabel.setText("File path cannot contain empty spaces!");
            }
        }

    }//GEN-LAST:event_fileChooserButtonActionPerformed

    private void updateClassesHandler(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_updateClassesHandler
        getClassConstructors();
    }//GEN-LAST:event_updateClassesHandler

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stopButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel artErrorCount;
    private javax.swing.JPanel artErrorPanel;
    private javax.swing.JLabel artFMeasureLabel;
    private javax.swing.JLabel artFMeasureOutputLabel;
    private javax.swing.JPanel artHeaderPanel;
    private javax.swing.JPanel artInfoPanel;
    private javax.swing.JPanel artOutputPanel;
    private javax.swing.JLabel artPMeasureLabel;
    private javax.swing.JLabel artPMeasureOutputLabel;
    private javax.swing.JLabel artTestCaseCount;
    private javax.swing.JTextPane artTextArea;
    private javax.swing.JLabel artTitle;
    private javax.swing.JPanel buttonPanel1;
    private javax.swing.JPanel buttonPanel2;
    private javax.swing.JComboBox<String> classComboBox;
    private javax.swing.JLabel classSelectLabel;
    private javax.swing.JComboBox<String> constructorComboBox;
    private javax.swing.JLabel constructorLabel;
    private javax.swing.JButton fileChooserButton;
    private javax.swing.JPanel inputPanel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel rtErrorCount;
    private javax.swing.JPanel rtErrorPanel;
    private javax.swing.JLabel rtFMeasureLabel;
    private javax.swing.JLabel rtFMeasureOutputLabel;
    private javax.swing.JPanel rtHeaderPanel;
    private javax.swing.JPanel rtInfoPanel;
    private javax.swing.JPanel rtOutputPanel;
    private javax.swing.JLabel rtPMeasureLabel;
    private javax.swing.JLabel rtPMeasureOutputLabel;
    private javax.swing.JLabel rtTestCaseCount;
    private javax.swing.JTextPane rtTextArea;
    private javax.swing.JLabel rtTitle;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel selectedFileLabel;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
