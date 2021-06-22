package Exceptions.Exceptions;

/**
 * customized exceptions generator
 */
public class CouponSystemExceptions extends Exception{
    private StringBuffer text;

    /**
     * common exception prefix text
     */
    private static StringBuffer messageBeginning = new StringBuffer("System Error: ");

    /**
     * @param exception thrown customized exception
     * construct an exception object
     */
    public CouponSystemExceptions(CouponExceptions exception) {
        this.text = exception.getText();
    }

    /**
     * default constructor if specified exception wasn't provided when the exception was thrown
     */
    public CouponSystemExceptions() {
        this.text = new StringBuffer("");
        System.out.println("Error: something went wrong");
    }

    /**
     * print exception text with prefix on user's console
     */
    public void getText() {
        System.out.println(messageBeginning.toString() + this.text.toString());
    }
}
