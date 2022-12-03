import java.sql.*;
import java.time.*;

/**
 * @author Carter Marcelo
 * control class to facilitate login/logout. Can store a single user variable at a time.
 *  
 */
public class LoginRegisterManager{
    private static LoginRegisterManager instance;
    private LoginRegisterManager(){}
    /**
     * @param email the user's email
     * @param password the user's password;
     * @return a new {@code User object if the }
     * @throws SQLException
     */
    public User LogInUser(String email, String password) throws SQLException{
        String query = "SELECT * FROM GenericUser AS U" + "WHERE U.Email = ? AND U.Password_ =?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2,query);
        ResultSet resultSet =  statement.executeQuery(query);
        if(resultSet.next() == false){
            return null;
        }
        else{
            String fname = resultSet.getString("Fname");
            String lname = resultSet.getString("Lname");
            String userType = resultSet.getString("UserType");
            String query2 = "SELECT * FROM Credit AS C WHERE C.email=?"; //insert try catch block here
            PreparedStatement statement2 = Database.getConnection().prepareStatement(query2);
            statement2.setString(1,query2);
            ResultSet resultSet2 = statement2.executeQuery(query2);
            User u = new User(email, fname, lname, userType);
            while(resultSet2.next()){
                LocalDate issueDate = resultSet2.getDate(3).toLocalDate();
                int id = resultSet.getInt(1);
                int amount = resultSet.getInt(4);
                if(issueDate.equals(LocalDate.now().minusDays(1))){
                    String stmt = "DELETE FROM Credit AS C WHERE C.ID = ?";
                    try(PreparedStatement prepstmt3 = Database.getConnection().prepareStatement(stmt);){
                        prepstmt3.setInt(1, id);
                        prepstmt3.executeUpdate();
                    }
                    catch(SQLException E){
                        E.printStackTrace();
                        try{
                            Database.getConnection().rollback();
                            Database.closeConnection();
                        }
                        catch(SQLException F){
                            F.printStackTrace();
                        }
                    }
                }else{
                    u.addCredit(new Credit(issueDate, amount, id));
                }
            }
            return u; //create new user object based on the data retrieved to represent the current user.
            //based on the user (and any other "key classes"), make new entities to represent them
            // I'm 
        }
    };
    /**
     * @param email The user's email
     * @param fname The user's first name
     * @param lname The user's password
     * @param type The type of user
     * @return a new {@code User} object if the user was created successfully {@code false} if an error occurred
     * @throws UserAlreadyExistsException if the email already exists in the system
     * @throws PasswordTooLongException if the password exceeds 32 characters
     * @throws SQLException If something goes wrong with SQL for no apparent reason :)
     */
    public User RegisterNewAccount(String email, String fname, String lname, String password) 
    throws SQLException, PasswordTooLongException, UserAlreadyExistsException{
        if(password.length() > 32){
            throw new PasswordTooLongException();
        }
        Connection connection = Database.getConnection();
        try(PreparedStatement EMAIL_QUERY = connection.prepareStatement("SELECT Email FROM GenericUser WHERE Email= ?");){
            EMAIL_QUERY.setString(1, email);
            ResultSet set = EMAIL_QUERY.executeQuery();
            if(set.next() == false){
                String temp = "INSERT INTO GenericUser VALUES(?, ?, ?, ?, ?)";
                try(PreparedStatement CREATE_USER = connection.prepareStatement(temp);){
                    CREATE_USER.setString(1, email);
                    CREATE_USER.setString(2, fname);
                    CREATE_USER.setString(3, lname);
                    CREATE_USER.setString(4, password);
                    CREATE_USER.setString(5, "Guest");
                    CREATE_USER.executeUpdate();
                }
                catch(SQLException e){
                    System.out.println("An error occurred while trying to create this user");
                    return null;
                }
                String temp2 = "INSERT INTO GuestUser VALUES(?)";
                try(PreparedStatement CREATE_USER = connection.prepareStatement(temp2);){
                    CREATE_USER.setString(1, email);
                    CREATE_USER.executeUpdate();
                }
                catch(SQLException e){
                    System.out.println("An error occurred while trying to update the database");
                    return null;
                }
            }
            else{
                throw new UserAlreadyExistsException();
            }
        }catch(SQLException e){
            System.out.println("An error occurred while trying to create this user");
            return null;
        }
        return new User(fname, lname, email, "Guest");
    }
    public void upgradeUser(User u, String cardNo){
        //unimplemented
    }
    public static LoginRegisterManager getInstance(){
        if(instance == null){
            instance = new LoginRegisterManager();
        }
        return instance;
    }
}
