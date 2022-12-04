import java.util.*;
import java.time.*;

public class Showtime {
    private LocalDateTime dateTime;
    private String MTitle;
    private int RemSeats;

    ShowDateTime DATETIME
    NOT NULL,
    MTitle

    VARCHAR(32) NOT NULL,
    RemSeats INT NOT NULL,
    RNumber INT NOT NULL,

    TName VARCHAR(32) NOT NULL,

    public Showtime(LocalDateTime dateTime, Movie movie, TheatreRoom theatreRoom) {
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
