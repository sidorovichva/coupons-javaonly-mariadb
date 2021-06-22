package RandomDataForTestRuns;

import Beans.*;
import DAO.CompaniesDAO;
import DAO.CustomersDAO;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;

import java.sql.Date;
import java.util.List;
import java.util.Random;

/**
 *the class provides randomly created objects (Coupon, Company, Customer)
 * and log in random existing clients into the system
 */
public class Entries {
    public static StringBuilder randomString(int length) {
        StringBuilder sb = new StringBuilder("");
        for (int j = 0; j < length; j++) {
            switch (MyArrayInt.randomNumber(1, 3)) {
                case 1: {
                    sb.append((char)MyArrayInt.randomNumber(48, 57));
                    break;
                } case 2: {
                    sb.append((char)MyArrayInt.randomNumber(65, 90));
                    break;
                } case 3: {
                    sb.append((char)MyArrayInt.randomNumber(97, 122));
                    break;
                } default: break;
            }
        }
        return sb;
    }

    public static Company randomCompany() {
        String s = MyArrayString.randomEntry(MyArrayString.myCompanyNames);
        Company c = new Company(s, s.toLowerCase() + "@" + s.toLowerCase() + ".com", randomString(10).toString());
        return c;
    }

    public static Coupon randomCoupon() {
        CouponsDBDAO couponsDBDAO = new CouponsDBDAO();
        List<Category> categoryList = couponsDBDAO.getAllCategories();
        Random rand = new Random();

        Date startDate = new Date(System.currentTimeMillis() + randomNumber(-2600000000l, 2600000000l));

        CompaniesDAO companiesDBDAO = new CompaniesDBDAO();
        List<Company> list = companiesDBDAO.getAllCompanies();
        Company company = list.get(MyArrayInt.randomNumber(0, list.size() - 1));

        Coupon randomCoupon = new Coupon(1,
                company.getId(),
                categoryList.get(rand.nextInt(categoryList.size())).getNumber(),
                MyArrayString.randomEntry(MyArrayString.myCouponTitles),
                (MyArrayString.randomEntry(MyArrayString.myColors) + " " + MyArrayString.randomEntry(MyArrayString.myAnimals)),
                startDate,
                new Date(startDate.getTime() + randomNumber(0, 2600000000l)), // to get some expired coupons
                /*new Date((startDate.getTime() > System.currentTimeMillis())
                        ? startDate.getTime() + randomNumber(0, 2600000000l)
                        : System.currentTimeMillis() + randomNumber(0, 2600000000l)),*/
                MyArrayInt.randomNumber(10, 500) * 100,
                Math.round(MyArrayDouble.randomNumber(20.0, 500.0) * 100.0) / 100.0,
                randomString(30).toString());
        return randomCoupon;
    }

    public static Customer randomCustomer() {
        String lastName = MyArrayString.randomEntry(MyArrayString.myLastNames);
        Customer randomCustomer = new Customer(MyArrayInt.randomNumber(100000000, 999999999),
                MyArrayString.randomEntry(MyArrayString.myNames),
                lastName,
                lastName.toLowerCase() + "@" + lastName.toLowerCase() + ".com",
                randomString(10).toString());
        return randomCustomer;
    }

    public static void randomPurchase() {
        CouponsDBDAO c = new CouponsDBDAO();
        List<Coupon> list1 = c.getAllCoupons();
        Coupon coupon = list1.get(MyArrayInt.randomNumber(0, list1.size() - 1));

        CustomersDBDAO d = new CustomersDBDAO();
        List<Customer> list2 = d.getAllCustomers();
        Customer customer = list2.get(MyArrayInt.randomNumber(0, list2.size() - 1));

        if (coupon.getAmount() >= 1
                && coupon.getEndDate().getTime() > System.currentTimeMillis()
                && coupon.getStartDate().getTime() > System.currentTimeMillis()
                && !d.doesPurchaseExist(customer.getId(), coupon.getId())) {
            c.addCouponPurchase(customer.getId(), coupon.getId());
            c.updateCouponAmount(coupon);
        }
    }

    public static Company getRandomCompanyFromDB() {
        CompaniesDAO c = new CompaniesDBDAO();
        List<Company> list = c.getAllCompanies();
        int r = MyArrayInt.randomNumber(0, list.size() - 1);
        return list.get(r);
    }

    public static Customer getRandomCustomerFromDB() {
        CustomersDAO c = new CustomersDBDAO();
        List<Customer> list = c.getAllCustomers();
        int r = MyArrayInt.randomNumber(0, list.size() - 1);
        return list.get(r);
    }

    public static long randomNumber(long min, long max) {
        return (min + (long)(Math.random() * (max - min + 1)));
    }
}
