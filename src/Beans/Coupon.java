package Beans;

import Exceptions.Exceptions.CouponSystemExceptions;
import RandomDataForTestRuns.Entries;

/**
 * Coupon object
 */
public class Coupon extends Client{
    private int id;
    private int companyID;
    private Category category;
    private String title;
    private String description;
    private java.sql.Date startDate;
    private java.sql.Date endDate;
    private int amount;
    private double price;
    private String image;

    /**
     * @param id input from the SQL DB
     * @param companyID input from the SQL DB
     * @param categoryNumber input from the SQL DB
     * @param title input from the SQL DB
     * @param description input from the SQL DB
     * @param startDate input from the SQL DB
     * @param endDate input from the SQL DB
     * @param amount input from the SQL DB
     * @param price input from the SQL DB
     * @param image input from the SQL DB
     * @return Coupon
     * @throws CouponSystemExceptions
     * Creates Coupon object out of data received from the SQL DB
     */
    public Coupon(int id,
                  int companyID,
                  int categoryNumber,
                  String title,
                  String description,
                  java.sql.Date startDate,
                  java.sql.Date endDate,
                  int amount,
                  double price,
                  String image) {
        this.id = id;
        this.companyID = companyID;
        this.category = Category.getCategory(categoryNumber);
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    /**
     * @param companyID CompanyID from the CompanyFacade object
     * @return Coupon
     * Creates new Coupon by a company
     */
    public Coupon(int companyID) {
        this.companyID = companyID;
        this.category = new Category();
        this.title = lineInput("Please, enter coupon title");
        this.description = lineInput("Please, enter coupon description");
        this.startDate = dateInput("START", System.currentTimeMillis());
        this.endDate = (dateInput("END", this.startDate.getTime()));
        this.amount = intInput("Please, enter coupon amount", 0);
        this.price = doubleInput("Please, enter coupon price", 0);
        this.image = Entries.randomString(20).toString();
    }

    /**
     * @param coupon Coupon object that is going to be updated/changed
     * @return Coupon
     * Creates new Coupon and replaces the old one
     */
    public Coupon(Coupon coupon) {
        this.id = coupon.getId();
        this.companyID = coupon.getCompanyID();
        this.category = new Category(coupon.getCategory());
        this.title = lineUpdate("Please, enter coupon title", coupon.getTitle());
        this.description = lineUpdate("Please, enter coupon description", coupon.getDescription());
        this.startDate = (coupon.getStartDate().getTime() < (System.currentTimeMillis()))
                ? coupon.getStartDate() : dateUpdate("START", System.currentTimeMillis(), coupon.getStartDate());
        this.endDate = dateUpdate("END", this.startDate.getTime(), coupon.getEndDate());
        this.amount = intUpdate("Please, enter coupon amount", coupon.getAmount(), 0);
        this.price = doubleUpdate("Please, enter coupon price", coupon.getPrice(), 0);
        this.image = coupon.getImage();
    }

    public int getId() {
        return id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    /**
     * Prints all coupon details to console
     */
    @Override
    public String toString() {
        StringBuffer separator = new StringBuffer(", ");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%10d", this.id) + separator.toString())
                .append(String.format("%4s", this.companyID) + separator.toString())
                .append(String.format("%16s", this.category.getName().toString()) + separator.toString())
                .append(String.format("%16s", this.title) + separator.toString())
                .append(String.format("%25s", this.description) + separator.toString())
                .append(String.format("%11s", this.startDate) + separator.toString())
                .append(String.format("%11s", this.endDate) + separator.toString())
                .append(String.format("%6s", this.amount) + separator.toString())
                .append(String.format("%8s", this.price) + separator.toString())
                .append(String.format("%32s", this.image) + separator.toString());
        return sb.toString();
    }
}
