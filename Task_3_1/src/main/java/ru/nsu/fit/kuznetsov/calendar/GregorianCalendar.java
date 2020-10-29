package ru.nsu.fit.kuznetsov.calendar;

import java.lang.IllegalArgumentException;
import java.lang.String;

/**
 * This class allows us to use methods from the interface Calendar using Gregorian Calendar Rules.
 * All of this methods work only with dates of A.D.
 */
public class GregorianCalendar implements Calendar {
  int year;
  int month;
  int dayOfMonth;
  static int[] maxDaysInMonthsInCommonYear = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
  static int[] maxDaysInMonthsInLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  /**
   * creates new Gregorian Calendar
   * @param year the year to be set
   * @param month the month to be set
   * @param dayOfMonth the day to be set
   */
  public GregorianCalendar(int year, int month, int dayOfMonth) {
    this.year = year;
    if (month > 12 || month <= 0) {
      throw new IllegalArgumentException("incorrect month!");
    }
    this.month = month;
    if (dayOfMonth <= 0) {
      throw new IllegalArgumentException("incorrect dayOfMonth!");
    }
    if (isLeap(year) && dayOfMonth > maxDaysInMonthsInLeapYear[month - 1]) {
      throw new IllegalArgumentException("incorrect dayOfMonth!");
    } else if (!isLeap(year) && dayOfMonth > maxDaysInMonthsInCommonYear[month - 1]) {
      throw new IllegalArgumentException("incorrect dayOfMonth!");
    }
    this.dayOfMonth = dayOfMonth;
  }

  /**
   * Checks whether the year leap or not.
   * @param year the year to be checked
   * @return true if the year is leap and false otherwise
   */
  private static boolean isLeap(int year) {
    return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
  }

  /**
   * Converts the date into number of days.
   * @param day the day of month to convert
   * @param month the month to convert
   * @param year the year to convert
   * @return the number of days this date contains
   */
  private static int dateToDays(int day, int month, int year) {
    int days = day;
    for (int i = 1; i < year; i++) {
      if (isLeap(i)) {
        days++;
      }
      days += 365;
    }
    for (int i = 1; i < month; i++) {
      if (isLeap(year)) {
        days += maxDaysInMonthsInLeapYear[i - 1];
      } else {
        days += maxDaysInMonthsInCommonYear[i - 1];
      }
    }
    return days;
  }

  /**
   * Adds days to the Calendar.
   * @param days the count of days to be added
   */
  @Override
  public void addDays(int days) {
    days += dayOfMonth;
    for (int i = month - 1; i < 12; i++) {
      if (isLeap(year)) {
        if (days - maxDaysInMonthsInLeapYear[i] <= 0) {
          break;
        } else {
          days -= maxDaysInMonthsInLeapYear[i];
          month++;
          if (month > 12) {
            month /= 12;
            year++;
            i = -1;
          }
        }
      } else if (days - maxDaysInMonthsInCommonYear[i] <= 0) {
        break;
      } else {
        days -= maxDaysInMonthsInCommonYear[i];
        month++;
        if (month > 12) {
          month /= 12;
          year++;
          i = -1;
        }
      }
    }
    dayOfMonth = days;
  }

  /**
   * Adds years to the Calendar.
   * @param years the count of years to be added
   */
  @Override
  public void addYears(int years) {
    int daysToAdd = 0;
    int lastYear = year + years;
    for (int i = year + 1; i <= lastYear; i++) {
      if (isLeap(i)) {
        daysToAdd++;
      }
      daysToAdd += 365;
    }
    addDays(daysToAdd);
  }

  /**
   * Adds months to the Calendar.
   * @param months the count of months to be added
   */
  @Override
  public void addMonths(int months) {
    int daysToAdd = 0;
    int lastMonth = month + months;
    int checkYear = year;
    for (int i = month + 1; i <= lastMonth; i++) {
      if (i > 12) {
        checkYear++;
      }
      if (isLeap(checkYear)) {
        daysToAdd += maxDaysInMonthsInLeapYear[(i - 1) % 12];
      } else {
        daysToAdd += maxDaysInMonthsInCommonYear[(i - 1) % 12];
      }
    }
    addDays(daysToAdd);
  }

  /**
   * Evaluates a weekday of the Calendar using constant day 25.10.2020.
   * @return the name of weekday as String
   */
  @Override
  public String weekDay() {
    int constDay = 25;
    int constMonth = 10;
    int constYear = 2020;
    int constWeekDay = 7;
    int constDate = dateToDays(constDay, constMonth, constYear);
    int resDate = dateToDays(dayOfMonth, month, year);
    resDate -= constDate;
    int ans = resDate % constWeekDay;
    if (ans == 0) {
      ans = 7;
    } else if (ans < 0) {
      ans += 7;
    }
    switch (ans) {
      case (1):
        return "Monday";
      case (2):
        return "Tuesday";
      case (3):
        return "Wednesday";
      case (4):
        return "Thursday";
      case (5):
        return "Friday";
      case (6):
        return "Saturday";
      case (7):
        return "Sunday";
    }
    return "";
  }

