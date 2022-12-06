package controller;

import java.util.*;

import database.Database;
import object.Credit;
import object.User;

import java.sql.*;
import java.time.LocalDate;
/**
A singleton control class repsonsible 
for simulating financial transactions in the system
@author Dani Jourain
@author Nicholas Lam
@author Carter Marcelo
@author Oliver Molina
@since 1.0
 */
public class FinanceManager {
    private static FinanceManager instance;
    private final String CARD_REGEX = "^[0-9]{16}$";

   /**
    * Constructor to initialize instance
    */
    private FinanceManager() {
    }

    /**
     * Function to verify if a credit card number is valid or not. NOTE: this function
     * only checks if the credit card is a valid possible card. It does not check if the card actually
     * exists and it does not charge the card
     * @param number the credit card number to be validated in String form
     * @return boolean true or false if the card is valid or not
     */
    private boolean verify(String number) {
        if (!number.matches(CARD_REGEX)) {
            System.out.println("false");
            return false;
        }
        int sum = 0;

        for (int i = 15; i >= 0; i--) {
            char c = number.charAt(i);
            int digit = Character.getNumericValue(c);
            if (i % 2 == 0) {
                digit = digit * 2;
            }
            sum += digit / 10;
            sum += digit % 10;
        }
        return (sum % 10 == 0);
    }

    /** */
    public static FinanceManager getInstance() {
        if (instance == null) {
            instance = new FinanceManager();
        }
        return instance;
    }
    
    /**
     * Determines if sufficient credit is available for a {@code User}object
     * @param price the ticket price
     * @param user a {@code User} object corresponding 
     to the current user of the system
     * @return <b>true</b> if sufficient credit was available in the user's account;<br>
     <b>false</b> if insufficient credit was available
     */
    public boolean checkCredit(int price, User user) {
        int r = applyCredit(price, user);
        if (r == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Applies a user's credit towards a transaction by order of expiry date
     * @param price amount of credit to be deducted
     * @param user The user who's credit will be deducted by price
     * @return  returns the amount that could not be deducted
     */
    public int applyCredit(int price, User user) {
        try {
            Connection connection = Database.getConnection();
            ArrayList<Credit> userCredit = user.getCredits();

            for (int i = 0; i < userCredit.size(); i++) {
                System.out.println("HERE");
                if (price <= 0) {
                    price = 0;
                    break;
                } else if (userCredit.get(i).getCreditAmount() - price > 0) {
                    System.out.println("decreasing credit");
                    System.out.println(userCredit.get(i).getCreditAmount() - price);
                    String update = "UPDATE Credit SET Amount=? WHERE ID=?";
                    PreparedStatement deleteStatement = connection.prepareStatement(update);
                    deleteStatement.setInt(1, (userCredit.get(i).getCreditAmount() - price));
                    deleteStatement.setInt(2, userCredit.get(i).getID());
                    deleteStatement.executeUpdate();

                    userCredit.get(i).setCreditAmount(userCredit.get(i).getCreditAmount() - price);
                    price = 0;
                } else {
                    System.out.println("deleting credit");
                    String delete = "DELETE FROM Credit WHERE ID=?";
                    PreparedStatement deleteStatement = connection.prepareStatement(delete);
                    deleteStatement.setInt(1, userCredit.get(i).getID());
                    deleteStatement.executeUpdate();

                    price -= userCredit.get(i).getCreditAmount();
                    userCredit.remove(i);
                    i--;
                    System.out.println(price);
                }
            }

            return price;
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            return -1;
        }
    }

    /**
     * Function to calculate the total amount of credit a User has available to them
     * @param User u is the {@code User} object to retrieve credit
     * @return the credit
     */
    public int getTotalUserCredit(User u) {
        int amount = 0;
        for (var c : u.getCredits()) {
            amount += c.getCreditAmount();
        }
        return amount;
    }

    /**
     * Function do complete a transaction for a user. If the user has enough credit to pay
     * for the purchsase, only the credit will be used. Otherwise any remaining credit will
     * be used up and the credit card will be "charged" the remaining amount.
     * @param price The dollar amount of the transaction
     * @param u The user who is making the transaction
     * @param cardNo    The user's credit card number
     * @return value charged to card if successful, else -1
     */
    public int doTransaction(int price, User u, String cardNo) throws SQLException {
        // boolean hasCredit = checkCredit(price, u);
        if (!verify(cardNo)) {
            throw new SQLException("Invalid Credit Card Number");
        } else {
            int remAmt = applyCredit(price, u);
            if (remAmt <= 0) {
                return 0;
            } else
                return remAmt;
        }
    }

    /**
     * Function to complete a refund for a user. Adds the desired amount of credit
     * to their account, expiring one year from the current date. 
     * @param price The amount of credit to be added to the user's account
     * @param user The user to add the credit to
     * @throws SQLException If the credit cannot be inserted into the Schema, throw exception
     */
    public void issueRefund(int price, User user) throws SQLException {
        String sql = "INSERT INTO Credit(Email, IssueDate, Amount) VALUES (?, ?, ?)";
        Connection conn = Database.getConnection();
        PreparedStatement PREPS = conn.prepareStatement(sql);
        int refund = price;

        if (user.getType().equalsIgnoreCase("Guest")) {
            refund = (int) (0.85 * price);
        }

        PREPS.setString(1, user.getEmail());
        PREPS.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        PREPS.setInt(3, refund);
        PREPS.executeUpdate();

        user.addCredit(refund);
    }
}
