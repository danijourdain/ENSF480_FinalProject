import java.sql.*;
/**
 * @author Carter Marcelo
 * @version 1.0
 * @since 1.0
 * A singleton class that represents the database
 */
public class Database{
    public static String username = "root";
    public static String url = "jdbc:mysql://localhost:3306/TheatreDatabase";
    public static String password = "password";
    public static Connection dbConnect = null;
    private Database(){}
    public static Connection getConnection() throws SQLException{
        if(dbConnect == null || dbConnect.isClosed()){
            dbConnect = DriverManager.getConnection(url,username,password);
        }
        return dbConnect;
    }
}
