package Classes;

import GUI.NNGUIRunSettingsPanel;
import ObjectCreation.AddObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import GUI.NonNumericalPanel;
import GUI.RunInput;
import GUI.TestFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingWorker;

public class NNGUIController {

    private int RTtestCases = 0;
    private int RTdetectedErrors = 0;
    private int ARTtestCases = 0;
    private int ARTdetectedErrors = 0;
    private HashMap<String, ArrayList<Object>> rtErrorsFound;
    private HashMap<String, ArrayList<Object>> artErrorsFound;

    int testCases = 1;
    int tests = 1;
    int mode = 1;
    Constructor chosenConstructor = null;
    SwingWorker<Void, Integer> workerRT = null;
    SwingWorker<Void, Integer> workerART = null;

    private NonNumericalPanel nnpanel;
    private JButton startFrameButton;

    private RT rt = new RT();
    private ARTOO artoo = new ARTOO();
    private TestEnvironment testenv;

    //Time tracking variable
    private int RTtestCasesFirstError = 0;
    private int ARTtestCasesFirstError = 0;

    long RTfirstError = 0;
    long RTtotalTime = 0;
    long ARTfirstError = 0;
    long ARTtotalTime = 0;

    //end Time tracking variable
    public NNGUIController(NonNumericalPanel nnpanel) {
        this.nnpanel = nnpanel;
        rtErrorsFound = new HashMap<>();
        artErrorsFound = new HashMap<>();

        // Add action Listeners
        nnpanel.getStopButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopProcessing();
            }
        });

        nnpanel.getSaveButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestFrame.infoBox("Successfully saved results to: Results_log.txt", "Save Complete");
                generateErrorReport();
                nnpanel.getSaveButton().setVisible(false);
            }
        });
    }

    public void setMode(int val) {
        this.mode = val;
    }

    public void setTests(int val) {
        tests = val;
    }

    public void setTestCases(int val) {
        testCases = val;
    }

    // filters constructor list and searches for selected constructor
    public void updateConstructor() {
        chosenConstructor = nnpanel.getSelectedConstructor();
    }

    public void startTest(JButton testFrameStartButton) {
        clearAll();
        startFrameButton = testFrameStartButton;
        startFrameButton.setEnabled(false);
        updateConstructor();

        try {
            if (chosenConstructor != null) {
                System.out.println(chosenConstructor);
                this.testenv = new TestEnvironment(nnpanel.getClassLoader());
                AddObject objDescription = testenv.objectDescription(nnpanel.getSelectedClass(), chosenConstructor);
                this.rt.setObjDescription(objDescription);
                this.artoo.setObjDescription(objDescription);

                //Get User Selections for run settings
                RunInput input = new RunInput(this);
                generateOptionPane(input);

                //Create Stop button
                nnpanel.getStopButton().setVisible(true);

                System.out.println("Tests: " + tests + "    testcases: " + testCases + "    mode: " + mode);
                // Run using settings
                switch (mode) {
                    case 1:
                        //run 1 time, stop when the first error is detected
                        runRT(Integer.MAX_VALUE, 1, true);
                        runART(Integer.MAX_VALUE, 1, true);
                        break;
                    case 2:
                        //run 1 time with m test cases
                        runRT(testCases, 1, false);
                        runART(testCases, 1, false);
                        break;
                    case 3:
                        //run n times with m test cases
                        runRT(testCases, tests, false);
                        runART(testCases, tests, false);
                        break;
                    default:
                        break;
                }

            } else {
                TestFrame.infoBox("No valid construtor was selected", "Constructor Error");
            }
        } catch (Exception e) {
            TestFrame.infoBox(e.getMessage(), "Exception Occurred");
        }
    }

    protected void runRT(int testCases, int tests, boolean stopAtFirstError) {
        rtErrorsFound.clear();

        workerRT = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {

                Object object;
                int noOfTests = 0;
                int totalErrors = 0;
                while (noOfTests < tests) {

                    noOfTests++;
                    clearRTPanel();
                    long startTime, firstError, endTime;
                    firstError = 0;
                    startTime = System.currentTimeMillis();

                    // Simulates work
                    while (RTtestCases < testCases) {
                        try {
                            // handles cancellation of the swingworker
                            if (isCancelled()) {
                                return null;
                            }

                            RTtestCases++;
                            nnpanel.setTestCases(RTtestCases);
                            object = rt.generateObject();
                            try {
                                // changing the file permissions
                                File file = new File(nnpanel.getSelectedFilePath());
                                file.setExecutable(true);
                                file.setReadable(true);

                                Process process = Runtime.getRuntime().exec("java -jar " + file.getAbsolutePath() + " " + object.toString());
                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(process.getErrorStream()));
                                String line = in.readLine();
                                if (line != null) {
                                    RTdetectedErrors++;
                                    nnpanel.setDetectedErrors(RTdetectedErrors);
                                    addError(object, line, rtErrorsFound);
                                    nnpanel.displayErrors(rtErrorsFound, true);
                                    in.close(); //close process
                                    if (RTdetectedErrors == 1) {
                                        RTtestCasesFirstError += RTtestCases;
                                        firstError = System.currentTimeMillis();
                                        if (stopAtFirstError) {
                                            break;//stop after first error detected
                                        }
                                    }
                                }
                                in.close(); //close process
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //end
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    endTime = System.currentTimeMillis();
                    RTfirstError += firstError - startTime;
                    RTtotalTime += endTime - startTime;
                    totalErrors += RTdetectedErrors;

                }

                nnpanel.getStopButton().setVisible(true);
                nnpanel.getSaveButton().setVisible(true);
                startFrameButton.setEnabled(true);

                if (!stopAtFirstError) {
                    nnpanel.setRTFMeasure((double) RTtestCasesFirstError / (double) tests);
                    nnpanel.setRTPMeasure((double) totalErrors / (double) testCases);
                    System.out.println("Average time required to reveal the first error RT " + (double) (RTfirstError / 1000 / tests) + " s");
                    System.out.println("F-measure RT " + getFMeasure(RTtestCasesFirstError, tests));
                    System.out.println("P-measure RT " + getPMeasure(totalErrors, testCases));
                }
                return null;
            }
        };
        workerRT.execute();

        nnpanel.getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                workerRT.cancel(true);
            }
        });
    }

    protected void runART(int testCases, int tests, boolean stopAtFirstError) {
        artErrorsFound.clear();

        workerART = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                Object object;
                int noOfTests = 0;
                int totalErrors = 0;
                while (noOfTests < tests) {
                    noOfTests++;
                    artoo.clear();
                    clearARTPanel();
                    // Simulates work
                    long startTime, firstError, endTime;
                    firstError = 0;
                    startTime = System.currentTimeMillis();
                    while (ARTtestCases < testCases) {

                        try {
                            // handles cancellation of the swingworker
                            if (isCancelled()) {
                                return null;
                            }

                            ARTtestCases++;
                            nnpanel.setARTTestCases(ARTtestCases);
                            object = artoo.generateObject();

                            //the code below only can use for current test program
                            //need to be generalized
                            try {
                                // changing the file permissions
                                File file = new File(nnpanel.getSelectedFilePath());
                                file.setExecutable(true);
                                file.setReadable(true);

                                Process process = Runtime.getRuntime().exec("java -jar " + file.getAbsolutePath() + " " + object.toString());

                                BufferedReader in = new BufferedReader(
                                        new InputStreamReader(process.getErrorStream()));
                                String line = in.readLine();
                                if (line != null) {
                                    ARTdetectedErrors++;
                                    nnpanel.setARTDetectedErrors(ARTdetectedErrors);
                                    addError(object, line, artErrorsFound);
                                    nnpanel.displayErrors(artErrorsFound, false);
                                    in.close(); //close buffer reader
                                    if (ARTdetectedErrors == 1) {
                                        ARTtestCasesFirstError += ARTtestCases;
                                        firstError = System.currentTimeMillis();
                                        if (stopAtFirstError) {
                                            break;//stop after first error detected
                                        }
                                    }
                                }
                                in.close(); //close buffer reader
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //end
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                    endTime = System.currentTimeMillis();
                    ARTfirstError += firstError - startTime;
                    ARTtotalTime += endTime - startTime;
                    totalErrors += ARTdetectedErrors;
                }

                nnpanel.getStopButton().setVisible(true);
                nnpanel.getSaveButton().setVisible(true);
                startFrameButton.setEnabled(true);

                if (!stopAtFirstError) {
                    nnpanel.setARTFMeasure((double) ARTtestCasesFirstError / (double) tests);
                    nnpanel.setARTPMeasure((double) totalErrors / (double) testCases);
                    System.out.println("Average time required to reveal the first error ART " + (double) (ARTfirstError / 1000 / tests) + " s.");
                    System.out.println("F-measure ART " + getFMeasure(ARTtestCasesFirstError, tests));
                    System.out.println("P-measure ART " + getPMeasure(totalErrors, testCases));
                }
                return null;
            }

        };
        workerART.execute();

        nnpanel.getStopButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                workerART.cancel(true);
            }
        });
    }

    public void addError(Object object, String line, HashMap<String, ArrayList<Object>> errorsFound) {
        String sub;
        if (line.contains("java.")) {
            sub = line.substring((line.indexOf("java.")));
        } else {
            sub = line;
        }
        System.out.println("ERROR: " + sub);
        String[] parts = sub.split(" ");
        String error = parts[0];

        // Use error to check whether that error code already exists
        ArrayList<Object> errorList = errorsFound.get(error);
        if (errorList != null) {
            errorList.add(object);
            errorsFound.put(error, errorList);
        } else {
            errorList = new ArrayList<Object>();
            errorList.add(object);
            errorsFound.put(error, errorList);
        }
    }

    public void generateErrorReport() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            BufferedWriter out = new BufferedWriter(new FileWriter("Results_log.txt"));

            String output = "Testing log generated at: " + dateFormat.format(date) + "\n";
            output += "Constructor tested: " + chosenConstructor + "\n\n";
            output += "\nRandom Test Cases\n";
            output += "----------------------------------------------------------------------\n";

            if (rtErrorsFound.size() > 0) {

                output += "The testing revealed the following error cases:\n";

                for (String err : rtErrorsFound.keySet()) {

                    output += "\n" + err + "\n";

                    for (Object obj : rtErrorsFound.get(err)) {

                        String[] components = obj.toString().split(" ");
                        for (int i = 0; i < components.length; i++) {
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

                            output += objVal;
                        }
                        output += "\n";
                    }
                }
            }

            output += "\n\nAdaptive Random Test Cases\n";
            output += "----------------------------------------------------------------------\n";

            if (artErrorsFound.size() > 0) {

                output += "The testing revealed the following error cases:\n";

                for (String err : artErrorsFound.keySet()) {

                    output += "\n" + err + "\n";

                    for (Object obj : artErrorsFound.get(err)) {

                        String[] components = obj.toString().split(" ");
                        for (int i = 0; i < components.length; i++) {
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

                            output += objVal;
                        }
                        output += "\n";
                    }
                }
            }

            out.write(output);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private double getFMeasure(int testCasesFirstError, int tests) {
        return (double) testCasesFirstError / (double) tests;
    }

    private double getPMeasure(int totalErrors, int testCases) {
        return (double) totalErrors / (double) testCases;
    }

    public void stopProcessing() {
        workerRT.cancel(true);
        workerART.cancel(true);
        clearRTPanel();
        clearARTPanel();
        nnpanel.getStopButton().setVisible(false);
        startFrameButton.setEnabled(true);
    }

    private void clearRTPanel() {
        RTtestCases = 0;
        RTdetectedErrors = 0;
        nnpanel.clearRTPanel();
    }

    private void clearARTPanel() {
        ARTtestCases = 0;
        ARTdetectedErrors = 0;
        nnpanel.clearARTPanel();

        // changing the file permissions
        File file = new File(nnpanel.getSelectedFilePath());
        file.setExecutable(false);
    }

    private void clearAll() {
        clearRTPanel();
        clearARTPanel();
        RTfirstError = 0;
        RTtotalTime = 0;
        ARTfirstError = 0;
        ARTtotalTime = 0;
        RTtestCasesFirstError = 0;
        ARTtestCasesFirstError = 0;
        nnpanel.getSaveButton().setVisible(false);
        nnpanel.getStopButton().setVisible(false);
    }

    public static void generateOptionPane(NNGUIRunSettingsPanel panel) {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Run Settings");
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        panel.getAcceptButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }
}
