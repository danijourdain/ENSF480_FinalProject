import java.time.*;
import java.util.*;
public class Showtime{
    private LocalDate RUBookingDate;
    
    private LocalDateTime dateTime;
    private Movie movie;
    private HashMap<Integer,Ticket>
    private TheatreRoom room;
    private boolean isFull;
    public Showtime(LocalDateTime dateTime, Movie movie){
        
    }
    public boolean getIfFull(){
        if(HashMap.size() >= room.getCapacity()){
            return true;
        }
        else return false;
    }
    
}
