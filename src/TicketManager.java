import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class TicketManager extends Manager {
    private static TicketManager instance;

    public static TicketManager getInstance() {
        if (instance == null) {
            instance = new TicketManager();
        }
        return instance;
    }

    public ArrayList<Showtime> getShowtimes() {
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM Showtime";
            PreparedStatement statement = connection.prepareStatement(query);
            return null;
        } catch (SQLException e) {
            return null;
        }

    }

    public ArrayList<Ticket> getShowtimeTickets(Showtime showtime) {
        try {
            Connection connection = Database.getConnection();

            String query = "SELECT * FROM Ticket AS T WHERE(T.ShowDateTime = ? AND T.RNumber = ? AND T.MTitle = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setObject(1, showtime.getDateTime());
            statement.setInt(2, showtime.getRoom().CAPCACITY);
            statement.setString(3, showtime.getMovie().getTitle());
            ResultSet result = statement.executeQuery();

            ArrayList<Ticket> tickets = new ArrayList<Ticket>();
            while (result.next()) {
                tickets.add(new Ticket(result.getInt(1), result.getInt(7), result.getInt(5),
                        result.getObject(3, LocalDateTime.class), showtime.getMovie(), showtime.getRoom(),
                        result.getString(8)));
            }
            return tickets;
        } catch (SQLException e) {
            return null;
        }
    }

    public Ticket PurchaseTicket(int ticketNo, LocalDateTime date, String title, int roomNo, String theaterName, User u)
            throws SQLException {
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM Ticket WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ticketNo);
        statement.setObject(2, date);
        statement.setString(3, title);
        statement.setInt(4, roomNo);
        statement.setString(5, theaterName);
        ResultSet resultSet = statement.executeQuery();
        // get the ticket from the database

        if (resultSet.next() == false) {
            throw new SQLException("Ticket does not exist");
            // if the ticket does not exist, throw an exception
        }

        String ticketEmail = resultSet.getString("Email");
        if (ticketEmail == null) {
            String movieQuery = "SELECT * FROM Movie WHERE Title=?";
            PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
            movieStatement.setString(1, title);
            ResultSet movieResult = movieStatement.executeQuery();
            if (movieResult.next() == false)
                throw new SQLException("Movie doesn't exist!");
            Movie m = new Movie(title, movieResult.getInt("Duration"));
            // get the movie associated from the ticket

            String theaterQuery = "SELECT * FROM TheaterRoom WHERE RNumber=? AND TheatreName=?";
            PreparedStatement theaterStatement = connection.prepareStatement(theaterQuery);
            theaterStatement.setInt(1, roomNo);
            theaterStatement.setString(2, theaterName);
            ResultSet theaterRestult = theaterStatement.executeQuery();
            if (theaterRestult.next() == false)
                throw new SQLException("TheaterRoom doesn't exist!");
            TheatreRoom r = new TheatreRoom(theaterRestult.getInt("Capacity"), roomNo);
            // get the theater room associated with the ticket

            Ticket t = new Ticket(ticketNo, ticketNo, roomNo, date, m, r, null);
            FinanceManager f = FinanceManager.getInstance();
            if (f.applyCredit(t, u) == true) {
                // if the ticketEmail does not exist, the ticket can be purchased
                String updateQuery = "UPDATE Ticket SET Email=? WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, u.getEmail());
                updateStatement.setInt(2, ticketNo);
                updateStatement.setObject(3, date);
                updateStatement.setString(4, title);
                updateStatement.setInt(5, roomNo);
                updateStatement.setString(6, theaterName);
                updateStatement.executeUpdate();
                // update the email of the ticket to indicate the ticket has been purchased
            } else {
                throw new SQLException("Purchase failed");
            }

            return t;
        } else {
            throw new SQLException("Ticket is already purchased!");
        }
    }

    public Ticket RefundTicket(int ticketNo, LocalDateTime date, String title, int roomNo, String theaterName, User u)
            throws SQLException {
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM Ticket WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ticketNo);
        statement.setObject(2, date);
        statement.setString(3, title);
        statement.setInt(4, roomNo);
        statement.setString(5, theaterName);
        ResultSet resultSet = statement.executeQuery();
        // get the ticket from the database

        if (resultSet.next() == false) {
            throw new SQLException("Ticket does not exist");
            // if the ticket does not exist, throw an exception
        }

        String ticketEmail = resultSet.getString("Email");
        if (ticketEmail != null) {
            // if the ticketEmail exists, the ticket can be refunded
            String updateQuery = "UPDATE Ticket SET Email=NULL WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, ticketNo);
            updateStatement.setObject(2, date);
            updateStatement.setString(3, title);
            updateStatement.setInt(4, roomNo);
            updateStatement.setString(5, theaterName);
            updateStatement.executeUpdate();
            // update the email of the ticket to indicate the ticket has been refunded

            String movieQuery = "SELECT * FROM Movie WHERE Title=?";
            PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
            movieStatement.setString(1, title);
            ResultSet movieResult = movieStatement.executeQuery();
            if (movieResult.next() == false)
                throw new SQLException("Movie doesn't exist!");
            Movie m = new Movie(title, movieResult.getInt("Duration"));
            // get the movie associated from the ticket

            String theaterQuery = "SELECT * FROM TheaterRoom WHERE RNumber=? AND TheatreName=?";
            PreparedStatement theaterStatement = connection.prepareStatement(theaterQuery);
            theaterStatement.setInt(1, roomNo);
            theaterStatement.setString(2, theaterName);
            ResultSet theaterRestult = theaterStatement.executeQuery();
            if (theaterRestult.next() == false)
                throw new SQLException("TheaterRoom doesn't exist!");
            TheatreRoom r = new TheatreRoom(theaterRestult.getInt("Capacity"), roomNo);
            // get the theater room associated with the ticket

            Ticket t = new Ticket(ticketNo, ticketNo, roomNo, date, m, r, null);
            FinanceManager f = FinanceManager.getInstance();
            f.issueRefund(t, u);

            return t;
        } else {
            throw new SQLException("Ticket hasn't been purchased");
        }
    }

    public Ticket RetrieveTicket(int ticketNo, LocalDateTime date, String title, int roomNo, String theaterName)
            throws SQLException {
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM Ticket WHERE TNo=? AND ShowDateTime=? AND MTitle=? AND RNumber=? AND TName=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, ticketNo);
        statement.setObject(2, date);
        statement.setString(3, title);
        statement.setInt(4, roomNo);
        statement.setString(5, theaterName);
        ResultSet resultSet = statement.executeQuery();
        // get the ticket from the database

        String email = new String();
        if (resultSet.next() != false) {
            email = resultSet.getString("Email");
        }

        String movieQuery = "SELECT * FROM Movie WHERE Title=?";
        PreparedStatement movieStatement = connection.prepareStatement(movieQuery);
        movieStatement.setString(1, title);
        ResultSet movieResult = movieStatement.executeQuery();
        if (movieResult.next() == false)
            throw new SQLException("Movie doesn't exist!");
        Movie m = new Movie(title, movieResult.getInt("Duration"));
        // get the movie associated from the ticket

        String theaterQuery = "SELECT * FROM TheaterRoom WHERE RNumber=? AND TheatreName=?";
        PreparedStatement theaterStatement = connection.prepareStatement(theaterQuery);
        theaterStatement.setInt(1, roomNo);
        theaterStatement.setString(2, theaterName);
        ResultSet theaterRestult = theaterStatement.executeQuery();
        if (theaterRestult.next() == false)
            throw new SQLException("TheaterRoom doesn't exist!");
        TheatreRoom r = new TheatreRoom(theaterRestult.getInt("Capacity"), roomNo);
        // get the theater room associated with the ticket

        Ticket t = new Ticket(ticketNo, ticketNo, roomNo, date, m, r, email);
        return t;
    }
}
