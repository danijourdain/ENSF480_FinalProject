
import java.sql.*;

import javax.lang.model.type.NullType;
import java.time.*;
public class LoginControl{
    private LoginControl(){}
    
    /**
     * 
     * @param email the user's email
     * @param password the user's password;
     * @return a new {@code User object if the }
     * @throws SQLException
     */
    public static User LogInUser(String email, String password) throws SQLException{
        Connection c = Database.getConnection();
        String query = "SELECT U.Email, U.Fname, U.Lname, U.Password_"+
        "FROM BaseUser AS U" + "WHERE U.Email =" + email +" AND U.password =" + password;
        Statement statement = Database.getConnection().createStatement();
        ResultSet res =  statement.executeQuery(query);
        LocalDate date = res.getDate(0).toLocalDate();
        if(res.wasNull()){
            return null;
        }
        else{
            return new User()
        }
    }

    public static boolean RegisterUser(User u){
        
    }
}
