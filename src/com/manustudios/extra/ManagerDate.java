package com.manustudios.extra;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;


public class ManagerDate {

	
	@SuppressLint("SimpleDateFormat")
	public String convertDate(String date){
		
		String monthString = date.substring(8, 11);
		int year = Integer.parseInt(date.substring(12, 16));
		int month = convertMonth(monthString);
		int day = Integer.parseInt(date.substring(5, 7));
		
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String today =formatter.format(new Date());
		
		int thisyear = Integer.parseInt(today.substring(0, 4));
		int thismonth = Integer.parseInt(today.substring(5, 7));
		int thisday = Integer.parseInt(today.substring(8, 10));
		
		if(year== thisyear){
			if(month==thismonth){
				if(day==thisday){
					date = "today";
				}
				else if(day+1==thisday){
					date = "yesterday";
				}
				else{
					date = date.substring(0, 7);
				}
				
			}
			else{
				date = date.substring(0, 11);
			}
			
		}else{
			date = date.substring(0, 16);
		}
		
		return date;
	}
	
	private int convertMonth(String month){
		int newMonth = 0;
		
		if(month == "Jan"){
			newMonth = 1;
		}
		else if(month == "Feb"){
			newMonth = 2;
		}
		else if(month == "Mar"){
			newMonth = 3;
		}
		else if(month == "Apr"){
			newMonth = 4;
		}
		else if(month == "May"){
			newMonth = 5;
		}
		else if(month == "Jun"){
			newMonth = 6;
		}
		else if(month == "Jul"){
			newMonth = 7;
		}
		else if(month == "Aug"){
			newMonth = 8;
		}
		else if(month == "Sep"){
			newMonth = 9;
		}
		else if(month == "Oct"){
			newMonth = 10;
		}
		else if(month == "Nov"){
			newMonth = 11;
		}
		else if(month == "Dec"){
			newMonth = 12;
		}

		
		return newMonth;
		
	}
	
	
	
}
