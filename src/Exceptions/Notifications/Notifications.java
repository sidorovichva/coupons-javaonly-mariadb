package Exceptions.Notifications;

/**
 *list of all possible notification
 */
public enum Notifications {
    SUCCESS(new StringBuffer("executed successfully")),
    FAIL(new StringBuffer("execution failed...")),

    ADD_COMPANY_SUCCESS(new StringBuffer("adding a company... success")),
    ADD_COMPANY_FAIL(new StringBuffer("adding a company... FAIL")),
    UPDATE_COMPANY_SUCCESS(new StringBuffer("updating a company... success")),
    UPDATE_COMPANY_FAIL(new StringBuffer("updating a company... FAIL")),
    DELETE_COMPANY_SUCCESS(new StringBuffer("deleting a company... success")),
    DELETE_COMPANY_FAIL(new StringBuffer("deleting a company... FAIL")),

    ADD_COUPON_SUCCESS(new StringBuffer("adding a coupon... success")),
    ADD_COUPON_FAIL(new StringBuffer("adding a coupon... FAIL")),
    UPDATE_COUPON_SUCCESS(new StringBuffer("updating a coupon... success")),
    UPDATE_COUPON_FAIL(new StringBuffer("updating a coupon... FAIL")),
    DELETE_COUPON_SUCCESS(new StringBuffer("deleting a coupon... success")),
    DELETE_COUPON_FAIL(new StringBuffer("deleting a coupon... FAIL")),

    ADD_CUSTOMER_SUCCESS(new StringBuffer("adding a customer... success")),
    ADD_CUSTOMER_FAIL(new StringBuffer("adding a customer... FAIL")),
    UPDATE_CUSTOMER_SUCCESS(new StringBuffer("updating a customer... success")),
    UPDATE_CUSTOMER_FAIL(new StringBuffer("updating a customer... FAIL")),
    DELETE_CUSTOMER_SUCCESS(new StringBuffer("deleting a customer... success")),
    DELETE_CUSTOMER_FAIL(new StringBuffer("deleting a customer... FAIL")),

    ADD_CATEGORY_SUCCESS(new StringBuffer("adding a category... success")),
    ADD_CATEGORY_FAIL(new StringBuffer("adding a category... FAIL")),

    PURCHASE_SUCCESS(new StringBuffer("adding a purchase... success")),
    PURCHASE_FAIL(new StringBuffer("adding a purchase... FAIL"));

    private StringBuffer text;

    Notifications(StringBuffer message) {
        this.text = message;
    }

    public StringBuffer getText() {
        return text;
    }
}
