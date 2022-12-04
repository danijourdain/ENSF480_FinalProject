import java.time.LocalDateTime;

public class Ticket {
    private int ticketNo;
    private int price;
    private int seatNo;
    private Showtime showtime;
    private String Email;

    public Ticket(int ticketNo, int price, int seatNo, LocalDateTime time, Movie movie, TheatreRoom room,
            String Email) {
        this.ticketNo = ticketNo;
        this.price = price;
        this.seatNo = seatNo;
        showtime = new Showtime(time, movie, room);
    }

    public int getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(int ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail(String Email) {
        return this.Email;
    }
}
