import java.sql.*;
import java.time.*;
import java.util.*;

public class TicketManager extends Manager {
    private static TicketManager instance;

    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    public Movie getMovie(String MTitle) throws SQLException {
        Connection connection = Database.getConnection();
        String movie_query = "SELECT * FROM Movie WHERE Movie.Title = ?";
        PreparedStatement statement = connection.prepareStatement(movie_query);
        statement.setString(1, MTitle);
        ResultSet result = statement.executeQuery();
        result.next();
        return new Movie(result.getString(1), result.getInt(2), result.getDate(3).toLocalDate());
    }

    public ArrayList<Showtime> getShowtimes() {
        try {
            Connection connection = Database.getConnection();
            String showtime_query = "SELECT * FROM Showtime";
            PreparedStatement statement = connection.prepareStatement(showtime_query);
            ResultSet result = statement.executeQuery();

            ArrayList<Showtime> showtimes = new ArrayList<Showtime>();
            while (result.next()) {
                showtimes.add(new Showtime(result.getTimestamp(1).toLocalDateTime(), result.getString(2),
                        result.getInt(3), result.getInt(4),
                        result.getInt(5), result.getString(6)));
            }
            return showtimes;
        } catch (SQLException e) {
            return new ArrayList<>();
        }

    }

    public ArrayList<Ticket> getShowtimeTickets(Showtime showtime) {
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM Ticket AS T WHERE T.ShowDateTime = ? AND T.RNumber = ? AND T.MTitle = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setTimestamp(1, Timestamp.valueOf(showtime.getShowDateTime()));
            statement.setInt(2, showtime.getRNumber());
            statement.setString(3, showtime.getMTitle());
            ResultSet result = statement.executeQuery();

            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while (result.next()) {
                tickets.add(new Ticket(result.getInt(1), result.getInt(6), showtime, result.getString(7)));
            }
            Collections.sort(tickets, Comparator.comparing(Ticket::getTicketNo));
            return tickets;
        } catch (SQLException e) {
            return new ArrayList<Ticket>();
        }
    }

    public ArrayList<Ticket> getUserTickets(User u) {
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM Ticket AS T WHERE T.Email=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, u.getEmail());
            ResultSet result = statement.executeQuery();

            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while (result.next()) {
                Timestamp showDateTime = result.getTimestamp(3);
                String mTitle = result.getString(2);
                int rNo = result.getInt(4);
                String tName = result.getString(5);

                String showQuery = "SELECT * FROM Showtime WHERE ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
                PreparedStatement showStatement = connection.prepareStatement(showQuery);
                showStatement.setTimestamp(1, showDateTime);
                showStatement.setString(2, mTitle);
                showStatement.setInt(3, rNo);
                showStatement.setString(4, tName);
                ResultSet r2 = showStatement.executeQuery();
                r2.next();

                Showtime s = new Showtime(showDateTime.toLocalDateTime(), mTitle, rNo, r2.getInt(4), r2.getInt(5),
                        tName);

                tickets.add(new Ticket(result.getInt(1), result.getInt(6), s, result.getString(7)));
            }

            Collections.sort(tickets, Comparator.comparing(Ticket::getTicketNo));
            return tickets;
        } catch (SQLException e) {
            return new ArrayList<Ticket>();
        }
    }

    public boolean purchaseTickets(ArrayList<Ticket> tickets, User u, String cardNo) throws SQLException {
        int total_price = 0;

        for (Ticket t : tickets) {
            total_price += t.getPrice();
            // sum up total price of all tickets
        }

        FinanceManager f = FinanceManager.getInstance();
        boolean purchase_success = false;

        if (u.getType().equals("Registered")) {
            purchase_success = f.doTransaction(total_price, u, u.getCreditCard());
        } else {
            purchase_success = f.doTransaction(total_price, u, cardNo);
        }
        // charge the user for the tickets

        if (purchase_success) {
            Connection connection = Database.getConnection();

            for (Ticket t : tickets) {
                Showtime s = t.getShowtime();

                String update = "UPDATE Ticket SET Email=? WHERE TNo=? AND MTitle=? AND ShowDateTime=? AND RNumber=? AND TName=?";
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setString(1, u.getEmail());
                statement.setInt(2, t.getTicketNo());
                statement.setString(3, s.getMTitle());
                statement.setTimestamp(4, Timestamp.valueOf(s.getShowDateTime()));
                statement.setInt(5, s.getRNumber());
                statement.setString(6, s.getTName());

                statement.executeUpdate();
                // update the email for the tickets
            }
        }

        return purchase_success;
    }

    public boolean RefundTicket(Ticket t, User u) throws SQLException {
        Showtime s = t.getShowtime();
        if (s.getShowDateTime().isAfter(LocalDateTime.now().minusDays(3))) {
            // if showtime is more than 72 hours away, refund is allowed
            FinanceManager f = FinanceManager.getInstance();
            f.issueRefund(t.getPrice(), u);
            Connection connection = Database.getConnection();

            String update = "UPDATE Ticket SET Email=NULL WHERE TNo=? AND MTitle=? AND ShowDateTime=? AND RNumber=? AND TName=?";
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1, t.getPrice());
            statement.setString(2, s.getMTitle());
            statement.setTimestamp(3, Timestamp.valueOf(s.getShowDateTime()));
            statement.setInt(4, s.getRNumber());
            statement.setString(5, s.getTName());

            statement.executeUpdate();
            // update the email for the tickets

            return true;
        } else {
            return false;
            // if the showtime is less than 72 hours away, refund fails
        }
    }
}
