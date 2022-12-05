import java.util.*;
import java.time.*;

public class User {
    private String email;
    private String password;
    private String fname;
    private String lname;
    private String address;
    private String creditCard;
    private LocalDate expDate;
    private ArrayList<Credit> credit;
    private String type; // Guest for guest, Registered for Registered

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        type = "Guest";
        fname = null;
        lname = null;
        creditCard = null;
        address = null;
        expDate = null;
        credit = new ArrayList<>();
        // users are only constructed when they are a guest user
    }

    /**
     * A copy constructor for the class {@code user}
     * 
     * @param u is the <b>reference</b> object
     */
    public String getEmail() {
        return this.email;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public String getAddress() {
        return address;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Credit> getCredits() {
        return this.credit;
    }

    public Credit getCredit(int index) {
        if (index >= credit.size()) {
            index = index % credit.size();
        }
        return this.credit.get(index);
    }

    public void setCredit(ArrayList<Credit> credit) {
        this.credit = credit;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void addCredit(int amount) {
        this.credit.add(new Credit(LocalDate.now(), amount));
    }

    public void addCredit(LocalDate issueDate, int amount, int id) {
        this.credit.add(new Credit(issueDate, amount, id));
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public LocalDate getExpDate() {
        return this.expDate;
    }
}
