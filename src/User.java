import java.util.*;
public class User{
    private String fname;
    private String lname;
    private String email;
    private String type; //A for admin, G for guest, R for Registered
    private ArrayList<Credit> credit;
    public User(String fname, String lname, String email, String type){
        this.credit = new ArrayList<>();
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.type = type;
    }
    public User(String fname, String lname, String email, String type, ArrayList<Credit> credits){
        this.credit = new ArrayList<>();
        credit.addAll(credits);
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.type = type;
    }
    /**
     * A copy constructor for the class {@code user}
     * @param u is the <b>reference</b> object
     */
    public User(User u){ //copy constructor
        this.fname = u.fname;
        this.lname = u.lname;
        this.email = u.email;
        this.type = u.type;
        this.credit = new ArrayList<>();
        credit.addAll(u.credit);
    }
    public String getEmail(){
        return this.email;
    }
    public String getFname(){
        return this.fname;
    }
    public String getLname(){
        return this.lname;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type = type;
    }
    public ArrayList<Credit> getCredits() {
        return this.credit;
    }
    public Credit getCredit(int index){
        if(index >= credit.size()){
            index = index % credit.size();
        }
        return this.credit.get(index);
    }
    public void setCredit(ArrayList<Credit> credit) {
        this.credit = credit;
    }
    public void addCredit(Credit c) {
        this.credit.add(c);
    }
}
