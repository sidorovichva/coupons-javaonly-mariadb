package Beans;

import Exceptions.Exceptions.CheckExceptionsAble;
import Auxiliaries.InputAble;
import Auxiliaries.UpdateAble;
import Exceptions.Exceptions.CouponExceptions;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *  abstract bean class that provides actual beans with all mutually used methods
 */
public abstract class Client implements InputAble, UpdateAble, CheckExceptionsAble {
    /**
     *  provides private enum of date types
     */
    private enum TimeType {
        DAY, MONTH, YEAR
    }

    private static final int minDay = 1;
    private static final int maxDay = 31;
    private static final int minMonth = 1;
    private static final int maxMonth = 12;
    private static final int minYear = 2021;
    private static final int maxYear = 2071;
    /**
     *  array contains 12 sub-arrays: month number / max number of days in the month
     */
    private static final int[][] monthDayArray = {{1, 31}, {2, 29}, {3, 31}, {4, 30}, {5, 31}, {6, 30}, {7, 31}, {8, 31}, {9, 30}, {10, 31}, {11, 30}, {12, 31}};

    /**
     *  throws exception if a number is 0 or less when a user gives an input
     */
    public int intInput(String text, int bottom) {
        int choice = intInput(text);
        if (check(choice > bottom, CouponExceptions.ZERO_OR_LESS)) return choice;
        else return intInput(text, bottom);
    }

    /**
     *  throws exception if a number is 0 or less when a user gives an input
     */
    public double doubleInput(String text, int bottom) {
        double choice = doubleInput(text);
        if (check(choice > bottom, CouponExceptions.ZERO_OR_LESS)) return choice;
        else return doubleInput(text, bottom);
    }

    /**
     *  throws exception if a number is 0 or less when a user updates object fields
     */
    public int intUpdate(String text, int oldNumber, int bottom) {
        int choice = intUpdate(text, oldNumber);
        if (check(choice >= bottom, CouponExceptions.ZERO_OR_LESS)) return choice;
        else return intUpdate(text, oldNumber, bottom);
    }

    /**
     *  throws exception if a number is 0 or less when a user updates object fields
     */
    public double doubleUpdate(String text, double oldNumber, int bottom) {
        double choice = doubleUpdate(text, oldNumber);
        if (check(choice >= bottom, CouponExceptions.ZERO_OR_LESS)) return choice;
        else return doubleUpdate(text, oldNumber, bottom);
    }

    /**
     * @param text a message to show to a user
     * @param timeType TimeType enum (day, month or year)
     * @return Date's Day, Month or Year (int)
     * throws exception if the entered value is in limit, defined by final variables of the class
     */
    public int intInput(String text, TimeType timeType) {
        int choice = intInput(text);
        switch (timeType) {
            case DAY: return (check(choice <= maxDay && choice >= minDay, CouponExceptions.WRONG_DAY) ? choice : intInput(text, timeType));
            case MONTH: return (check(choice <= maxMonth && choice >= minMonth, CouponExceptions.WRONG_MONTH) ? choice : intInput(text, timeType));
            case YEAR: return (check(choice <= maxYear && choice >= minYear, CouponExceptions.WRONG_YEAR) ? choice : intInput(text, timeType));
            default: return intInput(text, timeType);
        }
    }

    /**
     * @param text a message to show to a user
     * @param dateToCompare current date or coupon start date
     * @return Coupon's End or Start date (sql.Date)
     * checks if day and month were chosen correctly
     * checks if date is valid (not in the past and end date isn't less than start date)
     */
    public Date dateInput(String text, long dateToCompare) {
        int day = intInput("Please, enter " + text + " day", TimeType.DAY);
        int month = intInput("Please, enter " + text + " month", TimeType.MONTH);
        if (check(day <= monthDayArray[month - 1][1], CouponExceptions.WRONG_DAY_OF_MONTH)) {
            int year = intInput("Please, enter " + text + " year", TimeType.YEAR);
            //if (check(year % 4 != 0 && day <= 28, CouponExceptions.LEAP_YEAR)) {
            if (check(year % 4 != 0 || month != 2 || day != 29, CouponExceptions.LEAP_YEAR)) {
                Calendar date = new GregorianCalendar();
                date.set(Calendar.DAY_OF_MONTH, day);
                date.set(Calendar.MONTH, month - 1);
                date.set(Calendar.YEAR, year);
                Date returnDate = new java.sql.Date(date.getTimeInMillis());
                if (check(returnDate.getTime() >= System.currentTimeMillis(), CouponExceptions.TOO_LATE))
                    if (check(returnDate.getTime() > dateToCompare, CouponExceptions.END_BEFORE_FINISH))
                        return new java.sql.Date(date.getTimeInMillis());
            }
        }
        return dateInput(text, dateToCompare);
    }

    /**
     * @param text a message to show to a user
     * @param dateToCompare current date or coupon start date
     * @param oldDate the date before change
     * @return Coupon's End or Start date (sql.Date)
     * checks if day and month were chosen correctly
     * checks if date is valid (not in the past and end date isn't less than start date)
     */
    public Date dateUpdate(String text, long dateToCompare, Date oldDate) {
        int answer = intInput("0 - leave old " + text + " date, 1 enter new " + text + " date");
        if (check((answer == 0 || answer == 1), CouponExceptions.WRONG_CHOICE)) {
            if (answer == 0) return oldDate;
            else return dateInput(text, dateToCompare);
        }
        return dateUpdate(text, dateToCompare, oldDate);
    }
}
