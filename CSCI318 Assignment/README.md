# VisualART User Manual
A Java program for testing software which visualises the Random Testing and Adaptive Random Testing algorithms,
as well as allows software developers to test their programs using the two algorithms.
This is also a Maven project which uses the specified libraries in pom.xml
### Created in collaboration with
Samuel Black,
Trang Nguyen,
Luke Masliah,
Michaela Judge, and 
Minh Huy Tran

# Purpose of VisualART
VisualART is a black-box testing tool for use by developers in object-oriented programming. It allows users to test classes within a program to find the combination of field values (if any) that causes malfunction within the program using Adaptive Random Testing and Random Testing. It visualises results for the user, allowing them to fix any potential errors within their selected program, and thereby facilitating smoother releases of software and saving the time and money of developers. 
Its secondary purpose is as a teaching resource to show the value of testing techniques in developing code, or more aptly, the value of appropriate testing techniques to reduce time spent on a task.

# Getting started: Installation Instructions and System Requirements
The .jar files to be tested must be compiled with Java 14 or older.
To install VisualART, simply download the executable files. No further installation is required

## Testing Simulations
VisualART is by default launched in simulator mode, a section that allows visualisations of two popular software testing techniques, Random Testing and Adaptive Random Testing.
 
## Getting to Know Your Screen
### The Navigation Panel
![Alt text](https://github.com/TrangNgyn/University-Assignments/blob/master/Java%20Projects/RTandART-master/Snapshots/Navigation%20Panel.png?raw=true "The Navigation Panel")
The left-most panel of the screen, The Navigation Panel, shows the tools in which a user can toggle the simulation performed by the program. The checkbox labelled non-numerical input (A) is the box in which to toggle to Testing Mode.

The values within Test Parameters are the options a user can change to alter the simulation, Error Size (B), Test Speed (C), and Run Times (D).
For more information on the specifics of these values, see Running a Simulation.
The Start Button (E) begins a simulation when ready.

### The Simulation Panel
 ![Alt text](https://github.com/TrangNgyn/University-Assignments/blob/master/Java%20Projects/RTandART-master/Snapshots/Simulation%20Panel.png?raw=true "The Simulation Panel")
 
The Simulation Panel is where the simulation of RT and ART testing is visualised.

There are two large boxes, the Test Spaces, in which the two methods of testing are shown. 
The left Test Space (A) is for Random Testing, and the right Test Space is for ART.

When the simulation begins, a failure region is shown in each of the Test Spaces as a red square dictated in size by the Error Size value in the Navigation Panel. The program then begins producing testing points in the Test Spaces, visualised by green squares, using the two different testing techniques, until one or the other hits the predefined failure region, thereby “winning”, and ending the current round of simulations. If multiple Run Times were selected, only the final run will be seen.
 
Results from a simulation are displayed beneath the Test Spaces (C), with a tally being provided for the number of times each of the techniques wins, as well as how many times they tie.
 
## Running a Simulation
Before running a simulation, you have the option of changing the default parameters of the program, to see how the two testing techniques compare in different scenarios.
Error Size adjusts the size of the Failure Region of the program as a percentage of the total space. It is input as a value between 0 and 1.
Test Speed adjusts the speed at which each new Test Point is displayed for the duration of a single simulation. This value is only relevant if there is only one run occurring. If Run Times is greater than one, this value will be defaulted to 1, thereby removing waits in-between each run. The higher the value, the slower the run speed.
Run Times is the number of runs with the given values the program should run. This is to get an overall understanding of how the two techniques fair when testing. If this option is greater than one, you will only see the layout of the final run in the Test Spaces, as well as the total tallies in the results section.
 
When these three options have been changed as desired, the Start Button in the Navigation Panel will run the simulation.

### Manual Testing
When the non-numerical input box is checked, the program switches to Manual Testing mode. Here, a user can select their file to be tested and see any potential values that cause errors within their program.
 
### Getting to Know Your Screen
- The Navigation Panel: The left-most panel of the screen, The Navigation Panel, shows the tools in which a user can toggle between simulation and manual mode (A) and the button to begin the testing (B).
- The Testing Panel: The testing panel holds the options for selecting files to test, as well as sections for the results of the tests.

![Alt text](https://github.com/TrangNgyn/University-Assignments/blob/master/Java%20Projects/RTandART-master/Snapshots/TestingPanel.png?raw=true "The Testing Panel")

The first button within the section (A) brings up a directory window to select the location of the folder containing the file to be tested.
The first drop-down (B) takes the information from the chosen folder and provides a selection of classes to choose from to test.
The second drop-down (C) takes the information from the chosen class and provides a selection of constructors to choose from to test.
Below these options lies the results panels, with the top panel (D) being for Random Testing, and the Bottom (E) for ART. Within each Results Panel, the number of Test Cases and the number of Errors detected (F) is displayed, and the Error log (G) provides details on any errors detected.

## Running a Test
### Steps Before Using Testing Functionality
When conducting a test, you must first create a java class named Test.java, that includes the mainline of your program.
This step exists to make it explicit to the software what you are planning to test.

You must also override the toString() method in each of the classes that will be used for testing to return each variable in order from top to bottom separated by a space. This is done to pass the object variables as arguments to the Test main. (see below for example)

Choosing your class under test (incl. Choosing its constructor)
From the Manual Testing screen, select the first button in the Testing Panel to bring up a file selector. Here, navigate to and select the desired subdirectory.

From here, the program will read all of the classes associated with the program, which will appear in the first drop-down menu. Select the class you want to test.
 
Next, use the second drop-down menu to select which constructor you want to test from the chosen class.
 
Once these fields have been selected, you are ready to test your program.
 
### Testing your program
To begin Testing, press the Start Button once the file fields have been filled in. From here, VisualART will prompt you to enter the upper and lower limits of data to be tested in a series of popups.

If the argument that needs to be entered an upper and lower limit for is a String or Char type, you will need to enter the size of the string, as well as the range of ASCII values valid to test.
Once this occurs, the program will begin implementing test cases. Any errors discovered will appear in the Error Log. The given testing technique will stop when an error is found. i.e. If an error is found from ART process, the process immediately stops executing, but RT process continues until it also finds an error.
 
### Reading the test’s output
Outputs for the given tests will appear in the Results Panel. Each Results Panel (RT and ART) will keep a running total of the number of test cases performed, and the number of errors detected.
 
The Error Log provides details on any errors encountered within a given test case. If an error occurs, The Error Log First prints the combination of values that produced the error in blue, underneath a new heading of “the test case revealed the error”.

The Error Log then provides details on what error occurs from these values.
For example, in the below ART Results Panel, we can see that at Test Case number 120, an error occurred with the invalid date being entered of the 30/02/1806. This causes a Parse Exception because this is not a possible date, a result that the programmer may want to see, however, they should make sure to defensively program to prevent the program from crashing if this date was entered.