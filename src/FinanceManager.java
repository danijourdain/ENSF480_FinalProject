import java.util.*;

public class FinanceManager extends Manager {
    private static FinanceManager instance;
    private final String CARD_REGEX ="^[0-9]{16}$";
    /**
     * Uses Luhn's algorithm to verify the card number
     * @param number the credit card number
     * @return true if the number is valid, false otherwise
     * 
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
}
