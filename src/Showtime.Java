import java.util.*;
import java.time.*;
public class Showtime{
    private LocalDateTime dateTime;
    private Movie movie;
    private TheatreRoom room;
    public Showtime(LocalDateTime dateTime, Movie movie, TheatreRoom theatreRoom){
       this.dateTime = dateTime;
       this.movie = movie;
       this.room = theatreRoom;
    }
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public Movie getMovie() {
        return this.movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public TheatreRoom getRoom() {
        return this.room;
    }
    public void setRoom(TheatreRoom room) {
        this.room = room;
    }
    
}
