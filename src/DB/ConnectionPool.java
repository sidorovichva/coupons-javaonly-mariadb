package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * Singleton, creates pool of connection
 */
public class ConnectionPool {
    /**
     * Capacity of the pull connection
     */
    private static final int NUM_OF_CONS = 15;
    private static ConnectionPool instance = null;
    /**
     * Connection Pool itself
     */
    private static Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        openAllConnections();
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * @return takes one connection from the pool
     */
    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            if (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    /**
     * returns one connection to the pool
     */
    public void restoreConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    /**
     * creates the pool of accessible connections
     */
    private void openAllConnections() {
        for (int i = 0; i < NUM_OF_CONS; i++) {
            try {
                Connection connection = DriverManager.getConnection(DatabaseManager.url,
                        DatabaseManager.username,
                        DatabaseManager.password);
                connections.push(connection);
            } catch (SQLException e) {
                System.err.println("No connection to the DB,");
                System.out.println("Exiting...");
                System.exit(0);
            }

        }
    }

    public void closeAllConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }
}
