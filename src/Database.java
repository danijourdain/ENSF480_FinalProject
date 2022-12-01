import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
/**
 * @author Carter Marcelo
 * @version 1.0
 * @since 1.0
 * A singleton class that representns the database
 */
public class Database{
    public static Database instance = this;
    public static String username = "ENSF480";
    public static String url = "jdbc:mysql://localhost:3306/food_inventory";
    public static String password = "password";
    private Connection dbConnect;
    private Database() throws DatabaseException, SQLException{
        this.dbconnect = DriverManager.getConnection(url, username, password);
    }
static Database getInstance(){
    if(this.instance == null){
        this.instance = new Database();
    }
    return this.instance;
    }
}
