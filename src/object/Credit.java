package object;
import java.time.LocalDate;
public class Credit{
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private int amount;
    private int id;
    private boolean hasExpired;

    /**
     * Database Credit Constructor
     * @param issueDate The issuedate of the credit card object
     * @param amount    The amount of credit granted to the user associated with it
     * @param id        The ID of the credit object held within the database
     */
    public Credit(LocalDate issueDate, int amount, int id){
        this.issueDate = issueDate;
        this.expiryDate = issueDate.plusYears(1);
        this.amount = amount;
        this.id = id;
        if(expiryDate.isBefore(LocalDate.now())) hasExpired = true;
        else hasExpired = false;
    }

    /**
     * Program Credit Constructor
     * @param issueDate The issuedate of the credit card object
     * @param amount    The amount of credit granted to the user associated with it
     */
    public Credit(LocalDate issueDate, int amount){
        this.issueDate = issueDate;
        this.expiryDate = issueDate.plusYears(1);
        this.amount = amount;
        this.id = 0;
        if(expiryDate.isBefore(LocalDate.now())) hasExpired = true;
        else hasExpired = false;
    }

    /**
     * A copy constructor for the class Credit. 
     * Makes a deep copy of the object.
     * @param src : the reference object
     */
    public Credit(Credit src){
        this.issueDate = src.issueDate;
        this.expiryDate = src.expiryDate;
        this.amount = src.amount;
        this.id = src.id;
        this.hasExpired = src.hasExpired;
    }

    /**
     * Program Credit Constructor
     * @param amount The amount of credit granted to the user associated with it
     */
    public Credit(int amount) {
        issueDate = LocalDate.now();
        this.expiryDate = issueDate.plusYears(1);
        this.amount = amount;
        this.id = 0;
        hasExpired = false;
    }
    
    public boolean hasExpired(){
        return this.hasExpired;
    }
    
    public int getID(){
        return this.id;
    }
    public int getCreditAmount(){
        return this.amount;
    }
    public void redeemCredit(int amount){
        if(this.amount - amount <=0){
            this.amount = 0;
            this.hasExpired = true;
        }
        else this.amount -= amount;
    }
    public void setCreditAmount(int amount){
        this.amount = amount;
    }
    public LocalDate getIssueDate(){
        return this.issueDate;
    }
    public LocalDate getExpiryDate(){
        return this.expiryDate;
    }
}
