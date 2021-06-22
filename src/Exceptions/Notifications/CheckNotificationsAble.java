package Exceptions.Notifications;

/**
 * checks conditions and throws CouponSystemNotification
 */
public interface CheckNotificationsAble {

    /**
     * @param condition condition to check
     * @param success exception to throw if the condition is true
     * @param fail exception to throw if the condition is false
     * checks the condition and throws corresponding exception if condition is true or false
     */
    default void check(boolean condition, Notifications success, Notifications fail) {
        try {
            if (!condition) {
                throw new CouponSystemNotifications(fail);
            } else throw new CouponSystemNotifications(success);
        } catch (CouponSystemNotifications CouponNotifications) {
            CouponNotifications.getText();
        }
    }
}
