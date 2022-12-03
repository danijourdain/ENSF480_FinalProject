import java.time.*;
/**
 * @author Carter Marcelo
 * A class to represent Movie objects in the system
 */
public class Movie{
    private String title;
    private int duration;
    private LocalDate openDate;
    private boolean changed; // an internal flag to store updates. 
     /**
     * Constructor for the Movie object
     * @param title : is the title of the movie
     * @param duration : is the duration of the movie <b>in minutes</b>
     * @param openingDate : the opening date of 
     * the movie,<b>which will always be at least SEVEN days from</b>{@code LocalDate.now()}
     */
    public Movie(String title, int duration){
        this.title = title;
        this.duration= duration;
        this.changed = false;
        this.openDate = LocalDate.now().plusDays(7);
    }
    /**
     * Constructor for the Movie object
     * @param title : is the title of the movie
     * @param duration : is the duration of the movie <b>in minutes</b>
     * @param openingDate : the opening date of 
     * the movie, <b> which will always be at least SEVEN days from</b>{@code LocalDate.now()}
     */
    public Movie(String title, int duration, LocalDate openingDate){
        this.title = title;
        this.duration= duration;
        if(openingDate.isBefore(LocalDate.now().plusDays(7))){
            openingDate = LocalDate.now().plusDays(7);
        }
        this.openDate=openingDate;
    }  
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
        this.changed = true;
    }
    public int getDuration() {
        return this.duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
        this.changed = true;
    }
    public LocalDate getOpenDate() {
        return this.openDate;
    }
    public void setOpenDate(LocalDate openDate){
        this.openDate = openDate;
        this.changed = true;
    }
}
