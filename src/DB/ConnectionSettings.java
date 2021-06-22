package DB;

import java.io.Serializable;

/**
 * Creates connection settings object for further serialization and storing as a file
 * contains url, username and password to the DB
 */
public class ConnectionSettings implements Serialization, Serializable {
    private String url;
    private String username;
    private String password;

    public static final StringBuffer fileName = new StringBuffer("ConnectionSettings.config");

    private static final long serialVersionUID = 45646129348938L;

    public ConnectionSettings() {
        this.url = SQL.URL.getText();
        this.username = SQL.USERNAME.getText();
        this.password = SQL.PASSWORD.getText();
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
