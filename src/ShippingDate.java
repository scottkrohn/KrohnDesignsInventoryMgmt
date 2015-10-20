import java.io.Serializable;


public class ShippingDate implements Serializable{
	private int day;
	private int month;
	private int year;
	
	public ShippingDate(int month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}
	
	public ShippingDate() {
		day = 0;
		month = 0; 
		year = 0;
	}
	
	public boolean equals(ShippingDate date) {
		if(date.day == day && date.month == month && date.year == year) {
			return true;
		}
		return false;
	}
	
	/*
	 * Returns true if the argument ShippingDate comes before this object.
	 */
	public boolean comesBeforeDate(ShippingDate date) {
		 // If the year of this object is less than the year of the argument, return true
		 // because this object's year comes before the arguments year.
		if(date.year > year) {
			return true;	
		}
		else if(date.year < year) {
			return false;
		}
		
		// This point of the method is only reached if the year of the argument and object are equal.
		
		// If the month of this object is less than the month of the month of the arguments, return true
		// because this object's month comes before the month of the argument.
		if(date.month > month) {
			return true;
		}
		else if(date.month < month) {
			return false;
		}
		
		// This point of the method is only reached if the argument and object have matching year/month.
		
		// If the day of this object is less than the day of the argument object, return true
		// because this object's day comes before the day of the argument.
		if(date.day >= day) {
			return true;
		}
		else if(date.day < day) {
			return false;
		}
	
		// The default is to return false is nothing matches.
		return false;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void setDay (int day) {
		this.day = day;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getYear() {
		return year;
	}
	
	public String toString() {
		return new String(month + "/" + day + "/" + year);
	}

}
