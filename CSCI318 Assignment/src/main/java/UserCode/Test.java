package UserCode;
/*-----------------------------------------------------
My name: Michaela Judge
My student number:6439378
My course code: CSIT111
My email address: mrmj587@uowmail.edu.au
Assignment number: 3
-----------------------------------------------------*/

/*Program to caclulate the day of the week of a date, the week of the month of that date,
 and display that month's calendar*/

import java.util.Scanner; //scanner class allows input from keyboard
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class Test
{
	private MyDate myDate; //private MyDate Object
	
	/*Enum to store the days of the week*/
	enum Day {Saturday, Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, }
	private Day day;
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in); //creates new scanner object for user input
		int dayTemp, monthTemp, yearTemp; //variables to temporarily store user input
		int week; //variable store the integer value week (1,2, etc.)		
		
		/*Stores arguements input by user into variables for date*/
		dayTemp = Integer.parseInt(args[0]);
		monthTemp = Integer.parseInt(args[1]);
		yearTemp = Integer.parseInt(args[2]);
		
		MyDate myDate = new MyDate(dayTemp, monthTemp, yearTemp); //creates new MyDate object
		
		
		/*While loop prevents the program from continuing until user has input a valid date*/
		if (myDate.isDateValid() == false)
		{
			System.out.println("INVALID DATE!");
		} else{
			String inputDate = dayTemp + " " + monthTemp + " " + yearTemp;
			DateFormat formatter = new SimpleDateFormat("dd MM yyyy");
			formatter.setLenient(false);
			try {
			formatter.parse(inputDate);
			} catch (Exception e){
				e.printStackTrace();
				return;
			}
		
			Test myCalendar = new Test(myDate); //creates new MyCalendar Object
			
			Day day = Day.Saturday; //initialises day enum
			
			day = myCalendar.dayOfWeek(); //sets day to returned value of dayOfWeek 
			week = myCalendar.weekOfMonth(); //sets week to returned value of weekOfMonth
			
			/*Prints out the date, what day of the week it is and what week it is located in (in words)*/
			System.out.printf("%02d/%02d/%04d is a %s and located in the %s week of %s %d\n" , 
				myDate.getDay(), myDate.getMonth(), myDate.getYear(), day, myCalendar.weekToWords(week), 
					myCalendar.monthToWords(), myDate.getYear());	
			
			System.out.printf("The calendar of %s %d is:\n\n" , myCalendar.monthToWords(), 
				myDate.getYear());
			myCalendar.printCalendar(); //prints the calendar for the month of the date
		}
	}
	
	/*MyCalendar object constructor*/
	public Test(MyDate myDate)
	{
		this.myDate = myDate;
	}
	
	/*Method to calculate the day of the week for a date using Zeller's congruence*/
	public Day dayOfWeek()
	{
		int dayOfWeek; //stores the day of the week
		int tempMonth; //stores the month after any adjustments needed
		int tempYear; //stores the yeat after any adjustments needed
		int yearCentury; //stores the year of the century
		int zeroCentury; //stores the zero-based century
		Day day = Day.Saturday; //initialises day variable
		
		/*If the month is Jan or Feb, adds 12 to month and minuses one from year to follow the 
			rules of Zeller's congruence*/
		if ((myDate.getMonth() == 1) || (myDate.getMonth() == 2))
		{
			tempMonth = myDate.getMonth() + 12;
			tempYear = myDate.getYear() - 1;
		}
		else 
		{
			tempMonth = myDate.getMonth();
			tempYear = myDate.getYear();
		}
		
		yearCentury = tempYear % 100; //calculates the year of the century
		zeroCentury = tempYear / 100; //calculates the zero based century

		/*Calculates the day of the week of the user's date*/
		dayOfWeek = ((myDate.getDay() + ((13*(tempMonth+1))/5) + yearCentury + (yearCentury / 4) + 
			(zeroCentury / 4) + (5 * zeroCentury)) % 7);
		
		/*Switch converts the integer value of dayOfWeek to the enum value for day*/
		switch (dayOfWeek){
			case 0:
				day = day.Saturday;
				break;
			case 1:
				day = day.Sunday;
				break;
			case 2:
				day = day.Monday;
				break;
			case 3:
				day = day.Tuesday;
				break;
			case 4:
				day = day.Wednesday;
				break;
			case 5:
				day = day.Thursday;
				break;
			case 6:
				day = day.Friday;
				break;	
		}
		return day;
	}
	
	/*Method to calculate the week of the month a given date falls in*/
	public int weekOfMonth()
	{
		double weekMonth;
		
		int firstDayMonth = firstOfMonthDay(); //finds what day of week the first of the month is
		if (firstDayMonth == 0) //changes from Zeller's congruence form of saturday to normal
			firstDayMonth = 6;
		else
			firstDayMonth -= 1;	//changes from Zeller's congruence form of day to normal
		
		/*Calculates the week of the month the date falls in*/
		weekMonth = Math.ceil((myDate.getDay() + firstDayMonth)/ 7.0);
		
		return (int)weekMonth; //returns weekMonth as an integer
	}
	
	/*Method to calculate what day of the week the first day of the month falls in*/
	public int firstOfMonthDay()
	{
		int dayOfWeek; //stores the day of the week
		int tempMonth; //stores the month after any adjustments needed
		int tempYear; //stores the yeat after any adjustments needed
		int yearCentury; //stores the year of the century
		int zeroCentury; //stores the zero-based century
		
		/*If the month is Jan or Feb, adds 12 to month and minuses one from year to follow the 
			rules of Zeller's congruence*/
		if ((myDate.getMonth() == 1) || (myDate.getMonth() == 2))
		{
			tempMonth = myDate.getMonth() + 12;
			tempYear = myDate.getYear() - 1;
		}
		else 
		{
			tempMonth = myDate.getMonth();
			tempYear = myDate.getYear();
		}
		
		yearCentury = tempYear % 100; //calculates the year of the century
		zeroCentury = tempYear / 100; //calculates the zero based century
		
		/*Calculates the day of the week of the first of the month*/
		dayOfWeek = ((1 + ((13*(tempMonth+1))/5) + yearCentury + (yearCentury / 4) + 
			(zeroCentury / 4) + (5 * zeroCentury))%7);
		
		return dayOfWeek;
	}
	
	/*Method to convert integer of month to the written word (i.e. 06 to June)*/
	public String monthToWords()
	{
		String monthInText = ""; //initialises the variable
		
		switch (myDate.getMonth()){
			case 1:
				monthInText = "January";
				break;
			case 2:
				monthInText = "February";
				break;
			case 3:
				monthInText = "March";
				break;
			case 4:
				monthInText = "April";
				break;
			case 5:
				monthInText = "May";
				break;
			case 6:
				monthInText = "June";
				break;
			case 7:
				monthInText = "July";
				break;	
			case 8:
				monthInText = "August";
				break;	
			case 9:
				monthInText = "September";
				break;	
			case 10:
				monthInText = "October";
				break;	
			case 11:
				monthInText = "November";
				break;	
			case 12:
				monthInText = "December";
				break;	
		}
		return monthInText;
	}
	
	/*Method to convert integer of week to the written word (i.e. 1 to first)*/
	public String weekToWords(int week)
	{
		String weekInText = ""; //initialises the variable
		
		switch (week){
			case 1:
				weekInText = "first";
				break;
			case 2:
				weekInText = "second";
				break;
			case 3:
				weekInText = "third";
				break;
			case 4:
				weekInText = "fourth";
				break;
			case 5:
				weekInText = "fifth";
				break;
		}
		return weekInText;
	}
	
	/*Method to print the calendar of the month the user inputs*/
	public void printCalendar()
	{
		int monthLength = myDate.getMonthLength(); //finds the month length
		int count = firstOfMonthDay(); //finds the first day of the month
		
		System.out.println("SUN    MON    TUE    WED    THU    FRI    SAT");
		if (count == 0) //changes from Zeller's congruence form of saturday to normal
			count = 6;
		else
			count -= 1;	//changes from Zeller's congruence form of day of the week to normal
		
		if (count > 0) //accounts for what day of the week month starts on
		{
			for (int i = 1; i <= count; i++) 
			{
				System.out.printf("%7s", " "); //adds space until the first day of month
			}
		}
		
		/*Prints the calendar of the month month*/
		for (int i = 1; i <= monthLength; i++)
		{
			if (count >= 7)
			{
				count = 1;
				System.out.println();
				System.out.printf("%-3d%4s", i, " ");	
			}
			else
			{
				System.out.printf("%-3d%4s", i, " ");
				count++;
			}
		}
		System.out.println();	
	}
}
