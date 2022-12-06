package controller;

import java.util.*;

import database.Database;
import object.Credit;
import object.User;

import java.sql.*;
import java.time.LocalDate;

public class FinanceManager {
    private static FinanceManager instance;
    private final String CARD_REGEX = "^[0-9]{16}$";

    /**
     * Uses Luhn's algorithm to verify the card number
     * 
     * @param number the credit card number
     * @return true if the number is valid, false otherwise
     */
    private FinanceManager() {
    }

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

    public static FinanceManager getInstance() {
        if (instance == null) {
            instance = new FinanceManager();
        }
        return instance;
    }

    public boolean checkCredit(int price, User user) {
        int r = applyCredit(price, user);
        if (r == 0) {
            return true;
        } else {
            return false;
        }
    }

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

    public int getTotalUserCredit(User u) {
        int amount = 0;
        for (var c : u.getCredits()) {
            amount += c.getCreditAmount();
        }
        return amount;
    }

    /**
     * 
     * @param price
     * @param u
     * @param cardNo
     * @return value charged to card if successful, else -1
     */
    public int doTransaction(int price, User u, String cardNo) {
        int remAmt = applyCredit(price, u);

        if (remAmt > 0) {
            verify(cardNo);
            return remAmt;
        }
        if (remAmt <= 0) {
            return 0;
        } else
            return -1;
    }

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
