import Beans.*;
import DAO.CompaniesDAO;
import DAO.CouponDAO;
import DAO.CustomersDAO;
import DB.DatabaseManager;
import DBDAO.CompaniesDBDAO;
import DBDAO.CouponsDBDAO;
import DBDAO.CustomersDBDAO;
import Facade.AdminFacade;
import Facade.CompanyFacade;
import Facade.CustomerFacade;
import Jobs.CouponsActualization;
import Login.ClientType;
import Login.LoginManager;
import RandomDataForTestRuns.Entries;

import java.sql.Date;
import java.util.GregorianCalendar;

class Test {
    public static void testAll() {
        LoginManager loginManager = new LoginManager(true);

        AdminFacade admin = (AdminFacade) loginManager.login(true, ClientType.ADMINISTRATOR, "admin@admin.com", "admin");
        admin.addCompany(new Company("AAA", "aaa@company.com", "aaa111"));
        admin.addCompany(new Company("BBB", "bbb@company.com", "bbb111"));
        admin.addCompany(new Company("CCC", "ccc@company.com", "ccc111"));
        admin.updateCompany(new Company(1, "AAA", "a@company.com", "aaa111"));
        admin.deleteCompany(1);
        admin.getAllCompanies().stream().forEach(p -> System.out.println(p));
        System.out.println(admin.getOneCompany(2));
        admin.addCustomer(new Customer(0, "Adam", "Smith", "adam@adam.com", "qwe123"));
        admin.addCustomer(new Customer(0, "Bob", "Mitzi", "meu@bbb.com", "qwe321"));
        admin.addCustomer(new Customer(0, "Chen", "Chen", "chen@chen.com", "qaz123"));
        admin.updateCustomer(new Customer(1, "Abnormal", "Smith", "a@a.com", "qaz123"));
        admin.deleteCustomer(1);
        admin.getAllCustomers().stream().forEach(p -> System.out.println(p));
        System.out.println(admin.getOneCustomer(2));

        CompanyFacade company = (CompanyFacade) loginManager.login(true, ClientType.COMPANY, "bbb@company.com", "bbb111");
        company.addCoupon(new Coupon(0, 2, 2, "Hummus", "Tasty Hummus",
                new Date(new GregorianCalendar(2021, 3,10).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, 4,10).getTimeInMillis()),
                100, 29.99, "filename1"));
        company.addCoupon(new Coupon(0, 2, 3, "Hashmal", "3 months for free",
                new Date(new GregorianCalendar(2021, 2,10).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, 5,10).getTimeInMillis()),
                500, 99, "filename2"));
        company.addCoupon(new Coupon(0, 2, 4, "Azurro", "tasty arab food",
                new Date(new GregorianCalendar(2021, 5,10).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, 8,10).getTimeInMillis()),
                150, 149.50, "filename3"));
        company.updateCoupon(new Coupon(1, 2, 2, "Hummus 2.0", "Not really tasty Hummus",
                new Date(new GregorianCalendar(2021, 3,10).getTimeInMillis()),
                new Date(new GregorianCalendar(2021, 4,10).getTimeInMillis()),
                95, 19.99, "filename1"));
        company.deleteCoupon(1);
        company.getCompanyCoupons().stream().forEach(p -> System.out.println(p));
        company.getCompanyCoupons(Category.getCategory(4)).stream().forEach(p -> System.out.println(p));
        company.getCompanyCoupons(100).stream().forEach(p -> System.out.println(p));
        System.out.println(company.getCompanyDetails());

        CustomerFacade customer = (CustomerFacade) loginManager.login(true, ClientType.CUSTOMER, "meu@bbb.com", "qwe321");
        customer.purchaseCoupon(customer.getCouponDBDAO().getOneCoupon(2));
        customer.purchaseCoupon(customer.getCouponDBDAO().getOneCoupon(3));
        System.out.println("All coupons");
        customer.getCustomerCoupons().stream().forEach(p -> System.out.println(p));
        System.out.println("Coupons by category");
        customer.getCompanyCoupons(Category.getCategory(4)).stream().forEach(p -> System.out.println(p));
        System.out.println("Coupons by price");
        customer.getCompanyCoupons(100).stream().forEach(p -> System.out.println(p));
        System.out.println("Customer detail");
        System.out.println(customer.getCompanyDetails());
    }
}

public class Program {
    public static void main(String[] args) {
        DatabaseManager.dropAll();
        DatabaseManager.createAll();

        //Random Entries
        /*for (int i = 0; i < 30; i++) {
            CompaniesDAO d = new CompaniesDBDAO();
            Company company = Entries.randomCompany();
            if (!d.isCompanyExists(company.getEmail(), company.getName())) d.addCompany(company);
        }

        for (int i = 0; i < 100; i++) {
            CouponDAO d = new CouponsDBDAO();
            d.addCoupon(Entries.randomCoupon());
        }

        for (int i = 0; i < 25; i++) {
            CustomersDAO d = new CustomersDBDAO();
            d.addCustomer(Entries.randomCustomer());
        }

        for (int i = 0; i < 300; i++) {
            Entries.randomPurchase();
        }*/

        Test.testAll();

        CouponsActualization.getInstance();
        LoginManager.getInstance();
    }
}
