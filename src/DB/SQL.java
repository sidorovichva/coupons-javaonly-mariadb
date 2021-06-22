package DB;

/**
 * list of all DB related names (schema, tables, column names)root
 */
public enum SQL {
    URL(new StringBuffer("jdbc:mysql://localhost:3306?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=UTC")),
    USERNAME(new StringBuffer("root")),
    PASSWORD(new StringBuffer("")),
    DB_NAME(new StringBuffer("COUPON_MS")),
    COMPANIES(new StringBuffer(DB_NAME.getText() +".companies")),
    CUSTOMERS(new StringBuffer(DB_NAME.getText() +".customers")),
    CATEGORIES(new StringBuffer(DB_NAME.getText() +".categories")),
    COUPONS(new StringBuffer(DB_NAME.getText() +".coupons")),
    CUSTOMERS_VS_COUPONS(new StringBuffer(DB_NAME.getText() +".customers_vs_coupons")),

    company_ID(new StringBuffer(COMPANIES.getText() + ".ID")),
    company_NAME(new StringBuffer(COMPANIES.getText() + ".NAME")),
    company_EMAIL(new StringBuffer(COMPANIES.getText() + ".EMAIL")),
    company_PASSWORD(new StringBuffer(COMPANIES.getText() + ".PASSWORD")),

    customer_ID(new StringBuffer(CUSTOMERS.getText() + ".ID")),
    customer_FIRST_NAME(new StringBuffer(CUSTOMERS.getText() + ".FIRST_NAME")),
    customer_LAST_NAME(new StringBuffer(CUSTOMERS.getText() + ".LAST_NAME")),
    customer_EMAIL(new StringBuffer(CUSTOMERS.getText() + ".EMAIL")),
    customer_PASSWORD(new StringBuffer(CUSTOMERS.getText() + ".PASSWORD")),

    category_ID(new StringBuffer(CATEGORIES.getText() + ".ID")),
    category_NAME(new StringBuffer(CATEGORIES.getText() + ".NAME")),

    coupon_ID(new StringBuffer(COUPONS.getText() + ".ID")),
    coupon_COMPANY_ID(new StringBuffer(COUPONS.getText() + ".COMPANY_ID")),
    coupon_CATEGORY_ID(new StringBuffer(COUPONS.getText() + ".CATEGORY_ID")),
    coupon_TITLE(new StringBuffer(COUPONS.getText() + ".TITLE")),
    coupon_DESCRIPTION(new StringBuffer(COUPONS.getText() + ".DESCRIPTION")),
    coupon_START_DATE(new StringBuffer(COUPONS.getText() + ".START_DATE")),
    coupon_END_DATE(new StringBuffer(COUPONS.getText() + ".END_DATE")),
    coupon_AMOUNT(new StringBuffer(COUPONS.getText() + ".AMOUNT")),
    coupon_PRICE(new StringBuffer(COUPONS.getText() + ".PRICE")),
    coupon_IMAGE(new StringBuffer(COUPONS.getText() + ".IMAGE")),

    customers_vs_coupons_CUSTOMER_ID(new StringBuffer(CUSTOMERS_VS_COUPONS.getText() + ".CUSTOMER_ID")),
    customers_vs_coupons_COUPON_ID(new StringBuffer(CUSTOMERS_VS_COUPONS.getText() + ".COUPON_ID"));

    private StringBuffer tableName;

    SQL(StringBuffer tableName) {
        this.tableName = tableName;
    }

    public String getText() {
        return tableName.toString();
    }
}
