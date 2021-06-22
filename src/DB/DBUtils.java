package DB;

import Login.LoginManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

/**
 * Makes requests to the SQL DB and returns requested data
 */
public class DBUtils {
    /**
     * @param map list of parameters for PreparedStatement
     * @param query SQL command to execute
     * @return ResultSet contains information from the DB
     * Executes command that return data from the DB
     */
    public static ResultSet runQuery(Map<Integer, Object> map, StringBuffer query) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            map.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) statement.setInt(key, (int) value);
                    if (value instanceof Double) statement.setDouble(key, (double) value);
                    if (value instanceof String) statement.setString(key, (String) value);
                    if (value instanceof Date) statement.setDate(key, (java.sql.Date) value);
                    if (value instanceof Boolean) statement.setBoolean(key, (boolean) value);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("No connection to the DB, please log in again now or late");
            LoginManager.getInstance();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DB.ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    /**
     * @param query SQL command to execute
     * @return ResultSet contains information from the DB
     * Executes command that return data from the DB
     */
    public static ResultSet runQuery(StringBuffer query) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("No connection to the DB, please log in again now or late");
            LoginManager.getInstance();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DB.ConnectionPool.getInstance().restoreConnection(connection);
        }
    }

    /**
     * @param map list of parameters for PreparedStatement
     * @param query SQL command to execute
     * @return true if the command was executed
     * Executes command that don't return any data from the DB.
     */
    public static boolean runUpdate(Map<Integer, Object> map, StringBuffer query) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            map.forEach((key, value) -> {
                try {
                    if (value instanceof Integer) statement.setInt(key, (int) value);
                    if (value instanceof Double) statement.setDouble(key, (double) value);
                    if (value instanceof String) statement.setString(key, (String) value);
                    if (value instanceof Date) statement.setDate(key, (java.sql.Date) value);
                    if (value instanceof Boolean) statement.setBoolean(key, (boolean) value);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            if (statement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            System.err.println("No connection to the DB, please log in again now or late");
            LoginManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.ConnectionPool.getInstance().restoreConnection(connection);
        }
        return false;
    }

    /**
     * @param query SQL command to execute
     * @return true if the command was executed
     * Executes command that don't return any data from the DB, uses for DB initialization.
     */
    public static boolean runUpdate(StringBuffer query) {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString());
            if (statement.executeUpdate() > 0) return true;
        } catch (SQLException e) {
            System.err.println("No connection to the DB");
            System.out.println("Exiting...");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DB.ConnectionPool.getInstance().restoreConnection(connection);
        }
        return false;
    }
}
