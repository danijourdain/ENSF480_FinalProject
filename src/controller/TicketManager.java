package controller;

import java.sql.*;
import java.time.*;
import java.util.*;

import database.Database;
import object.*;

public class TicketManager {
    private static TicketManager instance;

    /**
     * Private constructor to inialize the instance
     */
    private TicketManager() {}

    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    /**
     * Getter for a movie object associated with a desired movie title
     * @param MTitle A {@code String} corresponding to the movie title
     * @return a {@code Movie} object containing the details of the movie with the same title
     * @throws SQLException if something goes wrong
     */
    public Movie getMovie(String MTitle) throws SQLException {
        Connection connection = Database.getConnection();
        String movie_query = "SELECT * FROM Movie WHERE Movie.Title = ?";
        PreparedStatement statement = connection.prepareStatement(movie_query);
        statement.setString(1, MTitle);
        ResultSet result = statement.executeQuery();
        result.next();
        return new Movie(result.getString(1), result.getInt(2), result.getDate(3).toLocalDate());
    }

    /**
     * Getter for all showtimes stored in the database.
     * @return An ArrayList of Showtimes corresponding to all possible showtimes in the database
     */
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
            Collections.sort(showtimes, (a, b) -> a.getShowDateTime().compareTo(b.getShowDateTime()));
            return showtimes;
        } catch (SQLException e) {
            return new ArrayList<>();
        }

    }

    /**
     * Function to get the tickets associated with a specific showtime.
     * @param showtime a {@code Showtime} object to retrieve tickets
     * @return an {@code ArrayList} of {@code Ticket} objects for the passed {@code Showtime} object
     * @see java.util.ArrayList
     */
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

    /**
     * Gets the user's tickets
     * @param u the {@code User} object corresponding to the current user
    * @return an {@code ArrayList} of {@code Ticket} objects in possession of the user
     */
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

            Collections.sort(tickets,
                    (a, b) -> a.getShowtime().getShowDateTime().compareTo(b.getShowtime().getShowDateTime()));
            return tickets;
        } catch (SQLException e) {
            return new ArrayList<Ticket>();
        }
    }

    /**
     * Facilitates the purchasing of ticket(s) for a movie
     * @param tickets The tickets that the user wishes to purchase
     * @param u a {@code User} object corresponding to the current user of the system
     * @param cardNo the credit card number of the user
     * @return the amount charged to the credit card passed to this function
     * @throws SQLException if an error occurs while accessing the database
     */
    public int purchaseTickets(ArrayList<Ticket> tickets, User u, String cardNo) throws SQLException {
        int total_price = 0;

        for (Ticket t : tickets) {
            total_price += t.getPrice();
            // sum up total price of all tickets
        }

        FinanceManager f = FinanceManager.getInstance();
        int chargeAmount = -1;

        if (u.getType().equals("Registered")) {
            chargeAmount = f.doTransaction(total_price, u, u.getCreditCard());
        } else {
            chargeAmount = f.doTransaction(total_price, u, cardNo);
        }
        // charge the user for the tickets

        if (chargeAmount >= 0) {
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

        return chargeAmount;
    }

    /**
     * Simulates the process of refunding a user's ticket
     * @param t is the ticket being refunded, as a {@code Ticket} object
     * @param u is the user requesting the refund, as a {@code User} object
     * @throws SQLException if an error occurs during the database interactions
     executed by the method.
     */
    public void refundTicket(Ticket t, User u) throws SQLException {
        try {
            Showtime s = t.getShowtime();
            if (s.getShowDateTime().isAfter(LocalDateTime.now().minusDays(3))) {
                // if showtime is more than 72 hours away, refund is allowed
                FinanceManager f = FinanceManager.getInstance();
                f.issueRefund(t.getPrice(), u);
                Connection connection = Database.getConnection();

                String update = "UPDATE Ticket SET Email=NULL WHERE TNo=? AND MTitle=? AND ShowDateTime=? AND RNumber=? AND TName=?";
                PreparedStatement statement = connection.prepareStatement(update);
                statement.setInt(1, t.getTicketNo());
                statement.setString(2, s.getMTitle());
                statement.setTimestamp(3, Timestamp.valueOf(s.getShowDateTime()));
                statement.setInt(4, s.getRNumber());
                statement.setString(5, s.getTName());

                statement.executeUpdate();
                // update the email for the tickets
            } else {
                throw new SQLException();
                // if the showtime is less than 72 hours away, refund fails
            }
        } catch (SQLException e) {
            throw new SQLException("Refund Is Unavailable");
        }
    }
}
