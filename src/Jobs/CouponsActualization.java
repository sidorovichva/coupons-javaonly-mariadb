package Jobs;

import DBDAO.CouponsDBDAO;

import java.sql.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *a thread runner that once a day checks an existence of invalid (with end date in the past) coupons in the system
 * and deletes them with all related purchases
 */
public class CouponsActualization implements Runnable{
    private CouponsDBDAO couponsDBDAO;

    /**
     *run method works only when the boolean equals true
     */
    private static AtomicBoolean runs = new AtomicBoolean(true);

    /**
     *singleton constructor that starts the thread
     */
    private CouponsActualization() {
        this.couponsDBDAO = new CouponsDBDAO();
        Thread t = new Thread(this);
        t.start();
    }

    /**
     *an inner class that provides an instance of Coupon actualization singleton
     */
    private static class InnerClass {
        private static final CouponsActualization INSTANCE = new CouponsActualization();
    }

    /**
     *returns instance of the singleton
     */
    public static synchronized CouponsActualization getInstance() {
        return InnerClass.INSTANCE;
    }

    /**
     *stops the thread by making runs boolean false
     */
    public static void stop() {
        runs.set(false);
    }

    /**
     *thread that runs only when the runs boolean is true. Once a day checks if there are any invalid coupons and
     * deletes them with all their purchase history
     */
    @Override
    public void run() {
        while (runs.get()) {
            try {
                if (this.couponsDBDAO.doesCouponExist(new Date(System.currentTimeMillis())))
                    this.couponsDBDAO.deleteAllCouponsByEndDate(new Date(System.currentTimeMillis()));
                TimeUnit.HOURS.sleep(24);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
