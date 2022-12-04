import java.time.*;

public class Showtime {
    private LocalDateTime ShowDateTime;
    private String MTitle;
    private int RNumber;
    private int PremiumSeats;
    private int seats;
    private String TName;
    
    public Showtime(LocalDateTime showDateTime, String mTitle, int rNumber, int premiumSeats, int seats, String tName) {
        this.ShowDateTime = showDateTime;
        this.MTitle = mTitle;
        this.RNumber = rNumber;
        this.PremiumSeats = premiumSeats;
        this.seats = seats;
        this.TName = tName;
    }

    public String getMTitle() {
        return this.MTitle;
    }

    public void setMTitle(String mTitle) {
        this.MTitle = mTitle;
    }

    public int getRNumber() {
        return this.RNumber;
    }

    public void setRNumber(int rNumber) {
        this.RNumber = rNumber;
    }

    public int getPremiumSeats() {
        return this.PremiumSeats;
    }

    public void setPremiumSeats(int premiumSeats) {
        this.PremiumSeats = premiumSeats;
    }

    public int getSeats() {
        return this.seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getTName() {
        return this.TName;
    }

    public void setTName(String tName) {
        this.TName = tName;
    }

    public LocalDateTime getShowDateTime() {
        return this.ShowDateTime;
    }

    public void setShowDateTime(LocalDateTime showDateTime) {
        this.ShowDateTime = showDateTime;
    }
}