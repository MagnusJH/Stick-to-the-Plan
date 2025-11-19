import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    // field
    Connection con;

    // Constructor
    public Connector () {
        con = connect();
    }

    private Connection connect() {
        // get the names of the database
        String url = "jdbc:mysql://localhost:3306/sticktotheplan";
        String username = "root";
        String password = "password";

        // try to open connection
        Connection con = null;
        try {
            // connection
            con = DriverManager.getConnection(url, username, password);
            System.out.println("connected");
            return con;

        } catch (Exception e) {
            // error connecting
            System.out.println("exception: " + e.getMessage());
            System.exit(1);
        }

        return con;
    }

    public Connection getConnection() {
        return con;
    }
}
