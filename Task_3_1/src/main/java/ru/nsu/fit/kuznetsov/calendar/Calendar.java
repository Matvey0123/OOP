package ru.nsu.fit.kuznetsov.calendar;

/**
 * Implementation of this interface allows us to use basic methods in our calendar.
 */
public interface Calendar {
    void addDays(int days);
    void addYears(int years);
    void addMonths(int months);
    WeekDay weekDay();
    int day();
    int month();
    int year();
    void subDate(int days, int months, int years);
    int daysToNewYear();
    Calendar nextFriday13();
}
