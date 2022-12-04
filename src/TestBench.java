import java.sql.SQLException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
public class TestBench{
    public static void main(String args[]){
        boolean caught = false;
        User u = LoginRegisterManager.getInstance().login("carter.marcelo@ucalgary.ca","password");
        System.out.println("Hello there");
    
        // tesing purhcasing a ticket with credits
        Movie movie = new Movie("Guardians of The Galaxy Vol.3", 122);
        TheatreRoom tr = new TheatreRoom(250, 1);
        Ticket t = new Ticket(1, 20, 1, LocalDateTime.parse("2022-12-20T16:30:00",DateTimeFormatter.ISO_DATE_TIME), movie, tr);
        boolean b = FinanceManager.getInstance().applyCredit(t, u);
        System.out.println(b);
    }
    
}
