package org.spec.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;



public class UI_Utilities
{	
	/*******************************************************************************
	 *	Symbol		Meaning							Type			
	 *	G			Era								Text			
	 *	y			Year							Number			
	 *																
	 *	M			Month							Text or Number	
	 *																
	 *																
	 *																
	 *																
	 *	d			Day in month					Number			
	 *																
	 *	h			Hour (1-12, AM/PM)				Number			
	 *																
	 *	H			Hour (0-23)						Number			
	 *																
	 *	k			Hour (1-24)						Number			
	 *																
	 *	K			Hour (0-11 AM/PM)				Number			
	 *																
	 *	m			Minute							Number			
	 *																
	 *																
	 *	s			Second							Number			
	 *																
	 *	S			Millisecond (0-999)				Number			
	 *	E			Day in week						Text			
	 *																
	 *	D			Day in year (1-365 or 1-364)	Number			
	 *																
	 *	F			Day of week in month (1-5)		Number			
	 *	w			Week in year (1-53)				Number			
	 *	W			Week in month (1-5)				Number			
	 *	a			AM/PM							Text			
	 *																
	 *	z			Time zone						Text			
	 *																
	 *																
	 *	�			Escape for text					Delimiter		
	 *	�			Single quote					Literal			
	 */

	public static String formatDate(Date date, String dateFormat) {
		
		if(date == null)
			return "";
		SimpleDateFormat sdFormatter = new SimpleDateFormat(dateFormat);
		return sdFormatter.format(date);
	}
	
	public static Boolean stringContains(String stringToFind, String stringToSearch)
	{
		stringToSearch = stringToSearch.toLowerCase();
		stringToFind = stringToFind.toLowerCase();
		return stringToSearch.indexOf(stringToFind, 0) != -1;
	}
	
	public static Properties toProperties(String propString)
	{
		Scanner scan = new  Scanner(propString);
		Properties p = new Properties();
		while(scan.hasNext())
		{
			String line = scan.nextLine();
			if(line.trim().length() > 0)
			{
				String[] split = line.split("\\s*=\\s*");
				if(split.length > 1)
					p.put(split[0].trim(), split[1].trim());
			}
		}
		return p;
	}
	
	
	public static String loadTextFile(String file)
	{
		String s = "";
		try {
			File f = null;
			URL url = UI_Utilities.class.getResource(file);
			if(url != null)
				f = new File(url.toURI());
			else
				f = new File(file);
			
			Scanner scan = new Scanner(f);
			while(scan.hasNext())
			{
				s += scan.nextLine() + "\n";
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static double getTimeDifference(Date date1, Date date2, int durationUnit) {
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		before.setTime(date1);
		after.setTime(date2);
		
		double result = 0;
		if(durationUnit == UI_Constants.DURATION_UNIT_IN_MINUTE)
		{
			result = 
				(after.get(Calendar.HOUR_OF_DAY)*60*60+60*after.get(Calendar.MINUTE)) + after.get(Calendar.SECOND) - 
				(before.get(Calendar.HOUR_OF_DAY)*60*60+60*before.get(Calendar.MINUTE) + before.get(Calendar.SECOND)); 
		}
		return result/60.0;
	}
	
	public static int compareDateTime(Date date1, Date date2, boolean compareDate) {
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal1.setTime(date1);
		cal2.setTime(date2);
		if(compareDate)
		{
			if(cal1.get(Calendar.YEAR) > cal2.get(Calendar.YEAR))
				return 1;
			else if(cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR))
				return -1;
			else
			{
				if(cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH))
					return 1;
				else if(cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH))
					return -1;
				else
				{
					if(cal1.get(Calendar.DAY_OF_MONTH) > cal2.get(Calendar.DAY_OF_MONTH))
						return 1;
					else if(cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH))
						return -1;
					else
						return 0;
				}
			}
		}
		else
		{
			if(cal1.get(Calendar.HOUR_OF_DAY) > cal2.get(Calendar.HOUR_OF_DAY))
				return 1;
			else if(cal1.get(Calendar.HOUR_OF_DAY) < cal2.get(Calendar.HOUR_OF_DAY))
				return -1;
			else
			{
				if(cal1.get(Calendar.MINUTE) > cal2.get(Calendar.MINUTE))
					return 1;
				else if(cal1.get(Calendar.MINUTE) < cal2.get(Calendar.MINUTE))
					return -1;
				else
					return 0;
			}
		}
	}

	

	public Date modifyDateForHibernateCriteria(Date date, boolean isBeginning) {
		
		Calendar cal = Calendar.getInstance();
		if(date != null)
		{
			cal.setTime(date);
			if(isBeginning)
			{
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 000);
				cal.set(Calendar.AM_PM, Calendar.AM);
			}
			else
			{
				cal.set(Calendar.HOUR, 11);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				cal.set(Calendar.MILLISECOND, 998);
				cal.set(Calendar.AM_PM, Calendar.PM);
			}
			return cal.getTime();
		}
		else
			return null;
	}
	
	
	
	private static String behavioralHealthStatus(int statusId)
	{
		if(statusId == 0)
			return "N/A";
		else if(statusId == 1)
			return "Yes";
		else if(statusId == 2)
			return "No";
		else
			return "";
	}
	
	
	
	public static String getFolderLocation(int fileId)
	{
		
		int temp = fileId / 100;
		String result = "";
		if(temp > 0)
		{
			String str = new Integer(temp).toString();
			for(int i=0; i < str.length(); i++)
				result += str.charAt(i) + "\\";			
		}
		return result;
	}
	
	public static boolean validateFolderPath(String folderPath) {

		File temp = new File(folderPath);
		if(temp.exists())
			return true;
		else
		{		
			return temp.mkdirs();
		}
				
	}
	
	
	public static boolean hasField(String fieldName, Object obj)
	{
		try 
		{
			Method m = obj.getClass().getMethod("get" + capitalize(fieldName));
			return m != null;
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * Allows you to use java reflect to reference a field using dot delimited format. 
	 * You can reference for exam the semesterId of a CourseFaculty with the path:
	 * "semesterDate.semester.semesterId"
	 * 
	 * @param fieldName 	name of the field to be retrieved in dot delimited format
	 * @param obj			reference of the object containing the field
	 * @return
	 */
	public static Object getField(String fieldName, Object obj)
	{
		String[] parts = fieldName.split("\\.");
		Object current = obj;
		for(String field : parts)
		{
			try 
			{
				Method m = current.getClass().getMethod("get" + capitalize(field));
				current =  m.invoke(current);				
			} 
			catch (Exception e) 
			{
				//e.printStackTrace();
				return null;
			} 			
		}
		return current;
	}
	
	public static void setField(String fieldName, Object obj, Object value)
	{
		String[] parts = fieldName.split("\\.");
		Object current = obj;

		try 
		{
			Method m = current.getClass().getMethod("get" + capitalize(fieldName));
			Method setter = current.getClass().getMethod("set" + capitalize(fieldName), m.getReturnType());
			setter.invoke(obj, value);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
		} 			

	}
	
	public static Object getFieldWithMap(String field, Object obj)
	{
		if(obj instanceof Map)
		{
			return ((Map) obj).get(field);
		}
		else
		{
			return getField(field, obj);
		}
	}
	
	public static String capitalize(String s)
	{
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
	
	public static int getAge(Date dobt)
	{
		if(dobt == null)
			return 0;
		Calendar dob = Calendar.getInstance();  
		dob.setTime(dobt);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))  
		age--;  
		return age;
	}
}