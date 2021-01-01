public class MyDate
{
	private int day, month, year; //variables to create MyDate objects
	
	/*Constructor for MyDate object*/
	public MyDate(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	/*Getter for MyDate object day*/
	public int getDay()
	{
		return day;
	}
	
	/*Getter for MyDate object month*/
	public int getMonth()
	{
		return month;
	}
	/*Getter for MyDate object year*/
	public int getYear()
	{
		return year;
	}
	
	/*Method to check if input date is valid*/
    public boolean isDateValid()
	{
		boolean dateValid = false;
		int monthLength;
		boolean yearCorrect = false, monthCorrect = false, dayCorrect = false;
		
		/*Checks that the year is correct (must be at least 15 October 1582 for Zeller's congruence)*/
		if (getYear() > 1582)
			yearCorrect = true;
		else if ((getYear() == 1582) && (getMonth() >= 10) && (getDay() >= 15))
			yearCorrect = true;
		
		/*Checks if the month is correct*/
		if ((getMonth() >= 1) && (getMonth() <= 12))
			monthCorrect = true;
		
		monthLength = getMonthLength(); //gets the length of the given month
		
		/*Checks if the days is correct, depending on the length of the month*/
		if (monthLength == 31)
		{
			if ((getDay() >= 1) && (getDay() <= 31))
				dayCorrect = true;
		}
		else if (monthLength == 30)
		{
			if ((getDay() >= 1) && (getDay() <= 31))/***** BUGS *****/
				dayCorrect = true;
		}
		else if (monthLength == 29)
		{
			if ((getDay() >= 1) && (getDay() <= 31))/***** BUGS *****/
				dayCorrect = true;
		}
		else
			if ((getDay() >= 1) && (getDay() <= 31))/***** BUGS *****/
				dayCorrect = true;
		
		/*Checks that the year and the month and the day are all valid*/				
		if ((yearCorrect == true) && (monthCorrect == true) && (dayCorrect == true))	
			dateValid = true;
		
		return dateValid; 
	}
	
	/*method to calculate the length of a month*/
	public int getMonthLength()
	{
		boolean leapYear = false; //initialises variable
		int monthLength = 0; //initialises variable
		
		/*If the month is Jan, March, May, Jul, Aug, Oct, Dec, sets month length to 31*/
		if ((getMonth() == 1) || (getMonth() == 3) || (getMonth() == 5) || (getMonth() == 7) ||
			(getMonth() == 8) || (getMonth() == 10) || (getMonth() == 12))
				monthLength = 31;
		
		/*If the month is Apr, June, Sep, November, sets the month length to 30*/		
		else if ((getMonth() == 4) || (getMonth() == 6) || (getMonth() == 9) || (getMonth() == 11))
				monthLength = 30;
		
		/*If the month is Feb, checks for a leap yeat*/				
		else if (getMonth() == 2)
		{
			leapYear = checkLeapYear();

			if (leapYear == true) //if it's a leap year, month length is 29
				monthLength = 29;
			else 
				monthLength = 28; //if it's not a leap year, month length is 28
		}
		return monthLength;		
	}
	
	/*Method to check if a given year is a leap year*/
	public boolean checkLeapYear()
	{
		boolean leapYear;
		
		/*If the year is divisible by 4 and isn't divisible by 100, it is a leap year*/
		if ((getYear() % 4 == 0) && (getYear() % 100 != 0))
			leapYear = true;
		/*If the year is divisible by 4 and divisible by 100 but also divisible by 400, it is a 
			leap year*/
		else if ((getYear() % 4 == 0) && (getYear() % 100 == 0) && (getYear() % 400 == 0))
			leapYear = true;
		else
			leapYear = false;
		
		return leapYear;
	}
	
	public String toString() {
		return " " + day + " " + month + " " + year;
	}
}