package object;

public class Ticket {
    private final int TICKETNO;
    private final int PRICE;
    private final Showtime SHOWTIME;
    private String email;

    public Ticket(int ticketNo, int price, Showtime showtime, String email) {
        this.TICKETNO = ticketNo;
        this.PRICE = price;
        this.SHOWTIME = showtime;
        this.email = email;
    }

    public int getTicketNo() {
        return this.TICKETNO;
    }

    public int getPrice() {
        return this.PRICE;
    }

    public Showtime getShowtime() {
        return SHOWTIME;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}
