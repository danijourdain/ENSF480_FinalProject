package object;
public class TheatreRoom{
    public final int CAPCACITY;
    public final int ROOM_NO;

    /**
     * Constructor for the TheatreRoom object
     * @param capcacity The maximum viewer capacity of the theatreRoom
     * @param roomNo    The roomNumber of the theatre
     */
    public TheatreRoom(int capcacity, int roomNo){
        this.ROOM_NO = roomNo;
        this.CAPCACITY = capcacity;
    }
}
