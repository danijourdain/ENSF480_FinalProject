import java.sql.*;
/**
 * @author Carter Marcelo
 * @version 1.0
 * @since 1.0
 * A singleton class that represents the database
 */
public class Database{
    private static String username = "root";
    private static String url = "jdbc:mysql://localhost:3306/Theatre";
    private static String password = "password";
    private static Connection connection = null;
    private Database(){}
    /**
     * Gets the connection to the database.</br><br><b>NO-THROW GUARANTEE:</b> this method will never throw an exception.</br>
     * @return {@code Connection}, or {@code null} if a connection couldn't be established
     */
    public static Connection getConnection(){
        if(connection != null){
            return connection;
        }
        try{
            if(connection == null || connection.isClosed()){
                connection = DriverManager.getConnection(url,username,password);
            }
        }catch(SQLException e){
            System.out.println("An error occured when accessing the database\n");
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * Changes the connection to the database.</br><br><b>NO-THROW GUARANTEE:</b> this method will never throw an exception.</br>
     * @param newUrl is the new url
     * @param newUsername is the new username
     * @param newPassword is the new password
     * @return {@code true} on success, or {@code false} if a connection couldn't be established
     */
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
    /**Closes the connection to the database.</br><br><b>NO-THROW GUARANTEE:</b> this method will never throw an exception.</br>
     * @since 1.0
     * 
     */
    public static void closeConnection(){
        try{
            if(connection != null && connection.isClosed() == false){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    /**Performs a rollback on the connection to the database.
     * </br><br><b>NO-THROW GUARANTEE:</b> this method will never throw an exception.</br>
     * @since 1.5
     */
    public static void rollback(){
        try{
            if(connection != null && connection.isClosed() == false){
                connection.rollback();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
