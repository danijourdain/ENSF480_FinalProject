import java.sql.*;
import java.time.*;

/**
 * @author Carter Marcelo
 *         control class to facilitate login/logout. Can store a single user
 *         variable at a time.
 * 
 */
public class LoginRegisterManager extends Manager {
    private static LoginRegisterManager instance;

    private LoginRegisterManager() {
    }

    /**
     * @param email    the user's email
     * @param password the user's password;
     * @return a new {@code User object if the }
     * @throws SQLException
     */

    public User login(String email, String password) throws SQLException {

        Connection connection = Database.getConnection();

        String query = "SELECT * FROM GenericUser AS U WHERE U.Email = ? AND U.Password_ =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next() == false) {
            throw new SQLException("Incorrect Password or Email");
        }
        String userType = resultSet.getString("UserType");
        try {
            String query2 = "SELECT C.ID, C.Email, C.IssueDate, C.Amount FROM Credit AS C WHERE C.email = ?";
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.setString(1, email);
            ResultSet resultSet2 = statement2.executeQuery();
            User u = new User(email, password);
            u.setType(userType);

            while (resultSet2.next()) {
                int id = resultSet2.getInt(1);
                LocalDate issueDate = resultSet2.getDate(3).toLocalDate();
                int amount = resultSet2.getInt(4);
                if (issueDate.compareTo(LocalDate.now().minusDays(365)) < 0 || amount <= 0) {
                    String stmt = "DELETE FROM Credit AS C WHERE C.ID = ?";
                    System.out.println(stmt);
                    PreparedStatement prepstmt3 = connection.prepareStatement(stmt);
                    prepstmt3.setInt(1, id);
                    prepstmt3.executeUpdate();
                } else {
                    u.addCredit(issueDate, amount, id);
                }
            }

            if (userType.equals("Registered")) {
                String registrationDate = "SELECT * FROM RegisteredUser AS R WHERE R.Email = ?";
                PreparedStatement registrationDateQuery = connection.prepareStatement(registrationDate);
                registrationDateQuery.setString(1, email);
                ResultSet regResult = registrationDateQuery.executeQuery();
                regResult.next();

                LocalDate expDate = regResult.getDate(6).toLocalDate();

                if (expDate.isBefore(LocalDate.now())) {
                    FinanceManager f = FinanceManager.getInstance();
                    if (!f.checkCredit(20, u)) {
                        u.setType("Expired");
                    } else {
                        f.applyCredit(20, u);
                    }
                }
            }
            return u;
        } catch (SQLException e) {
            throw new SQLException("Something went wrong");
        }
    }

    public int renewUser(User u) throws SQLException {
        Connection connection = Database.getConnection();

        LocalDate expDate = LocalDate.now().plusYears(1);

        u.setType("Registered");
        u.setExpDate(expDate);

        String query = "UPDATE RegisteredUser SET ExpDate=? WHERE Email=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, java.sql.Date.valueOf(expDate));
        statement.setString(2, u.getEmail());
        statement.executeUpdate();

        FinanceManager f = FinanceManager.getInstance();
        int res = f.doTransaction(20, u, u.getCreditCard());
        return res;
    }

    public void deregisterUser(User u) throws SQLException {
        u.setType("Guest");

        Connection connection = Database.getConnection();
        u.setAddress(null);
        u.setCreditCard(null);
        u.setFname(null);
        u.setLname(null);

        System.out.println(1);

        String deRegister = "UPDATE GenericUser SET UserType=? WHERE Email=?";
        PreparedStatement deRegStatement = connection.prepareStatement(deRegister);
        deRegStatement.setString(1, "Guest");
        deRegStatement.setString(2, u.getEmail());
        deRegStatement.executeUpdate();

        System.out.println(2);

        String deleteReg = "DELETE FROM RegisteredUser WHERE Email=?";
        PreparedStatement delStatement = connection.prepareStatement(deleteReg);
        delStatement.setString(1, u.getEmail());
        delStatement.executeUpdate();

        System.out.println(3);

        String insertReg = "INSERT INTO GuestUser(Email) VALUES(?)";
        PreparedStatement iStatement = connection.prepareStatement(insertReg);
        iStatement.setString(1, u.getEmail());
        iStatement.executeUpdate();

        System.out.println(4);
    }

    /**
     * @param email The user's email
     * @param fname The user's first name
     * @param lname The user's password
     * @param type  The type of user
     * @return a new {@code User} object if the user was created successfully
     *         {@code false} if an error occurred
     * @throws SQLException If something goes wrong with SQL for no apparent reason
     *                      :)
     */
    public User createNewUser(String email, String password) throws SQLException {

        Connection connection = Database.getConnection();

        // Ensure user doesn't already exist
        PreparedStatement EMAIL_QUERY = connection.prepareStatement("SELECT Email FROM GenericUser WHERE Email= ?");
        EMAIL_QUERY.setString(1, email);
        ResultSet set = EMAIL_QUERY.executeQuery();

        if (set.next() == true) {
            throw new SQLException("User already exists under this Email");
        }

        // Ensure user password is valid
        if (password.length() > 32) {
            throw new SQLException("Invalid Password");
        }
        // Generate generic user
        try {
            String temp = "INSERT INTO GenericUser VALUES(?, ?, ?)";
            PreparedStatement CREATE_USER = connection.prepareStatement(temp);
            CREATE_USER.setString(1, email);
            CREATE_USER.setString(2, password);
            CREATE_USER.setString(3, "Guest");
            CREATE_USER.executeUpdate();

            String temp2 = "INSERT INTO GuestUser VALUES(?)";
            PreparedStatement CREATE_GUEST = connection.prepareStatement(temp2);
            CREATE_GUEST.setString(1, email);
            CREATE_GUEST.executeUpdate();
            return new User(email, password);
        } catch (SQLException e) {
            throw new SQLException("An error occurred while trying to create this user");
        }
    }

    public void registerUser(User u, String fname, String lname, String cardNo, String StAddress) throws SQLException {
        Connection connection = Database.getConnection();
        String insert = "INSERT INTO RegisteredUser(Email, Fname, Lname, StAddress, CreditCard, ExpDate) VALUES(?,?,?,?,?,?)";
        PreparedStatement register_user = connection.prepareStatement(insert);
        register_user.setString(1, u.getEmail());
        register_user.setString(2, fname);
        register_user.setString(3, lname);
        register_user.setString(4, StAddress);
        register_user.setString(5, cardNo);
        register_user.setDate(6, java.sql.Date.valueOf(LocalDate.now().plusYears(1)));
        register_user.executeUpdate();

        String remove = "DELETE FROM GuestUser WHERE Email=?";
        PreparedStatement delete_guest = connection.prepareStatement(remove);
        delete_guest.setString(1, u.getEmail());
        delete_guest.executeUpdate();

        String update = "UPDATE GenericUser SET UserType = ? WHERE Email = ?";
        PreparedStatement update_statement = connection.prepareStatement(update);
        update_statement.setString(1, "Registered");
        update_statement.setString(2, u.getEmail());
        update_statement.executeUpdate();

        u.setFname(fname);
        u.setLname(lname);
        u.setCreditCard(cardNo);
        u.setAddress(StAddress);
        u.setExpDate(LocalDate.now());
        u.setType("Registered");
    }

    public static LoginRegisterManager getInstance() {
        if (instance == null) {
            instance = new LoginRegisterManager();
        }
        return instance;
    }
}
