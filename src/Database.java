import java.sql.*;
/**
 * @author Carter Marcelo
 * @version 1.0
 * @since 1.0
 * A singleton class that represents the database
 */
public final class Database{
    private static Database instance;
    public String username = "root";
    public String url = "jdbc:mysql://localhost:3306/TheatreDatabase";
    public String password = "password";
    public Connection dbConnect = null;
    private Database(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }
    private Database(){}
    public Connection getConnection() throws SQLException{
        if(dbConnect == null || dbConnect.isClosed()){
            dbConnect = DriverManager.getConnection(url,username,password);
        }
        return dbConnect;
    }
    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    };
    public static Database changeInstance(String username, String url, String password){
        if(instance == null){
            instance = new Database(url, username,password); 
        }
        else if(username.equals(username) && url.equals(url) && password.equals(password)){
            return instance;
        }
        return instance;
    }
}
