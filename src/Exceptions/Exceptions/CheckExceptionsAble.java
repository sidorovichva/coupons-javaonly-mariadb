package Exceptions.Exceptions;

/**
 * checks conditions and throws CouponSystemException
 */
public interface CheckExceptionsAble {
    /**
     * @param id in some cases user has an option to cancel action by pressing "0",
     *           so the method checks if 0 was chosen and return false in this case
     * @param condition condition to check
     * @param exception exception to throw if the condition is false
     * @return tru if condition is true and id is not zero
     * checks if user's choice wasn't 0. If so, returns false w/o checking the condition
     * checks provided condition and throws corresponding exception if condition is false
     */
    default boolean check(int id, boolean condition, CouponExceptions exception) {
        if (id == 0) return false;
        try {
            if (!condition) {
                throw new CouponSystemExceptions(exception);
            }
        } catch (CouponSystemExceptions couponSystemExceptions) {
            couponSystemExceptions.getText();
            return false;
        }
        return true;
    }

    /**
     * @param condition condition to check
     * @param exception exception to throw if the condition is false
     * @return tru if condition is true
     * checks provided condition and throws corresponding exception if condition is false
     */
    default boolean check(boolean condition, CouponExceptions exception) {
        try {
            if (!condition) {
                throw new CouponSystemExceptions(exception);
            }
        } catch (CouponSystemExceptions couponSystemExceptions) {
            couponSystemExceptions.getText();
            return false;
        }
        return true;
    }
}
