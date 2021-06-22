package Beans;

import DBDAO.CouponsDBDAO;
import Exceptions.Exceptions.CouponExceptions;

import java.util.List;

/**
 *Coupon's  object
 */
public class Category extends Client{
    private StringBuffer name;
    private int number;

    public Category(StringBuffer name, int number) {
        this.name = name;
        this.number = number;
    }

    public Category(StringBuffer name) {
        this.name = name;
    }

    /**
     * The constructor is in use a user wants to choose a category
     */
    public Category() {
        Category category = chooseCategory();
        this.name = category.getName();
        this.number = category.getNumber();
    }

    /**
     * @param oldCategory a category which was in an old version
     * The constructor is in use a user wants to change a category for an existing coupon
     */
    public Category(Category oldCategory) {
        Category category = chooseCategory(oldCategory);
        this.name = category.getName();
        this.number = category.getNumber();
    }

    public StringBuffer getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    /**
     * let the user to see a list of the existing categories and to choose one out of the list
     * throws an exception if the choice wasn't valid
     */
    public Category chooseCategory() {
        CouponsDBDAO c = new CouponsDBDAO();
        List<Category> list = c.getAllCategories();
        list.stream().forEach(p -> System.out.println(p));
        int answer = intInput("Please choose category", 0);
        if (check((answer <= list.size()), CouponExceptions.WRONG_CHOICE)) return list.stream().filter(p -> p.getNumber() == answer).findFirst().get();
        else return chooseCategory();
    }

    /**
     * let the user to see a list of the existing categories and to choose one out of the list
     * throws an exception if the choice wasn't valid
     * used in case of updating an existing coupon
     */
    public Category chooseCategory(Category category) {
        CouponsDBDAO c = new CouponsDBDAO();
        List<Category> list = c.getAllCategories();
        list.stream().forEach(p -> System.out.println(p));
        int answer = intUpdate("Please choose category", category.getNumber(), 0);
        if (check((answer <= list.size()), CouponExceptions.WRONG_CHOICE)) return list.stream().filter(p -> p.getNumber() == answer).findFirst().get();
        else return chooseCategory();
    }

    /**
     * @param number a category id number
     * returns Category object by its number
     */
    public static Category getCategory(int number) {
        CouponsDBDAO c = new CouponsDBDAO();
        List<Category> list = c.getAllCategories();
        return list.stream().filter(p -> p.getNumber() == number).findFirst().get();
    }

    @Override
    public String toString() {
        return this.number +". " + this.name;
    }
}
