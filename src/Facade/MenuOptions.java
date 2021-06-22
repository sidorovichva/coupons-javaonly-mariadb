package Facade;

import java.util.List;

/**
 * a list of all possible options in all the client menus
 */
public enum MenuOptions {
    //================================================================ COMMON
    EXIT(new StringBuffer("Exit")),
    BACK(new StringBuffer("Back")),
    LOG_OUT(new StringBuffer("Log out")),

    //================================================================ REPORTS

    GET_ALL_SOLD_COUPONS_BY_COMPANY(new StringBuffer("sold coupons")),
    TOTAL_SELLS_BY_COMPANY(new StringBuffer("total sells by company")),
    TOTAL_SELLS_BY_CATEGORY(new StringBuffer("total sells by category")),

    //================================================================ ADMIN
    COMPANIES(new StringBuffer("Companies")),
    CUSTOMERS(new StringBuffer("Customers")),
    CATEGORIES(new StringBuffer("Categories")),
    REPORTS(new StringBuffer("Reports")),

    IF_COMPANY_EXISTS(new StringBuffer("check existence")),
    ADD_COMPANY(new StringBuffer("add company")),
    UPDATE_COMPANY(new StringBuffer("update a company")),
    DELETE_COMPANY(new StringBuffer("delete a company")),
    GET_ALL_COMPANIES(new StringBuffer("show all companies")),
    GET_ONE_COMPANY(new StringBuffer("show a company")),

    ADD_CUSTOMER(new StringBuffer("add customer")),
    UPDATE_CUSTOMER(new StringBuffer("update a customer")),
    DELETE_CUSTOMER(new StringBuffer("delete a customer")),
    GET_ALL_CUSTOMERS(new StringBuffer("show all customers")),
    GET_ONE_CUSTOMER(new StringBuffer("show a customer")),

    ADD_CATEGORY(new StringBuffer("add category")),
    GET_ALL_CATEGORIES(new StringBuffer("show all categories")),

    //================================================================ COMPANY
    ADD_COUPON(new StringBuffer("add coupon")),
    UPDATE_COUPON(new StringBuffer("update coupon")),
    DELETE_COUPON(new StringBuffer("delete coupon")),
    GET_COMPANY_COUPONS(new StringBuffer("show coupons")),
    GET_COMPANY_DETAILS(new StringBuffer("show details")),

    GET_COMPANY_COUPONS_ALL(new StringBuffer("all coupons")),
    GET_COMPANY_COUPONS_CATEGORY(new StringBuffer("by category")),
    GET_COMPANY_COUPONS_MAX(new StringBuffer("max price")),
    GET_ALL_COUPONS_BY_CATEGORY(new StringBuffer("company coupons/category")),

    //================================================================ CUSTOMER
    BUY_COUPON(new StringBuffer("buy coupon")),
    GET_CUSTOMER_COUPONS(new StringBuffer("get customer coupons")),
    GET_CUSTOMER_DETAILS(new StringBuffer("show details")),

    GET_CUSTOMER_COUPONS_ALL(new StringBuffer("all coupons")),
    GET_CUSTOMER_COUPONS_CATEGORY(new StringBuffer("by category")),
    GET_CUSTOMER_COUPONS_MAX(new StringBuffer("max price"));

    private StringBuffer description;

    MenuOptions(StringBuffer description) {
        this.description = description;
    }

    public StringBuffer getDescription() {
        return description;
    }
}
