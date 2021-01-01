package Program;

import GUIDataInputs.IntegerInputFrame;
import java.util.Random;

public class CSCI318 {

    public static final Random generator = new Random(System.nanoTime());
    //public static int objectMutator = 20;

    public static void main(String[] args) {
        GUI.TestFrame frame = new GUI.TestFrame();
        frame.setVisible(true);
    }
}

// To-Do
/*
    Non-Numerical ART/RT implementation

    1. Build a bank from the ground up using the $10 in Lukes wallet. (class bank pre-defined functions). √√√

    2. Distance algorithms - 3:
            Elementary Distance
            Type distance
            Field distance

    3. generate test cases
            generate: 
                string 
                int 
                double for now.
            generate k test cases:
                Select best candidate

    4. GUI (input)
            display class we're testing.
            display amount of functions in test class.
            single or multiple tests (define upper bound limit of tests xoxo).
            display how many errors RT/ART find respectively.
            display error log for each algorithm.
            optional (something visually showing algorithms).

    Phase 2.
    Implement reflection for class/function identification.

 */
