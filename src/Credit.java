import java.time.LocalDate;
public class Credit{
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private int amount;
    private int id;
    private boolean hasExpired;
    public Credit(LocalDate issueDate, int amount, int id){
        this.issueDate = issueDate;
        this.expiryDate = issueDate.plusYears(1);
        this.amount = amount;
        this.id = id;
        if(expiryDate.isBefore(LocalDate.now())) hasExpired = true;
        else hasExpired = false;
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
