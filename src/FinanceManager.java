import java.util.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.math.*;
public class FinanceManager extends Manager{
    private final Comparator<Credit> BY_ISSUE_DATE = Comparator.comparing(Credit::getIssueDate);
    private static FinanceManager instance;
    private final String CARD_REGEX ="^[0-9]{16}$";
    /**
     * Uses Luhn's algorithm to verify the card number
     * @param number the credit card number
     * @return true if the number is valid, false otherwise
     */
    private FinanceManager(){}

    public boolean verify(String number){
        if(!number.matches(CARD_REGEX)){
            return false;
        }
        int sum = 0;
        for(int i = 1; i<16; i++){
            if(i % 2 == 0) continue;
            int digit = 2*number.charAt(i)-'0';
            sum += digit / 10;
            sum += digit % 10;
        }
        return(sum % 10 == 0);
    }

    public static FinanceManager getInstance(){
        if(instance == null){
            instance = new FinanceManager();
        }
        return instance;
    }

    public boolean applyCredit(Ticket ticket, User user){
        try{
        Connection connection = Database.getConnection();
         //abusing java's default shallow-copying of objects and containers
        int price = ticket.getPrice();
        ArrayList<Credit> userCredits = user.getCredits();
        userCredits.sort(BY_ISSUE_DATE.reversed()); 
        int i = userCredits.size()-1;
        while(i >=0 && price > 0){
            int amt = userCredits.get(i).getCreditAmount();
            if(userCredits.get(i).hasExpired()){
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Credit AS C WHERE C.ID = ?");
                statement.setInt(1,userCredits.get(i).getID());
                statement.executeUpdate();
                userCredits.remove(i);
            }
            if(price - amt < 0){
                userCredits.get(i).setCreditAmount(amt-price);
                PreparedStatement statement = connection.prepareStatement("UPDATE Credit AS C SET C.Amount = ? WHERE C.ID = ?");
                statement.setInt(2, userCredits.get(i).getID());
                statement.setInt(1, userCredits.get(i).getCreditAmount());
                statement.executeUpdate();
                statement.close();
                break;
            }
            if(price - amt >= 0){
                userCredits.get(i).setCreditAmount(0);
                PreparedStatement statement = connection.prepareStatement("DELETE FROM Credit AS C WHERE C.ID = ?");
                statement.setInt(1,userCredits.get(i).getID());
                statement.executeUpdate();
                statement.close();
                userCredits.remove(i);
                price -= amt;
                }
            i--;
        }
        if(price > 0){
            return false;
        }
        }catch(Exception e){
            e.printStackTrace();
            Database.rollback();
            return false;
        }
        return true;
    }
    
    public void issueRefund(Ticket ticket, User user){
        if(user.getType().equalsIgnoreCase("registered")){
            user.addCredit(LocalDate.now(), ticket.getPrice(),-1);
            System.out.println("Refunded: " + ticket.getPrice()*(1-0.15));
        }
        else{
            user.addCredit(LocalDate.now(),(int)Math.round(ticket.getPrice()*(1-0.15)),-1);
            System.out.println("Refunded: " + ticket.getPrice()*(1-0.15));
        }
    }

}
