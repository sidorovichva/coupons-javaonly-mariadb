package DBDAO;

import Beans.Category;
import DB.ConnectionPool;
import Exceptions.Exceptions.CouponExceptions;
import Exceptions.Exceptions.CouponSystemExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class ReportsDBDAO {
    private Connection connection;

    /**
     * sql command: return all existing coupons by specific category
     */
    private static final String GET_ALL_COUPONS_BY_CATEGORY =
            "SELECT coupons.`ID`, coupons.`TITLE`, coupons.`DESCRIPTION`, coupons.`START_DATE`, coupons.`END_DATE`, coupons.`AMOUNT`, coupons.`PRICE` " +
            "FROM coupon_ms.coupons " +
            "INNER JOIN coupon_ms.categories " +
            "ON categories.ID = coupons.CATEGORY_ID " +
            "WHERE categories.ID = ?";

    /**
     * sql command: all the amount of sold coupons per company
     */
    private static final String GET_NUMBER_OF_SOLD_COUPONS_BY_COMPANY =
            "SELECT coupons.`ID`, coupons.`TITLE`, coupons.`DESCRIPTION`, coupons.START_DATE, coupons.END_DATE, coupons.PRICE, COUNT(customers_vs_coupons.CUSTOMER_ID), coupons.AMOUNT " +
            "FROM coupon_ms.coupons " +
            "INNER JOIN coupon_ms.customers_vs_coupons " +
            "ON coupons.ID = customers_vs_coupons.COUPON_ID " +
            "WHERE coupons.COMPANY_ID = ? " +
            "GROUP BY customers_vs_coupons.COUPON_ID";

    /**
     * sql command: return all coupons with sells information by specified company
     */
    private static final String TOTAL_SELLS_BY_COMPANY =
            "SELECT coupons.COMPANY_ID, companies.NAME, COUNT(customers_vs_coupons.CUSTOMER_ID), coupons.PRICE * COUNT(customers_vs_coupons.CUSTOMER_ID) " +
            "FROM coupon_ms.customers_vs_coupons, coupon_ms.coupons " +
            "INNER JOIN coupon_ms.companies " +
            "ON coupons.COMPANY_ID = companies.ID " +
            "WHERE coupons.ID = customers_vs_coupons.COUPON_ID " +
            "GROUP BY coupons.COMPANY_ID";

    /**
     * sql command: return all coupons with sells information by specified category
     */
    private static final String TOTAL_SELLS_BY_CATEGORY =
            "SELECT coupons.CATEGORY_ID, categories.NAME, COUNT(customers_vs_coupons.CUSTOMER_ID), coupons.PRICE * COUNT(customers_vs_coupons.CUSTOMER_ID) " +
            "FROM coupon_ms.customers_vs_coupons, coupon_ms.coupons " +
            "INNER JOIN coupon_ms.categories " +
            "ON coupons.CATEGORY_ID = categories.ID " +
            "WHERE coupons.ID = customers_vs_coupons.COUPON_ID " +
            "GROUP BY coupons.CATEGORY_ID";

    public void getAllCouponsByCategory(Category category) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_COUPONS_BY_CATEGORY);
            statement.setInt(1, category.getNumber());
            ResultSet set = statement.executeQuery();
            if (!set.isBeforeFirst()) throw new CouponSystemExceptions(CouponExceptions.COUPONS_WERENT_FOUND);
            else {
                System.out.println("\t" + category.getName() + ":");
                StringBuffer formatter = new StringBuffer("%5s %12s %20s %11s %11s %7s %7s");
                System.out.println(String.format(formatter.toString(), "ID", "TITLE", "DESCRIPTION", "START_DATE", "END_DATE", "AMOUNT", "PRICE"));
                while (set.next()) {
                    System.out.println(String.format(formatter.toString(),
                            set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getDate(4),
                            set.getDate(5),
                            set.getInt(6),
                            set.getDouble(7)));
                }
            }
        } catch (CouponSystemExceptions couponSystemExceptions) {
            couponSystemExceptions.getText();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public void getNumberOfSoldCouponsByCouponByCompany(int companyID) {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_NUMBER_OF_SOLD_COUPONS_BY_COMPANY);
            statement.setInt(1, companyID);
            ResultSet set = statement.executeQuery();
            if (!set.isBeforeFirst()) throw new CouponSystemExceptions(CouponExceptions.COUPONS_WERENT_FOUND);
            else {
                StringBuffer formatter = new StringBuffer("%5s %12s %20s %11s %11s %7s %7s %5s");
                System.out.println(String.format(formatter.toString(), "ID", "TITLE", "DESCRIPTION", "START_DATE", "END_DATE", "PRICE", "SOLD", "LEFT"));
                while (set.next()) {
                    System.out.println(String.format(formatter.toString(),
                            set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getDate(4),
                            set.getDate(5),
                            set.getDouble(6),
                            set.getInt(7),
                            set.getInt(8)));
                }
            }
        } catch (CouponSystemExceptions couponSystemExceptions) {
            couponSystemExceptions.getText();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public void totalSellsByCompany() {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(TOTAL_SELLS_BY_COMPANY);
            ResultSet set = statement.executeQuery();
            if (!set.isBeforeFirst()) throw new CouponSystemExceptions(CouponExceptions.COMPANIES_WERENT_FOUND);
            else {
                System.out.println("\tTotal sells by companies:");
                System.out.println(String.format("%5s %15s %15s %12s", "ID", "COMPANY NAME", "COUPONS SOLD", "TOTAL PRICE"));
                while (set.next()) {
                    System.out.println(String.format("%5d %15s %15d %10.2f",
                            set.getInt(1),
                            set.getString(2),
                            set.getInt(3),
                            set.getDouble(4)));
                }
            }
        } catch (CouponSystemExceptions couponSystemExceptions) {
            couponSystemExceptions.getText();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    public void totalSellsByCategory() {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(TOTAL_SELLS_BY_CATEGORY);
            ResultSet set = statement.executeQuery();
            if (!set.isBeforeFirst()) throw new CouponSystemExceptions(CouponExceptions.NOTHING_FOUND);
            else {
                System.out.println("\tTotal sells by categories:");
                System.out.println(String.format("%5s %15s %15s %12s", "ID", "CATEGORY NAME", "COUPONS SOLD", "TOTAL PRICE"));
                while (set.next()) {
                    System.out.println(String.format("%5d %15s %15d %10.2f",
                            set.getInt(1),
                            set.getString(2),
                            set.getInt(3),
                            set.getDouble(4)));
                }
            }
        } catch (CouponSystemExceptions couponSystemExceptions) {
            couponSystemExceptions.getText();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            ConnectionPool.getInstance().restoreConnection(connection);
        }
    }
}
