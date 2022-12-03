import java.util.*;
public class MovieTheatre{
    private String locName;
    private String stAddress;
    private ArrayList<TheatreRoom>theatreRooms;
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
    public void setLocName(String locName) {
        this.locName = locName;
    }
    public String getStAddress() {
        return this.stAddress;
    }
    public void setStAddress(String stAddress) {
        this.stAddress = stAddress;
    }
    public ArrayList<TheatreRoom> getListOfTheatreRooms() {
        return this.theatreRooms;
    }
    /**
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
