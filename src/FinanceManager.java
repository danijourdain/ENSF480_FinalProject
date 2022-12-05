import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;

public class FinanceManager extends Manager {
    private final Comparator<Credit> BY_ISSUE_DATE = Comparator.comparing(Credit::getIssueDate);
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
        String CARD_REGEX = "^[0-9]{16}$";
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

    private int applyCredit(int price, User user) {
        try {
            Connection connection = Database.getConnection();
            ArrayList<Credit> userCredit = user.getCredits();

            for (int i = 0; i < userCredit.size(); i++) {
                if (price <= 0) {
                    price = 0;
                    break;
                } 
                else if (userCredit.get(i).getCreditAmount() - price > 0) {
                    System.out.println("here1");
                    String update = "UPDATE Credit SET Amount=? WHERE ID=?";
                    PreparedStatement deleteStatement = connection.prepareStatement(update);
                    deleteStatement.setInt(1, userCredit.get(i).getCreditAmount() - price);
                    deleteStatement.setInt(2, userCredit.get(i).getID());
                    deleteStatement.executeUpdate();
                    userCredit.remove(i);
                    System.out.println("here2");
                    price = 0;
                }  
                else {
                    System.out.println(userCredit.get(i).getCreditAmount());
                    String delete = "DELETE FROM Credit WHERE ID=?";
                    PreparedStatement deleteStatement = connection.prepareStatement(delete);
                    deleteStatement.setInt(1, userCredit.get(i).getID());
                    deleteStatement.executeUpdate();
                    userCredit.remove(i);

                    price -= userCredit.get(i).getCreditAmount();
                    System.out.println(price);
                }
            }

            return price;
        } catch (Exception e) {
            System.out.println("crying ;-;");
            return -1;
        }

        // try {
        // Connection connection = Database.getConnection();
        // // abusing java's default shallow-copying of objects and containers
        // // int price = ticket.getPrice();
        // ArrayList<Credit> userCredits = user.getCredits();
        // userCredits.sort(BY_ISSUE_DATE.reversed());
        // int i = userCredits.size() - 1;
        // while (i >= 0 && price > 0) {
        // int amt = userCredits.get(i).getCreditAmount();
        // if (userCredits.get(i).hasExpired()) {
        // PreparedStatement statement = connection.prepareStatement("DELETE FROM Credit
        // AS C WHERE C.ID = ?");
        // statement.setInt(1, userCredits.get(i).getID());
        // statement.executeUpdate();
        // userCredits.remove(i);
        // }
        // else if (price - amt <= 0) {
        // userCredits.get(i).setCreditAmount(amt - price);
        // PreparedStatement statement = connection.prepareStatement("DELETE FROM Credit
        // AS C WHERE C.ID = ?");
        // statement.setInt(2, userCredits.get(i).getID());
        // statement.setInt(1, userCredits.get(i).getCreditAmount());
        // statement.executeUpdate();
        // statement.close();
        // break;
        // }
        // else if (price - amt > 0) {
        // userCredits.get(i).setCreditAmount(0);
        // PreparedStatement statement = connection.prepareStatement("UPDATE Credit AS C
        // WHERE C.ID = ?");
        // statement.setInt(1, userCredits.get(i).getID());
        // statement.executeUpdate();
        // statement.close();
        // userCredits.remove(i);
        // price -= amt;
        // }

        // i--;
        // }
        // if (price > 0) {
        // return price;
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // Database.rollback();
        // return price;
        // }
        // return price;
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

        System.out.println(1);
        PREPS.setString(1, user.getEmail());
        System.out.println(2);
        PREPS.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
        System.out.println(3);
        PREPS.setInt(3, refund);
        System.out.println(4);
        PREPS.executeUpdate();

        user.addCredit(refund);
    }
}
