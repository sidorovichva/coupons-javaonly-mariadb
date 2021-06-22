package Beans;

import java.util.ArrayList;

/**
 * Company object
 */
public class Company extends Client {
    private int id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Coupon> coupons;

    public Company(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = new ArrayList<>();
    }

    /**
     * The constructor is in use when a new company is created from admin facade
     */
    public Company() {
        this.name = lineInput("Please, enter company name");
        this.email = stringInput("Please, enter e-mail");
        this.password = stringInput("Please, enter password");
    }

    /**
     * @param company provide information from the old version company
     * Provides a possibility to change all fields except id and name of the company
     * The constructor is in use when an old company is updated from admin facade
     */
    public Company(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.email = stringUpdate("Please, enter e-mail", company.getEmail());
        this.password = stringUpdate("Please, enter password", company.getPassword());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Coupon> getCoupons() {
        return coupons;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCoupons(ArrayList<Coupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        StringBuffer separator = new StringBuffer(", ");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%10d", this.id) + separator.toString())
                .append(String.format("%15s", this.name) + separator.toString())
                .append(String.format("%30s", this.email) + separator.toString())
                .append(String.format("%11s", this.password) + separator.toString());
        for (Coupon coupon : coupons) {
            sb.append("\n" + coupon);
        }
        return sb.toString();
    }
}
