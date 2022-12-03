import java.sql.*;
/**
 * @author Carter Marcelo
 * @version 1.0
 * @since 1.0
 * A singleton class that represents the database
 */
public class Database{
    private static String username = "root";
    private static String url = "jdbc:mysql://localhost:3306/TheatreDatabase";
    private static String password = "password";
    private static Connection connection = null;
    private Database(){}
    public static Connection getConnection() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }
    public static boolean setConnection(String newUrl, String newUsername, String newPassword){
        username = newUsername;
        url = newUrl;
        password = newPassword;
        try{ 
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (Exception e) {
            System.out.println("An error occured when accessing the database");
            return false;
        }
        try{
            connection = DriverManager.getConnection(newUrl, newUsername, newPassword);
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static void closeConnection(){
        try{
            if(connection != null && connection.isClosed() == false){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
