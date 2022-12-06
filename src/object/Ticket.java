package object;

public class Ticket {
    private final int TICKETNO;
    private final int PRICE;
    private final Showtime SHOWTIME;
    private String email;

    /**
     * Constructor for the Ticket object
     * @param ticketNo  The ID of this ticket
     * @param price      The price of this ticket
     * @param showtime   The showtime this ticket is for
     * @param email     The email associated with the owner of this ticket, null if the ticket is unpurchased
     */
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
