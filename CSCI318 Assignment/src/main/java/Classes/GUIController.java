package Classes;

import GUI.NumericalPanel;
import GUI.TestFrame;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class GUIController {

    RT randomTest;
    ART adaptiveRandomTest;

    TestFrame tFrame;

    double failureRate = 0.2;
    int coordDiameter = 5; // Diameter of random point in pixels
    final double lowerBound = 0.0;
    final double upperBound = 1.0;
    long timeBetweenExecute = 250; //time in milliseconds to be used for wait between repaint()
    int runAmt = 1;
    int RTWin = 0;
    int ARTWin = 0;
    int ties = 0;
    double failX = 0;
    double failY = 0;

    public GUIController(TestFrame tFrame) {
        this.tFrame = tFrame;
        // Create new Random Test Objects
        randomTest = new RT(failureRate, tFrame.getDisplayBoxDimensions().width, coordDiameter);
        adaptiveRandomTest = new ART(failureRate, tFrame.getDisplayBoxDimensions().width, coordDiameter);
    }

    public RT getRT() {
        return this.randomTest;
    }

    public ART getART() {
        return this.adaptiveRandomTest;
    }

    public void startTest() {
        setTimeBetweenExecute(); // Check for valid input in execute speed text field
        validateRunTimes();

        if (updateFailureRate()) {
            if (runAmt == 1) {
                runRandomTestProgram();
            } else {
                for (int i = 0; i < runAmt; i++) {
                    runRandomTestProgramMulti();
                }
                // Update Scores
                tFrame.setTies(ties + "");
                tFrame.setRTWinCount(RTWin + "");
                tFrame.setARTWinCount(ARTWin + "");
            }
        }
    }

    private void setTimeBetweenExecute() {
        timeBetweenExecute = tFrame.getSpeedSliderValue();
        if (timeBetweenExecute <= 5) {
            timeBetweenExecute = 6;
        }
    }

    private void validateRunTimes() {
        runAmt = tFrame.getRunTimeSlider();
    }

    private boolean updateFailureRate() {
        String failureRateString = tFrame.getFailureRateInput();
        if (!failureRateString.isBlank()) // Test for empty  
        {
            try {
                failureRate = Double.parseDouble(failureRateString);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
            if (failureRate <= lowerBound || failureRate >= upperBound) // Test for within bounds
            {
                JOptionPane.showMessageDialog(null, "Input must be between 0.0 and 1.0, non-inclusive.");
                return false;
            }
            randomTest.setFailureRadius(failureRate * randomTest.getPanelSize());
            randomTest.generateFailureRegion(randomTest.getFailureRadius());
            adaptiveRandomTest.setFailureRadius(failureRate * adaptiveRandomTest.getPanelSize());
            adaptiveRandomTest.setFailureRegion(randomTest.getFailureX(), randomTest.getFailureY(), failureRate * adaptiveRandomTest.getPanelSize());
            return true;
        }
        return false;
    }

    private void runRandomTestProgram() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                tFrame.disableStartButton();

                if (randomTest.getCoordinates().size() <= 0 && adaptiveRandomTest.getCoordinates().size() <= 0) {
                    createFirstCoordinate();
                } else {
                    randomTest.generateCoordinate();
                    adaptiveRandomTest.generateCoordinate();
                }
                tFrame.repaintRT(failX, failY, randomTest);
                tFrame.repaintART(failX, failY, adaptiveRandomTest);

                if (randomTest.hasFailed() && adaptiveRandomTest.hasFailed()) {
                    ties++;
                    tFrame.setTies(ties + "");
                    cancelExecutorService(executorService);
                    tFrame.enableStartButton();
                } else if (randomTest.hasFailed()) // RT Wins 
                {
                    RTWin++;
                    tFrame.setRTWinCount(RTWin + "");
                    cancelExecutorService(executorService);
                    tFrame.enableStartButton();
                } else if (adaptiveRandomTest.hasFailed()) // ART Wins
                {
                    ARTWin++;
                    tFrame.setARTWinCount(ARTWin + "");
                    cancelExecutorService(executorService);
                    tFrame.enableStartButton();
                }
            }
        }, 0, timeBetweenExecute, TimeUnit.MILLISECONDS);
    }

    private void runRandomTestProgramMulti() {
        randomTest.getCoordinates().clear();
        adaptiveRandomTest.getCoordinates().clear();
        do {
            if (randomTest.getCoordinates().size() <= 0 && adaptiveRandomTest.getCoordinates().size() <= 0) {
                createFirstCoordinate();
            } else {
                randomTest.generateCoordinate();
                adaptiveRandomTest.generateCoordinate();
            }

            if (randomTest.hasFailed() && adaptiveRandomTest.hasFailed()) {
                ties++;
            } else if (randomTest.hasFailed()) // RT Wins 
            {
                RTWin++;
            } else if (adaptiveRandomTest.hasFailed()) // ART Wins
            {
                ARTWin++;
            }
        } while (!randomTest.hasFailed() && !adaptiveRandomTest.hasFailed());
    }

    private void createFirstCoordinate() {
        Coordinate firstCoord = randomTest.generateCoordinate();
        failX = randomTest.getFailureX();
        failY = randomTest.getFailureY();

        adaptiveRandomTest.getCoordinates().add(firstCoord);
    }

    public void cancelExecutorService(ScheduledExecutorService executorService) {
        try {
            executorService.shutdown();
            if (!executorService.awaitTermination(300, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println(e);
            executorService.shutdownNow();
        }
    }

    public void clearNumericalPanels(NumericalPanel nPanel) {
        randomTest.clearCoordinates();
        adaptiveRandomTest.clearCoordinates();
        ARTWin = 0;
        RTWin = 0;
        ties = 0;

        nPanel.clearPanels(failX, failY, randomTest, adaptiveRandomTest);
    }

}
