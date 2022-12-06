package object;
import java.util.*;
public class MovieTheatre{
    private String locName;
    private String stAddress;
    private ArrayList<TheatreRoom>theatreRooms;

    /**
     * Constructor for the MovieTheatre object
     * @param locName The Name of the Movie Theatre
     * @param stAddress The street addresss of the movie theatre
     */
    public MovieTheatre(String locName, String stAddress){
        this.locName = locName;
        this.stAddress = stAddress;
        this.theatreRooms = new ArrayList<>();
    }
    
    public void addTheatreRoom(TheatreRoom tr){
        theatreRooms.add(tr);
    }

    public String getLocName() {
        return this.locName;
    }

    public String getStAddress() {
        return this.stAddress;
    }

    public ArrayList<TheatreRoom> getListOfTheatreRooms() {
        return this.theatreRooms;
    }
    
    /**
     * Get the theatre room associated with the specified room number
     * @param roomNo the room number: room numbers should always be >0
     * @return a {@code TheatreRoom} object if a theatre exists that matches the request;
     * null if the room number is less than 1 or does not exist
     */
    public TheatreRoom getTheatreRoom(int roomNo){
        if(roomNo >= theatreRooms.size() || roomNo-1 < 0){
            return null;
        }
        return this.theatreRooms.get(roomNo-1);
    }
}
