package Exceptions.Notifications;

/**
 *constructor and stringBuffer reader for Notifications
 */
public class CouponSystemNotifications extends Exception{
    /**
     *the text of the Notification message
     */
    private StringBuffer text;

    /**
     *the beginning of every notification
     */
    private static StringBuffer messageBeginning = new StringBuffer("Notification: ");

    public CouponSystemNotifications(Notifications notification) {
        this.text = notification.getText();
    }

    public void getText() {
        System.out.println(messageBeginning.toString() + this.text.toString());
    }
}