  /**
   * Returns the number of days in the current Calendar.
   * @return the number of days
   */
  @Override
  public int day() {
    return dayOfMonth;
  }

  /**
   * Returns the current month of the Calendar.
   * @return the current month
   */
  @Override
  public int month() {
    return month;
  }

  /**
   * Returns the current year of the Calendar.
   * @return the current year
   */
  @Override
  public int year() {
    return year;
  }

  /**
   * Subscribes the date from the Calendar.
   * First of all evaluates the count of days between two dates.
   * After that evaluates the count of years from the beginning of the next year in Date.
   * The count of months and days starts evaluate from 01.01. of the next after last year to be added.
   * @param days days of the Date to add
   * @param months months of the Date to add
   * @param years years of the Date to add
   */
  @Override
  public void subDate(int days, int months, int years) {
    int dateToSubInDays = dateToDays(days,months,years);
    int currDateInDays = dateToDays(dayOfMonth,month,year);
    int resultNumOfDays = currDateInDays - dateToSubInDays;
    dayOfMonth = month = 0;
    int countLeap = year;
    year = 0;
    while(resultNumOfDays >= 366){
      countLeap++;
      if(countLeap %4 == 0){
        resultNumOfDays--;
      }
      resultNumOfDays-=365;
      year++;
    }
    for(int i =0; i< 12; i++){
      if(countLeap %4 == 0){
        if(resultNumOfDays - maxDaysInMonthsInLeapYear[i] < 0){
          break;
        }else{
          resultNumOfDays -= maxDaysInMonthsInLeapYear[i];
          month++;
        }
      }else{
        if(resultNumOfDays -maxDaysInMonthsInCommonYear[i] < 0){
          break;
        } else{
          resultNumOfDays -= maxDaysInMonthsInCommonYear[i];
          month++;
        }
      }
    }
    dayOfMonth = resultNumOfDays;
  }

  /**
   * Evaluates how many days to new year.
   * @return the number of days
   */
  @Override
  public int daysToNewYear() {
    int curr = dateToDays(dayOfMonth, month, year);
    int NextYear = year;
    if (dayOfMonth > 1 || month > 1) {
      NextYear++;
    }
    int newYear = dateToDays(1, 1, NextYear);
    return newYear - curr - 1;
  }

  /**
   * Evaluates the date of the next friday 13.
   * @return the date of friday 13 as Calendar
   */
  @Override
  public Calendar nextFriday13() {
    String currWeekDay = this.weekDay();
    int nextFridayDay = dayOfMonth, nextFridayMonth = month, nextFridayYear = year;
    switch (currWeekDay) {
      case ("Friday"):
        break;
      case ("Monday"):
        nextFridayDay += 4;
        break;
      case ("Tuesday"):
        nextFridayDay += 3;
        break;
      case ("Wednesday"):
        nextFridayDay += 2;
        break;
      case ("Thursday"):
        nextFridayDay += 1;
        break;
      case ("Saturday"):
        nextFridayDay += 6;
        break;
      case ("Sunday"):
        nextFridayDay += 5;
        break;
    }
    if (isLeap(nextFridayYear)) {
      if (nextFridayDay > maxDaysInMonthsInLeapYear[nextFridayMonth - 1]) {
        nextFridayDay %= maxDaysInMonthsInLeapYear[nextFridayMonth - 1];
        nextFridayMonth++;
      }
    } else if (!isLeap(nextFridayYear)) {
      if (nextFridayDay > maxDaysInMonthsInCommonYear[nextFridayMonth - 1]) {
        nextFridayDay %= maxDaysInMonthsInCommonYear[nextFridayMonth - 1];
        nextFridayMonth++;
      }
    }
    if (nextFridayMonth > 12) {
      nextFridayMonth = 1;
      nextFridayYear++;
    }
    while (nextFridayDay != 13) {
      nextFridayDay += 7;
      if (isLeap(nextFridayYear)) {
        if (nextFridayDay > maxDaysInMonthsInLeapYear[nextFridayMonth - 1]) {
          nextFridayDay %= maxDaysInMonthsInLeapYear[nextFridayMonth - 1];
          nextFridayMonth++;
        }
      } else if (!isLeap(nextFridayYear)) {
        if (nextFridayDay > maxDaysInMonthsInCommonYear[nextFridayMonth - 1]) {
          nextFridayDay %= maxDaysInMonthsInCommonYear[nextFridayMonth - 1];
          nextFridayMonth++;
        }
      }
      if (nextFridayMonth > 12) {
        nextFridayMonth = 1;
        nextFridayYear++;
      }
    }
    return new GregorianCalendar(nextFridayYear, nextFridayMonth, nextFridayDay);
  }
}
