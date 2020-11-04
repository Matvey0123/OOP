package ru.nsu.fit.kuznetsov.calendar;

import java.lang.IllegalArgumentException;

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
   *
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
   *
   * @param year the year to be checked
   * @return true if the year is leap and false otherwise
   */
  private static boolean isLeap(int year) {
    return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
  }

  /**
   * Converts the date into number of days.
   *
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
   * Makes correct the number of month and the count of days. If this method gets already correct
   * first parameter, it does not do anything.
   *
   * @param daysToCorrect the incorrect count of days
   * @param numOfMonth the num of current month from 0 to 11 inclusive
   * @param isLeapYear the information about leap year
   * @return the correct count of days
   */
  private int correctMonth(int daysToCorrect, int numOfMonth, boolean isLeapYear) {
    if (isLeapYear && daysToCorrect - maxDaysInMonthsInLeapYear[numOfMonth] > 0) {
      daysToCorrect -= maxDaysInMonthsInLeapYear[numOfMonth];
      month++;
    }
    if (!isLeapYear && daysToCorrect - maxDaysInMonthsInCommonYear[numOfMonth] > 0) {
      daysToCorrect -= maxDaysInMonthsInCommonYear[numOfMonth];
      month++;
    }
    return daysToCorrect;
  }

  /**
   * Searches next friday from current week day.
   *
   * @param currDay the number of current day in month
   * @param currWeekDay the current week day
   * @return the changed current day in month
   */
  private int searchNextFriday(int currDay, WeekDay currWeekDay) {
    switch (currWeekDay) {
      case MONDAY:
        return currDay + 4;
      case TUESDAY:
        return currDay + 3;
      case WEDNESDAY:
        return currDay + 2;
      case THURSDAY:
        return currDay + 1;
      case SATURDAY:
        return currDay + 6;
      case SUNDAY:
        return currDay + 5;
    }
    return currDay;
  }

  /**
   * Adds days to the Calendar.
   *
   * @param days the count of days to be added
   */
  @Override
  public void addDays(int days) {
    days += dayOfMonth;
    for (int i = month - 1; i < 12; i++) {
      int restDays = correctMonth(days, i, isLeap(year));
      if (days == restDays) {
        break;
      } else {
        days = restDays;
      }
      if (month > 12) {
        month /= 12;
        year++;
        i = -1;
      }
    }
    dayOfMonth = days;
  }

  /**
   * Adds years to the Calendar.
   *
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
   *
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
   *
   * @return the name of weekday as String
   */
  @Override
  public WeekDay weekDay() {
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
        return WeekDay.MONDAY;
      case (2):
        return WeekDay.TUESDAY;
      case (3):
        return WeekDay.WEDNESDAY;
      case (4):
        return WeekDay.THURSDAY;
      case (5):
        return WeekDay.FRIDAY;
      case (6):
        return WeekDay.SATURDAY;
    }
    return WeekDay.SUNDAY;
  }

  /**
   * Returns the number of days in the current Calendar.
   *
   * @return the number of days
   */
  @Override
  public int day() {
    return dayOfMonth;
  }

  /**
   * Returns the current month of the Calendar.
   *
   * @return the current month
   */
  @Override
  public int month() {
    return month;
  }

  /**
   * Returns the current year of the Calendar.
   *
   * @return the current year
   */
  @Override
  public int year() {
    return year;
  }

  /**
   * Subscribes the date from the Calendar. First of all evaluates the count of days between two
   * dates. After that evaluates the count of years from the beginning of the next year in Date. The
   * count of months and days starts evaluate from 01.01. of the next after last year to be added.
   *
   * @param days days of the Date to add
   * @param months months of the Date to add
   * @param years years of the Date to add
   */
  @Override
  public void subDate(int days, int months, int years) {
    int dateToSubInDays = dateToDays(days, months, years);
    int currDateInDays = dateToDays(dayOfMonth, month, year);
    int resultNumOfDays = currDateInDays - dateToSubInDays;
    int countLeap = year;
    dayOfMonth = month = year = 0;
    while (resultNumOfDays >= 366) {
      countLeap++;
      if (isLeap(countLeap)) {
        resultNumOfDays--;
      }
      resultNumOfDays -= 365;
      year++;
    }
    for (int i = 0; i < 12; i++) {
      int res = correctMonth(resultNumOfDays, i, isLeap(countLeap));
      if (res == resultNumOfDays) {
        break;
      } else {
        resultNumOfDays = res;
      }
    }
    dayOfMonth = resultNumOfDays;
  }

  /**
   * Evaluates how many days to new year.
   *
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
   *
   * @return the date of friday 13 as Calendar
   */
  @Override
  public Calendar nextFriday13() {
    WeekDay currWeekDay = this.weekDay();
    int nextFridayDay = dayOfMonth, nextFridayMonth = month, nextFridayYear = year;
    nextFridayDay = searchNextFriday(nextFridayDay, currWeekDay);
    nextFridayDay = correctMonth(nextFridayDay, month - 1, isLeap(nextFridayYear));
    if (month > 12) {
      month = 1;
      nextFridayYear++;
    }
    while (nextFridayDay != 13) {
      nextFridayDay += 7;
      nextFridayDay = correctMonth(nextFridayDay, month - 1, isLeap(nextFridayYear));
      if (month > 12) {
        month = 1;
        nextFridayYear++;
      }
    }
    int t = month;
    month = nextFridayMonth;
    nextFridayMonth = t;
    return new GregorianCalendar(nextFridayYear, nextFridayMonth, nextFridayDay);
  }
}

enum WeekDay {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY
}
