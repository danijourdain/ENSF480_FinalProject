import java.sql.*;
import java.time.*;

/**
 * @author Carter Marcelo
 * control class to facilitate login/logout. Can store a single user variable at a time.
 *  
 */
public class LoginRegisterManager extends Manager {
    private static LoginRegisterManager instance;
    private LoginRegisterManager(){}
    /**
     * @param email the user's email
     * @param password the user's password;
     * @return a new {@code User object if the }
     * @throws SQLException
     */
    public User login(String email, String password) throws SQLException{

        Connection connection = Database.getConnection();

        String query = "SELECT * FROM GenericUser AS U WHERE U.Email = ? AND U.Password_ =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2,password);
        ResultSet resultSet =  statement.executeQuery();
        if(resultSet.next() == false){
            throw new SQLException("User does not exist");
        }
        String fname = resultSet.getString("Fname");
        String lname = resultSet.getString("Lname");
        String userType = resultSet.getString("UserType");
        try{
            String query2 = "SELECT * FROM Credit AS C WHERE C.email=?";
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setString(1,email);
            ResultSet resultSet2 = statement2.executeQuery();
            User u = new User(email, fname, lname, userType);
            while(resultSet2.next()){
                LocalDate issueDate = resultSet2.getDate(3).toLocalDate();
                int id = resultSet.getInt(1);
                int amount = resultSet.getInt(4);
                if(issueDate.compareTo(LocalDate.now().minusDays(365))<0){
                    String stmt = "DELETE FROM Credit AS C WHERE C.ID = ?";
                    PreparedStatement prepstmt3 = connection.prepareStatement(stmt);
                    prepstmt3.setInt(1, id);
                    prepstmt3.executeUpdate();
                }
                else{
                    u.addCredit(issueDate, amount, id);
                }
            }
            return u; //create new user object based on the data retrieved to represent the current user.
            //based on the user (and any other "key classes"), make new entities to represent them
            // I'm 
        }
        catch(SQLException e){
            Database.getConnection().rollback();
            Database.closeConnection();
            throw new SQLException("Something went wrong");
        }
    }

    /**
     * @param email The user's email
     * @param fname The user's first name
     * @param lname The user's password
     * @param type The type of user
     * @return a new {@code User} object if the user was created successfully {@code false} if an error occurred
     * @throws SQLException If something goes wrong with SQL for no apparent reason :)
     */
    public User createNewUser(String email, String fname, String lname, String password) throws SQLException{
        
        Connection connection = Database.getConnection();

        // Ensure user doesn't already exist
        PreparedStatement EMAIL_QUERY = connection.prepareStatement("SELECT Email FROM GenericUser WHERE Email= ?");
        EMAIL_QUERY.setString(1, email);
        ResultSet set = EMAIL_QUERY.executeQuery();

        if(set.next() == true){
            throw new SQLException("User already exists under this Email");
        }

        // Ensure user password is valid
        if(password.length() > 32){
            throw new SQLException("Invalid Password");
        }
        // Generate generic user
        try{
            String temp = "INSERT INTO GenericUser VALUES(?, ?, ?, ?, ?)";
            PreparedStatement CREATE_USER = connection.prepareStatement(temp);
            CREATE_USER.setString(1, email);
            CREATE_USER.setString(2, fname);
            CREATE_USER.setString(3, lname);
            CREATE_USER.setString(4, password);
            CREATE_USER.setString(5, "Guest");
            CREATE_USER.executeUpdate();

            String temp2 = "INSERT INTO GuestUser VALUES(?)";
            PreparedStatement CREATE_GUEST = connection.prepareStatement(temp2);
            CREATE_GUEST.setString(1, email);
            CREATE_GUEST.executeUpdate();
            return new User(fname, lname, email, "Guest");
        }
        catch(SQLException e){
            throw new SQLException("An error occurred while trying to create this user");
        }
    }

    public void registerUser(User u, String cardNo, String StAddress) throws SQLException{
        Connection connection = Database.getConnection();
        String insert = "INSERT INTO RegisteredUser VALUES(?,?,?,?)";
        PreparedStatement register_user = connection.prepareStatement(insert);
        register_user.setString(1,u.getEmail());
        register_user.setString(2, LocalDate.now().toString());
        register_user.setString(3,StAddress);
        register_user.setString(4, cardNo);
    }

    public static LoginRegisterManager getInstance(){
        if(instance == null){
            instance = new LoginRegisterManager();
        }
        return instance;
    }
}
