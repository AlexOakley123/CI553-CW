package dbAccess;
import java.sql.*;

public class DatabaseConnector  {

    Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/");
    Statement myStmt = myConn.createStatement();
    public DatabaseConnector() throws SQLException {
    }
}
